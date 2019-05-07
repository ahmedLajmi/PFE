/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.price;

import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSCalculationService
{
	public Map<String, Map<String, String>> prepareGetParams(final PriceWebServiceConfigurationModel priceConfiguration,
			final ProductModel product);

	public boolean validateCurrency(final String value);
}
