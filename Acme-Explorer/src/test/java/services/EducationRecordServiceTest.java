
package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	// Service under test --------------------------------------------------
	@Autowired
	private EducationRecordService	educationalRecordService;

	@Autowired
	private RangerService			rangerService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testCreate() {

		super.authenticate("ranger1");

		EducationRecord educationRecord = null;

		educationRecord = this.educationalRecordService.create();

		Assert.isNull(educationRecord.getTitle());
		Assert.isNull(educationRecord.getStartDate());
		Assert.isNull(educationRecord.getEndDate());
		Assert.isNull(educationRecord.getInstitution());
		Assert.isNull(educationRecord.getAttachment());
		Assert.isNull(educationRecord.getComments());

		super.unauthenticate();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSave() {

		super.authenticate("ranger1");

		EducationRecord educationRecord = null;
		EducationRecord saved = null;
		Ranger ranger = null;

		educationRecord = this.educationalRecordService.create();
		educationRecord.setTitle("EducationRecord test");
		educationRecord.setStartDate(new Date(2000, 10, 02, 15, 00));
		educationRecord.setEndDate(new Date(2002, 10, 02, 15, 00));
		educationRecord.setInstitution("institution");
		educationRecord.setAttachment("http://www.attachmentTest.com");

		saved = this.educationalRecordService.save(educationRecord);
		ranger = this.rangerService.findByPrincipal();
		Assert.isTrue(saved.getTitle().equals("EducationRecord test"));
		Assert.isTrue(ranger.getCurriculum().getEducationRecords().contains(saved));

		super.unauthenticate();
	}

	@Test
	public void testDelete() {

		super.authenticate("ranger1");

		Ranger ranger = null;
		EducationRecord educationRecord = null;
		Curriculum curriculum = null;

		ranger = this.rangerService.findByPrincipal();
		curriculum = ranger.getCurriculum();
		educationRecord = curriculum.getEducationRecords().iterator().next();

		this.educationalRecordService.delete(educationRecord);

		Assert.isTrue(!curriculum.getEducationRecords().contains(educationRecord));
		Assert.isTrue(!this.educationalRecordService.findAll().contains(educationRecord));
		Assert.isTrue(!ranger.getCurriculum().getEducationRecords().contains(educationRecord));

		super.unauthenticate();
	}

}
