/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.order;

import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;

import org.springframework.util.MultiValueMap;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSCheckoutService
{
	public void wsTreatement(final OrderModel model);

	public String prepareXMLRequest(final AbstractOrderEntryModel entry) throws CreateWsRequestException;

	public String prepareJSONRequest(final AbstractOrderEntryModel entry) throws CreateWsRequestException;

	public MultiValueMap<String, String> prepareFORMRequest(final AbstractOrderEntryModel entry);

}
