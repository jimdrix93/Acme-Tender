
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Administrative;
import domain.AdministrativeRequest;
import domain.Commercial;
import domain.Offer;
import repositories.AdministrativeRequestRepository;

@Service
@Transactional
public class AdministrativeRequestService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdministrativeRequestRepository	administrativeRequestRepository;
	@Autowired
	private OfferService					offerService;
	@Autowired
	private ActorService					actorService;
	//Validator
	@Autowired
	private Validator						validator;

	// Supporting services ----------------------------------------------------


	// Constructors -----------------------------------------------------------
	public AdministrativeRequestService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public AdministrativeRequest create(final int offerId) {
		AdministrativeRequest result;
		result = new AdministrativeRequest();

		Offer offer;
		offer = this.offerService.findOne(offerId);
		//Reutilizo este método para comprobar que la oferta para la que se quiere crear una solicitud de colaboración pertenece al principal
		Assert.isTrue(this.offerService.canEditOffer(offerId));
		Assert.isTrue(offer.isInDevelopment());
		result.setOffer(offer);

		return result;
	}

	public AdministrativeRequest save(final AdministrativeRequest administrativeRequest) {

		AdministrativeRequest result;
		Assert.notNull(administrativeRequest);

		checkPrincipal(administrativeRequest);

		Assert.isTrue(administrativeRequest.getMaxAcceptanceDate().before(administrativeRequest.getMaxDeliveryDate()), "administrativeRequest.error.maxAcceptanceDate.not.before.maxDeliveryDate");
		Assert.isTrue(administrativeRequest.getMaxDeliveryDate().before(administrativeRequest.getOffer().getTender().getMaxPresentationDate()), "administrativeRequest.error.maxDeliveryDate.not.before.tender.maxPresentationDate");

		result = this.administrativeRequestRepository.save(administrativeRequest);

		return result;

	}

	public AdministrativeRequest findOne(final int administrativeRequestId) {
		AdministrativeRequest result;
		result = this.administrativeRequestRepository.findOne(administrativeRequestId);
		Assert.notNull(result);
		checkDisplay(result);
		return result;
	}

	public AdministrativeRequest findOneToEdit(final int administrativeRequestId) {
		AdministrativeRequest result;
		result = this.administrativeRequestRepository.findOne(administrativeRequestId);
		Assert.notNull(result);
		this.checkReceiverAndState(result);
		return result;
	}

	public void deleteByAdmin(final Collection<AdministrativeRequest> administrativeRequests) {
		this.administrativeRequestRepository.deleteInBatch(administrativeRequests);
	}

	//Other -------------------------------------------------------------------
	public Collection<AdministrativeRequest> findAllByOffer(final int offerId) {

		return this.administrativeRequestRepository.findAllByOffer(offerId);
	}

	public void checkPrincipal(AdministrativeRequest administrativeRequest) {

		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		if (administrativeRequest.getId() == 0) {

			Assert.isTrue(principal instanceof Commercial);
			Assert.isTrue(principal.equals(administrativeRequest.getOffer().getCommercial()));

		} else {

			Assert.isTrue(principal instanceof Administrative);
			Assert.isTrue(principal.equals(administrativeRequest.getAdministrative()));

		}

	}

	private void checkDisplay(AdministrativeRequest administrativeRequest) {

		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.equals(administrativeRequest.getOffer().getCommercial()) || principal.equals(administrativeRequest.getAdministrative()));

	}

	private void checkReceiverAndState(final AdministrativeRequest administrativeRequest) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.equals(administrativeRequest.getAdministrative()) && administrativeRequest.getAccepted() == null);

	}

	public Collection<AdministrativeRequest> getSentAdministrativeRequestsFromCommercialId(int commercialId) {
		return this.administrativeRequestRepository.getSentAdministrativeRequestsFromCommercialId(commercialId);
	}

	public Collection<AdministrativeRequest> getReceivedAdministrativeRequestsFromAdministrativeId(int administrativeId) {
		return this.administrativeRequestRepository.getReceivedAdministrativeRequestsFromAdministrativeId(administrativeId);
	}

	public void flush() {
		this.administrativeRequestRepository.flush();

	}

	public AdministrativeRequest reconstruct(AdministrativeRequest administrativeRequest, BindingResult binding) {
		AdministrativeRequest result;

		if (administrativeRequest.getId() == 0) {
			result = administrativeRequest;
		} else {
			result = this.administrativeRequestRepository.findOne(administrativeRequest.getId());
			result.setRejectedReason(administrativeRequest.getRejectedReason());
		}

		validator.validate(result, binding);

		return result;

	}

}
