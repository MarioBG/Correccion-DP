
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ManagerService;
import domain.Manager;

@Controller
@RequestMapping("/manager")
public class ManagerEditController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ActorService	actorService;


	// Constructor -------------------------------------------------------------
	public ManagerEditController() {
		super();
	}

	// Edition ---------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Manager manager;
		manager = this.managerService.findByPrincipal();
		Assert.notNull(manager);
		result = this.createEditModelAndView(manager);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.actorService.saveUserAccountPass(manager);
				this.managerService.save(manager);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(manager, "manager.commit.error");
			}
		return result;
	}

	// other business methods ------------------------------------------------------------------ 
	protected ModelAndView createEditModelAndView(final Manager user) {
		ModelAndView result;

		result = this.createEditModelAndView(user, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager manager, final String message) {
		ModelAndView result;

		result = new ModelAndView("manager/edit");
		result.addObject("manager", manager);
		result.addObject("message", message);

		return result;
	}

}
