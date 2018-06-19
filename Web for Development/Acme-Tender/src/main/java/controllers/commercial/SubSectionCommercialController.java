
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
import domain.Offer;
import domain.SubSection;
import services.ActorService;
import services.ComboService;
import services.OfferService;
import services.SubSectionService;

@Controller
@RequestMapping("/subSection/commercial")
public class SubSectionCommercialController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private SubSectionService			subSectionService;
	@Autowired
	private ComboService				comboService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private OfferService  offerService;
	
	// Constructor -----------------------------------------------------------
	public SubSectionCommercialController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int offerId) {

		final ModelAndView result = new ModelAndView("subSection/commercial/create");
		
		Actor actor = this.actorService.findByPrincipal();
		Offer offer = this.offerService.findOne(offerId);
		Assert.isTrue(offer.getCommercial().getId() == actor.getId());

		final SubSection subSection = this.subSectionService.createByCommercialPropietary(offerId);
		result.addObject("subSection", subSection);
		result.addObject("requestURI", "subSection/commercial/edit.do");

		Collection<String> subSectionSectionsCombo = this.comboService.subSectionSections();
		result.addObject("subSectionSectionsCombo", subSectionSectionsCombo);
		result.addObject("request", false);
		result.addObject("requestId", 0);

		return result;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int subSectionId) {
		ModelAndView result;
		SubSection subSection = this.subSectionService.findOne(subSectionId);

		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(subSection.getCommercial().getId() == actor.getId());

		result = this.createEditModelAndView(subSection);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("subSection") @Valid final SubSection subSection, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(subSection);

		} else {
			try {
				this.subSectionService.save(subSection);
				result = new ModelAndView("redirect:/offer/display.do?offerId=" + subSection.getOffer().getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(subSection, "subSection.commit.error");
			}

		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SubSection subSection, final BindingResult binding) {
		ModelAndView result;

		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(subSection.getCommercial().getId() == actor.getId());

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

		ModelAndView result = null;
		if (subSection.getId() == 0)
			result = new ModelAndView("subSection/commercial/create");
		else
			result = new ModelAndView("subSection/commercial/edit");

		result.addObject("subSection", subSection);
		result.addObject("requestURI", "subSection/commercial/edit.do");
		result.addObject("message", message);
		Collection<String> subSectionSectionsCombo = this.comboService.subSectionSections();
		result.addObject("subSectionSectionsCombo", subSectionSectionsCombo);
		return result;
	}

}
