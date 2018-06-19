
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import forms.RegisterForm;
import security.Authority;
import services.ActorService;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Supporting services -----------------------------------------------------

	@Autowired
	private ActorService actorService;


	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	// List ------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Integer pageSize) {
		ModelAndView result;

		final Collection<Actor> actors = this.actorService.findAllActivated();

		result = new ModelAndView("actor/list");
		result.addObject("actors", actors);
		result.addObject("requestUri", "actor/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// Display user -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int actorId) {
		final ModelAndView result;

		final Actor actor;

		actor = this.actorService.findOne(actorId);

		result = new ModelAndView("actor/display");
		result.addObject("registerForm", actor);

		return result;
	}

	// Create ---------------------------------------------------------------

	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result;
		final RegisterForm registerForm = new RegisterForm();
		result = this.createEditModelAndView(registerForm, null);
		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping("/edit")
	public ModelAndView edit() {
		ModelAndView result;
		RegisterForm model;

		final Actor actor = this.actorService.findByPrincipal();
		model = new RegisterForm(actor);
		result = this.createEditModelAndView(model, null);
		return result;
	}

	// Save mediante Post ---------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterForm registerForm, final BindingResult binding) {
		ModelAndView result;
		Actor actor;

		try {
			actor = this.actorService.reconstruct(registerForm, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(registerForm);
			else
				try {
					this.actorService.save(actor);
					if (actor.getId() == 0)
						result = this.createMessageModelAndView("user.created.but.not.activated", "security/login.do");
					else
						result = new ModelAndView("redirect:/");
				} catch (final Throwable oops) {
					if (oops.getCause().getCause() != null && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
						result = this.createEditModelAndView(registerForm, "profile.duplicate.username");
					else
						result = this.createEditModelAndView(registerForm, "actor.commit.error");
				}

		} catch (final Throwable oops) {
			if (oops.getLocalizedMessage().contains("profile"))
				result = this.createEditModelAndView(registerForm, oops.getLocalizedMessage());
			else if (oops.getCause().getCause() != null && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
				result = this.createEditModelAndView(registerForm, "profile.duplicate.username");
			else
				result = this.createEditModelAndView(registerForm, "actor.reconstruct.error");
		}

		return result;
	}

	// Auxiliary methods -----------------------------------------------------
	protected ModelAndView createEditModelAndView(final RegisterForm model) {
		final ModelAndView result;
		result = this.createEditModelAndView(model, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final RegisterForm model, final String message) {
		final ModelAndView result;
		final Collection<Authority> permisos = Authority.listAuthorities();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		permisos.remove(authority);

		result = new ModelAndView("actor/create");
		result.addObject("registerForm", model);
		result.addObject("requestUri", "actor/create.do");
		result.addObject("permisos", permisos);
		result.addObject("edition", true);
		result.addObject("creation", model.getId() == 0);
		result.addObject("message", message);

		return result;

	}

}
