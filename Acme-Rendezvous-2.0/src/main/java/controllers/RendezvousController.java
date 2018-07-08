
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RendezvousService;
import services.UserService;
import domain.Actor;
import domain.Admin;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/rendezvous")
public class RendezvousController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserService			userService;


	// Constructors --------------------------------------------------

	public RendezvousController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer rendezvousId, @RequestParam(required = false) final Integer categoryId) {

		Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();

		final Actor principal = this.actorService.findByPrincipal();

		if (rendezvousId == null && principal == null)
			rendezvouses = this.rendezvousService.findFutureMomentAndNotAdult();
		else if (rendezvousId == null && this.actorService.isAdult(principal.getBirth()))
			rendezvouses = this.rendezvousService.findFutureMoment();
		else if (rendezvousId == null && !this.actorService.isAdult(principal.getBirth()))
			rendezvouses = this.rendezvousService.findFutureMomentAndNotAdult();
		else if (rendezvousId != null && principal == null)
			rendezvouses = this.rendezvousService.linkedRendezvousesFutureMomentAndNotAdultByRendezvousId(rendezvousId);
		else if (rendezvousId != null && this.actorService.isAdult(principal.getBirth()))
			rendezvouses = this.rendezvousService.linkedRendezvousesFutureMomentByRendezvousId(rendezvousId);
		else if (rendezvousId != null && !this.actorService.isAdult(principal.getBirth()))
			rendezvouses = this.rendezvousService.linkedRendezvousesFutureMomentAndNotAdultByRendezvousId(rendezvousId);

		if (categoryId != null) {
			rendezvouses = this.rendezvousService.rendezvousGroupedByCategory(categoryId);
			final Collection<Rendezvous> rendezvousNotAdult = this.rendezvousService.findFutureMomentAndNotAdult();
			if (principal == null || !(this.actorService.isAdult(principal.getBirth())))
				for (int i = 0; i <= rendezvouses.size(); i++)
					if (!(rendezvousNotAdult.contains(rendezvouses.toArray()[i])))
						rendezvouses.remove(rendezvouses.toArray()[i]);
		}

		final ModelAndView result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("rendezvousSource", null);
		result.addObject("requestURI", "rendezvous/list.do");

		return result;
	}

	@RequestMapping(value = "/list-organised", method = RequestMethod.GET)
	public ModelAndView organised(@RequestParam final int userId) {

		Collection<Rendezvous> rendezvouses;

		final User principal = this.userService.findByPrincipal();

		if (principal == null || !this.actorService.isAdult(principal.getBirth()))
			rendezvouses = this.rendezvousService.findByOrganiserIdNotAdult(userId);
		else
			rendezvouses = this.rendezvousService.findByOrganiserId(userId);

		final ModelAndView result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("rendezvousSource", null);
		result.addObject("requestURI", "rendezvous/list-organised.do");

		return result;
	}

	@RequestMapping(value = "/list-rspv", method = RequestMethod.GET)
	public ModelAndView rspv(@RequestParam final int userId) {

		Collection<Rendezvous> rendezvouses;

		final User principal = this.userService.findByPrincipal();

		if (principal == null || !this.actorService.isAdult(principal.getBirth()))
			rendezvouses = this.rendezvousService.findByAttendantIdNotAdult(userId);
		else
			rendezvouses = this.rendezvousService.findByAttendantId(userId);

		final ModelAndView result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("rendezvousSource", null);
		result.addObject("requestURI", "rendezvous/list-rspv.do");

		return result;
	}

	//	@RequestMapping(value = "/listByCategory", method = RequestMethod.GET)
	//	public ModelAndView list(@RequestParam int categoryId) {
	//		
	//		Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
	//		
	//		rendezvouses = this.rendezvousService.rendezvousGroupedByCategory(categoryId);
	//		
	//		ModelAndView result = new ModelAndView("rendezvous/list");
	//		result.addObject("rendezvouses", rendezvouses);
	//		result.addObject("rendezvousSource", null);
	//		result.addObject("requestURI", "rendezvous/list.do");
	//		
	//		return result;
	//	}

	// Display -------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rendezvousId) {

		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final Actor principal = this.actorService.findByPrincipal();

		Assert.isTrue((principal == null && rendezvous.getAdult() == false) || (!this.actorService.isAdult(principal.getBirth()) && rendezvous.getAdult() == false) || (this.actorService.isAdult(principal.getBirth())));

		final ModelAndView result = new ModelAndView("rendezvous/display");
		result.addObject("rendezvous", rendezvous);
		if ((principal == null) || (principal instanceof Admin))
			result.addObject("areRSPVd", null);
		else if (principal instanceof User)
			if (!((User) principal).getRsvpdRendezvouses().contains(rendezvous))
				result.addObject("areRSPVd", false);
			else
				result.addObject("areRSPVd", true);

		return result;
	}

}
