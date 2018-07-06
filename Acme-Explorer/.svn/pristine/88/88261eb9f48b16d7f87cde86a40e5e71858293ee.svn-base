
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Configuration;
import domain.Folder;
import domain.SocialIdentity;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository			actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor findOne(final int actorId) {

		Actor result = null;
		result = this.actorRepository.findOne(actorId);
		return result;
	}

	public Collection<Actor> findAll() {

		Collection<Actor> result = null;
		result = this.actorRepository.findAll();
		return result;
	}

	public Actor save(final Actor actor) {

		Actor result = null;
		actor.setPhone(this.addPrefixPhone(actor.getPhone()));

		result = this.actorRepository.save(actor);

		return result;
	}

	// Other business methods -------------------------------------------------

	public String addPrefixPhone(final String phone) {
		String result = phone;
		final Configuration confi = this.configurationService.findAll().iterator().next();
		if (result.matches("^[0-9]{4,25}$"))
			result = confi.getCountryCode() + " " + phone;
		return result;
	}

	public Actor findByPrincipal() {

		Actor result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Actor findByUserAccountId(final int userAccountId) {

		Actor result = null;
		result = this.actorRepository.findByUserAccountId(userAccountId);
		return result;
	}

	public Collection<Folder> findFoldersByUserAccountId(final int userAccountId) {
		Collection<Folder> result = null;
		result = this.actorRepository.findFoldersByUserAccountId(userAccountId);
		return result;
	}

	public Collection<SocialIdentity> findSocialIdentitiesByUserAccountId(final int userAccountId) {
		Collection<SocialIdentity> result = null;
		result = this.actorRepository.findSocialIdentitiesByUserAccountId(userAccountId);
		return result;
	}

	public Actor findActorBySocialIdentityId(final int socialIdentityId) {
		Actor result = null;
		result = this.actorRepository.findActorBySocialIdentityId(socialIdentityId);
		return result;
	}

	public Actor registerAsActor(final Actor actor) {
		Assert.notNull(actor);

		Actor result = null;
		String hash = null;
		Md5PasswordEncoder encoder;
		UserAccount userAccount;
		final UserAccount user = new UserAccount();

		user.addAuthority(actor.getUserAccount().getAuthorities().iterator().next());
		user.setUsername(actor.getUserAccount().getUsername());
		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(actor.getUserAccount().getPassword(), null);
		user.setPassword(hash);
		userAccount = this.userAccountService.save(user);
		actor.setUserAccount(userAccount);
		final Collection<Folder> folders = this.folderService.save(this.folderService.defaultFolders());
		actor.setFolders(folders);
		result = this.save(actor);

		return result;
	}

	public Actor saveUserAccountPass(final Actor actor) {
		Assert.notNull(actor);
		final Actor result = null;
		String hash = null;
		Md5PasswordEncoder encoder;
		UserAccount userAccount;
		final UserAccount user = actor.getUserAccount();

		user.setUsername(actor.getUserAccount().getUsername());
		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(actor.getUserAccount().getPassword(), null);
		user.setPassword(hash);
		userAccount = this.userAccountService.save(user);
		actor.setUserAccount(userAccount);

		return result;
	}

	public Actor findSenderByMessageId(final int messageId) {

		Actor result;

		result = this.actorRepository.findSenderByMessageId(messageId);

		return result;
	}

	public Actor findRecipientByMessageId(final int messageId) {

		Actor result;

		result = this.actorRepository.findRecipientByMessageId(messageId);

		return result;
	}

	public void editPhone(final Actor actor) {
		String aux;

		aux = this.addPrefixPhone(actor.getPhone());
		actor.setPhone(aux);
	}

}
