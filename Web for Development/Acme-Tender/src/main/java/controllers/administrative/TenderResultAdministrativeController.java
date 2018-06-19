
package controllers.administrative;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.TenderResultService;
import services.TenderService;
import controllers.AbstractController;
import domain.Actor;
import domain.Tender;
import domain.TenderResult;

@Controller
@RequestMapping("/tenderResult/administrative")
public class TenderResultAdministrativeController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private TenderResultService		tenderResultService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private TenderService tenderService;


	// Constructor -----------------------------------------------------------
	public TenderResultAdministrativeController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tenderId) {
		ModelAndView result;
		final TenderResult tenderResult;

		tenderResult = this.tenderResultService.create(tenderId);
		Tender tender = this.tenderService.findOne(tenderId);
		Assert.isTrue(tender.getTenderResult() ==  null);
		
		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(tenderResult.getTender().getAdministrative().getId() == actor.getId());
		
		result = this.createEditModelAndView(tenderResult);

		return result;
	}
	
	// Edit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tenderResultId) {
		ModelAndView result;
		
		final TenderResult tenderResult = this.tenderResultService.findOne(tenderResultId);
		Assert.notNull(tenderResult);

		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(tenderResult.getTender().getAdministrative().getId() == actor.getId());
		
		result = this.createEditModelAndView(tenderResult);

		return result;
	}	

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TenderResult tenderResult, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tenderResult);
		else
			try {
				TenderResult saved = this.tenderResultService.save(tenderResult);
				result = new ModelAndView("redirect:/tenderResult/display.do?tenderResultId=" + saved.getId());

			} catch (final Throwable oops) {
				
				if (oops.getMessage() == "tenderResult.error.tenderDate.must.be.after.tender.maxPresentationDate")
					result = this.createEditModelAndView(tenderResult, oops.getMessage());
				else
					result = this.createEditModelAndView(tenderResult, "tenderResult.commit.error");
			}
		return result;
	}

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final TenderResult tenderResult) {
		final ModelAndView result;
		result = this.createEditModelAndView(tenderResult, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final TenderResult tenderResult, final String message) {

		ModelAndView result = null;
		
		if (tenderResult.getId() != 0)
			result = new ModelAndView("tenderResult/administrative/edit");
		else
			result = new ModelAndView("tenderResult/administrative/create");
		
		result.addObject("tenderResult", tenderResult);
		result.addObject("message", message);
		return result;
	}


}
