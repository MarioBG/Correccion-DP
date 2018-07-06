
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AuditRecord;
import domain.Auditor;

@Repository
public interface AuditRecordRepository extends JpaRepository<AuditRecord, Integer> {

	@Query("select a from AuditRecord a where a.auditor = ?1")
	Collection<AuditRecord> findByAuditor(Auditor auditor);

}
