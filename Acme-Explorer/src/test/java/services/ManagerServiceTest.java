
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
import domain.Actor;
import domain.Folder;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ActorService	actorService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {

		super.authenticate("admin1");

		Manager manager = null;

		manager = this.managerService.create();

		Assert.notNull(manager.getSurvivalClasses());
		Assert.notNull(manager.getTrips());
		Assert.notNull(manager.getFolders());
		Assert.notNull(manager.getSocialIdentities());
		Assert.notNull(manager.getUserAccount());
		Assert.isNull(manager.getName());
		Assert.isNull(manager.getSurname());
		Assert.isNull(manager.getEmail());
		Assert.isNull(manager.getAddress());
		Assert.isNull(manager.getPhone());

		super.unauthenticate();
	}

	@Test
	public void testSave() {

		super.authenticate("manager1");

		Manager manager = null;
		Manager saved = null;
		Actor actor = null;
		Collection<Folder> folders = null;

		manager = this.managerService.create();
		manager.setName("Manager 1");
		manager.setSurname("Manager 1");
		manager.setEmail("manager1@acmeexplorer.com");
		manager.setAddress("Manager 1 Street");
		manager.setPhone("111111111");
		manager.getUserAccount().setUsername("manager11");
		manager.getUserAccount().setPassword("manager11");

		saved = this.managerService.save(manager);
		actor = this.actorService.findOne(saved.getId());
		folders = actor.getFolders();

		Assert.isTrue(actor.equals(saved));
		Assert.isTrue(folders.size() == 5);

		super.unauthenticate();
	}

	@Test
	public void testAvgMinAvgMinMaxDesvTripsPerManager() {

		super.authenticate("admin1");

		Collection<Double> result = null;

		result = this.managerService.avgMinAvgMinMaxDesvTripsPerManager();
		Assert.notNull(result);

		super.authenticate(null);
	}

	@Test
	public void testRatioManagersSuspicious() {

		super.authenticate("admin1");

		String result = null;

		result = this.managerService.ratioManagersSuspicious();
		Assert.notNull(result);

		super.authenticate(null);
	}

	@Test
	public void testFindByPrincipal() {

		super.authenticate("manager1");

		Manager manager = null;

		manager = this.managerService.findByPrincipal();
		Assert.isTrue(manager.getUserAccount().getUsername().equals("manager1"));

		super.authenticate(null);
	}

}
