
package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass/manager")
public class SurvivalClassManagerController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private TripService				tripService;


	// constructor ----------------------------------------------

	public SurvivalClassManagerController() {
		super();
	}

	// listing ---------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasses;
		final Manager manager;

		manager = this.managerService.findByPrincipal();
		survivalClasses = this.survivalClassService.findByManager(manager);
		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("requestURI", "survivalClass/manager/list.do");
		result.addObject("date", new Date());

		return result;
	}
	// creating --------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.create();
		result = this.createEditModelAndView(survivalClass);

		return result;
	}

	// edition --------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.findOneToEdit(survivalClassId);
		Assert.notNull(survivalClass);
		result = this.createEditModelAndView(survivalClass);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(survivalClass);
		else
			try {
				this.survivalClassService.save(survivalClass);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(survivalClass, "survivalClass.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(survivalClass);
		else
			try {
				this.survivalClassService.delete(survivalClass);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(survivalClass, "survivalClass.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.findOne(survivalClassId);
		result = new ModelAndView("survivalClass/display");
		result.addObject("survivalClass", survivalClass);
		result.addObject("RequestURI", "survivalClass/manager/display.do");
		result.addObject("cancelURI", "survivalClass/manager/list.do");

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

		final Manager principal = this.managerService.findByPrincipal();

		final Collection<Trip> avalibleTrips = this.tripService.findByDateBeforeStartDateTrip(new Date());
		final Collection<Trip> managerTrips = this.tripService.findByManager(principal);
		final Collection<Trip> trips = ListUtils.intersection(new ArrayList(avalibleTrips), new ArrayList(managerTrips));
		result = new ModelAndView("survivalClass/edit");
		result.addObject("survivalClass", survivalClass);
		result.addObject("trips", trips);

		result.addObject("message", messageCode);
		result.addObject("requestURI", "survivalClass/manager/edit.do");
		result.addObject("cancelURI", "survivalClass/manager/list.do");
		return result;
	}
}
