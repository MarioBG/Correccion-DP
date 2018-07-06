
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SocialIdentity;

@Repository
public interface SocialIdentityRepository extends JpaRepository<SocialIdentity, Integer> {

	// TODO: findByActor pasado a ActorRepository como findSocialIdentitiesByUserAccountId:
	// @Query("select s from SocialIdentity s where s.actor = ?1")
	// Collection<SocialIdentity> findByActor(Actor actor);

}
