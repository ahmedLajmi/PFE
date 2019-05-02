/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationDetailsData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceResponseData;

import java.util.List;
import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface PriceWebServiceConfigurationFacade
{
	public WebServiceConfigurationDetailsData getConfigurationDetails(String id);

	public List<WebServiceConfigurationData> getAllConfigurations();

	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> queryParams,
			final Map<String, String> pathParams);

}
