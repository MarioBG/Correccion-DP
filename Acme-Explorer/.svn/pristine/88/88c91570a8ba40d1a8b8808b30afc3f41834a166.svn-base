
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HasValueService;
import services.TripService;
import domain.HasValue;
import domain.Trip;

@Controller
@RequestMapping("/hasValue")
public class HasValueController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private HasValueService	hasValueService;

	@Autowired
	private TripService		tripService;


	// Constructors --------------------------------------------------

	public HasValueController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {

		ModelAndView result;
		Collection<HasValue> hasValues;
		Boolean belongTrip;

		hasValues = this.hasValueService.findByTripId(tripId);
		final Trip trip = this.tripService.findOne(tripId);
		belongTrip = this.tripService.checkPrincipalManager(trip);

		result = new ModelAndView("hasValue/list");
		result.addObject("hasValues", hasValues);
		result.addObject("trip", trip);
		result.addObject("belongTrip", belongTrip);

		return result;
	}

}
