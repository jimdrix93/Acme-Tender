
package controllers.administrative;

import java.util.Collection;
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
import services.ComboService;
import services.CompanyResultService;
import services.TenderResultService;
import controllers.AbstractController;
import domain.Actor;
import domain.CompanyResult;
import domain.TenderResult;

@Controller
@RequestMapping("/companyResult/administrative")
public class CompanyResultAdministrativeController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ComboService			comboService;
	@Autowired
	private CompanyResultService	companyResultService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private TenderResultService tenderResultService;

	// Constructor -----------------------------------------------------------
	public CompanyResultAdministrativeController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tenderResultId) {
		ModelAndView result;
		final CompanyResult companyResult;

		TenderResult tenderResult = this.tenderResultService.findOne(tenderResultId);
		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(tenderResult.getTender().getAdministrative().getId() == actor.getId());
		
		companyResult = this.companyResultService.create(tenderResultId);
		result = this.createEditModelAndView(companyResult);

		return result;
	}
	
	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int companyResultId) {
		ModelAndView result;
		final CompanyResult companyResult;

		companyResult = this.companyResultService.findOne(companyResultId);
		
		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(companyResult.getTenderResult().getTender().getAdministrative().getId() == actor.getId());
		
		result = this.createEditModelAndView(companyResult);

		return result;
	}	

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CompanyResult companyResult, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(companyResult);
		else
			try {
				this.companyResultService.save(companyResult);
				result = new ModelAndView("redirect:/tenderResult/display.do?tenderResultId=" + companyResult.getTenderResult().getId());

			} catch (final Throwable oops) {
				if (oops.getMessage() == "companyResult.only.winner")
					result = this.createEditModelAndView(companyResult, "companyResult.only.winner");
				else if (oops.getMessage() == "companyResult.not.repeat.position")
					result = this.createEditModelAndView(companyResult, "companyResult.not.repeat.position");
				else
					result = this.createEditModelAndView(companyResult, "companyResult.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(CompanyResult companyResult, final BindingResult binding) {
		ModelAndView result;
		Actor actor = this.actorService.findByPrincipal();

		Assert.isTrue(companyResult.getTenderResult().getTender().getAdministrative().getId() == actor.getId());

		try {
			this.companyResultService.delete(companyResult);
			result = new ModelAndView("redirect:/tenderResult/display.do?tenderResultId=" + companyResult.getTenderResult().getId());

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(companyResult, "companyResult.commit.error");
		}
		return result;
	}	
	

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final CompanyResult companyResult) {
		final ModelAndView result;
		result = this.createEditModelAndView(companyResult, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CompanyResult companyResult, final String message) {

		ModelAndView result = null;
		
		if (companyResult.getId() == 0) 
			result = new ModelAndView("companyResult/administrative/create");
		else
			result = new ModelAndView("companyResult/administrative/edit");
		
		Collection<String> companyResultStatesCombo = this.comboService.companyResultStates(companyResult);
		result.addObject("companyResultStatesCombo", companyResultStatesCombo);
		
		result.addObject("companyResult", companyResult);
		result.addObject("message", message);
		return result;
	}

}
