
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Priority;

public class MessageForm {

	// Constructor
	public MessageForm() {
		super();
	}


	// Atributes
	private int			id;
	private int			senderId;
	private Integer		recipientId;
	private int			folderId;
	private Date		moment;
	private String		subject;
	private String		body;
	private Priority	priority;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getSenderId() {
		return this.senderId;
	}

	public void setSenderId(final int senderId) {
		this.senderId = senderId;
	}

	public Integer getRecipientId() {
		return this.recipientId;
	}

	public void setRecipientId(final Integer recipientId) {
		this.recipientId = recipientId;
	}

	public int getFolderId() {
		return this.folderId;
	}

	public void setFolderId(final int folderId) {
		this.folderId = folderId;
	}

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
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotNull
	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(final Priority priority) {
		this.priority = priority;
	}

}
