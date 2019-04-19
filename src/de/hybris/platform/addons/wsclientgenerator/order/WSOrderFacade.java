/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.order;

import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSOrderFacade
{
	public List<OrderModel> updateStatuses(final List<OrderModel> orders,
			final OrderWebServiceConfigurationModel orderConfiguration);

	public String WsGetStatusCode(final OrderModel order) throws ParseWsResponseException, InvokeWsException;

	public String jsonParseResponse(final String response) throws ParseWsResponseException;

	public String xmlParseResponse(final String response) throws ParseWsResponseException;

	public String prepareUrl(final OrderWebServiceConfigurationModel orderConfiguration, final OrderModel model);

	public OrderStatus validateStatuses(final String value);

}
