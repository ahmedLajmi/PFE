/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.OrderWebServiceConfigurationDao;
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
public class DefaultOrderWebServiceConfigurationDao implements OrderWebServiceConfigurationDao
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<OrderWebServiceConfigurationModel> getAllConfigurations()
	{
		final StringBuilder builder = new StringBuilder(
				"SELECT " + OrderWebServiceConfigurationModel.PK + " FROM {OrderWebServiceConfiguration AS s } ");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<OrderWebServiceConfigurationModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public OrderWebServiceConfigurationModel findOrderWsConfiguration(final String id)
	{
		final StringBuilder builder = new StringBuilder("SELECT " + OrderWebServiceConfigurationModel.PK
				+ " FROM {OrderWebServiceConfiguration AS s } WHERE {s." + OrderWebServiceConfigurationModel.PK + "}=" + id);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<OrderWebServiceConfigurationModel> result = flexibleSearchService.search(query);
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
	public OrderWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method)
	{
		final StringBuilder builder = new StringBuilder("SELECT {s:" + OrderWebServiceConfigurationModel.PK
				+ "} FROM {OrderWebServiceConfiguration AS s LEFT JOIN EnumerationValue AS e ON {s."
				+ OrderWebServiceConfigurationModel.METHOD + " } = { e." + EnumerationValue.PK + "} } WHERE { s."
				+ OrderWebServiceConfigurationModel.ENABLE + " } = TRUE AND {e." + EnumerationValue.CODE + " } = '" + method + "'");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<OrderWebServiceConfigurationModel> result = flexibleSearchService.search(query);
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
