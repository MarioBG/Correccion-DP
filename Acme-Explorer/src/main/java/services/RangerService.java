
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.Folder;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RangerRepository	rangerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private FolderService		folderService;


	// Constructors -----------------------------------------------------------

	public RangerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Ranger create() {

		Ranger result = null;
		result = new Ranger();

		result.setSuspicious(false);
		result.setTrips(new ArrayList<Trip>());
		result.setFolders(this.folderService.defaultFolders());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setUserAccount(this.userAccountService.create("RANGER"));
		return result;
	}

	public Ranger findOne(final int rangerId) {

		Ranger result = null;
		result = this.rangerRepository.findOne(rangerId);
		return result;
	}

	public Collection<Ranger> findAll() {

		Collection<Ranger> result = null;
		result = this.rangerRepository.findAll();
		return result;
	}

	public Ranger save(final Ranger ranger) {

		Assert.notNull(ranger);

		Ranger result = null;

		if (ranger.getId() == 0) {
			final Collection<Folder> folders = this.folderService.save(this.folderService.defaultFolders());
			ranger.setFolders(folders);
			ranger.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(ranger.getUserAccount().getPassword(), null));
			result = this.rangerRepository.save(ranger);
		} else

			result = this.rangerRepository.save(ranger);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<Double> avgMinMaxDesvTripsPerRanger() {

		Collection<Double> result = null;
		result = this.rangerRepository.avgMinMaxDesvTripsPerRanger();
		return result;
	}

	public String ratioRangersWithCurriculum() {

		String result = null;
		result = this.rangerRepository.ratioRangersWithCurriculum();
		return result;
	}

	public String ratioRangersCurriculumWithEndorserRecords() {

		String result = null;
		result = this.rangerRepository.ratioRangersCurriculumWithEndorserRecords();
		return result;
	}

	public String ratioRangersSuspicious() {

		String result = null;
		result = this.rangerRepository.ratioRangersSuspicious();
		return result;
	}

	public Ranger findByPrincipal() {

		Ranger result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Ranger findByUserAccountId(final int userAccountId) {

		Ranger result = null;
		result = this.rangerRepository.findByUserAccountId(userAccountId);
		return result;
	}

	public Curriculum findCurriculumByRangerId(final int rangerId) {
		Curriculum result = null;
		result = this.rangerRepository.findCurriculumByRangerId(rangerId);
		return result;
	}

	public Ranger findRangerByCurriculumId(final int curriculumId) {
		Ranger result = null;
		result = this.rangerRepository.findRangerByCurriculumId(curriculumId);
		return result;
	}

}
