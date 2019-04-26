/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.CustomerWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.CustomerWebServiceConfigurationService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultCustomerWebServiceConfigurationService extends AbstractWebServiceConfigurationService
		implements CustomerWebServiceConfigurationService
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "customerWebServiceConfigurationDao")
	private CustomerWebServiceConfigurationDao customerWebServiceConfigurationDao;

	@Override
	public List<CustomerWebServiceConfigurationModel> getAllConfigurations()
	{
		return customerWebServiceConfigurationDao.getAllConfigurations();
	}

	@Override
	public CustomerWebServiceConfigurationModel findCustomerWsConfiguration(final String id)
	{
		return customerWebServiceConfigurationDao.findCustomerWsConfiguration(id);
	}

	@Override
	public CustomerWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method)
	{
		return customerWebServiceConfigurationDao.getWsEnabledConfiguration(method);
	}

}
