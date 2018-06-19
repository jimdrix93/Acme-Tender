
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.EvaluationCriteria;
import services.ActorService;
import services.EvaluationCriteriaService;

@Controller
@RequestMapping("/evaluationCriteria")
public class EvaluationCriteriaController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private EvaluationCriteriaService		evaluationCriteriaService;
	@Autowired
	private ActorService actorService;


	// Constructor -----------------------------------------------------------
	public EvaluationCriteriaController() {
		super();
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int evaluationCriteriaId) {

		ModelAndView result;

		EvaluationCriteria evaluationCriteria = this.evaluationCriteriaService.findOne(evaluationCriteriaId);
		Actor actor = this.actorService.findByPrincipal();
		
		result = new ModelAndView("evaluationCriteria/display");
		result.addObject("actor", actor);
		result.addObject("evaluationCriteria", evaluationCriteria);

		return result;

	}	
}
