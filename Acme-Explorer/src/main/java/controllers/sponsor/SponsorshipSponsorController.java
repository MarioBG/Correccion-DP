
package controllers.sponsor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	// Services --------------------------------------------

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;


	// Constructor --------------------------------------------

	public SponsorshipSponsorController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Sponsorship> sponsorships;

		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		sponsorships = this.sponsorshipService.findBySponsor(sponsor);
		result = new ModelAndView("sponsorship/list");// es el nombre de la vista
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/sponsor/list.do");// la requestURI si no esta bien falla a la hora de paginar o ordenar
		result.addObject("sponsorship/sponsor/display.do?sponsorshipId=");

		return result;

	}
	// Creation ------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();
		result = this.createEditModelAndView(sponsorship);

		return result;
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		result = new ModelAndView("sponsorship/display");
		result.addObject("sponsorship", sponsorship);
		result.addObject("cancelURI", "sponsorship/sponsor/list.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		final ModelAndView result;
		Sponsorship sponsorship;
		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(sponsorship);
		result = this.createEditModelAndView(sponsorship);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship);
		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		try {
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("sponsorship/sponsor/edit.do");//mirar esto despues
		result.addObject("sponsorship/sponsor/list.do");
		result.addObject("message", messageCode);
		return result;
	}
}
