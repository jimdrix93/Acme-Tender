
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
import services.TenderService;
import controllers.AbstractController;
import domain.Actor;
import domain.Tender;

@Controller
@RequestMapping("/tender/administrator")
public class TenderAdminController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private TenderService			tenderService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructor -----------------------------------------------------------
	public TenderAdminController() {
		super();
	}

	// List Tenders with taboo words ---------------------------------------------------------------
	@RequestMapping(value = "/listWithTabooWord", method = RequestMethod.GET)
	public ModelAndView listTender(final Integer pageSize) {

		final ModelAndView result;
		final Double benefitsPercentaje = this.configurationService.findBenefitsPercentage();
		final Actor actor = this.actorService.findByPrincipal();

		final Collection<Tender> tenders = this.tenderService.findAllTenderWithTabooWords();

		result = new ModelAndView("tender/administrator/list");
		result.addObject("tenders", tenders);
		result.addObject("requestUri", "tender/administrator/listWithTabooWord.do");
		result.addObject("actor", actor);
		result.addObject("benefitsPercentaje", benefitsPercentaje);
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);

		return result;
	}

	// Delete ---------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = false) final Integer tenderId) {
		ModelAndView result;

		Assert.notNull(tenderId);
		final Tender tender = this.tenderService.findOne(tenderId);

		try {
			this.tenderService.deleteByAdmin(tender);
			result = new ModelAndView("redirect: listWithTabooWord.do");

		} catch (final Throwable ooops) {
			result = new ModelAndView("redirect: listWithTabooWord.do");
			result.addObject("message", "tabooWord.commit.error");
		}
		return result;
	}

}
