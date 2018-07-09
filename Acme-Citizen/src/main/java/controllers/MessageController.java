
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.Priority;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FolderService	folderService;


	// Constructors --------------------------------------------------

	public MessageController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId) {

		ModelAndView result;
		Collection<Message> messages;
		Folder folder;

		messages = this.messageService.findByFolderId(folderId);
		folder = this.folderService.findOne(folderId);

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("folder", folder);

		return result;
	}

	// Creation ------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) Integer recipientId) {

		ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createEditModelAndView(message);

		if (recipientId != null) {
			Actor a = this.actorService.findOne(recipientId);
			result.addObject("recipient", a);
		}

		return result;
	}

	// Edition -------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId) {

		ModelAndView result;
		Message message;
		Actor actor;

		message = this.messageService.findOneToEdit(messageId);
		actor = this.actorService.findByPrincipal();

		result = new ModelAndView("message/edit2");
		result.addObject("patata", message);
		result.addObject("recipientName", message.getRecipient().getName());
		result.addObject("senderName", message.getSender().getName());
		result.addObject("folders", actor.getFolders());
		result.addObject("actionURI", "message/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("patata") final Message message0, final BindingResult binding) {

		ModelAndView result;
		this.messageService.reconstruct(message0, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(message0);
		else
			try {
				final Message message = this.messageService.reconstruct(message0, binding);
				final Message saved = this.messageService.save(message);
				result = new ModelAndView("redirect:list.do?folderId=" + saved.getFolder().getId());
			} catch (final Throwable oops) {
				String msg = oops.getMessage();
				if (msg == null)
					msg = "message.commit.error";
				result = this.createEditModelAndView(message0, msg);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Message message0, final BindingResult binding) {

		ModelAndView result;
		int folderid;

		try {
			Message message = this.messageService.reconstruct(message0, binding);
			folderid = message.getFolder().getId();
			this.messageService.delete(message);
			result = new ModelAndView("redirect:list.do?folderId=" + folderid);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(message0, "message.commit.error");
		}

		return result;
	}

	// Display -------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {

		ModelAndView result;
		Message message;
		Actor sender, recipient;

		message = this.messageService.findOneToEdit(messageId);
		sender = this.actorService.findSenderByMessageId(messageId);
		recipient = this.actorService.findRecipientByMessageId(messageId);

		result = new ModelAndView("message/display");
		result.addObject("messageDisplay", message);
		result.addObject("sender", sender);
		result.addObject("recipient", recipient);

		return result;
	}

	// Ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final Message message) {

		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message0, final String messageCode) {

		ModelAndView result;
		Collection<Actor> actors;
		final Collection<Priority> priorities;
		Collection<Folder> folders;

		actors = this.actorService.findAll();
		actors.remove(this.actorService.findByPrincipal());

		priorities = new ArrayList<Priority>();
		priorities.add(Priority.LOW);
		priorities.add(Priority.NEUTRAL);
		priorities.add(Priority.HIGH);

		folders = this.folderService.findByPrincipal();

		result = new ModelAndView("message/edit");
		result.addObject("patata", message0);
		if (message0.getId() != 0) {
			Message message = this.messageService.findOne(message0.getId());
			result.addObject("recipientName", message.getRecipient().getName());
			result.addObject("senderName", message.getSender().getName());
		}
		result.addObject("actors", actors);
		result.addObject("priorities", priorities);
		result.addObject("folders", folders);
		result.addObject("message", messageCode);
		result.addObject("actionURI", "message/edit.do");

		return result;
	}
}
