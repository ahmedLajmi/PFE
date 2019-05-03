/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.facade.impl.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationDetailsData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceParameterData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceResponseData;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.facade.webserviceconfiguration.CustomerWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.HeaderWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.CustomerWebServiceConfigurationService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.enumeration.EnumerationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultCustomerWebServiceConfigurationFacade implements CustomerWebServiceConfigurationFacade
{

	@Resource(name = "customerWebServiceConfigurationService")
	private CustomerWebServiceConfigurationService customerWsConfService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	private static final Logger LOG = Logger.getLogger(DefaultCustomerWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final CustomerWebServiceConfigurationModel cm = customerWsConfService.findCustomerWsConfiguration(id);
		final WebServiceConfigurationDetailsData cd = new WebServiceConfigurationDetailsData();
		cd.setId(cm.getPk().toString());
		cd.setName(cm.getName());
		cd.setUrl(cm.getUrl());
		cd.setDescription(cm.getDescription());
		cd.setMethod(enumerationService.getEnumerationName(cm.getMethod()));
		cd.setEnable(cm.getEnable().booleanValue());
		final List<WebServiceParameterData> pathParams = new ArrayList<>();
		final List<WebServiceParameterData> queryParams = new ArrayList<>();
		final List<WebServiceParameterData> persoParams = new ArrayList<>();
		final List<WebServiceParameterData> headerParams = new ArrayList<>();
		final List<WebServiceParameterData> securityParams = new ArrayList<>();

		for (final CustomerWebServiceParameterModel param : cm.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			queryParams.add(pd);
		}
		cd.setQueryParameters(queryParams);

		for (final CustomerWebServiceParameterModel param : cm.getPathParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			pathParams.add(pd);
		}
		cd.setPathParameters(pathParams);

		for (final PersoWSParamModel param : cm.getPersonalisedParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			persoParams.add(pd);
		}
		cd.setPersoParameters(persoParams);


		for (final HeaderWSParamModel param : cm.getHeadersParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			headerParams.add(pd);
		}
		cd.setHeaderParameters(headerParams);


		if (cm.getLogin() != null && !StringUtils.isEmpty(cm.getLogin()))
		{
			final WebServiceParameterData login = new WebServiceParameterData();
			final WebServiceParameterData password = new WebServiceParameterData();
			login.setKey("login");
			login.setValue(cm.getLogin());
			password.setKey("password");
			password.setValue(cm.getPassword());
			securityParams.add(login);
			securityParams.add(password);
			cd.setSecurityParameters(securityParams);
		}

		cd.setAccept(enumerationService.getEnumerationName(cm.getAccept()));

		if (cm.getContentType() != null)
		{
			cd.setContentType(enumerationService.getEnumerationName(cm.getContentType()));
		}
		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<CustomerWebServiceConfigurationModel> customerConfigModels = customerWsConfService.getAllConfigurations();
		final List<WebServiceConfigurationData> customerWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final CustomerWebServiceConfigurationModel cm : customerConfigModels)
		{
			final WebServiceConfigurationData cd = new WebServiceConfigurationData();
			cd.setId(cm.getPk().toString());
			cd.setName(cm.getName());
			customerWsConfigData.add(cd);
		}
		return customerWsConfigData;
	}

	@Override
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> queryParams,
			final Map<String, String> pathParams)
	{
		final CustomerWebServiceConfigurationModel cm = customerWsConfService.findCustomerWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
		final WSInvoke wsInvoke = new WSInvoke();
		ResponseEntity<String> wsResponse = null;
		queryParams.putAll(customerWsConfService.prepareStaticParams(cm));
		final Map<String, Map<String, String>> params = new HashMap<>();
		params.put("pathPrameters", pathParams);
		try
		{
			if (cm.getMethod().equals(MethodType.GET))
			{
				params.put("queryPrameters", queryParams);
				wsResponse = wsInvoke.getSimulationRequest(cm.getUrl(), params, customerWsConfService.prepareHeadersParams(cm),
						cm.getAccept());
			}
			else if (cm.getMethod().equals(MethodType.POST))
			{
				params.put("body", queryParams);
				wsResponse = wsInvoke.postSimulationRequest(cm.getUrl(), params, customerWsConfService.prepareHeadersParams(cm),
						cm.getAccept(), cm.getContentType());
			}
		}
		catch (final InvokeWsException | CreateWsRequestException e)
		{
			LOG.error(e.getMessage());
			response.setResponseBody(e.getMessage());
			return response;
		}
		if (wsResponse != null)
		{
			response.setResponseCode(wsResponse.getStatusCode().toString());
			response.setResponseBody(wsResponse.getBody());
			return response;
		}
		else
		{
			return null;
		}
	}

}
