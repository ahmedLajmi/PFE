/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.impl;

import de.hybris.platform.addons.wsclientgenerator.data.CustomerWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceParameterData;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceParameterModel;
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
	public CustomerWebServiceConfigurationData getCustomerConfigurationDetails(final String id)
	{
		final CustomerWebServiceConfigurationModel cm = customerWebServiceConfigurationService.findCustomerWsConfiguration(id);
		final CustomerWebServiceConfigurationData cd = new CustomerWebServiceConfigurationData();
		cd.setId(cm.getPk().toString());
		cd.setName(cm.getName());
		cd.setUrl(cm.getUrl());
		cd.setDescription(cm.getDescription());
		cd.setMethod(cm.getMethod().toString());
		cd.setEnable(cm.getEnable().booleanValue());
		final List<WebServiceParameterData> params = new ArrayList<>();
		for (final CustomerWebServiceParameterModel param : cm.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			params.add(pd);
		}
		cd.setPersoData(params);
		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllCustomerConfigurations()
	{
		final List<CustomerWebServiceConfigurationModel> customerConfigModels = customerWebServiceConfigurationService
				.getAllConfigurations();
		final List<WebServiceConfigurationData> customerWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final CustomerWebServiceConfigurationModel cm : customerConfigModels)
		{
			final WebServiceConfigurationData cd = new WebServiceConfigurationData();
			cd.setId(cm.getPk().toString());
			cd.setName(cm.getName());
			customerWsConfigData.add(cd);
		}
		return customerWsConfigData;
	}

}
