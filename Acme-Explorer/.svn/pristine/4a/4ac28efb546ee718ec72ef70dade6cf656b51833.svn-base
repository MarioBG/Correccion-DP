
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Sponsorship() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String		banner;
	private String		information;
	private CreditCard	creditCard;


	@NotNull
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotNull
	@URL
	public String getInformation() {
		return this.information;

	}
	public void setInformation(final String information) {
		this.information = information;
	}

	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	// Relationships ----------------------------------------------------------

	private Sponsor	sponsor;
	private Trip	trip;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}
	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@Valid
	@ManyToOne(optional = true)
	public Trip getTrip() {
		return this.trip;
	}
	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
