
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Constant;
import domain.Offer;
import domain.SubSection;
import domain.Tender;
import forms.SearchForm;
import services.ActorService;
import services.ConfigurationService;
import services.OfferService;
import services.SubSectionService;

@Controller
@RequestMapping("/offer")
public class OfferController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private OfferService			offerService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private SubSectionService		subSectionService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructor -----------------------------------------------------------
	public OfferController() {
		super();
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int offerId) {

		ModelAndView result;

		Offer offer = offerService.findOne(offerId);
		Double benefitsPercentage = this.configurationService.findBenefitsPercentage();
		Actor actor = this.actorService.findByPrincipal();

		Assert.isTrue(this.offerService.canViewOffer(offerId));
		
		Collection<SubSection> subSections = this.subSectionService.findAllByOffer(offerId);

		Collection<SubSection> subSectionsAcreditation = new ArrayList<SubSection>();
		Collection<SubSection> subSectionsTechnical = new ArrayList<SubSection>();
		Collection<SubSection> subSectionsEconomical = new ArrayList<SubSection>();

		for (SubSection ss : subSections) {
			if (ss.getSection().equals(Constant.SECTION_ADMINISTRATIVE_ACREDITATION))
				subSectionsAcreditation.add(ss);
			if (ss.getSection().equals(Constant.SECTION_TECHNICAL_OFFER))
				subSectionsTechnical.add(ss);
			if (ss.getSection().equals(Constant.SECTION_ECONOMICAL_OFFER))
				subSectionsEconomical.add(ss);
		}

		result = new ModelAndView("offer/display");
		result.addObject("offer", offer);
		result.addObject("subSectionsAcreditation", subSectionsAcreditation);
		result.addObject("subSectionsTechnical", subSectionsTechnical);
		result.addObject("subSectionsEconomical", subSectionsEconomical);
		result.addObject("benefitsPercentage", benefitsPercentage);
		result.addObject("actor", actor);

		return result;

	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(Integer pageSize) {

		ModelAndView result;

		final Collection<Offer> offers = this.offerService.findAllPublished();
		Double benefitsPercentaje = this.configurationService.findBenefitsPercentage();
		Actor actor = this.actorService.findByPrincipal();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("benefitsPercentaje", benefitsPercentaje);
		result.addObject("actorId", actor.getId());
		result.addObject("requestUri", "offer/list.do");
		result.addObject("pageSize", (pageSize!=null)?pageSize:5);
		return result;
	}


	@RequestMapping(value = "/search")
	public ModelAndView create() {
		ModelAndView result;
		final SearchForm searchForm = new SearchForm();

		result = this.createEditModelAndView(searchForm);

		return result;
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "searchButton")
	public ModelAndView search(final HttpServletRequest request, @Valid final SearchForm searchForm, final BindingResult binding) {
		ModelAndView result;
		final String word = request.getParameter("word");
		result = this.searchResult(word, 5);

		return result;
	}

	// searchResult ---------------------------------------------------------------
	@RequestMapping(value = "/searchResult", method = RequestMethod.GET)
	public ModelAndView searchResult(@RequestParam final String word, Integer pageSize) {
		ModelAndView result;
		
		final Collection<Offer> offers = this.offerService.findOfferByKeyWord(word);
		final Double benefitsPercentaje = this.configurationService.findBenefitsPercentage();
		final Actor actor = this.actorService.findByPrincipal();

		result = new ModelAndView("offer/searchResult");
		result.addObject("requestUri", "offer/searchResult.do");
		result.addObject("offers", offers);
		result.addObject("benefitsPercentaje", benefitsPercentaje);
		result.addObject("actorId", actor.getId());		
		result.addObject("backSearch", true);
		result.addObject("word", word);
		result.addObject("pageSize", (pageSize!=null)?pageSize:5);

		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(final SearchForm searchForm) {
		final ModelAndView result;
		result = this.createEditModelAndView(searchForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SearchForm searchForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("offer/search");
		result.addObject("searchForm", searchForm);

		return result;
	}
	

}
