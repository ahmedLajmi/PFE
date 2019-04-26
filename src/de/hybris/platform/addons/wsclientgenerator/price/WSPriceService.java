/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.price;

import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSPriceService
{
	public Map<String, String> jsonParseResponse(final String response) throws ParseWsResponseException;

	public Map<String, String> xmlParseResponse(final String response) throws ParseWsResponseException;

	public Map<String, String> textParseResponse(final String response, final PriceWebServiceConfigurationModel priceConfiguration)
			throws ParseWsResponseException;

	public Map<String, String> prepareGetRequestParams(final PriceWebServiceConfigurationModel priceConfiguration,
			final ProductModel model);

	public boolean validateCurrency(final String currency);

}
