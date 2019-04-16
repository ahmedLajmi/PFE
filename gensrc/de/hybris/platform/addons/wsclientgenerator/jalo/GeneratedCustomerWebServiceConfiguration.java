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
import de.hybris.platform.addons.wsclientgenerator.jalo.CustomerWebServiceParameter;
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem CustomerWebServiceConfiguration}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCustomerWebServiceConfiguration extends WebServiceConfiguration
{
	/** Qualifier of the <code>CustomerWebServiceConfiguration.firstNameKey</code> attribute **/
	public static final String FIRSTNAMEKEY = "firstNameKey";
	/** Qualifier of the <code>CustomerWebServiceConfiguration.lastNameKey</code> attribute **/
	public static final String LASTNAMEKEY = "lastNameKey";
	/** Qualifier of the <code>CustomerWebServiceConfiguration.emailKey</code> attribute **/
	public static final String EMAILKEY = "emailKey";
	/** Qualifier of the <code>CustomerWebServiceConfiguration.parameters</code> attribute **/
	public static final String PARAMETERS = "parameters";
	/**
	* {@link OneToManyHandler} for handling 1:n PARAMETERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<CustomerWebServiceParameter> PARAMETERSHANDLER = new OneToManyHandler<CustomerWebServiceParameter>(
	WsclientgeneratorConstants.TC.CUSTOMERWEBSERVICEPARAMETER,
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
		tmp.put(FIRSTNAMEKEY, AttributeMode.INITIAL);
		tmp.put(LASTNAMEKEY, AttributeMode.INITIAL);
		tmp.put(EMAILKEY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerWebServiceConfiguration.emailKey</code> attribute.
	 * @return the emailKey
	 */
	public String getEmailKey(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EMAILKEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerWebServiceConfiguration.emailKey</code> attribute.
	 * @return the emailKey
	 */
	public String getEmailKey()
	{
		return getEmailKey( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerWebServiceConfiguration.emailKey</code> attribute. 
	 * @param value the emailKey
	 */
	public void setEmailKey(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EMAILKEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerWebServiceConfiguration.emailKey</code> attribute. 
	 * @param value the emailKey
	 */
	public void setEmailKey(final String value)
	{
		setEmailKey( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerWebServiceConfiguration.firstNameKey</code> attribute.
	 * @return the firstNameKey
	 */
	public String getFirstNameKey(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FIRSTNAMEKEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerWebServiceConfiguration.firstNameKey</code> attribute.
	 * @return the firstNameKey
	 */
	public String getFirstNameKey()
	{
		return getFirstNameKey( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerWebServiceConfiguration.firstNameKey</code> attribute. 
	 * @param value the firstNameKey
	 */
	public void setFirstNameKey(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FIRSTNAMEKEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerWebServiceConfiguration.firstNameKey</code> attribute. 
	 * @param value the firstNameKey
	 */
	public void setFirstNameKey(final String value)
	{
		setFirstNameKey( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerWebServiceConfiguration.lastNameKey</code> attribute.
	 * @return the lastNameKey
	 */
	public String getLastNameKey(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LASTNAMEKEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerWebServiceConfiguration.lastNameKey</code> attribute.
	 * @return the lastNameKey
	 */
	public String getLastNameKey()
	{
		return getLastNameKey( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerWebServiceConfiguration.lastNameKey</code> attribute. 
	 * @param value the lastNameKey
	 */
	public void setLastNameKey(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LASTNAMEKEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerWebServiceConfiguration.lastNameKey</code> attribute. 
	 * @param value the lastNameKey
	 */
	public void setLastNameKey(final String value)
	{
		setLastNameKey( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerWebServiceConfiguration.parameters</code> attribute.
	 * @return the parameters
	 */
	public Collection<CustomerWebServiceParameter> getParameters(final SessionContext ctx)
	{
		return PARAMETERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CustomerWebServiceConfiguration.parameters</code> attribute.
	 * @return the parameters
	 */
	public Collection<CustomerWebServiceParameter> getParameters()
	{
		return getParameters( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerWebServiceConfiguration.parameters</code> attribute. 
	 * @param value the parameters
	 */
	public void setParameters(final SessionContext ctx, final Collection<CustomerWebServiceParameter> value)
	{
		PARAMETERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CustomerWebServiceConfiguration.parameters</code> attribute. 
	 * @param value the parameters
	 */
	public void setParameters(final Collection<CustomerWebServiceParameter> value)
	{
		setParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to parameters. 
	 * @param value the item to add to parameters
	 */
	public void addToParameters(final SessionContext ctx, final CustomerWebServiceParameter value)
	{
		PARAMETERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to parameters. 
	 * @param value the item to add to parameters
	 */
	public void addToParameters(final CustomerWebServiceParameter value)
	{
		addToParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from parameters. 
	 * @param value the item to remove from parameters
	 */
	public void removeFromParameters(final SessionContext ctx, final CustomerWebServiceParameter value)
	{
		PARAMETERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from parameters. 
	 * @param value the item to remove from parameters
	 */
	public void removeFromParameters(final CustomerWebServiceParameter value)
	{
		removeFromParameters( getSession().getSessionContext(), value );
	}
	
}
