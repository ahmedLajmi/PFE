/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.OrderWebServiceConfigurationDao;
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
public class OrderConfigurationInterceptor implements ValidateInterceptor, PrepareInterceptor
{

	private L10NService l10NService;

	@Resource(name = "orderWebServiceConfigurationDao")
	private OrderWebServiceConfigurationDao orderWebServiceConfigurationDao;

	@Override
	public void onValidate(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
		if (model instanceof OrderWebServiceConfigurationModel)
		{

			final OrderWebServiceConfigurationModel orderConfiguration = (OrderWebServiceConfigurationModel) model;

			if (orderConfiguration.getEnable().booleanValue())
			{
				final OrderWebServiceConfigurationModel enabledOrderConfiguration = orderWebServiceConfigurationDao
						.getWsEnabledConfiguration(orderConfiguration.getMethod());
				if (enabledOrderConfiguration != null
						&& !StringUtils.equals(enabledOrderConfiguration.getPk().toString(), orderConfiguration.getPk().toString()))
				{
					throw new InterceptorException(getL10NService().getLocalizedString("unique.configuration"));
				}
			}
			/*
			 * if (customerConfiguration.ge() == null || customerConfiguration.getId().isEmpty()) {
			 * customerConfiguration.setId(UUID.randomUUID().toString()); }
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





