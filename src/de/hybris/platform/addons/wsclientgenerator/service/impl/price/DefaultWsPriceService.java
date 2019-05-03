/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.price;

import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.PriceMappingResponse;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceResponseModel;
import de.hybris.platform.addons.wsclientgenerator.service.price.WSPriceService;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.PriceWebServiceConfigurationService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commerceservices.price.impl.NetPriceService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.PriceValue;

import java.util.ArrayList;
import java.util.Collection;
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
public class DefaultWsPriceService extends NetPriceService implements WSPriceService
{
	@Resource(name = "timeService")
	private TimeService timeService;

	@Resource(name = "priceWebServiceConfigurationService")
	private PriceWebServiceConfigurationService priceWsConfService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "storeSessionFacade")
	private StoreSessionFacade storeSessionFacade;

	private PriceWebServiceConfigurationModel priceConfiguration;

	private static final Logger LOG = Logger.getLogger(DefaultWsPriceService.class);


	@Override
	public List<PriceInformation> getPriceInformationsForProduct(final ProductModel model)
	{
		priceConfiguration = priceWsConfService.getWsEnabledConfiguration();
		if (priceConfiguration == null)
		{
			return super.getPriceInformationsForProduct(model);
		}
		else
		{
			final List<PriceInformation> prices = new ArrayList<PriceInformation>();
			PriceInformation priceInfo;
			String currency = "EUR";
			double value = 0;
			final WSInvoke wsinvoke = new WSInvoke();
			try
			{
				final Map<String, String> response = wsinvoke.getRequest(priceConfiguration.getUrl(),
						prepareGetParams(priceConfiguration, model), priceWsConfService.prepareHeadersParams(priceConfiguration),
						priceConfiguration.getAccept());

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
				if (value == 0 || !validateCurrency(currency))
				{
					throw new InvokeWsException("Problem in reading response!");
				}
				else
				{
					priceInfo = new PriceInformation(new PriceValue(currency, value, true));
					prices.add(priceInfo);
					return prices;
				}

			}
			catch (final InvokeWsException e)
			{
				LOG.error(e.getMessage());
				if (!priceConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
				{
					return super.getPriceInformationsForProduct(model);
				}
				else
				{
					return null;
				}
			}
			catch (final NumberFormatException e)
			{
				LOG.error("Incorrect result format" + e.getMessage());
				if (!priceConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
				{
					return super.getPriceInformationsForProduct(model);
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
