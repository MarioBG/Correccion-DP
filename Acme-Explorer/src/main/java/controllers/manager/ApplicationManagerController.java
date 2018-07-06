
package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ManagerService;
import services.MessageService;
import controllers.AbstractController;
import domain.Application;
import domain.Manager;
import domain.Status;

@Controller
@RequestMapping("/application/manager")
public class ApplicationManagerController extends AbstractController {

	// Services ------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private MessageService		messageService;


	// Constructors --------------------------------------------------------

	public ApplicationManagerController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer tripId) {
		ModelAndView result = null;
		result = this.listModelAndView();
		return result;
	}

	// Creation  --------------------------------------------------------------

	// Display --------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result = null;
		Application application = null;
		String cancelURI = null;

		application = this.applicationService.findOne(applicationId);

		cancelURI = "application/manager/list.do?tripId=" + application.getTrip().getId();

		result = new ModelAndView("application/display");
		result.addObject("application", application);
		result.addObject("cancelURI", cancelURI);

		return result;
	}

	// Edition    --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result = null;
		Application application = null;

		application = this.applicationService.findOne(applicationId);

		Assert.notNull(application);
		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult bindingResult) {
		ModelAndView result = null;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				if (application.getStatus() == Status.REJECTED) {
					final Status oldStatus = Status.PENDING;
					this.messageService.notifyChangeInApplicationStatus(oldStatus, application);
				}
				this.applicationService.save(application);

				result = new ModelAndView("redirect:../manager/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/due", method = RequestMethod.GET)
	public ModelAndView due(@RequestParam final int applicationId) {
		ModelAndView result = null;
		Application application = null;

		try {
			application = this.applicationService.findOne(applicationId);
			Assert.notNull(application);
			this.applicationService.dueApplication(application);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("application.commit.error");
		}

		return result;
	}

	// Ancillary methods ----------------------------------------------------

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result = null;
		result = this.createEditModelAndView(application, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result = null;
		String cancelURI = null;
		Collection<Status> status = null;

		cancelURI = "application/manager/list.do?tripId=" + application.getTrip().getId();
		status = this.getAvailableStatus(application);

		result = new ModelAndView("application/edit");
		result.addObject("message", messageCode);
		result.addObject("application", application);
		result.addObject("cancelURI", cancelURI);
		result.addObject("status", status);

		return result;
	}

	protected ModelAndView listModelAndView() {
		ModelAndView result = null;
		result = this.listModelAndView(null);
		return result;
	}

	protected ModelAndView listModelAndView(final String messageCode) {
		ModelAndView result = null;
		Collection<Application> applications = null;
		Manager manager = null;
		String requestURI = null;
		Date dateSystem = null;
		Date dateSystemPlus1Month = null;

		manager = this.managerService.findByPrincipal();
		dateSystem = new Date();
		dateSystemPlus1Month = DateUtils.addDays(dateSystem, 30);
		requestURI = "application/manager/list.do";
		applications = this.applicationService.findApplicationsByManagerId(manager.getId());

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", requestURI);
		result.addObject("dateSystem", dateSystem);
		result.addObject("dateSystemPlus1Month", dateSystemPlus1Month);
		result.addObject("message", messageCode);

		return result;
	}

	private Collection<Status> getAvailableStatus(final Application application) {
		Collection<Status> status = null;
		status = new ArrayList<Status>();

		if (application.getStatus().equals(Status.PENDING)) {
			status.add(Status.PENDING);
			status.add(Status.REJECTED);
		}

		return status;
	}
}
