/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.price;

import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.PriceMappingResponse;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceResponseModel;
import de.hybris.platform.addons.wsclientgenerator.service.price.WSCalculationService;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.PriceWebServiceConfigurationService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.order.impl.DefaultCalculationService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.PriceValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultWsCalculationService extends DefaultCalculationService implements WSCalculationService
{

	@Resource(name = "priceWebServiceConfigurationService")
	private PriceWebServiceConfigurationService priceWsConfService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "storeSessionFacade")
	private StoreSessionFacade storeSessionFacade;

	private PriceWebServiceConfigurationModel priceConfiguration;

	private static final Logger LOG = Logger.getLogger(DefaultWsPriceService.class);


	@Override
	protected PriceValue findBasePrice(final AbstractOrderEntryModel entry) throws CalculationException
	{

		priceConfiguration = priceWsConfService.getWsEnabledConfiguration();
		if (priceConfiguration == null)
		{
			return super.findBasePrice(entry);
		}
		else
		{
			String currency = "EUR";
			double value = 0;
			final WSInvoke wsinvoke = new WSInvoke();
			try
			{
				final Map<String, String> response = wsinvoke.getRequest(priceConfiguration.getUrl(),
						prepareGetParams(priceConfiguration, entry.getProduct()),
						priceWsConfService.prepareHeadersParams(priceConfiguration), priceConfiguration.getAccept());

				final String successCode = priceConfiguration.getSuccessCode();
				final String codeResponse = priceWsConfService.getResponseCode(priceConfiguration);
				if (codeResponse != null && successCode.equalsIgnoreCase(response.get(codeResponse).toString()))
				{
					for (final PriceWebServiceResponseModel item : priceConfiguration.getResponseMapping())
					{
						if (item.getValue().equals(PriceMappingResponse.PRICE))
						{
							final String itemValue = String.valueOf(response.get(item.getKey()));
							value = Double.parseDouble(StringUtils.replaceEach(itemValue, new String[]
							{ "\n" }, new String[]
							{ "" }));
						}
						else if (item.getValue().equals(PriceMappingResponse.CURRENCY))
						{
							currency = response.get(item.getKey());
						}
					}
				}
				else
				{
					priceWsConfService.saveCall(priceConfiguration,
							prepareGetParams(priceConfiguration, entry.getProduct()).toString(), response.toString(),
							response.get(codeResponse), "Response code mismatch");
					if (!priceConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
					{
						return super.findBasePrice(entry);
					}
					else
					{
						return null;
					}
				}
				if (value == 0 || !validateCurrency(currency))
				{
					throw new InvokeWsException("Problem in reading response!");
				}
				else
				{
					return new PriceValue(currency, value, true);
				}

			}
			catch (final InvokeWsException | NumberFormatException e)
			{
				LOG.error(e.getMessage());
				priceWsConfService.saveCall(priceConfiguration, prepareGetParams(priceConfiguration, entry.getProduct()).toString(),
						null, null, e.getMessage());
				if (!priceConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
				{
					return super.findBasePrice(entry);
				}
				else
				{
					return null;
				}
			}
		}
	}



	@Override
	public Map<String, Map<String, String>> prepareGetParams(final PriceWebServiceConfigurationModel priceConfiguration,
			final ProductModel product)
	{
		final Map<String, Map<String, String>> params = new HashMap<>();

		UserModel user = null;
		if (!userService.isAnonymousUser(userService.getCurrentUser()))
		{
			user = userService.getCurrentUser();
		}

		final Map<String, String> pathPrameters = priceWsConfService.prepareDynamicPathParameters(priceConfiguration, product,
				user);
		final Map<String, String> queryPrameters = priceWsConfService.prepareDynamicQueryParameters(priceConfiguration, product,
				user);
		queryPrameters.putAll(priceWsConfService.prepareStaticParams(priceConfiguration));

		params.put("pathPrameters", pathPrameters);
		params.put("queryPrameters", queryPrameters);
		return params;
	}


	@Override
	public boolean validateCurrency(final String value)
	{
		final Collection<CurrencyData> CurrencyList = storeSessionFacade.getAllCurrencies();
		for (final CurrencyData currency : CurrencyList)
		{
			if (StringUtils.equalsIgnoreCase(currency.getIsocode(), value))
			{
				return true;
			}
		}
		return false;
	}

}
