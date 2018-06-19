
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Answer;
import domain.Comment;
import services.AnswerService;
import services.CommentService;

@Controller
@RequestMapping("/answer/actor")
public class AnswerActorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private AnswerService	answerService;
	@Autowired
	private CommentService	commentService;


	// Constructor -----------------------------------------------------------
	public AnswerActorController() {
		super();
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int commentId) {
		ModelAndView result;
		final Answer answer;

		answer = this.answerService.create(commentId);
		result = this.createEditModelAndView(answer);

		return result;
	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Answer answer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(answer);
		else
			try {
				this.answerService.save(answer);
				result = new ModelAndView("redirect:/answer/actor/list.do?commentId=" + answer.getComment().getId());
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(answer, "answer.commit.error");
			}
		return result;
	}

	//List -------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int commentId) {

		ModelAndView result;

		final Collection<Answer> answers = this.answerService.findAllByComment(commentId);
		
		Comment comment = this.commentService.findOne(commentId);

		result = new ModelAndView("answer/actor/list");
		result.addObject("answers", answers);
		result.addObject("comment", comment);
		result.addObject("tenderId", comment.getTender().getId());

		return result;
	}

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final Answer answer) {
		final ModelAndView result;
		result = this.createEditModelAndView(answer, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Answer answer, final String message) {

		final ModelAndView result = new ModelAndView("answer/actor/create");
		result.addObject("answer", answer);
		result.addObject("message", message);
		return result;
	}
}
