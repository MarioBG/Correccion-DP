
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

import services.CurriculumService;
import services.EndorserRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord/ranger")
public class EndorserRecordRangerController extends AbstractController {

	// Services ------------------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private EndorserRecordService	endorserRecordService;


	// Constructors --------------------------------------------------------

	public EndorserRecordRangerController() {
		super();
	}

	// Listing --------------------------------------------------------------

	// Display --------------------------------------------------------------

	// Creation  --------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = null;
		EndorserRecord endorserRecord = null;

		endorserRecord = this.endorserRecordService.create();
		result = this.createEditModelAndView(endorserRecord);

		return result;
	}

	// Edition    --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {
		ModelAndView result = null;
		EndorserRecord endorserRecord = null;

		endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(endorserRecord);
		result = this.createEditModelAndView(endorserRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult bindingResult) {

		ModelAndView result = null;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:../../curriculum/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EndorserRecord endorserRecord, final BindingResult bindingResult) {
		ModelAndView result = null;
		Curriculum curriculum = null;
		int curriculumId;

		curriculum = this.curriculumService.findByPrincipal();
		curriculumId = curriculum.getId();

		try {
			this.endorserRecordService.delete(endorserRecord);
			result = new ModelAndView("redirect:../../curriculum/display.do?curriculumId=" + curriculumId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
		}

		return result;
	}

	// Ancillary methods ----------------------------------------------------

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView result = null;
		result = this.createEditModelAndView(endorserRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String messageCode) {

		ModelAndView result = null;
		String cancelURI = null;

		cancelURI = "curriculum/display.do";

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("cancelURI", cancelURI);
		result.addObject("message", messageCode);

		return result;
	}

}
