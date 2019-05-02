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
package de.hybris.platform.addons.wsclientgenerator.constants;

/**
 * @deprecated use constants in Model classes instead
 */
@Deprecated
@SuppressWarnings({"unused","cast","PMD"})
public class GeneratedWsclientgeneratorConstants
{
	public static final String EXTENSIONNAME = "wsclientgenerator";
	public static class TC
	{
		public static final String CUSTOMERMAPPINGRESPONSE = "CustomerMappingResponse".intern();
		public static final String CUSTOMERPARAMETER = "CustomerParameter".intern();
		public static final String CUSTOMERWEBSERVICECONFIGURATION = "CustomerWebServiceConfiguration".intern();
		public static final String CUSTOMERWEBSERVICEPARAMETER = "CustomerWebServiceParameter".intern();
		public static final String CUSTOMERWEBSERVICERESPONSE = "CustomerWebServiceResponse".intern();
		public static final String METHODTYPE = "MethodType".intern();
		public static final String MODETYPE = "ModeType".intern();
		public static final String ORDERMAPPINGRESPONSE = "OrderMappingResponse".intern();
		public static final String ORDERPARAMETER = "OrderParameter".intern();
		public static final String ORDERWEBSERVICECONFIGURATION = "OrderWebServiceConfiguration".intern();
		public static final String ORDERWEBSERVICEPARAMETER = "OrderWebServiceParameter".intern();
		public static final String ORDERWEBSERVICERESPONSE = "OrderWebServiceResponse".intern();
		public static final String PERSOWSPARAM = "PersoWSParam".intern();
		public static final String PRICEMAPPINGRESPONSE = "PriceMappingResponse".intern();
		public static final String PRICEPARAMETER = "PriceParameter".intern();
		public static final String PRICEWEBSERVICECONFIGURATION = "PriceWebServiceConfiguration".intern();
		public static final String PRICEWEBSERVICEPARAMETER = "PriceWebServiceParameter".intern();
		public static final String PRICEWEBSERVICERESPONSE = "PriceWebServiceResponse".intern();
		public static final String REQUESTTYPE = "RequestType".intern();
		public static final String RESPONSETYPE = "ResponseType".intern();
		public static final String STOCKMAPPINGRESPONSE = "StockMappingResponse".intern();
		public static final String STOCKPARAMETER = "StockParameter".intern();
		public static final String STOCKWEBSERVICECONFIGURATION = "StockWebServiceConfiguration".intern();
		public static final String STOCKWEBSERVICEPARAMETER = "StockWebServiceParameter".intern();
		public static final String STOCKWEBSERVICERESPONSE = "StockWebServiceResponse".intern();
		public static final String WEBSERVICECONFIGURATION = "WebServiceConfiguration".intern();
		public static final String WEBSERVICEPARAMETER = "WebServiceParameter".intern();
	}
	public static class Attributes
	{
		// no constants defined.
	}
	public static class Enumerations
	{
		public static class CustomerMappingResponse
		{
			public static final String UID = "uid".intern();
			public static final String FIRSTNAME = "firstName".intern();
			public static final String LASTNAME = "lastName".intern();
			public static final String TITLECODE = "titleCode".intern();
			public static final String SUCCESSCODE = "successCode".intern();
		}
		public static class CustomerParameter
		{
			public static final String UID = "uid".intern();
			public static final String FIRSTNAME = "firstName".intern();
			public static final String LASTNAME = "lastName".intern();
			public static final String OLDEMAIL = "oldEmail".intern();
			public static final String NEWEMAIL = "newEmail".intern();
			public static final String TITLECODE = "titleCode".intern();
		}
		public static class MethodType
		{
			public static final String GET = "get".intern();
			public static final String POST = "post".intern();
		}
		public static class ModeType
		{
			public static final String ONLYWITHWEBSERVICE = "onlyWithWebService".intern();
			public static final String WEBSERVICENATIVE = "webServiceNative".intern();
			public static final String NATIVEWEBSERVICE = "NativeWebService".intern();
		}
		public static class OrderMappingResponse
		{
			public static final String STATUS = "status".intern();
			public static final String SUCCESSCODE = "successCode".intern();
		}
		public static class OrderParameter
		{
			public static final String UID = "uid".intern();
			public static final String CODE = "code".intern();
			public static final String DATE = "date".intern();
		}
		public static class PriceMappingResponse
		{
			public static final String PRICE = "price".intern();
			public static final String CURRENCY = "currency".intern();
			public static final String SUCCESSCODE = "successCode".intern();
		}
		public static class PriceParameter
		{
			public static final String UID = "uid".intern();
			public static final String CODE = "code".intern();
			public static final String SUPERCATEGORIES = "Supercategories".intern();
		}
		public static class RequestType
		{
			public static final String JSON = "json".intern();
			public static final String XML = "xml".intern();
			public static final String FORM = "form".intern();
		}
		public static class ResponseType
		{
			public static final String JSON = "json".intern();
			public static final String XML = "xml".intern();
		}
		public static class StockMappingResponse
		{
			public static final String STOCKVALUE = "stockValue".intern();
			public static final String SUCCESSCODE = "successCode".intern();
		}
		public static class StockParameter
		{
			public static final String CODE = "code".intern();
			public static final String UID = "uid".intern();
			public static final String ORDERCODE = "orderCode".intern();
			public static final String STOCKVALUE = "stockValue".intern();
		}
	}
	public static class Relations
	{
		public static final String CUSTOMERWEBSERVICECONFIGURATIONHEADERPARAMETERRELATION = "CustomerWebServiceConfigurationHeaderParameterRelation".intern();
		public static final String CUSTOMERWEBSERVICECONFIGURATIONPARAMETERRELATION = "CustomerWebServiceConfigurationParameterRelation".intern();
		public static final String CUSTOMERWEBSERVICECONFIGURATIONRESPONSEMAPPINGRRELATION = "CustomerWebServiceConfigurationResponseMappingrRelation".intern();
		public static final String ORDERWEBSERVICECONFIGURATIONHEADERPARAMETERRELATION = "OrderWebServiceConfigurationHeaderParameterRelation".intern();
		public static final String ORDERWEBSERVICECONFIGURATIONPARAMETERRELATION = "OrderWebServiceConfigurationParameterRelation".intern();
		public static final String ORDERWEBSERVICECONFIGURATIONRESPONSEMAPPINGRRELATION = "OrderWebServiceConfigurationResponseMappingrRelation".intern();
		public static final String PRICEWEBSERVICECONFIGURATIONHEADERPARAMETERRELATION = "PriceWebServiceConfigurationHeaderParameterRelation".intern();
		public static final String PRICEWEBSERVICECONFIGURATIONPARAMETERRELATION = "PriceWebServiceConfigurationParameterRelation".intern();
		public static final String PRICEWEBSERVICECONFIGURATIONRESPONSEMAPPINGRRELATION = "PriceWebServiceConfigurationResponseMappingrRelation".intern();
		public static final String STOCKWEBSERVICECONFIGURATIONHEADERPARAMETERRELATION = "StockWebServiceConfigurationHeaderParameterRelation".intern();
		public static final String STOCKWEBSERVICECONFIGURATIONPARAMETERRELATION = "StockWebServiceConfigurationParameterRelation".intern();
		public static final String STOCKWEBSERVICECONFIGURATIONRESPONSEMAPPINGRRELATION = "StockWebServiceConfigurationResponseMappingrRelation".intern();
		public static final String WEBSERVICECONFIGURATIONHEADERSPARAMETERRELATION = "WebServiceConfigurationHeadersParameterRelation".intern();
		public static final String WEBSERVICECONFIGURATIONPARAMETERRELATION = "WebServiceConfigurationParameterRelation".intern();
	}
	
	protected GeneratedWsclientgeneratorConstants()
	{
		// private constructor
	}
	
	
}
