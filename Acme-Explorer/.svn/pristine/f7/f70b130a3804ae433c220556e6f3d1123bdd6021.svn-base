
package services;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;
import domain.Manager;
import domain.Message;
import domain.Priority;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private FolderService	folderService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {

		final Message message;
		final Actor sender;
		Folder folder;

		super.authenticate("manager1");

		message = this.messageService.create();
		sender = this.actorService.findByPrincipal();
		folder = this.folderService.findByFolderName(sender.getUserAccount().getId(), "Out Box");

		Assert.notNull(message.getMoment());
		Assert.isTrue(message.getSender().equals(sender));
		Assert.isTrue(message.getFolder().equals(folder));

		super.authenticate(null);
	}

	@Test
	public void testSave() {

		Actor sender;
		Collection<Actor> actors;
		int numMessagesBefore, numMessagesAfter;
		Message message, saved;

		super.authenticate("manager1");

		sender = this.actorService.findByPrincipal();
		actors = this.actorService.findAll();
		actors.remove(sender);
		final Iterator<Actor> actorsIterator = actors.iterator();

		message = this.messageService.create();
		message.setSubject("Test");
		message.setBody("Test");
		message.setPriority(Priority.HIGH);
		message.setRecipient(actorsIterator.next());

		numMessagesBefore = this.messageService.findAll().size();
		saved = this.messageService.save(message);
		numMessagesAfter = this.messageService.findAll().size();

		Assert.notNull(saved.getSubject());
		Assert.notNull(saved.getBody());
		Assert.notNull(saved.getPriority());
		Assert.isTrue(!saved.getMoment().equals(message.getMoment()));
		Assert.notNull(this.messageService.findOne(saved.getId()));
		Assert.isTrue(numMessagesAfter == numMessagesBefore + 2);

		super.authenticate(null);
	}

	@Test
	public void testSendSpamMessage() {
		Actor sender, reciever;
		Message messageToSend, saved;
		Folder spam;
		Manager manager;

		super.authenticate("manager1");

		sender = this.actorService.findByPrincipal();
		reciever = this.actorService.findAll().iterator().next();
		messageToSend = this.messageService.create();
		messageToSend.setSubject("Trip information ");
		messageToSend.setBody("This is the body of the SeX message to explorer1 from manager1.");
		messageToSend.setRecipient(reciever);
		messageToSend.setPriority(Priority.HIGH);
		saved = this.messageService.save(messageToSend);

		spam = this.folderService.findByFolderName(reciever.getUserAccount().getId(), "Spam Box");
		Assert.isTrue(spam.getMessages().contains(saved));

		manager = this.managerService.findByUserAccountId(sender.getUserAccount().getId());
		Assert.isTrue(manager.getSuspicious());

		super.authenticate(null);

	}

	@Test
	public void testSendNotification() {
		Actor reciever;
		Message messageToSend;
		Folder notificationBox;
		Integer notificationBoxSizeBefore;
		Integer notificationBoxSizeAfter;

		super.authenticate("admin1");

		reciever = this.actorService.findAll().iterator().next();
		messageToSend = this.messageService.create();
		messageToSend.setSubject("Notification to manager1 ");
		messageToSend.setBody("This is the body of the notification message to manager1 from admin1.");
		messageToSend.setRecipient(reciever);
		messageToSend.setPriority(Priority.HIGH);

		notificationBox = this.folderService.findByFolderName(reciever.getUserAccount().getId(), "Notification Box");

		notificationBoxSizeBefore = notificationBox.getMessages().size();

		this.messageService.notify(messageToSend);
		notificationBoxSizeAfter = notificationBox.getMessages().size();

		Assert.isTrue(notificationBoxSizeAfter > notificationBoxSizeBefore);

		super.unauthenticate();
	}

	@Test
	public void testMoveMessage() {

		super.authenticate("admin1");

		final Actor actor = this.actorService.findByPrincipal();

		final Folder outbox = this.folderService.findByFolderName(actor.getUserAccount().getId(), "Out Box");
		final Collection<Message> messages = outbox.getMessages();
		final Message messageToMove = messages.iterator().next();
		final Folder trashBox = this.folderService.findByFolderName(actor.getUserAccount().getId(), "Trash Box");

		this.messageService.moveMessageToFolder(messageToMove, trashBox);

		Assert.isTrue(!outbox.getMessages().contains(messageToMove));
		Assert.isTrue(trashBox.getMessages().contains(messageToMove));

		super.unauthenticate();
	}
}
