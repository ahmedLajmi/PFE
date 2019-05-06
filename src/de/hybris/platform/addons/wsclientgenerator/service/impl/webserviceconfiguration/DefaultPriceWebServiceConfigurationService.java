/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.PriceWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.enums.PriceMappingResponse;
import de.hybris.platform.addons.wsclientgenerator.enums.PriceParameter;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceResponseModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.PriceWebServiceConfigurationService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultPriceWebServiceConfigurationService extends AbstractWebServiceConfigurationService
		implements PriceWebServiceConfigurationService
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "priceWebServiceConfigurationDao")
	private PriceWebServiceConfigurationDao priceWebServiceConfigurationDao;

	@Override
	public List<PriceWebServiceConfigurationModel> getAllConfigurations()
	{
		return priceWebServiceConfigurationDao.getAllConfigurations();
	}

	@Override
	public PriceWebServiceConfigurationModel getWsEnabledConfiguration()
	{
		return priceWebServiceConfigurationDao.getWsEnabledConfiguration();
	}

	@Override
	public PriceWebServiceConfigurationModel findPriceWsConfiguration(final String id)
	{
		return priceWebServiceConfigurationDao.findPriceWsConfiguration(id);
	}

	@Override
	public Map<String, String> prepareDynamicQueryParameters(final PriceWebServiceConfigurationModel priceConfiguration,
			final ProductModel product, final UserModel user)
	{
		final Map<String, String> params = new HashMap<String, String>();
		for (final PriceWebServiceParameterModel param : priceConfiguration.getParameters())
		{
			if (param.getValue().equals(PriceParameter.UID))
			{
				if (user != null)
				{
					params.put(param.getKey(), user.getUid());
				}
			}
			else
			{
				final String value = callGetter(param.getValue().getCode(), product);
				if (value != null)
				{
					params.put(param.getKey(), value);
				}
			}
		}
		return params;
	}


	@Override
	public Map<String, String> prepareDynamicPathParameters(final PriceWebServiceConfigurationModel priceConfiguration,
			final ProductModel product, final UserModel user)
	{
		final Map<String, String> params = new HashMap<String, String>();
		for (final PriceWebServiceParameterModel param : priceConfiguration.getPathParameters())
		{
			if (param.getValue().equals(PriceParameter.UID))
			{
				if (user != null)
				{
					params.put(param.getKey(), user.getUid());
				}
			}
			else
			{
				final String value = callGetter(param.getValue().getCode(), product);
				if (value != null)
				{
					params.put(param.getKey(), value);
				}
			}
		}
		return params;
	}

	@Override
	public String getResponseCode(final PriceWebServiceConfigurationModel priceConfiguration)
	{
		final Collection<PriceWebServiceResponseModel> mapping = priceConfiguration.getResponseMapping();
		for (final PriceWebServiceResponseModel item : mapping)
		{
			if (item.getValue().equals(PriceMappingResponse.SUCCESSCODE))
			{
				return item.getKey();
			}
		}
		return null;
	}


}
