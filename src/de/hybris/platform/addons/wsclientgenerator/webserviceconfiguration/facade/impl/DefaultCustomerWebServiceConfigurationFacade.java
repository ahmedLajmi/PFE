/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.impl;

import de.hybris.platform.addons.wsclientgenerator.data.CustomerWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.CustomerWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.CustomerWebServiceConfigurationService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultCustomerWebServiceConfigurationFacade implements CustomerWebServiceConfigurationFacade
{

	@Resource(name = "customerWebServiceConfigurationService")
	private CustomerWebServiceConfigurationService customerWebServiceConfigurationService;

	@Override
	public List<CustomerWebServiceConfigurationData> getCustomerConfigurations()
	{
		final List<CustomerWebServiceConfigurationModel> customerConfigModels = customerWebServiceConfigurationService
				.getAllConfigurations();
		final List<CustomerWebServiceConfigurationData> CustomerWsConfigData = new ArrayList<CustomerWebServiceConfigurationData>();
		for (final CustomerWebServiceConfigurationModel cm : customerConfigModels)
		{
			final CustomerWebServiceConfigurationData cd = new CustomerWebServiceConfigurationData();
			cd.setName(cm.getName());
			cd.setUrl(cm.getUrl());
			cd.setDescription(cm.getDescription());
			cd.setMethod(cm.getMethod().toString());
			cd.setEnable(cm.getEnable().booleanValue());

			CustomerWsConfigData.add(cd);
		}
		return CustomerWsConfigData;
	}

}
