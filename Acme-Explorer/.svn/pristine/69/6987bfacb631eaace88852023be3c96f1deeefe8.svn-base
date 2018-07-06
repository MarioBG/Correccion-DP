
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ExplorerService;
import controllers.AbstractController;
import domain.Explorer;

@Controller
@RequestMapping("/explorer/explorer")
public class ExplorerExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	public ExplorerService	explorerService;

	@Autowired
	public ActorService actorService;


	// Constructor -------------------------------------------------------------

	public ExplorerExplorerController() {
		super();
	}

	// Edition  ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;

		final Explorer explorer = this.explorerService.findByPrincipal();
		result = this.createEditModelAndView(explorer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Explorer explorer, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(explorer);
		else
			try {
				this.actorService.editPhone(explorer);
				this.explorerService.save(explorer);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(explorer, "explorer.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Explorer explorer) {

		ModelAndView result;

		result = this.createEditModelAndView(explorer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Explorer explorer, final String message) {

		Assert.notNull(explorer);

		ModelAndView result;

		result = new ModelAndView("explorer/edit");
		result.addObject("actionURI", "explorer/explorer/edit.do");
		result.addObject("explorer", explorer);
		result.addObject("message", message);

		return result;
	}

}
