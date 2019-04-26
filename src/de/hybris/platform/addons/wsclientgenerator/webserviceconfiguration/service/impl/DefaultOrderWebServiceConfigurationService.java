/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.OrderWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.OrderWebServiceConfigurationService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

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
}
