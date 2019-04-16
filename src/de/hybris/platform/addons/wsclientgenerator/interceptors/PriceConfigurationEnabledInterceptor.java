/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.PriceWebServiceConfigurationDao;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.L10NService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Ahmed-LAJMI
 *
 */
public class PriceConfigurationEnabledInterceptor implements ValidateInterceptor, PrepareInterceptor
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
			if (priceConfiguration.getAccept().equals(ResponseType.TEXT))
			{
				if (priceConfiguration.getTextSeperator() == null || priceConfiguration.getTextSeperator().isEmpty())
				{
					throw new InterceptorException(getL10NService().getLocalizedString("empty.seperator"));
				}
				else if (priceConfiguration.getTextSeperator().length() > 1)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("invalid.seperator"));
				}
				try
				{
					Integer.parseInt(priceConfiguration.getPriceKey());
					if (priceConfiguration.getCurrencyKey() != null)
					{
						Integer.parseInt(priceConfiguration.getCurrencyKey());
					}
				}
				catch (final Exception e)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("invalid.key"));
				}
			}
		}
	}

	@Override
	public void onPrepare(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
		//
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





