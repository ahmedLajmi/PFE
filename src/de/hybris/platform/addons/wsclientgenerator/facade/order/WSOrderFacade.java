/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.facade.order;

import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;
import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSOrderFacade
{
	public List<OrderModel> updateStatuses(final List<OrderModel> orders,
			final OrderWebServiceConfigurationModel orderConfiguration);

	public String WsGetStatusCode(final OrderModel order) throws ParseWsResponseException, InvokeWsException;

	public Map<String, Map<String, String>> prepareGetParams(final OrderModel model);

	public OrderStatus validateStatuses(final String value);

}
