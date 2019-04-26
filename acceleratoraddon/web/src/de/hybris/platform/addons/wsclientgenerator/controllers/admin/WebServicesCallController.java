/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.controllers.admin;

import de.hybris.merchandise.storefront.util.CSRFTokenManager;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.addons.wsclientgenerator.controllers.WsclientgeneratorControllerConstants;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.Set;

import javax.annotation.Resource;
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
	@Resource(name = "userService")
	private UserService userService;

	public static final String FORWARD_PREFIX = "forward:";


	@RequireHardLogIn
	@RequestMapping(value = "/wsClientGeneratorSimulator", method = RequestMethod.GET)
	public String test(final Model model, final HttpServletRequest request)
	{
		final Set<PrincipalGroupModel> groups = userService.getCurrentUser().getAllgroups();
		if (checkAutorization(groups))
		{
			try
			{
				storeCmsPageInModel(model, getContentPageForLabelOrId("wsCallContentPage"));
			}
			catch (final CMSItemNotFoundException e)
			{
				e.printStackTrace();
			}

			model.addAttribute("CSRFToken", CSRFTokenManager.getTokenForSession(request.getSession()));
			return WsclientgeneratorControllerConstants.SIMULATION_PAGE;
		}
		else
		{
			return FORWARD_PREFIX + "/404";
		}

	}

	private boolean checkAutorization(final Set<PrincipalGroupModel> groups)
	{
		for (final PrincipalGroupModel group : groups)
		{
			if (group.getUid().equalsIgnoreCase("webServiceClientGroup"))
			{
				return true;
			}
		}
		return false;
	}



}
