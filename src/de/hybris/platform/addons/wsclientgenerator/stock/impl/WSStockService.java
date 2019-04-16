/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.stock.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.enums.StockParameter;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.price.impl.WSPriceService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.StockWebServiceConfigurationDao;
import de.hybris.platform.commerceservices.stock.impl.DefaultCommerceStockService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * @author Ahmed-LAJMI
 *
 */
public class WSStockService extends DefaultCommerceStockService
{

	@Resource(name = "stockWebServiceConfigurationDao")
	private StockWebServiceConfigurationDao stockWebServiceConfigurationDao;

	@Resource(name = "userService")
	private UserService userService;

	private StockWebServiceConfigurationModel stockConfiguration;

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
			final WSInvoke wsinvoke = new WSInvoke();
			Long stock = new Long(0);
			try
			{

				final ResponseEntity<String> response = wsinvoke.get(prepareUrl(stockConfiguration, product),
						stockConfiguration.getAccept());
				System.out.println(prepareUrl(stockConfiguration, product));
				System.out.println(response.getBody());
				if (stockConfiguration.getAccept().equals(ResponseType.JSON))
				{
					stock = jsonParseResponse(response.getBody());
				}
				else if (stockConfiguration.getAccept().equals(ResponseType.XML))
				{
					stock = xmlParseResponse(response.getBody());
				}
			}
			catch (final ParseWsResponseException | InvokeWsException e)
			{
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
			final WSInvoke wsinvoke = new WSInvoke();
			Long stock = new Long(0);
			try
			{

				final ResponseEntity<String> response = wsinvoke.get(prepareUrl(stockConfiguration, product),
						stockConfiguration.getAccept());
				System.out.println(prepareUrl(stockConfiguration, product));
				System.out.println(response.getBody());
				if (stockConfiguration.getAccept().equals(ResponseType.JSON))
				{
					stock = jsonParseResponse(response.getBody());
				}
				else if (stockConfiguration.getAccept().equals(ResponseType.XML))
				{
					stock = xmlParseResponse(response.getBody());
				}
			}
			catch (final ParseWsResponseException | InvokeWsException e)
			{
				LOG.error(e.getMessage());
				if (stockConfiguration.getMode().equals(ModeType.WEBSERVICEWITHNATIVE))
				{
					return super.getStockLevelForProductAndPointOfService(product, pointOfService);
				}
			}
			return stock;
		}
	}

	private Long jsonParseResponse(final String response) throws ParseWsResponseException
	{
		final ObjectMapper mapper = new ObjectMapper();
		try
		{
			final JsonNode root = mapper.readTree(response);
			if (root.has(stockConfiguration.getStockKey()))
			{
				final JsonNode stockValue = root.path(stockConfiguration.getStockKey());
				return new Long(stockValue.asLong());
			}
			else
			{
				throw new ParseWsResponseException("Key doesn t exist");
			}
		}
		catch (final IOException e)
		{
			throw new ParseWsResponseException("Problem in reading JSON response \n" + e.getMessage());
		}
	}

	private Long xmlParseResponse(final String response) throws ParseWsResponseException
	{
		try
		{
			final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(response)));
			if (StringUtils.equals(stockConfiguration.getStockKey(), doc.getDocumentElement().getTagName()))
			{
				return new Long(doc.getDocumentElement().getFirstChild().getNodeValue());
			}
			else
			{
				final NodeList nodeList = doc.getDocumentElement().getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++)
				{
					final Node node = nodeList.item(i);
					if (StringUtils.equals(stockConfiguration.getStockKey(), node.getNodeName()))
					{
						return new Long(node.getFirstChild().getNodeValue());
					}
				}
				throw new ParseWsResponseException("Key doesn t exist");
			}
		}
		catch (SAXException | ParserConfigurationException | IOException e)
		{
			throw new ParseWsResponseException("Problem in reading XML response \n" + e.getMessage());
		}
	}

	private String prepareUrl(final StockWebServiceConfigurationModel stockConfiguration, final ProductModel model)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(stockConfiguration.getUrl());
		final Collection<PersoWSParamModel> persoParams = stockConfiguration.getPersonalisedParameters();
		final Collection<PersoWSParamModel> securityParams = stockConfiguration.getSecurityParameters();
		final Collection<StockWebServiceParameterModel> additionelParams = stockConfiguration.getParameters();
		if (additionelParams != null && !additionelParams.isEmpty())
		{
			for (final StockWebServiceParameterModel additionelParam : additionelParams)
			{
				if (additionelParam.getValue().equals(StockParameter.PRODUCTCODE))
				{
					uriBuilder.queryParam(additionelParam.getKey(), model.getCode());
				}
				if (additionelParam.getValue().equals(StockParameter.CLIENTCODE))
				{
					if (!userService.isAnonymousUser(userService.getCurrentUser()))
					{
						uriBuilder.queryParam(additionelParam.getKey(), userService.getCurrentUser().getUid());
					}
				}
			}
		}
		if (securityParams != null && !securityParams.isEmpty())
		{
			for (final PersoWSParamModel securityParam : securityParams)
			{
				uriBuilder.queryParam(securityParam.getKey(), securityParam.getValue());
			}
		}
		{
			if (persoParams != null && !persoParams.isEmpty())
			{
				for (final PersoWSParamModel persoParam : persoParams)
				{
					uriBuilder.queryParam(persoParam.getKey(), persoParam.getValue());
				}
			}
		}
		return uriBuilder.toUriString();
	}


}
