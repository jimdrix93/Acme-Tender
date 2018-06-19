
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	@Query("select c from Curriculum c where c.subSection.id = ?1")
	Collection<Curriculum> getCurriculumsFromSubSectionId(int subSectionId);

	@Query("select c from Curriculum c where c.subSection in (select s from SubSection s where s.commercial.id = ?1)")
	Collection<Curriculum> getCurriculumsFromCommercialId(int commercialId);

}
