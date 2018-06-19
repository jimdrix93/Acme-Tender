
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrative;
import domain.Commercial;

@Repository
public interface AdministrativeRepository extends JpaRepository<Administrative, Integer> {

	@Query("select distinct a from SubSection s join s.administrative a where s.offer.id = ?1")
	Collection<Commercial> getSubSectionAdministrativesFromOfferId(int offerId);

}
