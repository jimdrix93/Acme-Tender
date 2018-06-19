
package controllers.commercial;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Comment;
import services.CommentService;

@Controller
@RequestMapping("/comment/commercial")
public class CommentCommercialController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CommentService commentService;


	// Constructor -----------------------------------------------------------
	public CommentCommercialController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tenderId) {
		ModelAndView result;
		final Comment comment;

		comment = this.commentService.create(tenderId);
		result = this.createEditModelAndView(comment);

		return result;
	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(comment);
		else
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:/tender/display.do?tenderId=" + comment.getTender().getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}
		return result;
	}

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final Comment comment) {
		final ModelAndView result;
		result = this.createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {

		final ModelAndView result = new ModelAndView("comment/commercial/create");
		result.addObject("comment", comment);
		result.addObject("message", message);
		return result;
	}
}
