
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Curriculum;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private RangerService					rangerService;


	// Constructors -----------------------------------------------------------

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public MiscellaneousRecord create() {

		MiscellaneousRecord result = null;

		result = new MiscellaneousRecord();

		return result;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {

		MiscellaneousRecord result = null;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {

		Collection<MiscellaneousRecord> result = null;

		result = this.miscellaneousRecordRepository.findAll();

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {

		Assert.notNull(miscellaneousRecord);

		Ranger ranger = null;
		Curriculum curriculum = null;
		MiscellaneousRecord result = null;

		ranger = this.rangerService.findByPrincipal();
		curriculum = ranger.getCurriculum();

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		if (miscellaneousRecord.getId() == 0) {
			curriculum.getMiscellaneousRecords().add(result);
			this.curriculumService.save(curriculum);
		}

		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {

		Assert.notNull(miscellaneousRecord);

		Ranger ranger = null;
		Curriculum curriculum = null;

		ranger = this.rangerService.findByPrincipal();
		curriculum = ranger.getCurriculum();

		curriculum.getMiscellaneousRecords().remove(miscellaneousRecord);
		this.curriculumService.save(curriculum);

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	// Other business methods -------------------------------------------------

}
