
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import controllers.AbstractController;
import domain.Curriculum;

@Controller
@RequestMapping("/curriculum/ranger")
public class CurriculumRangerController extends AbstractController {

	// Services ------------------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	// Constructors --------------------------------------------------------

	public CurriculumRangerController() {
		super();
	}

	// Listing --------------------------------------------------------------

	// Display --------------------------------------------------------------

	// Creation  --------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = null;
		Curriculum curriculum = null;

		curriculum = this.curriculumService.create();
		result = this.createEditModelAndView(curriculum);

		return result;
	}

	// Edition    --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Curriculum curriculum, final BindingResult bindingResult) {
		ModelAndView result = null;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:../display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(curriculum, "application.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Curriculum curriculum, final BindingResult bindingResult) {
		ModelAndView result = null;

		this.curriculumService.checkRangerByCurriculum(curriculum);
		try {
			final Curriculum cv = this.curriculumService.findOne(curriculum.getId());
			this.curriculumService.delete(cv);
			result = new ModelAndView("redirect:../display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
		}

		return result;
	}
	// Ancillary methods ----------------------------------------------------

	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView result = null;
		result = this.createEditModelAndView(curriculum, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String messageCode) {
		ModelAndView result = null;

		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);
		result.addObject("personalRecord", curriculum.getPersonalRecord());
		result.addObject("educationRecords", curriculum.getEducationRecords());
		result.addObject("endorserRecords", curriculum.getEndorserRecords());
		result.addObject("miscellaneousRecords", curriculum.getMiscellaneousRecords());
		result.addObject("professionalRecords", curriculum.getProfessionalRecords());

		return result;
	}
}
