
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TenderResult;

@Repository
public interface TenderResultRepository extends JpaRepository<TenderResult, Integer> {

	@Query("select tr from TenderResult tr where tr.tender.id = ?1")
	TenderResult findOneByTender(int tenderId);

}
