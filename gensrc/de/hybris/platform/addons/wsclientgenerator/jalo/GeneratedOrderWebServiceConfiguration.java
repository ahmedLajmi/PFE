/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 10 avr. 2019 15:56:21                       ---
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
import de.hybris.platform.addons.wsclientgenerator.jalo.OrderWebServiceParameter;
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem OrderWebServiceConfiguration}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedOrderWebServiceConfiguration extends WebServiceConfiguration
{
	/** Qualifier of the <code>OrderWebServiceConfiguration.parameters</code> attribute **/
	public static final String PARAMETERS = "parameters";
	/**
	* {@link OneToManyHandler} for handling 1:n PARAMETERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<OrderWebServiceParameter> PARAMETERSHANDLER = new OneToManyHandler<OrderWebServiceParameter>(
	WsclientgeneratorConstants.TC.ORDERWEBSERVICEPARAMETER,
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
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderWebServiceConfiguration.parameters</code> attribute.
	 * @return the parameters
	 */
	public Collection<OrderWebServiceParameter> getParameters(final SessionContext ctx)
	{
		return PARAMETERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderWebServiceConfiguration.parameters</code> attribute.
	 * @return the parameters
	 */
	public Collection<OrderWebServiceParameter> getParameters()
	{
		return getParameters( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderWebServiceConfiguration.parameters</code> attribute. 
	 * @param value the parameters
	 */
	public void setParameters(final SessionContext ctx, final Collection<OrderWebServiceParameter> value)
	{
		PARAMETERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderWebServiceConfiguration.parameters</code> attribute. 
	 * @param value the parameters
	 */
	public void setParameters(final Collection<OrderWebServiceParameter> value)
	{
		setParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to parameters. 
	 * @param value the item to add to parameters
	 */
	public void addToParameters(final SessionContext ctx, final OrderWebServiceParameter value)
	{
		PARAMETERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to parameters. 
	 * @param value the item to add to parameters
	 */
	public void addToParameters(final OrderWebServiceParameter value)
	{
		addToParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from parameters. 
	 * @param value the item to remove from parameters
	 */
	public void removeFromParameters(final SessionContext ctx, final OrderWebServiceParameter value)
	{
		PARAMETERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from parameters. 
	 * @param value the item to remove from parameters
	 */
	public void removeFromParameters(final OrderWebServiceParameter value)
	{
		removeFromParameters( getSession().getSessionContext(), value );
	}
	
}
