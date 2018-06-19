
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

import services.CollaborationRequestService;
import services.CurriculumService;
import services.EvaluationCriteriaService;
import services.OfferService;
import services.SubSectionEvaluationCriteriaService;
import services.SubSectionService;
import services.TenderResultService;
import services.TenderService;
import utilities.AbstractTest;
import domain.CollaborationRequest;
import domain.Curriculum;
import domain.EvaluationCriteria;
import domain.Offer;
import domain.SubSection;
import domain.SubSectionEvaluationCriteria;
import domain.Tender;
import domain.TenderResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UseCaseCommercial extends AbstractTest {

	@Autowired
	private CurriculumService					curriculumService;
	@Autowired
	private OfferService						offerService;
	@Autowired
	private SubSectionService					subsectionService;
	@Autowired
	private EvaluationCriteriaService			evaluationCriteriaService;
	@Autowired
	private SubSectionEvaluationCriteriaService	subSectionEvaluationCriteriaService;
	@Autowired
	private TenderResultService					tenderResultService;
	@Autowired
	private TenderService						tenderService;
	@Autowired
	private CollaborationRequestService			collaborationRequestService;


	/**
	 * Rol: Commercial
	 * 11.a. Crear ofertas asociadas a un concurso.(CU34)
	 */

	@Test
	public void CreateOfferTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "tender4", "IN_DEVELOPMENT", "19001", "05/07/2018 12:00", null
			}, {//Positive
				"commercial2", "tender5", "IN_DEVELOPMENT", "19001", "05/08/2018 12:00", null
			}, {// Negative: wrong roll
				"executive1", "tender4", "IN_DEVELOPMENT", "19001", "05/07/2018 12:00", IllegalArgumentException.class
			}, {// Negative: negative amount
				"commercial1", "tender5", "IN_DEVELOPMENT", "-19001", "05/08/2018 12:00", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateOffer((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // tenderId
				(String) testingData[i][2], // state
				Double.parseDouble((String) testingData[i][3]), // amount
				(String) testingData[i][4], //PresentationDate
				(Class<?>) testingData[i][5]);
	}
	protected void templateCreateOffer(final String principal, final Integer tenderId, final String state, final double amount, final String presentationDate, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final Offer offer = this.offerService.create(tenderId);

			offer.setAmount(amount);
			offer.setState(state);
			final DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Date d1 = format.parse(presentationDate);
			offer.setPresentationDate(d1);

			final Offer offerSaved = this.offerService.save(offer);
			this.offerService.flush();

			Assert.isTrue(this.offerService.findAll().contains(offerSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 11.b.1. Editar sus ofertas.(CU35)
	 */

	@Test
	public void EditOfferTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "offer1", "20000", null
			}, {// Negative: wrong roll
				"executive1", "offer1", "20000", IllegalArgumentException.class
			}, {// Negative: not her offer
				"commercial2", "offer1", "20000", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditOffer((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // offerId
				Double.parseDouble((String) testingData[i][2]), // amount
				(Class<?>) testingData[i][3]);
	}
	protected void templateEditOffer(final String principal, final Integer offerId, final double amount, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final Offer offer = this.offerService.findOne(offerId);
			offer.setAmount(amount);
			final Offer offerSaved = this.offerService.save(offer);
			this.offerService.flush();
			Assert.isTrue(this.offerService.findAll().contains(offerSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 11.c.1. Crear sub-apartados de sus ofertas.(CU37)
	 */

	@Test
	public void CreateSubSectionTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "offer1", "Title test", "ADMINISTRATIVE_ACREDITATION", "3", "Short Description", "Body test", "15/05/2018 00:00", null
			}, {// Negative: wrong roll
				"", "offer1", "Title test", "ADMINISTRATIVE_ACREDITATION", "3", "Short Description", "Body test", "15/05/2018 00:00", IllegalArgumentException.class
			}, {// Negative: not her offer
				"commercial2", "offer1", "Title test", "ADMINISTRATIVE_ACREDITATION", "3", "Short Description", "Body test", "15/05/2018 00:00", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSubSection((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // offerId
				(String) testingData[i][2], // title
				(String) testingData[i][3], // section
				(Integer) Integer.parseInt((String) testingData[i][4]), //subsectionOrder
				(String) testingData[i][5], //shortDescription
				(String) testingData[i][6], //body
				(String) testingData[i][7],	//lastReviewDate
				(Class<?>) testingData[i][8]);
	}
	protected void templateSubSection(final String principal, final Integer offerId, final String title, final String section, final Integer subsectionOrder, final String shortDescription, final String body, final String lastReviewDate,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final SubSection subsection = this.subsectionService.createByCommercialPropietary(offerId);

			subsection.setTitle(title);
			subsection.setSubsectionOrder(subsectionOrder);
			subsection.setShortDescription(shortDescription);
			subsection.setSection(section);
			subsection.setBody(body);

			final SubSection subsectionSaved = this.subsectionService.save(subsection);
			this.subsectionService.flush();

			Assert.isTrue(this.subsectionService.findAll().contains(subsectionSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 11.c.2. Editar sub-apartados de sus ofertas.(CU38)
	 */

	@Test
	public void EditSubSectionTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "subsection1", "Title test edit", "2", null
			}, {// Negative: wrong subsectionOrder
				"commercial1", "subsection1", "", "2", ConstraintViolationException.class
			}, {// Negative: not her subsection
				"commercial2", "subsection1", "Title test edit", "2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditSubSection((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // subsectionId
				(String) testingData[i][2], // title
				(Integer) Integer.parseInt((String) testingData[i][3]), //subsectionOrder
				(Class<?>) testingData[i][4]);
	}
	protected void templateEditSubSection(final String principal, final Integer subsectionId, final String title, final Integer subsectionOrder, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final SubSection subsection = this.subsectionService.findOne(subsectionId);

			subsection.setTitle(title);
			subsection.setSubsectionOrder(subsectionOrder);
			final SubSection subsectionSaved = this.subsectionService.save(subsection);
			this.subsectionService.flush();

			Assert.isTrue(this.subsectionService.findAll().contains(subsectionSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 11.c.3. Eliminar sub-apartados de sus ofertas.(CU39)
	 */

	@Test
	public void DeleteSubSectionTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "subsection1", null
			}, {// Positive as admin:
				"admin", "subsection1", IllegalArgumentException.class
			}, {// Negative: not her subsection
				"commercial2", "subsection1", IllegalArgumentException.class
			}, {// Negative: Bad roll
				"executive1", "subsection1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteSubSection((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // subsectionId
				(Class<?>) testingData[i][2]);
	}
	protected void templateDeleteSubSection(final String principal, final Integer subsectionId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final SubSection subsection = this.subsectionService.findOne(subsectionId);
			this.subsectionService.delete(subsection);
			this.subsectionService.flush();

			Assert.isTrue(!this.subsectionService.findAll().contains(subsection));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 24.a.2. Listar las ofertas con fecha de presentación pasada, presentes en el sistema
	 * y realizar búsquedas en dichas ofertas filtrando por palabra clave.(CU40)
	 */

	@Test
	public void SearchSubSectionTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "Creo que nos hemos presentado", "offer2", null
			}, {// Positive
				"commercial2", "Creo que nos hemos presentado", "offer2", null
			}, {// Negative: Busqueda de una oferta que no tiene fecha pasada
				"commercial1", "Servicio de comedor de la Escuela Técnica", "offer2", IllegalArgumentException.class
			}, {// Negative: Busqueda de una oferta que no tiene fecha pasada
				"commercial2", "Servicio de comedor de la Escuela Técnica", "offer2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSearchSubSection((String) testingData[i][0], // Username login
				(String) testingData[i][1], // keyword
				(Integer) super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);
	}
	protected void templateSearchSubSection(final String principal, final String keyword, final Integer offerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final Offer offer = this.offerService.findOne(offerId);
			final Collection<Offer> offers = this.offerService.findOfferByKeyWord(keyword);
			this.subsectionService.flush();
			Assert.isTrue(offers.contains(offer));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 26.a.1. Crear currículos asociados a los sub-apartados creados por él.(CU41)
	 */

	@Test
	public void CreateCurriculumTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "subsection4", "Jose", "Mena", "66453638T", "677588498", "mena@gmail.com", "15/05/1990 00:00", "La descripción del curriculum.", "21000", null
			}, {// Negative: wrong roll
				"executive1", "subsection4", "Jose", "Mena", "66453638T", "677588498", "", "15/05/1990 00:00", "La descripción del curriculum.", "21000", ConstraintViolationException.class
			}, {// Negative: bad phone number
				"", "subsection4", "Jose", "Mena", "66453638T", "6775884XX", "mena@gmail.com", "15/05/1990 00:00", "La descripción del curriculum.", "21000", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateCurriculum((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // subsection
				(String) testingData[i][2], // name
				(String) testingData[i][3], // surname
				(String) testingData[i][4], //identificationNumber
				(String) testingData[i][5], //phone
				(String) testingData[i][6], //email
				(String) testingData[i][7],	//dateOfBirth
				(String) testingData[i][8], //text
				Double.parseDouble((String) testingData[i][9]), //minSalaryExpectation
				(Class<?>) testingData[i][10]);
	}
	protected void templateCreateCurriculum(final String principal, final Integer subSectionId, final String name, final String surname, final String identificationNumber, final String phone, final String email, final String dateOfBirth,
		final String text, final Double minSalaryExpectation, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final Curriculum curriculum = this.curriculumService.create();
			final SubSection subsection = this.subsectionService.findOne(subSectionId);
			curriculum.setText(text);
			curriculum.setSurname(surname);
			curriculum.setSubSection(subsection);
			curriculum.setPhone(phone);
			curriculum.setName(name);
			curriculum.setMinSalaryExpectation(minSalaryExpectation);
			curriculum.setIdentificationNumber(identificationNumber);
			curriculum.setEmail(email);
			final DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Date d1 = format.parse(dateOfBirth);
			curriculum.setDateOfBirth(d1);

			final Curriculum curriculumSaved = this.curriculumService.save(curriculum);
			this.curriculumService.flush();

			Assert.isTrue(this.curriculumService.findAll().contains(curriculumSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	/**
	 * Rol: Commercial
	 * 26.a.2. Editar los currículos asociados a los sub-apartados creados por él.(CU42)
	 */

	@Test
	public void EditCurriculumTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "curriculum1", "Test edit", "Test Edit", "677564897", null
			}, {// Negative: bad phone
				"executive1", "curriculum1", "Test edit", "Test Edit", "677564XXX", IllegalArgumentException.class
			}, {// Negative: not her curriculum
				"commercial1", "curriculum4", "Test edit", "Test Edit", "677564897", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditCurriculum((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // subsection
				(String) testingData[i][2], // name
				(String) testingData[i][3], // surname
				(String) testingData[i][4], //phone
				(Class<?>) testingData[i][5]);
	}
	protected void templateEditCurriculum(final String principal, final Integer curriculumId, final String name, final String surname, final String phone, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

			curriculum.setSurname(surname);
			curriculum.setPhone(phone);
			curriculum.setName(name);

			final Curriculum curriculumSaved = this.curriculumService.save(curriculum);
			this.curriculumService.flush();

			Assert.isTrue(this.curriculumService.findAll().contains(curriculumSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	/**
	 * Rol: Commercial
	 * 26.a.3. Eliminar los currículos asociados a los sub-apartados creados por él.(CU43)
	 */

	@Test
	public void DeleteCurriculumTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "curriculum1", null
			}, {// Negative: bad roll
				"executive1", "curriculum1", IllegalArgumentException.class
			}, {// Negative: not her curriculum
				"commercial1", "curriculum4", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteCurriculum((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // subsection
				(Class<?>) testingData[i][2]);
	}
	protected void templateDeleteCurriculum(final String principal, final Integer curriculumId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

			this.curriculumService.delete(curriculum);
			this.curriculumService.flush();

			Assert.isTrue(!this.curriculumService.findAll().contains(curriculum));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 26.b.1. Crear asociaciones entre criterios de valoración y los sub-apartados de sus ofertas.(CU44)
	 */

	@Test
	public void CreateAssociationEvaluationCriteriaAndSubsectionTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "subsection5", "evaluationcriteria1", "Un comentario", null
			}, {// Negative: wrong roll
				"executive1", "subsection5", "evaluationcriteria1", "Un comentario", IllegalArgumentException.class
			}, {// Negative: not her subsection
				"commercial1", "subsection7", "evaluationcriteria1", "Un comentario", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAssociationEvaluationCriteriaAndSubsection((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // subsection
				(Integer) super.getEntityId((String) testingData[i][2]), // evaluationcriteria
				(String) testingData[i][3], // comment
				(Class<?>) testingData[i][4]);
	}
	protected void templateCreateAssociationEvaluationCriteriaAndSubsection(final String principal, final Integer subSectionId, final Integer evaluationcriteriaId, final String comments, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final EvaluationCriteria evaluationCriteria = this.evaluationCriteriaService.findOne(evaluationcriteriaId);
			final SubSectionEvaluationCriteria subSectionEvaluationCriteria = this.subSectionEvaluationCriteriaService.create(subSectionId);

			subSectionEvaluationCriteria.setComments(comments);
			subSectionEvaluationCriteria.setEvaluationCriteria(evaluationCriteria);

			final SubSectionEvaluationCriteria subSectionEvaluationCriteriaSaved = this.subSectionEvaluationCriteriaService.save(subSectionEvaluationCriteria);
			this.subSectionEvaluationCriteriaService.flush();
			Assert.isTrue(this.subSectionEvaluationCriteriaService.findAll().contains(subSectionEvaluationCriteriaSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 26.b.2. Editar las asociaciones entre criterios de valoración y los sub-apartados de sus ofertas.(CU45)
	 */

	@Test
	public void EditAssociationEvaluationCriteriaAndSubsectionTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "subsectionevaluationcriteria1", "Un comentario", null
			}, {// Negative: wrong roll
				"executive1", "subsectionevaluationcriteria1", "Un comentario", IllegalArgumentException.class
			}, {// Negative: comment null
				"commercial1", "subsectionevaluationcriteria4", null, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditAssociationEvaluationCriteriaAndSubsection((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // subsectionevaluationcriteria
				(String) testingData[i][2], // comment
				(Class<?>) testingData[i][3]);
	}
	protected void templateEditAssociationEvaluationCriteriaAndSubsection(final String principal, final Integer subsectionevaluationcriteriaId, final String comments, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final SubSectionEvaluationCriteria subSectionEvaluationCriteria = this.subSectionEvaluationCriteriaService.findOne(subsectionevaluationcriteriaId);

			subSectionEvaluationCriteria.setComments(comments);

			final SubSectionEvaluationCriteria subSectionEvaluationCriteriaSaved = this.subSectionEvaluationCriteriaService.save(subSectionEvaluationCriteria);
			this.subSectionEvaluationCriteriaService.flush();
			Assert.isTrue(this.subSectionEvaluationCriteriaService.findAll().contains(subSectionEvaluationCriteriaSaved));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 26.b.3. Eliminar las asociaciones entre criterios de valoración y los sub-apartados de sus ofertas.(CU46)
	 */

	@Test
	public void DeleteAssociationEvaluationCriteriaAndSubsectionTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "subsectionevaluationcriteria1", null
			}, {// Negative: wrong roll 
				"executive1", "subsectionevaluationcriteria1", IllegalArgumentException.class
			}, {// Negative: wrong commercial
				"commercial2", "subsectionevaluationcriteria4", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteAssociationEvaluationCriteriaAndSubsection((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), // subsectionevaluationcriteria
				(Class<?>) testingData[i][2]);
	}
	protected void templateDeleteAssociationEvaluationCriteriaAndSubsection(final String principal, final Integer subsectionevaluationcriteriaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final SubSectionEvaluationCriteria subSectionEvaluationCriteria = this.subSectionEvaluationCriteriaService.findOne(subsectionevaluationcriteriaId);

			this.subSectionEvaluationCriteriaService.delete(subSectionEvaluationCriteria);
			this.subSectionEvaluationCriteriaService.flush();
			Assert.isTrue(!this.subSectionEvaluationCriteriaService.findAll().contains(subSectionEvaluationCriteria));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 26.c.1. Visualizar los Resultados de concurso (CU47)
	 */

	@Test
	public void VisualizeResultsTendersTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateVisualizeResultsTenders((String) testingData[i][0], // Username login
				(Class<?>) testingData[i][1]);
	}
	protected void templateVisualizeResultsTenders(final String principal, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final Collection<TenderResult> tenderResult = this.tenderResultService.findAll();
			Assert.notNull(tenderResult);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 34.b. Realizar búsquedas de concursos (CU49)
	 */

	@Test
	public void SearchTendersTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial1", "proyecto", "tender1", null
			}, {// Negative bad keyword
				"commercial1", "Trollano", "tender1", IllegalArgumentException.class
			}, {// Negative bad tender
				"commercial1", "proyecto", "tender3", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSearchTenders((String) testingData[i][0], // Username login
				(String) testingData[i][1], (Integer) super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);
	}
	protected void templateSearchTenders(final String principal, final String keyWord, final Integer tenderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final Collection<Tender> tenders = this.tenderService.findTenderByKeyWord(keyWord);
			final Tender tender = this.tenderService.findOne(tenderId);
			Assert.isTrue(tenders.contains(tender));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/**
	 * Rol: Commercial
	 * 34.c. Aceptar o rechazar una solicitud de colaboración.
	 * En caso de rechazarla deberá de indicar un motivo.(CU50)
	 */

	@Test
	public void AcceptDenyTest() {

		final Object testingData[][] = {
			{// Positive
				"commercial2", "collaborationrequest2", false, null
			}, {// Positive
				"commercial2", "collaborationrequest1", true, null
			}, {// Negative bad commercial
				"commercial1", "collaborationrequest2", true, IllegalArgumentException.class
			}, {// Negative bad commercial
				"commercial1", "collaborationrequest1", false, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateAcceptDeny((String) testingData[i][0], // Username login
				(Integer) super.getEntityId((String) testingData[i][1]), (Boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void templateAcceptDeny(final String principal, final Integer collaborationRequestId, final Boolean response, final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(principal);
			final CollaborationRequest collaborationRequest = this.collaborationRequestService.findOneToEdit(collaborationRequestId);
			collaborationRequest.setAccepted(response);
			this.collaborationRequestService.save(collaborationRequest);
			this.collaborationRequestService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
