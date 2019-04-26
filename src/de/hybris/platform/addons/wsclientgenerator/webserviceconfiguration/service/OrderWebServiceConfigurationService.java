/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface OrderWebServiceConfigurationService extends WebServiceConfigurationService
{
	public List<OrderWebServiceConfigurationModel> getAllConfigurations();

	public OrderWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method);

	public OrderWebServiceConfigurationModel findOrderWsConfiguration(String id);

}
