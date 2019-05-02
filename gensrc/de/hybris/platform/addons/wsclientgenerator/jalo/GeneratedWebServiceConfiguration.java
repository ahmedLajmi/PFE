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
import de.hybris.platform.addons.wsclientgenerator.jalo.PersoWSParam;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem WebServiceConfiguration}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWebServiceConfiguration extends GenericItem
{
	/** Qualifier of the <code>WebServiceConfiguration.name</code> attribute **/
	public static final String NAME = "name";
	/** Qualifier of the <code>WebServiceConfiguration.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>WebServiceConfiguration.url</code> attribute **/
	public static final String URL = "url";
	/** Qualifier of the <code>WebServiceConfiguration.method</code> attribute **/
	public static final String METHOD = "method";
	/** Qualifier of the <code>WebServiceConfiguration.mode</code> attribute **/
	public static final String MODE = "mode";
	/** Qualifier of the <code>WebServiceConfiguration.enable</code> attribute **/
	public static final String ENABLE = "enable";
	/** Qualifier of the <code>WebServiceConfiguration.accept</code> attribute **/
	public static final String ACCEPT = "accept";
	/** Qualifier of the <code>WebServiceConfiguration.contentType</code> attribute **/
	public static final String CONTENTTYPE = "contentType";
	/** Qualifier of the <code>WebServiceConfiguration.responseTemplate</code> attribute **/
	public static final String RESPONSETEMPLATE = "responseTemplate";
	/** Qualifier of the <code>WebServiceConfiguration.textSeperator</code> attribute **/
	public static final String TEXTSEPERATOR = "textSeperator";
	/** Qualifier of the <code>WebServiceConfiguration.rootKey</code> attribute **/
	public static final String ROOTKEY = "rootKey";
	/** Qualifier of the <code>WebServiceConfiguration.login</code> attribute **/
	public static final String LOGIN = "login";
	/** Qualifier of the <code>WebServiceConfiguration.password</code> attribute **/
	public static final String PASSWORD = "password";
	/** Qualifier of the <code>WebServiceConfiguration.personalisedParameters</code> attribute **/
	public static final String PERSONALISEDPARAMETERS = "personalisedParameters";
	/** Qualifier of the <code>WebServiceConfiguration.headersParameters</code> attribute **/
	public static final String HEADERSPARAMETERS = "headersParameters";
	/**
	* {@link OneToManyHandler} for handling 1:n PERSONALISEDPARAMETERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<PersoWSParam> PERSONALISEDPARAMETERSHANDLER = new OneToManyHandler<PersoWSParam>(
	WsclientgeneratorConstants.TC.PERSOWSPARAM,
	false,
	"configuration",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	/**
	* {@link OneToManyHandler} for handling 1:n HEADERSPARAMETERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<PersoWSParam> HEADERSPARAMETERSHANDLER = new OneToManyHandler<PersoWSParam>(
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
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(NAME, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(URL, AttributeMode.INITIAL);
		tmp.put(METHOD, AttributeMode.INITIAL);
		tmp.put(MODE, AttributeMode.INITIAL);
		tmp.put(ENABLE, AttributeMode.INITIAL);
		tmp.put(ACCEPT, AttributeMode.INITIAL);
		tmp.put(CONTENTTYPE, AttributeMode.INITIAL);
		tmp.put(RESPONSETEMPLATE, AttributeMode.INITIAL);
		tmp.put(TEXTSEPERATOR, AttributeMode.INITIAL);
		tmp.put(ROOTKEY, AttributeMode.INITIAL);
		tmp.put(LOGIN, AttributeMode.INITIAL);
		tmp.put(PASSWORD, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.accept</code> attribute.
	 * @return the accept
	 */
	public EnumerationValue getAccept(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, ACCEPT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.accept</code> attribute.
	 * @return the accept
	 */
	public EnumerationValue getAccept()
	{
		return getAccept( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.accept</code> attribute. 
	 * @param value the accept
	 */
	public void setAccept(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, ACCEPT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.accept</code> attribute. 
	 * @param value the accept
	 */
	public void setAccept(final EnumerationValue value)
	{
		setAccept( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.contentType</code> attribute.
	 * @return the contentType
	 */
	public EnumerationValue getContentType(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, CONTENTTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.contentType</code> attribute.
	 * @return the contentType
	 */
	public EnumerationValue getContentType()
	{
		return getContentType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.contentType</code> attribute. 
	 * @param value the contentType
	 */
	public void setContentType(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, CONTENTTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.contentType</code> attribute. 
	 * @param value the contentType
	 */
	public void setContentType(final EnumerationValue value)
	{
		setContentType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.description</code> attribute.
	 * @return the description
	 */
	public String getDescription(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.description</code> attribute.
	 * @return the description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.description</code> attribute. 
	 * @param value the description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.enable</code> attribute.
	 * @return the enable
	 */
	public Boolean isEnable(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ENABLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.enable</code> attribute.
	 * @return the enable
	 */
	public Boolean isEnable()
	{
		return isEnable( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.enable</code> attribute. 
	 * @return the enable
	 */
	public boolean isEnableAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isEnable( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.enable</code> attribute. 
	 * @return the enable
	 */
	public boolean isEnableAsPrimitive()
	{
		return isEnableAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.enable</code> attribute. 
	 * @param value the enable
	 */
	public void setEnable(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ENABLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.enable</code> attribute. 
	 * @param value the enable
	 */
	public void setEnable(final Boolean value)
	{
		setEnable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.enable</code> attribute. 
	 * @param value the enable
	 */
	public void setEnable(final SessionContext ctx, final boolean value)
	{
		setEnable( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.enable</code> attribute. 
	 * @param value the enable
	 */
	public void setEnable(final boolean value)
	{
		setEnable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.headersParameters</code> attribute.
	 * @return the headersParameters
	 */
	public Collection<PersoWSParam> getHeadersParameters(final SessionContext ctx)
	{
		return HEADERSPARAMETERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.headersParameters</code> attribute.
	 * @return the headersParameters
	 */
	public Collection<PersoWSParam> getHeadersParameters()
	{
		return getHeadersParameters( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.headersParameters</code> attribute. 
	 * @param value the headersParameters
	 */
	public void setHeadersParameters(final SessionContext ctx, final Collection<PersoWSParam> value)
	{
		HEADERSPARAMETERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.headersParameters</code> attribute. 
	 * @param value the headersParameters
	 */
	public void setHeadersParameters(final Collection<PersoWSParam> value)
	{
		setHeadersParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to headersParameters. 
	 * @param value the item to add to headersParameters
	 */
	public void addToHeadersParameters(final SessionContext ctx, final PersoWSParam value)
	{
		HEADERSPARAMETERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to headersParameters. 
	 * @param value the item to add to headersParameters
	 */
	public void addToHeadersParameters(final PersoWSParam value)
	{
		addToHeadersParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from headersParameters. 
	 * @param value the item to remove from headersParameters
	 */
	public void removeFromHeadersParameters(final SessionContext ctx, final PersoWSParam value)
	{
		HEADERSPARAMETERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from headersParameters. 
	 * @param value the item to remove from headersParameters
	 */
	public void removeFromHeadersParameters(final PersoWSParam value)
	{
		removeFromHeadersParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.login</code> attribute.
	 * @return the login
	 */
	public String getLogin(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOGIN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.login</code> attribute.
	 * @return the login
	 */
	public String getLogin()
	{
		return getLogin( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.login</code> attribute. 
	 * @param value the login
	 */
	public void setLogin(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOGIN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.login</code> attribute. 
	 * @param value the login
	 */
	public void setLogin(final String value)
	{
		setLogin( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.method</code> attribute.
	 * @return the method
	 */
	public EnumerationValue getMethod(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, METHOD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.method</code> attribute.
	 * @return the method
	 */
	public EnumerationValue getMethod()
	{
		return getMethod( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.method</code> attribute. 
	 * @param value the method
	 */
	public void setMethod(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, METHOD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.method</code> attribute. 
	 * @param value the method
	 */
	public void setMethod(final EnumerationValue value)
	{
		setMethod( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.mode</code> attribute.
	 * @return the mode
	 */
	public EnumerationValue getMode(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, MODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.mode</code> attribute.
	 * @return the mode
	 */
	public EnumerationValue getMode()
	{
		return getMode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.mode</code> attribute. 
	 * @param value the mode
	 */
	public void setMode(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, MODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.mode</code> attribute. 
	 * @param value the mode
	 */
	public void setMode(final EnumerationValue value)
	{
		setMode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.name</code> attribute.
	 * @return the name
	 */
	public String getName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.name</code> attribute.
	 * @return the name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.password</code> attribute.
	 * @return the password
	 */
	public String getPassword(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PASSWORD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.password</code> attribute.
	 * @return the password
	 */
	public String getPassword()
	{
		return getPassword( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.password</code> attribute. 
	 * @param value the password
	 */
	public void setPassword(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PASSWORD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.password</code> attribute. 
	 * @param value the password
	 */
	public void setPassword(final String value)
	{
		setPassword( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.personalisedParameters</code> attribute.
	 * @return the personalisedParameters
	 */
	public Collection<PersoWSParam> getPersonalisedParameters(final SessionContext ctx)
	{
		return PERSONALISEDPARAMETERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.personalisedParameters</code> attribute.
	 * @return the personalisedParameters
	 */
	public Collection<PersoWSParam> getPersonalisedParameters()
	{
		return getPersonalisedParameters( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.personalisedParameters</code> attribute. 
	 * @param value the personalisedParameters
	 */
	public void setPersonalisedParameters(final SessionContext ctx, final Collection<PersoWSParam> value)
	{
		PERSONALISEDPARAMETERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.personalisedParameters</code> attribute. 
	 * @param value the personalisedParameters
	 */
	public void setPersonalisedParameters(final Collection<PersoWSParam> value)
	{
		setPersonalisedParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to personalisedParameters. 
	 * @param value the item to add to personalisedParameters
	 */
	public void addToPersonalisedParameters(final SessionContext ctx, final PersoWSParam value)
	{
		PERSONALISEDPARAMETERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to personalisedParameters. 
	 * @param value the item to add to personalisedParameters
	 */
	public void addToPersonalisedParameters(final PersoWSParam value)
	{
		addToPersonalisedParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from personalisedParameters. 
	 * @param value the item to remove from personalisedParameters
	 */
	public void removeFromPersonalisedParameters(final SessionContext ctx, final PersoWSParam value)
	{
		PERSONALISEDPARAMETERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from personalisedParameters. 
	 * @param value the item to remove from personalisedParameters
	 */
	public void removeFromPersonalisedParameters(final PersoWSParam value)
	{
		removeFromPersonalisedParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.responseTemplate</code> attribute.
	 * @return the responseTemplate
	 */
	public String getResponseTemplate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, RESPONSETEMPLATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.responseTemplate</code> attribute.
	 * @return the responseTemplate
	 */
	public String getResponseTemplate()
	{
		return getResponseTemplate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.responseTemplate</code> attribute. 
	 * @param value the responseTemplate
	 */
	public void setResponseTemplate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, RESPONSETEMPLATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.responseTemplate</code> attribute. 
	 * @param value the responseTemplate
	 */
	public void setResponseTemplate(final String value)
	{
		setResponseTemplate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.rootKey</code> attribute.
	 * @return the rootKey
	 */
	public String getRootKey(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ROOTKEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.rootKey</code> attribute.
	 * @return the rootKey
	 */
	public String getRootKey()
	{
		return getRootKey( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.rootKey</code> attribute. 
	 * @param value the rootKey
	 */
	public void setRootKey(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ROOTKEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.rootKey</code> attribute. 
	 * @param value the rootKey
	 */
	public void setRootKey(final String value)
	{
		setRootKey( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.textSeperator</code> attribute.
	 * @return the textSeperator
	 */
	public String getTextSeperator(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TEXTSEPERATOR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.textSeperator</code> attribute.
	 * @return the textSeperator
	 */
	public String getTextSeperator()
	{
		return getTextSeperator( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.textSeperator</code> attribute. 
	 * @param value the textSeperator
	 */
	public void setTextSeperator(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TEXTSEPERATOR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.textSeperator</code> attribute. 
	 * @param value the textSeperator
	 */
	public void setTextSeperator(final String value)
	{
		setTextSeperator( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.url</code> attribute.
	 * @return the url - The URL of the webservice
	 */
	public String getUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, URL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebServiceConfiguration.url</code> attribute.
	 * @return the url - The URL of the webservice
	 */
	public String getUrl()
	{
		return getUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.url</code> attribute. 
	 * @param value the url - The URL of the webservice
	 */
	public void setUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, URL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebServiceConfiguration.url</code> attribute. 
	 * @param value the url - The URL of the webservice
	 */
	public void setUrl(final String value)
	{
		setUrl( getSession().getSessionContext(), value );
	}
	
}
