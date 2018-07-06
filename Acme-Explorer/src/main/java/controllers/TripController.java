
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ExplorerService;
import services.TripService;
import domain.Category;
import domain.Explorer;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private TripService		tripService;

	@Autowired
	private CategoryService	categoryService;

	@Autowired
	private ExplorerService	explorerService;


	// Constructors -----------------------------------------------------------

	public TripController() {
		super();
	}

	// Display  -------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		ModelAndView result = null;
		Trip trip = null;
		Sponsorship sponsorship = null;

		trip = this.tripService.findOne(tripId);

		sponsorship = this.tripService.getRandomSponsorship(trip);
		result = new ModelAndView("trip/display");
		result.addObject("trip", trip);
		result.addObject("sponsorship", sponsorship);
		result.addObject("canEdit", this.tripService.canEdit(trip));
		result.addObject("canCreateApplication", this.canCreateApplication(trip));

		return result;
	}

	// Listing  -------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String keyword, @RequestParam(required = false) final Integer categoryId) {
		final ModelAndView result;
		Collection<Trip> trips = new ArrayList<Trip>();

		if (keyword != null)
			trips = this.tripService.findTripPerKeywordR(keyword);
		if (categoryId != null)
			trips = this.tripService.findTripPerCategory(categoryId);
		if (keyword == null && categoryId == null)
			trips = this.tripService.findTripPerPublicationDate();

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");
		return result;
	}

	@RequestMapping(value = "/list-byCategoryId", method = RequestMethod.GET)
	public ModelAndView listByCategoryId(@RequestParam final Integer categoryId) {
		ModelAndView result;
		Collection<Trip> trips = null;
		Category category = null;

		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);
		trips = category.getTrips();

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");
		return result;
	}

	// Ancillary methods ----------------------------------------------------

	// TODO: comprobar trips
	protected boolean canCreateApplication(final Trip trip) {
		boolean result = false;
		Explorer explorer = null;
		Date moment = null;

		try {
			moment = new Date();
			explorer = this.explorerService.findByPrincipal();

			if (!this.tripService.hasThisTripAnyApplicationFromThisExplorer(trip.getId(), explorer.getId()) && (trip.getPublicationDate().before(moment)) && (trip.getStartDateTrip().after(moment)))
				result = true;
		} catch (final NullPointerException oops) {
			result = false;
		}

		return result;
	}
}
