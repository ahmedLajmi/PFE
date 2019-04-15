/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.tools;

import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;



/**
 * @author Ahmed-LAJMI
 *
 */
public class WSInvoke
{

	public ResponseEntity<String> get(final String url, final String parameters, final ResponseType accept)
			throws InvokeWsException
	{
		final HttpHeaders headers = new HttpHeaders();
		if (accept.equals(ResponseType.JSON))
		{
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		}
		else if (accept.equals(ResponseType.XML))
		{
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		}
		else if (accept.equals(ResponseType.TEXT))
		{
			headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
		}
		final HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		final RestTemplate restTemplate = new RestTemplate();
		try
		{
			final ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			return response;
		}
		catch (final RestClientException e)
		{
			throw new InvokeWsException();
		}

	}

	public ResponseEntity<String> postRequest(final String url, final String parameters, final ResponseType accept)
			throws InvokeWsException
	{
		final HttpHeaders headers = new HttpHeaders();
		if (accept.equals(ResponseType.JSON))
		{
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		}
		else if (accept.equals(ResponseType.XML))
		{
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		}
		else if (accept.equals(ResponseType.TEXT))
		{
			headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
		}
		final HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		final RestTemplate restTemplate = new RestTemplate();
		try
		{
			final ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			return response;
		}
		catch (final RestClientException e)
		{
			throw new InvokeWsException();
		}

	}

}
