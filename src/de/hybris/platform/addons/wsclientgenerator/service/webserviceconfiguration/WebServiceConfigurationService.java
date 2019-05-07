/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration;

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

	public void saveCall(final WebServiceConfigurationModel configuration, final String requestBody, final String responseBody,
			final String responseCode, final String description);
}
