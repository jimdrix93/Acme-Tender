
package usecases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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
import services.AdministrativeRequestService;
import services.CategoryService;
import services.CompanyResultService;
import services.EvaluationCriteriaService;
import services.EvaluationCriteriaTypeService;
import services.MyMessageService;
import services.SubSectionService;
import services.TenderResultService;
import services.TenderService;
import utilities.AbstractTest;
import domain.AdministrativeRequest;
import domain.Category;
import domain.CompanyResult;
import domain.EvaluationCriteria;
import domain.EvaluationCriteriaType;
import domain.SubSection;
import domain.Tender;
import domain.TenderResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UseCaseAdministrative extends AbstractTest {

	@Autowired
	private ActorService					actorService;
	@Autowired
	private TenderService					tenderService;
	@Autowired
	private EvaluationCriteriaService		evaluationCriteriaService;
	@Autowired
	private EvaluationCriteriaTypeService	evaluationCriteriaTypeService;
	@Autowired
	private UserAccountService				userAccountService;
	@Autowired
	private CategoryService					categoryService;
	@Autowired
	private TenderResultService				tenderResultService;
	@Autowired
	private CompanyResultService			companyResultService;
	@Autowired
	private AdministrativeRequestService	administrativeRequestService;
	@Autowired
	private SubSectionService				subSectionService;
	@Autowired
	private MyMessageService				myMessageService;


	/* SUIT DE TESTS FUNCIONALES: CREAR CONCURSO */
	/*
	 * 10. Un usuario autenticado como administrativo podrá:
	 * a. Administrar sus concursos, lo que incluye crearlos, editarlos o eliminarlos.
	 */
	/* Autenticado como administrativo --> crear un concurso */
	@Test
	public void createTenderTest() {
		final Object testingData[][] = {
			//Positivo(sin observaciones ni comentario de interés)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "", null
			},
			//Positivo(con observaciones)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "observaciones", "http://www.juntadeandalucia.es/index.html", "HIGH", "", null
			},
			//Positivo(con comentario de interés)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "comentario de interés", null
			},
			//Negativo(autenticado como comercial)
			{
				"commercial1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "comentario de interés",
				IllegalArgumentException.class
			},
			//Negativo(sin título)
			{
				"administrative1", "", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "",
				ConstraintViolationException.class
			},
			//Negativo(con fecha del boletín futura)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/07/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "",
				ConstraintViolationException.class
			},
			//Negativo(con fecha de apertura pasada)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/06/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "",
				ConstraintViolationException.class
			},

			//Negativo(con fecha máxima de presentación anterior a la fecha de apertura)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "03/07/2018 12:00", "01/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "",
				ConstraintViolationException.class
			},

			//Negativo(con cantidad estimada menor que 0)
			{
				"administrative1", "title", "category1", "expedient", -20000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "",
				ConstraintViolationException.class
			},
			//Negativo(con tiempo de ejecución menor que 0)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", -90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "",
				ConstraintViolationException.class
			},
			//Negativo(con página de información incorrecta)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "juntadeandalucia", "HIGH", "", ConstraintViolationException.class
			},
			//Negativo(sin categoría)
			{
				"administrative1", "title", "", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "", ConstraintViolationException.class
			},
			//Negativo(sin expediente)
			{
				"administrative1", "title", "category1", "", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "", ConstraintViolationException.class
			},
			//Negativo(sin organismo)
			{
				"administrative1", "title", "category1", "expedient", 50000., "", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "", ConstraintViolationException.class
			},
			//Negativo(sin boletín)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "", ConstraintViolationException.class
			},
			//Negativo(sin interés)
			{
				"administrative1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "", "",
				ConstraintViolationException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateTender((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (double) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Integer) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(Class<?>) testingData[i][15]);
	}

	private void templateCreateTender(final String principal, final String title, final String category, final String expedient, final Double estimatedAmount, final String organism, final String bulletin, final String bulletinDate,
		final String openingDate, final String maxPresentationDate, final Integer executionTime, final String observations, final String informationPage, final String interest, final String interestComment, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.authenticate(principal);
			final Tender tender = this.tenderService.create();
			tender.setTitle(title);
			Category c;
			if (!category.isEmpty()) {
				final Integer categoryId = super.getEntityId(category);
				c = this.categoryService.findOne(categoryId);
			} else
				c = null;
			tender.setCategory(c);
			tender.setExpedient(expedient);
			tender.setEstimatedAmount(estimatedAmount);
			tender.setOrganism(organism);
			tender.setBulletin(bulletin);
			final DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Date d1 = format.parse(bulletinDate);
			tender.setBulletinDate(d1);
			final Date d2 = format.parse(openingDate);
			tender.setOpeningDate(d2);
			final Date d3 = format.parse(maxPresentationDate);
			tender.setMaxPresentationDate(d3);
			tender.setExecutionTime(executionTime);
			tender.setObservations(observations);
			tender.setInformationPage(informationPage);
			tender.setInterest(interest);
			tender.setInterestComment(interestComment);

			final Tender saved = this.tenderService.save(tender);
			this.tenderService.flush();
			final Collection<Tender> tenders = this.tenderService.findAllByAdministrative();
			Assert.isTrue(tenders.contains(saved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 10. Un usuario autenticado como administrativo podrá:
	 * a. Administrar sus concursos, lo que incluye crearlos, editarlos o eliminarlos.
	 */
	/* Autenticado como administrativo --> editar un concurso */
	@Test
	public void editTenderTest() {
		final Object testingData[][] = {
			//Positivo(sin observaciones ni comentario de interés)
			{
				"administrative1", "tender1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "", null
			},

			//Negativo(autenticado como otro usuario)
			{
				"administrative2", "tender1", "title", "category1", "expedient", 50000., "organism", "", "03/06/2018 12:00", "01/07/2018 12:00", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "HIGH", "",
				IllegalArgumentException.class
			},
			//Negativo(sin fecha de comienzo)
			{
				"administrative1", "tender1", "title", "category1", "expedient", 50000., "organism", "bulletin", "03/06/2018 12:00", "", "03/07/2018 12:00", 90, "", "http://www.juntadeandalucia.es/index.html", "", "", NullPointerException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateTender((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Integer) super.getEntityId((String) testingData[i][3]), (String) testingData[i][4],
				(double) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (Integer) testingData[i][11], (String) testingData[i][12],
				(String) testingData[i][13], (String) testingData[i][14], (String) testingData[i][15], (Class<?>) testingData[i][16]);
	}

	private void templateCreateTender(final String principal, final Integer tenderId, final String title, final Integer categoryId, final String expedient, final Double estimatedAmount, final String organism, final String bulletin,
		final String bulletinDate, final String openingDate, final String maxPresentationDate, final Integer executionTime, final String observations, final String informationPage, final String interest, final String interestComment,
		final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.authenticate(principal);
			final Collection<Tender> tenders = this.tenderService.findAllByAdministrative();
			final Tender tender = this.tenderService.findOneToEdit(tenderId);
			tender.setTitle(title);
			final Category category = this.categoryService.findOne(categoryId);
			tender.setCategory(category);
			tender.setExpedient(expedient);
			tender.setEstimatedAmount(estimatedAmount);
			tender.setOrganism(organism);
			tender.setBulletin(bulletin);
			final DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Date d1 = format.parse(bulletinDate);
			tender.setBulletinDate(d1);
			Date d2;
			if (!openingDate.isEmpty())
				d2 = format.parse(openingDate);
			else
				d2 = null;
			tender.setOpeningDate(d2);
			final Date d3 = format.parse(maxPresentationDate);
			tender.setMaxPresentationDate(d3);
			tender.setExecutionTime(executionTime);
			tender.setObservations(observations);
			tender.setInformationPage(informationPage);
			tender.setInterest(interest);
			tender.setInterestComment(interestComment);

			final Tender saved = this.tenderService.save(tender);
			this.tenderService.flush();
			Assert.isTrue(tenders.contains(saved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * a. Administrar los “criterios de valoración” asociados a sus concursos.
	 */
	/* Autenticado como administrativo --> crear un criterio de valoración */

	@Test
	public void createTenderCriteriaTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "tender1", "title", "description", 30, "evaluationcriteriatype1", null
			}, {// Negativo(sin título)
				"administrative1", "tender1", "title", "", 30, "evaluationcriteriatype1", ConstraintViolationException.class
			}, {// Negativo(con puntuación máxima negativa)
				"administrative1", "tender1", "title", "description", -30, "evaluationcriteriatype1", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateTenderCriteria((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4],
				(Integer) super.getEntityId((String) testingData[i][5]), (Class<?>) testingData[i][6]);
	}
	protected void templateCreateTenderCriteria(final String principal, final Integer tenderId, final String title, final String description, final Integer maxScore, final Integer typeCriteriaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final EvaluationCriteria evCriteria = this.evaluationCriteriaService.create(tenderId);
			final EvaluationCriteriaType evTypeCriteria = this.evaluationCriteriaTypeService.findOne(typeCriteriaId);

			evCriteria.setTitle(title);
			evCriteria.setDescription(description);
			evCriteria.setMaxScore(maxScore);
			evCriteria.setEvaluationCriteriaType(evTypeCriteria);

			final EvaluationCriteria evCriteriaSaved = this.evaluationCriteriaService.save(evCriteria);
			this.evaluationCriteriaService.flush();

			Assert.isTrue(this.evaluationCriteriaService.findAllByTender(tenderId).contains(evCriteriaSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Administrativo
	 * 25.a.3. Editar criterios de valoración asociados a sus concursos.(CU29)
	 */
	@Test
	public void editTenderCriteriaTest() {

		final Object testingData[][] = {
			{// Positive
				"administrative1", "tender1", "criterio1", "criterio1 descipción", "10", "evaluationcriteriatype2", null
			}, {//Positive
				"administrative1", "tender3", "criterio2", "criterio2 descipción", "8", "evaluationcriteriatype1", null
			}, {// Negative: wrong roll
				"executive1", "tender1", "criterio2", "criterio2 descipción", "8", "evaluationcriteriatype1", IllegalArgumentException.class
			}, {// Negative:  tender3 is not administrative1's property
				"administrative1", "tender2", "criterio2", "criterio2 descipción", "8", "evaluationcriteriatype1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditTenderCriteria((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // tenderId
				(String) testingData[i][2], // titleCriteria
				(String) testingData[i][3], // descriptionCriteria
				(Integer) Integer.parseInt((String) testingData[i][4]), // maxScoreCriteria
				(Integer) super.getEntityId((String) testingData[i][5]), // typeCriteriaId
				(Class<?>) testingData[i][6]);
	}
	protected void templateEditTenderCriteria(final String principal, final Integer tenderId, final String titleCriteria, final String descriptionCriteria, final Integer maxScoreCriteria, final Integer typeCriteriaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final EvaluationCriteria evCriteria = this.evaluationCriteriaService.create(tenderId);
			final EvaluationCriteriaType evTypeCriteria = this.evaluationCriteriaTypeService.findOne(typeCriteriaId);

			evCriteria.setTitle(titleCriteria);
			evCriteria.setDescription(descriptionCriteria);
			evCriteria.setMaxScore(maxScoreCriteria);
			evCriteria.setEvaluationCriteriaType(evTypeCriteria);

			final EvaluationCriteria evCriteriaSaved = this.evaluationCriteriaService.save(evCriteria);
			this.evaluationCriteriaService.flush();

			Assert.isTrue(this.evaluationCriteriaService.findAllByTender(tenderId).contains(evCriteriaSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * a. Administrar los “criterios de valoración” asociados a sus concursos.
	 * Solo se podrá eliminar un “criterio de valoración” si no existe una subsección de una oferta asociada a él.
	 */
	/* Autenticado como administrativo --> eliminar un criterio de valoración */

	@Test
	public void deleteTenderCriteriaTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative2", "evaluationcriteria5", null
			}, {// Negativo(criterio de evaluación con subsección de una oferta asociada a él)
				"administrative1", "evaluationcriteria1", IllegalArgumentException.class
			}, {// Negativo(autenticado como comercial)
				"commercial1", "evaluationcriteria5", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteTenderCriteria((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	protected void templateDeleteTenderCriteria(final String principal, final Integer evaluationCriteriaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final EvaluationCriteria evaluationCriteria = this.evaluationCriteriaService.findOne(evaluationCriteriaId);
			// Se busca en la bd el evaluationCriteria
			Assert.isTrue(this.evaluationCriteriaService.findAll().contains(evaluationCriteria));
			this.evaluationCriteriaService.delete(evaluationCriteria);
			this.evaluationCriteriaService.flush();
			// Se busca en la bd de nuevo
			Assert.isTrue(!this.evaluationCriteriaService.findAll().contains(evaluationCriteria), "El elemento se encuentra en la bd");
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * b. Administrar los “tipos de criterios de valoración” existentes en el sistema.
	 */
	/* Autenticado como administrativo --> crear un tipo de criterio de valoración */

	@Test
	public void createTenderCriteriaTypeTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "name", "description", null
			}, {// Negativo(sin nombre)
				"administrative1", "", "description", ConstraintViolationException.class
			}, {// Negativo(sin descripción)
				"administrative1", "name", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateTenderCriteriaType((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void templateCreateTenderCriteriaType(final String principal, final String name, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final EvaluationCriteriaType evCriteriaType = this.evaluationCriteriaTypeService.create();
			evCriteriaType.setName(name);
			evCriteriaType.setDescription(description);
			this.evaluationCriteriaTypeService.save(evCriteriaType);
			this.evaluationCriteriaTypeService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * b. Administrar los “tipos de criterios de valoración” existentes en el sistema.
	 */
	/* Autenticado como administrativo --> editar un tipo de criterio de valoración */

	@Test
	public void editTenderCriteriaTypeTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "evaluationcriteriatype1", "name", "description", null
			}, {// Negativo(autenticado como directivo)
				"executive1", "evaluationcriteriatype1", "name", "description", IllegalArgumentException.class
			}, {// Negativo(sin descripción)
				"administrative1", "evaluationcriteriatype1", "name", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditTenderCriteriaType((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void templateEditTenderCriteriaType(final String principal, final Integer evCriteriaTypeId, final String name, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final EvaluationCriteriaType evCriteriaType = this.evaluationCriteriaTypeService.findOne(evCriteriaTypeId);
			evCriteriaType.setName(name);
			evCriteriaType.setDescription(description);
			this.evaluationCriteriaTypeService.save(evCriteriaType);
			this.evaluationCriteriaTypeService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * b. Administrar los “tipos de criterios de valoración” existentes en el sistema.
	 * Solo se podrá eliminar un “tipo de criterio de valoración” si no existe ningún “criterio de valoración” asociado.
	 */
	/* Autenticado como administrativo --> eliminar un tipo de criterio de valoración */

	@Test
	public void deleteTenderCriteriaTypeTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "evaluationcriteriatype3", null
			}, {// Negativo(autenticado como administrador)
				"admin", "evaluationcriteriatype1", IllegalArgumentException.class
			}, {// Negativo(con criterio de valoración asociado )
				"administrative1", "evaluationcriteriatype1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteTenderCriteriaType((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	protected void templateDeleteTenderCriteriaType(final String principal, final Integer evCriteriaTypeId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final EvaluationCriteriaType evCriteriaType = this.evaluationCriteriaTypeService.findOne(evCriteriaTypeId);
			this.evaluationCriteriaTypeService.delete(evCriteriaType);
			this.evaluationCriteriaTypeService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * c. Administrar los “resultados de concurso” y los “resultados de las empresas” asociados a los concursos creados por él.
	 */
	/* Autenticado como administrativo --> crear un resultado de concurso */

	@Test
	public void createTenderResultTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "tender7", "01/05/2018 12:00", "description", null
			}, {// Negativo(con fecha futura)
				"administrative1", "tender7", "04/07/2018 12:00", "description", ConstraintViolationException.class
			}, {// Negativo(sin descripción)
				"administrative1", "tender7", "01/05/2018 12:00", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateTenderResult((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void templateCreateTenderResult(final String principal, final Integer tenderId, final String date, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final TenderResult tenderResult = this.tenderResultService.create(tenderId);
			final DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Date tenderDate = format.parse(date);
			tenderResult.setTenderDate(tenderDate);
			tenderResult.setDescription(description);
			this.tenderResultService.save(tenderResult);
			this.tenderResultService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * c. Administrar los “resultados de concurso” y los “resultados de las empresas” asociados a los concursos creados por él.
	 */
	/* Autenticado como administrativo --> editar un resultado de concurso */

	@Test
	public void editTenderResultTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "tenderresult1", "01/05/2018 12:00", "description", null
			}, {// Negativo(autenticado como comercial)
				"commercial1", "tenderresult1", "01/05/2018 12:00", "description", IllegalArgumentException.class
			}, {// Negativo(sin descripción)
				"administrative1", "tenderresult1", "01/05/2018 12:00", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditTenderResult((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void templateEditTenderResult(final String principal, final Integer tenderResultId, final String date, final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final TenderResult tenderResult = this.tenderResultService.findOne(tenderResultId);
			final DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Date tenderDate = format.parse(date);
			tenderResult.setTenderDate(tenderDate);
			tenderResult.setDescription(description);
			this.tenderResultService.save(tenderResult);
			this.tenderResultService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * c. Administrar los “resultados de concurso” y los “resultados de las empresas” asociados a los concursos creados por él.
	 */
	/* Autenticado como administrativo --> crear un resultado de empresa */

	@Test
	public void createCompanyResultTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "tenderresult1", "name", 20000., 25., 4, "comments", "LOSER", null
			}, {// Negativo(con puntuación negativa)
				"administrative1", "tenderresult1", "name", 20000., 25., -4, "comments", "LOSER", ConstraintViolationException.class
			}, {// Negativo(con oferta económica negativa)
				"administrative1", "tenderresult1", "name", -20000., 25., 4, "comments", "LOSER", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateCompanyResult((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Integer) testingData[i][5],
				(String) testingData[i][6], (String) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void templateCreateCompanyResult(final String principal, final Integer tenderResultId, final String name, final Double economicalOffer, final Double score, final Integer position, final String comments, final String state,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final CompanyResult companyResult = this.companyResultService.create(tenderResultId);
			companyResult.setName(name);
			companyResult.setEconomicalOffer(economicalOffer);
			companyResult.setScore(score);
			companyResult.setPosition(position);
			companyResult.setComments(comments);
			companyResult.setState(state);
			this.companyResultService.save(companyResult);
			this.companyResultService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * c. Administrar los “resultados de concurso” y los “resultados de las empresas” asociados a los concursos creados por él.
	 */
	/* Autenticado como administrativo --> editar un resultado de empresa */

	@Test
	public void editCompanyResultTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "companyresult1", "name", 20000., 25., 4, "comments", "LOSER", null
			}, {// Negativo(sin nombre)
				"administrative1", "companyresult1", "", 20000., 25., 4, "comments", "LOSER", ConstraintViolationException.class
			}, {// Negativo(con el patrón del estado erróneo)
				"administrative1", "companyresult1", "name", 20000., 25., 4, "comments", "LOOSSERRR", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditCompanyResult((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4], (Integer) testingData[i][5],
				(String) testingData[i][6], (String) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void templateEditCompanyResult(final String principal, final Integer companyResultId, final String name, final Double economicalOffer, final Double score, final Integer position, final String comments, final String state,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final CompanyResult companyResult = this.companyResultService.findOne(companyResultId);
			companyResult.setName(name);
			companyResult.setEconomicalOffer(economicalOffer);
			companyResult.setScore(score);
			companyResult.setPosition(position);
			companyResult.setComments(comments);
			companyResult.setState(state);
			this.companyResultService.save(companyResult);
			this.companyResultService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 25. Un usuario autenticado como administrativo podrá:
	 * c. Administrar los “resultados de concurso” y los “resultados de las empresas” asociados a los concursos creados por él.
	 */
	/* Autenticado como administrativo --> eliminar un resultado de empresa */

	@Test
	public void deleteCompanyResultTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "companyresult1", null
			}, {// Negativo(autenticado como comercial)
				"commercial1", "companyresult1", IllegalArgumentException.class
			}, {// Negativo(resultado de empresa con resultado de concurso de otro administrativo)
				"administrative2", "companyresult1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteCompanyResult((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	protected void templateDeleteCompanyResult(final String principal, final Integer companyResultId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final CompanyResult companyResult = this.companyResultService.findOne(companyResultId);
			this.companyResultService.delete(companyResult);
			this.companyResultService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 33. Un usuario autenticado como administrativo podrá:
	 * a. Aceptar o rechazar una “solicitud administrativa”. Solo podrá aceptar o
	 * rechazar una “solicitud administrativa” si no ha pasado la fecha máxima
	 * de contestación de dicha solicitud. En caso de rechazarla deberá de
	 * indicar el motivo.
	 */
	/* Autenticado como administrativo --> aceptar una solicitud administrativa */

	@Test
	public void acceptAdministrativeRequestTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "administrativerequest1", null
			}, {// Negativo(autenticado como administrador)
				"admin", "administrativerequest1", IllegalArgumentException.class
			}, {// Negativo(con solicitud ya aceptada)
				"administrative1", "administrativerequest3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateAcceptAdministrativeRequest((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	protected void templateAcceptAdministrativeRequest(final String principal, final Integer administrativeRequestId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final AdministrativeRequest administrativeRequest = this.administrativeRequestService.findOneToEdit(administrativeRequestId);
			administrativeRequest.setAccepted(true);
			final AdministrativeRequest savedAR = this.administrativeRequestService.save(administrativeRequest);
			this.administrativeRequestService.flush();
			this.myMessageService.administrativeRequestNotification(savedAR, true);
			this.myMessageService.flush();
			final SubSection subSection = this.subSectionService.createByAdministrativeCollaborationAcceptation(administrativeRequest);
			this.subSectionService.save(subSection);
			this.subSectionService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 33. Un usuario autenticado como administrativo podrá:
	 * a. Aceptar o rechazar una “solicitud administrativa”. Solo podrá aceptar o
	 * rechazar una “solicitud administrativa” si no ha pasado la fecha máxima
	 * de contestación de dicha solicitud.
	 */
	/* Autenticado como administrativo --> aceptar una solicitud administrativa */

	@Test
	public void rejectAdministrativeRequestTest() {

		final Object testingData[][] = {
			{	// Positivo
				"administrative1", "administrativerequest1", null
			}, {// Negativo(autenticado como otro administrativo)
				"administrative2", "administrativerequest1", IllegalArgumentException.class
			}, {// Negativo(con solicitud aceptada)
				"administrative1", "administrativerequest3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRejectAdministrativeRequest((String) testingData[i][0], (Integer) super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	protected void templateRejectAdministrativeRequest(final String principal, final Integer administrativeRequestId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final AdministrativeRequest administrativeRequest = this.administrativeRequestService.findOneToEdit(administrativeRequestId);
			administrativeRequest.setAccepted(false);
			final AdministrativeRequest savedAR = this.administrativeRequestService.save(administrativeRequest);
			this.administrativeRequestService.flush();
			this.myMessageService.administrativeRequestNotification(savedAR, false);
			this.myMessageService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
