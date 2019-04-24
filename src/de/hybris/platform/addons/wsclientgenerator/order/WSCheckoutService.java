/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.order;

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

	public MultiValueMap<String, String> prepareRequest(final AbstractOrderEntryModel entry);

}
