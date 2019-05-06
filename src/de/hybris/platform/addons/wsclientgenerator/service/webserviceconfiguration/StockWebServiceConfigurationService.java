/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;

import java.util.List;
import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface StockWebServiceConfigurationService extends WebServiceConfigurationService
{
	public List<StockWebServiceConfigurationModel> getAllConfigurations();

	public StockWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method);

	public StockWebServiceConfigurationModel findStockWsConfiguration(String id);

	public Map<String, String> prepareDynamicQueryParameters(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product, final UserModel user);

	public Map<String, String> prepareDynamicPathParameters(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product, final UserModel user);

	public Map<String, String> prepareDynamicQueryParameters(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product, final UserModel user, String orderCode, String quantity);

	public Map<String, String> prepareDynamicPathParameters(final StockWebServiceConfigurationModel stockConfiguration,
			final ProductModel product, final UserModel user, String orderCode);

	public String getResponseCode(final StockWebServiceConfigurationModel stockConfiguration);

}
