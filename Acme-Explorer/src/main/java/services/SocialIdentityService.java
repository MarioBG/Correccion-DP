
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService				actorService;


	// Constructors -----------------------------------------------------------

	public SocialIdentityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SocialIdentity create() {
		SocialIdentity result = null;
		result = new SocialIdentity();
		return result;
	}

	public SocialIdentity findOne(final int socialIdentityId) {

		SocialIdentity result = null;
		result = this.socialIdentityRepository.findOne(socialIdentityId);
		return result;
	}

	public SocialIdentity findOneToEdit(final int socialIdentityId) {

		SocialIdentity result = null;
		result = this.socialIdentityRepository.findOne(socialIdentityId);
		this.checkByPrincipal(result);
		return result;
	}

	public Collection<SocialIdentity> findAll() {

		Collection<SocialIdentity> result = null;
		result = this.socialIdentityRepository.findAll();
		return result;
	}

	public SocialIdentity save(final SocialIdentity socialIdentity) {

		Assert.notNull(socialIdentity);

		Actor actor = null;
		SocialIdentity result = null;

		actor = this.actorService.findByPrincipal();
		result = this.socialIdentityRepository.save(socialIdentity);
		if (socialIdentity.getId() == 0) {
			actor.getSocialIdentities().add(result);
			this.actorService.save(actor);
		}

		return result;
	}

	public void delete(final SocialIdentity socialIdentity) {

		Assert.notNull(socialIdentity);
		Actor actor = null;

		actor = this.actorService.findByPrincipal();
		this.checkByPrincipal(socialIdentity);
		actor.getSocialIdentities().remove(socialIdentity);
		this.actorService.save(actor);
		this.socialIdentityRepository.delete(socialIdentity);
	}

	// Other business methods -------------------------------------------------

	public Collection<SocialIdentity> findByPrincipal() {

		Collection<SocialIdentity> result = null;
		final Actor actor = this.actorService.findByPrincipal();
		result = this.findByActor(actor);
		return result;
	}

	public Collection<SocialIdentity> findByActor(final Actor actor) {

		Assert.notNull(actor);

		Collection<SocialIdentity> result = null;
		result = this.actorService.findSocialIdentitiesByUserAccountId(actor.getUserAccount().getId());
		return result;
	}

	public void checkByPrincipal(final SocialIdentity socialIdentity) {

		Assert.notNull(socialIdentity);

		final Actor actor = this.actorService.findActorBySocialIdentityId(socialIdentity.getId());

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(actor.equals(principal));
	}

}
