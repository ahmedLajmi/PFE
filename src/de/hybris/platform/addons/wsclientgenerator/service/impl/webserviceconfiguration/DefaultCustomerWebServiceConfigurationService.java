/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.service.impl.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.CustomerWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.enums.CustomerParameter;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceResponseModel;
import de.hybris.platform.addons.wsclientgenerator.service.webserviceconfiguration.CustomerWebServiceConfigurationService;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultCustomerWebServiceConfigurationService extends AbstractWebServiceConfigurationService
		implements CustomerWebServiceConfigurationService
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "customerWebServiceConfigurationDao")
	private CustomerWebServiceConfigurationDao customerWebServiceConfigurationDao;

	@Override
	public List<CustomerWebServiceConfigurationModel> getAllConfigurations()
	{
		return customerWebServiceConfigurationDao.getAllConfigurations();
	}

	@Override
	public CustomerWebServiceConfigurationModel findCustomerWsConfiguration(final String id)
	{
		return customerWebServiceConfigurationDao.findCustomerWsConfiguration(id);
	}

	@Override
	public CustomerWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method)
	{
		return customerWebServiceConfigurationDao.getWsEnabledConfiguration(method);
	}

	@Override
	public Map<String, String> prepareDynamicQueryParameters(final CustomerWebServiceConfigurationModel customerConfiguration,
			final CustomerData customer)
	{
		final Map<String, String> params = new HashMap<String, String>();
		for (final CustomerWebServiceParameterModel param : customerConfiguration.getParameters())
		{
			final String value = callGetter(param.getValue().getCode(), customer);
			if (value != null)
			{
				params.put(param.getKey(), value);
			}
		}
		return params;
	}

	@Override
	public Map<String, String> prepareDynamicQueryParameters(final CustomerWebServiceConfigurationModel customerConfiguration,
			final CustomerData customer, final String newUid)
	{
		final Map<String, String> params = new HashMap<String, String>();
		for (final CustomerWebServiceParameterModel param : customerConfiguration.getParameters())
		{
			if (newUid != null && !newUid.isEmpty() && param.getValue().equals(CustomerParameter.NEWEMAIL))
			{
				params.put(param.getKey(), newUid);
			}
			else if (param.getValue().equals(CustomerParameter.OLDEMAIL))
			{
				params.put(param.getKey(), customer.getUid());
			}
			else
			{
				final String value = callGetter(param.getValue().getCode(), customer);
				if (value != null)
				{
					params.put(param.getKey(), value);
				}
			}

		}
		return params;
	}

	@Override
	public Map<String, String> prepareDynamicPathParameters(final CustomerWebServiceConfigurationModel customerConfiguration,
			final CustomerData customer)
	{
		final Map<String, String> params = new HashMap<String, String>();
		for (final CustomerWebServiceParameterModel param : customerConfiguration.getPathParameters())
		{
			final String value = callGetter(param.getValue().getCode(), customer);
			if (value != null)
			{
				params.put(param.getKey(), value);
			}
		}
		return params;
	}

	@Override
	public CustomerData prepareCustomer(final CustomerWebServiceConfigurationModel customerConfiguration,
			final Map<String, String> response)
	{
		final Collection<CustomerWebServiceResponseModel> mapping = customerConfiguration.getResponseMapping();
		final CustomerData customer = new CustomerData();
		for (final CustomerWebServiceResponseModel item : mapping)
		{
			callSetter(item.getValue().getCode(), customer, response.get(item.getKey()));
		}
		return customer;
	}
}
