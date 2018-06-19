
package controllers.administrative;

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
import domain.SubSection;
import services.ActorService;
import services.SubSectionService;

@Controller
@RequestMapping("/subSection/administrative")
public class SubSectionAdministrativeController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private SubSectionService				subSectionService;
	@Autowired
	private ActorService					actorService;


	// Constructor -----------------------------------------------------------
	public SubSectionAdministrativeController() {
		super();
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int subSectionId) {
		ModelAndView result;
		SubSection subSection = this.subSectionService.findOne(subSectionId);

		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(subSection.getAdministrative().getId() == actor.getId());

		result = this.createEditModelAndView(subSection);
		result.addObject("request", false);
		result.addObject("requestId", 0);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("subSection") @Valid final SubSection subSection, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(subSection);

		} else
			try {
				this.subSectionService.save(subSection);
				result = new ModelAndView("redirect:/offer/display.do?offerId=" + subSection.getOffer().getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(subSection, "subSection.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SubSection subSection, final BindingResult binding) {
		ModelAndView result;

		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(subSection.getAdministrative().getId() == actor.getId());

		try {
			this.subSectionService.delete(subSection);
			result = new ModelAndView("redirect:/offer/display.do?offerId=" + subSection.getOffer().getId());

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(subSection, "subSection.commit.error");
		}
		return result;
	}

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final SubSection subSection) {
		final ModelAndView result;
		result = this.createEditModelAndView(subSection, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SubSection subSection, final String message) {

		final ModelAndView result = new ModelAndView("subSection/administrative/edit");
		result.addObject("subSection", subSection);
		result.addObject("requestUri", "subSection/administrative/edit.do");
		result.addObject("message", message);
		return result;
	}

}
