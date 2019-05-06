/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;

import java.util.List;
import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface PriceWebServiceConfigurationService extends WebServiceConfigurationService
{
	public List<PriceWebServiceConfigurationModel> getAllConfigurations();

	public PriceWebServiceConfigurationModel getWsEnabledConfiguration();

	public PriceWebServiceConfigurationModel findPriceWsConfiguration(String id);

	public Map<String, String> prepareDynamicQueryParameters(final PriceWebServiceConfigurationModel priceConfiguration,
			final ProductModel product, UserModel user);

	public Map<String, String> prepareDynamicPathParameters(final PriceWebServiceConfigurationModel priceConfiguration,
			final ProductModel product, UserModel user);

	public String getResponseCode(final PriceWebServiceConfigurationModel priceConfiguration);
}
