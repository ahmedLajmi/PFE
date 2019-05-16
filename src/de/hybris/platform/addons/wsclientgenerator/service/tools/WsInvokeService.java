/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.tools;

import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;

import java.util.Map;

import org.springframework.http.ResponseEntity;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface WsInvokeService
{
	public Map<String, String> getRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept) throws InvokeWsException;

	public Map<String, String> postRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException;

	public Map<String, String> putRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException;

	public void deleteRequest(final String url, final Map<String, Map<String, String>> params) throws InvokeWsException;

	public ResponseEntity<String> getSimulationRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept) throws InvokeWsException;

	public ResponseEntity<String> postSimulationRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException;

	public ResponseEntity<String> putSimulationRequest(final String url, final Map<String, Map<String, String>> params,
			final Map<String, String> headersParam, final ResponseType accept, final RequestType contentType)
			throws InvokeWsException, CreateWsRequestException;

}
