
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Message;
import domain.Priority;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 13.1: Exchange messages with other actors and manage them,
	 * which includes deleting and moving them from one folder to another
	 * folder.
	 */

	@Test
	public void driverCreateMessage() {
		final Object testingCreateMessageData[][] = {

			// Casos positivos
			{
				"user1", "user2", "Subject test", "Body test", Priority.HIGH, "folder10", null
			}, {
				"agent1", "user2", "Petition", "I request something", Priority.NEUTRAL, "folder40", null
			}, {
				"admin", "customer1", "Alert!", "You posted a taboo word.", Priority.HIGH, "folder5", null
			}, {
				"customer1", "customer2", "Hola", "Que tal todo?", Priority.LOW, "folder30", null
			}, {
				"agent2", "agent1", "Opini�n", "�C�mo fu�?", Priority.NEUTRAL, "folder45", null
			},
			// Casos negativos
			{
				"user1", null, "Subject test", "Body test", Priority.HIGH, "folder10", AssertionError.class
			}, /*
				 * Se debe de
				 * seleccionar
				 * un
				 * destinatario
				 */
			{
				"agent1", "user2", "", "Dej� el mensaje sin asunto", Priority.NEUTRAL, "folder40", ConstraintViolationException.class
			},/*
			 * No se puede dejar
			 * en blanco el
			 * campo asunto
			 */
			{
				"admin", "customer1", "Alert!", "", Priority.HIGH, "folder5", ConstraintViolationException.class
			},/*
			 * No se puede dejar
			 * en blanco el
			 * campo cuerpo
			 */
			{
				"customer1", "customer2", "Hola", "Que tal todo?", Priority.LOW, "folder1", IllegalArgumentException.class
			},/*
			 * No
			 * se
			 * puede
			 * mover
			 * un
			 * mensaje
			 * a
			 * una
			 * carpeta
			 * que
			 * no
			 * es
			 * del
			 * usuario
			 */
			{
				null, "agent1", "Opini�n", "�C�mo fu�?", Priority.NEUTRAL, "folder45", NullPointerException.class
			},/*
			 * Solo los
			 * usuarios
			 * autenticados
			 * pueden enviar
			 * mensajes
			 */
		};

		for (int i = 0; i < testingCreateMessageData.length; i++)
			this.templateCreateMessage((String) testingCreateMessageData[i][0], (String) testingCreateMessageData[i][1], (String) testingCreateMessageData[i][2], (String) testingCreateMessageData[i][3], (Priority) testingCreateMessageData[i][4],
				(String) testingCreateMessageData[i][5], (Class<?>) testingCreateMessageData[i][6]);

	}

	protected void templateCreateMessage(String authenticate, String recipientBeanName, String subject, String body, Priority priority, String folderBeanName, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int recipientId = this.getEntityId(recipientBeanName);
			int folderId = this.getEntityId(folderBeanName);
			super.authenticate(authenticate);
			Message message = this.messageService.create();
			message.setRecipient(this.actorService.findOne(recipientId));
			message.setSubject(subject);
			message.setBody(body);
			message.setPriority(priority);
			Message message2 = this.messageService.reconstruct(message, null);
			Message messageSend = this.messageService.save(message2);
			Assert.isTrue(this.folderService.findByFolderName(this.actorService.findByPrincipal().getUserAccount().getId(), "out box").getMessages().contains(messageSend));
			message2 = this.messageService.create();
			message2.setFolder(this.folderService.findOne(folderId));
			Message message3 = this.messageService.reconstruct(message2, null);
			Message messageMoved = this.messageService.save(message3);
			Assert.isTrue(this.folderService.findOne(folderId).getMessages().contains(messageMoved));
			this.messageService.delete(messageMoved);
			Assert.isTrue(this.folderService.findByFolderName(this.actorService.findByPrincipal().getUserAccount().getId(), "trash box").getMessages().contains(messageMoved));
			this.messageService.delete(messageMoved);
			Assert.isTrue(!this.messageService.findAll().contains(messageMoved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 14.1: Broadcast a message to the actors of the system.
	 */

	@Test
	public void driverCreateNotification() {
		final Object testingCreateNotificationData[][] = {

			// Casos positivos
			{
				"admin", "Subject test", "Body test", Priority.HIGH, null
			},
			// Casos negativos
			{
				"user1", "Alert!", "This is a notification", Priority.HIGH, IllegalArgumentException.class
			}, /*
				 * S�lo el administrador
				 * puede enviar
				 * notificaciones
				 */
			{
				"admin", "", "Dejo el asunto en blanco", Priority.HIGH, ConstraintViolationException.class
			}
		/*
		 * El campo asunto no
		 * puede ser vacio
		 */
		};

		for (int i = 0; i < testingCreateNotificationData.length; i++)
			this.templateCreateNotification((String) testingCreateNotificationData[i][0], (String) testingCreateNotificationData[i][1], (String) testingCreateNotificationData[i][2], (Priority) testingCreateNotificationData[i][3],
				(Class<?>) testingCreateNotificationData[i][4]);

	}

	protected void templateCreateNotification(String authenticate, String subject, String body, Priority priority, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int numberMessages = this.messageService.findAll().size();
			int numberRecipients = this.actorService.findAll().size() - 1;
			super.authenticate(authenticate);
			Message message = this.messageService.create();
			message.setSubject(subject);
			message.setBody(body);
			message.setPriority(priority);
			Message message2 = this.messageService.reconstruct(message, null);
			this.messageService.notify(message2);
			Assert.isTrue(this.messageService.findAll().size() == numberMessages + (numberRecipients * 2));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
