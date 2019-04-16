/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.customer.impl;

import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.CustomerWebServiceConfigurationDao;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class WSCustomerFacade extends DefaultCustomerFacade
{
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "customerWebServiceConfigurationDao")
	private CustomerWebServiceConfigurationDao customerWebServiceConfigurationDao;

	private CustomerWebServiceConfigurationModel customerConfiguration;

	@Override
	public CustomerData getCurrentCustomer()
	{
		final UserModel user = userService.getCurrentUser();

		final CustomerData customer = getCustomerConverter().convert(user);

		customer.setFirstName("test 1 ");
		customer.setLastName("test 2");
		customer.setDisplayUid("test@test.com");
		return customer;
	}

}
