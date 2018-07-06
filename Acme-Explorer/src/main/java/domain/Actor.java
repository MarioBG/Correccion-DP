
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Actor() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;
	private String	surname;
	private String	email;
	private String	address;
	private String	phone;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
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
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Pattern(regexp = "^(\\+[1-9][0-9]{2}|\\+[1-9][0-9]|\\+[1-9])(\\s\\([1-9][0-9]{2}\\)|\\ \\([1-9][0-9]\\)|\\ \\([1-9]\\))?(\\ \\d{4,})|(\\d{4,})|(\\d+)|()$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}


	// Relationships ----------------------------------------------------------

	private Collection<SocialIdentity>	socialIdentities;
	private UserAccount					userAccount;
	private Collection<Folder>			folders;


	@Valid
	@NotNull
	@OneToMany
	public Collection<SocialIdentity> getSocialIdentities() {
		return this.socialIdentities;
	}

	public void setSocialIdentities(final Collection<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@NotEmpty
	@OneToMany
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final Collection<Folder> folder) {
		this.folders = folder;
	}

}
