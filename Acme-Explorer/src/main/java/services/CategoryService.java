
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import domain.Actor;
import domain.Admin;
import domain.Category;
import domain.Trip;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository	categoryRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdminService		adminService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Category create() {

		Collection<Trip> trips = null;
		Collection<Category> children = null;
		Category res = null;

		children = new ArrayList<Category>();
		trips = new ArrayList<Trip>();

		res = new Category();

		res.setTrips(trips);
		res.setChildrenCategories(children);

		return res;
	}

	public Category findOne(final int categoryId) {

		Assert.notNull(categoryId);
		final Category res = this.categoryRepository.findOne(categoryId);
		return res;

	}

	public Collection<Category> findAll() {

		Collection<Category> result = null;

		result = this.categoryRepository.findAll();
		return result;

	}
	public Category save(final Category category) {

		Actor actor = null;
		Collection<Authority> test = null;
		String authority = null;
		Category saved = null;
		Integer parentId = null;

		Assert.notNull(category);
		Assert.isTrue(!category.getName().equals("CATEGORY"));
		Assert.isTrue(!category.getName().equals(category.getParentCategory().getName()));

		parentId = category.getParentCategory().getId();
		actor = this.actorService.findByPrincipal();
		test = actor.getUserAccount().getAuthorities();
		authority = test.toArray()[0].toString();

		Assert.isTrue(authority.equals("MANAGER") || authority.equals("ADMIN"));

		if (category.getId() == 0) {
			Assert.isTrue(!this.categoryRepository.existsThisCategoryName(category.getName(), parentId));
			saved = this.categoryRepository.save(category);
			saved.getParentCategory().getChildrenCategories().add(saved);
		} else {
			final String oldName = this.categoryRepository.findOne(category.getId()).getName();

			if (category.getName().equals(oldName)) {
				saved = this.categoryRepository.save(category);
				saved.getParentCategory().getChildrenCategories().add(saved);
			} else {
				Assert.isTrue(!this.categoryRepository.existsThisCategoryName(category.getName(), parentId));
				saved = this.categoryRepository.save(category);
				saved.getParentCategory().getChildrenCategories().add(saved);
			}
		}

		return saved;

	}
	public void delete(final Category category) {

		Assert.notNull(category);
		Assert.isTrue(!category.getName().equals("CATEGORY"));

		final Admin admin = this.adminService.findByPrincipal();

		Assert.notNull(admin);

		category.getParentCategory().getChildrenCategories().remove(category);

		for (final Trip t : category.getTrips())
			t.setCategory(null);

		for (final Category c : category.getChildrenCategories()) {
			c.setParentCategory(category.getParentCategory());
			category.getParentCategory().getChildrenCategories().add(c);
		}

		this.categoryRepository.delete(category);
	}

	// Other business methods -------------------------------------------------

	public Category findByName(final String name) {

		Category category;

		category = this.categoryRepository.findByName(name);

		return category;
	}

	public Collection<Trip> findAllTripByCategoryId(final int categoryId) {

		Admin admin = null;
		Collection<Authority> authorities = null;
		String authority = null;
		Collection<Trip> result = null;

		admin = this.adminService.findByPrincipal();
		authorities = admin.getUserAccount().getAuthorities();
		authority = authorities.toArray()[0].toString();

		Assert.isTrue((authority.equals("ADMIN")));
		Assert.notNull(categoryId);

		result = this.categoryRepository.findAllTripByCategoryId(categoryId);

		return result;

	}

	public Collection<Category> findCategoryChildrenId(final int categoryId) {

		Collection<Category> result = null;

		Assert.notNull(categoryId);

		result = this.categoryRepository.findCategoryChildrenId(categoryId);
		return result;
	}

	public Category saveDelete(final Category category) {
		Assert.notNull(category);

		return this.categoryRepository.save(category);
	}

}
