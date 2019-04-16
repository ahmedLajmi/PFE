/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.customer.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.CustomerParameter;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.OrderType;
import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.CustomerWebServiceConfigurationDao;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.ResponseEntity;
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
public class WSCustomerFacade extends DefaultCustomerFacade
{
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "customerWebServiceConfigurationDao")
	private CustomerWebServiceConfigurationDao customerWebServiceConfigurationDao;

	private CustomerWebServiceConfigurationModel customerConfiguration;

	private static final Logger LOG = Logger.getLogger(WSCustomerFacade.class);

	@Override
	public CustomerData getCurrentCustomer()
	{
		final UserModel user = userService.getCurrentUser();
		final CustomerData customer = getCustomerConverter().convert(user);

		customerConfiguration = customerWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.GET);
		if (customerConfiguration != null)
		{
			Map<String, String> result = new HashMap<>();
			final WSInvoke wsinvoke = new WSInvoke();
			try
			{
				final ResponseEntity<String> response = wsinvoke.get(prepareUrl(customerConfiguration, user),
						customerConfiguration.getAccept());
				if (customerConfiguration.getAccept().equals(ResponseType.JSON))
				{
					result = jsonParseResponse(response.getBody());
				}
				else if (customerConfiguration.getAccept().equals(ResponseType.XML))
				{
					result = xmlParseResponse(response.getBody());
				}
				customer.setFirstName(result.get("firstName"));
				customer.setLastName(result.get("lastName"));
				customer.setDisplayUid(result.get("mail"));
			}
			catch (final ParseWsResponseException | InvokeWsException e)
			{
				LOG.error(e.getMessage());
				if (customerConfiguration.getMode().equals(ModeType.WEBSERVICEWITHNATIVE))
				{
					return customer;
				}
				else
				{
					customer.setFirstName("");
					customer.setLastName("");
					customer.setDisplayUid("");
					return customer;
				}
			}
		}
		return customer;
	}

	@Override
	public void changeUid(final String newUid, final String currentPassword)
			throws DuplicateUidException, PasswordMismatchException
	{
		try
		{
			getCustomerAccountService().changeUid(newUid, currentPassword);
		}
		catch (final de.hybris.platform.commerceservices.customer.PasswordMismatchException pse)
		{
			throw new PasswordMismatchException(pse);
		}

		customerConfiguration = customerWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.POST);
		if (customerConfiguration != null)
		{
			super.changeUid(newUid, currentPassword);
			wsUpdateEmail(customerConfiguration, newUid);
		}
		else
		{
			super.changeUid(newUid, currentPassword);
		}
	}

	@Override
	public void updateProfile(final CustomerData customerData) throws DuplicateUidException
	{
		validateDataBeforeUpdate(customerData);
		customerConfiguration = customerWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.POST);
		if (customerConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
		{
			wsUpdateProfil(customerConfiguration, customerData);
		}
		else if (customerConfiguration.getMode().equals(ModeType.WEBSERVICEWITHNATIVE))
		{
			if (customerConfiguration.getOrder().equals(OrderType.NATIVEWEBSERVICE))
			{
				super.updateProfile(customerData);
				wsUpdateProfil(customerConfiguration, customerData);
			}
			else if (customerConfiguration.getOrder().equals(OrderType.WEBSERVICENATIVE))
			{
				wsUpdateProfil(customerConfiguration, customerData);
				super.updateProfile(customerData);
			}
		}
	}


	private Map<String, String> jsonParseResponse(final String response) throws ParseWsResponseException
	{
		final Map<String, String> result = new HashMap<>();
		final ObjectMapper mapper = new ObjectMapper();
		final String firstName, lastName, mail;
		try
		{
			final JsonNode root = mapper.readTree(response);
			if (root.has(customerConfiguration.getFirstNameKey()) && root.has(customerConfiguration.getLastNameKey())
					&& root.has(customerConfiguration.getEmailKey()))
			{
				firstName = root.path(customerConfiguration.getFirstNameKey()).asText();
				lastName = root.path(customerConfiguration.getLastNameKey()).asText();
				mail = root.path(customerConfiguration.getEmailKey()).asText();
			}
			else
			{
				throw new ParseWsResponseException("Error in parsing response!! Key doesn t exist");
			}
		}
		catch (final IOException e)
		{
			throw new ParseWsResponseException("Problem in reading JSON response \n" + e.getMessage());
		}
		result.put("firstName", firstName);
		result.put("lastName", lastName);
		result.put("mail", mail);
		return result;
	}

	private Map<String, String> xmlParseResponse(final String response) throws ParseWsResponseException
	{
		final Map<String, String> result = new HashMap<>();
		String firstName = "", lastName = "", mail = "";
		Document doc;
		try
		{
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response)));
			final NodeList nodeList = doc.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				final Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					final Element elem = (Element) node;

					if (StringUtils.equals(customerConfiguration.getFirstNameKey(), elem.getTagName()))
					{
						firstName = elem.getFirstChild().getNodeValue();
					}
					else if (StringUtils.equals(customerConfiguration.getLastNameKey(), elem.getTagName()))
					{
						lastName = elem.getFirstChild().getNodeValue();
					}
					else if (StringUtils.equals(customerConfiguration.getEmailKey(), elem.getTagName()))
					{
						mail = elem.getFirstChild().getNodeValue();
					}
				}
			}
			if (firstName.isEmpty() || lastName.isEmpty() || mail.isEmpty())
			{
				throw new ParseWsResponseException("Error in parsing response!! Key doesn t exist");
			}
			else
			{
				result.put("firstName", firstName);
				result.put("lastName", lastName);
				result.put("mail", mail);
				return result;
			}
		}
		catch (SAXException | ParserConfigurationException | IOException e)
		{
			throw new ParseWsResponseException("Problem in reading XML response \n" + e.getMessage());
		}
	}

	private void wsUpdateEmail(final CustomerWebServiceConfigurationModel customerConfiguration, final String newUid)
	{
		final WSInvoke wsinvoke = new WSInvoke();
		ResponseEntity<String> response;
		try
		{
			if (customerConfiguration.getContentType().equals(RequestType.JSON))
			{
				response = wsinvoke.postRequest(customerConfiguration.getUrl(),
						prepareJSONUpdateEmailRequest(newUid, customerConfiguration), customerConfiguration.getAccept(),
						customerConfiguration.getContentType());
				System.out.println(response);

			}
			else if (customerConfiguration.getContentType().equals(RequestType.XML))
			{
				//
			}
			else if (customerConfiguration.getContentType().equals(RequestType.FORM))
			{
				//
			}
		}
		catch (final CreateWsRequestException | InvokeWsException e)
		{
			LOG.error(e.getMessage());
		}
	}

	private void wsUpdateProfil(final CustomerWebServiceConfigurationModel customerConfiguration, final CustomerData customerData)
	{
		final WSInvoke wsinvoke = new WSInvoke();
		ResponseEntity<String> response;
		try
		{
			if (customerConfiguration.getContentType().equals(RequestType.JSON))
			{
				response = wsinvoke.postRequest(customerConfiguration.getUrl(),
						prepareJSONUpdateProfilRequest(customerData, customerConfiguration), customerConfiguration.getAccept(),
						customerConfiguration.getContentType());
				System.out.println(response);

			}
			else if (customerConfiguration.getContentType().equals(RequestType.XML))
			{
				response = wsinvoke.postRequest(customerConfiguration.getUrl(),
						prepareXMLUpdateProfilRequest(customerData, customerConfiguration), customerConfiguration.getAccept(),
						customerConfiguration.getContentType());
				System.out.println(response);
			}
			else if (customerConfiguration.getContentType().equals(RequestType.FORM))
			{
				//
			}
		}
		catch (final CreateWsRequestException | InvokeWsException e)
		{
			LOG.error(e.getMessage());
		}
	}


	private final String prepareJSONUpdateEmailRequest(final String newUid,
			final CustomerWebServiceConfigurationModel customerConfiguration) throws CreateWsRequestException
	{

		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode rootNode = mapper.createObjectNode();
		final UserModel user = userService.getCurrentUser();
		final CustomerData customer = getCustomerConverter().convert(user);
		for (final CustomerWebServiceParameterModel additionelParam : customerConfiguration.getParameters())
		{
			if (additionelParam.getValue().equals(CustomerParameter.CLIENTCODE))
			{
				rootNode.put(additionelParam.getKey(), user.getPk().toString());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTEMAIL))
			{
				rootNode.put(additionelParam.getKey(), newUid);
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTFIRSTNAME))
			{
				rootNode.put(additionelParam.getKey(), customer.getFirstName());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTLASTNAME))
			{
				rootNode.put(additionelParam.getKey(), customer.getLastName());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.TITLECODE))
			{
				rootNode.put(additionelParam.getKey(), customer.getTitleCode());
			}
		}
		for (final PersoWSParamModel persoParam : customerConfiguration.getPersonalisedParameters())
		{
			rootNode.put(persoParam.getKey(), persoParam.getValue());
		}
		for (final PersoWSParamModel securityParam : customerConfiguration.getSecurityParameters())
		{
			rootNode.put(securityParam.getKey(), securityParam.getValue());
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

	private final String prepareJSONUpdateProfilRequest(final CustomerData customerData,
			final CustomerWebServiceConfigurationModel customerConfiguration) throws CreateWsRequestException
	{
		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode rootNode = mapper.createObjectNode();
		final UserModel user = userService.getCurrentUser();
		for (final CustomerWebServiceParameterModel additionelParam : customerConfiguration.getParameters())
		{
			if (additionelParam.getValue().equals(CustomerParameter.CLIENTCODE))
			{
				rootNode.put(additionelParam.getKey(), user.getPk().toString());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTEMAIL))
			{
				rootNode.put(additionelParam.getKey(), user.getUid());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTFIRSTNAME))
			{
				rootNode.put(additionelParam.getKey(), customerData.getFirstName());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTLASTNAME))
			{
				rootNode.put(additionelParam.getKey(), customerData.getLastName());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.TITLECODE))
			{
				rootNode.put(additionelParam.getKey(), customerData.getTitleCode());
			}
		}
		for (final PersoWSParamModel persoParam : customerConfiguration.getPersonalisedParameters())
		{
			rootNode.put(persoParam.getKey(), persoParam.getValue());
		}
		for (final PersoWSParamModel securityParam : customerConfiguration.getSecurityParameters())
		{
			rootNode.put(securityParam.getKey(), securityParam.getValue());
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

	private final String prepareXMLUpdateProfilRequest(final CustomerData customerData,
			final CustomerWebServiceConfigurationModel customerConfiguration) throws CreateWsRequestException
	{
		try
		{
			final UserModel user = userService.getCurrentUser();
			final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			final Document document = documentBuilder.newDocument();
			final TransformerFactory tf = TransformerFactory.newInstance();
			final Transformer transformer = tf.newTransformer();
			final StringWriter writer = new StringWriter();
			final Element root = document.createElement("CustomerDetails");
			Element elem;

			for (final CustomerWebServiceParameterModel additionelParam : customerConfiguration.getParameters())
			{
				if (additionelParam.getValue().equals(CustomerParameter.CLIENTCODE))
				{
					elem = document.createElement(additionelParam.getKey());
					elem.appendChild(document.createTextNode(user.getPk().toString()));
					root.appendChild(elem);
				}
				else if (additionelParam.getValue().equals(CustomerParameter.CLIENTEMAIL))
				{
					elem = document.createElement(additionelParam.getKey());
					elem.appendChild(document.createTextNode(user.getUid()));
					root.appendChild(elem);
				}
				else if (additionelParam.getValue().equals(CustomerParameter.CLIENTFIRSTNAME))
				{
					elem = document.createElement(additionelParam.getKey());
					elem.appendChild(document.createTextNode(customerData.getFirstName()));
					root.appendChild(elem);
				}
				else if (additionelParam.getValue().equals(CustomerParameter.CLIENTLASTNAME))
				{
					elem = document.createElement(additionelParam.getKey());
					elem.appendChild(document.createTextNode(customerData.getLastName()));
					root.appendChild(elem);
				}
				else if (additionelParam.getValue().equals(CustomerParameter.TITLECODE))
				{
					elem = document.createElement(additionelParam.getKey());
					elem.appendChild(document.createTextNode(customerData.getTitleCode()));
					root.appendChild(elem);
				}
			}
			for (final PersoWSParamModel persoParam : customerConfiguration.getPersonalisedParameters())
			{
				elem = document.createElement(persoParam.getKey());
				elem.appendChild(document.createTextNode(persoParam.getValue()));
				root.appendChild(elem);
			}
			for (final PersoWSParamModel securityParam : customerConfiguration.getSecurityParameters())
			{
				elem = document.createElement(securityParam.getKey());
				elem.appendChild(document.createTextNode(securityParam.getValue()));
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

	private String prepareUrl(final CustomerWebServiceConfigurationModel customerConfiguration, final UserModel model)
	{
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(customerConfiguration.getUrl());
		final Collection<PersoWSParamModel> persoParams = customerConfiguration.getPersonalisedParameters();
		final Collection<PersoWSParamModel> securityParams = customerConfiguration.getSecurityParameters();
		final Collection<CustomerWebServiceParameterModel> additionelParams = customerConfiguration.getParameters();
		final CustomerData customer = getCustomerConverter().convert(model);
		if (additionelParams != null && !additionelParams.isEmpty())
		{
			for (final CustomerWebServiceParameterModel additionelParam : additionelParams)
			{
				if (additionelParam.getValue().equals(CustomerParameter.CLIENTCODE))
				{
					uriBuilder.queryParam(additionelParam.getKey(), model.getPk());
				}
				if (additionelParam.getValue().equals(CustomerParameter.CLIENTEMAIL))
				{
					uriBuilder.queryParam(additionelParam.getKey(), model.getUid());
				}
				else if (additionelParam.getValue().equals(CustomerParameter.CLIENTFIRSTNAME))
				{
					uriBuilder.queryParam(additionelParam.getKey(), customer.getFirstName());
				}
				else if (additionelParam.getValue().equals(CustomerParameter.CLIENTLASTNAME))
				{
					uriBuilder.queryParam(additionelParam.getKey(), customer.getLastName());
				}
				else if (additionelParam.getValue().equals(CustomerParameter.TITLECODE))
				{
					uriBuilder.queryParam(additionelParam.getKey(), customer.getTitleCode());
				}
			}
		}
		if (securityParams != null && !securityParams.isEmpty())
		{
			for (final PersoWSParamModel securityParam : securityParams)
			{
				uriBuilder.queryParam(securityParam.getKey(), securityParam.getValue());
			}
		}
		if (persoParams != null && !persoParams.isEmpty())
		{
			for (final PersoWSParamModel persoParam : persoParams)
			{
				uriBuilder.queryParam(persoParam.getKey(), persoParam.getValue());
			}
		}
		return uriBuilder.toUriString();
	}

}
