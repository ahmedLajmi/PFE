/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.controllers.admin;

import de.hybris.merchandise.storefront.util.CSRFTokenManager;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.addons.wsclientgenerator.controllers.WsclientgeneratorControllerConstants;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author Ahmed-LAJMI
 *
 */

@Controller
@RequestMapping("/admin")
public class WebServicesCallController extends AbstractPageController
{
	@RequestMapping(value = "/wscallform", method = RequestMethod.GET)
	public String getWsCallForm(final Model model)
	{
		return WsclientgeneratorControllerConstants.FORM_PAGE;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(final Model model, final HttpServletRequest request)
	{

		try
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId("wsCallContentPage"));
		}
		catch (final CMSItemNotFoundException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("CSRFToken", CSRFTokenManager.getTokenForSession(request.getSession()));
		return WsclientgeneratorControllerConstants.Test;
	}



}
