
package controllers.auditor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditRecordService;
import services.AuditorService;
import services.TripService;
import controllers.AbstractController;
import domain.AuditRecord;
import domain.Auditor;
import domain.Trip;

@Controller
@RequestMapping("/auditRecord/auditor")
public class AuditRecordAuditorController extends AbstractController {

	// Services --------------------------------------------------------------------

	@Autowired
	private AuditRecordService	auditRecordService;

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private TripService			tripService;


	// constructor ----------------------------------------------

	public AuditRecordAuditorController() {
		super();
	}

	// listing ---------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<AuditRecord> audits;
		Auditor auditor;

		auditor = this.auditorService.findByPrincipal();
		audits = this.auditRecordService.findByAuditor(auditor);
		result = new ModelAndView("auditRecord/list");
		result.addObject("auditRecords", audits);
		result.addObject("requestURI", "auditRecord/auditor/list.do");

		return result;
	}

	// creating --------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		AuditRecord auditRecord;

		auditRecord = this.auditRecordService.create();
		result = this.createEditModelAndView(auditRecord);

		return result;
	}

	// edition --------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditRecordId) {
		ModelAndView result;
		AuditRecord auditRecord;

		auditRecord = this.auditRecordService.findOne(auditRecordId);
		Assert.notNull(auditRecord);
		result = this.createEditModelAndView(auditRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AuditRecord auditRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(auditRecord);
		else
			try {
				this.auditRecordService.save(auditRecord);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditRecord, "auditRecord.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final AuditRecord auditRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(auditRecord);
		else
			try {
				this.auditRecordService.delete(auditRecord);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditRecord, "auditRecord.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditRecordId) {
		ModelAndView result;
		AuditRecord auditRecord;
		String cancelURI;

		auditRecord = this.auditRecordService.findOne(auditRecordId);
		Assert.notNull(auditRecord);
		cancelURI = "auditRecord/auditor/list.do";
		result = new ModelAndView("auditRecord/display");
		result.addObject("auditRecord", auditRecord);

		result.addObject("cancelURI", cancelURI);

		return result;
	}

	// Ancillary methods ------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AuditRecord auditRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(auditRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AuditRecord auditRecord, final String messageCode) {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAll();
		result = new ModelAndView("auditRecord/edit");
		result.addObject("auditRecord", auditRecord);
		result.addObject("trips", trips);

		result.addObject("message", messageCode);
		return result;
	}

}
