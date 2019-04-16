/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.enums.StockParameter;
import de.hybris.platform.addons.wsclientgenerator.exceptions.CreateWsRequestException;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceParameterModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.StockWebServiceConfigurationDao;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.L10NService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.StringWriter;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author Ahmed-LAJMI
 *
 */
public class OrderCreationInterceptor implements ValidateInterceptor, PrepareInterceptor
{

	@Autowired
	private EventService eventService;

	private L10NService l10NService;

	@Resource(name = "stockWebServiceConfigurationDao")
	private StockWebServiceConfigurationDao stockWebServiceConfigurationDao;

	@Resource(name = "userService")
	private UserService userService;

	private StockWebServiceConfigurationModel stockConfiguration;

	@Override
	public void onValidate(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
		/*
		 * if (model instanceof OrderModel) { stockConfiguration =
		 * stockWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.POST); if (stockConfiguration != null) {
		 * final OrderModel order = (OrderModel) model; final WSInvoke wsinvoke = new WSInvoke(); final
		 * List<AbstractOrderEntryModel> entries = order.getEntries(); ResponseEntity<String> response; for (final
		 * AbstractOrderEntryModel entry : entries) { if (stockConfiguration.getContentType().equals(RequestType.XML)) {
		 * try { response = wsinvoke.postRequest(stockConfiguration.getUrl(), prepareXMLRequest(entry),
		 * stockConfiguration.getAccept(), stockConfiguration.getContentType()); System.out.println(response.getBody()); }
		 *
		 * catch (final CreateWsRequestException e) { e.printStackTrace(); } catch (final InvokeWsException e) {
		 * e.printStackTrace(); } } else if (stockConfiguration.getContentType().equals(RequestType.JSON)) { //
		 *
		 * } else if (stockConfiguration.getContentType().equals(RequestType.FORM)) { //
		 *
		 * }
		 *
		 * } } }
		 */
	}


	private final String prepareXMLRequest(final AbstractOrderEntryModel entry) throws CreateWsRequestException
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
			transformer.transform(new DOMSource(root), new StreamResult(writer));
			return writer.getBuffer().toString();
		}
		catch (ParserConfigurationException | TransformerException e)
		{
			throw new CreateWsRequestException(e.getMessage());
		}
	}


	private final String prepareJSONRequest(final AbstractOrderEntryModel entry) throws CreateWsRequestException
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
			transformer.transform(new DOMSource(root), new StreamResult(writer));
			return writer.getBuffer().toString();
		}
		catch (ParserConfigurationException | TransformerException e)
		{
			throw new CreateWsRequestException(e.getMessage());
		}
	}



	@Override
	public void onPrepare(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
		/*
		 * if (model instanceof StockWebServiceConfigurationModel) { final StockWebServiceConfigurationModel
		 * stockConfiguration = (StockWebServiceConfigurationModel) model; if
		 * (stockConfiguration.getEnable().booleanValue()) { final Integer nbEnabledPriceConfiguration =
		 * stockWebServiceConfigurationDao .getCountWsEnabledConfiguration(stockConfiguration.getMethod()); if
		 * (nbEnabledPriceConfiguration.intValue() > 1) { throw new
		 * InterceptorException(getL10NService().getLocalizedString("error.message.txt")); } } }
		 */
		//System.out.println("i m here");
	}

	private L10NService getL10NService()
	{
		return this.l10NService;
	}

	@Autowired
	public void setL10NService(final L10NService l10NService)
	{
		this.l10NService = l10NService;
	}

}





