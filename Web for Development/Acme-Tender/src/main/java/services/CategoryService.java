
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	//Repository
	@Autowired
	private CategoryRepository		categoryRepository;

	@Autowired
	private AdministratorService	administratorService;


	//CRUDS

	//Create
	public Category create(final Integer parentCategoryId) {
		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		Category result;
		result = new Category();

		if (parentCategoryId == null)
			result.setFatherCategory(null);
		else
			result.setFatherCategory(this.findOne(parentCategoryId));

		return result;
	}
	//Save

	public Category save(final Category category) {
		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		Assert.isTrue(this.categoryRepository.findAllTendersByCategory(category.getId()).size() == 0, "category.cannot.delete.because.has.tender");

		Assert.notNull(category);
		final Category saved = this.categoryRepository.save(category);
		return saved;
	}
	//Delete
	public void delete(final Category category) {
		Assert.notNull(category);

		final Administrator administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);

		Assert.isTrue(this.categoryRepository.findAllTendersByCategory(category.getId()).size() == 0, "category.cannot.delete.because.has.tender");
		Assert.isTrue(this.getChildCategories(category.getId()).size() == 0, "category.cannot.delete.because.has.childs");

		this.categoryRepository.delete(category);
	}
	//findOne
	public Category findOne(final int categoryId) {
		Category result;
		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);
		return result;
	}

	//findAll
	public Collection<Category> findAll() {

		Collection<Category> result;

		result = this.categoryRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	//Other
	public Collection<Category> getChildCategories(final int parentCategoryId) {

		return this.categoryRepository.getChildCategories(parentCategoryId);
	}

	public Collection<Category> getFirstLevelCategories() {

		return this.categoryRepository.getFirstLevelCategories();

	}

	public void flush() {
		this.categoryRepository.flush();

	}

}
