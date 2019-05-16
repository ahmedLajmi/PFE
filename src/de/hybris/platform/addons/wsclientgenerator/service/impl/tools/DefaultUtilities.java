/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.tools;

import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.service.tools.Utilities;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
public class DefaultUtilities implements Utilities
{
	@Override
	public MultiValueMap<String, String> mapToMultiValue(final Map<String, String> map)
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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
					map.put(node.getNodeName(), node.getTextContent().toString());
				}
			}
			return map;
		}
		catch (SAXException | ParserConfigurationException | IOException e)
		{
			throw new ParseWsResponseException("Problem in reading XML response \n" + e.getMessage());
		}
	}

}
