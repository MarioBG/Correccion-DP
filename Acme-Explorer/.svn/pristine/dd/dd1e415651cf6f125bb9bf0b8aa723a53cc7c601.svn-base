
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	// Constructor ---------------------------------------------------------------------
	public Curriculum() {
		super();
	}


	// Attributes -----------------------------------------------------------------------

	private String	ticker;


	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^(\\d{2})(\\d{2})(\\d{2})\\-([A-Z]{4})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}


	// Relationships -----------------------------------------------------------------------

	private Collection<ProfessionalRecord>	professionalRecords;
	private Collection<EducationRecord>		educationRecords;
	private PersonalRecord					personalRecord;
	private Collection<MiscellaneousRecord>	miscellaneousRecords;
	private Collection<EndorserRecord>		endorserRecords;


	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalRecords() {

		return this.professionalRecords;
	}

	public void setProfessionalRecords(final Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}

	public void setEducationRecords(final Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}

	public void setEndorserRecords(final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

}
