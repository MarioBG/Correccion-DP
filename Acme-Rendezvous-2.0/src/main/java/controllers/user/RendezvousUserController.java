
package controllers.user;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Rendezvous;
import domain.User;
import forms.RendezvousForm;

@Controller
@RequestMapping("/rendezvous/user")
public class RendezvousUserController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;

	@Autowired
	private ActorService		actorService;


	// Constructors --------------------------------------------------

	public RendezvousUserController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list-organised", method = RequestMethod.GET)
	public ModelAndView organised() {

		final Date date = new Date();
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findOrganisedRendezvousesByPrincipal();

		final ModelAndView result = new ModelAndView("rendezvous/list");
		result.addObject("date", date);
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("rendezvousSource", null);
		result.addObject("requestURI", "rendezvous/user/list-organised.do");

		return result;
	}

	@RequestMapping(value = "/list-rspv", method = RequestMethod.GET)
	public ModelAndView rspv() {

		final Date date = new Date();
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findRspvdRendezvousesByPrincipal();

		final ModelAndView result = new ModelAndView("rendezvous/list");
		result.addObject("date", date);
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("rendezvousSource", null);
		result.addObject("requestURI", "rendezvous/user/list-rspv.do");

		return result;
	}

	@RequestMapping(value = "/list-link", method = RequestMethod.GET)
	public ModelAndView link(@RequestParam final int rendezvousId) {

		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findOrganisedRendezvousesByPrincipalFinal();
		final Rendezvous rendezvousSource = this.rendezvousService.findOneToEdit(rendezvousId);

		rendezvouses.remove(rendezvousSource);

		final ModelAndView result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("rendezvousSource", rendezvousSource);
		result.addObject("requestURI", "rendezvous/user/list-link.do");

		return result;
	}

	// Create ---------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final Rendezvous rendezvous = this.rendezvousService.create();
		final RendezvousForm rendezvousForm = this.rendezvousService.construct(rendezvous);

		final ModelAndView result = this.createEditModelAndView(rendezvousForm);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rendezvousId) {

		final Rendezvous rendezvous = this.rendezvousService.findOneToEdit(rendezvousId);
		final RendezvousForm rendezvousForm = this.rendezvousService.construct(rendezvous);

		final ModelAndView result = this.createEditModelAndView(rendezvousForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RendezvousForm rendezvousForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(rendezvousForm);
		else if (!this.rendezvousService.isFuture(rendezvousForm.getMoment()))
			result = this.createEditModelAndView(rendezvousForm, "rendezvous.date.past");
		else if ((rendezvousForm.getLatitude() != null && rendezvousForm.getLongitude() == null) || (rendezvousForm.getLatitude() == null && rendezvousForm.getLongitude() != null))
			result = this.createEditModelAndView(rendezvousForm, "rendezvous.wrong.coordinate");
		else if (rendezvousForm.getLatitude() != null && (rendezvousForm.getLatitude() < -90.0 || rendezvousForm.getLatitude() > 90.0))
			result = this.createEditModelAndView(rendezvousForm, "rendezvous.wrong.coordinate");
		else if (rendezvousForm.getLongitude() != null && (rendezvousForm.getLongitude() < -180.0 || rendezvousForm.getLongitude() > 180.0))
			result = this.createEditModelAndView(rendezvousForm, "rendezvous.wrong.coordinate");
		else
			try {
				final Rendezvous rendezvous = this.rendezvousService.reconstruct(rendezvousForm, binding);
				this.rendezvousService.save(rendezvous);
				result = new ModelAndView("redirect:list-organised.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(rendezvousForm, "rendezvous.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final RendezvousForm rendezvousForm, final BindingResult binding) {

		ModelAndView result;

		try {
			this.rendezvousService.changeToDeleted(rendezvousForm.getId());
			result = new ModelAndView("redirect:list-organised.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rendezvousForm, "rendezvous.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assign(final int rendezvousSourceId, final int rendezvousTargetId) {

		final Rendezvous source = this.rendezvousService.findOneToEdit(rendezvousSourceId);
		final Rendezvous target = this.rendezvousService.findOneToEdit(rendezvousTargetId);

		ModelAndView result;

		try {
			source.getLinkedRendezvouses().add(target);
			this.rendezvousService.save(source);
			result = new ModelAndView("redirect:list-link.do?rendezvousId=" + rendezvousSourceId);
		} catch (final Throwable oops) {
			final RendezvousForm sourceForm = this.rendezvousService.construct(source);
			result = this.createEditModelAndView(sourceForm, "rendezvous.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/unassign", method = RequestMethod.GET)
	public ModelAndView unassign(final int rendezvousSourceId, final int rendezvousTargetId) {

		final Rendezvous source = this.rendezvousService.findOneToEdit(rendezvousSourceId);
		final Rendezvous target = this.rendezvousService.findOneToEdit(rendezvousTargetId);

		ModelAndView result;

		try {
			source.getLinkedRendezvouses().remove(target);
			this.rendezvousService.save(source);
			result = new ModelAndView("redirect:list-link.do?rendezvousId=" + rendezvousSourceId);
		} catch (final Throwable oops) {
			final RendezvousForm sourceForm = this.rendezvousService.construct(source);
			result = this.createEditModelAndView(sourceForm, "rendezvous.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/rspv", method = RequestMethod.GET)
	public ModelAndView rspv(@RequestParam final int rendezvousId) {

		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final User principal = this.userService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(!principal.getRsvpdRendezvouses().contains(rendezvous));

		principal.getRsvpdRendezvouses().add(rendezvous);
		rendezvous.getAttendants().add(principal);

		this.userService.save(principal);

		return new ModelAndView("redirect:list-rspv.do");
	}

	@RequestMapping(value = "/unrspv", method = RequestMethod.GET)
	public ModelAndView unrspv(@RequestParam final int rendezvousId) {

		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final User principal = this.userService.findByPrincipal();

		Assert.notNull(principal);
		Assert.isTrue(principal.getRsvpdRendezvouses().contains(rendezvous));

		principal.getRsvpdRendezvouses().remove(rendezvous);
		rendezvous.getAttendants().remove(principal);

		this.userService.save(principal);

		return new ModelAndView("redirect:list-rspv.do");
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final RendezvousForm rendezvousForm) {
		ModelAndView result;

		result = this.createEditModelAndView(rendezvousForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final RendezvousForm rendezvousForm, final String messageCode) {

		this.rendezvousService.checkPrincipalForm(rendezvousForm);
		final User user = this.userService.findByPrincipal();
		final boolean adult = this.actorService.isAdult(user.getBirth());

		ModelAndView result;

		if (rendezvousForm.getId() == 0)
			result = new ModelAndView("rendezvous/create");
		else
			result = new ModelAndView("rendezvous/edit");

		result.addObject("rendezvousForm", rendezvousForm);
		result.addObject("adultUser", adult);
		result.addObject("message", messageCode);

		return result;
	}

}
