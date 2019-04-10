/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.impl;

import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.PriceWebServiceConfigurationDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultPriceWebServiceConfigurationDao implements PriceWebServiceConfigurationDao
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;


	@Override
	public List<PriceWebServiceConfigurationModel> getAllConfigurations()
	{
		final StringBuilder builder = new StringBuilder(
				"SELECT " + PriceWebServiceConfigurationModel.PK + " FROM {PriceWebServiceConfiguration AS p } ");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<PriceWebServiceConfigurationModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public PriceWebServiceConfigurationModel getWsEnabledConfiguration()
	{
		final StringBuilder builder = new StringBuilder("SELECT " + PriceWebServiceConfigurationModel.PK
				+ " FROM {PriceWebServiceConfiguration AS p } WHERE {p.enable} = true");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<PriceWebServiceConfigurationModel> result = flexibleSearchService.search(query);
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
	public Integer getCountWsEnabledConfiguration()
	{
		final StringBuilder builder = new StringBuilder("SELECT count(" + PriceWebServiceConfigurationModel.PK
				+ ") FROM {PriceWebServiceConfiguration AS p } WHERE {p.enable} = true");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<Integer> result = flexibleSearchService.search(query);
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
