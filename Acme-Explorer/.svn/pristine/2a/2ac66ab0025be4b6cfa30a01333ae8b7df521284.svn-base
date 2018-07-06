
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditRecordService;
import services.TripService;
import domain.AuditRecord;
import domain.Trip;

@Controller
@RequestMapping("/auditRecord")
public class AuditRecordController extends AbstractController {

	// Services --------------------------------------------------------

	@Autowired
	private AuditRecordService	auditRecordService;

	@Autowired
	private TripService			tripService;


	// constructor -----------------------------------------------------

	public AuditRecordController() {
		super();
	}

	// Listing ---------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditRecordId) {
		ModelAndView result;
		AuditRecord auditRecord;
		String cancelURI ;
		
		auditRecord = this.auditRecordService.findOne(auditRecordId);
		Assert.notNull(auditRecord);
		cancelURI="auditRecord/list.do?tripId=" + auditRecord.getTrip().getId();
		result = new ModelAndView("auditRecord/display");
		result.addObject("auditRecord", auditRecord);

		result.addObject("cancelURI", cancelURI);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		ModelAndView result;
		Collection<AuditRecord> auditRecords;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		auditRecords = trip.getAuditRecords();
		result = new ModelAndView("auditRecord/list");
		result.addObject("auditRecords", auditRecords);
		result.addObject("requestURI", "auditRecord/list.do");

		return result;
	}

}
