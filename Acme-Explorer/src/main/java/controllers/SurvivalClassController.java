
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SurvivalClassService;
import services.TripService;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass")
public class SurvivalClassController extends AbstractController {

	// Services --------------------------------------------------------

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;


	// constructor -----------------------------------------------------

	public SurvivalClassController() {
		super();
	}

	// Listing ---------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;
		String cancelURI;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);
		cancelURI = "survivalClass/list.do?tripId=" + survivalClass.getTrip().getId();
		result = new ModelAndView("survivalClass/display");
		result.addObject("survivalClass", survivalClass);

		result.addObject("cancelURI", cancelURI);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasses;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		survivalClasses = trip.getSurvivalClasses();
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("requestURI", "survivalClass/list.do");
		result.addObject("date", new Date());

		return result;
	}

}
