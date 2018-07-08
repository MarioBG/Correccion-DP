
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

public class RendezvousForm {

	// Constructors -----------------------------------------------------------

	public RendezvousForm() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private int		id;
	private String	name;
	private String	description;
	private String	picture;
	private Date	moment;
	private Double	latitude;
	private Double	longitude;
	private boolean	adult;
	private boolean	finalVersion;
	private boolean	deleted;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Valid
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	@Valid
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

	public boolean getFinalVersion() {
		return this.finalVersion;
	}

	public void setFinalVersion(final boolean finalVersion) {
		this.finalVersion = finalVersion;
	}

	public boolean getAdult() {
		return this.adult;
	}

	public void setAdult(final boolean adult) {
		this.adult = adult;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}

}
