/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao;

import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface PriceWebServiceConfigurationDao
{
	public List<PriceWebServiceConfigurationModel> getAllConfigurations();

	public PriceWebServiceConfigurationModel getWsEnabledConfiguration();

	public PriceWebServiceConfigurationModel findPriceWsConfiguration(String id);
}
