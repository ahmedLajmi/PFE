/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.price.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.PriceParameter;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.price.WSPriceService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.PriceWebServiceConfigurationService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commerceservices.price.impl.NetPriceService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.PriceValue;
import de.hybris.platform.ycommercewebservices.storesession.data.CurrencyDataList;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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
public class DefaultWsPriceService extends NetPriceService implements WSPriceService
{
	@Resource(name = "timeService")
	private TimeService timeService;

	@Resource(name = "priceWebServiceConfigurationService")
	private PriceWebServiceConfigurationService priceWebServiceConfigurationService;

	@Resource(name = "userService")
	private UserService userService;

	private PriceWebServiceConfigurationModel priceConfiguration;

	private static final Logger LOG = Logger.getLogger(DefaultWsPriceService.class);


	@Override
	public List<PriceInformation> getPriceInformationsForProduct(final ProductModel model)
	{
		priceConfiguration = priceWebServiceConfigurationService.getWsEnabledConfiguration();
		if (priceConfiguration == null)
		{
			return super.getPriceInformationsForProduct(model);
		}
		else
		{
			Map<String, String> result = new HashMap<>();
			final List<PriceInformation> prices = new ArrayList<PriceInformation>();
			PriceInformation priceInfo;
			final WSInvoke wsinvoke = new WSInvoke();
			try
			{
				final ResponseEntity<String> response = wsinvoke.getRequest(priceConfiguration.getUrl(),
						prepareRequestParams(priceConfiguration, model), priceConfiguration.getAccept());
				System.out.println(response.getBody());

				if (priceConfiguration.getAccept().equals(ResponseType.JSON))
				{
					result = jsonParseResponse(response.getBody());
				}
				else if (priceConfiguration.getAccept().equals(ResponseType.XML))
				{
					result = xmlParseResponse(response.getBody());
				}
				else if (priceConfiguration.getAccept().equals(ResponseType.TEXT))
				{
					result = textParseResponse(response.getBody(), priceConfiguration);
				}
				priceInfo = new PriceInformation(
						new PriceValue(result.get("currency"), Double.parseDouble(result.get("price")), true));
				prices.add(priceInfo);
				return prices;
			}
			catch (final ParseWsResponseException | InvokeWsException e)
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
	public Map<String, String> jsonParseResponse(final String response) throws ParseWsResponseException
	{
		final Map<String, String> result = new HashMap<>();
		final ObjectMapper mapper = new ObjectMapper();
		try
		{
			final JsonNode root = mapper.readTree(response);
			if (root.has(priceConfiguration.getPriceKey()))
			{
				final String price = root.path(priceConfiguration.getPriceKey()).asText();
				String currency = "EUR";
				if (root.has(priceConfiguration.getCurrencyKey()))
				{
					currency = root.path(priceConfiguration.getCurrencyKey()).asText();
					if (!validateCurrency(currency))
					{
						throw new ParseWsResponseException("Error in parsing response!! Invalid currency value");
					}
				}
				else
				{
					if (!userService.isAnonymousUser(userService.getCurrentUser()))
					{
						currency = userService.getCurrentUser().getSessionCurrency().getIsocode().toUpperCase();
					}
				}

				result.put("price", price);
				result.put("currency", currency);
				return result;
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
	public Map<String, String> xmlParseResponse(final String response) throws ParseWsResponseException
	{
		final Map<String, String> result = new HashMap<>();
		String price = "";
		String currency = "EUR";
		Document doc;
		try
		{
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
			if (StringUtils.equals(priceConfiguration.getPriceKey(), doc.getDocumentElement().getTagName()))
			{
				price = doc.getDocumentElement().getFirstChild().getNodeValue();
				result.put("price", price);
				result.put("currency", currency);
				return result;
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

						if (StringUtils.equals(priceConfiguration.getPriceKey(), elem.getTagName()))
						{
							price = elem.getFirstChild().getNodeValue();
						}
						else if (StringUtils.equals(priceConfiguration.getCurrencyKey(), elem.getTagName()))
						{
							currency = elem.getFirstChild().getNodeValue().toUpperCase();
							if (!validateCurrency(currency))
							{
								throw new ParseWsResponseException("Error in parsing response!! Invalid currency value");
							}
						}
					}
				}
				if (price.isEmpty())
				{
					throw new ParseWsResponseException("Error in parsing response!! Key doesn t exist");
				}
				else
				{
					result.put("price", price);
					result.put("currency", currency);
					return result;
				}
			}
		}
		catch (SAXException | ParserConfigurationException | IOException e)
		{
			throw new ParseWsResponseException("Problem in reading XML response \n" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> textParseResponse(final String response, final PriceWebServiceConfigurationModel priceConfiguration)
			throws ParseWsResponseException
	{
		final Map<String, String> result = new HashMap<>();
		final String sepearator = priceConfiguration.getTextSeperator();
		try
		{
			final String[] arrOfStr = response.split(sepearator);
			if (arrOfStr != null && arrOfStr.length >= Integer.parseInt(priceConfiguration.getPriceKey()) - 1)
			{
				result.put("price", arrOfStr[Integer.parseInt(priceConfiguration.getPriceKey()) - 1]);
				if (arrOfStr.length >= Integer.parseInt(priceConfiguration.getCurrencyKey()) - 1)
				{
					final String currency = arrOfStr[Integer.parseInt(priceConfiguration.getCurrencyKey()) - 1].toUpperCase();
					if (validateCurrency(currency))
					{
						result.put("currency", arrOfStr[Integer.parseInt(priceConfiguration.getCurrencyKey()) - 1].toUpperCase());
					}
					else
					{
						throw new ParseWsResponseException("Error in parsing response!! Invalid currency value");
					}
				}
				return result;
			}
			else
			{
				throw new ParseWsResponseException("Error in parsing response!! Key doesn t exist");
			}
		}
		catch (final Exception e)
		{
			throw new ParseWsResponseException("Problem in reading TEXT response \n" + e.getMessage());
		}
	}

	@Override
	public Map<String, String> prepareRequestParams(final PriceWebServiceConfigurationModel priceConfiguration,
			final ProductModel model)
	{
		final Collection<PersoWSParamModel> persoParams = priceConfiguration.getPersonalisedParameters();
		final Collection<PriceWebServiceParameterModel> additionelParams = priceConfiguration.getParameters();
		final Map<String, String> params = new HashMap<>();
		if (additionelParams != null && !additionelParams.isEmpty())
		{
			for (final PriceWebServiceParameterModel additionelParam : additionelParams)
			{
				if (additionelParam.getValue().equals(PriceParameter.PRODUCTCODE))
				{
					params.put(additionelParam.getKey(), model.getCode());
				}
				if (additionelParam.getValue().equals(PriceParameter.CLIENTCODE))
				{
					if (!userService.isAnonymousUser(userService.getCurrentUser()))
					{
						params.put(additionelParam.getKey(), userService.getCurrentUser().getUid());
					}
				}
				else if (additionelParam.getValue().equals(PriceParameter.CATEGORIECODE))
				{
					if (!userService.isAnonymousUser(userService.getCurrentUser()))
					{
						final Collection<CategoryModel> categories = model.getSupercategories();
						if (categories != null && !categories.isEmpty())
						{
							params.put(additionelParam.getKey(), categories.toArray().toString());
						}
					}
				}
			}
		}
		params.putAll(priceWebServiceConfigurationService.preparePersoParams(priceConfiguration));
		return params;
	}

	@Override
	public boolean validateCurrency(final String value)
	{

		final Collection<CurrencyData> CurrencyList = new CurrencyDataList().getCurrencies();
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
