
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import services.ActorService;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdminController extends AbstractController {

	// Supporting services -----------------------------------------------------

	@Autowired
	ActorService actorService;


	// Constructors -----------------------------------------------------------

	public ActorAdminController() {
		super();
	}
	
	// List ------------------------------------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list(final Integer pageSize) {
		ModelAndView result;

		final Collection<Actor> actors = this.actorService.findAll();

		result = new ModelAndView("actor/list");
		result.addObject("actors", actors);
		result.addObject("requestUri", "actor/administrator/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}	

	// ActivateOrDeactivate ------------------------------------------------------------------
	@RequestMapping(value = "/activeOrDeactivate", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int actorId, final Integer pageSize) {
		ModelAndView result;

		this.actorService.ActivateOrDeactivate(actorId);
		int pSize = (pageSize != null) ? pageSize : 5;
		result = new ModelAndView("redirect:/actor/administrator/list.do?pageSize="+pSize);
		result.addObject("pageSize", pSize);

		return result;
	}

}
