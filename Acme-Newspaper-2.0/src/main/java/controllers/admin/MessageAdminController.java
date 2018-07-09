
package controllers.admin;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.Priority;

@Controller
@RequestMapping("/message/admin")
public class MessageAdminController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FolderService	folderService;


	// Constructors --------------------------------------------------

	public MessageAdminController() {
		super();
	}

	// Notification --------------------------------------------------

	@RequestMapping(value = "/create-notification", method = RequestMethod.GET)
	public ModelAndView createNotification() {
		ModelAndView result = null;
		Message message = null;

		message = this.messageService.create();
		message.setRecipient(this.actorService.findByPrincipal());

		result = this.createNotificationModelAndView(message);

		return result;
	}

	// Notificate a  message -----------------------------------------

	@RequestMapping(value = "/notification", method = RequestMethod.POST, params = "notify")
	public ModelAndView notification(@ModelAttribute("messageNotification") final Message messagePruned, final BindingResult binding) {

		ModelAndView result;
		this.messageService.reconstruct(messagePruned, binding);

		if (binding.hasErrors())
			result = this.createNotificationModelAndView(messagePruned);
		else
			try {
				final Message message2 = this.messageService.reconstruct(messagePruned, binding);
				final Message saved = this.messageService.notify(message2);
				result = new ModelAndView("redirect:../list.do?folderId=" + saved.getFolder().getId());
			} catch (final Throwable oops) {
				String msg = "message.commit.error";
				result = this.createNotificationModelAndView(messagePruned, msg);
			}

		return result;
	}

	// Ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final Message message) {

		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {

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
		result.addObject("messageForm", message);
		result.addObject("actors", actors);
		result.addObject("priorities", priorities);
		result.addObject("folders", folders);
		result.addObject("message", messageCode);
		result.addObject("actionURI", "message/edit.do");

		return result;
	}

	protected ModelAndView createNotificationModelAndView(final Message message) {
		ModelAndView result;

		result = this.createNotificationModelAndView(message, null);

		return result;
	}

	protected ModelAndView createNotificationModelAndView(final Message message, final String messageCode) {

		ModelAndView result = null;
		Collection<Actor> actors = null;
		Collection<Priority> priorities = null;

		actors = this.actorService.findAll();
		actors.remove(this.actorService.findByPrincipal());

		priorities = new ArrayList<Priority>();
		priorities.add(Priority.LOW);
		priorities.add(Priority.NEUTRAL);
		priorities.add(Priority.HIGH);

		result = new ModelAndView("message/notify");
		result.addObject("messageNotification", message);
		result.addObject("priorities", priorities);
		result.addObject("message", messageCode);
		result.addObject("actionURI", "message/admin/notification.do");

		return result;
	}
}
