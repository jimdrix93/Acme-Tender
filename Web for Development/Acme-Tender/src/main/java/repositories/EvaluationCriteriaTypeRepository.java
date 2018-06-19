
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.EvaluationCriteriaType;

@Repository
public interface EvaluationCriteriaTypeRepository extends JpaRepository<EvaluationCriteriaType, Integer> {

}
