
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Contact extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Contact() {
		super();
	}


	// Attributes -----------------------------------------------------------------------

	private String	name;
	private String	email;
	private String	phone;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Pattern(regexp = "^(\\+[1-9][0-9]{2}|\\+[1-9][0-9]|\\+[1-9])(\\s\\([1-9][0-9]{2}\\)|\\ \\([1-9][0-9]\\)|\\ \\([1-9]\\))?(\\ \\d{4,})|(\\d{4,})|(\\d+)|()$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}


	// Relationships -----------------------------------------------------------------------

	private Explorer	explorer;


	@Valid
	@ManyToOne(optional = false)
	public Explorer getExplorer() {
		return this.explorer;
	}

	public void setExplorer(final Explorer explorer) {
		this.explorer = explorer;
	}

}
