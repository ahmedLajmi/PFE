/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.impl;

import de.hybris.platform.addons.wsclientgenerator.data.StockWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceParameterData;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.StockWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.StockWebServiceConfigurationService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultStockWebServiceConfigurationFacade implements StockWebServiceConfigurationFacade
{

	@Resource(name = "stockWebServiceConfigurationService")
	private StockWebServiceConfigurationService stockWebServiceConfigurationService;

	@Override
	public StockWebServiceConfigurationData getStockConfigurationDetails(final String id)
	{
		final StockWebServiceConfigurationModel cm = stockWebServiceConfigurationService.findStockWsConfiguration(id);
		final StockWebServiceConfigurationData cd = new StockWebServiceConfigurationData();
		cd.setId(cm.getPk().toString());
		cd.setName(cm.getName());
		cd.setUrl(cm.getUrl());
		cd.setDescription(cm.getDescription());
		cd.setMethod(cm.getMethod().toString());
		cd.setEnable(cm.getEnable().booleanValue());
		final List<WebServiceParameterData> params = new ArrayList<>();
		for (final StockWebServiceParameterModel param : cm.getParameters())
		{
			final WebServiceParameterData pd = new WebServiceParameterData();
			pd.setKey(param.getKey());
			pd.setValue(param.getValue().toString());
			params.add(pd);
		}
		cd.setPersoData(params);
		return cd;
	}

	@Override
	public List<WebServiceConfigurationData> getAllStockConfigurations()
	{
		final List<StockWebServiceConfigurationModel> stockConfigModels = stockWebServiceConfigurationService
				.getAllConfigurations();
		final List<WebServiceConfigurationData> stockWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final StockWebServiceConfigurationModel cm : stockConfigModels)
		{
			final WebServiceConfigurationData cd = new WebServiceConfigurationData();
			cd.setId(cm.getPk().toString());
			cd.setName(cm.getName());
			stockWsConfigData.add(cd);
		}
		return stockWsConfigData;
	}

}
