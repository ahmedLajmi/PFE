/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.facade.impl.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationDetailsData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceResponseData;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.facade.webserviceconfiguration.CustomerWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.populator.WebServiceConfigurationDetailsPopulator;
import de.hybris.platform.addons.wsclientgenerator.populator.WebServiceConfigurationPopulator;
import de.hybris.platform.addons.wsclientgenerator.service.tools.WsInvokeService;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.CustomerWebServiceConfigurationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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

	@Resource(name = "wsInvokeService")
	WsInvokeService wsInvoke;

	@Resource(name = "wsConfPopulator")
	WebServiceConfigurationPopulator wsConfPopulator;

	@Resource(name = "wsConfDetailsPopulator")
	WebServiceConfigurationDetailsPopulator wsConfDetailsPopulator;

	private static final Logger LOG = Logger.getLogger(DefaultCustomerWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final CustomerWebServiceConfigurationModel confModel = customerWsConfService.findCustomerWsConfiguration(id);
		final WebServiceConfigurationDetailsData confData = new WebServiceConfigurationDetailsData();

		wsConfDetailsPopulator.populate(confModel, confData);

		return confData;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<CustomerWebServiceConfigurationModel> customerConfigModels = customerWsConfService.getAllConfigurations();
		final List<WebServiceConfigurationData> customerWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final CustomerWebServiceConfigurationModel cm : customerConfigModels)
		{
			final WebServiceConfigurationData cd = new WebServiceConfigurationData();
			wsConfPopulator.populate(cm, cd);
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
			else if (cm.getMethod().equals(MethodType.PUT))
			{
				params.put("body", queryParams);
				wsResponse = wsInvoke.putSimulationRequest(cm.getUrl(), params, customerWsConfService.prepareHeadersParams(cm),
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
