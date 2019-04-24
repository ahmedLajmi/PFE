/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.impl;

import de.hybris.platform.addons.wsclientgenerator.data.OrderWebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceParameterData;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.facade.OrderWebServiceConfigurationFacade;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.service.OrderWebServiceConfigurationService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class DefaultOrderWebServiceConfigurationFacade implements OrderWebServiceConfigurationFacade
{

	@Resource(name = "orderWebServiceConfigurationService")
	private OrderWebServiceConfigurationService orderWebServiceConfigurationService;

	@Override
	public OrderWebServiceConfigurationData getOrderConfigurationDetails(final String id)
	{
		final OrderWebServiceConfigurationModel cm = orderWebServiceConfigurationService.findOrderWsConfiguration(id);
		final OrderWebServiceConfigurationData cd = new OrderWebServiceConfigurationData();
		cd.setId(cm.getPk().toString());
		cd.setName(cm.getName());
		cd.setUrl(cm.getUrl());
		cd.setDescription(cm.getDescription());
		cd.setMethod(cm.getMethod().toString());
		cd.setEnable(cm.getEnable().booleanValue());
		final List<WebServiceParameterData> params = new ArrayList<>();
		for (final OrderWebServiceParameterModel param : cm.getParameters())
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
	public List<WebServiceConfigurationData> getAllOrderConfigurations()
	{
		final List<OrderWebServiceConfigurationModel> orderConfigModels = orderWebServiceConfigurationService
				.getAllConfigurations();
		final List<WebServiceConfigurationData> orderWsConfigData = new ArrayList<WebServiceConfigurationData>();
		for (final OrderWebServiceConfigurationModel cm : orderConfigModels)
		{
			final WebServiceConfigurationData cd = new WebServiceConfigurationData();
			cd.setId(cm.getPk().toString());
			cd.setName(cm.getName());
			orderWsConfigData.add(cd);
		}
		return orderWsConfigData;
	}



}
