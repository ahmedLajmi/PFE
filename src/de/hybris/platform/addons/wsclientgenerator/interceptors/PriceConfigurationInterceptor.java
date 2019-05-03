/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.PriceWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.L10NService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Ahmed-LAJMI
 *
 */
public class PriceConfigurationInterceptor implements ValidateInterceptor
{

	@Autowired
	private EventService eventService;

	private L10NService l10NService;

	@Resource(name = "priceWebServiceConfigurationDao")
	private PriceWebServiceConfigurationDao priceWebServiceConfigurationDao;

	@Override
	public void onValidate(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
		if (model instanceof PriceWebServiceConfigurationModel)
		{
			final PriceWebServiceConfigurationModel priceConfiguration = (PriceWebServiceConfigurationModel) model;
			if (priceConfiguration.getEnable().booleanValue())
			{
				final PriceWebServiceConfigurationModel enabledPriceConfig = priceWebServiceConfigurationDao
						.getWsEnabledConfiguration();
				if (enabledPriceConfig != null
						&& !StringUtils.equals(enabledPriceConfig.getPk().toString(), priceConfiguration.getPk().toString()))
				{
					throw new InterceptorException(getL10NService().getLocalizedString("unique.configuration"));
				}
			}


		}
	}

	private L10NService getL10NService()
	{
		return this.l10NService;
	}

	@Autowired
	public void setL10NService(final L10NService l10NService)
	{
		this.l10NService = l10NService;
	}

}





