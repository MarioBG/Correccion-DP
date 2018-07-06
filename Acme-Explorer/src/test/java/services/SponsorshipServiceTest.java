
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
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// Service under test --------------------------------------------------------
	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private TripService			tripService;


	@Test
	public void testCreate() {
		super.authenticate("sponsor1");
		final Sponsorship sponsorship = this.sponsorshipService.create();
		Assert.isNull(sponsorship.getBanner());
		Assert.isNull(sponsorship.getCreditCard());
		Assert.isNull(sponsorship.getInformation());
	}

	@Test
	public void testSave() {

		Sponsorship saved;

		super.authenticate("sponsor1");

		final Sponsorship sponsorship = this.sponsorshipService.create();
		sponsorship.setBanner("http://www.banner1.com");
		final CreditCard creditCard = new CreditCard();
		final Trip trip = this.tripService.findOne(this.tripService.findAll().iterator()// Obtenemos el trip autenticado de la base de datos
			.next().getId());

		creditCard.setBrand("VISA");
		creditCard.setCvv(773);
		creditCard.setExpirationMonth(7);
		creditCard.setExpirationYear(2019);
		creditCard.setHolder("Name creditCard2");
		creditCard.setNumber("4961155805327500");
		sponsorship.setCreditCard(creditCard);
		sponsorship.setInformation("http://www.information1.com");
		sponsorship.setTrip(trip);

		saved = this.sponsorshipService.save(sponsorship);
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAll();

		Assert.isTrue(sponsorships.contains(saved));

		super.unauthenticate();
	}

	@Test
	public void testDelete() {

		Sponsorship saved;

		super.authenticate("sponsor1");

		final Sponsorship sponsorship = this.sponsorshipService.create();
		sponsorship.setBanner("http://www.banner1.com");
		final CreditCard creditCard = new CreditCard();
		final Trip trip = this.tripService.findOne(this.tripService.findAll().iterator()
			.next().getId());

		creditCard.setBrand("VISA");
		creditCard.setCvv(773);
		creditCard.setExpirationMonth(7);
		creditCard.setExpirationYear(2019);
		creditCard.setHolder("Name creditCard2");
		creditCard.setNumber("4961155805327500");
		sponsorship.setCreditCard(creditCard);
		sponsorship.setInformation("http://www.information1.com");
		sponsorship.setTrip(trip);

		saved = this.sponsorshipService.save(sponsorship);
		this.sponsorshipService.delete(saved);

		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAll();

		Assert.isTrue(!sponsorships.contains(saved));

		super.unauthenticate();

	}

	@Test
	public void testFindBySponsor() {

		super.authenticate("sponsor1");

		final Sponsor sponsor = this.sponsorService.findByPrincipal();

		Assert.isTrue(!this.sponsorshipService.findBySponsor(sponsor).isEmpty());

		super.unauthenticate();
	}

}
