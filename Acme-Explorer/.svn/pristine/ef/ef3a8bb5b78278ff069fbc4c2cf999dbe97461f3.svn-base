
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import controllers.AbstractController;
import domain.Admin;

@Controller
@RequestMapping("/admin/admin")
public class AdminAdminController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	public AdminService	adminService;


	// Constructor -------------------------------------------------------------

	public AdminAdminController() {
		super();
	}

	// Edition  ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;

		final Admin admin = this.adminService.findByPrincipal();
		result = this.createEditModelAndView(admin);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Admin admin, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(admin);
		else
			try {
				this.adminService.save(admin);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(admin, "admin.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Admin admin) {

		ModelAndView result;

		result = this.createEditModelAndView(admin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Admin admin, final String message) {

		Assert.notNull(admin);

		ModelAndView result;

		result = new ModelAndView("admin/edit");
		result.addObject("admin", admin);
		result.addObject("message", message);

		return result;
	}

}
