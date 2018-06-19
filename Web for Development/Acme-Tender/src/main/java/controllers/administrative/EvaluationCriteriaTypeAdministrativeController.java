
package controllers.administrative;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EvaluationCriteriaTypeService;
import controllers.AbstractController;
import domain.EvaluationCriteriaType;

@Controller
@RequestMapping("/evaluationCriteriaType/administrative")
public class EvaluationCriteriaTypeAdministrativeController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	@Autowired
	private EvaluationCriteriaTypeService	evaluationCriteriaTypeService;


	// Constructor -----------------------------------------------------------
	public EvaluationCriteriaTypeAdministrativeController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final EvaluationCriteriaType evaluationCriteriaType = this.evaluationCriteriaTypeService.create();

		final ModelAndView result = new ModelAndView("evaluationCriteriaType/administrative/create");

		result.addObject("evaluationCriteriaType", evaluationCriteriaType);
		return result;
	}

	// Edit  ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int evaluationCriteriaTypeId) {
		ModelAndView result;
		final EvaluationCriteriaType evaluationCriteriaType = this.evaluationCriteriaTypeService.findOne(evaluationCriteriaTypeId);

		result = this.createEditModelAndView(evaluationCriteriaType);
		return result;
	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EvaluationCriteriaType evaluationCriteriaType, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(evaluationCriteriaType);
		else
			try {
				this.evaluationCriteriaTypeService.save(evaluationCriteriaType);
				result = new ModelAndView("redirect:/evaluationCriteriaType/administrative/list.do");
				
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(evaluationCriteriaType, "evaluationCriteriaType.commit.error");
			}
		return result;
	}
	
	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EvaluationCriteriaType evaluationCriteriaType, final BindingResult binding) {
		ModelAndView result;

		try {
			this.evaluationCriteriaTypeService.delete(evaluationCriteriaType);
			result = new ModelAndView("redirect:/evaluationCriteriaType/administrative/list.do");
			
		} catch (final Throwable ooops) {
			if (ooops.getMessage().equals("evaluationCriteriaType.cannot.delete.in.use")) 
				result = this.createEditModelAndView(evaluationCriteriaType, "evaluationCriteriaType.cannot.delete.in.use");
			else
				result = this.createEditModelAndView(evaluationCriteriaType, "evaluationCriteriaType.commit.error");
		}
		return result;
	}	

	// List EvaluationCriteriaTypes ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final Collection<EvaluationCriteriaType> evaluationCriteriaTypes = this.evaluationCriteriaTypeService.findAll();

		result = new ModelAndView("evaluationCriteriaType/administrative/list");
		result.addObject("evaluationCriteriaTypes", evaluationCriteriaTypes);

		return result;
	}

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final EvaluationCriteriaType evaluationCriteriaType) {
		final ModelAndView result;
		result = this.createEditModelAndView(evaluationCriteriaType, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EvaluationCriteriaType evaluationCriteriaType, final String message) {
		final ModelAndView result;
		
		if (evaluationCriteriaType.getId() == 0)
			result = new ModelAndView("evaluationCriteriaType/administrative/create");
		else
			result = new ModelAndView("evaluationCriteriaType/administrative/edit");

		result.addObject("evaluationCriteriaType", evaluationCriteriaType);
		result.addObject("message", message);
		return result;
	}
}
