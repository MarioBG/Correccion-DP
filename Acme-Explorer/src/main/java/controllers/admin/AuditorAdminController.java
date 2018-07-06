
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;

@Controller
@RequestMapping("/auditor/admin")
public class AuditorAdminController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	public AuditorService	auditorService;


	// Constructor -------------------------------------------------------------
	public AuditorAdminController() {
		super();
	}

	// Creating  --------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		final Auditor auditor = this.auditorService.create();
		result = this.createEditModelAndView(auditor);
		return result;
	}

	// Edition ---------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Auditor auditor, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(auditor);
		else
			try {
				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditor, "auditor.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Auditor auditor) {

		ModelAndView result;

		result = this.createEditModelAndView(auditor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Auditor auditor, final String message) {

		Assert.notNull(auditor);

		ModelAndView result;

		result = new ModelAndView("auditor/register");
		result.addObject("actionURI", "auditor/admin/edit.do");
		result.addObject("auditor", auditor);
		result.addObject("message", message);

		return result;
	}
}
