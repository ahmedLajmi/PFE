/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.facade.impl.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationDetailsData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceParameterData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceResponseData;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.facade.webserviceconfiguration.PriceWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.model.HeaderWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.PriceWebServiceConfigurationService;
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
public class DefaultPriceWebServiceConfigurationFacade implements PriceWebServiceConfigurationFacade
{

	@Resource(name = "priceWebServiceConfigurationService")
	private PriceWebServiceConfigurationService priceWsConfService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	private static final Logger LOG = Logger.getLogger(DefaultPriceWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final PriceWebServiceConfigurationModel pm = priceWsConfService.findPriceWsConfiguration(id);
		final WebServiceConfigurationDetailsData cd = new WebServiceConfigurationDetailsData();
		cd.setId(pm.getPk().toString());
		cd.setName(pm.getName());
		cd.setUrl(pm.getUrl());
		cd.setDescription(pm.getDescription());
		cd.setMethod(enumerationService.getEnumerationName(pm.getMethod()));
		cd.setEnable(pm.getEnable().booleanValue());
		final List<WebServiceParameterData> pathParams = new ArrayList<>();
		final List<WebServiceParameterData> queryParams = new ArrayList<>();
		final List<WebServiceParameterData> persoParams = new ArrayList<>();
		final List<WebServiceParameterData> headerParams = new ArrayList<>();
		final List<WebServiceParameterData> securityParams = new ArrayList<>();

		for (final PriceWebServiceParameterModel param : pm.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			queryParams.add(pd);
		}
		cd.setQueryParameters(queryParams);

		for (final PriceWebServiceParameterModel param : pm.getPathParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			pathParams.add(pd);
		}
		cd.setPathParameters(pathParams);

		for (final PersoWSParamModel param : pm.getPersonalisedParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			persoParams.add(pd);
		}
		cd.setPersoParameters(persoParams);


		for (final HeaderWSParamModel param : pm.getHeadersParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			headerParams.add(pd);
		}
		cd.setHeaderParameters(headerParams);


		if (pm.getLogin() != null && !StringUtils.isEmpty(pm.getLogin()))
		{
			final WebServiceParameterData login = new WebServiceParameterData();
			final WebServiceParameterData password = new WebServiceParameterData();
			login.setKey("login");
			login.setValue(pm.getLogin());
			password.setKey("password");
			password.setValue(pm.getPassword());
			securityParams.add(login);
			securityParams.add(password);
			cd.setSecurityParameters(securityParams);
		}

		cd.setAccept(enumerationService.getEnumerationName(pm.getAccept()));
		if (pm.getContentType() != null)
		{
			cd.setContentType(enumerationService.getEnumerationName(pm.getContentType()));
		}

		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<PriceWebServiceConfigurationModel> priceConfigModels = priceWsConfService.getAllConfigurations();
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
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> queryParams,
			final Map<String, String> pathParams)
	{
		final PriceWebServiceConfigurationModel pm = priceWsConfService.findPriceWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
		final WSInvoke wsInvoke = new WSInvoke();
		ResponseEntity<String> wsResponse = null;
		queryParams.putAll(priceWsConfService.prepareStaticParams(pm));
		final Map<String, Map<String, String>> params = new HashMap<>();
		params.put("pathPrameters", pathParams);
		try
		{
			if (pm.getMethod().equals(MethodType.GET))
			{
				params.put("queryPrameters", queryParams);
				wsResponse = wsInvoke.getSimulationRequest(pm.getUrl(), params, priceWsConfService.prepareHeadersParams(pm),
						pm.getAccept());
			}
		}
		catch (final InvokeWsException e)
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
