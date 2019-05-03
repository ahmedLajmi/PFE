/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.stock;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.StockMappingResponse;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceResponseModel;
import de.hybris.platform.addons.wsclientgenerator.service.impl.price.DefaultWsPriceService;
import de.hybris.platform.addons.wsclientgenerator.service.stock.WSStockService;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.StockWebServiceConfigurationService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.commerceservices.stock.impl.DefaultCommerceStockService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultWsStockService extends DefaultCommerceStockService implements WSStockService
{

	@Resource(name = "stockWebServiceConfigurationService")
	private StockWebServiceConfigurationService stockWsConfigService;

	@Resource(name = "userService")
	private UserService userService;

	private StockWebServiceConfigurationModel stockConfiguration;

	private static final Logger LOG = Logger.getLogger(DefaultWsPriceService.class);

	@Override
	public Long getStockLevelForProductAndBaseStore(final ProductModel product, final BaseStoreModel baseStore)
	{
		stockConfiguration = stockWsConfigService.getWsEnabledConfiguration(MethodType.GET);
		if (stockConfiguration == null)
		{
			return super.getStockLevelForProductAndBaseStore(product, baseStore);
		}
		else
		{
			try
			{
				return wsTreatment(product);
			}
			catch (final InvokeWsException e)
			{
				LOG.error(e.getMessage());
				if (!stockConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
				{
					return super.getStockLevelForProductAndBaseStore(product, baseStore);
				}
				else
				{
					return new Long(0);
				}
			}
		}
	}

	@Override
	public Long getStockLevelForProductAndPointOfService(final ProductModel product, final PointOfServiceModel pointOfService)
	{
		stockConfiguration = stockWsConfigService.getWsEnabledConfiguration(MethodType.GET);
		if (stockConfiguration == null)
		{
			return super.getStockLevelForProductAndPointOfService(product, pointOfService);
		}
		else
		{

			try
			{
				return wsTreatment(product);
			}
			catch (final InvokeWsException e)
			{
				LOG.error(e.getMessage());
				if (!stockConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
				{
					return super.getStockLevelForProductAndPointOfService(product, pointOfService);
				}
				else
				{
					return new Long(0);
				}
			}
		}
	}

	private Long wsTreatment(final ProductModel product) throws InvokeWsException
	{
		final WSInvoke wsinvoke = new WSInvoke();
		Long stock = new Long(0);
		final Map<String, String> response = wsinvoke.getRequest(stockConfiguration.getUrl(),
				prepareGetParams(stockConfiguration, product), stockWsConfigService.prepareHeadersParams(stockConfiguration),
				stockConfiguration.getAccept());

		for (final StockWebServiceResponseModel item : stockConfiguration.getResponseMapping())
		{
			if (item.getValue().equals(StockMappingResponse.STOCKVALUE))
			{
				final String value = String.valueOf(response.get(item.getKey()));
				stock = new Long(StringUtils.replaceEach(value, new String[]
				{ "\n", " " }, new String[]
				{ "", "" }));
			}
		}

		return stock;

	}

	@Override
	public Map<String, Map<String, String>> prepareGetParams(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product)
	{
		final Map<String, Map<String, String>> params = new HashMap<>();
		UserModel user = null;
		if (!userService.isAnonymousUser(userService.getCurrentUser()))
		{
			user = userService.getCurrentUser();
		}

		final Map<String, String> pathPrameters = stockWsConfigService.prepareDynamicPathParameters(stockConfiguration, product,
				user);
		final Map<String, String> queryPrameters = stockWsConfigService.prepareDynamicQueryParameters(stockConfiguration, product,
				user);
		queryPrameters.putAll(stockWsConfigService.prepareStaticParams(stockConfiguration));

		params.put("pathPrameters", pathPrameters);
		params.put("queryPrameters", queryPrameters);
		return params;
	}



}
