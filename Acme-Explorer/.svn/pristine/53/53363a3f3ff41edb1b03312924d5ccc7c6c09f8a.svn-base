
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Tag extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Tag() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	// Relationships ----------------------------------------------------------

	private Collection<HasValue>	hasValues;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "tag")
	public Collection<HasValue> getHasValues() {
		return this.hasValues;
	}

	public void setHasValues(final Collection<HasValue> hasValues) {
		this.hasValues = hasValues;
	}

}
