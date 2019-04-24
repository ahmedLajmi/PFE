/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.customer.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.addons.wsclientgenerator.customer.WSCustomerFacade;
import de.hybris.platform.addons.wsclientgenerator.enums.CustomerParameter;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.OrderType;
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
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
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
public class DefaultWsCustomerFacade extends DefaultCustomerFacade implements WSCustomerFacade
{
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "customerWebServiceConfigurationDao")
	private CustomerWebServiceConfigurationDao customerWebServiceConfigurationDao;

	private CustomerWebServiceConfigurationModel customerConfiguration;

	private static final Logger LOG = Logger.getLogger(DefaultWsCustomerFacade.class);

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
				final ResponseEntity<String> response = wsinvoke.getRequest(customerConfiguration.getUrl(),
						prepareRequestParams(customerConfiguration, user), customerConfiguration.getAccept());
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
		if (customerConfiguration != null)
		{
			if (customerConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
			{
				wsCreateUpdateProfil(customerConfiguration, customerData);
			}
			else if (customerConfiguration.getMode().equals(ModeType.WEBSERVICEWITHNATIVE))
			{
				if (customerConfiguration.getOrder().equals(OrderType.NATIVEWEBSERVICE))
				{
					super.updateProfile(customerData);
					wsCreateUpdateProfil(customerConfiguration, customerData);
				}
				else if (customerConfiguration.getOrder().equals(OrderType.WEBSERVICENATIVE))
				{
					wsCreateUpdateProfil(customerConfiguration, customerData);
					super.updateProfile(customerData);
				}
			}
			else
			{
				super.updateProfile(customerData);
			}
		}

	}

	@Override
	public void register(final RegisterData registerData) throws DuplicateUidException
	{
		validateParameterNotNullStandardMessage("registerData", registerData);
		Assert.hasText(registerData.getFirstName(), "The field [FirstName] cannot be empty");
		Assert.hasText(registerData.getLastName(), "The field [LastName] cannot be empty");
		Assert.hasText(registerData.getLogin(), "The field [Login] cannot be empty");

		final CustomerData customerData = new CustomerData();
		customerData.setFirstName(registerData.getFirstName());
		customerData.setLastName(registerData.getLastName());
		customerData.setTitleCode(registerData.getTitleCode());
		customerData.setDisplayUid(registerData.getLogin());
		customerData.setUid(registerData.getLogin());

		customerConfiguration = customerWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.POST);
		if (customerConfiguration != null)
		{
			if (customerConfiguration.getOrder() != null && customerConfiguration.getOrder().equals(OrderType.WEBSERVICENATIVE))
			{
				wsCreateUpdateProfil(customerConfiguration, customerData);
				super.register(registerData);
			}
			else
			{
				super.register(registerData);
				wsCreateUpdateProfil(customerConfiguration, customerData);
			}
		}
		else
		{
			super.register(registerData);
		}
	}

	@Override
	public Map<String, String> jsonParseResponse(final String response) throws ParseWsResponseException
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

	@Override
	public Map<String, String> xmlParseResponse(final String response) throws ParseWsResponseException
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

	@Override
	public void wsUpdateEmail(final CustomerWebServiceConfigurationModel customerConfiguration, final String newUid)
	{
		final WSInvoke wsinvoke = new WSInvoke();
		ResponseEntity<String> response;
		try
		{
			response = wsinvoke.postRequest(customerConfiguration.getUrl(), prepareUpdateEmailRequest(newUid, customerConfiguration),
					customerConfiguration.getAccept(), customerConfiguration.getContentType());
			System.out.println(response);
		}
		catch (final CreateWsRequestException | InvokeWsException e)
		{
			LOG.error(e.getMessage());
		}
	}

	@Override
	public void wsCreateUpdateProfil(final CustomerWebServiceConfigurationModel customerConfiguration,
			final CustomerData customerData)
	{
		final WSInvoke wsinvoke = new WSInvoke();
		ResponseEntity<String> response;
		try
		{
			response = wsinvoke.postRequest(customerConfiguration.getUrl(),
					prepareProfilRequest(customerData, customerConfiguration), customerConfiguration.getAccept(),
					customerConfiguration.getContentType());
			System.out.println(response);
		}
		catch (final CreateWsRequestException | InvokeWsException e)
		{
			LOG.error(e.getMessage());
		}
	}


	@Override
	public MultiValueMap<String, String> prepareUpdateEmailRequest(final String newUid,
			final CustomerWebServiceConfigurationModel customerConfiguration) throws CreateWsRequestException
	{
		final UserModel user = userService.getCurrentUser();
		final CustomerData customer = getCustomerConverter().convert(user);
		final MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		for (final CustomerWebServiceParameterModel additionelParam : customerConfiguration.getParameters())
		{
			if (additionelParam.getValue().equals(CustomerParameter.CLIENTEMAIL))
			{
				request.add(additionelParam.getKey(), newUid);
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTFIRSTNAME))
			{
				request.add(additionelParam.getKey(), customer.getFirstName());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTLASTNAME))
			{
				request.add(additionelParam.getKey(), customer.getLastName());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.TITLECODE))
			{
				request.add(additionelParam.getKey(), customer.getTitleCode());
			}
		}
		for (final PersoWSParamModel persoParam : customerConfiguration.getPersonalisedParameters())
		{
			request.add(persoParam.getKey(), persoParam.getValue());
		}
		for (final PersoWSParamModel securityParam : customerConfiguration.getSecurityParameters())
		{
			request.add(securityParam.getKey(), securityParam.getValue());
		}
		if (customerConfiguration.getRootKey() != null)
		{
			request.add("root", customerConfiguration.getRootKey());
		}
		return request;
	}

	@Override
	public MultiValueMap<String, String> prepareProfilRequest(final CustomerData customerData,
			final CustomerWebServiceConfigurationModel customerConfiguration)
	{

		final MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		for (final CustomerWebServiceParameterModel additionelParam : customerConfiguration.getParameters())
		{
			if (additionelParam.getValue().equals(CustomerParameter.CLIENTEMAIL))
			{
				request.add(additionelParam.getKey(), customerData.getUid());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTFIRSTNAME))
			{
				request.add(additionelParam.getKey(), customerData.getFirstName());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.CLIENTLASTNAME))
			{
				request.add(additionelParam.getKey(), customerData.getLastName());
			}
			else if (additionelParam.getValue().equals(CustomerParameter.TITLECODE))
			{
				request.add(additionelParam.getKey(), customerData.getTitleCode());
			}
		}
		for (final PersoWSParamModel persoParam : customerConfiguration.getPersonalisedParameters())
		{
			request.add(persoParam.getKey(), persoParam.getValue());
		}
		for (final PersoWSParamModel securityParam : customerConfiguration.getSecurityParameters())
		{
			request.add(securityParam.getKey(), securityParam.getValue());
		}
		if (customerConfiguration.getRootKey() != null)
		{
			request.add("root", customerConfiguration.getRootKey());
		}
		return request;
	}

	@Override
	public Map<String, String> prepareRequestParams(final CustomerWebServiceConfigurationModel customerConfiguration,
			final UserModel model)
	{
		final Map<String, String> params = new HashMap<>();
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
					params.put(additionelParam.getKey(), model.getPk().toString());
				}
				if (additionelParam.getValue().equals(CustomerParameter.CLIENTEMAIL))
				{
					params.put(additionelParam.getKey(), model.getUid());
				}
				else if (additionelParam.getValue().equals(CustomerParameter.CLIENTFIRSTNAME))
				{
					params.put(additionelParam.getKey(), customer.getFirstName());
				}
				else if (additionelParam.getValue().equals(CustomerParameter.CLIENTLASTNAME))
				{
					params.put(additionelParam.getKey(), customer.getLastName());
				}
				else if (additionelParam.getValue().equals(CustomerParameter.TITLECODE))
				{
					params.put(additionelParam.getKey(), customer.getTitleCode());
				}
			}
		}
		if (securityParams != null && !securityParams.isEmpty())
		{
			for (final PersoWSParamModel securityParam : securityParams)
			{
				params.put(securityParam.getKey(), securityParam.getValue());
			}
		}
		if (persoParams != null && !persoParams.isEmpty())
		{
			for (final PersoWSParamModel persoParam : persoParams)
			{
				params.put(persoParam.getKey(), persoParam.getValue());
			}
		}
		return params;
	}

}
