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
import de.hybris.platform.addons.wsclientgenerator.facade.webserviceconfiguration.StockWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.populator.WebServiceConfigurationDetailsPopulator;
import de.hybris.platform.addons.wsclientgenerator.populator.WebServiceConfigurationPopulator;
import de.hybris.platform.addons.wsclientgenerator.service.tools.WsInvokeService;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.StockWebServiceConfigurationService;
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
public class DefaultStockWebServiceConfigurationFacade implements StockWebServiceConfigurationFacade
{

	@Resource(name = "stockWebServiceConfigurationService")
	private StockWebServiceConfigurationService stockWsConfService;

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
		final StockWebServiceConfigurationModel confModel = stockWsConfService.findStockWsConfiguration(id);
		final WebServiceConfigurationDetailsData confData = new WebServiceConfigurationDetailsData();

		wsConfDetailsPopulator.populate(confModel, confData);

		return confData;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<StockWebServiceConfigurationModel> stockConfigModels = stockWsConfService.getAllConfigurations();
		final List<WebServiceConfigurationData> stockWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final StockWebServiceConfigurationModel confModel : stockConfigModels)
		{
			final WebServiceConfigurationData confData = new WebServiceConfigurationData();
			wsConfPopulator.populate(confModel, confData);
			stockWsConfigData.add(confData);
		}
		return stockWsConfigData;
	}

	@Override
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> queryParams,
			final Map<String, String> pathParams)
	{
		final StockWebServiceConfigurationModel sm = stockWsConfService.findStockWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
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
