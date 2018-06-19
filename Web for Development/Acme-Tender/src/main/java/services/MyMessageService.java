
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MyMessageRepository;
import domain.Actor;
import domain.Administrative;
import domain.AdministrativeRequest;
import domain.Administrator;
import domain.CollaborationRequest;
import domain.Commercial;
import domain.Folder;
import domain.MyMessage;

@Service
@Transactional
public class MyMessageService {

	// Validator
	@Autowired
	private Validator				validator;

	// Managed repositories ------------------------------------------------
	@Autowired
	private MyMessageRepository		myMessageRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private FolderService			folderService;


	// CRUD Methods

	// Create

	public MyMessage create() {
		MyMessage res;
		Date moment;
		res = new MyMessage();
		final Actor actor = this.actorService.findByPrincipal();
		moment = new Date(System.currentTimeMillis() - 1);
		res.setSender(actor);
		res.setMoment(moment);
		return res;
	}

	// Save

	public MyMessage save(final MyMessage message) {
		// Compruebo que no sea nulo el mensaje que me pasan
		Assert.notNull(message);
		Assert.notNull(message.getRecipient());
		// Inicializo el momento en el que se envía
		Date moment;
		// Inicializo el Folder del destinatario
		Folder recipientFolder;
		// Inicializo el mensaje guardado
		MyMessage saved = null;
		// Si el mensaje que me pasan ya había estado guardado en la base de
		// datos (se quiere cambiar de Folder)
		// Si el mensaje se está guardando en la base de datos por
		// primera vez:
		// instancio el momento en que se envía como el momento actual
		moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);
		// guardo el mensaje en la base de datos
		saved = this.myMessageRepository.save(message);

