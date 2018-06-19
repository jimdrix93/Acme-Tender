
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CompanyResultService;
import services.FileService;
import services.TenderResultService;
import domain.Actor;
import domain.CompanyResult;
import domain.File;
import domain.TenderResult;

@Controller
@RequestMapping("/tenderResult")
public class TenderResultController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private TenderResultService		tenderResultService;
	@Autowired
	private CompanyResultService	companyResultService;
	@Autowired
	private FileService fileService;	
	@Autowired
	private ActorService			actorService;	


	// Constructor -----------------------------------------------------------
	public TenderResultController() {
		super();
	}

	// Display ---------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tenderResultId) {
		ModelAndView result;
		
		Actor actor = this.actorService.findByPrincipal();
		TenderResult tenderResult = this.tenderResultService.findOne(tenderResultId);

		Collection<CompanyResult> companyResults = this.companyResultService.findAllByTenderResultAnonymous(tenderResult.getId());
		
		Collection<File> files = this.fileService.findAllByTenderResult(tenderResultId);
		
		result = new ModelAndView("tenderResult/display");
		result.addObject("tenderResult", tenderResult);
		result.addObject("files", files);
		result.addObject("companyResults", companyResults);
		result.addObject("actor", actor);

		return result;
	}

}
