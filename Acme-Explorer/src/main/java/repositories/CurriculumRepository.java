
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	// TODO: findByRangerId pasado a RangerRepository como findCurriculumByRangerId
	//@Query("select c from Curriculum c where c.ranger.id = ?1")
	//Curriculum findByRangerId(int rangerId);

	@Query("select c from Curriculum c where c.ticker = ?1")
	Curriculum findByTicker(String tickerName);
}
