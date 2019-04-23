/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service;

import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface PriceWebServiceConfigurationService
{
	public List<PriceWebServiceConfigurationModel> getAllConfigurations();

	public PriceWebServiceConfigurationModel getWsEnabledConfiguration();

	public PriceWebServiceConfigurationModel findPriceWsConfiguration(String id);
}
