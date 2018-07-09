
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "languageCode")
})
public class WelcomeMessage extends DomainEntity {

	// Constructors

	public WelcomeMessage() {
		super();
	}


	// Attributes

	private String	languageCode;
	private String	content;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLanguageCode() {
		return this.languageCode;
	}

	public void setLanguageCode(final String languageCode) {
		this.languageCode = languageCode;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

}
