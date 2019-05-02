/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.order;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSCheckoutService
{
	public void wsTreatement(final OrderModel model);

	public Map<String, Map<String, String>> prepareRequestData(final ProductModel product, final String orderCode,
			String quantity);

}
