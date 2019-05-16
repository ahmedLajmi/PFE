/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.tools;

import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.ParseWsResponseException;

import java.util.Map;

import org.springframework.util.MultiValueMap;


/**
 * @author Ahmed-LAJMI
 *
 */
public interface Utilities
{
	public MultiValueMap<String, String> mapToMultiValue(final Map<String, String> map);

	public String prepareJSONRequest(final Map<String, String> body) throws CreateWsRequestException;

	public String prepareXMLRequest(final Map<String, String> body) throws CreateWsRequestException;

	public Map<String, String> jsonParseResponse(final String response) throws ParseWsResponseException;

	public Map<String, String> xmlParseResponse(String response) throws ParseWsResponseException;
}
