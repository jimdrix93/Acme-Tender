
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

	@Query("select f from File f where f.subSection.id = ?1")
	Collection<File> findBySubsection(int subSectionId);
	
	@Query("select f from File f where f.tender.id = ?1")
	Collection<File> findByTender(int tenderId);
	
	@Query("select f from File f where f.tenderResult.id = ?1")
	Collection<File> findByTenderResult(int tenderResultId);
	
	@Query("select f from File f where f.curriculum.id = ?1")
	Collection<File> findByCurriculum(int curriculumId);	
}
