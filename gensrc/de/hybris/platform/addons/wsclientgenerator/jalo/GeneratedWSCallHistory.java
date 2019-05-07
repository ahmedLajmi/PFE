/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 7 mai 2019 13:12:56                         ---
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
import de.hybris.platform.jalo.GenericItem;
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem WSCallHistory}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWSCallHistory extends GenericItem
{
	/** Qualifier of the <code>WSCallHistory.confName</code> attribute **/
	public static final String CONFNAME = "confName";
	/** Qualifier of the <code>WSCallHistory.confMethod</code> attribute **/
	public static final String CONFMETHOD = "confMethod";
	/** Qualifier of the <code>WSCallHistory.url</code> attribute **/
	public static final String URL = "url";
	/** Qualifier of the <code>WSCallHistory.requestBody</code> attribute **/
	public static final String REQUESTBODY = "requestBody";
	/** Qualifier of the <code>WSCallHistory.responseBody</code> attribute **/
	public static final String RESPONSEBODY = "responseBody";
	/** Qualifier of the <code>WSCallHistory.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>WSCallHistory.responseCode</code> attribute **/
	public static final String RESPONSECODE = "responseCode";
	/** Qualifier of the <code>WSCallHistory.configuration</code> attribute **/
	public static final String CONFIGURATION = "configuration";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n CONFIGURATION's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedWSCallHistory> CONFIGURATIONHANDLER = new BidirectionalOneToManyHandler<GeneratedWSCallHistory>(
	WsclientgeneratorConstants.TC.WSCALLHISTORY,
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
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CONFNAME, AttributeMode.INITIAL);
		tmp.put(CONFMETHOD, AttributeMode.INITIAL);
		tmp.put(URL, AttributeMode.INITIAL);
		tmp.put(REQUESTBODY, AttributeMode.INITIAL);
		tmp.put(RESPONSEBODY, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(RESPONSECODE, AttributeMode.INITIAL);
		tmp.put(CONFIGURATION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.configuration</code> attribute.
	 * @return the configuration
	 */
	public WebServiceConfiguration getConfiguration(final SessionContext ctx)
	{
		return (WebServiceConfiguration)getProperty( ctx, CONFIGURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.configuration</code> attribute.
	 * @return the configuration
	 */
	public WebServiceConfiguration getConfiguration()
	{
		return getConfiguration( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.configuration</code> attribute. 
	 * @param value the configuration
	 */
	public void setConfiguration(final SessionContext ctx, final WebServiceConfiguration value)
	{
		CONFIGURATIONHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.configuration</code> attribute. 
	 * @param value the configuration
	 */
	public void setConfiguration(final WebServiceConfiguration value)
	{
		setConfiguration( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.confMethod</code> attribute.
	 * @return the confMethod
	 */
	public EnumerationValue getConfMethod(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, CONFMETHOD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.confMethod</code> attribute.
	 * @return the confMethod
	 */
	public EnumerationValue getConfMethod()
	{
		return getConfMethod( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.confMethod</code> attribute. 
	 * @param value the confMethod
	 */
	public void setConfMethod(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, CONFMETHOD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.confMethod</code> attribute. 
	 * @param value the confMethod
	 */
	public void setConfMethod(final EnumerationValue value)
	{
		setConfMethod( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.confName</code> attribute.
	 * @return the confName
	 */
	public String getConfName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONFNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.confName</code> attribute.
	 * @return the confName
	 */
	public String getConfName()
	{
		return getConfName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.confName</code> attribute. 
	 * @param value the confName
	 */
	public void setConfName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONFNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.confName</code> attribute. 
	 * @param value the confName
	 */
	public void setConfName(final String value)
	{
		setConfName( getSession().getSessionContext(), value );
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		CONFIGURATIONHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.requestBody</code> attribute.
	 * @return the requestBody
	 */
	public String getRequestBody(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REQUESTBODY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.requestBody</code> attribute.
	 * @return the requestBody
	 */
	public String getRequestBody()
	{
		return getRequestBody( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.requestBody</code> attribute. 
	 * @param value the requestBody
	 */
	public void setRequestBody(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REQUESTBODY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.requestBody</code> attribute. 
	 * @param value the requestBody
	 */
	public void setRequestBody(final String value)
	{
		setRequestBody( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.responseBody</code> attribute.
	 * @return the responseBody
	 */
	public String getResponseBody(final SessionContext ctx)
	{
		return (String)getProperty( ctx, RESPONSEBODY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.responseBody</code> attribute.
	 * @return the responseBody
	 */
	public String getResponseBody()
	{
		return getResponseBody( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.responseBody</code> attribute. 
	 * @param value the responseBody
	 */
	public void setResponseBody(final SessionContext ctx, final String value)
	{
		setProperty(ctx, RESPONSEBODY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.responseBody</code> attribute. 
	 * @param value the responseBody
	 */
	public void setResponseBody(final String value)
	{
		setResponseBody( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.responseCode</code> attribute.
	 * @return the responseCode
	 */
	public String getResponseCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, RESPONSECODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.responseCode</code> attribute.
	 * @return the responseCode
	 */
	public String getResponseCode()
	{
		return getResponseCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.responseCode</code> attribute. 
	 * @param value the responseCode
	 */
	public void setResponseCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, RESPONSECODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.responseCode</code> attribute. 
	 * @param value the responseCode
	 */
	public void setResponseCode(final String value)
	{
		setResponseCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.url</code> attribute.
	 * @return the url
	 */
	public String getUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, URL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WSCallHistory.url</code> attribute.
	 * @return the url
	 */
	public String getUrl()
	{
		return getUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.url</code> attribute. 
	 * @param value the url
	 */
	public void setUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, URL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WSCallHistory.url</code> attribute. 
	 * @param value the url
	 */
	public void setUrl(final String value)
	{
		setUrl( getSession().getSessionContext(), value );
	}
	
}
