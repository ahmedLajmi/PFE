/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.controllers.admin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author Ahmed-LAJMI
 *
 */

@Controller
@Scope("tenant")
@RequestMapping("/admin/wsclientgenerator")
public class WebServicesCallController
{
	@RequestMapping(value = "/wscallform", method = RequestMethod.GET)
	public String getWsCallForm()
	{
		return "addon:/wsclientgenerator/pages/test";
	}

}
