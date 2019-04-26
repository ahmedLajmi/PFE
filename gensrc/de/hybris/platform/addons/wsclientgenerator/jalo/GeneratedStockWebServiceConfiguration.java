/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 26 avr. 2019 11:07:06                       ---
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
import de.hybris.platform.addons.wsclientgenerator.jalo.StockWebServiceParameter;
import de.hybris.platform.addons.wsclientgenerator.jalo.WebServiceConfiguration;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem StockWebServiceConfiguration}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedStockWebServiceConfiguration extends WebServiceConfiguration
{
	/** Qualifier of the <code>StockWebServiceConfiguration.stockKey</code> attribute **/
	public static final String STOCKKEY = "stockKey";
	/** Qualifier of the <code>StockWebServiceConfiguration.productCode</code> attribute **/
	public static final String PRODUCTCODE = "productCode";
	/** Qualifier of the <code>StockWebServiceConfiguration.parameters</code> attribute **/
	public static final String PARAMETERS = "parameters";
	/**
	* {@link OneToManyHandler} for handling 1:n PARAMETERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<StockWebServiceParameter> PARAMETERSHANDLER = new OneToManyHandler<StockWebServiceParameter>(
	WsclientgeneratorConstants.TC.STOCKWEBSERVICEPARAMETER,
	false,
	"configuration",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(WebServiceConfiguration.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(STOCKKEY, AttributeMode.INITIAL);
		tmp.put(PRODUCTCODE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.parameters</code> attribute.
	 * @return the parameters
	 */
	public Collection<StockWebServiceParameter> getParameters(final SessionContext ctx)
	{
		return PARAMETERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.parameters</code> attribute.
	 * @return the parameters
	 */
	public Collection<StockWebServiceParameter> getParameters()
	{
		return getParameters( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.parameters</code> attribute. 
	 * @param value the parameters
	 */
	public void setParameters(final SessionContext ctx, final Collection<StockWebServiceParameter> value)
	{
		PARAMETERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.parameters</code> attribute. 
	 * @param value the parameters
	 */
	public void setParameters(final Collection<StockWebServiceParameter> value)
	{
		setParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to parameters. 
	 * @param value the item to add to parameters
	 */
	public void addToParameters(final SessionContext ctx, final StockWebServiceParameter value)
	{
		PARAMETERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to parameters. 
	 * @param value the item to add to parameters
	 */
	public void addToParameters(final StockWebServiceParameter value)
	{
		addToParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from parameters. 
	 * @param value the item to remove from parameters
	 */
	public void removeFromParameters(final SessionContext ctx, final StockWebServiceParameter value)
	{
		PARAMETERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from parameters. 
	 * @param value the item to remove from parameters
	 */
	public void removeFromParameters(final StockWebServiceParameter value)
	{
		removeFromParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.productCode</code> attribute.
	 * @return the productCode
	 */
	public Boolean isProductCode(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, PRODUCTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.productCode</code> attribute.
	 * @return the productCode
	 */
	public Boolean isProductCode()
	{
		return isProductCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.productCode</code> attribute. 
	 * @return the productCode
	 */
	public boolean isProductCodeAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isProductCode( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.productCode</code> attribute. 
	 * @return the productCode
	 */
	public boolean isProductCodeAsPrimitive()
	{
		return isProductCodeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, PRODUCTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final Boolean value)
	{
		setProductCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final SessionContext ctx, final boolean value)
	{
		setProductCode( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final boolean value)
	{
		setProductCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.stockKey</code> attribute.
	 * @return the stockKey
	 */
	public String getStockKey(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STOCKKEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.stockKey</code> attribute.
	 * @return the stockKey
	 */
	public String getStockKey()
	{
		return getStockKey( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.stockKey</code> attribute. 
	 * @param value the stockKey
	 */
	public void setStockKey(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STOCKKEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.stockKey</code> attribute. 
	 * @param value the stockKey
	 */
	public void setStockKey(final String value)
	{
		setStockKey( getSession().getSessionContext(), value );
	}
	
}
