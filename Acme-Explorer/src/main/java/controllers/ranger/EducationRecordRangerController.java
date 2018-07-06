
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EducationRecordService;
import controllers.AbstractController;
import domain.EducationRecord;

@Controller
@RequestMapping("/educationRecord/ranger")
public class EducationRecordRangerController extends AbstractController {

	// Services ------------------------------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;


	// Constructors --------------------------------------------------------

	public EducationRecordRangerController() {
		super();
	}

	// Listing --------------------------------------------------------------

	// Display --------------------------------------------------------------

	// Creation  --------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createEducationRecord() {
		ModelAndView result = null;
		EducationRecord educationRecord = null;

		educationRecord = this.educationRecordService.create();
		result = this.createEditModelAndView(educationRecord);

		return result;
	}

	// Edition    --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView result = null;
		EducationRecord educationRecord = null;

		educationRecord = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(educationRecord);
		result = this.createEditModelAndView(educationRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult bindingResult) {
		ModelAndView result = null;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:../../curriculum/display.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().contains("Start Date before end Date"))
					result = this.createEditModelAndView(educationRecord, "general.commit.error.start");
				else
					result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationRecord educationRecord, final BindingResult bindingResult) {
		ModelAndView result = null;

		try {
			this.educationRecordService.delete(educationRecord);
			result = new ModelAndView("redirect:../../curriculum/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
		}

		return result;
	}
	// Ancillary methods ----------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result = null;
		result = this.createEditModelAndView(educationRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {

		ModelAndView result = null;
		String cancelURI = null;

		cancelURI = "curriculum/display.do";

		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("cancelURI", cancelURI);
		result.addObject("message", messageCode);

		return result;
	}
}
