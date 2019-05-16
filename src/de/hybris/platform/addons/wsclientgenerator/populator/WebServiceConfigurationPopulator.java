/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.populator;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.model.WebServiceConfigurationModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


/**
 * @author Ahmed-LAJMI
 *
 */
public class WebServiceConfigurationPopulator implements Populator<WebServiceConfigurationModel, WebServiceConfigurationData>
{


	@Override
	public void populate(final WebServiceConfigurationModel source, final WebServiceConfigurationData target)
			throws ConversionException
	{
		target.setId(source.getCode());
		target.setName(source.getName());

	}

}
