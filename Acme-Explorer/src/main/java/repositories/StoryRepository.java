
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Explorer;
import domain.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {

	@Query("select s from Story s where s.writer = ?1")
	Collection<Story> findByExplorer(Explorer explorer);

}
