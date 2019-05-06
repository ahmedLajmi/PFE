/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.List;
import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface CustomerWebServiceConfigurationService extends WebServiceConfigurationService
{
	public List<CustomerWebServiceConfigurationModel> getAllConfigurations();

	public CustomerWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method);

	public CustomerWebServiceConfigurationModel findCustomerWsConfiguration(final String id);

	public Map<String, String> prepareDynamicQueryParameters(final CustomerWebServiceConfigurationModel customerConfiguration,
			final CustomerData customer);

	public Map<String, String> prepareDynamicPathParameters(final CustomerWebServiceConfigurationModel customerConfiguration,
			final CustomerData customer);

	public Map<String, String> prepareDynamicQueryParameters(final CustomerWebServiceConfigurationModel customerConfiguration,
			final CustomerData customer, final String newUid);

	public CustomerData prepareCustomer(final CustomerWebServiceConfigurationModel customerConfiguration,
			final Map<String, String> response);

	public String getResponseCode(final CustomerWebServiceConfigurationModel customerConfiguration);

}
