
package controllers.commercial;

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
import domain.SubSection;
import domain.SubSectionEvaluationCriteria;
import services.ActorService;
import services.EvaluationCriteriaService;
import services.SubSectionEvaluationCriteriaService;
import services.SubSectionService;

@Controller
@RequestMapping("/subSectionEvaluationCriteria/commercial")
public class SubSectionEvaluationCriteriaCommercialController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private SubSectionEvaluationCriteriaService	subSectionEvaluationCriteriaService;
	@Autowired
	private EvaluationCriteriaService			evaluationCriteriaService;
	@Autowired
	private SubSectionService					subSectionService;
	@Autowired
	private ActorService actorService;


	// Constructor -----------------------------------------------------------
	public SubSectionEvaluationCriteriaCommercialController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int subSectionId) {

		ModelAndView result = new ModelAndView("subSectionEvaluationCriteria/commercial/create");

		SubSection subSection = this.subSectionService.findOne(subSectionId);
		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(subSection.getCommercial().getId() == actor.getId());
		
		SubSectionEvaluationCriteria subSectionEvaluationCriteria = this.subSectionEvaluationCriteriaService.create(subSectionId);
		result.addObject("subSectionEvaluationCriteria", subSectionEvaluationCriteria);

		Collection<EvaluationCriteria> evaluationCriterias = this.evaluationCriteriaService.findAllByTender(subSectionEvaluationCriteria.getSubSection().getOffer().getTender().getId());
		result.addObject("evaluationCriterias", evaluationCriterias);

		result.addObject("subSectionId", subSectionId);

		return result;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int subSectionEvaluationCriteriaId) {
		ModelAndView result;
		final SubSectionEvaluationCriteria subSectionEvaluationCriteria = this.subSectionEvaluationCriteriaService.findOne(subSectionEvaluationCriteriaId);
		
		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(subSectionEvaluationCriteria.getSubSection().getCommercial().getId() == actor.getId());

		result = this.createEditModelAndView(subSectionEvaluationCriteria);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("subSectionEvaluationCriteria") @Valid final SubSectionEvaluationCriteria subSectionEvaluationCriteria, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(subSectionEvaluationCriteria);
		else
			try {
				this.subSectionEvaluationCriteriaService.save(subSectionEvaluationCriteria);
				result = new ModelAndView("redirect:/subSection/display.do?subSectionId=" + subSectionEvaluationCriteria.getSubSection().getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(subSectionEvaluationCriteria, "subSectionEvaluationCriteria.commit.error");
			}
		return result;
	}

	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SubSectionEvaluationCriteria subSectionEvaluationCriteria, final BindingResult binding) {
		ModelAndView result;

		try {
			Actor actor = this.actorService.findByPrincipal();
			Assert.isTrue(subSectionEvaluationCriteria.getSubSection().getCommercial().getId() == actor.getId());			
			
			this.subSectionEvaluationCriteriaService.delete(subSectionEvaluationCriteria);
			result = new ModelAndView("redirect:/subSection/display.do?subSectionId=" + subSectionEvaluationCriteria.getSubSection().getId());

		} catch (final Throwable ooops) {
			result = this.createEditModelAndView(subSectionEvaluationCriteria, "subSectionEvaluationCriteria.commit.error");
		}
		return result;
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int subSectionId) {

		ModelAndView result;

		final Collection<SubSectionEvaluationCriteria> subSectionEvaluationCriterias = this.subSectionEvaluationCriteriaService.findAllBySubSection(subSectionId);

		result = new ModelAndView("subSectionEvaluationCriteria/commercial/list");
		result.addObject("subSectionEvaluationCriterias", subSectionEvaluationCriterias);
		result.addObject("subSectionId", subSectionId);

		SubSection subSection = this.subSectionService.findOne(subSectionId);
		result.addObject("offerId", subSection.getOffer().getId());

		return result;
	}

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final SubSectionEvaluationCriteria subSectionEvaluationCriteria) {
		final ModelAndView result;
		result = this.createEditModelAndView(subSectionEvaluationCriteria, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SubSectionEvaluationCriteria subSectionEvaluationCriteria, final String message) {

		ModelAndView result = null;
		if (subSectionEvaluationCriteria.getId()==0)
			result = new ModelAndView("subSectionEvaluationCriteria/commercial/create");
		else
			result = new ModelAndView("subSectionEvaluationCriteria/commercial/edit");
		
		result.addObject("subSectionEvaluationCriteria", subSectionEvaluationCriteria);

		Collection<EvaluationCriteria> evaluationCriterias = this.evaluationCriteriaService.findAllByTender(subSectionEvaluationCriteria.getSubSection().getOffer().getTender().getId());
		result.addObject("evaluationCriterias", evaluationCriterias);

		result.addObject("subSectionId", subSectionEvaluationCriteria.getSubSection().getId());

		result.addObject("message", message);
		return result;
	}
}
