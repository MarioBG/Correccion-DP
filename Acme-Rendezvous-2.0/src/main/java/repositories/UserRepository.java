package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.userAccount.id = ?1")
	User findUserByUserAccountId(int uA);
	
	@Query("select r.attendants from Rendezvous r where r.id = ?1")
	Collection<User> findAttendsByRendezvousId(int idRendezvous);
	
	@Query("select r.organiser from Rendezvous r where r.id = ?1")
	User findOrganiserByRendezvousId(int idRendezvous);
	
}
