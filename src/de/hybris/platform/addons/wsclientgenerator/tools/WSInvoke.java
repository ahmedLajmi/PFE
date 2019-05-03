/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.tools;

import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



/**
 * @author Ahmed-LAJMI
 *
 */
public class WSInvoke
{

	public Map<String, String> getRequest(final String url, final Map<String, Map<String, String>> params,
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
		for (final String key : headersParam.keySet())
		{
			headers.add(key, headersParam.get(key));
		}

		final HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		final RestTemplate restTemplate = new RestTemplate();
		try
		{
			System.out.println("GET URL :" + prepareUrl(url, params));
			final ResponseEntity<String> response = restTemplate.exchange(prepareUrl(url, params), HttpMethod.GET, entity,
					String.class);
			if (accept.equals(ResponseType.JSON))
			{
				return jsonParseResponse(response.getBody());
			}
			else
			{
				return xmlParseResponse(response.getBody());
			}
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

	public Map<String, String> postRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException
	{

		return postAndput(url, params, headersParam, accept, contentType, HttpMethod.POST);
	}

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
				entityForm = new HttpEntity<>(mapToMultiValue(params.get("body")), headers);
				System.out.println("Body of request: " + entityForm.getBody());
				response = restTemplate.exchange(uri, HttpMethod.POST, entityForm, String.class);
			}
			else
			{
				if (contentType.equals(RequestType.JSON))
				{
					headers.setContentType(MediaType.APPLICATION_JSON);
					entity = new HttpEntity<>(prepareJSONRequest(params.get("body")), headers);
				}
				else if (contentType.equals(RequestType.XML))
				{
					headers.setContentType(MediaType.APPLICATION_XML);
					entity = new HttpEntity<>(prepareXMLRequest(params.get("body")), headers);
				}
				System.out.println("Body of request: " + entity.getBody());
				response = restTemplate.exchange(uri, method, entity, String.class);
			}
			if (accept.equals(ResponseType.JSON))
			{
				return jsonParseResponse(response.getBody());
			}
			else
			{
				return xmlParseResponse(response.getBody());
			}
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

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

	public ResponseEntity<String> getSimulationRequest(final String url, final Map<String, Map<String, String>> params,
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
			return response;
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

	public ResponseEntity<String> postSimulationRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException
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
				entityForm = new HttpEntity<>(mapToMultiValue(params.get("body")), headers);
				System.out.println("Body of request: " + entityForm.getBody());
				response = restTemplate.exchange(uri, HttpMethod.POST, entityForm, String.class);
			}
			else
			{
				if (contentType.equals(RequestType.JSON))
				{
					headers.setContentType(MediaType.APPLICATION_JSON);
					entity = new HttpEntity<>(prepareJSONRequest(params.get("body")), headers);
				}
				else if (contentType.equals(RequestType.XML))
				{
					headers.setContentType(MediaType.APPLICATION_XML);
					entity = new HttpEntity<>(prepareXMLRequest(params.get("body")), headers);
				}
				System.out.println("Body of request: " + entity.getBody());
				response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			}
			return response;
		}
		catch (final Exception e)
		{
			throw new InvokeWsException("Error in invoking web service!! " + e.getMessage());
		}
	}

	public final MultiValueMap<String, String> mapToMultiValue(final Map<String, String> map)
	{
		MultiValueMap<String, String> result = null;
		if (map != null)
		{
			result = new LinkedMultiValueMap<>();
			for (final String key : map.keySet())
			{
				result.add(key, map.get(key));
			}
		}
		return result;
	}

	public String prepareJSONRequest(final Map<String, String> body) throws CreateWsRequestException
	{
		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode rootNode = mapper.createObjectNode();
		body.remove("root");
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

	public String prepareXMLRequest(final Map<String, String> body) throws CreateWsRequestException
	{
		final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		final TransformerFactory tf = TransformerFactory.newInstance();
		final StringWriter writer = new StringWriter();
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

	public Map<String, String> jsonParseResponse(final String response) throws ParseWsResponseException
	{
		Map<String, String> result = new HashMap<>();
		final ObjectMapper mapper = new ObjectMapper();
		try
		{
			result = mapper.readValue(response, HashMap.class);
		}
		catch (final IOException e)
		{
			throw new ParseWsResponseException("Problem in reading JSON response \n" + e.getMessage());
		}
		return result;
	}

	public Map<String, String> xmlParseResponse(String response) throws ParseWsResponseException
	{
		Document doc;
		final Map<String, String> map = new HashMap<>();
		response = StringUtils.replaceEach(response, new String[]
		{ "\n", "\r", "\t" }, new String[]
		{ "", "", "" });
		try
		{
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
			doc.getDocumentElement().normalize();
			final NodeList nodeList = doc.getDocumentElement().getChildNodes();
			if (doc.getDocumentElement().hasChildNodes() && doc.getDocumentElement().getFirstChild().getNodeType() == Node.TEXT_NODE)
			{
				final Node node = doc.getDocumentElement().getFirstChild();
				map.put(doc.getDocumentElement().getTagName(), node.getTextContent());
			}
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				final Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					map.put(node.getNodeName(), node.getTextContent());
				}
			}
			return map;
		}
		catch (SAXException | ParserConfigurationException | IOException e)
		{
			throw new ParseWsResponseException("Problem in reading XML response \n" + e.getMessage());
		}
	}



	public String prepareUrl(final String url, final Map<String, Map<String, String>> params)
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
