/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.stock;

import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.core.model.product.ProductModel;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSStockService
{
	public Long jsonParseResponse(final String response) throws ParseWsResponseException;

	public Long xmlParseResponse(final String response) throws ParseWsResponseException;

	public String prepareUrl(final StockWebServiceConfigurationModel stockConfiguration, final ProductModel model);

}
