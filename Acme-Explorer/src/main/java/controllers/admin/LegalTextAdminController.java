
package controllers.admin;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LegalTextService;
import controllers.AbstractController;
import domain.LegalText;

@Controller
@RequestMapping("/legalText/admin")
public class LegalTextAdminController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private LegalTextService	legalTextService;


	// Constructors --------------------------------------------------

	public LegalTextAdminController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<LegalText> legalTexts;

		legalTexts = this.legalTextService.findAll();

		result = new ModelAndView("legalText/list");
		result.addObject("legalTexts", legalTexts);

		return result;
	}

	// Creation -----------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView result;
		LegalText legalText;

		legalText = this.legalTextService.create();

		result = this.createEditModelAndView(legalText);

		return result;
	}

	// Edition -------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int legalTextId) {

		ModelAndView result;
		LegalText legalText;

		legalText = this.legalTextService.findOneToEdit(legalTextId);

		result = this.createEditModelAndView(legalText);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LegalText legalText, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(legalText);
		else
			try {
				this.legalTextService.save(legalText);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(legalText, "legalText.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LegalText legalText, final BindingResult binding) {

		ModelAndView result;

		try {
			this.legalTextService.delete(legalText);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(legalText, "legalText.commit.error");
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final LegalText legalText) {

		ModelAndView result;

		result = this.createEditModelAndView(legalText, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LegalText legalText, final String messageCode) {

		final ModelAndView result;

		result = new ModelAndView("legalText/edit");
		result.addObject("legalText", legalText);

		return result;
	}

}
