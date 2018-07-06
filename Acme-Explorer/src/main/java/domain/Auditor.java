
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	// Constructors -----------------------------------------------------------

	public Auditor() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<Note>		notes;
	private Collection<AuditRecord>	auditRecords;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "auditor")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "auditor")
	public Collection<AuditRecord> getAuditRecords() {
		return this.auditRecords;
	}

	public void setAuditRecords(final Collection<AuditRecord> auditRecords) {
		this.auditRecords = auditRecords;
	}
}
