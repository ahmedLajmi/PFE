/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade;

import de.hybris.platform.addons.wsclientgenerator.data.CustomerWebServiceConfigurationData;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface CustomerWebServiceConfigurationFacade
{
	public List<CustomerWebServiceConfigurationData> getCustomerConfigurations();
}
