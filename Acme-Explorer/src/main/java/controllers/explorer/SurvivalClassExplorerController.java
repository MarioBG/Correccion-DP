
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass/explorer")
public class SurvivalClassExplorerController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripService				tripService;


	// constructor ----------------------------------------------

	public SurvivalClassExplorerController() {
		super();
	}

	// listing ---------------------------------------------------

	@RequestMapping(value = "/list-enrolled", method = RequestMethod.GET)
	public ModelAndView listEnrolled() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasses;
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
		survivalClasses = this.survivalClassService.findByExplorer(explorer);
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("requestURI", "survivalClass/explorer/list-enrolled.do");

		return result;
	}

	@RequestMapping(value = "/list-notenrolled", method = RequestMethod.GET)
	public ModelAndView listNotEnrolled() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasses;

		survivalClasses = this.survivalClassService.findCanEnrolByApplicantId(this.explorerService.findByPrincipal().getId());
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("requestURI", "survivalClass/explorer/list-notenrolled.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listForTrip(@RequestParam final int tripId) {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasses;

		survivalClasses = this.survivalClassService.findByTripId(tripId);
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("requestURI", "survivalClass/explorer/list.do");
		result.addObject("cancelURI", "redirect:list.do");

		return result;
	}

	// creating --------------------------------------------------

	// display --------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;
		final boolean enrolled;
		Explorer explorer;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);
		explorer = this.explorerService.findByPrincipal();
		enrolled = survivalClass.getExplorers().contains(explorer);

		result = new ModelAndView("survivalClass/display");
		result.addObject("survivalClass", survivalClass);
		result.addObject("requestURI", "survivalClass/explorer/display.do");
		result.addObject("cancelURI", "survivalClass/explorer/list-notenrolled.do");
		result.addObject("enrolled", enrolled);

		return result;
	}

	@RequestMapping(value = "/enrol", method = RequestMethod.GET)
	public ModelAndView enrol(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;
		Explorer explorer;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);
		explorer = this.explorerService.findByPrincipal();

		try {
			this.survivalClassService.enrolAnExplorerToSurvivalClass(explorer, survivalClass);
			result = this.listEnrolled();
			result.addObject("message", "survivalClass.commit.ok");
		} catch (final Throwable oops) {
			result = this.display(survivalClassId);
			result.addObject("message", "survivalClass.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/disenrol", method = RequestMethod.GET)
	public ModelAndView disenrol(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;
		Explorer explorer;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);
		explorer = this.explorerService.findByPrincipal();

		try {
			this.survivalClassService.disenrolAnExplorerToSurvivalClass(explorer, survivalClass);
			result = this.listEnrolled();
			result.addObject("message", "survivalClass.commit.ok");
		} catch (final Throwable oops) {
			result = this.display(survivalClassId);
			result.addObject("message", "survivalClass.commit.error");
		}

		return result;
	}
	// Ancillary methods ------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass) {
		ModelAndView result;

		result = this.createEditModelAndView(survivalClass, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass, final String messageCode) {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAll();
		result = new ModelAndView("survivalClass/edit");
		result.addObject("survivalClass", survivalClass);
		result.addObject("trips", trips);

		result.addObject("message", messageCode);
		return result;
	}

}
