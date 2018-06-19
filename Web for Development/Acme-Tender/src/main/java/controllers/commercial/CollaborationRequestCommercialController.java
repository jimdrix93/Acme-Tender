
package controllers.commercial;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.CollaborationRequest;
import domain.Commercial;
import domain.SubSection;
import services.ActorService;
import services.CollaborationRequestService;
import services.CommercialService;
import services.ConfigurationService;
import services.MyMessageService;
import services.SubSectionService;

@Controller
@RequestMapping("/collaborationRequest/commercial")
public class CollaborationRequestCommercialController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CollaborationRequestService	collaborationRequestService;
	@Autowired
	private CommercialService			commercialService;
	@Autowired
	private ActorService				actorService;
	@Autowired
	private MyMessageService			myMessageService;
	@Autowired
	private SubSectionService			subSectionService;
	@Autowired
	private ConfigurationService		configurationService;


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int collaborationRequestId) {

		ModelAndView result;

		final Double benefitsPercentage = this.configurationService.findBenefitsPercentage();

		CollaborationRequest collaborationRequest = collaborationRequestService.findOne(collaborationRequestId);
		Actor actor = this.actorService.findByPrincipal();

		//Puede verla si es el remitente o el destinatario
		Assert.isTrue(collaborationRequest.getCommercial().getId() == actor.getId() || collaborationRequest.getOffer().getCommercial().getId() == actor.getId());

		result = new ModelAndView("collaborationRequest/display");
		result.addObject("benefitsPercentage", benefitsPercentage);
		result.addObject("collaborationRequest", collaborationRequest);
		result.addObject("principal", actor);
		return result;

	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int offerId) {
		ModelAndView result;
		CollaborationRequest collaborationRequest;

		collaborationRequest = this.collaborationRequestService.create(offerId);

		result = this.createEditModelAndView(collaborationRequest);
		result.addObject("reject", false);

		return result;
	}

	//List sent 

	@RequestMapping(value = "/listSent", method = RequestMethod.GET)
	public ModelAndView listSent() {

		ModelAndView result;
		Collection<CollaborationRequest> collaborationRequests;

		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Commercial);

		collaborationRequests = collaborationRequestService.getSentCollaborationRequestsFromCommercialId(principal.getId());

		result = new ModelAndView("collaborationRequest/listSent");
		result.addObject("collaborationRequests", collaborationRequests);
		result.addObject("requestURI", "collaborationRequest/commercial/listSent.do");

		return result;

	}

	//List received 

	@RequestMapping(value = "/listReceived", method = RequestMethod.GET)
	public ModelAndView listReceived() {

		ModelAndView result;
		Collection<CollaborationRequest> collaborationRequests;

		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Commercial);

		collaborationRequests = collaborationRequestService.getReceivedCollaborationRequestsFromCommercialId(principal.getId());

		result = new ModelAndView("collaborationRequest/listReceived");
		result.addObject("collaborationRequests", collaborationRequests);
		result.addObject("requestURI", "collaborationRequest/commercial/listReceived.do");

		return result;

	}

	//Reject
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam int requestId) {

		ModelAndView result;

		CollaborationRequest collaborationRequest;
		collaborationRequest = this.collaborationRequestService.findOneToEdit(requestId);

		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(collaborationRequest.getCommercial().getId() == actor.getId());

		result = this.createEditModelAndView(collaborationRequest);
		result.addObject("reject", true);

		return result;

	}

	//Accept
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam int requestId) {

		CollaborationRequest collaborationRequest = this.collaborationRequestService.findOneToEdit(requestId);

		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(collaborationRequest.getCommercial().getId() == actor.getId());
		Assert.isTrue(collaborationRequest.getMaxAcceptanceDate().after(new Date()));

		collaborationRequest.setAccepted(true);
		CollaborationRequest savedCR = this.collaborationRequestService.save(collaborationRequest);
		this.myMessageService.collaborationRequestNotification(savedCR, true);

		SubSection subSection = this.subSectionService.createByCommercialCollaborationAcceptation(collaborationRequest);
		SubSection savedSS = this.subSectionService.save(subSection);

		return this.createMessageModelAndView("collaborationRequest.message.accepted", "offer/display.do?offerId=" + savedSS.getOffer().getId());

	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(CollaborationRequest collaborationRequest, final BindingResult binding, @ModelAttribute("reject") Boolean reject) {

		ModelAndView result;

		collaborationRequest = collaborationRequestService.reconstruct(collaborationRequest, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(collaborationRequest);

		} else if (!collaborationRequest.getMaxDeliveryDate().after(collaborationRequest.getMaxAcceptanceDate())) {
			result = this.createEditModelAndView(collaborationRequest, "collaborationRequest.date.error");

		} else {

			if (collaborationRequest.getId() != 0 && collaborationRequest.getRejectedReason().isEmpty()) {
				result = this.createEditModelAndView(collaborationRequest, "collaborationRequest.rejection.error");

			} else {

				try {
					boolean sendNotification = false;
					if (reject == true) {
						sendNotification = true;
						collaborationRequest.setAccepted(false);
					}
					CollaborationRequest saved = collaborationRequestService.save(collaborationRequest);
					if (sendNotification == true) {
						this.myMessageService.collaborationRequestNotification(saved, false);
					}
					if (collaborationRequest.getId() == 0) {
						result = new ModelAndView("redirect:/offer/display.do?offerId=" + collaborationRequest.getOffer().getId());
					} else {
						result = new ModelAndView("redirect:display.do?collaborationRequestId=" + collaborationRequest.getId());
					}
				} catch (final Throwable oops) {

					if (oops.getMessage() == "collaborationRequest.error.maxAcceptanceDate.not.before.maxDeliveryDate")
						result = this.createEditModelAndView(collaborationRequest, oops.getMessage());
					else if (oops.getMessage() == "collaborationRequest.error.maxDeliveryDate.not.before.tender.maxPresentationDate")
						result = this.createEditModelAndView(collaborationRequest, oops.getMessage());
					else
						result = this.createEditModelAndView(collaborationRequest, "collaborationRequest.commit.error");

				}

			}
		}
		return result;
	}

	// Ancillary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(CollaborationRequest collaborationRequest) {
		final ModelAndView result;
		result = this.createEditModelAndView(collaborationRequest, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(CollaborationRequest collaborationRequest, String messageCode) {

		final ModelAndView result = new ModelAndView("collaborationRequest/edit");

		Collection<Commercial> commercials = commercialService.findAll();
		commercials.remove(collaborationRequest.getOffer().getCommercial());

		result.addObject("collaborationRequest", collaborationRequest);
		result.addObject("message", messageCode);
		result.addObject("commercials", commercials);

		return result;
	}

}
