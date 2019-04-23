/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.CustomerWebServiceConfigurationDao;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultCustomerWebServiceConfigurationDao implements CustomerWebServiceConfigurationDao
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<CustomerWebServiceConfigurationModel> getAllConfigurations()
	{
		final StringBuilder builder = new StringBuilder(
				"SELECT " + CustomerWebServiceConfigurationModel.PK + " FROM {CustomerWebServiceConfiguration AS s } ");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<CustomerWebServiceConfigurationModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public CustomerWebServiceConfigurationModel findCustomerWsConfiguration(final String id)
	{
		final StringBuilder builder = new StringBuilder("SELECT " + CustomerWebServiceConfigurationModel.PK
				+ " FROM {CustomerWebServiceConfiguration AS s } WHERE {s." + CustomerWebServiceConfigurationModel.PK + "}=" + id);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<CustomerWebServiceConfigurationModel> result = flexibleSearchService.search(query);
		if (result != null && result.getTotalCount() != 0)
		{
			return result.getResult().get(0);
		}
		else
		{
			return null;
		}
	}

	@Override
	public CustomerWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method)
	{
		final StringBuilder builder = new StringBuilder("SELECT {s:" + CustomerWebServiceConfigurationModel.PK
				+ "} FROM {CustomerWebServiceConfiguration AS s LEFT JOIN EnumerationValue AS e ON {s."
				+ CustomerWebServiceConfigurationModel.METHOD + " } = { e." + EnumerationValue.PK + "} } WHERE { s."
				+ CustomerWebServiceConfigurationModel.ENABLE + " } = TRUE AND {e." + EnumerationValue.CODE + " } = '" + method
				+ "'");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<CustomerWebServiceConfigurationModel> result = flexibleSearchService.search(query);
		if (result != null && result.getTotalCount() != 0)
		{
			return result.getResult().get(0);
		}
		else
		{
			return null;
		}
	}
}
