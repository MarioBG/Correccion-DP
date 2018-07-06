
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class GPSCoordinates {

	// Constructors -----------------------------------------------------------

	public GPSCoordinates() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;
	private double	longitude;
	private double	latitude;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Digits(integer = 3, fraction = 5)
	@Range(min = (long) -180.0, max = (long) 180.0)
	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	@Digits(integer = 2, fraction = 5)
	@Range(min = (long) -90.0, max = (long) 90.0)
	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	// Relationships ----------------------------------------------------------

}
