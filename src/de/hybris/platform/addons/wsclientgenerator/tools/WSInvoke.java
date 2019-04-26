/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.tools;

import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * @author Ahmed-LAJMI
 *
 */
public class WSInvoke
{

	public ResponseEntity<String> getRequest(final String url, final Map<String, String> params,
			final Map<String, String> headersParam, final ResponseType accept) throws InvokeWsException
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
		for (final String key : headersParam.keySet())
		{
			headers.add(key, headersParam.get(key));
		}

		final HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		final RestTemplate restTemplate = new RestTemplate();
		try
		{
			final ResponseEntity<String> response = restTemplate.exchange(prepareUrl(url, params), HttpMethod.GET, entity,
					String.class);
			System.out.println(prepareUrl(url, params));
			return response;
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}

	}

	public ResponseEntity<String> getRequest(final String url, final Map<String, String> params, final ResponseType accept)
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
			final ResponseEntity<String> response = restTemplate.exchange(prepareUrl(url, params), HttpMethod.GET, entity,
					String.class);
			System.out.println(prepareUrl(url, params));
			return response;
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}

	}



	public ResponseEntity<String> postRequest(final String url, final MultiValueMap<String, String> body,
			final ResponseType accept, final RequestType contentType) throws InvokeWsException, CreateWsRequestException
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
		else if (accept.equals(ResponseType.TEXT))
		{
			headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
		}
		try
		{

			if (contentType.equals(RequestType.FORM))
			{
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				entityForm = new HttpEntity<>(body, headers);
				response = restTemplate.exchange(url, HttpMethod.POST, entityForm, String.class);
				System.out.println("Body of request: " + entityForm.getBody());
			}
			else
			{
				if (contentType.equals(RequestType.JSON))
				{
					headers.setContentType(MediaType.APPLICATION_JSON);
					entity = new HttpEntity<>(prepareJSONRequest(body), headers);
				}
				else if (contentType.equals(RequestType.XML))
				{
					headers.setContentType(MediaType.APPLICATION_XML);
					entity = new HttpEntity<>(prepareXMLRequest(body), headers);
				}
				response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
				System.out.println("Body of request: " + entity.getBody());
			}
			return response;
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

	public String prepareJSONRequest(final MultiValueMap<String, String> requestBody) throws CreateWsRequestException
	{

		final ObjectMapper mapper = new ObjectMapper();
		final Map<String, String> body = requestBody.toSingleValueMap();
		final ObjectNode rootNode = mapper.createObjectNode();
		for (final String key : body.keySet())
		{
			rootNode.put(key, body.get(key));
		}
		try
		{
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
		}
		catch (final IOException e)
		{
			throw new CreateWsRequestException("Error in creating JSON request! " + e.getMessage());
		}
	}

	public String prepareXMLRequest(final MultiValueMap<String, String> requestBody) throws CreateWsRequestException
	{
		final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		final TransformerFactory tf = TransformerFactory.newInstance();
		final StringWriter writer = new StringWriter();
		final Map<String, String> body = requestBody.toSingleValueMap();
		try
		{
			final DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			final Document document = documentBuilder.newDocument();
			final Transformer transformer = tf.newTransformer();
			Element root = null;
			if (body.containsKey("root"))
			{
				root = document.createElement(body.get("root"));
				body.remove("root");
			}
			else
			{
				throw new CreateWsRequestException("Error in creating XML request! No root key specified ");
			}
			for (final String key : body.keySet())
			{
				final Element elem = document.createElement(key);
				elem.appendChild(document.createTextNode(body.get(key)));
				root.appendChild(elem);
			}
			transformer.transform(new DOMSource(root), new StreamResult(writer));
			return writer.getBuffer().toString();
		}
		catch (ParserConfigurationException | TransformerException e)
		{
			throw new CreateWsRequestException("Error in creating XML request! " + e.getMessage());
		}
	}

	public String prepareUrl(final String url, final Map<String, String> params)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

		for (final String key : params.keySet())
		{
			uriBuilder.queryParam(key, params.get(key));
		}
		return uriBuilder.toUriString();
	}
}
