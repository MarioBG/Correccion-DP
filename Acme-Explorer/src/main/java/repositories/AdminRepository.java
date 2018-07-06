
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;
import domain.Manager;
import domain.Ranger;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select a from Admin a where a.userAccount.id = ?1")
	Admin findByUserAccountId(int userAccountId);

	@Query("select m from Manager m where (m.suspicious= true)")
	Collection<Manager> findSuspiciousManagers();

	@Query("select r from Ranger r where (r.suspicious= true)")
	Collection<Ranger> findSuspiciousRangers();
}
