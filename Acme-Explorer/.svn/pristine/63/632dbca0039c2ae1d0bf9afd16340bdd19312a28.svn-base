
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {

		super.authenticate("ranger1");

		Folder folder = null;

		folder = this.folderService.create(false, null);
		Assert.isNull(folder.getName());
		Assert.isTrue(folder.getPredefined() == false);

		Assert.isNull(folder.getParent());
		Assert.notNull(folder.getChildren());
		Assert.notNull(folder.getMessages());

		super.unauthenticate();
	}

	@Test
	public void testSave() {

		super.authenticate("ranger1");

		Folder folder = null;
		Folder savedFolder = null;
		Collection<Folder> folders = null;

		folder = this.folderService.create(false, null);
		folder.setName("Folder test");

		savedFolder = this.folderService.save(folder);
		folders = this.folderService.findByPrincipal();

		Assert.isTrue(folders.contains(savedFolder));

		super.unauthenticate();
	}

	@Test
	public void testDelete() {

		super.authenticate("ranger1");

		Folder folder = null;
		Folder savedFolder = null;
		Collection<Folder> folders = null;

		folder = this.folderService.create(false, null);
		folder.setName("Folder test");

		savedFolder = this.folderService.save(folder);
		this.folderService.delete(savedFolder);
		folders = this.folderService.findByPrincipal();

		Assert.isTrue(!folders.contains(savedFolder));

		super.unauthenticate();
	}

	@Test
	public void testFindOneToEditPositive() {

		super.authenticate("admin1");

		Folder folder = null;
		Folder savedFolder = null;
		Folder folderRetrieved = null;

		folder = this.folderService.create(false, null);
		folder.setName("Folder test");
		savedFolder = this.folderService.save(folder);
		folderRetrieved = this.folderService.findOneToEdit(savedFolder.getId());
		Assert.isTrue(savedFolder.equals(folderRetrieved));

		super.unauthenticate();
	}

	@Test
	public void testFindOneToEditNegative() {

		Folder folder, saved, folder1;

		super.authenticate("admin1");

		folder = this.folderService.create(false, null);
		folder.setName("Folder test");
		saved = this.folderService.save(folder);
		folder1 = this.folderService.findOneToEdit(saved.getId());

		super.unauthenticate();

		super.authenticate("auditor1");

		try {
			@SuppressWarnings("unused")
			final Folder folder2 = this.folderService.findOneToEdit(folder1.getId());
		} catch (final Exception e) {
			System.out.println(e);
		}

		super.unauthenticate();
	}

	@Test
	public void testFindByPrincipal() {

		super.authenticate("admin1");

		Actor actor = null;
		Collection<Folder> folders = null;

		actor = this.actorService.findByPrincipal();
		folders = this.folderService.findByPrincipal();

		for (final Folder f : folders)
			actor.getFolders().contains(f);

		super.unauthenticate();
	}
}
