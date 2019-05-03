/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.dao.impl.webserviceconfiguration;

import de.hybris.platform.addons.wsclientgenerator.dao.webserviceconfiguration.StockWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
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
public class DefaultStockWebServiceConfigurationDao implements StockWebServiceConfigurationDao
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Override
	public List<StockWebServiceConfigurationModel> getAllConfigurations()
	{
		final StringBuilder builder = new StringBuilder(
				"SELECT " + StockWebServiceConfigurationModel.PK + " FROM {StockWebServiceConfiguration AS s } ");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<StockWebServiceConfigurationModel> result = flexibleSearchService.search(query);
		return result.getResult();
	}

	@Override
	public StockWebServiceConfigurationModel findStockWsConfiguration(final String id)
	{
		final StringBuilder builder = new StringBuilder("SELECT " + StockWebServiceConfigurationModel.PK
				+ " FROM {StockWebServiceConfiguration AS s } WHERE {s." + StockWebServiceConfigurationModel.PK + "}=" + id);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<StockWebServiceConfigurationModel> result = flexibleSearchService.search(query);
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
	public StockWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method)
	{
		final StringBuilder builder = new StringBuilder("SELECT {s:" + StockWebServiceConfigurationModel.PK
				+ "} FROM {StockWebServiceConfiguration AS s LEFT JOIN EnumerationValue AS e ON {s."
				+ StockWebServiceConfigurationModel.METHOD + " } = { e." + EnumerationValue.PK + "} } WHERE { s."
				+ StockWebServiceConfigurationModel.ENABLE + " } = TRUE AND {e." + EnumerationValue.CODE + " } = '" + method + "'");

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString());
		final SearchResult<StockWebServiceConfigurationModel> result = flexibleSearchService.search(query);
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
