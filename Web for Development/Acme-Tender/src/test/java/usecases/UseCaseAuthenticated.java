
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

import services.ActorService;
import services.FolderService;
import services.MyMessageService;
import services.OfferService;
import services.TenderService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;
import domain.MyMessage;
import domain.Offer;
import domain.Tender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UseCaseAuthenticated extends AbstractTest {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private TenderService		tenderService;
	@Autowired
	private MyMessageService	myMessageService;
	@Autowired
	private FolderService		folderService;
	@Autowired
	private OfferService		offerService;


	/*
	 * Caso de uso:
	 * Auth-> Ver la información de contacto de cualquier otro usuario.(CU02)
	 */
	@Test
	public void contactInformationTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "administrative1", null
			}, {//Positive
				"administrative1", "executive1", null
			}, {// Negative: without name
				"executive1", "papayarroz", NumberFormatException.class
			}, {// Negative: with name null
				"", "administrative1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateContactInformationTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateContactInformationTest(final Integer i, final String principal, final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final Collection<Actor> actors = this.actorService.findAll();

			for (final Actor actor : actors)
				if (actor.getId() == this.getEntityId(user)) {
					final Actor actorBD = this.actorService.findOne(actor.getId());
					Assert.notNull(actorBD);
				}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth-> Listar los concursos existentes en el sistema.(CU03)
	 */
	@Test
	public void tenderListTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", null
			}, {//Positive
				"administrative1", null
			}, {//Positive
				"commercial1", null
			}, {// Negative: without user
				"", IllegalArgumentException.class
			}, {// Negative: with user not exist
				"user55", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateTenderListTest(i, (String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void templateTenderListTest(final Integer i, final String principal, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final Collection<Tender> tenders = this.tenderService.findAll();

			Assert.notNull(tenders);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth-> Realizar búsquedas filtrando por palabra clave.(CU04)
	 */
	@Test
	public void searchTenderTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "", null
			}, {//Positive
				"administrative1", "web", null
			}, {//Positive
				"commercial1", "andaluz", null
			}, {// Negative: without user
				"", "web", IllegalArgumentException.class
			}, {// Negative: with user not exist
				"user55", "web", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSearchTenderTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateSearchTenderTest(final Integer i, final String principal, final String word, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final Collection<Tender> tenders = this.tenderService.findTenderByKeyWord(word);

			Assert.notNull(tenders);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth-> Intercambiar mensajes con otros actores.(CU05)
	 */
	@Test
	public void sendMessageTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "administrative1", "subject1", "body1", "HIGH", null
			}, {//Positive
				"administrative1", "executive1", "subject2", "body2", "HIGH", null
			}, {//Positive
				"commercial1", "executive1", "subject3", "body3", "HIGH", null
			}, {// Negative: without sender
				"", "executive1", "subject3", "body3", "HIGH", IllegalArgumentException.class
			}, {// Negative: with sender not exist
				"user55", "executive1", "subject3", "body3", "HIGH", IllegalArgumentException.class
			}, {// Negative: without subject
				"administrative1", "executive1", "", "body3", "HIGH", ConstraintViolationException.class
			}, {// Negative: without recipient
				"administrative1", "", "subject3", "body3", "HIGH", NumberFormatException.class
			}, {// Negative: without body
				"administrative1", "executive1", "subject3", "", "HIGH", ConstraintViolationException.class
			}, {// Negative: without priority
				"administrative1", "executive1", "subject3", "body3", "", ConstraintViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSendMessageTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void templateSendMessageTest(final Integer i, final String principal, final String recipient, final String subject, final String body, final String priority, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final MyMessage message = this.myMessageService.create();
			message.setBody(body);
			message.setPriority(priority);
			message.setSubject(subject);

			final Actor actor = this.actorService.findOne(super.getEntityId(recipient));
			message.setRecipient(actor);

			this.myMessageService.save(message);
			this.myMessageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth-> Borrar mensaje.(CU06)
	 */
	@Test
	public void deleteMessageTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "message1", null
			}, {//Positive
				"administrative1", "message2", null
			}, {//Positive
				"commercial1", "message3", null
			}, {// Negative: without sender
				"", "message4", IllegalArgumentException.class
			}, {// Negative: with sender not exist
				"commercial1", "message2", IllegalArgumentException.class
			}, {// Negative: without subject
				"administrative1", "", NumberFormatException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteMessageTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateDeleteMessageTest(final Integer i, final String principal, final String messageId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final MyMessage message = this.myMessageService.findOne(super.getEntityId(messageId));

			this.myMessageService.delete(message);
			this.myMessageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth-> Mover mensaje de carpeta.(CU07)
	 */
	@Test
	public void moveMessageTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "message1", "notificationbox6", null
			}, {//Positive
				"administrative1", "message2", "notificationbox4", null
			}, {//Positive
				"commercial1", "message3", "notificationbox2", null
			}, {// Negative: without sender
				"", "message4", "notificationbox1", IllegalArgumentException.class
			}, {// Negative: with folder not exist
				"admministrative1", "message2", "notificationbox255", IllegalArgumentException.class
			}, {// Negative: without folder
				"administrative1", "message2", "", NumberFormatException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateMoveMessageTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void templateMoveMessageTest(final Integer i, final String principal, final String messageId, final String folderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final MyMessage message = this.myMessageService.findOne(super.getEntityId(messageId));
			final Folder folder = this.folderService.findOne(super.getEntityId(folderId));

			this.myMessageService.saveToMove(message, folder);
			this.myMessageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth-> Crear folder.(CU08)
	 */
	@Test
	public void createFolderTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "nameFolder1", "", null
			}, {//Positive
				"administrative1", "nameFolder2", "notificationbox4", null
			}, {// Negative: without principal
				"", "nameFolder3", "", IllegalArgumentException.class
			}, {// Negative: with parent folder not exist
				"admministrative1", "nameFolder3", "notificationbox255", IllegalArgumentException.class
			}, {// Negative: without name Folder
				"administrative1", "", "notificationbox4", ConstraintViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateFolderTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void templateCreateFolderTest(final Integer i, final String principal, final String nameFolder, final String folderParentId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final Folder folder = this.folderService.create();

			folder.setName(nameFolder);
			if (folderParentId != "") {
				final Folder parentFolder = this.folderService.findOne(super.getEntityId(folderParentId));
				folder.setParentFolder(parentFolder);
			}

			this.folderService.save(folder);
			this.folderService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth-> Borrar folder.(CU09)
	 */
	@Test
	public void deleteFolderTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "custombox6", null
			}, {//Positive
				"administrative1", "custombox4", null
			}, {// Negative: without principal
				"", "custombox6", IllegalArgumentException.class
			}, {// Negative: with parent folder not exist
				"admministrative1", "custombox4", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteFolderTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateDeleteFolderTest(final Integer i, final String principal, final String folderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final Folder folder = this.folderService.findOne(super.getEntityId(folderId));

			this.folderService.delete(folder);
			this.folderService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth-> Editar folder.(CU10)
	 */
	@Test
	public void editFolderTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "custombox6", "nombreNuevo", null
			}, {//Positive
				"administrative1", "custombox4", "nombreNuevo", null
			}, {// Negative: without principal
				"", "custombox6", "nombreNuevo", IllegalArgumentException.class
			}, {// Negative: without folder name
				"admministrative1", "custombox4", "", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditFolderTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void templateEditFolderTest(final Integer i, final String principal, final String folderId, final String nameFolder, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final Folder folder = this.folderService.findOne(super.getEntityId(folderId));

			folder.setName(nameFolder);

			this.folderService.save(folder);
			this.folderService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth-> Mover folder.(CU10)
	 */
	@Test
	public void moveFolderTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "custombox6", "inbox6", null
			}, {//Positive
				"administrative1", "custombox4", "inbox4", null
			}, {// Negative: without principal
				"", "custombox6", "inbox6", IllegalArgumentException.class
			}, {// Negative: without folder name
				"admministrative1", "custombox4", "", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateMoveFolderTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void templateMoveFolderTest(final Integer i, final String principal, final String folderId, final String parentFolderId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final Folder folder = this.folderService.findOne(super.getEntityId(folderId));
			final Folder targetFolder = this.folderService.findOne(super.getEntityId(parentFolderId));

			this.folderService.saveToMove(folder, targetFolder);
			this.folderService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * 24. Un usuario autenticado podrá:
	 * a. Listar las ofertas publicadas (en estados PRESENTADA, GANADA, PERDIDA e IMPUGNADA)
	 * y realizar búsquedas en dichas ofertas filtrando por palabra clave.
	 */
	/* Autenticado --> buscar oferta por palabra clave */
	@Test
	public void searchOfferTest() {
		final Object testingData[][] = {
			//Positivo
			{
				"commercial1", "sistema", null
			},

			//Negativo(no autenticado)
			{
				"", "canguro", IllegalArgumentException.class
			},
			//Negativo(usuario que no existe)
			{
				"user25", "", IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSearchOffer((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void templateSearchOffer(final String principal, final String keyWord, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.authenticate(principal);
			this.offerService.findAllPublished();
			final Collection<Offer> offers = this.offerService.findOfferByKeyWord(keyWord);
			Assert.notNull(offers);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
