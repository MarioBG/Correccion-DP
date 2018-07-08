
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import services.UserService;
import domain.Rendezvous;
import domain.User;
import forms.UserForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Constructors ---------------------------------------------------------

	public UserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;

		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("user", users);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	//	@RequestMapping(value = "creator/list", method = RequestMethod.GET)
	//	public ModelAndView listCreator(@RequestParam final int organiserId) {
	//		ModelAndView result;
	//		User organizer;
	//
	//		organizer = this.userService.findOne(organiserId);
	//
	//		result = new ModelAndView("user/list");
	//		result.addObject("user", organizer);
	//		result.addObject("requestURI", "user/creator/list.do");
	//
	//		return result;
	//	}

	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {
		ModelAndView result;
		User user;

		user = this.userService.findOne(userId);

		result = new ModelAndView("user/display");
		result.addObject("user", user);

		return result;
	}

	// Registering ----------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		UserForm userForm;

		userForm = new UserForm();
		res = this.createEditModelAndView(userForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final UserForm userForm, final BindingResult binding) {
		ModelAndView res;
		User user;

		if (binding.hasErrors())
			res = this.createEditModelAndView(userForm, "user.params.error");
		else if (!userForm.getRepeatPassword().equals(userForm.getPassword()))
			res = this.createEditModelAndView(userForm, "user.commit.errorPassword");
		else if (userForm.getTermsAndConditions() == false)
			res = this.createEditModelAndView(userForm, "user.params.errorTerms");
		else
			try {
				user = this.userService.reconstruct(userForm, binding);
				this.userService.save(user);
				res = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(userForm, "user.commit.error");
			}

		return res;
	}

	//	@RequestMapping(value = "/rendezvousCreator/list", method = RequestMethod.GET)
	//	public ModelAndView listRendezvousCreator(@RequestParam final int rendezvousId) {
	//		ModelAndView result;
	//		User organizer;
	//
	//		organizer = this.userService.findOrganiserByRendezvousId(rendezvousId);
	//
	//		result = new ModelAndView("user/list");
	//		result.addObject("user", organizer);
	//		result.addObject("requestURI", "user/rendezvousCreator/list.do");
	//
	//		return result;
	//	}

	@RequestMapping(value = "/listRendezvousAttends", method = RequestMethod.GET)
	public ModelAndView listRendezvousAttendant(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Collection<User> attends = new ArrayList<User>();

		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		attends = this.userService.findAttendsByRendezvousId(rendezvousId);

		result = new ModelAndView("user/list");
		result.addObject("user", attends);
		result.addObject("rendezvous", rendezvous);
		result.addObject("requestURI", "user/listRendezvousAttends.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm) {
		ModelAndView result;

		result = this.createEditModelAndView(userForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("user/register");
		result.addObject("userForm", userForm);
		result.addObject("message", message);

		return result;
	}
}
