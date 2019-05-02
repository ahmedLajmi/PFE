/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;

import java.util.List;
import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface OrderWebServiceConfigurationService extends WebServiceConfigurationService
{
	public List<OrderWebServiceConfigurationModel> getAllConfigurations();

	public OrderWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method);

	public OrderWebServiceConfigurationModel findOrderWsConfiguration(String id);

	public Map<String, String> prepareDynamicQueryParameters(final OrderWebServiceConfigurationModel orderConfiguration,
			final OrderModel order, final UserModel user);

	public Map<String, String> prepareDynamicPathParameters(final OrderWebServiceConfigurationModel orderConfiguration,
			final OrderModel order, final UserModel user);

}
