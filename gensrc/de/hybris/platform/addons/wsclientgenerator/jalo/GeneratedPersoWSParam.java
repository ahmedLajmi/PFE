/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 16 avr. 2019 12:17:53                       ---
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
import de.hybris.platform.addons.wsclientgenerator.jalo.WebServiceConfiguration;
import de.hybris.platform.addons.wsclientgenerator.jalo.WebServiceParameter;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.BidirectionalOneToManyHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem PersoWSParam}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedPersoWSParam extends WebServiceParameter
{
	/** Qualifier of the <code>PersoWSParam.value</code> attribute **/
	public static final String VALUE = "value";
	/** Qualifier of the <code>PersoWSParam.configuration</code> attribute **/
	public static final String CONFIGURATION = "configuration";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n CONFIGURATION's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedPersoWSParam> CONFIGURATIONHANDLER = new BidirectionalOneToManyHandler<GeneratedPersoWSParam>(
	WsclientgeneratorConstants.TC.PERSOWSPARAM,
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
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(WebServiceParameter.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(VALUE, AttributeMode.INITIAL);
		tmp.put(CONFIGURATION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PersoWSParam.configuration</code> attribute.
	 * @return the configuration
	 */
	public WebServiceConfiguration getConfiguration(final SessionContext ctx)
	{
		return (WebServiceConfiguration)getProperty( ctx, CONFIGURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PersoWSParam.configuration</code> attribute.
	 * @return the configuration
	 */
	public WebServiceConfiguration getConfiguration()
	{
		return getConfiguration( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PersoWSParam.configuration</code> attribute. 
	 * @param value the configuration
	 */
	public void setConfiguration(final SessionContext ctx, final WebServiceConfiguration value)
	{
		CONFIGURATIONHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PersoWSParam.configuration</code> attribute. 
	 * @param value the configuration
	 */
	public void setConfiguration(final WebServiceConfiguration value)
	{
		setConfiguration( getSession().getSessionContext(), value );
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		CONFIGURATIONHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PersoWSParam.value</code> attribute.
	 * @return the value - The value of the parametre
	 */
	public String getValue(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PersoWSParam.value</code> attribute.
	 * @return the value - The value of the parametre
	 */
	public String getValue()
	{
		return getValue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PersoWSParam.value</code> attribute. 
	 * @param value the value - The value of the parametre
	 */
	public void setValue(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PersoWSParam.value</code> attribute. 
	 * @param value the value - The value of the parametre
	 */
	public void setValue(final String value)
	{
		setValue( getSession().getSessionContext(), value );
	}
	
}
