/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.interceptors;

import de.hybris.platform.addons.wsclientgenerator.enums.MethodType;
import de.hybris.platform.addons.wsclientgenerator.enums.ResponseType;
import de.hybris.platform.addons.wsclientgenerator.model.PersoWSParamModel;
import de.hybris.platform.addons.wsclientgenerator.model.StockWebServiceConfigurationModel;
import de.hybris.platform.addons.wsclientgenerator.webserviceconfiguration.dao.StockWebServiceConfigurationDao;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.L10NService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

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

	private StockWebServiceConfigurationModel stockConfiguration;

	@Override
	public void onValidate(final Object model, final InterceptorContext ctx) throws InterceptorException
	{


		stockConfiguration = stockWebServiceConfigurationDao.getWsEnabledConfiguration(MethodType.POST);
		if (stockConfiguration != null)
		{
			if (stockConfiguration.getContentType().equals(ResponseType.XML))
			{
				final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder;
				try
				{
					documentBuilder = documentFactory.newDocumentBuilder();
					final Document document = documentBuilder.newDocument();
					final Element root = document.createElement("StockInformations");
					for (final PersoWSParamModel persoParam : stockConfiguration.getPersonalisedParameters())
					{
						final Element elem = document.createElement(persoParam.getKey());
						elem.appendChild(document.createTextNode(persoParam.getValue()));
						root.appendChild(elem);
					}

					final TransformerFactory tf = TransformerFactory.newInstance();
					final Transformer transformer;
					transformer = tf.newTransformer();
					final StringWriter writer = new StringWriter();
					transformer.transform(new DOMSource(root), new StreamResult(writer));
					final String xmlString = writer.getBuffer().toString();
					System.out.println(xmlString);
				}
				catch (final ParserConfigurationException | TransformerException e)
				{
					e.printStackTrace();
				}

			}
		}
		//final String url = stockConfiguration.getUrl() + "/";
		//final WSInvoke wsinvoke = new WSInvoke();
		//final ResponseEntity<String> response = wsinvoke.get(stockConfiguration.getUrl(), "", stockConfiguration.getAccept());

		/*
		 * System.out.println(response.getBody()); final ObjectMapper mapper = new ObjectMapper(); try { final JsonNode
		 * root = mapper.readTree(response.getBody()); final JsonNode stockValue =
		 * root.path(stockConfiguration.getStockKey()); return new Long(stockValue.asLong());
		 *
		 * } catch (final Exception e) { LOG.error("Erreur lors du parsing de la réponse"); return
		 * super.getStockLevelForProductAndBaseStore(product, baseStore); }
		 */

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





