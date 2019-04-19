/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.customer;

import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.UserModel;

import java.util.Map;

import org.springframework.util.MultiValueMap;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSCustomerFacade
{
	public Map<String, String> jsonParseResponse(final String response) throws ParseWsResponseException;

	public Map<String, String> xmlParseResponse(final String response) throws ParseWsResponseException;

	public void wsUpdateEmail(final CustomerWebServiceConfigurationModel customerConfiguration, final String newUid);

	public void wsCreateUpdateProfil(final CustomerWebServiceConfigurationModel customerConfiguration,
			final CustomerData customerData);

	public String prepareJSONUpdateEmailRequest(final String newUid,
			final CustomerWebServiceConfigurationModel customerConfiguration) throws CreateWsRequestException;

	public String prepareJSONProfilRequest(final CustomerData customerData,
			final CustomerWebServiceConfigurationModel customerConfiguration) throws CreateWsRequestException;

	public String prepareXMLProfilRequest(final CustomerData customerData,
			final CustomerWebServiceConfigurationModel customerConfiguration) throws CreateWsRequestException;

	public MultiValueMap<String, String> prepareFORMProfilRequest(final CustomerData customerData,
			final CustomerWebServiceConfigurationModel customerConfiguration);

	public String prepareUrl(final CustomerWebServiceConfigurationModel customerConfiguration, final UserModel model);
}
