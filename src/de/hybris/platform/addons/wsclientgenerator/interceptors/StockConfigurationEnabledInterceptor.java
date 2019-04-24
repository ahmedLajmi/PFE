/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ModeType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.StockWebServiceConfigurationDao;
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
public class StockConfigurationEnabledInterceptor implements ValidateInterceptor, PrepareInterceptor
{

	@Autowired
	private EventService eventService;

	private L10NService l10NService;

	@Resource(name = "stockWebServiceConfigurationDao")
	private StockWebServiceConfigurationDao stockWebServiceConfigurationDao;

	@Override
	public void onValidate(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
		if (model instanceof StockWebServiceConfigurationModel)
		{

			final StockWebServiceConfigurationModel stockConfiguration = (StockWebServiceConfigurationModel) model;

			if (stockConfiguration.getEnable().booleanValue())
			{
				final StockWebServiceConfigurationModel enabledStockConfiguration = stockWebServiceConfigurationDao
						.getWsEnabledConfiguration(stockConfiguration.getMethod());
				if (enabledStockConfiguration != null
						&& !StringUtils.equals(enabledStockConfiguration.getPk().toString(), stockConfiguration.getPk().toString()))
				{
					throw new InterceptorException(getL10NService().getLocalizedString("unique.configuration"));
				}
			}

			if (stockConfiguration.getMode() != null && stockConfiguration.getMode().equals(ModeType.WEBSERVICEWITHNATIVE)
					&& stockConfiguration.getOrder() == null)
			{
				throw new InterceptorException(getL10NService().getLocalizedString("empty.order"));
			}

			if (stockConfiguration.getMethod() != null && stockConfiguration.getMethod().equals(MethodType.GET)
					&& StringUtils.isEmpty(stockConfiguration.getStockKey()))
			{
				throw new InterceptorException(getL10NService().getLocalizedString("empty.key"));
			}

			if (stockConfiguration.getMethod() != null && stockConfiguration.getMethod().equals(MethodType.POST)
					&& stockConfiguration.getContentType() == null)
			{
				throw new InterceptorException(getL10NService().getLocalizedString("empty.contentType"));
			}
			if (stockConfiguration.getAccept().equals(ResponseType.TEXT))
			{
				if (stockConfiguration.getTextSeperator() == null || stockConfiguration.getTextSeperator().isEmpty())
				{
					throw new InterceptorException(getL10NService().getLocalizedString("empty.seperator"));
				}
				else if (stockConfiguration.getTextSeperator().length() > 1)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("invalid.seperator"));
				}
				try
				{
					Integer.parseInt(stockConfiguration.getStockKey());
				}
				catch (final Exception e)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("invalid.key"));
				}
			}
			/*
			 * if (stockConfiguration.getId() == null || stockConfiguration.getId().isEmpty()) {
			 * stockConfiguration.setId(UUID.randomUUID().toString()); }
			 */
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





