
package controllers.admin;

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

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/admin")
public class CategoryAdminController extends AbstractController {

	// Services ------------------------------------------------------------

	@Autowired
	private CategoryService	categoryService;


	// Constructors --------------------------------------------------------

	public CategoryAdminController() {
		super();
	}

	// Listing  --------------------------------------------------------------

	// Creation  --------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result = null;
		Category category = null;

		category = this.categoryService.create();
		result = this.createEditModelAndView(category);

		return result;

	}

	// Edition    --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {

		ModelAndView result = null;
		Category category = null;

		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);

		result = this.createEditModelAndView(category);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult bindingResult) {

		ModelAndView result = null;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:../list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(category, "category.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Category category, final BindingResult bindingResult) {
		ModelAndView result = null;

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:../list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category, "category.commit.error");
		}

		return result;
	}

	// Ancillary methods ----------------------------------------------------

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result = null;
		result = this.createEditModelAndView(category, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String messageCode) {
		ModelAndView result = null;
		Collection<Category> categories = null;

		categories = this.categoryService.findAll();
		categories.remove(category);
		result = new ModelAndView("category/edit");	//
		result.addObject("category", category);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);
		return result;
	}
}
