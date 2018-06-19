
package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/info")
public class InfoController extends AbstractController {

	// Services ---------------------------------------------------------------


	// Constructor -----------------------------------------------------------
	public InfoController() {
		super();
	}

	//Display
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public ModelAndView display() {

		ModelAndView result = new ModelAndView("info/accessDenied");
		return result;

	}


}
