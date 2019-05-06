/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 6 mai 2019 13:57:02                         ---
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem HeaderWSParam}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedHeaderWSParam extends WebServiceParameter
{
	/** Qualifier of the <code>HeaderWSParam.value</code> attribute **/
	public static final String VALUE = "value";
	/** Qualifier of the <code>HeaderWSParam.configurationHeader</code> attribute **/
	public static final String CONFIGURATIONHEADER = "configurationHeader";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n CONFIGURATIONHEADER's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedHeaderWSParam> CONFIGURATIONHEADERHANDLER = new BidirectionalOneToManyHandler<GeneratedHeaderWSParam>(
	WsclientgeneratorConstants.TC.HEADERWSPARAM,
	false,
	"configurationHeader",
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
		tmp.put(CONFIGURATIONHEADER, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HeaderWSParam.configurationHeader</code> attribute.
	 * @return the configurationHeader
	 */
	public WebServiceConfiguration getConfigurationHeader(final SessionContext ctx)
	{
		return (WebServiceConfiguration)getProperty( ctx, CONFIGURATIONHEADER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HeaderWSParam.configurationHeader</code> attribute.
	 * @return the configurationHeader
	 */
	public WebServiceConfiguration getConfigurationHeader()
	{
		return getConfigurationHeader( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HeaderWSParam.configurationHeader</code> attribute. 
	 * @param value the configurationHeader
	 */
	public void setConfigurationHeader(final SessionContext ctx, final WebServiceConfiguration value)
	{
		CONFIGURATIONHEADERHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HeaderWSParam.configurationHeader</code> attribute. 
	 * @param value the configurationHeader
	 */
	public void setConfigurationHeader(final WebServiceConfiguration value)
	{
		setConfigurationHeader( getSession().getSessionContext(), value );
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		CONFIGURATIONHEADERHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HeaderWSParam.value</code> attribute.
	 * @return the value - The value of the parametre
	 */
	public String getValue(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HeaderWSParam.value</code> attribute.
	 * @return the value - The value of the parametre
	 */
	public String getValue()
	{
		return getValue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HeaderWSParam.value</code> attribute. 
	 * @param value the value - The value of the parametre
	 */
	public void setValue(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HeaderWSParam.value</code> attribute. 
	 * @param value the value - The value of the parametre
	 */
	public void setValue(final String value)
	{
		setValue( getSession().getSessionContext(), value );
	}
	
}
