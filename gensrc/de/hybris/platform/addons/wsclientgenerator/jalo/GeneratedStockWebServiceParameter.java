/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2 mai 2019 13:28:40                         ---
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
import de.hybris.platform.addons.wsclientgenerator.jalo.StockWebServiceConfiguration;
import de.hybris.platform.addons.wsclientgenerator.jalo.WebServiceParameter;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.BidirectionalOneToManyHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem StockWebServiceParameter}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedStockWebServiceParameter extends WebServiceParameter
{
	/** Qualifier of the <code>StockWebServiceParameter.value</code> attribute **/
	public static final String VALUE = "value";
	/** Qualifier of the <code>StockWebServiceParameter.configuration</code> attribute **/
	public static final String CONFIGURATION = "configuration";
	/** Qualifier of the <code>StockWebServiceParameter.stockConfiguration</code> attribute **/
	public static final String STOCKCONFIGURATION = "stockConfiguration";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n CONFIGURATION's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedStockWebServiceParameter> CONFIGURATIONHANDLER = new BidirectionalOneToManyHandler<GeneratedStockWebServiceParameter>(
	WsclientgeneratorConstants.TC.STOCKWEBSERVICEPARAMETER,
	false,
	"configuration",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n STOCKCONFIGURATION's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedStockWebServiceParameter> STOCKCONFIGURATIONHANDLER = new BidirectionalOneToManyHandler<GeneratedStockWebServiceParameter>(
	WsclientgeneratorConstants.TC.STOCKWEBSERVICEPARAMETER,
	false,
	"stockConfiguration",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(WebServiceParameter.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(VALUE, AttributeMode.INITIAL);
		tmp.put(CONFIGURATION, AttributeMode.INITIAL);
		tmp.put(STOCKCONFIGURATION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceParameter.configuration</code> attribute.
	 * @return the configuration
	 */
	public StockWebServiceConfiguration getConfiguration(final SessionContext ctx)
	{
		return (StockWebServiceConfiguration)getProperty( ctx, CONFIGURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceParameter.configuration</code> attribute.
	 * @return the configuration
	 */
	public StockWebServiceConfiguration getConfiguration()
	{
		return getConfiguration( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceParameter.configuration</code> attribute. 
	 * @param value the configuration
	 */
	public void setConfiguration(final SessionContext ctx, final StockWebServiceConfiguration value)
	{
		CONFIGURATIONHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceParameter.configuration</code> attribute. 
	 * @param value the configuration
	 */
	public void setConfiguration(final StockWebServiceConfiguration value)
	{
		setConfiguration( getSession().getSessionContext(), value );
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		CONFIGURATIONHANDLER.newInstance(ctx, allAttributes);
		STOCKCONFIGURATIONHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceParameter.stockConfiguration</code> attribute.
	 * @return the stockConfiguration
	 */
	public StockWebServiceConfiguration getStockConfiguration(final SessionContext ctx)
	{
		return (StockWebServiceConfiguration)getProperty( ctx, STOCKCONFIGURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceParameter.stockConfiguration</code> attribute.
	 * @return the stockConfiguration
	 */
	public StockWebServiceConfiguration getStockConfiguration()
	{
		return getStockConfiguration( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceParameter.stockConfiguration</code> attribute. 
	 * @param value the stockConfiguration
	 */
	public void setStockConfiguration(final SessionContext ctx, final StockWebServiceConfiguration value)
	{
		STOCKCONFIGURATIONHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceParameter.stockConfiguration</code> attribute. 
	 * @param value the stockConfiguration
	 */
	public void setStockConfiguration(final StockWebServiceConfiguration value)
	{
		setStockConfiguration( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceParameter.value</code> attribute.
	 * @return the value
	 */
	public EnumerationValue getValue(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, VALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceParameter.value</code> attribute.
	 * @return the value
	 */
	public EnumerationValue getValue()
	{
		return getValue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceParameter.value</code> attribute. 
	 * @param value the value
	 */
	public void setValue(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, VALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceParameter.value</code> attribute. 
	 * @param value the value
	 */
	public void setValue(final EnumerationValue value)
	{
		setValue( getSession().getSessionContext(), value );
	}
	
}
