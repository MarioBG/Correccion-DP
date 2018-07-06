
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.LegalText;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class LegalTextServiceTest extends AbstractTest {

	// Service under test ---------------------------------------

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private TripService			tripService;


	// Tests -----------------------------------------------------

	@Test
	public void testCreate() {

		LegalText legalText;

		super.authenticate("admin1");
		legalText = this.legalTextService.create();

		Assert.notNull(legalText.getMoment());
		Assert.notNull(legalText);

		super.unauthenticate();
	}

	@Test
	public void testSave() {

		LegalText legalText, saved;
		Collection<Trip> trips;

		super.authenticate("admin1");

		legalText = this.legalTextService.create();
		legalText.setBody("LegalText Body");
		legalText.setDraft(false);
		legalText.setLaws("law230");
		legalText.setTitle("title 1");
		trips = this.tripService.findAll();
		legalText.setTrips(trips);
		saved = this.legalTextService.save(legalText);
		final Collection<LegalText> legalTexts = this.legalTextService.findAll();

		Assert.isTrue(legalTexts.contains(saved));

		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		Collection<Trip> trips;
		LegalText legalText, saved;

		super.authenticate("admin1");

		legalText = this.legalTextService.create();
		legalText.setBody("LegalText Body");
		legalText.setDraft(true);
		legalText.setLaws("law230");
		legalText.setTitle("title 1");
		trips = this.tripService.findAll();
		legalText.setTrips(trips);
		saved = this.legalTextService.save(legalText);
		this.legalTextService.delete(saved);
		final Collection<LegalText> legalTexts = this.legalTextService.findAll();

		Assert.isTrue(!legalTexts.contains(saved));

		super.unauthenticate();

	}

	@Test
	public void testFindOneToEdit() {

		LegalText legalText, saved, legalTextRetrieved;
		Collection<Trip> trips;

		this.authenticate("admin1");

		legalText = this.legalTextService.create();
		legalText.setBody("LegalText Body");
		legalText.setDraft(true);
		legalText.setLaws("law230");
		legalText.setTitle("title 1");
		trips = this.tripService.findAll();
		legalText.setTrips(trips);
		saved = this.legalTextService.save(legalText);
		legalTextRetrieved = this.legalTextService.findOneToEdit(saved.getId());

		Assert.isTrue(saved == legalTextRetrieved);

		super.unauthenticate();
	}

	@Test
	public void testFindLegalTextPerTrips() {

		super.authenticate("admin1");

		Assert.isTrue(!this.legalTextService.findLegalTextperTrips().isEmpty());

		super.unauthenticate();
	}

}
