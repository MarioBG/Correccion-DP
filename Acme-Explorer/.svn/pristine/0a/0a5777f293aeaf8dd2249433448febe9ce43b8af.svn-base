
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

import services.TagService;
import controllers.AbstractController;
import domain.Tag;

@Controller
@RequestMapping("/tag/admin")
public class TagAdminController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private TagService	tagService;


	// Constructors --------------------------------------------------

	public TagAdminController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Tag> tags;

		tags = this.tagService.findAll();

		result = new ModelAndView("tag/list");
		result.addObject("tags", tags);

		return result;
	}

	// Creation -------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView result;
		Tag tag;

		tag = this.tagService.create();

		result = this.createEditModelAndView(tag);

		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagId) {

		ModelAndView result;
		Tag tag;

		tag = this.tagService.findOne(tagId);

		result = this.createEditModelAndView(tag);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tag tag, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tag);
		else
			try {
				this.tagService.save(tag);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tag, "tag.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteGet(@RequestParam final int tagId) {

		ModelAndView result;

		try {
			final Tag tag = this.tagService.findOne(tagId);
			this.tagService.delete(tag);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "tag.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deletePost(final Tag tag, final BindingResult binding) {

		ModelAndView result;

		try {
			this.tagService.delete(tag);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tag, "tag.commit.error");
		}

		return result;
	}

	// Ancillary methods ----------------------------------------------

	protected ModelAndView createEditModelAndView(final Tag tag) {

		ModelAndView result;

		result = this.createEditModelAndView(tag, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tag tag, final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("tag/edit");
		result.addObject("tag", tag);

		return result;
	}

}
