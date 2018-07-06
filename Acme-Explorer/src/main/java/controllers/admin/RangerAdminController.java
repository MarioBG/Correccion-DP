
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RangerService;
import controllers.AbstractController;
import domain.Ranger;

@Controller
@RequestMapping("/ranger/admin")
public class RangerAdminController extends AbstractController {

	// Services ------------------------------------------------------------

	@Autowired
	private RangerService	rangerService;


	// Constructors --------------------------------------------------------

	public RangerAdminController() {
		super();
	}

	// Listing -------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result = null;
		Ranger ranger = null;

		ranger = this.rangerService.create();
		result = this.createEditModelAndView(ranger);
		return result;
	}

	// Creation  -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Ranger ranger, final BindingResult bindingResult) {

		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(ranger);
		else
			try {
				this.rangerService.save(ranger);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ranger, "ranger.commit.error");
			}
		return result;
	}

	// Display -------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Ranger ranger) {

		ModelAndView result = null;

		result = this.createEditModelAndView(ranger, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Ranger ranger, final String message) {

		Assert.notNull(ranger);

		ModelAndView result = null;

		result = new ModelAndView("ranger/register");
		result.addObject("actionURI", "ranger/admin/edit.do");
		result.addObject("ranger", ranger);
		result.addObject("message", message);

		return result;
	}
}
