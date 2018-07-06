
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagRepository;
import security.Authority;
import domain.Admin;
import domain.HasValue;
import domain.Manager;
import domain.Tag;

@Service
@Transactional
public class TagService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TagRepository	tagRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private HasValueService	hasValueService;

	@Autowired
	private AdminService	adminService;

	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------

	public TagService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Tag create() {

		Admin admin = null;

		admin = this.adminService.findByPrincipal();
		final Collection<Authority> authorities = admin.getUserAccount().getAuthorities();
		final String authority = authorities.toArray()[0].toString();

		Assert.isTrue((authority.equals("ADMIN")));

		final Tag res = new Tag();

		Collection<HasValue> hasValues;
		hasValues = new ArrayList<HasValue>();
		res.setHasValues(hasValues);

		return res;
	}

	public Tag save(final Tag tag) {
		Admin admin = null;

		admin = this.adminService.findByPrincipal();
		final Collection<Authority> authorities = admin.getUserAccount().getAuthorities();
		final String authority = authorities.toArray()[0].toString();

		Assert.isTrue((authority.equals("ADMIN")));
		Assert.notNull(tag);

		final Tag res = this.tagRepository.save(tag);
		return res;

	}

	public Tag findOne(final int tagId) {

		Assert.notNull(tagId);
		Assert.isTrue(this.checkFindTagPerManager() || this.checkFindTagPerAdmin());

		Tag res = null;

		res = this.tagRepository.findOne(tagId);

		return res;
	}

	public boolean checkFindTagPerManager() {

		boolean res = false;
		Manager manager = null;

		manager = this.managerService.findByPrincipal();
		if (manager != null) {
			final Collection<Authority> authorities = manager.getUserAccount().getAuthorities();
			final String authority = authorities.toArray()[0].toString();
			if (authority.equals("MANAGER"))
				res = true;
		}

		return res;
	}

	public boolean checkFindTagPerAdmin() {

		boolean res = false;
		Admin admin = null;

		admin = this.adminService.findByPrincipal();
		if (admin != null) {
			final Collection<Authority> authorities = admin.getUserAccount().getAuthorities();
			final String authority = authorities.toArray()[0].toString();
			if (authority.equals("ADMIN"))
				res = true;
		}

		return res;
	}

	public Collection<Tag> findAll() {

		final Collection<Tag> res = this.tagRepository.findAll();
		return res;

	}

	public void delete(final Tag tag) {
		Admin admin = null;

		admin = this.adminService.findByPrincipal();
		final Collection<Authority> authorities = admin.getUserAccount().getAuthorities();
		final String authority = authorities.toArray()[0].toString();

		Assert.isTrue((authority.equals("ADMIN")));

		this.hasValueService.deleteByTag(tag);
		this.tagRepository.delete(tag);
	}
	// Other business methods -------------------------------------------------

	public Tag findToEdit(final Tag tag) {

		Admin admin = null;
		Tag res = null;

		admin = this.adminService.findByPrincipal();
		final Collection<Authority> authorities = admin.getUserAccount().getAuthorities();
		final String authority = authorities.toArray()[0].toString();

		Assert.isTrue((authority.equals("ADMIN")));
		Assert.notNull(tag);
		Assert.isTrue(tag.getHasValues().isEmpty());

		res = this.save(tag);

		return res;
	}
}
