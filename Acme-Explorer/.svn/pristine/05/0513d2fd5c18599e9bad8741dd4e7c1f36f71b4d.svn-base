
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalRecord extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public PersonalRecord() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	fullName;
	private String	photo;
	private String	email;
	private String	phone;
	private String	link;


	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "^(\\+[1-9][0-9]{2}|\\+[1-9][0-9]|\\+[1-9])(\\s\\([1-9][0-9]{2}\\)|\\ \\([1-9][0-9]\\)|\\ \\([1-9]\\))?(\\ \\d{4,})|(\\d{4,})|(\\d+)$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@URL
	public String getLink() {
		return this.link;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	// Relationships ----------------------------------------------------------

}
