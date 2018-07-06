
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RangerService		rangerService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("ranger1");

		Curriculum curriculum = null;

		curriculum = this.curriculumService.create();

		Assert.notNull(curriculum.getTicker());
		Assert.notNull(curriculum.getEducationRecords());
		Assert.notNull(curriculum.getEndorserRecords());
		Assert.notNull(curriculum.getMiscellaneousRecords());
		Assert.notNull(curriculum.getProfessionalRecords());
		Assert.notNull(curriculum.getPersonalRecord());

		super.unauthenticate();
	}

	@Test
	public void testSave() {

		super.authenticate("ranger1");

		Curriculum curriculum = null;
		Curriculum saved = null;
		Curriculum curriculumRetrieved = null;
		Ranger rangerLogin = null;

		rangerLogin = this.rangerService.findByPrincipal();
		curriculum = rangerLogin.getCurriculum();

		curriculum.getPersonalRecord().setFullName("Personal Record FullName");
		curriculum.getPersonalRecord().setPhone("+34 4444");
		curriculum.getPersonalRecord().setEmail("test@gmail.com");
		curriculum.getPersonalRecord().setPhoto("http://www.photo.com/");
		curriculum.getPersonalRecord().setLink("http://www.linkedin.com/");

		saved = this.curriculumService.save(curriculum);
		curriculumRetrieved = this.curriculumService.findOne(saved.getId());

		Assert.notNull(curriculumRetrieved);

		super.unauthenticate();
	}

	@Test
	public void testDelete() {

		super.authenticate("ranger1");

		Curriculum curriculum = null;
		Curriculum curriculumRetrieved = null;
		Ranger rangerLogin = null;

		rangerLogin = this.rangerService.findByPrincipal();
		curriculum = rangerLogin.getCurriculum();
		this.curriculumService.delete(curriculum);

		curriculumRetrieved = this.curriculumService.findOne(curriculum.getId());

		Assert.isNull(curriculumRetrieved);

		super.unauthenticate();
	}

	@Test
	public void testFindByPrincipal() {

		super.authenticate("ranger1");

		Curriculum curriculum = null;
		Curriculum curriculumRanger = null;
		Ranger ranger = null;

		ranger = this.rangerService.findByPrincipal();
		curriculum = this.curriculumService.findByPrincipal();

		curriculumRanger = this.rangerService.findCurriculumByRangerId(ranger.getId());

		Assert.isTrue(curriculum.equals(curriculumRanger));

		super.authenticate(null);
	}
}
