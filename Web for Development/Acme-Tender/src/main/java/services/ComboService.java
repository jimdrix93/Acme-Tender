
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Actor;
import domain.CompanyResult;
import domain.Constant;
import domain.Executive;
import domain.Offer;

@Service
@Transactional
public class ComboService {

	// Managed repositories ------------------------------------------------
	
	// Managed services
	@Autowired
	ActorService actorService;
	@Autowired
	OfferService offerService;
	@Autowired
	CompanyResultService companyResultService;
	

	// Constructor ----------------------------------------------------------
	public ComboService() {
		super();
	}

	public Collection<String> offerStates(int offerId) {
		
		//Obtenemos el estado desde el que partimos
		String actualState= "";
		if (offerId == 0) {
			actualState = "";
		} else {
			Offer offer = this.offerService.findOne(offerId);
			actualState = offer.getState();
		}
		
		
		Collection<String> result = new ArrayList<String>();
		
		//el combo tiene que contener el estado en el que estamos
		if (!actualState.equals(""))
			result.add(actualState);
		
		switch (actualState) {
		
		case "":
			result.add(Constant.OFFER_CREATED);
			break;
			
		case Constant.OFFER_CREATED:
			result.add(Constant.OFFER_IN_DEVELOPMENT);
			result.add(Constant.OFFER_ABANDONED);
			break;
			
		case Constant.OFFER_IN_DEVELOPMENT:
			result.add(Constant.OFFER_PRESENTED);
			result.add(Constant.OFFER_ABANDONED);
			break;

		case Constant.OFFER_ABANDONED:
			break;
			
		case Constant.OFFER_PRESENTED:
			result.add(Constant.OFFER_WINNED);
			result.add(Constant.OFFER_LOSED);
			result.add(Constant.OFFER_CHALLENGED);
			break;
			
		case Constant.OFFER_WINNED:
			break;
			
		case Constant.OFFER_LOSED:
			break;
			
		case Constant.OFFER_CHALLENGED:
			break;
			
		case Constant.OFFER_DENIED:
			break;
			
		}
		
		return result;
	}

	
	public Collection<String> collaborationRequestSections() {
		
		Collection<String> result = new ArrayList<String>();
		
		result.add(Constant.SECTION_TECHNICAL_OFFER);
		result.add(Constant.SECTION_ECONOMICAL_OFFER);
		
		return result;
	}	
	
	public Collection<String> subSectionSections() {
		
		Collection<String> result = new ArrayList<String>();
		
		result.add(Constant.SECTION_ADMINISTRATIVE_ACREDITATION);
		result.add(Constant.SECTION_TECHNICAL_OFFER);
		result.add(Constant.SECTION_ECONOMICAL_OFFER);
		
		return result;
	}	
	
	public Collection<String> tenderInterests() {
		
		Collection<String> result = new ArrayList<String>();
		
		result.add(Constant.TENDER_INTEREST_UNDEFINED);
		result.add(Constant.TENDER_INTEREST_LOW);
		result.add(Constant.TENDER_INTEREST_MEDIUM);
		result.add(Constant.TENDER_INTEREST_HIGH);
		
		return result;
	}		
	
	public Collection<String> companyResultStates(CompanyResult companyResult) {
		
		Collection<String> result = new ArrayList<String>();
		
		Collection<CompanyResult> winners = this.companyResultService.findAllWinnerByTenderResult(companyResult.getTenderResult().getId());
		if (winners.size() == 0)
			result.add(Constant.COMPANY_RESULT_WINNER);
		
		result.add(Constant.COMPANY_RESULT_LOSER);
		result.add(Constant.COMPANY_RESULT_RECKLESS_OFFER);
		
		return result;
	}		
	
}
