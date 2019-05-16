/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.StockWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.servicelayer.i18n.L10NService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import java.util.Collection;
import java.util.UUID;

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
				if (stockConfiguration.getContentType().equals(RequestType.XML) && stockConfiguration.getRootKey() == null)
				{
					throw new InterceptorException(getL10NService().getLocalizedString("empty.rootKey"));
				}
			}
			if (stockConfiguration.getPathParameters() != null && !stockConfiguration.getPathParameters().isEmpty())
			{
				if (StringUtils.countMatches(stockConfiguration.getUrl(), "{") > 0)
				{
					final Collection<StockWebServiceParameterModel> pathParameters = stockConfiguration.getPathParameters();
					final int size = pathParameters.size();
					if (StringUtils.countMatches(stockConfiguration.getUrl(), "{") != StringUtils
							.countMatches(stockConfiguration.getUrl(), "}")
							&& StringUtils.countMatches(stockConfiguration.getUrl(), "{") != size)
					{
						throw new InterceptorException(getL10NService().getLocalizedString("invalid.pathParameters"));
					}
					else
					{
						for (final StockWebServiceParameterModel param : pathParameters)
						{
							if (!StringUtils.contains(stockConfiguration.getUrl(), "{" + param.getKey() + "}"))
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
			if (stockConfiguration.getCode() == null || StringUtils.isEmpty(stockConfiguration.getCode()))
			{
				stockConfiguration.setCode(UUID.randomUUID().toString());
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





