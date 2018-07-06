package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rendezvous;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous, Integer> {

	@Query("select r from Rendezvous r where r.moment > current_timestamp")
	Collection<Rendezvous> findFutureMoment();
	
	@Query("select r from Rendezvous r where r.adult = false and r.moment > current_timestamp")
	Collection<Rendezvous> findFutureMomentAndNotAdult();
	
	@Query("select l from Rendezvous r join r.linkedRendezvouses l where r.id = ?1 and l.moment > current_timestamp")
	Collection<Rendezvous> linkedRendezvousesFutureMomentByRendezvousId(int rendezvousId);
	
	@Query("select l from Rendezvous r join r.linkedRendezvouses l where r.id = ?1 and l.adult = false and l.moment > current_timestamp")
	Collection<Rendezvous> linkedRendezvousesFutureMomentAndNotAdultByRendezvousId(int rendezvousId);

	@Query("select u.organisedRendezvouses from User u where u.id = ?1")
	Collection<Rendezvous> findByOrganiserId(int organiserId);
	
	@Query("select r from User u join u.organisedRendezvouses r where u.id = ?1 and r.adult = false")
	Collection<Rendezvous> findByOrganiserIdNotAdult(int organiserId);
	
	@Query("select u.rsvpdRendezvouses from User u where u.id = ?1")
	Collection<Rendezvous> findByAttendantId(int attendantId);
	
	@Query("select r from User u join u.rsvpdRendezvouses r where u.id = ?1 and r.adult = false")
	Collection<Rendezvous> findByAttendantIdNotAdult(int attendantId);
	
	@Query("select r from Rendezvous r join r.linkedRendezvouses l where l.id=?1")
	Collection<Rendezvous> findParentRendezvouses(int rendezvousId);

	@Query("select r.rendezvous from Request r join r.service.category c where c.id=?1")
	Collection<Rendezvous> rendezvousGroupedByCategory(int categoryId);
	

}
