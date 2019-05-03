/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface CustomerWebServiceConfigurationDao
{
	public List<CustomerWebServiceConfigurationModel> getAllConfigurations();

	public CustomerWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method);

	public CustomerWebServiceConfigurationModel findCustomerWsConfiguration(final String id);

}
