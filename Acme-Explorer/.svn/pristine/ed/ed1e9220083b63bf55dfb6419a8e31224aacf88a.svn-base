
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StageService;
import services.TripService;
import controllers.AbstractController;
import domain.Stage;
import domain.Trip;

@Controller
@RequestMapping("/stage/manager")
public class StageManagerController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private StageService	stageService;

	@Autowired
	private TripService		tripService;


	// Constructors --------------------------------------------------

	public StageManagerController() {
		super();
	}

	// Creation ------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {

		ModelAndView result;
		Stage stage;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		stage = this.stageService.create(trip.getId());

		result = this.createEditModelAndView(stage);

		return result;
	}

	// Edition -------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int stageId) {

		final ModelAndView result;
		Stage stage;

		stage = this.stageService.findOne(stageId);

		result = this.createEditModelAndView(stage);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Stage stage, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(stage);
		else
			try {
				this.stageService.save(stage);
				result = new ModelAndView("redirect:/stage/list.do?tripId=" + stage.getTrip().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(stage, "stage.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Stage stage, final BindingResult binding) {

		ModelAndView result;

		try {
			this.stageService.delete(stage);
			result = new ModelAndView("redirect:/stage/list.do?tripId=" + stage.getTrip().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(stage, "stage.commit.error");
		}

		return result;
	}
	// Ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final Stage stage) {

		ModelAndView result;

		result = this.createEditModelAndView(stage, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Stage stage, final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("stage/edit");
		result.addObject("stage", stage);

		return result;
	}
}
