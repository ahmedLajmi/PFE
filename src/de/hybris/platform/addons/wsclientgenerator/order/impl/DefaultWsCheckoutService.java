/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.order.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.StockParameter;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.order.WSCheckoutService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.StockWebServiceConfigurationDao;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCheckoutService;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderResult;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;



public class DefaultWsCheckoutService extends DefaultCommerceCheckoutService implements WSCheckoutService
{

	@Resource(name = "stockWebServiceConfigurationDao")
	private StockWebServiceConfigurationDao stockWebServiceConfigurationDao;

	@Resource(name = "userService")
	private UserService userService;

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
		stockConfiguration = stockWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.POST);
		if (stockConfiguration != null)
		{
			final WSInvoke wsinvoke = new WSInvoke();
			final List<AbstractOrderEntryModel> entries = model.getEntries();
			ResponseEntity<String> response;
			for (final AbstractOrderEntryModel entry : entries)
			{
				try
				{
					response = wsinvoke.postRequest(stockConfiguration.getUrl(), prepareRequest(entry), stockConfiguration.getAccept(),
							stockConfiguration.getContentType());
					System.out.println(response.getBody());
				}
				catch (final CreateWsRequestException | InvokeWsException e)
				{
					LOG.error(e.getMessage());
				}
			}
		}
	}


	@Override
	public MultiValueMap<String, String> prepareRequest(final AbstractOrderEntryModel entry)
	{
		final MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		for (final StockWebServiceParameterModel additionelParam : stockConfiguration.getParameters())
		{
			if (additionelParam.getValue().equals(StockParameter.STOCKCODE))
			{
				request.add(additionelParam.getKey(), entry.getQuantity().toString());

			}
			else if (additionelParam.getValue().equals(StockParameter.PRODUCTCODE))
			{
				request.add(additionelParam.getKey(), entry.getProduct().getCode());
			}
			else if (additionelParam.getValue().equals(StockParameter.ORDERCODE))
			{
				request.add(additionelParam.getKey(), entry.getOrder().getCode());
			}
			else if (additionelParam.getValue().equals(StockParameter.CLIENTCODE))
			{
				if (!userService.isAnonymousUser(userService.getCurrentUser()))
				{
					request.add(additionelParam.getKey(), userService.getCurrentUser().getUid());
				}
			}
		}
		for (final PersoWSParamModel persoParam : stockConfiguration.getPersonalisedParameters())
		{
			request.add(persoParam.getKey(), persoParam.getValue());
		}
		for (final PersoWSParamModel securityParam : stockConfiguration.getSecurityParameters())
		{
			request.add(securityParam.getKey(), securityParam.getValue());
		}
		if (stockConfiguration.getRootKey() != null)
		{
			request.add("root", stockConfiguration.getRootKey());
		}
		return request;
	}

}
