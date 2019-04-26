/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service;

import de.hybris.platform.addons.wsclientgenerator.model.WebServiceConfigurationModel;

import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WebServiceConfigurationService
{
	public Map<String, String> prepareSecurityParams(final WebServiceConfigurationModel configuration);

	public Map<String, String> preparePersoParams(final WebServiceConfigurationModel configuration);

}