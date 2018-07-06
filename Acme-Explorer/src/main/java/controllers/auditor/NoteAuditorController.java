
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

import services.AuditorService;
import services.NoteService;
import services.TripService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note/auditor")
public class NoteAuditorController extends AbstractController {

	// Services --------------------------------------------

	@Autowired
	private NoteService		noteService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private TripService		tripService;


	// Constructor --------------------------------------------

	public NoteAuditorController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Note> notes;

		final Auditor auditor = this.auditorService.findByPrincipal();
		notes = this.noteService.findByAuditor(auditor);
		result = new ModelAndView("note/list");// es el nombre de la vista
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/auditor/list.do");// la requestURI si no esta bien falla a la hora de paginar o ordenar
		return result;

	}
	// Creation ------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Note note;

		note = this.noteService.create();
		result = this.createEditModelAndView(note);

		return result;
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);
		result = new ModelAndView("note/display");
		result.addObject("note", note);
		result.addObject("cancelURI", "note/auditor/list.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {
		final ModelAndView result;
		Note note;
		note = this.noteService.findOne(noteId);
		Assert.notNull(note);
		result = this.createEditModelAndView(note);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAll();

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("trips", trips);
		result.addObject("actionURI", "note/auditor/edit.do");
		result.addObject("cancelURI", "note/auditor/list.do");
		result.addObject("message", messageCode);
		return result;
	}
}
