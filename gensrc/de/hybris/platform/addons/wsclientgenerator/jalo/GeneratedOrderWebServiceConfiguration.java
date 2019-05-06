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
import de.hybris.platform.addons.wsclientgenerator.jalo.OrderWebServiceParameter;
import de.hybris.platform.addons.wsclientgenerator.jalo.OrderWebServiceResponse;
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
	/** Qualifier of the <code>OrderWebServiceConfiguration.pathParameters</code> attribute **/
	public static final String PATHPARAMETERS = "pathParameters";
	/** Qualifier of the <code>OrderWebServiceConfiguration.responseMapping</code> attribute **/
	public static final String RESPONSEMAPPING = "responseMapping";
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
	/**
	* {@link OneToManyHandler} for handling 1:n PATHPARAMETERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<OrderWebServiceParameter> PATHPARAMETERSHANDLER = new OneToManyHandler<OrderWebServiceParameter>(
	WsclientgeneratorConstants.TC.ORDERWEBSERVICEPARAMETER,
	false,
	"orderConfiguration",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	/**
	* {@link OneToManyHandler} for handling 1:n RESPONSEMAPPING's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<OrderWebServiceResponse> RESPONSEMAPPINGHANDLER = new OneToManyHandler<OrderWebServiceResponse>(
	WsclientgeneratorConstants.TC.ORDERWEBSERVICERESPONSE,
	false,
	"customerConfiguration",
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
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderWebServiceConfiguration.pathParameters</code> attribute.
	 * @return the pathParameters
	 */
	public Collection<OrderWebServiceParameter> getPathParameters(final SessionContext ctx)
	{
		return PATHPARAMETERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderWebServiceConfiguration.pathParameters</code> attribute.
	 * @return the pathParameters
	 */
	public Collection<OrderWebServiceParameter> getPathParameters()
	{
		return getPathParameters( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderWebServiceConfiguration.pathParameters</code> attribute. 
	 * @param value the pathParameters
	 */
	public void setPathParameters(final SessionContext ctx, final Collection<OrderWebServiceParameter> value)
	{
		PATHPARAMETERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderWebServiceConfiguration.pathParameters</code> attribute. 
	 * @param value the pathParameters
	 */
	public void setPathParameters(final Collection<OrderWebServiceParameter> value)
	{
		setPathParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pathParameters. 
	 * @param value the item to add to pathParameters
	 */
	public void addToPathParameters(final SessionContext ctx, final OrderWebServiceParameter value)
	{
		PATHPARAMETERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pathParameters. 
	 * @param value the item to add to pathParameters
	 */
	public void addToPathParameters(final OrderWebServiceParameter value)
	{
		addToPathParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pathParameters. 
	 * @param value the item to remove from pathParameters
	 */
	public void removeFromPathParameters(final SessionContext ctx, final OrderWebServiceParameter value)
	{
		PATHPARAMETERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pathParameters. 
	 * @param value the item to remove from pathParameters
	 */
	public void removeFromPathParameters(final OrderWebServiceParameter value)
	{
		removeFromPathParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderWebServiceConfiguration.responseMapping</code> attribute.
	 * @return the responseMapping
	 */
	public Collection<OrderWebServiceResponse> getResponseMapping(final SessionContext ctx)
	{
		return RESPONSEMAPPINGHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderWebServiceConfiguration.responseMapping</code> attribute.
	 * @return the responseMapping
	 */
	public Collection<OrderWebServiceResponse> getResponseMapping()
	{
		return getResponseMapping( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderWebServiceConfiguration.responseMapping</code> attribute. 
	 * @param value the responseMapping
	 */
	public void setResponseMapping(final SessionContext ctx, final Collection<OrderWebServiceResponse> value)
	{
		RESPONSEMAPPINGHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderWebServiceConfiguration.responseMapping</code> attribute. 
	 * @param value the responseMapping
	 */
	public void setResponseMapping(final Collection<OrderWebServiceResponse> value)
	{
		setResponseMapping( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to responseMapping. 
	 * @param value the item to add to responseMapping
	 */
	public void addToResponseMapping(final SessionContext ctx, final OrderWebServiceResponse value)
	{
		RESPONSEMAPPINGHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to responseMapping. 
	 * @param value the item to add to responseMapping
	 */
	public void addToResponseMapping(final OrderWebServiceResponse value)
	{
		addToResponseMapping( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from responseMapping. 
	 * @param value the item to remove from responseMapping
	 */
	public void removeFromResponseMapping(final SessionContext ctx, final OrderWebServiceResponse value)
	{
		RESPONSEMAPPINGHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from responseMapping. 
	 * @param value the item to remove from responseMapping
	 */
	public void removeFromResponseMapping(final OrderWebServiceResponse value)
	{
		removeFromResponseMapping( getSession().getSessionContext(), value );
	}
	
}
