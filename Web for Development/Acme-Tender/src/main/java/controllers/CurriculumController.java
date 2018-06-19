
package controllers;

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

import domain.Actor;
import domain.Constant;
import domain.Curriculum;
import domain.File;
import domain.SubSection;
import services.ActorService;
import services.CurriculumService;
import services.FileService;
import services.SubSectionService;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CurriculumService	curriculumService;
	@Autowired
	private SubSectionService	subSectionService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private FileService			fileService;


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int curriculumId) {

		ModelAndView result;

		Actor actor = this.actorService.findByPrincipal();

		Curriculum curriculum = curriculumService.findOne(curriculumId);
		Assert.isTrue(this.subSectionService.canViewSubSection(curriculum.getSubSection().getId()));
		curriculumService.checkListAndDisplay(curriculum.getSubSection().getId());

		Collection<File> files = this.fileService.findAllByCurriculum(curriculumId);

		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);
		result.addObject("actor", actor);
		result.addObject("files", files);
		return result;

	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int subSectionId) {

		ModelAndView result;

		Actor actor = actorService.findByPrincipal();

		SubSection subSection = subSectionService.findOne(subSectionId);
		Assert.isTrue(subSection.getCommercial().getId() == actor.getId());
		Assert.isTrue(!subSection.getSection().equals(Constant.SECTION_ADMINISTRATIVE_ACREDITATION));

		Curriculum curriculum;
		curriculum = curriculumService.create();
		curriculum.setSubSection(subSection);

		result = this.createEditModelAndView(curriculum);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int curriculumId) {
		ModelAndView result;

		final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);

		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(curriculum.getSubSection().getCommercial().getId() == actor.getId());

		result = this.createEditModelAndView(curriculum);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Curriculum curriculum, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(curriculum);
		} else if (!this.curriculumService.checkLegalAge(curriculum.getDateOfBirth())) {
			result = this.createEditModelAndView(curriculum, "curriculum.age.error");

		} else {

			try {
				Curriculum saved = curriculumService.save(curriculum);
				result = new ModelAndView("redirect:/subSection/display.do?subSectionId=" + saved.getSubSection().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
			}
		}
		return result;
	}

	//Delete 
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Curriculum curriculum, BindingResult binding) {

		ModelAndView result;
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(curriculum.getSubSection().getCommercial().getId() == actor.getId());

		try {
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:/subSection/display.do?subSectionId=" + curriculum.getSubSection().getId());

		} catch (Throwable oops) {
			result = createEditModelAndView(curriculum, "curriculum.commit.error");

		}

		return result;

	}

	//Ancillary Methods

	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createEditModelAndView(curriculum, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);

		return result;
	}

}
