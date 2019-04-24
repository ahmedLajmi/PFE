/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade;

import de.hybris.platform.addons.wsclientgenerator.data.CustomerWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface CustomerWebServiceConfigurationFacade
{
	public CustomerWebServiceConfigurationData getCustomerConfigurationDetails(String id);

	public List<WebServiceConfigurationData> getAllCustomerConfigurations();
}
