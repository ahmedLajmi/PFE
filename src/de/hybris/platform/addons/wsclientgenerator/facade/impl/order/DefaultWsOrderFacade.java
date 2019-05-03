/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.facade.impl.order;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.OrderMappingResponse;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.facade.impl.customer.DefaultWsCustomerFacade;
import de.hybris.platform.addons.wsclientgenerator.facade.order.WSOrderFacade;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceResponseModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.OrderWebServiceConfigurationService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.order.impl.DefaultOrderFacade;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultWsOrderFacade extends DefaultOrderFacade implements WSOrderFacade
{
	private static final String ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE = "Order with guid %s not found for current user in current BaseStore";

	@Resource(name = "orderWebServiceConfigurationService")
	private OrderWebServiceConfigurationService orderWsConfService;

	@Resource(name = "userService")
	private UserService userService;

	private OrderWebServiceConfigurationModel orderConfiguration;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	private static final Logger LOG = Logger.getLogger(DefaultWsCustomerFacade.class);

	@Override
	public OrderData getOrderDetailsForCode(final String code)
	{
		final BaseStoreModel baseStoreModel = getBaseStoreService().getCurrentBaseStore();

		OrderModel orderModel = null;
		if (getCheckoutCustomerStrategy().isAnonymousCheckout())
		{
			orderModel = getCustomerAccountService().getOrderDetailsForGUID(code, baseStoreModel);
		}
		else
		{
			try
			{
				orderModel = getCustomerAccountService().getOrderForCode((CustomerModel) getUserService().getCurrentUser(), code,
						baseStoreModel);
			}
			catch (final ModelNotFoundException e)
			{
				throw new UnknownIdentifierException(String.format(ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE, code));
			}
		}

		if (orderModel == null)
		{
			throw new UnknownIdentifierException(String.format(ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE, code));
		}

		// custom treatment
		orderConfiguration = orderWsConfService.getWsEnabledConfiguration(MethodType.GET);

		if (orderConfiguration != null)
		{
			try
			{
				final OrderStatus status = validateStatuses(WsGetStatusCode(orderModel));
				if (status != null)
				{
					orderModel.setStatus(status);
				}
				else
				{
					throw new ParseWsResponseException(
							"Error in parsing response!! Invalid status for order number: " + orderModel.getCode());
				}
			}
			catch (final ParseWsResponseException | InvokeWsException e)
			{
				LOG.error(e.getMessage());
				if (orderConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
				{
					return null;
				}
			}
		}
		return getOrderConverter().convert(orderModel);
	}

	@Override
	public List<OrderHistoryData> getOrderHistoryForStatuses(final OrderStatus... statuses)
	{
		final CustomerModel currentCustomer = (CustomerModel) getUserService().getCurrentUser();
		final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
		List<OrderModel> orderList = getCustomerAccountService().getOrderList(currentCustomer, currentBaseStore, statuses);

		orderConfiguration = orderWsConfService.getWsEnabledConfiguration(MethodType.GET);
		if (orderConfiguration != null)
		{
			orderList = updateStatuses(orderList, orderConfiguration);
		}

		return Converters.convertAll(orderList, getOrderHistoryConverter());

	}

	@Override
	public SearchPageData<OrderHistoryData> getPagedOrderHistoryForStatuses(final PageableData pageableData,
			final OrderStatus... statuses)
	{
		final CustomerModel currentCustomer = (CustomerModel) getUserService().getCurrentUser();
		final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
		final SearchPageData<OrderModel> orderResults = getCustomerAccountService().getOrderList(currentCustomer, currentBaseStore,
				statuses, pageableData);

		orderConfiguration = orderWsConfService.getWsEnabledConfiguration(MethodType.GET);

		if (orderConfiguration != null)
		{
			orderResults.setResults(updateStatuses(orderResults.getResults(), orderConfiguration));
		}
		return convertPageData(orderResults, getOrderHistoryConverter());
	}

	@Override
	public List<OrderModel> updateStatuses(final List<OrderModel> orders,
			final OrderWebServiceConfigurationModel orderConfiguration)
	{
		for (final OrderModel order : orders)
		{
			try
			{
				final OrderStatus status = validateStatuses(WsGetStatusCode(order));
				if (status != null)
				{
					order.setStatus(status);
				}
				else
				{
					throw new ParseWsResponseException(
							"Error in parsing response!! Invalid status for order number: " + order.getCode());
				}
			}
			catch (final ParseWsResponseException | InvokeWsException e)
			{
				LOG.error(e.getMessage());
				if (orderConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
				{
					return null;
				}
			}
		}
		return orders;
	}

	@Override
	public String WsGetStatusCode(final OrderModel order) throws ParseWsResponseException, InvokeWsException
	{
		final WSInvoke wsInvoke = new WSInvoke();
		final Map<String, String> response = wsInvoke.getRequest(orderConfiguration.getUrl(), prepareGetParams(order),
				orderWsConfService.prepareHeadersParams(orderConfiguration), orderConfiguration.getAccept());

		for (final OrderWebServiceResponseModel item : orderConfiguration.getResponseMapping())
		{
			if (item.getValue().equals(OrderMappingResponse.STATUS))
			{
				return String.valueOf(response.get(item.getKey()));
			}
		}
		throw new ParseWsResponseException("Invalid keys for parsing");
	}

	@Override
	public Map<String, Map<String, String>> prepareGetParams(final OrderModel model)
	{
		final Map<String, Map<String, String>> params = new HashMap<>();
		UserModel user = null;
		if (!userService.isAnonymousUser(userService.getCurrentUser()))
		{
			user = userService.getCurrentUser();
		}

		final Map<String, String> pathPrameters = orderWsConfService.prepareDynamicPathParameters(orderConfiguration, model, user);
		final Map<String, String> queryPrameters = orderWsConfService.prepareDynamicQueryParameters(orderConfiguration, model,
				user);
		queryPrameters.putAll(orderWsConfService.prepareStaticParams(orderConfiguration));

		params.put("pathPrameters", pathPrameters);
		params.put("queryPrameters", queryPrameters);
		return params;
	}

	@Override
	public OrderStatus validateStatuses(final String value)
	{
		final List<OrderStatus> statuses = enumerationService.getEnumerationValues(OrderStatus.class);
		for (final OrderStatus status : statuses)
		{
			if (StringUtils.equalsIgnoreCase(status.getCode(), value))
			{
				return status;
			}
		}
		return null;
	}


}
