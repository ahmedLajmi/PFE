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
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.PriceWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.PriceWebServiceConfigurationService;

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
public class DefaultPriceWebServiceConfigurationFacade implements PriceWebServiceConfigurationFacade
{

	@Resource(name = "priceWebServiceConfigurationService")
	private PriceWebServiceConfigurationService priceWebServiceConfigurationService;

	private static final Logger LOG = Logger.getLogger(DefaultPriceWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final PriceWebServiceConfigurationModel pm = priceWebServiceConfigurationService.findPriceWsConfiguration(id);
		final WebServiceConfigurationDetailsData cd = new WebServiceConfigurationDetailsData();
		cd.setId(pm.getPk().toString());
		cd.setName(pm.getName());
		cd.setUrl(pm.getUrl());
		cd.setDescription(pm.getDescription());
		cd.setMethod(pm.getMethod().toString());
		cd.setEnable(pm.getEnable().booleanValue());
		final List<WebServiceParameterData> params = new ArrayList<>();
		final List<WebServiceParameterData> persoParams = new ArrayList<>();
		final List<WebServiceParameterData> securityParams = new ArrayList<>();
		for (final PriceWebServiceParameterModel param : pm.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			params.add(pd);
		}
		cd.setParameters(params);
		for (final PersoWSParamModel param : pm.getPersonalisedParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			persoParams.add(pd);
		}
		cd.setPersoParameters(persoParams);
		for (final PersoWSParamModel param : pm.getSecurityParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			securityParams.add(pd);
		}
		cd.setSecurityParameters(securityParams);
		cd.setAccept(pm.getAccept().toString());
		if (pm.getContentType() != null)
		{
			cd.setContentType(pm.getContentType().toString());
		}

		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<PriceWebServiceConfigurationModel> priceConfigModels = priceWebServiceConfigurationService
				.getAllConfigurations();
		final List<WebServiceConfigurationData> priceWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final PriceWebServiceConfigurationModel pm : priceConfigModels)
		{
			final WebServiceConfigurationData cd = new WebServiceConfigurationData();
			cd.setId(pm.getPk().toString());
			cd.setName(pm.getName());
			priceWsConfigData.add(cd);
		}
		return priceWsConfigData;
	}

	@Override
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> params)
	{
		final PriceWebServiceConfigurationModel pm = priceWebServiceConfigurationService.findPriceWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
		final WSInvoke wsInvoke = new WSInvoke();
		ResponseEntity<String> wsResponse = null;
		try
		{
			if (pm.getMethod().equals(MethodType.GET))
			{
				wsResponse = wsInvoke.getRequest(pm.getUrl(), params, pm.getAccept());
			}
			else if (pm.getMethod().equals(MethodType.POST))
			{
				final Collection<PersoWSParamModel> persoParams = pm.getPersonalisedParameters();
				for (final PersoWSParamModel persoParam : persoParams)
				{
					params.put(persoParam.getKey(), persoParam.getValue());
				}
				wsResponse = wsInvoke.postRequest(pm.getUrl(), new LinkedMultiValueMap(params), pm.getAccept(), pm.getContentType());
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
