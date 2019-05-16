/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.tools;

import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.service.tools.Utilities;
import de.hybris.platform.addons.wsclientgenerator.service.tools.WsInvokeService;

import java.util.Collections;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;



/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultWsInvokeService implements WsInvokeService
{

	@Resource(name = "utilities")
	Utilities utils;

	@Override
	public Map<String, String> getRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept) throws InvokeWsException
	{
		final RestTemplate restTemplate = new RestTemplate();
		final HttpEntity<String> entity = prepareGet(accept, headersParam);
		try
		{
			System.out.println("GET URL :" + prepareUrl(url, params));
			final ResponseEntity<String> response = restTemplate.exchange(prepareUrl(url, params), HttpMethod.GET, entity,
					String.class);
			//if (response.getStatusCode().equals(HttpStatus.OK)) {
			if (accept.equals(ResponseType.JSON))
			{
				return utils.jsonParseResponse(response.getBody());
			}
			else
			{
				return utils.xmlParseResponse(response.getBody());
			}
			//}
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

	@Override
	public Map<String, String> postRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException
	{

		return postAndput(url, params, headersParam, accept, contentType, HttpMethod.POST);
	}

	@Override
	public Map<String, String> putRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException
	{

		return postAndput(url, params, headersParam, accept, contentType, HttpMethod.PUT);
	}

	private Map<String, String> postAndput(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType,
			final HttpMethod method) throws InvokeWsException, CreateWsRequestException
	{
		final HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = null;
		HttpEntity<MultiValueMap<String, String>> entityForm;
		ResponseEntity<String> response = null;
		final RestTemplate restTemplate = new RestTemplate();

		if (accept.equals(ResponseType.JSON))
		{
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		}
		else if (accept.equals(ResponseType.XML))
		{
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		}
		for (final String key : headersParam.keySet())
		{
			headers.add(key, headersParam.get(key));
		}
		try
		{
			final String uri = prepareUrl(url, params);
			System.out.println("POST URL :" + prepareUrl(url, params));
			if (contentType.equals(RequestType.FORM))
			{
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				entityForm = new HttpEntity<>(utils.mapToMultiValue(params.get("body")), headers);
				System.out.println("Body of request: " + entityForm.getBody());
				response = restTemplate.exchange(uri, HttpMethod.POST, entityForm, String.class);
			}
			else
			{
				if (contentType.equals(RequestType.JSON))
				{
					headers.setContentType(MediaType.APPLICATION_JSON);
					entity = new HttpEntity<>(utils.prepareJSONRequest(params.get("body")), headers);
				}
				else if (contentType.equals(RequestType.XML))
				{
					headers.setContentType(MediaType.APPLICATION_XML);
					entity = new HttpEntity<>(utils.prepareXMLRequest(params.get("body")), headers);
				}
				System.out.println("Body of request: " + entity.getBody());
				response = restTemplate.exchange(uri, method, entity, String.class);
			}
			if (accept.equals(ResponseType.JSON))
			{
				return utils.jsonParseResponse(response.getBody());
			}
			else
			{
				return utils.xmlParseResponse(response.getBody());
			}
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

	@Override
	public void deleteRequest(final String url, final Map<String, Map<String, String>> params) throws InvokeWsException
	{
		final RestTemplate restTemplate = new RestTemplate();
		try
		{
			System.out.println("DELETE URL :" + prepareUrl(url, params));
			restTemplate.delete(prepareUrl(url, params));
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> getSimulationRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept) throws InvokeWsException
	{
		final HttpEntity<String> entity = prepareGet(accept, headersParam);
		final RestTemplate restTemplate = new RestTemplate();
		try
		{
			final ResponseEntity<String> response = restTemplate.exchange(prepareUrl(url, params), HttpMethod.GET, entity,
					String.class);
			return response;
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> postSimulationRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException
	{
		return postAndPutSimulationRequest(url, params, headersParam, accept, contentType, HttpMethod.POST);
	}

	@Override
	public ResponseEntity<String> putSimulationRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException
	{
		return postAndPutSimulationRequest(url, params, headersParam, accept, contentType, HttpMethod.PUT);
	}

	public ResponseEntity<String> postAndPutSimulationRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType,
			final HttpMethod method) throws InvokeWsException, CreateWsRequestException
	{
		final HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = null;
		HttpEntity<MultiValueMap<String, String>> entityForm;
		ResponseEntity<String> response = null;
		final RestTemplate restTemplate = new RestTemplate();

		if (accept.equals(ResponseType.JSON))
		{
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		}
		else if (accept.equals(ResponseType.XML))
		{
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		}
		for (final String key : headersParam.keySet())
		{
			headers.add(key, headersParam.get(key));
		}
		try
		{
			final String uri = prepareUrl(url, params);
			System.out.println("POST URL :" + prepareUrl(url, params));
			if (contentType.equals(RequestType.FORM))
			{
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				entityForm = new HttpEntity<>(utils.mapToMultiValue(params.get("body")), headers);
				System.out.println("Body of request: " + entityForm.getBody());
				response = restTemplate.exchange(uri, HttpMethod.POST, entityForm, String.class);
			}
			else
			{
				if (contentType.equals(RequestType.JSON))
				{
					headers.setContentType(MediaType.APPLICATION_JSON);
					entity = new HttpEntity<>(utils.prepareJSONRequest(params.get("body")), headers);
				}
				else if (contentType.equals(RequestType.XML))
				{
					headers.setContentType(MediaType.APPLICATION_XML);
					entity = new HttpEntity<>(utils.prepareXMLRequest(params.get("body")), headers);
				}
				System.out.println("Body of request: " + entity.getBody());
				response = restTemplate.exchange(uri, method, entity, String.class);
			}
			return response;
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

	private HttpEntity<String> prepareGet(final ResponseType accept, final Map<String, String> headersParam)
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
		for (final String key : headersParam.keySet())
		{
			headers.add(key, headersParam.get(key));
		}

		return new HttpEntity<>("parameters", headers);
	}

	private String prepareUrl(final String url, final Map<String, Map<String, String>> params)
	{
		final Map<String, String> pathPrameters = params.get("pathPrameters");
		final Map<String, String> queryPrameters = params.get("queryPrameters");
		String newUrl = url;
		if (pathPrameters != null)
		{
			for (final String key : pathPrameters.keySet())
			{
				newUrl = StringUtils.replace(newUrl, "{" + key + "}", pathPrameters.get(key));
			}
		}
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(newUrl);
		if (queryPrameters != null)
		{
			for (final String key : queryPrameters.keySet())
			{
				uriBuilder.queryParam(key, queryPrameters.get(key));
			}
		}
		return uriBuilder.toUriString();
	}
}
