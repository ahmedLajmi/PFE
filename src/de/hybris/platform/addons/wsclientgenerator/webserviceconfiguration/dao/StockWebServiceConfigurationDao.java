/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface StockWebServiceConfigurationDao
{
	public List<StockWebServiceConfigurationModel> getAllConfigurations();

	public StockWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method);

	public StockWebServiceConfigurationModel findStockWsConfiguration(String id);

}