		// Hago una copia del mensaje original, guardo la copia en la base
		// de datos y
		// lo añado a la colección de mensajes del sender
		final MyMessage copiedMessage = message;
		moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);
		final MyMessage copiedAndSavedMessage = this.myMessageRepository.save(copiedMessage);

		// Comprubeo si el mensaje contiene texto marcado como spam
		// si contiene spam
		if (this.administratorService.checkIsSpam(saved.getSubject(), saved.getBody()))
			// instancio el Folder del destinatario como el spambox
			recipientFolder = this.folderService.getSpamBoxFolderFromActorId(saved.getRecipient().getId());
		else
			// instancio el Folder del destinatario como inbox
			recipientFolder = this.folderService.getInBoxFolderFromActorId(saved.getRecipient().getId());
		// cojo los mensajes del Folder del destinatario
		final Collection<MyMessage> recipientFolderMessages = recipientFolder.getMymessages();
		// Añado el mensaje guardado
		recipientFolderMessages.add(saved);
		// Actualizo el conjunto de mensajes
		recipientFolder.setMymessages(recipientFolderMessages);
		// Cojo el sender
		final Actor sender = this.actorService.findByPrincipal();
		// Cojo el outbox del sender
		final Folder senderOutbox = this.folderService.getOutBoxFolderFromActorId(sender.getId());
		// Cojo los mensajes del outbox del sender
		final Collection<MyMessage> senderOutboxMessages = senderOutbox.getMymessages();

		// Añado el mensaje guardado a los mensajes del outbox del sender
		senderOutboxMessages.add(copiedAndSavedMessage);
		// Actualizo los mensajes del outbox del sender
		senderOutbox.setMymessages(senderOutboxMessages);
		// folderService.save(senderOutbox);

		return copiedAndSavedMessage;
	}

	// Save to move
	public void saveToMove(final MyMessage message, final Folder folder) {

		Assert.notNull(message);
		Assert.notNull(folder);

		final Folder currentFolder = this.folderService.getFolderFromMyMessageId(message.getId());
		final Collection<MyMessage> currentFolderMessages = currentFolder.getMymessages();
		currentFolderMessages.remove(message);
		currentFolder.setMymessages(currentFolderMessages);
		this.folderService.simpleSave(currentFolder);

		// this.messageRepository.delete(message.getId());

		// Message savedCopy = this.messageRepository.save(copy);

		// Message saved = this.messageRepository.save(message);
		final Collection<MyMessage> folderMessages = folder.getMymessages();
		folderMessages.add(message);
		folder.setMymessages(folderMessages);
		this.folderService.simpleSave(folder);

		// this.messageRepository.save(message);

	}

	// Delete
	public void delete(final MyMessage message) {
		// Compruebo que el mensaje que me pasan no sea nulo
		Assert.notNull(message);
		// Saco el actor que está logueado
		final Actor actor = this.actorService.findByPrincipal();
		// Compruebo que el mensaje que me pasan sea del actor que está logueado
		// String type = actorService.getType(actor.getUserAccount());
		//
		// if (type.equals("USER")) {
		// actor = (User) actor;
		// } else if (type.equals("AUDITOR")) {
		// actor = (Auditor) actor;
		// } else if (type.equals("RANGER")) {
		// actor = (Ranger) actor;
		// } else if (type.equals("MANAGER")) {
		// actor = (Manager) actor;
		// } else if (type.equals("SPONSOR")) {
		// actor = (Sponsor) actor;
		// }

		this.checkPrincipal(message, actor);
		// cojo el trashbox del actor logueado
		final Folder trashbox = this.folderService.getTrashBoxFolderFromActorId(actor.getId());
		// Compruebo que el trashbox del actor logueado no sea nulo
		Assert.notNull(trashbox);
		// si el mensaje que me pasan está en el trashbox del actor logueado:
		if (trashbox.getMymessages().contains(message)) {
			// saco la collection de mensajes del trashbox del actor logueado
			final Collection<MyMessage> trashboxMessages = trashbox.getMymessages();
			// borro el mensaje que me pasan de la collection de mensajes del
			// trashbox
			trashboxMessages.remove(message);
			// actualizo la collection de mensajes del trashbox
			trashbox.setMymessages(trashboxMessages);
			// borro el mensaje del sistema
			this.myMessageRepository.delete(message);

		} else {// si el mensaje que se quiere borrar no está en el trashbox:

			// Borro el mensaje del folder en el que estaba
			final Folder messageFolder = this.folderService.getFolderFromMyMessageId(message.getId());
			Assert.notNull(messageFolder);
			final Collection<MyMessage> messages = messageFolder.getMymessages();
			messages.remove(message);
			messageFolder.setMymessages(messages);

			// Meto en el trashbox el mensaje
			final Collection<MyMessage> trashboxMessages = trashbox.getMymessages();
			trashboxMessages.add(message);
			trashbox.setMymessages(trashboxMessages);

		}
	}

	// Delete collection
	public void delete(final Iterable<MyMessage> messages) {
		Assert.notNull(messages);
		this.myMessageRepository.delete(messages);
	}

	// FindOne
	public MyMessage findOne(final int messageId) {
		final MyMessage message = this.myMessageRepository.findOne(messageId);
		Assert.notNull(message);
		Actor principal;
		principal = this.actorService.findByPrincipal();
		this.checkPrincipal(message, principal);
		return message;

	}

	// Other methods

	public void checkPrincipal(final MyMessage message, final Actor actor) {
		final Collection<MyMessage> messages = this.myMessageRepository.messagesFromActorId(actor.getId());
		Assert.isTrue(messages.contains(message));
	}

	public void broadcastMessage(final MyMessage message) {
		Assert.notNull(message);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrator);

		for (final Actor recipient : this.actorService.findAll())
			if (!recipient.equals(principal)) {
				final Folder notificationbox = this.folderService.getNotificationBoxFolderFromActorId(recipient.getId());
				final MyMessage copy = new MyMessage(message);
				copy.setSubject("[BROADCAST] " + copy.getSubject());
				copy.setBroadcast(true);
				final MyMessage saved = this.myMessageRepository.save(copy);
				notificationbox.getMymessages().add(saved);

			} else {

				final Folder outbox = this.folderService.getOutBoxFolderFromActorId(principal.getId());
				final MyMessage copy = new MyMessage(message);
				copy.setSubject("[BROADCAST] " + copy.getSubject());
				copy.setBroadcast(true);
				final MyMessage saved = this.myMessageRepository.save(copy);
				outbox.getMymessages().add(saved);
			}

	}

	public MyMessage reconstruct(final MyMessage m, final BindingResult binding) {

		final Administrator sender = (Administrator) m.getSender();
		final Administrator recipient = sender;
		m.setRecipient(recipient);
		this.validator.validate(m, binding);

		return m;
	}

	public void collaborationRequestNotification(final CollaborationRequest collaborationRequest, final boolean accept) {

		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Commercial);

		Actor recipient;
		recipient = collaborationRequest.getOffer().getCommercial();
		Assert.notNull(recipient);

		final MyMessage notification = this.create();
		notification.setRecipient(recipient);
		notification.setPriority("NEUTRAL");
		if (accept == true) {

			notification.setSubject("Solicitud de colaboración aceptada");
			notification.setBody(principal.getName() + " " + principal.getSurname() + " ha aceptado su " + "solicitud de colaboración en la subsección con título " + "'" + collaborationRequest.getSubSectionTitle() + "'" + " perteneciente a la sección "
				+ collaborationRequest.getSection());

		} else {

			notification.setSubject("Solicitud de colaboración rechazada");
			notification.setBody(principal.getName() + " " + principal.getSurname() + " ha rechazado su " + "solicitud de colaboración en la subsección con título " + "'" + collaborationRequest.getSubSectionTitle() + "'" + " perteneciente a la sección "
				+ collaborationRequest.getSection());

		}

		final Date moment = new Date(System.currentTimeMillis() - 1);
		notification.setMoment(moment);

		final MyMessage saved = this.myMessageRepository.save(notification);

		final Folder notificationbox = this.folderService.getNotificationBoxFolderFromActorId(recipient.getId());
		final Collection<MyMessage> notifications = notificationbox.getMymessages();
		notifications.add(saved);
		notificationbox.setMymessages(notifications);

	}

	public void administrativeRequestNotification(final AdministrativeRequest administrativeRequest, final boolean accept) {

		Actor principal;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Administrative);

		Actor recipient;
		recipient = administrativeRequest.getOffer().getCommercial();
		Assert.notNull(recipient);

		final MyMessage notification = this.create();
		notification.setRecipient(recipient);
		notification.setPriority("NEUTRAL");
		if (accept == true) {

			notification.setSubject("Solicitud administrativa aceptada");
			notification.setBody(principal.getName() + " " + principal.getSurname() + " ha aceptado su " + "solicitud administrativa en la subsección con título " + "'" + administrativeRequest.getSubSectionTitle() + "'");

		} else {
			notification.setSubject("Solicitud administrativa rechazada");
			notification.setBody(principal.getName() + " " + principal.getSurname() + " ha rechazado su " + "solicitud administrativa en la subsección con título " + "'" + administrativeRequest.getSubSectionTitle() + "'");

		}

		final Date moment = new Date(System.currentTimeMillis() - 1);
		notification.setMoment(moment);

		final MyMessage saved = this.myMessageRepository.save(notification);

		final Folder notificationbox = this.folderService.getNotificationBoxFolderFromActorId(recipient.getId());
		final Collection<MyMessage> notifications = notificationbox.getMymessages();
		notifications.add(saved);
		notificationbox.setMymessages(notifications);

	}

	public void flush() {
		this.myMessageRepository.flush();

	}

}
