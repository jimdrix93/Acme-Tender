
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AdministrativeRequest;
import domain.CollaborationRequest;
import domain.Executive;
import domain.Offer;
import domain.Tender;

@Repository
public interface ExecutiveRepository extends JpaRepository<Executive, Integer> {

	@Query("select t.administrative.id, t.administrative.userAccount.username, count(t.id) from Tender t group by t.administrative.id, t.administrative.name")
	Collection<Object> numberTenderByUser();

	@Query("select sum(case when t.interest = 'UNDEFINED' then 1 else 0 end)*100/count(t), sum(case when t.interest = 'HIGH' then 1 else 0 end)*100/count(t), sum(case when t.interest = 'MEDIUM' then 1 else 0 end)*100/count(t), sum(case when t.interest = 'LOW' then 1 else 0 end)*100/count(t) from Tender t")
	Collection<Object> tendersByInterestLevel();

	@Query("select sum(case when o.state='CREATED' then 1 else 0 end),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end),  sum(case when o.state='ABANDONED' then 1 else 0 end), sum(case when o.state='PRESENTED' then 1 else 0 end), sum(case when o.state='WINNED' then 1 else 0 end), sum(case when o.state='LOSSED' then 1 else 0 end),  sum(case when o.state='CHALLENGED' then 1 else 0 end),  sum(case when o.state='DENIED' then 1 else 0 end) from Offer o")
	Collection<Object> offersByState();

	@Query("select o.commercial.name, sum(case when o.state='CREATED' then 1 else 0 end),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end),  sum(case when o.state='ABANDONED' then 1 else 0 end), sum(case when o.state='PRESENTED' then 1 else 0 end), sum(case when o.state='WINNED' then 1 else 0 end), sum(case when o.state='LOSSED' then 1 else 0 end),  sum(case when o.state='CHALLENGED' then 1 else 0 end),  sum(case when o.state='DENIED' then 1 else 0 end) from Offer o group by o.commercial.name")
	Collection<Object> offersByStateAndCommercial();

	@Query("select sum(case when o.state='CREATED' then 1 else 0 end)*100/count(o),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end)*100/count(o),  sum(case when o.state='ABANDONED' then 1 else 0 end)*100/count(o), sum(case when o.state='PRESENTED' then 1 else 0 end)*100/count(o), sum(case when o.state='WINNED' then 1 else 0 end)*100/count(o), sum(case when o.state='LOSSED' then 1 else 0 end)*100/count(o),  sum(case when o.state='CHALLENGED' then 1 else 0 end)*100/count(o),  sum(case when o.state='DENIED' then 1 else 0 end)*100/count(o)  from Offer o")
	Collection<Object> offersByStateRatio();

	@Query("select o.commercial.name, sum(case when o.state='CREATED' then 1 else 0 end)*100/count(o),  sum(case when o.state='IN_DEVELOPMENT' then 1 else 0 end)*100/count(o),  sum(case when o.state='ABANDONED' then 1 else 0 end)*100/count(o), sum(case when o.state='PRESENTED' then 1 else 0 end)*100/count(o), sum(case when o.state='WINNED' then 1 else 0 end)*100/count(o), sum(case when o.state='LOSSED' then 1 else 0 end)*100/count(o),  sum(case when o.state='CHALLENGED' then 1 else 0 end)*100/count(o),  sum(case when o.state='DENIED' then 1 else 0 end)*100/count(o) from Offer o group by o.commercial.name")
	Collection<Object> offersByStateAndCommercialRatio();
	
	@Query("select o "
			+ "from Offer o "
			+ "where year(o.presentationDate) = year(?1) "
			+ "and month(o.presentationDate) = month(?1) "
			+ "order by o.amount desc")
	List<Offer> findTopOffersOnMonth(Date date, Pageable pageSize);
	
	@Query("select o from Offer o "
			+ "where o.presentationDate between ?1 and ?2 "
			+ "and o.state = 'WINNED' "
			+ "order by o.amount desc")
	List<Offer> findTopWinedOffersOnThreeMonths(Date from, Date to, Pageable pageSize);
	
	@Query("select cr.name, avg(cr.economicalOffer / t.estimatedAmount) from CompanyResult cr join cr.tenderResult tr join tr.tender t where t.estimatedAmount != 0 group by cr.name")
	Collection<Object> findAvgRatioAmounOfferOverTender();

	@Query("select cr.name, count(cr.name) from CompanyResult cr group by cr.name order by count(cr.name) desc")
	List<Object> findTopFrecuentsCompanies(Pageable pageSize);

	@Query("select cr.name, count(cr.name) from CompanyResult cr "
			+ "where cr.state = 'WINNER' "
			+ "group by cr.name "
			+ "order by count(cr.name) desc")
	List<Object> findTopFrecuentsWinnersCompanies(Pageable pageSize);

	@Query("select t from Tender t "
			+ "where t.maxPresentationDate between ?1 and ?2 "
			+ "and t.interest='HIGH' "
			+ "and not exists (select o from Offer o where t=o.tender) "
			+ "order by t.estimatedAmount desc")
	List<Tender> findHighInterestNoOferTendersCloseToExpire(Date from, Date to);
	
	@Query("select t from Tender t "
			+ "where t.maxPresentationDate between ?1 and ?2 "
			+ "and exists (select o from Offer o where t=o.tender and o.state='ABANDONED') "
			+ "order by t.estimatedAmount desc")
	List<Tender> findHighInterestTendersWithAbandonedOfferCloseToExpire(Date from, Date to);
	
	@Query("select ar "
			+ "from AdministrativeRequest ar "
			+ "where ar.accepted = false " 
			+ "order by ar.maxAcceptanceDate desc")
	Collection<AdministrativeRequest> findRejectedAdministrativeRequest();
	
	@Query("select cr "
			+ "from CollaborationRequest cr "
			+ "where cr.accepted = false " 
			+ "order by cr.maxAcceptanceDate desc")
	Collection<CollaborationRequest> findRejectedComercialRequest();
	
	@Query("select avg(cr.benefitsPercentage), "
			+ "sqrt(sum(cr.benefitsPercentage*cr.benefitsPercentage)/count(cr.benefitsPercentage) "
			+ "- avg(cr.benefitsPercentage)*avg(cr.benefitsPercentage)) "
			+ "from CollaborationRequest cr "
			+ "where cr.accepted = true")
	Collection<Object> findAvgAndDevPerncentOfferProffitOnAceptedColaborationRequests();

	@Query("select avg(cr.benefitsPercentage), "
			+ "sqrt(sum(cr.benefitsPercentage*cr.benefitsPercentage)/count(cr.benefitsPercentage) "
			+ "- avg(cr.benefitsPercentage)*avg(cr.benefitsPercentage)) "
			+ "from CollaborationRequest cr "
			+ "where cr.accepted = false")
	Collection<Object> findAvgAndDevPerncentOfferProffitOnRejectedColaborationRequests();
	
}
