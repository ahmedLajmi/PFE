/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface CustomerWebServiceConfigurationService extends WebServiceConfigurationService
{
	public List<CustomerWebServiceConfigurationModel> getAllConfigurations();

	public CustomerWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method);

	public CustomerWebServiceConfigurationModel findCustomerWsConfiguration(final String id);

}
