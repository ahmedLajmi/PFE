/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.tools;

import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



/**
 * @author Ahmed-LAJMI
 *
 */
public class WSInvoke
{

	public ResponseEntity<String> get(final String url, final ResponseType accept) throws InvokeWsException
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
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}

	}

	public ResponseEntity<String> postRequest(final String url, final String body, final ResponseType accept,
			final RequestType contentType) throws InvokeWsException
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
		if (contentType.equals(RequestType.JSON))
		{
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		else if (contentType.equals(RequestType.XML))
		{
			headers.setContentType(MediaType.APPLICATION_XML);
		}

		final HttpEntity<String> entity = new HttpEntity<>(body, headers);
		final RestTemplate restTemplate = new RestTemplate();
		try
		{
			final ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			System.out.println("Body of request: " + entity.getBody());
			return response;
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}


	public ResponseEntity<String> postRequest(final String url, final MultiValueMap<String, String> body,
			final ResponseType accept) throws InvokeWsException
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

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


		final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
		final RestTemplate restTemplate = new RestTemplate();
		try
		{
			final ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			System.out.println("Body of request: " + entity.getBody());
			return response;
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}
}
