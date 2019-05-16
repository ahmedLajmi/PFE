/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.order;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.service.order.WSCheckoutService;
import de.hybris.platform.addons.wsclientgenerator.service.tools.WsInvokeService;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.StockWebServiceConfigurationService;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCheckoutService;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderResult;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;



public class DefaultWsCheckoutService extends DefaultCommerceCheckoutService implements WSCheckoutService
{

	@Resource(name = "stockWebServiceConfigurationService")
	private StockWebServiceConfigurationService stockWsConfService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "wsInvokeService")
	WsInvokeService wsInvoke;

	private StockWebServiceConfigurationModel stockConfiguration;

	private static final Logger LOG = Logger.getLogger(DefaultWsCheckoutService.class);

	@Override
	@Deprecated
	public OrderModel placeOrder(final CartModel cartModel) throws InvalidCartException
	{
		final OrderModel result = super.placeOrder(cartModel);
		wsTreatement(result);
		return result;
	}


	@Override
	@Deprecated
	public OrderModel placeOrder(final CartModel cartModel, final SalesApplication salesApplication) throws InvalidCartException
	{
		final OrderModel result = super.placeOrder(cartModel);
		wsTreatement(result);
		return result;
	}


	@Override
	public CommerceOrderResult placeOrder(final CommerceCheckoutParameter parameter) throws InvalidCartException
	{
		final CommerceOrderResult result = super.placeOrder(parameter);
		wsTreatement(result.getOrder());
		return result;
	}

	@Override
	public void wsTreatement(final OrderModel model)
	{

		stockConfiguration = stockWsConfService.getWsEnabledConfiguration(MethodType.POST);
		if (stockConfiguration != null)
		{
			final List<AbstractOrderEntryModel> entries = model.getEntries();
			for (final AbstractOrderEntryModel entry : entries)
			{
				try
				{
					wsInvoke.postRequest(stockConfiguration.getUrl(),
							prepareRequestData(entry.getProduct(), model.getCode(), entry.getQuantity().toString()),
							stockWsConfService.prepareHeadersParams(stockConfiguration), stockConfiguration.getAccept(),
							stockConfiguration.getContentType());
				}
				catch (final CreateWsRequestException | InvokeWsException e)
				{
					LOG.error(e.getMessage());
				}
			}
		}

	}


	@Override
	public Map<String, Map<String, String>> prepareRequestData(final ProductModel product, final String orderCode,
			final String quantity)
	{

		final Map<String, Map<String, String>> params = new HashMap<>();

		final Map<String, String> pathPrameters = stockWsConfService.prepareDynamicPathParameters(stockConfiguration, product,
				userService.getCurrentUser());
		final Map<String, String> body = stockWsConfService.prepareDynamicQueryParameters(stockConfiguration, product,
				userService.getCurrentUser(), orderCode, quantity);
		body.putAll(stockWsConfService.prepareStaticParams(stockConfiguration));
		body.put("root", stockConfiguration.getRootKey());

		params.put("pathPrameters", pathPrameters);
		params.put("body", body);

		return params;
	}

}
