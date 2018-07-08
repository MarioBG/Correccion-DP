
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.Rendezvous;
import domain.User;
import forms.AnnouncementForm;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository

	@Autowired
	private AnnouncementRepository	announcementRepository;

	// Supporting services

	@Autowired
	private RendezvousService		rendezvousService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdminService			adminService;

	@Autowired
	private Validator				validator;


	// Constructors

	public AnnouncementService() {
		super();
	}

	// Simple CRUD methods 

	public Announcement create(final Rendezvous rendezvous) {
		final Announcement res = new Announcement();

		final Date moment = new Date(System.currentTimeMillis() - 1);

		res.setRendezvous(rendezvous);
		res.setMoment(moment);

		return res;

	}

	public Collection<Announcement> findAll() {
		Collection<Announcement> res;
		res = new ArrayList<Announcement>();

		res = this.announcementRepository.findAll();

		Assert.notNull(res);
		return res;
	}

	public Announcement findOne(final int announcementId) {
		Assert.isTrue(announcementId != 0);
		Announcement res;

		res = this.announcementRepository.findOne(announcementId);

		Assert.notNull(res);
		return res;
	}

	public Announcement save(final Announcement announcement) {
		Assert.notNull(announcement);
		this.checkPrincipal(announcement);

		Announcement res;

		res = this.announcementRepository.save(announcement);

		Assert.notNull(res);
		return res;

	}

	public void delete(final Announcement announcement) {
		this.adminService.checkAuthority();

		Assert.notNull(announcement);
		Assert.isTrue(announcement.getId() != 0);
		Assert.isTrue(this.announcementRepository.exists(announcement.getId()));

		this.announcementRepository.delete(announcement);
	}

	// Other busines methods

	public void checkPrincipal(final Announcement announcement) {
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(principal.equals(announcement.getRendezvous().getOrganiser()));
	}

	public Collection<Announcement> findAnnouncementsByRendezvous(final int rendezvousId) {
		Collection<Announcement> res = new ArrayList<Announcement>();

		res = this.announcementRepository.findAnnouncementsByRendezvous(rendezvousId);
		Assert.notNull(res);

		return res;
	}

	public AnnouncementForm construct(final Announcement announcement) {
		final AnnouncementForm res = new AnnouncementForm();

		res.setId(announcement.getId());
		res.setTitle(announcement.getTitle());
		res.setDescription(announcement.getDescription());
		res.setRendezvousId(announcement.getRendezvous().getId());

		return res;
	}

	public Announcement reconstruct(final AnnouncementForm announcementForm, final BindingResult binding) {
		Assert.notNull(announcementForm);
		final Announcement res = new Announcement();

		final Date moment = new Date(System.currentTimeMillis() - 1);

		res.setId(announcementForm.getId());
		res.setMoment(moment);
		res.setTitle(announcementForm.getTitle());
		res.setDescription(announcementForm.getDescription());
		res.setRendezvous(this.rendezvousService.findOne(announcementForm.getRendezvousId()));

		if (binding != null)
			this.validator.validate(res, binding);

		return res;
	}

	public Collection<Announcement> findAnnouncementsByAttendants() {
		Collection<Announcement> res;
		User user = new User();
		user = this.userService.findByPrincipal();
		res = this.announcementRepository.findAnnouncementsByAttendants(user.getId());
		Assert.notNull(res);
		return res;
	}

	public void flush() {
		this.announcementRepository.flush();
	}

}
