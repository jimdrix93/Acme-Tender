
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.MyMessage;
import services.ActorService;
import services.MyMessageService;

@Controller
@RequestMapping("/myMessage/administrator")
public class MyMessageAdminController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ActorService		actorService;
	@Autowired
	private MyMessageService	myMessageService;


	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MyMessage m;
		m = this.myMessageService.create();
		result = this.createEditModelAndView(m);
		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("modelMessage") MyMessage m, final BindingResult binding) {

		ModelAndView result;

		m = this.myMessageService.reconstruct(m, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(m);
		else
			try {
				this.myMessageService.broadcastMessage(m);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(m, "ms.commit.error");
			}
		return result;
	}

	//Ancillary Methods

	protected ModelAndView createEditModelAndView(final MyMessage m) {
		ModelAndView result;

		result = this.createEditModelAndView(m, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MyMessage m, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("broadcast/create");
		result.addObject("modelMessage", m);
		result.addObject("message", messageCode);
		result.addObject("requestUri", "myMessage/administrator/edit.do");

		return result;
	}

}
