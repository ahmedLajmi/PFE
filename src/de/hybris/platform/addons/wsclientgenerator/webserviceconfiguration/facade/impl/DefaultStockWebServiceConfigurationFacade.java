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
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.StockWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.StockWebServiceConfigurationService;

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
public class DefaultStockWebServiceConfigurationFacade implements StockWebServiceConfigurationFacade
{

	@Resource(name = "stockWebServiceConfigurationService")
	private StockWebServiceConfigurationService stockWebServiceConfigurationService;

	private static final Logger LOG = Logger.getLogger(DefaultStockWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final StockWebServiceConfigurationModel sm = stockWebServiceConfigurationService.findStockWsConfiguration(id);
		final WebServiceConfigurationDetailsData cd = new WebServiceConfigurationDetailsData();
		cd.setId(sm.getPk().toString());
		cd.setName(sm.getName());

		cd.setUrl(sm.getUrl());
		cd.setDescription(sm.getDescription());
		cd.setMethod(sm.getMethod().toString());
		cd.setEnable(sm.getEnable().booleanValue());
		final List<WebServiceParameterData> params = new ArrayList<>();
		final List<WebServiceParameterData> persoParams = new ArrayList<>();
		final List<WebServiceParameterData> securityParams = new ArrayList<>();
		for (final StockWebServiceParameterModel param : sm.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			params.add(pd);
		}
		cd.setParameters(params);
		for (final PersoWSParamModel param : sm.getPersonalisedParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			persoParams.add(pd);
		}
		cd.setPersoParameters(persoParams);
		for (final PersoWSParamModel param : sm.getSecurityParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			securityParams.add(pd);
		}
		cd.setSecurityParameters(securityParams);
		cd.setAccept(sm.getAccept().toString());
		if (sm.getContentType() != null)
		{
			cd.setContentType(sm.getContentType().toString());
		}

		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<StockWebServiceConfigurationModel> stockConfigModels = stockWebServiceConfigurationService
				.getAllConfigurations();
		final List<WebServiceConfigurationData> stockWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final StockWebServiceConfigurationModel sm : stockConfigModels)
		{
			final WebServiceConfigurationData cd = new WebServiceConfigurationData();
			cd.setId(sm.getPk().toString());
			cd.setName(sm.getName());
			stockWsConfigData.add(cd);
		}
		return stockWsConfigData;
	}

	@Override
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> params)
	{
		final StockWebServiceConfigurationModel pm = stockWebServiceConfigurationService.findStockWsConfiguration(id);
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
