
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.AdministrativeRequest;
import domain.CollaborationRequest;
import domain.Offer;
import domain.Tender;
import repositories.ExecutiveRepository;

@Service
@Transactional
public class ExecutiveService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private ExecutiveRepository	executiveRepository;


	// Constructor ----------------------------------------------------------
	public ExecutiveService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	// Dashboard -------------------------------------------------------------------
	public Collection<Object> numberTenderByAdministrative() {
		final Collection<Object> numbers = this.executiveRepository.numberTenderByUser();

		return numbers;
	}

	public Collection<Object> tendersByInterestLevel() {
		final Collection<Object> numbers = this.executiveRepository.tendersByInterestLevel();

		return numbers;
	}

	public Collection<Object> offersByState() {
		final Collection<Object> numbers = this.executiveRepository.offersByState();

		return numbers;
	}

	public Collection<Object> offersByStateAndCommercial() {
		final Collection<Object> numbers = this.executiveRepository.offersByStateAndCommercial();

		return numbers;
	}

	public Collection<Object> offersByStateRatio() {
		final Collection<Object> numbers = this.executiveRepository.offersByStateRatio();

		return numbers;
	}

	public Collection<Object> offersByStateAndCommercialRatio() {
		final Collection<Object> numbers = this.executiveRepository.offersByStateAndCommercialRatio();

		return numbers;
	}

	public Collection<Offer> findTopOffersOnMonth(Date fecha, int pageSize) {
		return this.executiveRepository.findTopOffersOnMonth(fecha, new PageRequest(0, pageSize));
	}

	public List<Offer> findTopWinedOffersOnThreeMonthsFrom(Date from, Date to, int pageSize) {
		return this.executiveRepository.findTopWinedOffersOnThreeMonths(from, to, new PageRequest(0, pageSize));
	}

	public Collection<Object> findAvgRatioAmounOfferOverTender() {
		return this.executiveRepository.findAvgRatioAmounOfferOverTender();
	}

	public Collection<Object> findTopFrecuentsCompanies(int pageSize) {
		return this.executiveRepository.findTopFrecuentsCompanies(new PageRequest(0, 10));
	}

	public Collection<Object> findTopFrecuentsWinnersCompanies(int pageSize) {
		return this.executiveRepository.findTopFrecuentsWinnersCompanies(new PageRequest(0, 10));
	}

	public List<Tender> findHighInterestNoOferTendersCloseToExpire(Date from, Date to) {
		return this.executiveRepository.findHighInterestNoOferTendersCloseToExpire(from, to);
	}

	public List<Tender> findHighInterestTendersWithAbandonedOfferCloseToExpire(Date from, Date to) {
		return this.executiveRepository.findHighInterestTendersWithAbandonedOfferCloseToExpire(from, to);
	}

	public Collection<AdministrativeRequest> findRejectedAdministrativeRequest() {
		return this.executiveRepository.findRejectedAdministrativeRequest();
	}

	public Collection<CollaborationRequest> findRejectedComercialRequest() {
		return this.executiveRepository.findRejectedComercialRequest();
	}

	public Collection<Object> findAvgAndDevPerncentOfferProffitOnAceptedColaborationRequests() {
		return this.executiveRepository.findAvgAndDevPerncentOfferProffitOnAceptedColaborationRequests();
	}

	public Collection<Object> findAvgAndDevPerncentOfferProffitOnRejectedColaborationRequests() {
		return this.executiveRepository.findAvgAndDevPerncentOfferProffitOnRejectedColaborationRequests();
	}
}
