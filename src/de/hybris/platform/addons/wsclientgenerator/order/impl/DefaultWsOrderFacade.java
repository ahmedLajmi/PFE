/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.order.impl;

import de.hybris.platform.addons.wsclientgenerator.customer.impl.DefaultWsCustomerFacade;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.OrderParameter;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.order.WSOrderFacade;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.OrderWebServiceConfigurationDao;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.commercefacades.order.impl.DefaultOrderFacade;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultWsOrderFacade extends DefaultOrderFacade implements WSOrderFacade
{
	private static final String ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE = "Order with guid %s not found for current user in current BaseStore";

	@Resource(name = "orderWebServiceConfigurationDao")
	private OrderWebServiceConfigurationDao orderWebServiceConfigurationDao;

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
		orderConfiguration = orderWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.GET);

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
	public OrderData getOrderDetailsForGUID(final String guid)
	{
		final OrderModel orderModel = getCustomerAccountService().getGuestOrderForGUID(guid,
				getBaseStoreService().getCurrentBaseStore());
		if (orderModel == null)
		{
			throw new UnknownIdentifierException(String.format(ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE, guid));
		}
		return getOrderConverter().convert(orderModel);
	}

	@Override
	public List<OrderHistoryData> getOrderHistoryForStatuses(final OrderStatus... statuses)
	{
		final CustomerModel currentCustomer = (CustomerModel) getUserService().getCurrentUser();
		final BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
		List<OrderModel> orderList = getCustomerAccountService().getOrderList(currentCustomer, currentBaseStore, statuses);

		orderConfiguration = orderWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.GET);
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

		orderConfiguration = orderWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.GET);

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
		String result = "";

		final ResponseEntity<String> response = wsInvoke.getRequest(orderConfiguration.getUrl(),
				prepareRequestParams(orderConfiguration, order), orderConfiguration.getAccept());
		System.out.println(response.getBody());

		if (orderConfiguration.getAccept().equals(ResponseType.JSON))
		{
			result = jsonParseResponse(response.getBody());
		}
		else if (orderConfiguration.getAccept().equals(ResponseType.XML))
		{
			result = xmlParseResponse(response.getBody());
		}
		else if (orderConfiguration.getAccept().equals(ResponseType.TEXT))
		{
			//result = textParseResponse(response.getBody(), priceConfiguration);
		}
		return result;
	}

	@Override
	public String jsonParseResponse(final String response) throws ParseWsResponseException
	{
		final ObjectMapper mapper = new ObjectMapper();
		try
		{
			final JsonNode root = mapper.readTree(response);
			if (root.has(orderConfiguration.getStatusKey()))
			{
				return root.path(orderConfiguration.getStatusKey()).asText();

			}
			else
			{
				throw new ParseWsResponseException("Error in parsing response!! Key doesn t exist");
			}
		}
		catch (final IOException e)
		{
			throw new ParseWsResponseException("Problem in reading JSON response \n" + e.getMessage());
		}
	}

	@Override
	public String xmlParseResponse(final String response) throws ParseWsResponseException
	{
		Document doc;
		try
		{
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
			if (StringUtils.equals(orderConfiguration.getStatusKey(), doc.getDocumentElement().getTagName()))
			{
				return doc.getDocumentElement().getFirstChild().getNodeValue();
			}
			else
			{
				final NodeList nodeList = doc.getDocumentElement().getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++)
				{
					final Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						final Element elem = (Element) node;

						if (StringUtils.equals(orderConfiguration.getStatusKey(), elem.getTagName()))
						{
							return elem.getFirstChild().getNodeValue();
						}
					}
				}
				throw new ParseWsResponseException("Error in parsing response!! Key doesn t exist");
			}
		}
		catch (SAXException | ParserConfigurationException | IOException e)
		{
			throw new ParseWsResponseException("Problem in reading XML response \n" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> prepareRequestParams(final OrderWebServiceConfigurationModel orderConfiguration,
			final OrderModel model)
	{
		final Map<String, String> params = new HashMap<>();
		final Collection<PersoWSParamModel> persoParams = orderConfiguration.getPersonalisedParameters();
		final Collection<PersoWSParamModel> securityParams = orderConfiguration.getSecurityParameters();
		final Collection<OrderWebServiceParameterModel> additionelParams = orderConfiguration.getParameters();
		if (additionelParams != null && !additionelParams.isEmpty())
		{
			for (final OrderWebServiceParameterModel additionelParam : additionelParams)
			{
				if (additionelParam.getValue().equals(OrderParameter.ORDERCODE))
				{
					params.put(additionelParam.getKey(), model.getCode());
				}
				if (additionelParam.getValue().equals(OrderParameter.CLIENTCODE))
				{
					if (!userService.isAnonymousUser(userService.getCurrentUser()))
					{
						params.put(additionelParam.getKey(), userService.getCurrentUser().getUid());
					}
				}
				else if (additionelParam.getValue().equals(OrderParameter.ORDERDATE))
				{
					params.put(additionelParam.getKey(), model.getDate().toString());
				}
				else if (additionelParam.getValue().equals(OrderParameter.CURRENCYCODE))
				{
					params.put(additionelParam.getKey(), model.getCurrency().getIsocode());
				}
			}
		}
		if (securityParams != null && !securityParams.isEmpty())
		{
			for (final PersoWSParamModel securityParam : securityParams)
			{
				params.put(securityParam.getKey(), securityParam.getValue());
			}
		}
		if (persoParams != null && !persoParams.isEmpty())
		{
			for (final PersoWSParamModel persoParam : persoParams)
			{
				params.put(persoParam.getKey(), persoParam.getValue());
			}
		}
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
