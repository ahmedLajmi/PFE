/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 19 avr. 2019 10:28:41                       ---
 * ----------------------------------------------------------------
 *  
 * [y] hybris Platform
 *  
 * Copyright (c) 2000-2015 hybris AG
 * All rights reserved.
 *  
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *  
 */
package de.hybris.platform.addons.wsclientgenerator.jalo;

import de.hybris.platform.addons.wsclientgenerator.constants.WsclientgeneratorConstants;
import de.hybris.platform.addons.wsclientgenerator.jalo.CustomerWebServiceConfiguration;
import de.hybris.platform.addons.wsclientgenerator.jalo.CustomerWebServiceParameter;
import de.hybris.platform.addons.wsclientgenerator.jalo.OrderWebServiceConfiguration;
import de.hybris.platform.addons.wsclientgenerator.jalo.OrderWebServiceParameter;
import de.hybris.platform.addons.wsclientgenerator.jalo.PersoWSParam;
import de.hybris.platform.addons.wsclientgenerator.jalo.PriceWebServiceConfiguration;
import de.hybris.platform.addons.wsclientgenerator.jalo.PriceWebServiceParameter;
import de.hybris.platform.addons.wsclientgenerator.jalo.StockWebServiceConfiguration;
import de.hybris.platform.addons.wsclientgenerator.jalo.StockWebServiceParameter;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type <code>WsclientgeneratorManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWsclientgeneratorManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	public CustomerWebServiceConfiguration createCustomerWebServiceConfiguration(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WsclientgeneratorConstants.TC.CUSTOMERWEBSERVICECONFIGURATION );
			return (CustomerWebServiceConfiguration)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomerWebServiceConfiguration : "+e.getMessage(), 0 );
		}
	}
	
	public CustomerWebServiceConfiguration createCustomerWebServiceConfiguration(final Map attributeValues)
	{
		return createCustomerWebServiceConfiguration( getSession().getSessionContext(), attributeValues );
	}
	
	public CustomerWebServiceParameter createCustomerWebServiceParameter(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WsclientgeneratorConstants.TC.CUSTOMERWEBSERVICEPARAMETER );
			return (CustomerWebServiceParameter)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CustomerWebServiceParameter : "+e.getMessage(), 0 );
		}
	}
	
	public CustomerWebServiceParameter createCustomerWebServiceParameter(final Map attributeValues)
	{
		return createCustomerWebServiceParameter( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderWebServiceConfiguration createOrderWebServiceConfiguration(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WsclientgeneratorConstants.TC.ORDERWEBSERVICECONFIGURATION );
			return (OrderWebServiceConfiguration)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderWebServiceConfiguration : "+e.getMessage(), 0 );
		}
	}
	
	public OrderWebServiceConfiguration createOrderWebServiceConfiguration(final Map attributeValues)
	{
		return createOrderWebServiceConfiguration( getSession().getSessionContext(), attributeValues );
	}
	
	public OrderWebServiceParameter createOrderWebServiceParameter(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WsclientgeneratorConstants.TC.ORDERWEBSERVICEPARAMETER );
			return (OrderWebServiceParameter)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating OrderWebServiceParameter : "+e.getMessage(), 0 );
		}
	}
	
	public OrderWebServiceParameter createOrderWebServiceParameter(final Map attributeValues)
	{
		return createOrderWebServiceParameter( getSession().getSessionContext(), attributeValues );
	}
	
	public PersoWSParam createPersoWSParam(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WsclientgeneratorConstants.TC.PERSOWSPARAM );
			return (PersoWSParam)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PersoWSParam : "+e.getMessage(), 0 );
		}
	}
	
	public PersoWSParam createPersoWSParam(final Map attributeValues)
	{
		return createPersoWSParam( getSession().getSessionContext(), attributeValues );
	}
	
	public PriceWebServiceConfiguration createPriceWebServiceConfiguration(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WsclientgeneratorConstants.TC.PRICEWEBSERVICECONFIGURATION );
			return (PriceWebServiceConfiguration)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PriceWebServiceConfiguration : "+e.getMessage(), 0 );
		}
	}
	
	public PriceWebServiceConfiguration createPriceWebServiceConfiguration(final Map attributeValues)
	{
		return createPriceWebServiceConfiguration( getSession().getSessionContext(), attributeValues );
	}
	
	public PriceWebServiceParameter createPriceWebServiceParameter(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WsclientgeneratorConstants.TC.PRICEWEBSERVICEPARAMETER );
			return (PriceWebServiceParameter)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PriceWebServiceParameter : "+e.getMessage(), 0 );
		}
	}
	
	public PriceWebServiceParameter createPriceWebServiceParameter(final Map attributeValues)
	{
		return createPriceWebServiceParameter( getSession().getSessionContext(), attributeValues );
	}
	
	public StockWebServiceConfiguration createStockWebServiceConfiguration(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WsclientgeneratorConstants.TC.STOCKWEBSERVICECONFIGURATION );
			return (StockWebServiceConfiguration)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating StockWebServiceConfiguration : "+e.getMessage(), 0 );
		}
	}
	
	public StockWebServiceConfiguration createStockWebServiceConfiguration(final Map attributeValues)
	{
		return createStockWebServiceConfiguration( getSession().getSessionContext(), attributeValues );
	}
	
	public StockWebServiceParameter createStockWebServiceParameter(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WsclientgeneratorConstants.TC.STOCKWEBSERVICEPARAMETER );
			return (StockWebServiceParameter)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating StockWebServiceParameter : "+e.getMessage(), 0 );
		}
	}
	
	public StockWebServiceParameter createStockWebServiceParameter(final Map attributeValues)
	{
		return createStockWebServiceParameter( getSession().getSessionContext(), attributeValues );
	}
	
	@Override
	public String getName()
	{
		return WsclientgeneratorConstants.EXTENSIONNAME;
	}
	
}
