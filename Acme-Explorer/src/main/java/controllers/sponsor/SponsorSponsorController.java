
package controllers.sponsor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import controllers.AbstractController;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor/sponsor")
public class SponsorSponsorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SponsorService	sponsorService;


	// Constructor -------------------------------------------------------------
	public SponsorSponsorController() {
		super();
	}

	// Edition ---------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;

		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		result = this.createEditModelAndView(sponsor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsor);
		else
			try {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsor, "sponsor.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------------------ 

	protected ModelAndView createEditModelAndView(final Sponsor sponsor) {

		ModelAndView result;

		result = this.createEditModelAndView(sponsor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String message) {

		Assert.notNull(sponsor);

		ModelAndView result;

		result = new ModelAndView("sponsor/edit");
		result.addObject("actionURI", "sponsor/sponsor/edit.do");
		result.addObject("sponsor", sponsor);
		result.addObject("message", message);

		return result;
	}

}
