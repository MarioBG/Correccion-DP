
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RendezvousRepository;
import domain.Announcement;
import domain.Comment;
import domain.GpsCoordinate;
import domain.Rendezvous;
import domain.Request;
import domain.User;
import forms.RendezvousForm;

@Service
@Transactional
public class RendezvousService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RendezvousRepository	rendezvousRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService				userService;

	@Autowired
	private AdminService			adminService;

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private RequestService			requestService;

	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------------------------

	public RendezvousService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Rendezvous create() {

		final Rendezvous result = new Rendezvous();

		result.setAdult(false);
		result.setFinalVersion(false);
		result.setDeleted(false);
		result.setOrganiser(this.userService.findByPrincipal());
		result.setAnnouncements(new ArrayList<Announcement>());
		result.setComments(new ArrayList<Comment>());
		//result.setQuestions(new ArrayList<Question>());
		result.setAttendants(new ArrayList<User>());
		result.getAttendants().add(this.userService.findByPrincipal());
		result.setLinkedRendezvouses(new ArrayList<Rendezvous>());
		result.setRequests(new ArrayList<Request>());

		return result;

	}

	public Collection<Rendezvous> findAll() {
		Collection<Rendezvous> res;
		res = this.rendezvousRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Rendezvous findOne(final int rendezvousId) {

		final Rendezvous result = this.rendezvousRepository.findOne(rendezvousId);
		Assert.notNull(result);
		return result;
	}

	public Rendezvous findOneToEdit(final int rendezvousId) {

		final Rendezvous result = this.findOne(rendezvousId);
		this.checkPrincipal(result);
		return result;
	}

	public Rendezvous save(final Rendezvous rendezvous) {

		Assert.notNull(rendezvous);
		Assert.notNull(rendezvous.getName());
		Assert.notNull(rendezvous.getDescription());
		Assert.isTrue(this.isFuture(rendezvous.getMoment()));
		this.checkPrincipal(rendezvous);

		final Rendezvous result;

		result = this.rendezvousRepository.save(rendezvous);

		final User principal = this.userService.findByPrincipal();

		if (rendezvous.getId() == 0)
			principal.getOrganisedRendezvouses().add(result);
		principal.getRsvpdRendezvouses().add(result);

		return result;
	}

	public void delete(final Rendezvous rendezvous) {

		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getId() != 0);
		Assert.isTrue(this.adminService.findByPrincipal() != null);

		final Collection<User> attendants = new ArrayList<User>(rendezvous.getAttendants());
		final Collection<Announcement> announcements = new ArrayList<Announcement>(rendezvous.getAnnouncements());
		final Collection<Rendezvous> linkedRendezvouses = new ArrayList<Rendezvous>(rendezvous.getLinkedRendezvouses());
		final Collection<Rendezvous> parentRendezvouses = new ArrayList<Rendezvous>(this.findParentRendezvouses(rendezvous.getId()));
		final Collection<Request> requests = new ArrayList<Request>(rendezvous.getRequests());

		rendezvous.getOrganiser().getOrganisedRendezvouses().remove(rendezvous);
		for (User attendant : attendants) {
			attendant.getRsvpdRendezvouses().remove(rendezvous);
			rendezvous.getAttendants().remove(attendant);
		}
		for (Announcement announcement : announcements) {
			this.announcementService.delete(announcement);
			rendezvous.getAnnouncements().remove(announcement);
		}
		for (Comment comment : rendezvous.getComments()) {
			this.commentService.delete(comment);
			rendezvous.setComments(new ArrayList<Comment>());
		}
		for (Request request : requests) {
			this.requestService.delete(request);
			rendezvous.setRequests(new ArrayList<Request>());
		}
		for (Rendezvous linkedRendezvous : linkedRendezvouses) {
			rendezvous.getLinkedRendezvouses().remove(linkedRendezvous);
		}
		for (Rendezvous parentRendezvous : parentRendezvouses)
			parentRendezvous.getLinkedRendezvouses().remove(rendezvous);

		this.rendezvousRepository.delete(rendezvous);
	}
	// Other business methods -------------------------------------------------

	public boolean isFuture(final Date date) {

		boolean result = false;
		final Date actualDate = new Date();
		if (actualDate.before(date))
			result = true;

		return result;
	}

	public RendezvousForm construct(final Rendezvous rendezvous) {

		Assert.notNull(rendezvous);

		RendezvousForm rendezvousForm;

		rendezvousForm = new RendezvousForm();

		rendezvousForm.setId(rendezvous.getId());
		rendezvousForm.setName(rendezvous.getName());
		rendezvousForm.setDescription(rendezvous.getDescription());
		rendezvousForm.setPicture(rendezvous.getPicture());
		rendezvousForm.setMoment(rendezvous.getMoment());
		if (rendezvous.getGpsCoordinate() == null) {
			rendezvousForm.setLatitude(null);
			rendezvousForm.setLongitude(null);
		} else {
			rendezvousForm.setLatitude(rendezvous.getGpsCoordinate().getLatitude());
			rendezvousForm.setLongitude(rendezvous.getGpsCoordinate().getLongitude());
		}
		rendezvousForm.setAdult(rendezvous.getAdult());
		rendezvousForm.setFinalVersion(rendezvous.getFinalVersion());
		rendezvousForm.setDeleted(rendezvous.getDeleted());

		return rendezvousForm;
	}

	public Rendezvous reconstruct(final RendezvousForm rendezvousForm, final BindingResult binding) {

		Assert.notNull(rendezvousForm);

		Rendezvous rendezvous;

		if (rendezvousForm.getId() != 0)
			rendezvous = this.findOne(rendezvousForm.getId());
		else
			rendezvous = this.create();

		final GpsCoordinate gpsCoordinate = new GpsCoordinate();
		gpsCoordinate.setLatitude(rendezvousForm.getLatitude());
		gpsCoordinate.setLongitude(rendezvousForm.getLongitude());

		rendezvous.setName(rendezvousForm.getName());
		rendezvous.setDescription(rendezvousForm.getDescription());
		rendezvous.setPicture(rendezvousForm.getPicture());
		rendezvous.setMoment(rendezvousForm.getMoment());
		rendezvous.setGpsCoordinate(gpsCoordinate);
		rendezvous.setAdult(rendezvousForm.getAdult());
		rendezvous.setFinalVersion(rendezvousForm.getFinalVersion());
		rendezvous.setDeleted(rendezvousForm.getDeleted());

		if (binding != null)
			this.validator.validate(rendezvous, binding);

		return rendezvous;
	}

	public void checkPrincipal(final Rendezvous rendezvous) {

		Assert.notNull(rendezvous);

		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(this.adminService.findByPrincipal() != null || rendezvous.getOrganiser().equals(principal));
	}

	public void checkPrincipalForm(final RendezvousForm rendezvousForm) {

		Assert.notNull(rendezvousForm);

		final Rendezvous rendezvous = this.reconstruct(rendezvousForm, null);
		final User principal = this.userService.findByPrincipal();

		Assert.isTrue(rendezvous.getOrganiser().equals(principal));
	}

	public void changeToDeleted(final int rendezvousId) {

		final Rendezvous rendezvous = this.findOneToEdit(rendezvousId);

		Assert.isTrue(rendezvous.getDeleted() == false);
		Assert.isTrue(rendezvous.getFinalVersion() == false);

		rendezvous.setDeleted(true);
		this.save(rendezvous);
	}

	public Collection<Rendezvous> findFutureMomentAndNotAdult() {

		final Collection<Rendezvous> result = this.rendezvousRepository.findFutureMomentAndNotAdult();
		return result;
	}

	public Collection<Rendezvous> findFutureMoment() {

		final Collection<Rendezvous> result = this.rendezvousRepository.findFutureMoment();
		return result;
	}

	public Collection<Rendezvous> linkedRendezvousesFutureMomentByRendezvousId(final int rendezvousId) {

		final Collection<Rendezvous> result = this.rendezvousRepository.linkedRendezvousesFutureMomentByRendezvousId(rendezvousId);
		return result;
	}

	public Collection<Rendezvous> linkedRendezvousesFutureMomentAndNotAdultByRendezvousId(final int rendezvousId) {

		final Collection<Rendezvous> result = this.rendezvousRepository.linkedRendezvousesFutureMomentAndNotAdultByRendezvousId(rendezvousId);
		return result;
	}

	public Collection<Rendezvous> findByOrganiserId(final int organiserId) {

		final Collection<Rendezvous> result = this.rendezvousRepository.findByOrganiserId(organiserId);
		return result;
	}

	public Collection<Rendezvous> findByOrganiserIdNotAdult(final int organiserId) {

		final Collection<Rendezvous> result = this.rendezvousRepository.findByOrganiserIdNotAdult(organiserId);
		return result;
	}

	public Collection<Rendezvous> findByAttendantId(final int attendantId) {

		final Collection<Rendezvous> result = this.rendezvousRepository.findByAttendantId(attendantId);
		return result;
	}

	public Collection<Rendezvous> findByAttendantIdNotAdult(final int attendantId) {

		final Collection<Rendezvous> result = this.rendezvousRepository.findByAttendantIdNotAdult(attendantId);
		return result;
	}

	public Collection<Rendezvous> findOrganisedRendezvousesByPrincipal() {

		final User organiser = this.userService.findByPrincipal();
		final Collection<Rendezvous> result = this.findByOrganiserId(organiser.getId());
		return result;
	}

	public Collection<Rendezvous> findByOrganiserIdFinal(final int organiserId) {

		final Collection<Rendezvous> result = this.rendezvousRepository.findByOrganiserIdFinal(organiserId);
		return result;
	}

	public Collection<Rendezvous> findOrganisedRendezvousesByPrincipalFinal() {

		final User organiser = this.userService.findByPrincipal();
		final Collection<Rendezvous> result = this.findByOrganiserIdFinal(organiser.getId());
		return result;
	}

	public Collection<Rendezvous> findRspvdRendezvousesByPrincipal() {

		final User attendant = this.userService.findByPrincipal();
		final Collection<Rendezvous> result = this.findByAttendantId(attendant.getId());
		return result;
	}

	public Collection<Rendezvous> findParentRendezvouses(final int rendezvousId) {

		final Collection<Rendezvous> result = this.rendezvousRepository.findParentRendezvouses(rendezvousId);
		return result;
	}

	public Collection<Rendezvous> rendezvousGroupedByCategory(final int categoryId) {
		final Collection<Rendezvous> result = this.rendezvousRepository.rendezvousGroupedByCategory(categoryId);
		return result;
	}

	public void flush() {
		this.rendezvousRepository.flush();
	}

	public void YesRSVP(final int rendezvousId) {
		User usuario;
		Rendezvous rendezvous;
		rendezvous = this.rendezvousRepository.findOne(rendezvousId);
		usuario = this.userService.findByPrincipal();

		if (rendezvous.getAdult() == true)
			Assert.isTrue(this.Age(usuario.getBirth()) > 17);

		if (!rendezvous.getAttendants().contains(usuario))
			rendezvous.getAttendants().add(usuario);
		this.rendezvousRepository.save(rendezvous);

	}

	public void NoRSVP(final int rendezvousId) {
		User usuario;
		Rendezvous rendezvous;
		rendezvous = this.rendezvousRepository.findOne(rendezvousId);
		usuario = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getAttendants().contains(usuario));
		rendezvous.getAttendants().remove(usuario);
		this.rendezvousRepository.save(rendezvous);

	}

	public int Age(final Date birthDay) {
		Calendar today, fechan;
		today = Calendar.getInstance();
		fechan = Calendar.getInstance();
		fechan.setTime(birthDay);

		int diffYear = today.get(Calendar.YEAR) - fechan.get(Calendar.YEAR);
		final int diffMonth = today.get(Calendar.MONTH) - fechan.get(Calendar.MONTH);
		final int diffDay = today.get(Calendar.DAY_OF_MONTH) - fechan.get(Calendar.DAY_OF_MONTH);

		if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0))
			diffYear = diffYear - 1;
		return diffYear;

	}

	public Rendezvous FakeDelete(final Rendezvous rendezvous) {
		User user;
		Rendezvous res;

		Assert.notNull(rendezvous);

		user = this.userService.findByPrincipal();
		Assert.isTrue(user.getOrganisedRendezvouses().contains(rendezvous));
		final boolean x = true;
		Assert.isTrue(rendezvous.getFinalVersion() == true);
		rendezvous.setDeleted(x);
		res = this.rendezvousRepository.save(rendezvous);

		return res;
	}

}
