/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface StockWebServiceConfigurationService extends WebServiceConfigurationService
{
	public List<StockWebServiceConfigurationModel> getAllConfigurations();

	public StockWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method);

	public StockWebServiceConfigurationModel findStockWsConfiguration(String id);

}
