/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.facade.impl.customer;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.facade.customer.WSCustomerFacade;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.CustomerWebServiceConfigurationService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultWsCustomerFacade extends DefaultCustomerFacade implements WSCustomerFacade
{
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "customerWebServiceConfigurationService")
	private CustomerWebServiceConfigurationService customerWsConfService;

	private CustomerWebServiceConfigurationModel customerConfiguration;

	private static final Logger LOG = Logger.getLogger(DefaultWsCustomerFacade.class);

	@Override
	public CustomerData getCurrentCustomer()
	{
		final UserModel user = userService.getCurrentUser();
		CustomerData customer = getCustomerConverter().convert(user);

		customerConfiguration = customerWsConfService.getWsEnabledConfiguration(MethodType.GET);
		if (customerConfiguration != null)
		{
			final WSInvoke wsinvoke = new WSInvoke();
			try
			{
				final Map<String, String> response = wsinvoke.getRequest(customerConfiguration.getUrl(), prepareGetParams(user),
						customerWsConfService.prepareHeadersParams(customerConfiguration), customerConfiguration.getAccept());
				// test sur code success
				final String successCode = customerConfiguration.getSuccessCode();
				final String codeResponse = customerWsConfService.getResponseCode(customerConfiguration);
				if (codeResponse != null && successCode.equalsIgnoreCase(response.get(codeResponse).toString()))
				{
					customer = customerWsConfService.prepareCustomer(customerConfiguration, response);
				}
				else
				{
					customerWsConfService.saveCall(customerConfiguration, prepareGetParams(user).toString(), response.toString(),
							response.get(codeResponse), "Response code mismatch");
					if (!customerConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
					{
						return customer;
					}
					else
					{
						customer.setFirstName("");
						customer.setLastName("");
						customer.setDisplayUid("");
						customer.setTitle("");
						customer.setTitleCode("");
						return customer;
					}
				}
			}
			catch (final InvokeWsException e)
			{
				LOG.error(e.getMessage());
				customerWsConfService.saveCall(customerConfiguration, prepareGetParams(user).toString(), null, null, e.getMessage());
				if (!customerConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
				{
					return customer;
				}
				else
				{
					customer.setFirstName("");
					customer.setLastName("");
					customer.setDisplayUid("");
					customer.setTitle("");
					customer.setTitleCode("");
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

		customerConfiguration = customerWsConfService.getWsEnabledConfiguration(MethodType.POST);
		if (customerConfiguration != null)
		{
			super.changeUid(newUid, currentPassword);
			wsUpdateEmail(newUid);
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
		customerConfiguration = customerWsConfService.getWsEnabledConfiguration(MethodType.POST);
		if (customerConfiguration != null)
		{
			if (customerConfiguration.getMode().equals(ModeType.ONLYWITHWEBSERVICE))
			{
				wsCreateUpdateProfil(customerData);
			}
			else if (customerConfiguration.getMode().equals(ModeType.NATIVEWEBSERVICE))
			{

				super.updateProfile(customerData);
				wsCreateUpdateProfil(customerData);
			}
			else
			{
				wsCreateUpdateProfil(customerData);
				super.updateProfile(customerData);
			}
		}
		else
		{
			super.updateProfile(customerData);
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

		customerConfiguration = customerWsConfService.getWsEnabledConfiguration(MethodType.POST);
		if (customerConfiguration != null)
		{
			if (customerConfiguration.getMode().equals(ModeType.WEBSERVICENATIVE))
			{
				wsCreateUpdateProfil(customerData);
				super.register(registerData);
			}
			else
			{
				super.register(registerData);
				wsCreateUpdateProfil(customerData);
			}
		}
		else
		{
			super.register(registerData);
		}
	}


	@Override
	public void wsUpdateEmail(final String newUid)
	{
		final WSInvoke wsinvoke = new WSInvoke();
		try
		{
			wsinvoke.postRequest(customerConfiguration.getUrl(), prepareUpdateEmailRequest(newUid),
					customerWsConfService.prepareHeadersParams(customerConfiguration), customerConfiguration.getAccept(),
					customerConfiguration.getContentType());
		}
		catch (final CreateWsRequestException | InvokeWsException e)
		{
			LOG.error(e.getMessage());
		}

	}

	@Override
	public void wsCreateUpdateProfil(final CustomerData customerData)
	{
		final WSInvoke wsinvoke = new WSInvoke();
		try
		{
			wsinvoke.postRequest(customerConfiguration.getUrl(), prepareProfilRequest(customerData),
					customerWsConfService.prepareHeadersParams(customerConfiguration), customerConfiguration.getAccept(),
					customerConfiguration.getContentType());
		}
		catch (final CreateWsRequestException | InvokeWsException e)
		{
			LOG.error(e.getMessage());
		}
	}


	@Override
	public Map<String, Map<String, String>> prepareUpdateEmailRequest(final String newUid) throws CreateWsRequestException
	{
		final UserModel user = userService.getCurrentUser();
		final CustomerData customer = getCustomerConverter().convert(user);
		final Map<String, Map<String, String>> params = new HashMap<>();

		final Map<String, String> pathPrameters = customerWsConfService.prepareDynamicPathParameters(customerConfiguration,
				customer);
		final Map<String, String> body = customerWsConfService.prepareDynamicQueryParameters(customerConfiguration, customer,
				newUid);
		body.putAll(customerWsConfService.prepareStaticParams(customerConfiguration));
		body.put("root", customerConfiguration.getRootKey());

		params.put("pathPrameters", pathPrameters);
		params.put("body", body);

		return params;
	}

	@Override
	public Map<String, Map<String, String>> prepareProfilRequest(final CustomerData customerData)
	{

		final Map<String, Map<String, String>> params = new HashMap<>();

		final Map<String, String> pathPrameters = customerWsConfService.prepareDynamicPathParameters(customerConfiguration,
				customerData);
		final Map<String, String> body = customerWsConfService.prepareDynamicQueryParameters(customerConfiguration, customerData);
		body.putAll(customerWsConfService.prepareStaticParams(customerConfiguration));
		body.put("root", customerConfiguration.getRootKey());

		params.put("pathPrameters", pathPrameters);
		params.put("body", body);

		return params;
	}

	@Override
	public Map<String, Map<String, String>> prepareGetParams(final UserModel model)
	{
		final Map<String, Map<String, String>> params = new HashMap<>();
		final CustomerData customer = getCustomerConverter().convert(model);

		final Map<String, String> pathPrameters = customerWsConfService.prepareDynamicPathParameters(customerConfiguration,
				customer);
		final Map<String, String> queryPrameters = customerWsConfService.prepareDynamicQueryParameters(customerConfiguration,
				customer);
		queryPrameters.putAll(customerWsConfService.prepareStaticParams(customerConfiguration));

		params.put("pathPrameters", pathPrameters);
		params.put("queryPrameters", queryPrameters);
		return params;
	}

}
