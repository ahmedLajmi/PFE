/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.impl;

import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.WebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.WebServiceConfigurationService;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	public Map<String, String> prepareHeadersParams(final WebServiceConfigurationModel configuration)
	{
		final Collection<PersoWSParamModel> headersParams = configuration.getHeadersParameters();
		final Map<String, String> response = new HashMap<>();
		for (final PersoWSParamModel param : headersParams)
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
	public Map<String, String> prepareStaticParams(final WebServiceConfigurationModel configuration)
	{
		final Collection<PersoWSParamModel> extraParams = configuration.getPersonalisedParameters();
		final Map<String, String> response = new HashMap<>();
		for (final PersoWSParamModel param : extraParams)
		{
			response.put(param.getKey(), param.getValue());
		}
		return response;
	}

	protected String callGetter(final String field, final Object obj)
	{
		PropertyDescriptor pd;
		Method getter;

		try
		{
			pd = new PropertyDescriptor(field, obj.getClass());
			getter = pd.getReadMethod();
			return String.valueOf(getter.invoke(obj));
		}
		catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			return null;
		}
	}

	protected String callSetter(final String field, final Object obj, final Object value)
	{
		PropertyDescriptor pd;
		Method setter;

		try
		{
			pd = new PropertyDescriptor(field, obj.getClass());
			setter = pd.getWriteMethod();
			return (String) setter.invoke(obj, value);
		}
		catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			return null;
		}
	}


}
