
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Stage extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Stage() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Double	price;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}


	// Relationships ----------------------------------------------------------

	private Trip	trip;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
