/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.OrderWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.OrderMappingResponse;
import de.hybris.platform.addons.wsclientgenerator.enums.OrderParameter;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceResponseModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.OrderWebServiceConfigurationService;
import de.hybris.platform.core.model.order.OrderModel;
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
public class DefaultOrderWebServiceConfigurationService extends AbstractWebServiceConfigurationService
		implements OrderWebServiceConfigurationService
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "orderWebServiceConfigurationDao")
	private OrderWebServiceConfigurationDao orderWebServiceConfigurationDao;

	@Override
	public List<OrderWebServiceConfigurationModel> getAllConfigurations()
	{
		return orderWebServiceConfigurationDao.getAllConfigurations();
	}

	@Override
	public OrderWebServiceConfigurationModel findOrderWsConfiguration(final String id)

	{
		return orderWebServiceConfigurationDao.findOrderWsConfiguration(id);
	}

	@Override
	public OrderWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method)
	{
		return orderWebServiceConfigurationDao.getWsEnabledConfiguration(method);
	}

	@Override
	public Map<String, String> prepareDynamicQueryParameters(final OrderWebServiceConfigurationModel orderConfiguration,
			final OrderModel order, final UserModel user)
	{
		final Map<String, String> params = new HashMap<String, String>();

		for (final OrderWebServiceParameterModel param : orderConfiguration.getParameters())
		{
			if (param.getValue().equals(OrderParameter.UID))
			{
				if (user != null)
				{
					params.put(param.getKey(), user.getUid());
				}
			}
			else
			{
				final String value = callGetter(param.getValue().getCode(), order);
				if (value != null)
				{
					params.put(param.getKey(), value);
				}
			}
		}

		return params;
	}

	@Override
	public Map<String, String> prepareDynamicPathParameters(final OrderWebServiceConfigurationModel orderConfiguration,
			final OrderModel order, final UserModel user)
	{
		final Map<String, String> params = new HashMap<String, String>();

		for (final OrderWebServiceParameterModel param : orderConfiguration.getParameters())
		{
			if (param.getValue().equals(OrderParameter.UID))
			{
				if (user != null)
				{
					params.put(param.getKey(), user.getUid());
				}
			}
			else
			{
				final String value = callGetter(param.getValue().getCode(), order);
				if (value != null)
				{
					params.put(param.getKey(), value);
				}
			}
		}

		return params;
	}

	@Override
	public String getResponseCode(final OrderWebServiceConfigurationModel orderConfiguration)
	{
		final Collection<OrderWebServiceResponseModel> mapping = orderConfiguration.getResponseMapping();
		for (final OrderWebServiceResponseModel item : mapping)
		{
			if (item.getValue().equals(OrderMappingResponse.SUCCESSCODE))
			{
				return item.getKey();
			}
		}
		return null;
	}
}
