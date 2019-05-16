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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


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
	public String formPage(final Model model, final HttpServletRequest request)
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

	@RequestMapping(value = "/downloadResponse", method = RequestMethod.POST)
	public void downloadResponse(final HttpServletResponse response, @RequestParam
	final String responseText, @RequestParam
	final String responseType)
	{
		System.out.println(responseText);
		System.out.println(responseType);
		String mimeType;
		/*
		 * if (responseType.equalsIgnoreCase("json")) { mimeType = "application/json"; } else { mimeType =
		 * "application/xml"; }
		 */
		mimeType = "application/text";
		response.setContentType(mimeType);

		response.setHeader("Content-Disposition", String.format("inline; filename=\response"));


		/*
		 * "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser
		 * setting
		 */
		//response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

		//response.setContentLength((int) file.length());

		final InputStream inputStream = new ByteArrayInputStream(responseText.getBytes(Charset.forName("UTF-8")));

		//Copy bytes from source to destination(outputstream in this example), closes both streams.
		try
		{
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
		catch (final IOException e)
		{
			e.printStackTrace();
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
