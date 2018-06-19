
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrative;
import domain.AdministrativeRequest;
import domain.Commercial;
import domain.SubSection;
import services.ActorService;
import services.AdministrativeRequestService;
import services.AdministrativeService;
import services.MyMessageService;
import services.SubSectionService;

@Controller
@RequestMapping("/administrativeRequest")
public class AdministrativeRequestController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private AdministrativeRequestService	administrativeRequestService;
	@Autowired
	private AdministrativeService			administrativeService;
	@Autowired
	private MyMessageService				myMessageService;
	@Autowired
	private ActorService					actorService;
	@Autowired
	private SubSectionService				subSectionService;


	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int offerId) {
		ModelAndView result;
		AdministrativeRequest administrativeRequest;

		administrativeRequest = this.administrativeRequestService.create(offerId);

		result = this.createEditModelAndView(administrativeRequest);
		result.addObject("reject", false);

		return result;
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int administrativeRequestId) {

		ModelAndView result;

		final AdministrativeRequest administrativeRequest = this.administrativeRequestService.findOne(administrativeRequestId);
		final Actor principal = this.actorService.findByPrincipal();

		result = new ModelAndView("administrativeRequest/display");
		result.addObject("administrativeRequest", administrativeRequest);
		result.addObject("principal", principal);
		return result;

	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(AdministrativeRequest administrativeRequest, final BindingResult binding, @ModelAttribute("reject") final Boolean reject) {

		ModelAndView result;

		administrativeRequest = this.administrativeRequestService.reconstruct(administrativeRequest, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(administrativeRequest);
		else

		if (!administrativeRequest.getMaxDeliveryDate().after(administrativeRequest.getMaxAcceptanceDate()))
			result = this.createEditModelAndView(administrativeRequest, "administrativeRequest.date.error");
		else if (administrativeRequest.getId() != 0 && administrativeRequest.getRejectedReason().isEmpty())
			result = this.createEditModelAndView(administrativeRequest, "administrativeRequest.rejection.error");
		else
			try {
				boolean sendNotification = false;
				if (reject == true) {
					sendNotification = true;
					administrativeRequest.setAccepted(false);
				}
				final AdministrativeRequest saved = this.administrativeRequestService.save(administrativeRequest);
				if (sendNotification == true)
					this.myMessageService.administrativeRequestNotification(saved, false);
				if (administrativeRequest.getId() == 0)
					result = new ModelAndView("redirect:/offer/display.do?offerId=" + administrativeRequest.getOffer().getId());
				else
					result = new ModelAndView("redirect:display.do?administrativeRequestId=" + administrativeRequest.getId());
			} catch (final Throwable oops) {
				if (oops.getMessage() == "administrativeRequest.error.maxAcceptanceDate.not.before.maxDeliveryDate")
					result = this.createEditModelAndView(administrativeRequest, oops.getMessage());
				else if (oops.getMessage() == "administrativeRequest.error.maxDeliveryDate.not.before.tender.maxPresentationDate")
					result = this.createEditModelAndView(administrativeRequest, oops.getMessage());
				else
					result = this.createEditModelAndView(administrativeRequest, "administrativeRequest.commit.error");

			}
		return result;
	}

	//List sent 

	@RequestMapping(value = "/listSent", method = RequestMethod.GET)
	public ModelAndView listSent() {

		ModelAndView result;
		Collection<AdministrativeRequest> administrativeRequests;

		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Commercial);

		administrativeRequests = this.administrativeRequestService.getSentAdministrativeRequestsFromCommercialId(principal.getId());

		result = new ModelAndView("administrativeRequest/listSent");
		result.addObject("administrativeRequests", administrativeRequests);
		result.addObject("requestURI", "administrativeRequest/listSent.do");

		return result;

	}

	//List received 

	@RequestMapping(value = "/listReceived", method = RequestMethod.GET)
	public ModelAndView listReceived() {

		ModelAndView result;
		Collection<AdministrativeRequest> administrativeRequests;

		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Administrative);

		administrativeRequests = this.administrativeRequestService.getReceivedAdministrativeRequestsFromAdministrativeId(principal.getId());

		result = new ModelAndView("administrativeRequest/listReceived");
		result.addObject("administrativeRequests", administrativeRequests);
		result.addObject("requestURI", "administrativeRequest/listReceived.do");

		return result;

	}

	//Reject
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int requestId) {

		ModelAndView result;

		AdministrativeRequest administrativeRequest;
		administrativeRequest = this.administrativeRequestService.findOneToEdit(requestId);

		result = this.createEditModelAndView(administrativeRequest);
		result.addObject("reject", true);

		return result;

	}

	//Accept
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int requestId) {

		final AdministrativeRequest administrativeRequest = this.administrativeRequestService.findOneToEdit(requestId);
		administrativeRequest.setAccepted(true);
		final AdministrativeRequest savedAR = this.administrativeRequestService.save(administrativeRequest);
		this.myMessageService.administrativeRequestNotification(savedAR, true);

		final SubSection subSection = this.subSectionService.createByAdministrativeCollaborationAcceptation(administrativeRequest);
		final SubSection savedSS = this.subSectionService.save(subSection);

		return this.createMessageModelAndView("administrativeRequest.message.accepted", "offer/display.do?offerId=" + savedSS.getOffer().getId());

	}

	// Ancillary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(final AdministrativeRequest administrativeRequest) {
		final ModelAndView result;
		result = this.createEditModelAndView(administrativeRequest, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministrativeRequest administrativeRequest, final String messageCode) {

		final ModelAndView result = new ModelAndView("administrativeRequest/edit");

		final Collection<Administrative> administratives = this.administrativeService.simpleFindAll();

		result.addObject("administrativeRequest", administrativeRequest);
		result.addObject("message", messageCode);
		result.addObject("administratives", administratives);

		return result;
	}

}
