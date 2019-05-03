/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.StockWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
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
public class StockConfigurationInterceptor implements ValidateInterceptor
{

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
			if (stockConfiguration.getMethod().equals(MethodType.POST))
			{
				if (stockConfiguration.getContentType() == null)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("empty.contentType"));
				}
				else if (stockConfiguration.getContentType().equals(RequestType.XML) && stockConfiguration.getRootKey() == null)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("empty.rootKey"));
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





