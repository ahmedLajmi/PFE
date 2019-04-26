/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.impl;

import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.WebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.WebServiceConfigurationService;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;


/**
 * @author Ahmed-LAJMI
 *
 */
public abstract class AbstractWebServiceConfigurationService implements WebServiceConfigurationService
{

	@Override
	public Map<String, String> prepareSecurityParams(final WebServiceConfigurationModel configuration)
	{
		final Collection<PersoWSParamModel> extraSecurityParams = configuration.getSecurityParameters();
		final Map<String, String> response = new HashMap<>();
		for (final PersoWSParamModel param : extraSecurityParams)
		{
			response.put(param.getKey(), param.getValue());
		}
		if (configuration.getLogin() != null && configuration.getPassword() != null)
		{
			final String auth = configuration.getLogin() + ":" + configuration.getPassword();
			final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
			final String authHeader = "Basic " + new String(encodedAuth);
			response.put("Authorization", authHeader);
		}
		return response;
	}

	@Override
	public Map<String, String> preparePersoParams(final WebServiceConfigurationModel configuration)
	{
		final Collection<PersoWSParamModel> extraParams = configuration.getPersonalisedParameters();
		final Map<String, String> response = new HashMap<>();
		for (final PersoWSParamModel param : extraParams)
		{
			response.put(param.getKey(), param.getValue());
		}
		return response;
	}



}
