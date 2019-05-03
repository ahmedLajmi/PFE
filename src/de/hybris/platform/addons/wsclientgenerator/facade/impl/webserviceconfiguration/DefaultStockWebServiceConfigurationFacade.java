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
import de.hybris.platform.addons.wsclientgenerator.facade.webserviceconfiguration.StockWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.model.HeaderWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.StockWebServiceConfigurationService;
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
public class DefaultStockWebServiceConfigurationFacade implements StockWebServiceConfigurationFacade
{

	@Resource(name = "stockWebServiceConfigurationService")
	private StockWebServiceConfigurationService stockWsConfService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	private static final Logger LOG = Logger.getLogger(DefaultStockWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final StockWebServiceConfigurationModel sm = stockWsConfService.findStockWsConfiguration(id);
		final WebServiceConfigurationDetailsData cd = new WebServiceConfigurationDetailsData();
		cd.setId(sm.getPk().toString());
		cd.setName(sm.getName());

		cd.setUrl(sm.getUrl());
		cd.setDescription(sm.getDescription());
		cd.setMethod(enumerationService.getEnumerationName(sm.getMethod()));
		cd.setEnable(sm.getEnable().booleanValue());
		final List<WebServiceParameterData> pathParams = new ArrayList<>();
		final List<WebServiceParameterData> queryParams = new ArrayList<>();
		final List<WebServiceParameterData> persoParams = new ArrayList<>();
		final List<WebServiceParameterData> headerParams = new ArrayList<>();
		final List<WebServiceParameterData> securityParams = new ArrayList<>();

		for (final StockWebServiceParameterModel param : sm.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			queryParams.add(pd);
		}
		cd.setQueryParameters(queryParams);

		for (final StockWebServiceParameterModel param : sm.getPathParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			pathParams.add(pd);
		}
		cd.setPathParameters(pathParams);

		for (final PersoWSParamModel param : sm.getPersonalisedParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			persoParams.add(pd);
		}
		cd.setPersoParameters(persoParams);


		for (final HeaderWSParamModel param : sm.getHeadersParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			headerParams.add(pd);
		}
		cd.setHeaderParameters(headerParams);


		if (sm.getLogin() != null && !StringUtils.isEmpty(sm.getLogin()))
		{
			final WebServiceParameterData login = new WebServiceParameterData();
			final WebServiceParameterData password = new WebServiceParameterData();
			login.setKey("login");
			login.setValue(sm.getLogin());
			password.setKey("password");
			password.setValue(sm.getPassword());
			securityParams.add(login);
			securityParams.add(password);
			cd.setSecurityParameters(securityParams);
		}

		cd.setAccept(enumerationService.getEnumerationName(sm.getAccept()));
		if (sm.getContentType() != null)
		{
			cd.setContentType(enumerationService.getEnumerationName(sm.getContentType()));
		}

		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<StockWebServiceConfigurationModel> stockConfigModels = stockWsConfService.getAllConfigurations();
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
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> queryParams,
			final Map<String, String> pathParams)
	{
		final StockWebServiceConfigurationModel sm = stockWsConfService.findStockWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
		final WSInvoke wsInvoke = new WSInvoke();
		ResponseEntity<String> wsResponse = null;
		queryParams.putAll(stockWsConfService.prepareStaticParams(sm));
		final Map<String, Map<String, String>> params = new HashMap<>();
		params.put("pathPrameters", pathParams);
		try
		{
			if (sm.getMethod().equals(MethodType.GET))
			{
				params.put("queryPrameters", queryParams);
				wsResponse = wsInvoke.getSimulationRequest(sm.getUrl(), params, stockWsConfService.prepareHeadersParams(sm),
						sm.getAccept());
			}
			else if (sm.getMethod().equals(MethodType.POST))
			{
				params.put("body", queryParams);
				wsResponse = wsInvoke.postSimulationRequest(sm.getUrl(), params, stockWsConfService.prepareHeadersParams(sm),
						sm.getAccept(), sm.getContentType());
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
