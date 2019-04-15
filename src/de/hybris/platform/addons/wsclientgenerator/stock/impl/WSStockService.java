/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.stock.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
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

import java.io.StringReader;
import java.util.Collection;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


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
			Long stock = new Long(0);
			try
			{

				final ResponseEntity<String> response = wsinvoke.get(stockConfiguration.getUrl(), "", stockConfiguration.getAccept());
				System.out.println(url + getParameters(stockConfiguration, product));
				System.out.println(response.getBody());
				if (stockConfiguration.getAccept().equals(ResponseType.JSON))
				{
					final ObjectMapper mapper = new ObjectMapper();
					final JsonNode root = mapper.readTree(response.getBody());
					if (root.has(stockConfiguration.getStockKey()))
					{
						final JsonNode stockValue = root.path(stockConfiguration.getStockKey());
						stock = new Long(stockValue.asLong());
					}
					else
					{
						throw new Exception("Key doesn t exist");
					}
				}
				else if (stockConfiguration.getAccept().equals(ResponseType.XML))
				{
					final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.parse(new InputSource(new StringReader(response.getBody())));
					boolean exist = false;
					if (StringUtils.equals(stockConfiguration.getStockKey(), doc.getDocumentElement().getTagName()))
					{
						exist = true;
						stock = new Long(doc.getDocumentElement().getFirstChild().getNodeValue());
					}
					else
					{
						final NodeList nodeList = doc.getDocumentElement().getChildNodes();
						for (int i = 0; i < nodeList.getLength(); i++)
						{
							final Node node = nodeList.item(i);
							if (StringUtils.equals(stockConfiguration.getStockKey(), node.getNodeName()))
							{
								exist = true;
								stock = new Long(node.getFirstChild().getNodeValue());
							}
						}
					}
					if (!exist)
					{
						throw new Exception("Key doesn t exist");
					}
				}
			}
			catch (final Exception e)
			{
				LOG.error("Error in parsing response");
				LOG.error(e.getMessage());
				if (stockConfiguration.getMode().equals(ModeType.WEBSERVICEWITHNATIVE))
				{
					return super.getStockLevelForProductAndBaseStore(product, baseStore);
				}
			}
			return stock;
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
			Long stock = new Long(0);
			try
			{

				final ResponseEntity<String> response = wsinvoke.get(stockConfiguration.getUrl(), "", stockConfiguration.getAccept());
				System.out.println(url + getParameters(stockConfiguration, product));
				System.out.println(response.getBody());
				if (stockConfiguration.getAccept().equals(ResponseType.JSON))
				{
					final ObjectMapper mapper = new ObjectMapper();
					final JsonNode root = mapper.readTree(response.getBody());
					if (root.has(stockConfiguration.getStockKey()))
					{
						final JsonNode stockValue = root.path(stockConfiguration.getStockKey());
						stock = new Long(stockValue.asLong());
					}
					else
					{
						throw new Exception("Key doesn t exist");
					}
				}
				else if (stockConfiguration.getAccept().equals(ResponseType.XML))
				{
					final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.parse(new InputSource(new StringReader(response.getBody())));
					boolean exist = false;
					if (StringUtils.equals(stockConfiguration.getStockKey(), doc.getDocumentElement().getTagName()))
					{
						exist = true;
						stock = new Long(doc.getDocumentElement().getFirstChild().getNodeValue());
					}
					else
					{
						final NodeList nodeList = doc.getDocumentElement().getChildNodes();
						for (int i = 0; i < nodeList.getLength(); i++)
						{
							final Node node = nodeList.item(i);
							if (StringUtils.equals(stockConfiguration.getStockKey(), node.getNodeName()))
							{
								exist = true;
								stock = new Long(node.getFirstChild().getNodeValue());
							}
						}
					}
					if (!exist)
					{
						throw new Exception("Key doesn t exist");
					}
				}
			}
			catch (final Exception e)
			{
				LOG.error("Error in parsing response");
				LOG.error(e.getMessage());
				if (stockConfiguration.getMode().equals(ModeType.WEBSERVICEWITHNATIVE))
				{
					return super.getStockLevelForProductAndPointOfService(product, pointOfService);
				}
			}
			return stock;
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
