/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.impl;

import de.hybris.platform.addons.wsclientgenerator.data.PriceWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceParameterData;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.PriceWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.PriceWebServiceConfigurationService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultPriceWebServiceConfigurationFacade implements PriceWebServiceConfigurationFacade
{

	@Resource(name = "priceWebServiceConfigurationService")
	private PriceWebServiceConfigurationService priceWebServiceConfigurationService;

	@Override
	public PriceWebServiceConfigurationData getPriceConfigurationDetails(final String id)
	{
		final PriceWebServiceConfigurationModel cm = priceWebServiceConfigurationService.findPriceWsConfiguration(id);
		final PriceWebServiceConfigurationData cd = new PriceWebServiceConfigurationData();
		cd.setId(cm.getPk().toString());
		cd.setName(cm.getName());
		cd.setUrl(cm.getUrl());
		cd.setDescription(cm.getDescription());
		cd.setMethod(cm.getMethod().toString());
		cd.setEnable(cm.getEnable().booleanValue());
		final List<WebServiceParameterData> params = new ArrayList<>();
		for (final PriceWebServiceParameterModel param : cm.getParameters())
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
	public List<WebServiceConfigurationData> getAllPriceConfigurations()
	{
		final List<PriceWebServiceConfigurationModel> priceConfigModels = priceWebServiceConfigurationService
				.getAllConfigurations();
		final List<WebServiceConfigurationData> priceWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final PriceWebServiceConfigurationModel cm : priceConfigModels)
		{
			final WebServiceConfigurationData cd = new WebServiceConfigurationData();
			cd.setId(cm.getPk().toString());
			cd.setName(cm.getName());
			priceWsConfigData.add(cd);
		}
		return priceWsConfigData;
	}

}
