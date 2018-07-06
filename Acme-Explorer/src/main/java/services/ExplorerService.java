
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Contact;
import domain.Explorer;
import domain.Finder;
import domain.Folder;
import domain.SocialIdentity;
import domain.Story;
import domain.SurvivalClass;

@Service
@Transactional
public class ExplorerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ExplorerRepository	explorerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private FinderService		finderService;


	// Constructors -----------------------------------------------------------

	public ExplorerService() {

		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Explorer create() {

		Explorer result = null;
		result = new Explorer();

		result.setApplications(new ArrayList<Application>());
		result.setStories(new ArrayList<Story>());
		result.setFolders(this.folderService.defaultFolders());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setSurvivalClasses(new ArrayList<SurvivalClass>());
		result.setContacts(new ArrayList<Contact>());
		result.setUserAccount(this.userAccountService.create("EXPLORER"));
		result.setFinder(this.finderService.create());

		return result;
	}

	public Explorer findOne(final int explorerId) {

		Explorer result = null;
		result = this.explorerRepository.findOne(explorerId);
		return result;
	}

	public Collection<Explorer> findAll() {

		Collection<Explorer> result = null;
		result = this.explorerRepository.findAll();
		return result;
	}

	public Explorer save(final Explorer explorer) {

		Assert.notNull(explorer);

		Explorer result = null;

		if (explorer.getId() == 0) {
			final Finder finder = this.finderService.save(this.finderService.create());
			final Collection<Folder> folders = this.folderService.save(this.folderService.defaultFolders());
			explorer.setFinder(finder);
			explorer.setFolders(folders);
			explorer.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(explorer.getUserAccount().getPassword(), null));
			result = this.explorerRepository.save(explorer);
		} else
			result = this.explorerRepository.save(explorer);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Explorer findByPrincipal() {

		Explorer result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Explorer findByUserAccountId(final int userAccountId) {

		Explorer result = null;
		result = this.explorerRepository.findByUserAccountId(userAccountId);
		return result;
	}

	public Finder findFinderByUserAccountId(final int userAccountId) {
		Finder result = null;
		result = this.explorerRepository.findFinderByUserAccountId(userAccountId);
		return result;
	}

	public Explorer findExplorerByFinderId(final int finderId) {
		Explorer result = null;
		result = this.explorerRepository.findExplorerByFinderId(finderId);
		return result;
	}

	public Collection<SurvivalClass> findAvailableSurvivalClasses(final int explorerId) {
		Collection<SurvivalClass> result = null;
		result = this.explorerRepository.findAvailableSurvivalClasses(explorerId);
		return result;
	}
}
