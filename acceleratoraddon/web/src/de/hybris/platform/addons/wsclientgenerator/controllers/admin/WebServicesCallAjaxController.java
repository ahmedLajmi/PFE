/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.controllers.admin;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationDetailsData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceResponseData;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.CustomerWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.OrderWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.PriceWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.StockWebServiceConfigurationFacade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Ahmed-LAJMI
 *
 */

@RestController
public class WebServicesCallAjaxController
{

	private static final String PRICE_KEY = "price";
	private static final String CUSTOMER_KEY = "customer";
	private static final String STOCK_KEY = "stock";
	private static final String ORDER_KEY = "order";

	@Resource(name = "customerWebServiceConfigurationFacade")
	private CustomerWebServiceConfigurationFacade customerWebServiceConfigurationFacade;

	@Resource(name = "orderWebServiceConfigurationFacade")
	private OrderWebServiceConfigurationFacade orderWebServiceConfigurationFacade;

	@Resource(name = "priceWebServiceConfigurationFacade")
	private PriceWebServiceConfigurationFacade priceWebServiceConfigurationFacade;

	@Resource(name = "stockWebServiceConfigurationFacade")
	private StockWebServiceConfigurationFacade stockWebServiceConfigurationFacade;


	@RequestMapping(value = "/wsAllConfigurations", method = RequestMethod.POST)
	public List<WebServiceConfigurationData> getAllWsConfigurations(@RequestParam(defaultValue = PRICE_KEY)
	final String functionality)
	{
		List<WebServiceConfigurationData> configurations = null;
		if (StringUtils.equals(functionality, PRICE_KEY))
		{
			configurations = priceWebServiceConfigurationFacade.getAllConfigurations();
		}
		else if (StringUtils.equals(functionality, CUSTOMER_KEY))
		{
			configurations = customerWebServiceConfigurationFacade.getAllConfigurations();
		}
		else if (StringUtils.equals(functionality, STOCK_KEY))
		{
			configurations = stockWebServiceConfigurationFacade.getAllConfigurations();
		}
		else if (StringUtils.equals(functionality, ORDER_KEY))
		{
			configurations = orderWebServiceConfigurationFacade.getAllConfigurations();
		}

		return configurations;
	}

	@RequestMapping(value = "/wsConfigurationDetails", method = RequestMethod.POST)
	public WebServiceConfigurationDetailsData getWsConfigurationDetails(@RequestParam
	final String functionality, @RequestParam
	final String configurationID)
	{
		if (StringUtils.equals(functionality, PRICE_KEY))
		{
			return priceWebServiceConfigurationFacade.getConfigurationDetails(configurationID);
		}
		else if (StringUtils.equals(functionality, CUSTOMER_KEY))
		{
			return customerWebServiceConfigurationFacade.getConfigurationDetails(configurationID);
		}
		else if (StringUtils.equals(functionality, STOCK_KEY))
		{
			return stockWebServiceConfigurationFacade.getConfigurationDetails(configurationID);
		}
		else if (StringUtils.equals(functionality, ORDER_KEY))
		{
			return orderWebServiceConfigurationFacade.getConfigurationDetails(configurationID);
		}
		return null;
	}

	@RequestMapping(value = "/wsConfigurationCall", method = RequestMethod.POST)
	public WebServiceResponseData wsConfigurationCall(@RequestParam
	final Map<String, String> params)
	{
		params.remove("CSRFToken");
		final String functionality = params.get("functionality");
		final String configurationID = params.get("configurationID");
		params.remove("functionality");
		params.remove("configurationID");
		if (functionality != null && configurationID != null)
		{
			if (StringUtils.equals(functionality, PRICE_KEY))
			{
				return priceWebServiceConfigurationFacade.wsConfigurationCall(configurationID, params);
			}
			else if (StringUtils.equals(functionality, CUSTOMER_KEY))
			{
				return customerWebServiceConfigurationFacade.wsConfigurationCall(configurationID, params);
			}
			else if (StringUtils.equals(functionality, STOCK_KEY))
			{
				return stockWebServiceConfigurationFacade.wsConfigurationCall(configurationID, params);
			}
			else if (StringUtils.equals(functionality, ORDER_KEY))
			{
				return orderWebServiceConfigurationFacade.wsConfigurationCall(configurationID, params);
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}




}
