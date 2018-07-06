
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SurvivalClass;

@Repository
public interface SurvivalClassRepository extends JpaRepository<SurvivalClass, Integer> {

	@Query("select s from SurvivalClass s where s.trip.id = ?1")
	SurvivalClass findByTrip(int tripID);

	@Query("select m.survivalClasses from Manager m where m.id=?1")
	Collection<SurvivalClass> findByManager(int id);

	@Query("select case when (count(s) > 0) then true else false end from SurvivalClass s join s.explorers exp where s.id = ?1 and exp.id = ?2")
	Boolean isEnrolExplorerToSurvivalClass(Integer survivalClassId, Integer explorerId);

	@Query("select case when (count(app.applicant) > 0) then true else false end from SurvivalClass s join s.trip tr join tr.applications app where s.id=?1 and app.status = 'ACCEPTED' and app.applicant.id = ?2")
	Boolean checkEnrolExplorerToSurvivalClass(Integer survivalClassId, Integer explorerId);

	@Query("select s from SurvivalClass s join s.explorers exp where exp.id=?1")
	Collection<SurvivalClass> findByExplorer(int id);

	@Query("select s from SurvivalClass s where s.trip.id = ?1")
	Collection<SurvivalClass> findByTripId(int tripID);

	@Query("select t.survivalClasses from Trip t join t.applications a where a.status = 'ACCEPTED' and a.applicant.id = ?1 and t.endDateTrip >= current_timestamp")
	Collection<SurvivalClass> findCanEnrolByApplicantId(int explorerId);
}
