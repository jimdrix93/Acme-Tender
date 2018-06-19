/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Tender;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select c from Category c where c.fatherCategory.id= ?1")
	Collection<Category> getChildCategories(int parentCategoryId);

	@Query("select c from Category c where c.fatherCategory = null")
	Collection<Category> getFirstLevelCategories();

	@Query("select t from Tender t where t.category.id = ?1")
	Collection<Tender> findAllTendersByCategory(int categoryId);
}
