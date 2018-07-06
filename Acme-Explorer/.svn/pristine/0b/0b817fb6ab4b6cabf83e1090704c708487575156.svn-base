
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Trip;

@Controller
@RequestMapping("/trip/explorer")
public class TripExplorerController extends AbstractController {

	// Services -------------------------------------------------------------
	@Autowired
	private TripService		tripService;

	@Autowired
	private ExplorerService	explorerService;


	// Constructors -----------------------------------------------------------

	public TripExplorerController() {
		super();
	}

	// Listing  -------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Explorer explorer = this.explorerService.findByPrincipal();
		Collection<Trip> trips;

		trips = this.tripService.listTripPerExplorer(explorer);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/explorer/list.do");
		result.addObject("searchURI", "trip/explorer/list-keyword.do?keyword=");

		return result;
	}

	@RequestMapping(value = "/list-keyword", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final String keyword) {
		final ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findTripPerKeywordR(keyword);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/explorer/list.do");

		return result;
	}

}
