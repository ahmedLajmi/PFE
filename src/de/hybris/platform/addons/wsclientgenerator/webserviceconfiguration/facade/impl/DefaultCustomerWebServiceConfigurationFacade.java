/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.impl;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationDetailsData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceParameterData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceResponseData;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.CustomerWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.CustomerWebServiceConfigurationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultCustomerWebServiceConfigurationFacade implements CustomerWebServiceConfigurationFacade
{

	@Resource(name = "customerWebServiceConfigurationService")
	private CustomerWebServiceConfigurationService customerWebServiceConfigurationService;

	private static final Logger LOG = Logger.getLogger(DefaultCustomerWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final CustomerWebServiceConfigurationModel cm = customerWebServiceConfigurationService.findCustomerWsConfiguration(id);
		final WebServiceConfigurationDetailsData cd = new WebServiceConfigurationDetailsData();
		cd.setId(cm.getPk().toString());
		cd.setName(cm.getName());
		cd.setUrl(cm.getUrl());
		cd.setDescription(cm.getDescription());
		cd.setMethod(cm.getMethod().toString());
		cd.setEnable(cm.getEnable().booleanValue());
		final List<WebServiceParameterData> params = new ArrayList<>();
		final List<WebServiceParameterData> persoParams = new ArrayList<>();
		final List<WebServiceParameterData> securityParams = new ArrayList<>();
		for (final CustomerWebServiceParameterModel param : cm.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			params.add(pd);
		}
		cd.setParameters(params);
		for (final PersoWSParamModel param : cm.getPersonalisedParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			persoParams.add(pd);
		}
		cd.setPersoParameters(persoParams);
		for (final PersoWSParamModel param : cm.getSecurityParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			securityParams.add(pd);
		}
		cd.setSecurityParameters(securityParams);
		cd.setAccept(cm.getAccept().toString());
		if (cm.getContentType() != null)
		{
			cd.setContentType(cm.getContentType().toString());
		}
		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<CustomerWebServiceConfigurationModel> customerConfigModels = customerWebServiceConfigurationService
				.getAllConfigurations();
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
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> params)
	{
		final CustomerWebServiceConfigurationModel cm = customerWebServiceConfigurationService.findCustomerWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
		final WSInvoke wsInvoke = new WSInvoke();
		ResponseEntity<String> wsResponse = null;
		try
		{
			if (cm.getMethod().equals(MethodType.GET))
			{
				wsResponse = wsInvoke.getRequest(cm.getUrl(), params, cm.getAccept());
			}
			else if (cm.getMethod().equals(MethodType.POST))
			{
				final Collection<PersoWSParamModel> persoParams = cm.getPersonalisedParameters();
				for (final PersoWSParamModel persoParam : persoParams)
				{
					params.put(persoParam.getKey(), persoParam.getValue());
				}
				wsResponse = wsInvoke.postRequest(cm.getUrl(), new LinkedMultiValueMap(params), cm.getAccept(), cm.getContentType());
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
