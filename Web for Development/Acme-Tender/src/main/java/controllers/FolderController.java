
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

import services.ActorService;
import services.FolderService;
import domain.Actor;
import domain.Folder;
import domain.MyMessage;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {

	// Supporting services -----------------------------------------------------
	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int folderId) {

		ModelAndView result;
		Folder folder;
		Collection<MyMessage> myMessages;
		Collection<Folder> folders;
		folder = this.folderService.findOne(folderId);
		myMessages = folder.getMymessages();
		folders = this.folderService.getChildFolders(folderId);
		result = new ModelAndView("folder/display");
		result.addObject("folders", folders);
		result.addObject("messages", myMessages);
		result.addObject("folder", folder);

		return result;

	}

	//List first level folders
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Folder> folders;

		final Actor actor = this.actorService.findByPrincipal();
		folders = this.folderService.getFirstLevelFoldersFromActorId(actor.getId());

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);

		return result;

	}

	//Create folder on the first level
	@RequestMapping(value = "/createFirst", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Folder folder;
		folder = this.folderService.create();
		result = this.createEditModelAndView(folder, true, false);
		return result;
	}

	//Create folder within any other level

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int folderId) {
		ModelAndView result;
		final Folder parentFolder = this.folderService.findOne(folderId);
		Folder folder;
		folder = this.folderService.create();
		folder.setParentFolder(parentFolder);
		result = this.createEditModelAndView(folder, false, false);
		return result;
	}

	//Create to move

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView createMove(@RequestParam final int folderId) {
		ModelAndView result;
		Folder folder;

		folder = this.folderService.findOne(folderId);
		Assert.isTrue(!folder.getSystemFolder());
		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Folder> folders = principal.getFolders();
		result = new ModelAndView("folder/move");
		result.addObject("folder", folder);
		result.addObject("message", null);
		result.addObject("folders", folders);

		return result;

	}

	//Save folder on the first level
	@RequestMapping(value = "/editFirst", method = RequestMethod.POST, params = "saveFirst")
	public ModelAndView saveFirst(@Valid final Folder folder, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(folder, true, false);
		else
			try {

				this.folderService.save(folder);

				if (folder.getId() == 0)
					result = new ModelAndView("redirect:list.do");
				else
					result = new ModelAndView("redirect:display.do?folderId=" + folder.getId());

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(folder, "ms.commit.error", true, false);

			}
		return result;

	}

	//Save folder within any other level
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(folder, false, false);
		else
			try {

				this.folderService.save(folder);
				if (folder.getId() == 0)
					result = new ModelAndView("redirect:display.do?folderId=" + folder.getParentFolder().getId());
				else
					result = new ModelAndView("redirect:display.do?folderId=" + folder.getId());

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(folder, "ms.commit.error", false, false);

			}
		return result;

	}
	//Save to move

	@RequestMapping(value = "/saveMove", method = RequestMethod.GET)
	public ModelAndView saveMove(@RequestParam(required = true) final int targetfolderId, @RequestParam(required = true) final int folderId) {
		ModelAndView result;
		final Folder folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
		Assert.isTrue(!folder.getSystemFolder());
		final Folder targetFolder = this.folderService.findOne(targetfolderId);
		Assert.notNull(targetFolder);
		Assert.isTrue(!targetFolder.equals(folder));
		if (folder.getParentFolder() != null)
			Assert.isTrue(!folder.getParentFolder().equals(targetFolder));

		try {
			this.folderService.saveToMove(folder, targetFolder);
			result = new ModelAndView("redirect:/folder/display.do?folderId=" + targetFolder.getId());

		} catch (final Throwable oops) {
			final Actor principal = this.actorService.findByPrincipal();
			final Collection<Folder> folders = principal.getFolders();
			result = new ModelAndView("folder/move");
			result.addObject("folder", folder);
			result.addObject("message", "ms.commit.error");
			result.addObject("folders", folders);

		}

		return result;
	}

	//Edit first level folder name

	@RequestMapping(value = "/editFirst", method = RequestMethod.GET)
	public ModelAndView editFirst(@RequestParam final int folderId) {
		ModelAndView result;
		final Folder folder = this.folderService.findOneToEdit(folderId);

		result = this.createEditModelAndView(folder, true, true);

		return result;
	}

	//Edit other folder name

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		ModelAndView result;
		final Folder folder = this.folderService.findOneToEdit(folderId);

		result = this.createEditModelAndView(folder, false, true);

		return result;
	}

	//Delete folder 
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int folderId) {
		ModelAndView result;
		Folder folder;
		folder = this.folderService.findOne(folderId);
		final Folder parentFolder = folder.getParentFolder();

		if (folder.getParentFolder() == null)
			try {
				this.folderService.delete(folder);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/folder/display.do?folderId=" + folder.getId());

			}
		else
			try {
				this.folderService.delete(folder);

				result = new ModelAndView("redirect:/folder/display.do?folderId=" + parentFolder.getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/folder/display.do?folderId=" + folder.getId());

			}

		return result;

	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Folder folder, final boolean isFirst, final boolean editing) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null, isFirst, editing);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String messageCode, final boolean isFirst, final boolean editing) {

		ModelAndView result;
		if (isFirst) {
			result = new ModelAndView("folder/createFirst");
			result.addObject("folder", folder);
			result.addObject("message", messageCode);
			result.addObject("editing", editing);
		} else {
			result = new ModelAndView("folder/create");
			final Folder parentFolder = folder.getParentFolder();
			result.addObject("folder", folder);
			result.addObject("message", messageCode);
			result.addObject("parentFolder", parentFolder);
			result.addObject("editing", editing);
		}

		return result;
	}

}
