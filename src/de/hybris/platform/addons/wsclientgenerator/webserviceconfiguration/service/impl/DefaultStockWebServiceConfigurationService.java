/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.StockWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.StockWebServiceConfigurationService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultStockWebServiceConfigurationService extends WebServiceConfigurationService
		implements StockWebServiceConfigurationService
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "stockWebServiceConfigurationDao")
	private StockWebServiceConfigurationDao stockWebServiceConfigurationDao;

	@Override
	public List<StockWebServiceConfigurationModel> getAllConfigurations()
	{
		return stockWebServiceConfigurationDao.getAllConfigurations();
	}

	@Override
	public StockWebServiceConfigurationModel findStockWsConfiguration(final String id)
	{
		return stockWebServiceConfigurationDao.findStockWsConfiguration(id);
	}

	@Override
	public StockWebServiceConfigurationModel getWsEnabledConfiguration(final MethodType method)
	{
		return stockWebServiceConfigurationDao.getWsEnabledConfiguration(method);
	}
}
