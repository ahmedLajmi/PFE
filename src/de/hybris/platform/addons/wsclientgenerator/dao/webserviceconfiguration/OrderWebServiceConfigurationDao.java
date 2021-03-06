/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface OrderWebServiceConfigurationDao
{
	public List<OrderWebServiceConfigurationModel> getAllConfigurations();

	public OrderWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method);

	public OrderWebServiceConfigurationModel findOrderWsConfiguration(String id);

}
