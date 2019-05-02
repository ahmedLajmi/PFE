/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.customer;

import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.user.UserModel;

import java.util.Map;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WSCustomerFacade
{

	public void wsUpdateEmail(final String newUid);

	public void wsCreateUpdateProfil(final CustomerData customerData);


	public Map<String, Map<String, String>> prepareGetParams(final UserModel model);


	public Map<String, Map<String, String>> prepareProfilRequest(final CustomerData customerData);

	public Map<String, Map<String, String>> prepareUpdateEmailRequest(final String newUid) throws CreateWsRequestException;
}
