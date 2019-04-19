/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.order.impl;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.RequestType;
import de.hybris.platform.addons.wsclientgenerator.enums.StockParameter;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.exceptions.InvokeWsException;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.order.WSCheckoutService;
import de.hybris.platform.addons.wsclientgenerator.tools.WSInvoke;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.StockWebServiceConfigurationDao;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCheckoutService;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderResult;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class DefaultWsCheckoutService extends DefaultCommerceCheckoutService implements WSCheckoutService
{

	@Resource(name = "stockWebServiceConfigurationDao")
	private StockWebServiceConfigurationDao stockWebServiceConfigurationDao;

	@Resource(name = "userService")
	private UserService userService;

	private StockWebServiceConfigurationModel stockConfiguration;

	private static final Logger LOG = Logger.getLogger(DefaultWsCheckoutService.class);

	@Override
	@Deprecated
	public OrderModel placeOrder(final CartModel cartModel) throws InvalidCartException
	{
		final OrderModel result = super.placeOrder(cartModel);
		wsTreatement(result);
		return result;
	}


	@Override
	@Deprecated
	public OrderModel placeOrder(final CartModel cartModel, final SalesApplication salesApplication) throws InvalidCartException
	{
		final OrderModel result = super.placeOrder(cartModel);
		wsTreatement(result);
		return result;
	}


	@Override
	public CommerceOrderResult placeOrder(final CommerceCheckoutParameter parameter) throws InvalidCartException
	{
		final CommerceOrderResult result = super.placeOrder(parameter);
		wsTreatement(result.getOrder());
		return result;
	}

	@Override
	public void wsTreatement(final OrderModel model)
	{
		stockConfiguration = stockWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.POST);
		if (stockConfiguration != null)
		{
			final WSInvoke wsinvoke = new WSInvoke();
			final List<AbstractOrderEntryModel> entries = model.getEntries();
			ResponseEntity<String> response;
			for (final AbstractOrderEntryModel entry : entries)
			{
				try
				{
					if (stockConfiguration.getContentType().equals(RequestType.XML))
					{

						response = wsinvoke.postRequest(stockConfiguration.getUrl(), prepareXMLRequest(entry),
								stockConfiguration.getAccept(), stockConfiguration.getContentType());
						System.out.println(response.getBody());

					}
					else if (stockConfiguration.getContentType().equals(RequestType.JSON))
					{
						response = wsinvoke.postRequest(stockConfiguration.getUrl(), prepareJSONRequest(entry),
								stockConfiguration.getAccept(), stockConfiguration.getContentType());
						System.out.println(response.getBody());

					}
					else if (stockConfiguration.getContentType().equals(RequestType.FORM))
					{
						response = wsinvoke.postRequest(stockConfiguration.getUrl(), prepareFORMRequest(entry),
								stockConfiguration.getAccept());
						System.out.println(response.getBody());
					}
				}
				catch (final CreateWsRequestException | InvokeWsException e)
				{
					LOG.error(e.getMessage());
				}
			}
		}
	}

	@Override
	public String prepareXMLRequest(final AbstractOrderEntryModel entry) throws CreateWsRequestException
	{
		try
		{
			final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			final Document document = documentBuilder.newDocument();
			final TransformerFactory tf = TransformerFactory.newInstance();
			final Transformer transformer = tf.newTransformer();
			final StringWriter writer = new StringWriter();
			final Element root = document.createElement("StockInformations");
			Element elem = document.createElement(stockConfiguration.getStockKey());

			elem.appendChild(document.createTextNode(entry.getQuantity().toString()));
			root.appendChild(elem);
			for (final StockWebServiceParameterModel additionelParam : stockConfiguration.getParameters())
			{
				if (additionelParam.getValue().equals(StockParameter.PRODUCTCODE))
				{
					elem = document.createElement(additionelParam.getKey());
					elem.appendChild(document.createTextNode(entry.getProduct().getCode()));
					root.appendChild(elem);
				}
				if (additionelParam.getValue().equals(StockParameter.CLIENTCODE))
				{
					if (!userService.isAnonymousUser(userService.getCurrentUser()))
					{
						elem = document.createElement(additionelParam.getKey());
						elem.appendChild(document.createTextNode(userService.getCurrentUser().getUid()));
						root.appendChild(elem);
					}
				}
			}
			for (final PersoWSParamModel persoParam : stockConfiguration.getPersonalisedParameters())
			{
				elem = document.createElement(persoParam.getKey());
				elem.appendChild(document.createTextNode(persoParam.getValue()));
				root.appendChild(elem);
			}
			for (final PersoWSParamModel securityParam : stockConfiguration.getSecurityParameters())
			{
				elem = document.createElement(securityParam.getKey());
				elem.appendChild(document.createTextNode(securityParam.getValue()));
				root.appendChild(elem);
			}
			transformer.transform(new DOMSource(root), new StreamResult(writer));
			return writer.getBuffer().toString();
		}
		catch (ParserConfigurationException | TransformerException e)
		{
			throw new CreateWsRequestException("Error in creating XML request! " + e.getMessage());
		}
	}

	@Override
	public String prepareJSONRequest(final AbstractOrderEntryModel entry) throws CreateWsRequestException
	{

		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode rootNode = mapper.createObjectNode();
		rootNode.put(stockConfiguration.getStockKey(), entry.getQuantity().toString());
		for (final StockWebServiceParameterModel additionelParam : stockConfiguration.getParameters())
		{
			if (additionelParam.getValue().equals(StockParameter.PRODUCTCODE))
			{
				rootNode.put(additionelParam.getKey(), entry.getProduct().getCode());
			}
			if (additionelParam.getValue().equals(StockParameter.CLIENTCODE))
			{
				if (!userService.isAnonymousUser(userService.getCurrentUser()))
				{
					rootNode.put(additionelParam.getKey(), userService.getCurrentUser().getUid());
				}
			}
		}
		for (final PersoWSParamModel persoParam : stockConfiguration.getPersonalisedParameters())
		{
			rootNode.put(persoParam.getKey(), persoParam.getValue());
		}
		for (final PersoWSParamModel securityParam : stockConfiguration.getSecurityParameters())
		{
			rootNode.put(securityParam.getKey(), securityParam.getValue());
		}
		try
		{
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
		}
		catch (final IOException e)
		{
			throw new CreateWsRequestException("Error in creating JSON request! " + e.getMessage());
		}
	}

	@Override
	public MultiValueMap<String, String> prepareFORMRequest(final AbstractOrderEntryModel entry)
	{

		final MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.add(stockConfiguration.getStockKey(), entry.getQuantity().toString());
		for (final StockWebServiceParameterModel additionelParam : stockConfiguration.getParameters())
		{
			if (additionelParam.getValue().equals(StockParameter.PRODUCTCODE))
			{
				request.add(additionelParam.getKey(), entry.getProduct().getCode());
			}
			if (additionelParam.getValue().equals(StockParameter.CLIENTCODE))
			{
				if (!userService.isAnonymousUser(userService.getCurrentUser()))
				{
					request.add(additionelParam.getKey(), userService.getCurrentUser().getUid());
				}
			}
		}
		for (final PersoWSParamModel persoParam : stockConfiguration.getPersonalisedParameters())
		{
			request.add(persoParam.getKey(), persoParam.getValue());
		}
		for (final PersoWSParamModel securityParam : stockConfiguration.getSecurityParameters())
		{
			request.add(securityParam.getKey(), securityParam.getValue());
		}
		return request;
	}


}
