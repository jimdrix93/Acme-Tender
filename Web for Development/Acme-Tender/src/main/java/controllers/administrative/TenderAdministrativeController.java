
package controllers.administrative;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.Category;
import domain.Constant;
import domain.Tender;
import services.ActorService;
import services.CategoryService;
import services.ConfigurationService;
import services.TenderService;

@Controller
@RequestMapping("/tender/administrative")
public class TenderAdministrativeController extends AbstractController {

	// Supporting services -----------------------------------------------------
	@Autowired
	TenderService	tenderService;
	@Autowired
	MessageSource	messageSource;
	@Autowired
	CategoryService	categoryService;
	@Autowired
	ActorService	actorService;
	@Autowired
	private ConfigurationService		configurationService;	


	// Constructors -----------------------------------------------------------
	public TenderAdministrativeController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Tender tender = this.tenderService.create();

		result = this.createEditModelAndView(tender);
		return result;
	}

	// Edit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tenderId) {
		ModelAndView result;
		final Tender tender = this.tenderService.findOneToEdit(tenderId);
		Assert.notNull(tender);

		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(tender.getAdministrative().getId() == actor.getId());

		result = this.createEditModelAndView(tender);

		return result;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@Valid final Tender tender, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tender);
		else
			try {
				this.tenderService.save(tender);
				result = new ModelAndView("redirect:/tender/administrative/list.do");
				result.addObject("requestUri", "tender/administrative/list.do");

			} catch (final Throwable ooops) {
				//								if (ooops.getMessage() == "Invalid bulletinDate")
				//									result = this.createEditModelAndView(tender, "tender.commit.error.invalidBulletinDate");
				if (ooops.getMessage() == "Invalid openingDate")
					result = this.createEditModelAndView(tender, "tender.commit.error.invalidOpeningDate");
				else if (ooops.getMessage() == "Invalid maxPresentationDate")
					result = this.createEditModelAndView(tender, "tender.commit.error.maxPresentationDate");
				else
					result = this.createEditModelAndView(tender, "tender.commit.error");
			}
		return result;
	}

	// List ------------------------------------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		final Double benefitsPercentaje = this.configurationService.findBenefitsPercentage();
		final Collection<Tender> tenders = this.tenderService.findAllByAdministrative();
		Actor actor = this.actorService.findByPrincipal();

		result = new ModelAndView("tender/administrative/list");
		result.addObject("tenders", tenders);
		result.addObject("actor", actor);
		result.addObject("benefitsPercentaje", benefitsPercentaje);		
		result.addObject("myTender", true);
		result.addObject("requestUri", "tender/administrative/list.do");

		return result;
	}

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final Tender tender) {
		final ModelAndView result;
		result = this.createEditModelAndView(tender, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Tender tender, final String message) {
		ModelAndView result;
		final Map<String, String> interests = this.getInterestList();
		final Collection<Category> categories = this.categoryService.findAll();

		if (tender.getId() != 0)
			result = new ModelAndView("tender/edit");
		else
			result = new ModelAndView("tender/create");

		result.addObject("tender", tender);
		result.addObject("message", message);
		result.addObject("interest", interests);
		result.addObject("category", categories);
		result.addObject("requestUri", "tender/administrative/edit.do");

		return result;
	}

	private Map<String, String> getInterestList() {
		final Locale locale = LocaleContextHolder.getLocale();
		final String low = this.messageSource.getMessage("tender.interest.low", null, "tender.interest.low", locale);
		final String medium = this.messageSource.getMessage("tender.interest.medium", null, "tender.interest.medium", locale);
		final String high = this.messageSource.getMessage("tender.interest.high", null, "tender.interest.high", locale);
		final String undefined = this.messageSource.getMessage("tender.interest.undefined", null, "tender.interest.undefined", locale);

		final Map<String, String> result = new HashMap<>();
		result.put(Constant.TENDER_INTEREST_LOW, low);
		result.put(Constant.TENDER_INTEREST_MEDIUM, medium);
		result.put(Constant.TENDER_INTEREST_HIGH, high);
		result.put(Constant.TENDER_INTEREST_UNDEFINED, undefined);

		return result;
	}
}
