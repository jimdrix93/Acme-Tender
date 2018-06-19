
package controllers.administrative;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.EvaluationCriteria;
import domain.EvaluationCriteriaType;
import domain.Tender;
import services.ActorService;
import services.EvaluationCriteriaService;
import services.EvaluationCriteriaTypeService;
import services.TenderService;

@Controller
@RequestMapping("/evaluationCriteria/administrative")
public class EvaluationCriteriaAdministrativeController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private EvaluationCriteriaService		evaluationCriteriaService;
	@Autowired
	private EvaluationCriteriaTypeService	evaluationCriteriaTypeService;
	@Autowired
	private TenderService					tenderService;
	@Autowired
	private ActorService					actorService;


	// Constructor -----------------------------------------------------------
	public EvaluationCriteriaAdministrativeController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tenderId) {

		final ModelAndView result = new ModelAndView("evaluationCriteria/administrative/create");

		Tender tender = this.tenderService.findOne(tenderId);
		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(tender.getAdministrative().getId() == actor.getId());

		final EvaluationCriteria evaluationCriteria = this.evaluationCriteriaService.create(tenderId);
		result.addObject("evaluationCriteria", evaluationCriteria);

		Collection<EvaluationCriteriaType> evaluationCriteriaTypes = this.evaluationCriteriaTypeService.findAll();
		result.addObject("evaluationCriteriaTypes", evaluationCriteriaTypes);

		result.addObject("tenderId", tenderId);

		return result;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int evaluationCriteriaId) {
		ModelAndView result;
		final EvaluationCriteria evaluationCriteria = this.evaluationCriteriaService.findOne(evaluationCriteriaId);

		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(evaluationCriteria.getTender().getAdministrative().getId() == actor.getId());

		result = this.createEditModelAndView(evaluationCriteria);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("evaluationCriteria") @Valid final EvaluationCriteria evaluationCriteria, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(evaluationCriteria);
		else
			try {
				this.evaluationCriteriaService.save(evaluationCriteria);
				result = new ModelAndView("redirect:/tender/display.do?tenderId=" + evaluationCriteria.getTender().getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(evaluationCriteria, "evaluationCriteria.commit.error");
			}
		return result;
	}

	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EvaluationCriteria evaluationCriteria, final BindingResult binding) {
		ModelAndView result;

		try {
			Actor actor = this.actorService.findByPrincipal();
			Assert.isTrue(evaluationCriteria.getTender().getAdministrative().getId() == actor.getId());

			this.evaluationCriteriaService.delete(evaluationCriteria);
			result = new ModelAndView("redirect:/tender/display.do?tenderId=" + evaluationCriteria.getTender().getId());

		} catch (final Throwable ooops) {
			if (ooops.getMessage().equals("evaluationCriteria.cannot.delete.in.use"))
				result = this.createEditModelAndView(evaluationCriteria, "evaluationCriteria.cannot.delete.in.use");
			else
				result = this.createEditModelAndView(evaluationCriteria, "evaluationCriteria.commit.error");
		}
		return result;
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tenderId) {

		ModelAndView result;

		result = new ModelAndView("evaluationCriteria/administrative/list");

		final Collection<EvaluationCriteria> evaluationCriterias = this.evaluationCriteriaService.findAllByTender(tenderId);
		result.addObject("evaluationCriterias", evaluationCriterias);

		Tender tender = this.tenderService.findOneToEdit(tenderId);
		result.addObject("tender", tender);

		return result;
	}

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final EvaluationCriteria evaluationCriteria) {
		final ModelAndView result;
		result = this.createEditModelAndView(evaluationCriteria, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EvaluationCriteria evaluationCriteria, final String message) {

		ModelAndView result = null;
		if (evaluationCriteria.getId() != 0)
			result = new ModelAndView("evaluationCriteria/administrative/edit");
		else
			result = new ModelAndView("evaluationCriteria/administrative/create");

		result.addObject("evaluationCriteria", evaluationCriteria);

		Collection<EvaluationCriteriaType> evaluationCriteriaTypes = this.evaluationCriteriaTypeService.findAll();
		result.addObject("evaluationCriteriaTypes", evaluationCriteriaTypes);

		result.addObject("tenderId", evaluationCriteria.getTender().getId());

		result.addObject("message", message);
		return result;
	}
}
