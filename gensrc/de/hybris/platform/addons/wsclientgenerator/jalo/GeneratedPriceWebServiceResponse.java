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
import de.hybris.platform.addons.wsclientgenerator.jalo.PriceWebServiceConfiguration;
import de.hybris.platform.addons.wsclientgenerator.jalo.WebServiceParameter;
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
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem PriceWebServiceResponse}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedPriceWebServiceResponse extends WebServiceParameter
{
	/** Qualifier of the <code>PriceWebServiceResponse.value</code> attribute **/
	public static final String VALUE = "value";
	/** Qualifier of the <code>PriceWebServiceResponse.customerConfiguration</code> attribute **/
	public static final String CUSTOMERCONFIGURATION = "customerConfiguration";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n CUSTOMERCONFIGURATION's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedPriceWebServiceResponse> CUSTOMERCONFIGURATIONHANDLER = new BidirectionalOneToManyHandler<GeneratedPriceWebServiceResponse>(
	WsclientgeneratorConstants.TC.PRICEWEBSERVICERESPONSE,
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
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(WebServiceParameter.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(VALUE, AttributeMode.INITIAL);
		tmp.put(CUSTOMERCONFIGURATION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		CUSTOMERCONFIGURATIONHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceResponse.customerConfiguration</code> attribute.
	 * @return the customerConfiguration
	 */
	public PriceWebServiceConfiguration getCustomerConfiguration(final SessionContext ctx)
	{
		return (PriceWebServiceConfiguration)getProperty( ctx, CUSTOMERCONFIGURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceResponse.customerConfiguration</code> attribute.
	 * @return the customerConfiguration
	 */
	public PriceWebServiceConfiguration getCustomerConfiguration()
	{
		return getCustomerConfiguration( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceResponse.customerConfiguration</code> attribute. 
	 * @param value the customerConfiguration
	 */
	public void setCustomerConfiguration(final SessionContext ctx, final PriceWebServiceConfiguration value)
	{
		CUSTOMERCONFIGURATIONHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceResponse.customerConfiguration</code> attribute. 
	 * @param value the customerConfiguration
	 */
	public void setCustomerConfiguration(final PriceWebServiceConfiguration value)
	{
		setCustomerConfiguration( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceResponse.value</code> attribute.
	 * @return the value
	 */
	public EnumerationValue getValue(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, VALUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceWebServiceResponse.value</code> attribute.
	 * @return the value
	 */
	public EnumerationValue getValue()
	{
		return getValue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceResponse.value</code> attribute. 
	 * @param value the value
	 */
	public void setValue(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, VALUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceWebServiceResponse.value</code> attribute. 
	 * @param value the value
	 */
	public void setValue(final EnumerationValue value)
	{
		setValue( getSession().getSessionContext(), value );
	}
	
}
