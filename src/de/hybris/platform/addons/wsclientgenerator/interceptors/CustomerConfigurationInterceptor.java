/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.CustomerWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceParameterModel;
import de.hybris.platform.servicelayer.i18n.L10NService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Ahmed-LAJMI
 *
 */
public class CustomerConfigurationInterceptor implements ValidateInterceptor
{

	private L10NService l10NService;

	@Resource(name = "customerWebServiceConfigurationDao")
	private CustomerWebServiceConfigurationDao customerWebServiceConfigurationDao;

	@Override
	public void onValidate(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
		if (model instanceof CustomerWebServiceConfigurationModel)
		{
			final CustomerWebServiceConfigurationModel customerConfiguration = (CustomerWebServiceConfigurationModel) model;

			if (customerConfiguration.getEnable().booleanValue())
			{
				final CustomerWebServiceConfigurationModel enabledCustomerConfiguration = customerWebServiceConfigurationDao
						.getWsEnabledConfiguration(customerConfiguration.getMethod());
				if (enabledCustomerConfiguration != null && !StringUtils.equals(enabledCustomerConfiguration.getPk().toString(),
						customerConfiguration.getPk().toString()))
				{
					throw new InterceptorException(getL10NService().getLocalizedString("unique.configuration"));
				}
			}
			if (customerConfiguration.getMethod().equals(MethodType.POST))
			{
				if (customerConfiguration.getContentType() == null)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("empty.contentType"));
				}
				if (customerConfiguration.getContentType().equals(RequestType.XML) && customerConfiguration.getRootKey() == null)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("empty.rootKey"));
				}
			}
			if (customerConfiguration.getPathParameters() != null && !customerConfiguration.getPathParameters().isEmpty())
			{
				if (StringUtils.countMatches(customerConfiguration.getUrl(), "{") > 0)
				{
					final Collection<CustomerWebServiceParameterModel> pathParameters = customerConfiguration.getPathParameters();
					final int size = pathParameters.size();
					if (StringUtils.countMatches(customerConfiguration.getUrl(), "{") != StringUtils
							.countMatches(customerConfiguration.getUrl(), "}")
							&& StringUtils.countMatches(customerConfiguration.getUrl(), "{") != size)
					{
						throw new InterceptorException(getL10NService().getLocalizedString("invalid.pathParameters"));
					}
					else
					{
						for (final CustomerWebServiceParameterModel param : pathParameters)
						{
							if (!StringUtils.contains(customerConfiguration.getUrl(), "{" + param.getKey() + "}"))
							{
								throw new InterceptorException(getL10NService().getLocalizedString("invalid.pathParameters"));
							}
						}
					}
				}
				else
				{
					throw new InterceptorException(getL10NService().getLocalizedString("invalid.pathParameters"));
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





