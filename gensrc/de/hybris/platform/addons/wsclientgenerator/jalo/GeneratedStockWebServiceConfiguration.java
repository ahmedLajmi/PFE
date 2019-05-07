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
import de.hybris.platform.addons.wsclientgenerator.jalo.StockWebServiceParameter;
import de.hybris.platform.addons.wsclientgenerator.jalo.StockWebServiceResponse;
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
	/** Qualifier of the <code>StockWebServiceConfiguration.parameters</code> attribute **/
	public static final String PARAMETERS = "parameters";
	/** Qualifier of the <code>StockWebServiceConfiguration.pathParameters</code> attribute **/
	public static final String PATHPARAMETERS = "pathParameters";
	/** Qualifier of the <code>StockWebServiceConfiguration.responseMapping</code> attribute **/
	public static final String RESPONSEMAPPING = "responseMapping";
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
	/**
	* {@link OneToManyHandler} for handling 1:n PATHPARAMETERS's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<StockWebServiceParameter> PATHPARAMETERSHANDLER = new OneToManyHandler<StockWebServiceParameter>(
	WsclientgeneratorConstants.TC.STOCKWEBSERVICEPARAMETER,
	false,
	"stockConfiguration",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	/**
	* {@link OneToManyHandler} for handling 1:n RESPONSEMAPPING's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<StockWebServiceResponse> RESPONSEMAPPINGHANDLER = new OneToManyHandler<StockWebServiceResponse>(
	WsclientgeneratorConstants.TC.STOCKWEBSERVICERESPONSE,
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
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.pathParameters</code> attribute.
	 * @return the pathParameters
	 */
	public Collection<StockWebServiceParameter> getPathParameters(final SessionContext ctx)
	{
		return PATHPARAMETERSHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.pathParameters</code> attribute.
	 * @return the pathParameters
	 */
	public Collection<StockWebServiceParameter> getPathParameters()
	{
		return getPathParameters( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.pathParameters</code> attribute. 
	 * @param value the pathParameters
	 */
	public void setPathParameters(final SessionContext ctx, final Collection<StockWebServiceParameter> value)
	{
		PATHPARAMETERSHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.pathParameters</code> attribute. 
	 * @param value the pathParameters
	 */
	public void setPathParameters(final Collection<StockWebServiceParameter> value)
	{
		setPathParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pathParameters. 
	 * @param value the item to add to pathParameters
	 */
	public void addToPathParameters(final SessionContext ctx, final StockWebServiceParameter value)
	{
		PATHPARAMETERSHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pathParameters. 
	 * @param value the item to add to pathParameters
	 */
	public void addToPathParameters(final StockWebServiceParameter value)
	{
		addToPathParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pathParameters. 
	 * @param value the item to remove from pathParameters
	 */
	public void removeFromPathParameters(final SessionContext ctx, final StockWebServiceParameter value)
	{
		PATHPARAMETERSHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pathParameters. 
	 * @param value the item to remove from pathParameters
	 */
	public void removeFromPathParameters(final StockWebServiceParameter value)
	{
		removeFromPathParameters( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.responseMapping</code> attribute.
	 * @return the responseMapping
	 */
	public Collection<StockWebServiceResponse> getResponseMapping(final SessionContext ctx)
	{
		return RESPONSEMAPPINGHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>StockWebServiceConfiguration.responseMapping</code> attribute.
	 * @return the responseMapping
	 */
	public Collection<StockWebServiceResponse> getResponseMapping()
	{
		return getResponseMapping( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.responseMapping</code> attribute. 
	 * @param value the responseMapping
	 */
	public void setResponseMapping(final SessionContext ctx, final Collection<StockWebServiceResponse> value)
	{
		RESPONSEMAPPINGHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>StockWebServiceConfiguration.responseMapping</code> attribute. 
	 * @param value the responseMapping
	 */
	public void setResponseMapping(final Collection<StockWebServiceResponse> value)
	{
		setResponseMapping( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to responseMapping. 
	 * @param value the item to add to responseMapping
	 */
	public void addToResponseMapping(final SessionContext ctx, final StockWebServiceResponse value)
	{
		RESPONSEMAPPINGHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to responseMapping. 
	 * @param value the item to add to responseMapping
	 */
	public void addToResponseMapping(final StockWebServiceResponse value)
	{
		addToResponseMapping( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from responseMapping. 
	 * @param value the item to remove from responseMapping
	 */
	public void removeFromResponseMapping(final SessionContext ctx, final StockWebServiceResponse value)
	{
		RESPONSEMAPPINGHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from responseMapping. 
	 * @param value the item to remove from responseMapping
	 */
	public void removeFromResponseMapping(final StockWebServiceResponse value)
	{
		removeFromResponseMapping( getSession().getSessionContext(), value );
	}
	
}
