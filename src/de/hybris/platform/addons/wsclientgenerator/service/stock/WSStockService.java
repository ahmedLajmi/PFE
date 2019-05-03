/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.stock;

import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSStockService
{

	public Map<String, Map<String, String>> prepareGetParams(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product);

}
