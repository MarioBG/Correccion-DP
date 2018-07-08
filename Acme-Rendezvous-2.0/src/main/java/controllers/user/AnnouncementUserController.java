
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Rendezvous;
import forms.AnnouncementForm;

@Controller
@RequestMapping("/announcement/user")
public class AnnouncementUserController extends AbstractController {

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Constructors -----------------------------------------------------------

	public AnnouncementUserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Announcement> announcements;

		announcements = this.announcementService.findAnnouncementsByAttendants();

		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("requestURI", "announcement/user/list.do");

		return result;
	}

	// Create --------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int rendezvousId) {
		ModelAndView res;
		Announcement announcement;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(rendezvousId);

		announcement = this.announcementService.create(rendezvous);

		AnnouncementForm announcementForm;
		announcementForm = this.announcementService.construct(announcement);

		res = this.createEditModelAndView(announcementForm);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AnnouncementForm announcementForm, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(announcementForm, "announcement.params.error");
		else
			try {
				final Announcement announcement = this.announcementService.reconstruct(announcementForm, binding);
				this.announcementService.save(announcement);

				final Integer id = announcement.getRendezvous().getId();
				res = new ModelAndView("redirect:/announcement/list.do?rendezvousId=" + id);
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(announcementForm, "announcement.commit.error");
			}

		return res;
	}

	//		protected ModelAndView createEditModelAndView(final Announcement announcement) {
	//			ModelAndView res;
	//			
	//			res = this.createEditModelAndView(announcement,null);
	//			
	//			return res;
	//		}
	//
	//		protected ModelAndView createEditModelAndView(final Announcement announcement,
	//				final String message) {
	//			ModelAndView res;
	//			res = new ModelAndView("announcement/edit");
	//			
	//			res.addObject("announcement",announcement);
	//			res.addObject("message",message);
	//			res.addObject("requestURI","announcement/user/edit.do");
	//			
	//			return res;
	//		}

	protected ModelAndView createEditModelAndView(final AnnouncementForm announcementForm) {
		ModelAndView res;

		res = this.createEditModelAndView(announcementForm, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final AnnouncementForm announcementForm, final String message) {
		ModelAndView res;

		if (announcementForm.getId() == 0)
			res = new ModelAndView("announcement/create");
		else
			res = new ModelAndView("announcement/edit");

		res.addObject("announcementForm", announcementForm);
		res.addObject("message", message);
		res.addObject("requestURI", "announcement/user/edit.do");

		return res;
	}

}
