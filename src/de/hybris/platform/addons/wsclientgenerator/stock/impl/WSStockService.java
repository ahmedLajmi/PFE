/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.stock.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.price.impl.WSPriceService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.StockWebServiceConfigurationDao;
import de.hybris.platform.commerceservices.stock.impl.DefaultCommerceStockService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.WarehouseService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;


/**
 * @author Ahmed-LAJMI
 *
 */
public class WSStockService extends DefaultCommerceStockService
{

	@Resource(name = "stockWebServiceConfigurationDao")
	private StockWebServiceConfigurationDao stockWebServiceConfigurationDao;

	private StockWebServiceConfigurationModel stockConfiguration;

	private WarehouseService warehouseService;

	private static final Logger LOG = Logger.getLogger(WSPriceService.class);

	@Override
	public Long getStockLevelForProductAndBaseStore(final ProductModel product, final BaseStoreModel baseStore)
	{
		stockConfiguration = stockWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.GET);
		if (stockConfiguration == null)
		{
			return super.getStockLevelForProductAndBaseStore(product, baseStore);
		}
		else
		{

			final String url = stockConfiguration.getUrl() + "/" + product.getCode();
			final WSInvoke wsinvoke = new WSInvoke();
			final ResponseEntity<String> response = wsinvoke.get(stockConfiguration.getUrl(), "", stockConfiguration.getAccept());
			System.out.println(url + getParameters(stockConfiguration, product));
			System.out.println(response.getBody());
			final ObjectMapper mapper = new ObjectMapper();
			try
			{
				final JsonNode root = mapper.readTree(response.getBody());
				if (root.has(stockConfiguration.getStockKey()))
				{
					final JsonNode stockValue = root.path(stockConfiguration.getStockKey());
					return new Long(stockValue.asLong());
				}
				else
				{
					throw new Exception("Key doesn t exist");
				}
			}
			catch (final Exception e)
			{
				LOG.error("Erreur lors du parsing de la réponse");
				LOG.error(e.getMessage());
				return super.getStockLevelForProductAndBaseStore(product, baseStore);
			}
		}
	}

	@Override
	public Long getStockLevelForProductAndPointOfService(final ProductModel product, final PointOfServiceModel pointOfService)
	{
		stockConfiguration = stockWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.GET);
		if (stockConfiguration == null)
		{
			return super.getStockLevelForProductAndPointOfService(product, pointOfService);
		}
		else
		{

			final String url = stockConfiguration.getUrl() + "/" + product.getCode();
			final WSInvoke wsinvoke = new WSInvoke();
			final ResponseEntity<String> response = wsinvoke.get(stockConfiguration.getUrl(), "", stockConfiguration.getAccept());
			System.out.println(url + getParameters(stockConfiguration, product));
			System.out.println(response.getBody());
			final ObjectMapper mapper = new ObjectMapper();
			try
			{
				final JsonNode root = mapper.readTree(response.getBody());
				if (root.has(stockConfiguration.getStockKey()))
				{
					final JsonNode stockValue = root.path(stockConfiguration.getStockKey());
					return new Long(stockValue.asLong());
				}
				else
				{
					throw new Exception("Key doesn t exist");
				}
			}
			catch (final Exception e)
			{
				LOG.error("Erreur lors du parsing de la réponse");
				LOG.error(e.getMessage());
				return super.getStockLevelForProductAndPointOfService(product, pointOfService);
			}
		}
	}

	private String getParameters(final StockWebServiceConfigurationModel stockConfiguration, final ProductModel model)
	{
		final StringBuilder param = new StringBuilder();
		param.append("?");
		final Collection<PersoWSParamModel> persoParams = stockConfiguration.getPersonalisedParameters();
		if (persoParams != null && !persoParams.isEmpty())
		{
			for (final PersoWSParamModel persoParam : persoParams)
			{
				param.append(persoParam.getKey());
				param.append("=");
				param.append(persoParam.getValue());
				param.append("&");
			}
			param.deleteCharAt(param.length() - 1);
		}
		return param.toString();
	}


}
