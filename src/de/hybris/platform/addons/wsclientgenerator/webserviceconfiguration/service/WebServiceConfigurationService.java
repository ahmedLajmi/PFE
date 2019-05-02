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
	public Map<String, String> prepareHeadersParams(WebServiceConfigurationModel configuration);

	public Map<String, String> prepareStaticParams(WebServiceConfigurationModel configuration);

}
