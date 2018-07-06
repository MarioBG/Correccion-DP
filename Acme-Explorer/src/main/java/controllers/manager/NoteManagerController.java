
package controllers.manager;

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

import services.ManagerService;
import services.NoteService;
import services.TripService;
import controllers.AbstractController;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note/manager")
public class NoteManagerController extends AbstractController {

	// Services --------------------------------------------

	@Autowired
	private NoteService		noteService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private TripService		tripService;


	// Constructor --------------------------------------------

	public NoteManagerController() {
		super();
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);
		result = new ModelAndView("note/display");
		result.addObject("note", note);
		result.addObject("cancelURI", "note/manager/list-notes.do");

		return result;
	}

	// Listing (TripId)-------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		final ModelAndView result;
		Collection<Note> notes;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		notes = trip.getNotes();

		result = new ModelAndView("note/list");
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/manager/list.do");
		result.addObject("displayURI", "note/manager/display.do?noteId=");

		return result;

	}

	// Listing (ManagerId)-------------------------------------------------------

	@RequestMapping(value = "/list-notes", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Note> notes;
		final int managerId = this.managerService.findByPrincipal().getId();
		notes = this.managerService.findNotesByManager(managerId);

		result = new ModelAndView("note/list");
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/manager/list-notes.do");
		result.addObject("displayURI", "note/manager/display.do?noteId=");

		return result;

	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {
		final ModelAndView result;
		Note note;
		note = this.noteService.findOneToEdit(noteId);
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
				result = new ModelAndView("redirect:list-notes.do");
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
		result.addObject("actionURI", "note/manager/edit.do");
		result.addObject("cancelURI", "note/manager/list-notes.do");
		result.addObject("message", messageCode);
		return result;
	}

}
