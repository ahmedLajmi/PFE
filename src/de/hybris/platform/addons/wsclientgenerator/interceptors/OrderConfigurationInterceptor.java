/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.OrderWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
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
public class OrderConfigurationInterceptor implements ValidateInterceptor
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
			if (orderConfiguration.getMethod().equals(MethodType.POST))
			{
				if (orderConfiguration.getContentType() == null)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("empty.contentType"));
				}
				else if (orderConfiguration.getContentType().equals(RequestType.XML) && orderConfiguration.getRootKey() == null)
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





