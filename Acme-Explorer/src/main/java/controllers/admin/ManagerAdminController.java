
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/manager/admin")
public class ManagerAdminController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	public ManagerService	managerService;


	// Constructor -------------------------------------------------------------
	public ManagerAdminController() {
		super();
	}

	// Creating  --------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		final Manager manager = this.managerService.create();
		result = this.createEditModelAndView(manager);
		return result;
	}

	// Edition ---------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final Manager manager, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(manager, "manager.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Manager manager) {

		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager manager, final String message) {

		ModelAndView result;

		result = new ModelAndView("manager/register");
		result.addObject("actionURI", "manager/admin/edit.do");
		result.addObject("manager", manager);
		result.addObject("message", message);

		return result;
	}

}
