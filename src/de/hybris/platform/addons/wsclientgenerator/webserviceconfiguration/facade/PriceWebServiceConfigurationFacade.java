/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade;

import de.hybris.platform.addons.wsclientgenerator.data.PriceWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface PriceWebServiceConfigurationFacade
{
	public PriceWebServiceConfigurationData getPriceConfigurationDetails(String id);

	public List<WebServiceConfigurationData> getAllPriceConfigurations();
}
