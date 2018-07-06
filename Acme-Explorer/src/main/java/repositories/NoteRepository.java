
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auditor;
import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select n from Note n where n.auditor = ?1")
	Collection<Note> findByAuditor(Auditor auditor);

}
