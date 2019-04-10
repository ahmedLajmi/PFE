/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.tools;

import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;



/**
 * @author Ahmed-LAJMI
 *
 */
public class WSInvoke
{

	public ResponseEntity<String> get(final String url, final String parameters, final ResponseType accept)
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
		/*
		 * HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		 *
		 * restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		 */
		final HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		final RestTemplate restTemplate = new RestTemplate();
		final ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return response;
	}

}
