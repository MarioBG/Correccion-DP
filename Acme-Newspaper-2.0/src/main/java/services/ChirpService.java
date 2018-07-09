
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Chirp;
import domain.Configuration;
import domain.User;
import repositories.ChirpRepository;

@Service
@Transactional
public class ChirpService {

	// Managed repository

	@Autowired
	private ChirpRepository chirpRepository;

	// Supporting services

	@Autowired
	private UserService userService;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private Validator validator;
	// Constructors

	public ChirpService() {
		super();
	}

	// Simple CRUD methods

	public Chirp create() {

		Chirp res = new Chirp();

		Date publicationMoment;
		User user = userService.findByPrincipal();
		Assert.notNull(user);

		publicationMoment = new Date(System.currentTimeMillis() - 1);

		res.setPublicationMoment(publicationMoment);
		res.setUser(user);

		return res;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> res;
		res = this.chirpRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Chirp findOne(final int chirpId) {
		Assert.isTrue(chirpId != 0);
		Chirp res;
		res = this.chirpRepository.findOne(chirpId);
		Assert.notNull(res);
		return res;
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp);
		Chirp res;

		final Date publicationMoment;
		final User user;
		Collection<Chirp> chirps;

		user = this.userService.findByPrincipal();
		publicationMoment = new Date(System.currentTimeMillis() - 1);
		chirps = user.getChirps();

		chirp.setPublicationMoment(publicationMoment);
		chirp.setUser(user);
		res = this.chirpRepository.save(chirp);
		chirps.add(res);
		user.setChirps(chirps);

		return res;
	}

	public void delete(final Chirp chirp) {
		Assert.isTrue(this.adminService.checkAuthority());
		final User u = chirp.getUser();
		final Collection<Chirp> chirps = u.getChirps();
		chirps.remove(chirp);
		u.setChirps(chirps);

		this.chirpRepository.delete(chirp);
	}

	// Other business methods

	public Collection<Chirp> findChirpsByFollowedFromUser(final User u) {
		return this.chirpRepository.findChirpsByFollowedFromUserId(u.getId());
	}

	public Collection<Chirp> findChirpsByUser(final User u) {
		return this.chirpRepository.findChirpsByUserId(u.getId());
	}

	public Collection<Chirp> chirpContainTabooWord() {
		Assert.notNull(adminService.findByPrincipal());
		Collection<Chirp> res = new ArrayList<>();
		Configuration configuration;
		Collection<String> tabooWords = new ArrayList<>();
		Collection<Chirp> allChirp = new ArrayList<>();

		configuration = this.configurationService.findAll().iterator().next();
		tabooWords = Arrays.asList(configuration.getTabooWords().split(","));
		allChirp = this.findAll();

		for (Chirp chirp : allChirp) {
			for (String tabooWord : tabooWords) {
				String lowTabooWord = tabooWord.toLowerCase();
				if (chirp.getTitle().toLowerCase().contains(lowTabooWord.trim())
						|| chirp.getDescription().toLowerCase().contains(lowTabooWord.trim())) {
					if (!res.contains(chirp)) {
						res.add(chirp);
					}
				}
			}
		}
		return res;
	}

	public Chirp construct(final Chirp chirp) {

		Assert.notNull(chirp);

		Chirp chirpForm;

		chirpForm = new Chirp();

		chirpForm.setTitle(chirp.getTitle());
		chirpForm.setUser(chirp.getUser());
		chirpForm.setPublicationMoment(new Date(System.currentTimeMillis() - 10));
		chirpForm.setDescription(chirp.getDescription());

		return chirpForm;
	}

	public Chirp reconstruct(final Chirp chirpForm, final BindingResult binding) {

		Assert.notNull(chirpForm);

		Chirp chirp;

		if (chirpForm.getId() != 0) {
			chirp = this.findOne(chirpForm.getId());
		} else {
			chirp = this.create();
		}

		chirp.setDescription(chirpForm.getDescription());
		chirp.setTitle(chirpForm.getTitle());

		if (binding != null)
			this.validator.validate(chirp, binding);

		return chirp;
	}

	public void flush() {
		this.chirpRepository.flush();
	}

}
