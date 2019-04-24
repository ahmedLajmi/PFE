/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade;

import de.hybris.platform.addons.wsclientgenerator.data.OrderWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface OrderWebServiceConfigurationFacade
{
	public OrderWebServiceConfigurationData getOrderConfigurationDetails(String id);

	public List<WebServiceConfigurationData> getAllOrderConfigurations();
}
