/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.controllers.admin;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.CustomerWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.OrderWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.PriceWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.StockWebServiceConfigurationFacade;

import java.util.List;

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

	//@Resource(name = "stockWebServiceConfigurationFacade")
	private StockWebServiceConfigurationFacade stockWebServiceConfigurationFacade;

	@RequestMapping(value = "/wsconfigurations", method = RequestMethod.GET)
	public List<WebServiceConfigurationData> getWsConfigurations()
	{

		final List<WebServiceConfigurationData> configurations = customerWebServiceConfigurationFacade
				.getAllCustomerConfigurations();
		return configurations;
	}

	@RequestMapping(value = "/wsallconfigurations", method = RequestMethod.POST)
	public List<WebServiceConfigurationData> getAllWsConfigurations(@RequestParam(defaultValue = PRICE_KEY)
	final String functionality)
	{
		List<WebServiceConfigurationData> configurations = null;
		if (StringUtils.equals(functionality, PRICE_KEY))
		{
			configurations = priceWebServiceConfigurationFacade.getAllPriceConfigurations();
		}
		else if (StringUtils.equals(functionality, CUSTOMER_KEY))
		{
			configurations = customerWebServiceConfigurationFacade.getAllCustomerConfigurations();
		}
		else if (StringUtils.equals(functionality, STOCK_KEY))
		{
			configurations = stockWebServiceConfigurationFacade.getAllStockConfigurations();
		}
		else if (StringUtils.equals(functionality, ORDER_KEY))
		{
			configurations = orderWebServiceConfigurationFacade.getAllOrderConfigurations();
		}

		return configurations;
	}



}
