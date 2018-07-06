
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
import domain.Manager;
import domain.Stage;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StageServiceTest extends AbstractTest {

	@Autowired
	private StageService	stageService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ManagerService	managerService;


	@Test
	public void testCreate() {

		Stage stage;
		Collection<Trip> trips;
		Manager manager;

		super.authenticate("manager1");

		manager = this.managerService.findByPrincipal();
		trips = this.tripService.listTripPerManager(manager.getId());
		stage = this.stageService.create(trips.iterator().next().getId());

		stage.setPrice(0.0);

		Assert.isNull(stage.getDescription());
		Assert.notNull(stage.getPrice());
		Assert.isNull(stage.getTitle());

		super.unauthenticate();
	}
	@Test
	public void testSave() {

		Stage stage, saved;
		Collection<Stage> stages;
		Collection<Trip> trips;
		Manager manager;

		super.authenticate("manager1");

		manager = this.managerService.findByPrincipal();
		trips = this.tripService.listTripPerManager(manager.getId());
		stage = this.stageService.create(trips.iterator().next().getId());

		stage.setPrice(0.0);
		stage.setDescription("Description test 1");
		stage.setTitle("Title test 1");

		saved = this.stageService.save(stage);
		stages = this.stageService.findAll();

		Assert.isTrue(stages.contains(saved));

		super.unauthenticate();
	}

}
