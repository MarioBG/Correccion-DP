
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;
import domain.Note;
import domain.SurvivalClass;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select avg(m.trips.size), min(m.trips.size), max(m.trips.size), sqrt(sum(m.trips.size * m.trips.size)/count(t) - (avg(m.trips.size) * avg(m.trips.size))) from Manager m, Trip t")
	Collection<Double> avgMinMaxDesvTripsPerManager();

	@Query("select concat(100*(select count(m) from Manager m where m.suspicious is true)/count(a), '%') from Manager a")
	String ratioManagersSuspicious();

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccounId(int userAccountId);

	@Query("select m.survivalClasses from Manager m where m.id = ?1")
	Collection<SurvivalClass> findSurvivalClassesByManagerId(int managerId);

	@Query("select t.notes from Trip t where t.manager.id=?1")
	Collection<Note> findNotesByManager(int managerId);

}
