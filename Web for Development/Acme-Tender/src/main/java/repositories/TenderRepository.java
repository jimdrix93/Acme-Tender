
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tender;

@Repository
public interface TenderRepository extends JpaRepository<Tender, Integer> {

	@Query("select t from Tender t where t.administrative.id = ?1")
	Collection<Tender> findAllByAdministrative(int id);

	@Query("select t from Tender t where t.offertable = true")
	Collection<Tender> findAllOffertable();
 
	@Query("select t from Tender t " 
			+ "where (t.title like %?1% "
			+ "or t.expedient like %?1% "
			+ "or t.reference like %?1% "
			+ "or t.organism like %?1% "
			+ "or t.bulletin like %?1% "
			+ "or t.observations like %?1% "
			+ "or t.informationPage like %?1% "
			+ "or t.interestComment like %?1%) ")
	public Collection<Tender> findTenderByKeyword(String word);

	@Query(value = "select * " + " from Tender t" + " where " + "      t.title regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1 "
		+ "   OR t.expedient regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1 " + "   OR t.organism regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1 "
		+ "   OR t.bulletin regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1 " + "   OR t.observations regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1 "
		+ "   OR t.informationPage regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1 " + "   OR t.interestComment regexp (select group_concat(t.`text` SEPARATOR '|') from TabooWord t) = 1 ", nativeQuery = true)
	public Collection<Object[]> findAllTenderWithTabooWord();

	@Query("select count(*) from Tender t where t.reference = ?1")
	public Integer checkReference(String reference);

}
