/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.facade.impl.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationDetailsData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceResponseData;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.facade.webserviceconfiguration.PriceWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.populator.WebServiceConfigurationDetailsPopulator;
import de.hybris.platform.addons.wsclientgenerator.populator.WebServiceConfigurationPopulator;
import de.hybris.platform.addons.wsclientgenerator.service.tools.WsInvokeService;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.PriceWebServiceConfigurationService;
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
public class DefaultPriceWebServiceConfigurationFacade implements PriceWebServiceConfigurationFacade
{

	@Resource(name = "priceWebServiceConfigurationService")
	private PriceWebServiceConfigurationService priceWsConfService;

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
		final PriceWebServiceConfigurationModel confModel = priceWsConfService.findPriceWsConfiguration(id);
		final WebServiceConfigurationDetailsData confData = new WebServiceConfigurationDetailsData();

		wsConfDetailsPopulator.populate(confModel, confData);

		return confData;
	}

	@Override
	public List<WebServiceConfigurationData> getAllConfigurations()
	{
		final List<PriceWebServiceConfigurationModel> priceConfigModels = priceWsConfService.getAllConfigurations();
		final List<WebServiceConfigurationData> priceWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final PriceWebServiceConfigurationModel confModel : priceConfigModels)
		{
			final WebServiceConfigurationData confData = new WebServiceConfigurationData();
			wsConfPopulator.populate(confModel, confData);
			priceWsConfigData.add(confData);
		}
		return priceWsConfigData;
	}

	@Override
	public WebServiceResponseData wsConfigurationCall(final String id, final Map<String, String> queryParams,
			final Map<String, String> pathParams)
	{
		final PriceWebServiceConfigurationModel pm = priceWsConfService.findPriceWsConfiguration(id);
		final WebServiceResponseData response = new WebServiceResponseData();
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
