/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade;

import de.hybris.platform.addons.wsclientgenerator.data.StockWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface StockWebServiceConfigurationFacade
{
	public StockWebServiceConfigurationData getStockConfigurationDetails(String id);

	public List<WebServiceConfigurationData> getAllStockConfigurations();
}
