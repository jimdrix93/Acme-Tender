
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.OfferService;
import controllers.AbstractController;
import domain.Actor;
import domain.Offer;

@Controller
@RequestMapping("/offer/administrator")
public class OfferAdminController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private OfferService			offerService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructor -----------------------------------------------------------
	public OfferAdminController() {
		super();
	}

	// List Offers with taboo words ---------------------------------------------------------------
	@RequestMapping(value = "/listWithTabooWord", method = RequestMethod.GET)
	public ModelAndView listOffer(final Integer pageSize) {

		final ModelAndView result;

		final Collection<Offer> offers = this.offerService.findAllOfferWithTabooWords();
		final Double benefitsPercentaje = this.configurationService.findBenefitsPercentage();
		final Actor actor = this.actorService.findByPrincipal();

		result = new ModelAndView("offer/administrator/list");
		result.addObject("offers", offers);
		result.addObject("benefitsPercentaje", benefitsPercentaje);
		result.addObject("actorId", actor.getId());
		result.addObject("requestUri", "offer/administrator/listWithTabooWord.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);

		return result;

	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/listNotPublished", method = RequestMethod.GET)
	public ModelAndView listOffersByPropietary(final Integer pageSize) {

		ModelAndView result;

		final Collection<Offer> offers = this.offerService.findAllNotPublished();
		final Double benefitsPercentaje = this.configurationService.findBenefitsPercentage();
		final Actor actor = this.actorService.findByPrincipal();

		result = new ModelAndView("offer/listNotPublished");
		result.addObject("offers", offers);
		result.addObject("requestUri", "offer/administrator/listNotPublished.do");
		result.addObject("benefitsPercentaje", benefitsPercentaje);
		result.addObject("actorId", actor.getId());
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);

		return result;
	}

	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = false) final Integer offerId) {
		ModelAndView result;

		Assert.notNull(offerId);
		final Offer offer = this.offerService.findOne(offerId);

		try {
			this.offerService.deleteByAdmin(offer);
			result = new ModelAndView("redirect: listWithTabooWord.do");

		} catch (final Throwable ooops) {
			result = new ModelAndView("redirect: listWithTabooWord.do");
			result.addObject("message", "tabooWord.commit.error");
		}
		return result;
	}

}
