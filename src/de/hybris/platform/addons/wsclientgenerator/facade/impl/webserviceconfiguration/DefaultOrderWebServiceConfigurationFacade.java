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
import de.hybris.platform.addons.wsclientgenerator.facade.webserviceconfiguration.OrderWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.populator.WebServiceConfigurationDetailsPopulator;
import de.hybris.platform.addons.wsclientgenerator.populator.WebServiceConfigurationPopulator;
import de.hybris.platform.addons.wsclientgenerator.service.tools.WsInvokeService;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.OrderWebServiceConfigurationService;
import de.hybris.platform.enumeration.EnumerationService;

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
public class DefaultOrderWebServiceConfigurationFacade implements OrderWebServiceConfigurationFacade
{

	@Resource(name = "orderWebServiceConfigurationService")
	private OrderWebServiceConfigurationService orderWsConfService;

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Resource(name = "wsInvokeService")
	WsInvokeService wsInvoke;

	@Resource(name = "wsConfPopulator")
	WebServiceConfigurationPopulator wsConfPopulator;

	@Resource(name = "wsConfDetailsPopulator")
	WebServiceConfigurationDetailsPopulator wsConfDetailsPopulator;

	private static final Logger LOG = Logger.getLogger(DefaultOrderWebServiceConfigurationFacade.class);


	@Override
	public WebServiceConfigurationDetailsData getConfigurationDetails(final String id)
	{
		final OrderWebServiceConfigurationModel confModel = orderWsConfService.findOrderWsConfiguration(id);
		final WebServiceConfigurationDetailsData confData = new WebServiceConfigurationDetailsData();

		wsConfDetailsPopulator.populate(confModel, confData);

		return confData;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<OrderWebServiceConfigurationModel> orderConfigModels = orderWsConfService.getAllConfigurations();
		final List<WebServiceConfigurationData> orderWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final OrderWebServiceConfigurationModel confModel : orderConfigModels)
		{
			final WebServiceConfigurationData confData = new WebServiceConfigurationData();
			wsConfPopulator.populate(confModel, confData);
			orderWsConfigData.add(confData);
		}
		return orderWsConfigData;
	}

	@Override
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> queryParams,
			final Map<String, String> pathParams)
	{
		final OrderWebServiceConfigurationModel om = orderWsConfService.findOrderWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
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
