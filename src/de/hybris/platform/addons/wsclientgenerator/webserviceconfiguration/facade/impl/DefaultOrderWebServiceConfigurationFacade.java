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
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.OrderWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.OrderWebServiceConfigurationService;

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
public class DefaultOrderWebServiceConfigurationFacade implements OrderWebServiceConfigurationFacade
{

	@Resource(name = "orderWebServiceConfigurationService")
	private OrderWebServiceConfigurationService orderWebServiceConfigurationService;

	private static final Logger LOG = Logger.getLogger(DefaultOrderWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final OrderWebServiceConfigurationModel om = orderWebServiceConfigurationService.findOrderWsConfiguration(id);
		final WebServiceConfigurationDetailsData cd = new WebServiceConfigurationDetailsData();
		cd.setId(om.getPk().toString());
		cd.setName(om.getName());
		cd.setUrl(om.getUrl());
		cd.setDescription(om.getDescription());
		cd.setMethod(om.getMethod().toString());
		cd.setEnable(om.getEnable().booleanValue());
		final List<WebServiceParameterData> params = new ArrayList<>();
		final List<WebServiceParameterData> persoParams = new ArrayList<>();
		final List<WebServiceParameterData> securityParams = new ArrayList<>();
		for (final OrderWebServiceParameterModel param : om.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			params.add(pd);
		}
		cd.setParameters(params);
		for (final PersoWSParamModel param : om.getPersonalisedParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			persoParams.add(pd);
		}
		cd.setPersoParameters(persoParams);
		for (final PersoWSParamModel param : om.getSecurityParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			securityParams.add(pd);
		}
		cd.setSecurityParameters(securityParams);
		cd.setAccept(om.getAccept().toString());
		if (om.getContentType() != null)
		{
			cd.setContentType(om.getContentType().toString());
		}

		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<OrderWebServiceConfigurationModel> orderConfigModels = orderWebServiceConfigurationService
				.getAllConfigurations();
		final List<WebServiceConfigurationData> orderWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final OrderWebServiceConfigurationModel om : orderConfigModels)
		{
			final WebServiceConfigurationData cd = new WebServiceConfigurationData();
			cd.setId(om.getPk().toString());
			cd.setName(om.getName());
			orderWsConfigData.add(cd);
		}
		return orderWsConfigData;
	}

	@Override
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> params)
	{
		final OrderWebServiceConfigurationModel om = orderWebServiceConfigurationService.findOrderWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
		final WSInvoke wsInvoke = new WSInvoke();
		ResponseEntity<String> wsResponse = null;
		try
		{
			if (om.getMethod().equals(MethodType.GET))
			{
				wsResponse = wsInvoke.getRequest(om.getUrl(), params, orderWebServiceConfigurationService.prepareHeadersParams(om),
						om.getAccept());
			}
			else if (om.getMethod().equals(MethodType.POST))
			{
				final Collection<PersoWSParamModel> persoParams = om.getPersonalisedParameters();
				for (final PersoWSParamModel persoParam : persoParams)
				{
					params.put(persoParam.getKey(), persoParam.getValue());
				}
				wsResponse = wsInvoke.postRequest(om.getUrl(), new LinkedMultiValueMap(params), om.getAccept(), om.getContentType());
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
