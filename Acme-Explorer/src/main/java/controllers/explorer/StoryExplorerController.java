
package controllers.explorer;

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

import services.ExplorerService;
import services.StoryService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Controller
@RequestMapping("/story/explorer")
public class StoryExplorerController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private StoryService	storyService;

	@Autowired
	private ExplorerService	explorerService;

	@Autowired
	private TripService		tripService;


	// constructor ----------------------------------------------

	public StoryExplorerController() {
		super();
	}

	// listing ---------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Story> stories;
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
		stories = this.storyService.findByExplorer(explorer);
		result = new ModelAndView("story/list");
		result.addObject("requestURI", "story/explorer/list.do");
		result.addObject("stories", stories);

		return result;
	}

	// creating --------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Story story;

		story = this.storyService.create();
		result = this.createEditModelAndView(story);

		return result;
	}

	// edition --------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int storyId) {
		ModelAndView result;
		Story story;

		story = this.storyService.findOne(storyId);
		Assert.notNull(story);
		result = this.createEditModelAndView(story);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Story story, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(story);
		else
			try {
				this.storyService.save(story);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(story, "story.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Story story, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(story);
		else
			try {
				this.storyService.delete(story);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(story, "story.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int storyId) {
		ModelAndView result;
		Story story;

		story = this.storyService.findOne(storyId);
		Assert.notNull(story);
		result = new ModelAndView("story/display");
		result.addObject("story", story);

		return result;
	}

	// Ancillary methods ------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Story story) {
		ModelAndView result;

		result = this.createEditModelAndView(story, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Story story, final String messageCode) {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findCanWriteStoryByApplicantId(this.explorerService.findByPrincipal().getId());
		result = new ModelAndView("story/edit");
		result.addObject("story", story);
		result.addObject("trips", trips);
		result.addObject("message", messageCode);
		return result;
	}

	private ModelAndView createEditModelAndViewforTrip(final Story story, final Trip trip) {
		ModelAndView result;

		result = new ModelAndView("story/edit");
		result.addObject("story", story);
		result.addObject("trip", trip);
		return result;
	}

}
