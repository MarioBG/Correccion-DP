
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	// Constructor -------------------------------------------------------------------

	// Attributes --------------------------------------------------------------------

	private Date	moment;
	private String	remark;
	private String	reply;
	private Date	replyMoment;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(final String remark) {
		this.remark = remark;
	}

	public String getReply() {
		return this.reply;
	}
	public void setReply(final String reply) {
		this.reply = reply;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy:mm")
	public Date getReplyMoment() {
		return this.replyMoment;
	}
	public void setReplyMoment(final Date replyMoment) {
		this.replyMoment = replyMoment;
	}


	// Relationships ----------------------------------------------------------

	private Auditor	auditor;
	private Trip	trip;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Auditor getAuditor() {
		return this.auditor;
	}

	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

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
