
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Commercial;

@Repository
public interface CommercialRepository extends JpaRepository<Commercial, Integer> {

	@Query("select distinct c from SubSection s join s.commercial c where s.offer.id = ?1")
	Collection<Commercial> getSubSectionCommercialsFromOfferId(int offerId);

}
