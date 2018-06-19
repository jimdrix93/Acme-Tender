
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ComboService;
import services.CompanyResultService;
import controllers.AbstractController;
import domain.Actor;
import domain.CompanyResult;

@Controller
@RequestMapping("/companyResult")
public class CompanyResultController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CompanyResultService	companyResultService;
	@Autowired
	private ActorService	actorService;


	// Constructor -----------------------------------------------------------
	public CompanyResultController() {
		super();
	}

	// Display ---------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int companyResultId) {
		ModelAndView result;
		
		Actor actor = this.actorService.findByPrincipal();
		CompanyResult companyResult = this.companyResultService.findOne(companyResultId);

		
		result = new ModelAndView("companyResult/display");
		result.addObject("companyResult", companyResult);
		result.addObject("actor", actor);

		return result;
	}

}
