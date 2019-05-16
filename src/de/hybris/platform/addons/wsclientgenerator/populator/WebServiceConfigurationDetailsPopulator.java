/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.populator;

import de.hybris.platform.addons.wsclientgenerator.data.WebServiceConfigurationDetailsData;
import de.hybris.platform.addons.wsclientgenerator.data.WebServiceParameterData;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.CustomerWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.HeaderWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.OrderWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.PriceWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.model.WebServiceConfigurationModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;


/**
 * @author Ahmed-LAJMI
 *
 */
public class WebServiceConfigurationDetailsPopulator
		implements Populator<WebServiceConfigurationModel, WebServiceConfigurationDetailsData>
{

	@Resource(name = "enumerationService")
	private EnumerationService enumerationService;

	@Override
	public void populate(final WebServiceConfigurationModel source, final WebServiceConfigurationDetailsData target)
			throws ConversionException
	{
		List<WebServiceParameterData> pathParams = new ArrayList<>();
		List<WebServiceParameterData> queryParams = new ArrayList<>();

		target.setId(source.getPk().toString());
		target.setName(source.getName());
		target.setUrl(source.getUrl());
		target.setDescription(source.getDescription());
		target.setEnable(source.getEnable().booleanValue());
		target.setMethod(enumerationService.getEnumerationName(source.getMethod()));
		target.setAccept(enumerationService.getEnumerationName(source.getAccept()));

		if (source.getContentType() != null)
		{
			target.setContentType(enumerationService.getEnumerationName(source.getContentType()));
		}

		target.setPersoParameters(populatePersoParam(source.getPersonalisedParameters()));
		target.setHeaderParameters(populateHeaderParam(source.getHeadersParameters()));
		target.setSecurityParameters(populateSecurityParam(source.getLogin(), source.getPassword()));

		if (source instanceof CustomerWebServiceConfigurationModel)
		{
			final CustomerWebServiceConfigurationModel conf = (CustomerWebServiceConfigurationModel) source;
			queryParams = populateCustomerParam(conf.getParameters());
			pathParams = populateCustomerParam(conf.getPathParameters());
		}
		else if (source instanceof PriceWebServiceConfigurationModel)
		{
			final PriceWebServiceConfigurationModel conf = (PriceWebServiceConfigurationModel) source;
			queryParams = populatePriceParam(conf.getParameters());
			pathParams = populatePriceParam(conf.getPathParameters());
		}
		else if (source instanceof OrderWebServiceConfigurationModel)
		{
			final OrderWebServiceConfigurationModel conf = (OrderWebServiceConfigurationModel) source;
			queryParams = populateOrderParam(conf.getParameters());
			pathParams = populateOrderParam(conf.getPathParameters());
		}
		else if (source instanceof StockWebServiceConfigurationModel)
		{
			final StockWebServiceConfigurationModel conf = (StockWebServiceConfigurationModel) source;
			queryParams = populateStockParam(conf.getParameters());
			pathParams = populateStockParam(conf.getPathParameters());
		}

		target.setQueryParameters(queryParams);
		target.setPathParameters(pathParams);



	}



	protected List<WebServiceParameterData> populateStockParam(final Collection<StockWebServiceParameterModel> source)
	{
		final List<WebServiceParameterData> target = new ArrayList<>();
		for (final StockWebServiceParameterModel param : source)
		{
			final WebServiceParameterData paramData = new WebServiceParameterData();
			paramData.setKey(param.getKey());
			paramData.setValue(param.getValue().toString());
			target.add(paramData);
		}
		return target;
	}

	protected List<WebServiceParameterData> populatePriceParam(final Collection<PriceWebServiceParameterModel> source)
	{
		final List<WebServiceParameterData> target = new ArrayList<>();
		for (final PriceWebServiceParameterModel param : source)
		{
			final WebServiceParameterData paramData = new WebServiceParameterData();
			paramData.setKey(param.getKey());
			paramData.setValue(param.getValue().toString());
			target.add(paramData);
		}
		return target;
	}

	protected List<WebServiceParameterData> populateOrderParam(final Collection<OrderWebServiceParameterModel> source)
	{
		final List<WebServiceParameterData> target = new ArrayList<>();
		for (final OrderWebServiceParameterModel param : source)
		{
			final WebServiceParameterData paramData = new WebServiceParameterData();
			paramData.setKey(param.getKey());
			paramData.setValue(param.getValue().toString());
			target.add(paramData);
		}
		return target;
	}

	protected List<WebServiceParameterData> populateCustomerParam(final Collection<CustomerWebServiceParameterModel> source)
	{
		final List<WebServiceParameterData> target = new ArrayList<>();
		for (final CustomerWebServiceParameterModel param : source)
		{
			final WebServiceParameterData paramData = new WebServiceParameterData();
			paramData.setKey(param.getKey());
			paramData.setValue(param.getValue().toString());
			target.add(paramData);
		}
		return target;
	}

	protected List<WebServiceParameterData> populatePersoParam(final Collection<PersoWSParamModel> source)
	{
		final List<WebServiceParameterData> target = new ArrayList<>();
		for (final PersoWSParamModel param : source)
		{
			final WebServiceParameterData paramData = new WebServiceParameterData();
			paramData.setKey(param.getKey());
			paramData.setValue(param.getValue().toString());
			target.add(paramData);
		}
		return target;
	}

	protected List<WebServiceParameterData> populateHeaderParam(final Collection<HeaderWSParamModel> source)
	{
		final List<WebServiceParameterData> target = new ArrayList<>();
		for (final HeaderWSParamModel param : source)
		{
			final WebServiceParameterData paramData = new WebServiceParameterData();
			paramData.setKey(param.getKey());
			paramData.setValue(param.getValue().toString());
			target.add(paramData);
		}
		return target;
	}

	protected List<WebServiceParameterData> populateSecurityParam(final String login, final String password)
	{
		if (login != null)
		{
			final List<WebServiceParameterData> target = new ArrayList<>();
			final WebServiceParameterData loginParam = new WebServiceParameterData();
			final WebServiceParameterData passwordParam = new WebServiceParameterData();
			loginParam.setKey("login");
			loginParam.setValue(login);
			passwordParam.setKey("password");
			passwordParam.setValue(password);
			target.add(loginParam);
			target.add(passwordParam);
			return target;
		}
		else
		{
			return null;
		}
	}

}
