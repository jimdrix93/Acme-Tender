
package usecases;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccountService;
import services.ActorService;
import services.CategoryService;
import services.ConfigurationService;
import services.MyMessageService;
import services.OfferService;
import services.TabooWordService;
import services.TenderService;
import utilities.AbstractTest;
import domain.Category;
import domain.Configuration;
import domain.MyMessage;
import domain.Offer;
import domain.TabooWord;
import domain.Tender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UseCaseAdministrator extends AbstractTest {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private CategoryService			categoryService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private TabooWordService		tabooWordService;
	@Autowired
	private TenderService			tenderService;
	@Autowired
	private OfferService			offerService;
	@Autowired
	private MyMessageService		myMessageService;
	@Autowired
	private ConfigurationService	configurationService;


	/*
	 * Caso de uso:
	 * Admin-> Aceptar o rechazar las solicitudes de registro.(CU12)
	 */
	@Test
	public void activateAccountTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", "executive3", null
			}, {//Positive
				"admin", "administrative1", null
			}, {// Negative: without name
				"executive1", "executive3", ClassCastException.class
			}, {// Negative: with name null
				"admin", "", NumberFormatException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateActivateAccountTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateActivateAccountTest(final Integer i, final String principal, final String account, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			this.actorService.ActivateOrDeactivate(super.getEntityId(account));

			this.actorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Admin-> Añadir categorías de concurso a la jerarquía.(CU13)
	 */
	@Test
	public void createCategoryTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", "nameCategory", "descriptionCategory", "category1", null
			}, {//Positive
				"admin", "nameCategory2", "descriptionCategory2", null, null
			}, {// Negative: with executive like principal
				"executive1", "nameCategory2", "descriptionCategory2", "category1", ClassCastException.class
			}, {// Negative: with name null
				"admin", "", "descriptionCategory2", "category1", ConstraintViolationException.class
			}, {// Negative: with description null
				"admin", "nameCategory3", "", "category1", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateCategoryTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void templateCreateCategoryTest(final Integer i, final String principal, final String nameCategory, final String descriptionCategory, final String categoryParent, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			Integer categoryId = 0;
			if (categoryParent == "" || categoryParent == null)
				categoryId = null;
			else
				categoryId = super.getEntityId(categoryParent);

			final Category category = this.categoryService.create(categoryId);
			category.setName(nameCategory);
			category.setDescription(descriptionCategory);

			this.categoryService.save(category);
			this.categoryService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Admin-> Podrá editar categorías si no están siendo utilizadas por ningún concurso.(CU14)
	 */
	@Test
	public void editCategoryTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", "category1", "descriptionCategory", null
			}, {// Negative: with executive like principal
				"executive1", "category1", "descriptionCategory1", ClassCastException.class
			}, {// Negative: category used by tender
				"admin", "category4", "descriptionCategory2", IllegalArgumentException.class
			}, {// Negative: without description
				"admin", "category1", "", ConstraintViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditCategoryTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateEditCategoryTest(final Integer i, final String principal, final String category, final String descriptionCategory, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			Integer categoryId = 0;
			if (category == "" || category == null)
				categoryId = null;
			else
				categoryId = super.getEntityId(category);

			final Category categoryBD = this.categoryService.findOne(categoryId);
			categoryBD.setDescription(descriptionCategory);

			this.categoryService.save(categoryBD);
			this.categoryService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Admin-> Podrá eliminar categorías si no están siendo utilizadas por ningún concurso.(CU14)
	 */
	@Test
	public void deleteCategoryTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", "category7", null
			}, {// Negative: with executive like principal
				"executive1", "category7", IllegalArgumentException.class
			}, {// Negative: category used by tender
				"admin", "category4", IllegalArgumentException.class
			}, {// Negative: without description
				"admin", "category7", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteCategoryTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDeleteCategoryTest(final Integer i, final String principal, final String category, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			Integer categoryId = 0;
			if (category == "" || category == null)
				categoryId = null;
			else
				categoryId = super.getEntityId(category);

			final Category categoryBD = this.categoryService.findOne(categoryId);

			this.categoryService.delete(categoryBD);
			this.categoryService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Admin-> Crear palabras tabú.(CU15)
	 */
	@Test
	public void createTabooWordTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", "text1", null
			}, {// Negative: with executive like principal
				"executive1", "text1", ClassCastException.class
			}, {// Negative: without text
				"admin", "", ConstraintViolationException.class
			}, {// Negative: with anonymous user
				"", "text1", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateTabooWordTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateCreateTabooWordTest(final Integer i, final String principal, final String text, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			final TabooWord tabooWord = this.tabooWordService.create();
			tabooWord.setText(text);

			this.tabooWordService.save(tabooWord);
			this.tabooWordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Admin-> Borrar palabras tabú.(CU16)
	 */
	@Test
	public void deleteTabooWordTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", "tabooWord1", null
			}, {// Negative: with executive like principal
				"executive1", "tabooWord1", ClassCastException.class
			}, {// Negative: without tabooWord
				"admin", "", NullPointerException.class
			}, {// Negative: with anonymous user
				"", "tabooWord1", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteTabooWordTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDeleteTabooWordTest(final Integer i, final String principal, final String tabooWord, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			Integer tabooId = 0;
			if (tabooWord == "" || tabooWord == null)
				tabooId = null;
			else
				tabooId = super.getEntityId(tabooWord);

			final TabooWord tabooWordBD = this.tabooWordService.findOne(tabooId);

			this.tabooWordService.delete(tabooWordBD);
			this.tabooWordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Admin-> Editar palabras tabú.(CU17)
	 */
	@Test
	public void editTabooWordTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", "tabooWord1", "text1", null
			}, {// Negative: with executive like principal
				"executive1", "tabooWord1", "text1", ClassCastException.class
			}, {// Negative: without text
				"admin", "", "text1", NullPointerException.class
			}, {// Negative: with anonymous user
				"", "tabooWord1", "text1", IllegalArgumentException.class
			}, {// Positive
				"admin", "tabooWord1", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditTabooWordTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateEditTabooWordTest(final Integer i, final String principal, final String tabooWord, final String text, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			Integer tabooId = 0;
			if (tabooWord == "" || tabooWord == null)
				tabooId = null;
			else
				tabooId = super.getEntityId(tabooWord);

			final TabooWord tabooWordBD = this.tabooWordService.findOne(tabooId);
			tabooWordBD.setText(text);

			this.tabooWordService.save(tabooWordBD);
			this.tabooWordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Admin-> Listar las ofertas que contienen palabras tabú.(CU18)
	 */
	@Test
	public void listOfferTabooWordTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", null
			}, {// Negative: with executive like principal
				"executive1", ClassCastException.class
			}, {// Negative: with anonymous user
				"", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListOfferTabooWordTest(i, (String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateListOfferTabooWordTest(final Integer i, final String principal, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			final Collection<Offer> offersWithTabooWord = this.offerService.findAllOfferWithTabooWords();

			Assert.notNull(offersWithTabooWord);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Admin-> Listar los concursos que contienen palabras tabú.(CU19)
	 */
	@Test
	public void listTenderTabooWordTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", null
			}, {// Negative: with executive like principal
				"executive1", ClassCastException.class
			}, {// Negative: with anonymous user
				"", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListTenderTabooWordTest(i, (String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateListTenderTabooWordTest(final Integer i, final String principal, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			final Collection<Tender> tendersWithTabooWord = this.tenderService.findAllTenderWithTabooWords();

			Assert.notNull(tendersWithTabooWord);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Admin-> Enviar un mensaje de "broadcast" a todos los usuarios del sistema.(CU20)
	 */
	@Test
	public void sendBroadcastTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", "subject1", "body1", "HIGH", null
			}, {// Negative: with executive like principal
				"executive1", "subject1", "body1", "HIGH", IllegalArgumentException.class
			}, {// Negative: with anonymous user
				"", "subject1", "body1", "HIGH", IllegalArgumentException.class
			}, {// Negative: without body
				"admin", "subject1", "", "HIGH", ConstraintViolationException.class
			}, {// Negative: without priority
				"admin", "subject1", "body1", "", ConstraintViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSendBroadcastTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void templateSendBroadcastTest(final Integer i, final String principal, final String subject, final String body, final String priority, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			final MyMessage message = this.myMessageService.create();
			message.setBody(body);
			message.setBroadcast(true);
			message.setPriority(priority);
			message.setSubject(subject);
			message.setRecipient(message.getSender());

			Assert.notNull(message);

			this.myMessageService.broadcastMessage(message);
			this.myMessageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth Executive-> Administrar el porcentaje de comisión del sistema.(CU55)
	 */
	@Test
	public void comissionPercentajeTest() {

		final Object testingData[][] = {
			{// Positive
				"admin", "configuration", null
			}, {//Negative
				"administrative1", "configuration", ClassCastException.class
			}, {//Negative
				"executive1", "configuration", ClassCastException.class
			}, {// Negative: without user
				"", "configuration", IllegalArgumentException.class
			}, {// Negative: with user not exist
				"user55", "configuration", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateComissionPercentajeTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateComissionPercentajeTest(final Integer i, final String principal, final String config, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final Configuration configuration = this.configurationService.findOne(super.getEntityId(config));

			configuration.setBenefitsPercentage(8.50);

			this.configurationService.save(configuration);
			this.configurationService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
