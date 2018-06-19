
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Configuration;
import services.AdministratorService;
import services.ConfigurationService;

@Controller
@RequestMapping("/configuration/administrator")
public class ConfigurationAdminController extends AbstractController {

	@Autowired
	ConfigurationService	configurationService;

	@Autowired
	AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public ConfigurationAdminController() {
		super();
	}

	// ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;

		final Collection<Configuration> configurations = this.configurationService.findAll();

		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configurations.toArray()[0]);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {

		ModelAndView result = null;
		Configuration newConfiguration;

		if (binding.hasErrors()) {
			result = new ModelAndView("configuration/edit");
			result.addObject("configuration", configuration);
		} else
			try {
				newConfiguration = this.configurationService.save(configuration);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(configuration, "configuration.commit.ko");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration c) {
		final ModelAndView result = this.createEditModelAndView(c, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration c, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", c);
		result.addObject("message", messageCode);

		return result;
	}
}
