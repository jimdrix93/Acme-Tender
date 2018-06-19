
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SubSectionEvaluationCriteria;

@Repository
public interface SubSectionEvaluationCriteriaRepository extends JpaRepository<SubSectionEvaluationCriteria, Integer> {

	
	@Query("select sec from SubSectionEvaluationCriteria sec where sec.evaluationCriteria.id = ?1")
	Collection<SubSectionEvaluationCriteria> findAllWithEvaluationCriteria(int evaluationCriteriaId); 
	
	@Query("select sec from SubSectionEvaluationCriteria sec where sec.subSection.id = ?1")
	Collection<SubSectionEvaluationCriteria> findAllBySubSection(int subSectionId); 
	
	@Query("select sec from SubSectionEvaluationCriteria sec where sec.subSection.offer.id = ?1 and sec.evaluationCriteria.id = ?2")
	Collection<SubSectionEvaluationCriteria> findByOfferAndEvaluationCriteria(int offerId, int evaluationCriteriaId); 
	
	
	
}
