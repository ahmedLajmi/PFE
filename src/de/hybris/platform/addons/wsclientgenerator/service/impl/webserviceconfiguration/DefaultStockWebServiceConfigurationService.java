/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.StockWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.StockParameter;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.StockWebServiceConfigurationService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultStockWebServiceConfigurationService extends AbstractWebServiceConfigurationService
		implements StockWebServiceConfigurationService
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "stockWebServiceConfigurationDao")
	private StockWebServiceConfigurationDao stockWebServiceConfigurationDao;

	@Override
	public List<StockWebServiceConfigurationModel> getAllConfigurations()
	{
		return stockWebServiceConfigurationDao.getAllConfigurations();
	}

	@Override
	public StockWebServiceConfigurationModel findStockWsConfiguration(final String id)
	{
		return stockWebServiceConfigurationDao.findStockWsConfiguration(id);
	}

	@Override
	public StockWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method)
	{
		return stockWebServiceConfigurationDao.getWsEnabledConfiguration(method);
	}


	@Override
	public Map<String, String> prepareDynamicQueryParameters(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product, final UserModel user)
	{
		final Map<String, String> params = new HashMap<String, String>();
		for (final StockWebServiceParameterModel param : stockConfiguration.getParameters())
		{
			if (param.getValue().equals(StockParameter.UID))
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
	public Map<String, String> prepareDynamicQueryParameters(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product, final UserModel user, final String orderCode, final String quantity)
	{
		final Map<String, String> params = new HashMap<String, String>();
		for (final StockWebServiceParameterModel param : stockConfiguration.getParameters())
		{
			if (param.getValue().equals(StockParameter.UID))
			{
				if (user != null)
				{
					params.put(param.getKey(), user.getUid());
				}
			}
			else if (param.getValue().equals(StockParameter.ORDERCODE))
			{
				params.put(param.getKey(), orderCode);
			}
			else if (param.getValue().equals(StockParameter.STOCKVALUE))
			{
				params.put(param.getKey(), quantity);
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
	public Map<String, String> prepareDynamicPathParameters(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product, final UserModel user)
	{
		final Map<String, String> params = new HashMap<String, String>();
		for (final StockWebServiceParameterModel param : stockConfiguration.getParameters())
		{
			if (param.getValue().equals(StockParameter.UID))
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
	public Map<String, String> prepareDynamicPathParameters(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product, final UserModel user, final String orderCode)
	{
		final Map<String, String> params = new HashMap<String, String>();
		for (final StockWebServiceParameterModel param : stockConfiguration.getParameters())
		{
			if (param.getValue().equals(StockParameter.UID))
			{
				if (user != null)
				{
					params.put(param.getKey(), user.getUid());
				}
			}
			else if (param.getValue().equals(StockParameter.ORDERCODE))
			{
				params.put(param.getKey(), orderCode);
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


}
