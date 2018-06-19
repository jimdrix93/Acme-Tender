
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.CollaborationRequest;
import domain.Commercial;
import domain.Offer;
import repositories.CollaborationRequestRepository;

@Service
@Transactional
public class CollaborationRequestService {

	// Managed repository ------------------------------------------------
	@Autowired
	private CollaborationRequestRepository	collaborationRequestRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private OfferService					offerService;
	@Autowired
	private ActorService					actorService;
	//Validator
	@Autowired
	private Validator						validator;


	//CRUD methods

	public CollaborationRequest create(final int offerId) {
		CollaborationRequest result;
		result = new CollaborationRequest();

		Offer offer;
		offer = this.offerService.findOne(offerId);
		//Reutilizo este método para comprobar que la oferta para la que se quiere crear una solicitud de colaboración pertenece al principal
		Assert.isTrue(this.offerService.canEditOffer(offerId));
		Assert.isTrue(offer.isInDevelopment());
		result.setOffer(offer);

		return result;
	}

	public CollaborationRequest save(final CollaborationRequest collaborationRequest) {

		CollaborationRequest result;
		Assert.notNull(collaborationRequest);

		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Commercial);

		if (collaborationRequest.getId() == 0)
			Assert.isTrue(this.offerService.canEditOffer(collaborationRequest.getOffer().getId()));
		else
			Assert.isTrue(principal.equals(collaborationRequest.getCommercial()));

		Assert.isTrue(collaborationRequest.getMaxAcceptanceDate().before(collaborationRequest.getMaxDeliveryDate()), "collaborationRequest.error.maxAcceptanceDate.not.before.maxDeliveryDate");
		Assert.isTrue(collaborationRequest.getMaxDeliveryDate().before(collaborationRequest.getOffer().getTender().getMaxPresentationDate()), "collaborationRequest.error.maxDeliveryDate.not.before.tender.maxPresentationDate");

		result = this.collaborationRequestRepository.save(collaborationRequest);

		return result;

	}

	public CollaborationRequest findOne(final int collaborationRequestId) {
		CollaborationRequest result;
		result = this.collaborationRequestRepository.findOne(collaborationRequestId);
		Assert.notNull(result);
		this.checkPrincipal(result);
		return result;
	}

	public CollaborationRequest findOneToEdit(final int collaborationRequestId) {
		CollaborationRequest result;
		result = this.collaborationRequestRepository.findOne(collaborationRequestId);
		Assert.notNull(result);
		this.checkReceiverAndState(result);
		return result;
	}

	//Other methods

	public Collection<CollaborationRequest> getSentCollaborationRequestsFromCommercialId(final int commercialId) {
		return this.collaborationRequestRepository.getSentCollaborationRequestsFromCommercialId(commercialId);
	}

	public Collection<CollaborationRequest> getReceivedCollaborationRequestsFromCommercialId(final int commercialId) {
		return this.collaborationRequestRepository.getReceivedCollaborationRequestsFromCommercialId(commercialId);
	}

	private void checkPrincipal(final CollaborationRequest collaborationRequest) {

		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Commercial);
		final Collection<CollaborationRequest> sent = this.collaborationRequestRepository.getSentCollaborationRequestsFromCommercialId(principal.getId());
		final Collection<CollaborationRequest> received = this.collaborationRequestRepository.getReceivedCollaborationRequestsFromCommercialId(principal.getId());
		Assert.isTrue(sent.contains(collaborationRequest) || received.contains(collaborationRequest));

	}

	private void checkReceiverAndState(final CollaborationRequest collaborationRequest) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.equals(collaborationRequest.getCommercial()) && collaborationRequest.getAccepted() == null);

	}

	public void deleteByAdmin(final Collection<CollaborationRequest> collaborationRequest) {
		this.collaborationRequestRepository.deleteInBatch(collaborationRequest);
	}

	//Other -------------------------------------------------------------------
	public Collection<CollaborationRequest> findAllByOffer(final int offerId) {

		return this.collaborationRequestRepository.findAllByOffer(offerId);
	}

	public void flush() {
		this.collaborationRequestRepository.flush();

	}

	public CollaborationRequest reconstruct(CollaborationRequest collaborationRequest, BindingResult binding) {
		CollaborationRequest result;

		if (collaborationRequest.getId() == 0) {
			result = collaborationRequest;
		} else {
			result = this.collaborationRequestRepository.findOne(collaborationRequest.getId());
			result.setRejectedReason(collaborationRequest.getRejectedReason());
		}

		validator.validate(result, binding);

		return result;

	}

}
