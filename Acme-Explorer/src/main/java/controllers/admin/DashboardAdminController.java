
package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.LegalTextService;
import services.ManagerService;
import services.RangerService;
import services.TripService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/admin")
public class DashboardAdminController extends AbstractController {

	// services -------------------------------------------------
	@Autowired
	private TripService			tripService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private LegalTextService	legalTextService;


	// constructor ----------------------------------------------

	public DashboardAdminController() {
		super();
	}

	// listing ---------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("dashboard/list");

		result.addObject("minMaxAvgStddevOfApplicationsPerTrip", this.tripService.avgMinMaxDevApplicationsPerTrip());
		result.addObject("minMaxAvgStddevOfTripsPerManager", this.managerService.avgMinAvgMinMaxDesvTripsPerManager());
		result.addObject("minMaxAvgStddevOfPricePerTrip", this.tripService.avgMinMaxDevPriceOfTheTrips());
		result.addObject("minMaxAvgOfTripsPerRanger", this.rangerService.avgMinMaxDesvTripsPerRanger());

		result.addObject("ratioApplicationsPending", this.applicationService.findRatioOfPendingApplications());
		result.addObject("ratioApplicationsDue", this.applicationService.findRatioOfDueApplications());
		result.addObject("ratioApplicationsAccepted", this.applicationService.findRatioOfAcceptedApplications());
		result.addObject("ratioApplicationsCancelled", this.applicationService.findRatioOfCancellededApplications());
		result.addObject("ratioTripsCancelled", this.tripService.ratioOfTripsCancelledVsTotalTripsOrganized());
		result.addObject("findTrips10MoreApplications", this.tripService.listingTrips10PercentMoreApplicantionsThanAvg());
		result.addObject("findLegalTextOrderByReferenced", this.legalTextService.findLegalTextperTrips());
		result.addObject("minMaxAvgDevNotesPerTrip", this.tripService.minMaxAvgDevNotesPerTrip());
		result.addObject("minMaxAvgDevAuditRecordPerTrip", this.tripService.minMaxAvgDevAuditRecordPerTrip());
		result.addObject("ratioOfTripsWithAnyAuditRecord", this.tripService.ratioOfTripsWithAnyAuditRecord());
		result.addObject("ratioRangersWithCurriculum", this.rangerService.ratioRangersWithCurriculum());
		result.addObject("ratioRangersCurriculumWithEndorserRecords", this.rangerService.ratioRangersCurriculumWithEndorserRecords());
		result.addObject("ratioManagersSuspicious", this.managerService.ratioManagersSuspicious());
		result.addObject("ratioRangersSuspicious", this.rangerService.ratioRangersSuspicious());

		return result;
	}

	// creating --------------------------------------------------

	// edition --------------------------------------------------

	// Ancillary Methods ------------------------------------------------------------------------

}
