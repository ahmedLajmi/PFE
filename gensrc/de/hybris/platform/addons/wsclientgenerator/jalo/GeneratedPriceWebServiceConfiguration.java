/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 12 avr. 2019 14:56:44                       ---
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
import de.hybris.platform.addons.wsclientgenerator.jalo.PriceWebServiceParameter;
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem PriceWebServiceConfiguration}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedPriceWebServiceConfiguration extends WebServiceConfiguration
{
	/** Qualifier of the <code>PriceWebServiceConfiguration.priceKey</code> attribute **/
	public static final String PRICEKEY = "priceKey";
	/** Qualifier of the <code>PriceWebServiceConfiguration.currencyKey</code> attribute **/
	public static final String CURRENCYKEY = "currencyKey";
	/** Qualifier of the <code>PriceWebServiceConfiguration.parameters</code> attribute **/
	public static final String PARAMETERS = "parameters";
	/**
	* {@link OneToManyHandler} for handling 1:n PARAMETERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<PriceWebServiceParameter> PARAMETERSHANDLER = new OneToManyHandler<PriceWebServiceParameter>(
	WsclientgeneratorConstants.TC.PRICEWEBSERVICEPARAMETER,
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
		tmp.put(PRICEKEY, AttributeMode.INITIAL);
		tmp.put(CURRENCYKEY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceConfiguration.currencyKey</code> attribute.
	 * @return the currencyKey
	 */
	public String getCurrencyKey(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CURRENCYKEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceConfiguration.currencyKey</code> attribute.
	 * @return the currencyKey
	 */
	public String getCurrencyKey()
	{
		return getCurrencyKey( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceConfiguration.currencyKey</code> attribute. 
	 * @param value the currencyKey
	 */
	public void setCurrencyKey(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CURRENCYKEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceConfiguration.currencyKey</code> attribute. 
	 * @param value the currencyKey
	 */
	public void setCurrencyKey(final String value)
	{
		setCurrencyKey( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceConfiguration.parameters</code> attribute.
	 * @return the parameters
	 */
	public Collection<PriceWebServiceParameter> getParameters(final SessionContext ctx)
	{
		return PARAMETERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceConfiguration.parameters</code> attribute.
	 * @return the parameters
	 */
	public Collection<PriceWebServiceParameter> getParameters()
	{
		return getParameters( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceConfiguration.parameters</code> attribute. 
	 * @param value the parameters
	 */
	public void setParameters(final SessionContext ctx, final Collection<PriceWebServiceParameter> value)
	{
		PARAMETERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceConfiguration.parameters</code> attribute. 
	 * @param value the parameters
	 */
	public void setParameters(final Collection<PriceWebServiceParameter> value)
	{
		setParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to parameters. 
	 * @param value the item to add to parameters
	 */
	public void addToParameters(final SessionContext ctx, final PriceWebServiceParameter value)
	{
		PARAMETERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to parameters. 
	 * @param value the item to add to parameters
	 */
	public void addToParameters(final PriceWebServiceParameter value)
	{
		addToParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from parameters. 
	 * @param value the item to remove from parameters
	 */
	public void removeFromParameters(final SessionContext ctx, final PriceWebServiceParameter value)
	{
		PARAMETERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from parameters. 
	 * @param value the item to remove from parameters
	 */
	public void removeFromParameters(final PriceWebServiceParameter value)
	{
		removeFromParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceConfiguration.priceKey</code> attribute.
	 * @return the priceKey
	 */
	public String getPriceKey(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRICEKEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceConfiguration.priceKey</code> attribute.
	 * @return the priceKey
	 */
	public String getPriceKey()
	{
		return getPriceKey( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceConfiguration.priceKey</code> attribute. 
	 * @param value the priceKey
	 */
	public void setPriceKey(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRICEKEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceConfiguration.priceKey</code> attribute. 
	 * @param value the priceKey
	 */
	public void setPriceKey(final String value)
	{
		setPriceKey( getSession().getSessionContext(), value );
	}
	
}
