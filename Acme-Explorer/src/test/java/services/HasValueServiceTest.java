
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.HasValue;
import domain.Tag;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HasValueServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private HasValueService	hasValueService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private TagService		tagService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {

		super.authenticate("manager1");

		HasValue hasValue = null;
		Trip trip = null;

		trip = this.tripService.findOne(this.tripService.findAll().iterator().next().getId());
		hasValue = this.hasValueService.create(trip);

		Assert.isNull(hasValue.getValue());

		Assert.notNull(hasValue.getTrip());
		Assert.isNull(hasValue.getTag());

		super.unauthenticate();

	}

	@Test
	public void testSave() {

		super.authenticate("manager1");

		HasValue hasValue = null;
		HasValue saved = null;
		Collection<HasValue> hasValues = null;
		Trip trip = null;
		Tag tag = null;

		trip = this.tripService.findOne(this.tripService.findAll().iterator().next().getId());

		hasValue = this.hasValueService.create(trip);
		hasValue.setValue("Value 1");

		tag = this.tagService.findOne(this.tagService.findAll().iterator().next().getId());

		hasValue.setTag(tag);

		saved = this.hasValueService.save(hasValue);
		hasValues = this.hasValueService.findAll();

		Assert.isTrue(hasValues.contains(saved));

		super.unauthenticate();

	}

	@Test
	public void testDelete() {

		super.authenticate("manager1");

		HasValue hasValue = null;
		HasValue saved = null;
		Collection<HasValue> hasValues = null;
		Trip trip = null;
		Tag tag = null;

		trip = this.tripService.findOne(this.tripService.findAll().iterator().next().getId());

		hasValue = this.hasValueService.create(trip);
		hasValue.setValue("Value 1");

		tag = this.tagService.findOne(this.tagService.findAll().iterator().next().getId());

		hasValue.setTag(tag);

		saved = this.hasValueService.save(hasValue);
		this.hasValueService.delete(saved);
		hasValues = this.hasValueService.findAll();

		Assert.isTrue(!hasValues.contains(saved));

		super.unauthenticate();

	}
}
