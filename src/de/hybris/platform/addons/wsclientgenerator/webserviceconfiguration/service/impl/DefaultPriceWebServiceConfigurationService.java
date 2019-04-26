/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.impl;

import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.PriceWebServiceConfigurationDao;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.PriceWebServiceConfigurationService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultPriceWebServiceConfigurationService extends AbstractWebServiceConfigurationService
		implements PriceWebServiceConfigurationService
{

	@Resource(name = "flexibleSearchService")
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "priceWebServiceConfigurationDao")
	private PriceWebServiceConfigurationDao priceWebServiceConfigurationDao;

	@Override
	public List<PriceWebServiceConfigurationModel> getAllConfigurations()
	{
		return priceWebServiceConfigurationDao.getAllConfigurations();
	}

	@Override
	public PriceWebServiceConfigurationModel getWsEnabledConfiguration()
	{
		return priceWebServiceConfigurationDao.getWsEnabledConfiguration();
	}

	@Override
	public PriceWebServiceConfigurationModel findPriceWsConfiguration(final String id)
	{
		return priceWebServiceConfigurationDao.findPriceWsConfiguration(id);
	}

}
