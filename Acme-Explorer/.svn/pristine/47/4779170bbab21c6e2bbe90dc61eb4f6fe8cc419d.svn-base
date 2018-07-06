
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;
import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s from Sponsorship s where s.sponsor = ?1")
	Collection<Sponsorship> findBySponsor(Sponsor sponsor);

}
