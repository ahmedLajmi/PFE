/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.price.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.PriceParameter;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.PriceWebServiceConfigurationDao;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.price.impl.NetPriceService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.servicelayer.exceptions.SystemException;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.PriceValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;


/**
 * @author Ahmed-LAJMI
 *
 */
public class WSPriceService extends NetPriceService
{
	@Resource(name = "timeService")
	private TimeService timeService;

	@Resource(name = "priceWebServiceConfigurationDao")
	private PriceWebServiceConfigurationDao priceWebServiceConfigurationDao;

	@Resource(name = "userService")
	private UserService userService;

	private PriceWebServiceConfigurationModel priceConfiguration;

	private static final Logger LOG = Logger.getLogger(WSPriceService.class);


	@Override
	public List<PriceInformation> getPriceInformationsForProduct(final ProductModel model)
	{
		priceConfiguration = priceWebServiceConfigurationDao.getWsEnabledConfiguration();
		if (priceConfiguration == null)
		{
			return defaultTreatment(model);
		}
		else
		{

			final List<PriceInformation> prices = new ArrayList<PriceInformation>();
			final String url = priceConfiguration.getUrl() + "/" + model.getCode();
			final WSInvoke wsinvoke = new WSInvoke();
			final ResponseEntity<String> response = wsinvoke.get(priceConfiguration.getUrl(), "", priceConfiguration.getAccept());
			System.out.println(url + getParameters(priceConfiguration, model));
			System.out.println(response.getBody());
			final ObjectMapper mapper = new ObjectMapper();
			try
			{
				if (priceConfiguration.getAccept().equals(ResponseType.JSON))
				{
					final JsonNode root = mapper.readTree(response.getBody());
					if (root.has(priceConfiguration.getPriceKey()))
					{
						final PriceInformation price;
						if (root.has(priceConfiguration.getCurrencyKey()))
						{
							price = new PriceInformation(new PriceValue(root.path(priceConfiguration.getCurrencyKey()).asText(),
									root.path(priceConfiguration.getPriceKey()).asDouble(), true));
						}
						else
						{
							if (!userService.isAnonymousUser(userService.getCurrentUser()))
							{
								final String currency = userService.getCurrentUser().getSessionCurrency().getIsocode();
								price = new PriceInformation(
										new PriceValue(currency, root.path(priceConfiguration.getPriceKey()).asDouble(), true));
							}
							else
							{
								price = new PriceInformation(
										new PriceValue("EUR", root.path(priceConfiguration.getPriceKey()).asDouble(), true));
							}

						}
						prices.add(price);
					}
					else
					{
						throw new Exception("Key doesn t exist");
					}
				}
				else if (priceConfiguration.getAccept().equals(ResponseType.XML))
				{
					//
					System.out.println("cc c est la");
				}

			}
			catch (final Exception e)
			{
				LOG.error("Erreur lors du parsing de la réponse");
				LOG.error(e.getMessage());
			}
			return prices;

		}
	}


	private List<PriceInformation> defaultTreatment(final ProductModel model)
	{
		final boolean net = getNetGrossStrategy().isNet();
		final Product product = (Product) getModelService().getSource(model);
		try
		{
			return product.getPriceInformations(timeService.getCurrentTime(), net);
		}
		catch (final JaloPriceFactoryException e)
		{
			throw new SystemException(e.getMessage(), e);
		}
	}

	private String getParameters(final PriceWebServiceConfigurationModel priceConfiguration, final ProductModel model)
	{
		final StringBuilder param = new StringBuilder();
		param.append("?");
		final Collection<PersoWSParamModel> persoParams = priceConfiguration.getPersonalisedParameters();
		final Collection<PriceWebServiceParameterModel> additionelParams = priceConfiguration.getParameters();
		if (additionelParams != null && !additionelParams.isEmpty())
		{
			for (final PriceWebServiceParameterModel additionelParam : additionelParams)
			{
				if (additionelParam.getValue().equals(PriceParameter.CLIENTCODE))
				{
					if (!userService.isAnonymousUser(userService.getCurrentUser()))
					{
						param.append(additionelParam.getKey());
						param.append("=");
						param.append(userService.getCurrentUser().getUid());
						param.append("&");
					}
				}
				else if (additionelParam.getValue().equals(PriceParameter.CATEGORIECODE))
				{
					if (!userService.isAnonymousUser(userService.getCurrentUser()))
					{
						final Collection<CategoryModel> categories = model.getSupercategories();
						if (categories != null && !categories.isEmpty())
						{
							param.append(additionelParam.getKey());
							param.append("=");
							param.append(categories.toArray().toString());
							param.append("&");
						}
						param.deleteCharAt(param.length() - 1);
					}
				}
			}

		}
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
