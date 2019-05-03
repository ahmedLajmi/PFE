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
import de.hybris.platform.addons.wsclientgenerator.facade.webserviceconfiguration.OrderWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.model.HeaderWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.OrderWebServiceConfigurationService;
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
public class DefaultOrderWebServiceConfigurationFacade implements OrderWebServiceConfigurationFacade
{

	@Resource(name = "orderWebServiceConfigurationService")
	private OrderWebServiceConfigurationService orderWsConfService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	private static final Logger LOG = Logger.getLogger(DefaultOrderWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final OrderWebServiceConfigurationModel om = orderWsConfService.findOrderWsConfiguration(id);
		final WebServiceConfigurationDetailsData cd = new WebServiceConfigurationDetailsData();
		cd.setId(om.getPk().toString());
		cd.setName(om.getName());
		cd.setUrl(om.getUrl());
		cd.setDescription(om.getDescription());
		cd.setMethod(enumerationService.getEnumerationName(om.getMethod()));
		cd.setEnable(om.getEnable().booleanValue());

		final List<WebServiceParameterData> pathParams = new ArrayList<>();
		final List<WebServiceParameterData> queryParams = new ArrayList<>();
		final List<WebServiceParameterData> persoParams = new ArrayList<>();
		final List<WebServiceParameterData> headerParams = new ArrayList<>();
		final List<WebServiceParameterData> securityParams = new ArrayList<>();

		for (final OrderWebServiceParameterModel param : om.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			queryParams.add(pd);
		}
		cd.setQueryParameters(queryParams);

		for (final OrderWebServiceParameterModel param : om.getPathParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			pathParams.add(pd);
		}
		cd.setPathParameters(pathParams);

		for (final PersoWSParamModel param : om.getPersonalisedParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			persoParams.add(pd);
		}
		cd.setPersoParameters(persoParams);


		for (final HeaderWSParamModel param : om.getHeadersParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			headerParams.add(pd);
		}
		cd.setHeaderParameters(headerParams);


		if (om.getLogin() != null && !StringUtils.isEmpty(om.getLogin()))
		{
			final WebServiceParameterData login = new WebServiceParameterData();
			final WebServiceParameterData password = new WebServiceParameterData();
			login.setKey("login");
			login.setValue(om.getLogin());
			password.setKey("password");
			password.setValue(om.getPassword());
			securityParams.add(login);
			securityParams.add(password);
			cd.setSecurityParameters(securityParams);
		}

		cd.setAccept(enumerationService.getEnumerationName(om.getAccept()));
		if (om.getContentType() != null)
		{
			cd.setContentType(enumerationService.getEnumerationName(om.getContentType()));
		}

		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<OrderWebServiceConfigurationModel> orderConfigModels = orderWsConfService.getAllConfigurations();
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
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> queryParams,
			final Map<String, String> pathParams)
	{
		final OrderWebServiceConfigurationModel om = orderWsConfService.findOrderWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
		final WSInvoke wsInvoke = new WSInvoke();
		ResponseEntity<String> wsResponse = null;
		queryParams.putAll(orderWsConfService.prepareStaticParams(om));
		final Map<String, Map<String, String>> params = new HashMap<>();
		params.put("pathPrameters", pathParams);
		try
		{
			if (om.getMethod().equals(MethodType.GET))
			{
				params.put("queryPrameters", queryParams);
				wsResponse = wsInvoke.getSimulationRequest(om.getUrl(), params, orderWsConfService.prepareHeadersParams(om),
						om.getAccept());
			}
			else if (om.getMethod().equals(MethodType.POST))
			{
				params.put("body", queryParams);
				wsResponse = wsInvoke.postSimulationRequest(om.getUrl(), params, orderWsConfService.prepareHeadersParams(om),
						om.getAccept(), om.getContentType());
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
