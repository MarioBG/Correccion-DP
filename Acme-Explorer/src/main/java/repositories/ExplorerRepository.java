
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Explorer;
import domain.Finder;
import domain.SurvivalClass;

@Repository
public interface ExplorerRepository extends JpaRepository<Explorer, Integer> {

	@Query("select e from Explorer e where e.userAccount.id = ?1")
	Explorer findByUserAccountId(int userAccountId);

	// TODO: Antes estaba en FinderRepository. Preguntar al profesor si podemos dejarlo así.
	@Query("select e.finder from Explorer e where e.userAccount.id = ?1")
	Finder findFinderByUserAccountId(int userAccountId);

	@Query("select e from Explorer e where e.finder.id = ?1")
	Explorer findExplorerByFinderId(int finderId);

	@Query("select distinct sv from Explorer e join e.applications app join app.trip tr join tr.survivalClasses sv where e.id = ?1 and app.status = 'ACCEPTED' and sv.id not in (select srv.id from Explorer e join e.survivalClasses srv)")
	Collection<SurvivalClass> findAvailableSurvivalClasses(int explorerId);
}
