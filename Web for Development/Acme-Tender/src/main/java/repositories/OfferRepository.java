
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrative;
import domain.Commercial;
import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

	@Query("select o from Offer o where o.commercial.id = ?1")
	Collection<Offer> findAllByCommercialPropietary(int commercialId);

	@Query("select o from Offer o where o.commercial.id != ?1 and o.id in (select sb.offer.id from SubSection sb where sb.commercial.id = ?1)")
	Collection<Offer> findAllByCommercialCollaboration(int evaluationCriteriaId);

	@Query("select o from Offer o where o.id in (select sb.offer.id from SubSection sb where sb.administrative.id = ?1)")
	Collection<Offer> findAllByAdministrativeCollaboration(int evaluationCriteriaId);

	@Query("select sb.commercial from SubSection sb where sb.offer.id = ?1 and sb.commercial != null group by sb.commercial")
	Collection<Commercial> findCommercialCollaboratorsByOffer(int offerId);

	@Query("select sb.administrative from SubSection sb where sb.offer.id = ?1 and sb.administrative != null group by sb.administrative")
	Collection<Administrative> findAdministrativeCollaboratorsByOffer(int offerId);

	@Query("select o from Offer o where o.published = true ")
	Collection<Offer> findAllPublished();

	@Query("select o from Offer o where o.published = false ")
	Collection<Offer> findAllNotPublished();

	@Query("select o "
			+ "from Offer o "
			+ "where (o.id in ("
				+ "select ss.offer.id "
				+ "from SubSection ss "
				+ "where ss.title like %?1% "
				+ "or ss.shortDescription like %?1% "
				+ "or ss.body like %?1% "
				+ "or ss.comments like %?1%) "
			+ "or o.tender.title like %?1% "
			+ "or o.tender.expedient like %?1% "
			+ "or o.tender.organism like %?1% "
			+ "or o.tender.bulletin like %?1% "
			+ "or o.tender.observations like %?1% "
			+ "or o.tender.informationPage like %?1% "
			+ "or o.tender.interestComment like %?1%) "
			+ "and o.published = true")
	Collection<Offer> findOfferByKeyword(String word);

	@Query("select o from Offer o where o.tender.id = ?1")
	Offer findByTender(Integer tenderId);

	//	@Query(value = "" + " select * " + " from Offer o " + " where (o.id in (select ss.offer.id from SubSection ss where (ss.title regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1"
	//		+ " OR ss.shortDescription regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1" + " OR ss.body regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1"
	//		+ " OR ss.comments regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1))) ", nativeQuery = true)
	//	Collection<Object[]> findAllOfferWithTabooWord();

}
