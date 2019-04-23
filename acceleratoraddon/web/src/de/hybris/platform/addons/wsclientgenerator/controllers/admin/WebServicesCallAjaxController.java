/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.controllers.admin;

import de.hybris.platform.addons.wsclientgenerator.data.CustomerWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.CustomerWebServiceConfigurationFacade;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Ahmed-LAJMI
 *
 */

@RestController
public class WebServicesCallAjaxController
{

	@Resource(name = "customerWebServiceConfigurationFacade")
	private CustomerWebServiceConfigurationFacade customerWebServiceConfigurationFacade;

	@RequestMapping(value = "/wsconfigurations", method = RequestMethod.GET)
	public List<CustomerWebServiceConfigurationData> getWsConfigurations()
	{

		final List<CustomerWebServiceConfigurationData> configurations = customerWebServiceConfigurationFacade
				.getCustomerConfigurations();
		return configurations;
	}

}
