
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CollaborationRequest;

@Repository
public interface CollaborationRequestRepository extends JpaRepository<CollaborationRequest, Integer> {

	@Query("select c from CollaborationRequest c where c.offer in (select o from Offer o where o.commercial.id = ?1)")
	Collection<CollaborationRequest> getSentCollaborationRequestsFromCommercialId(int commercialId);

	@Query("select c from CollaborationRequest c where c.commercial.id = ?1")
	Collection<CollaborationRequest> getReceivedCollaborationRequestsFromCommercialId(int commercialId);

	@Query("select c from CollaborationRequest c where c.offer.id = ?1")
	Collection<CollaborationRequest> findAllByOffer(int offer);

}
