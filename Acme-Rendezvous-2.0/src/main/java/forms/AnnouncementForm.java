
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class AnnouncementForm {

	// Constructor 

	public AnnouncementForm() {
		super();
	}


	// Attributes

	private int		id;
	private String	title;
	private String	description;
	private int		rendezvousId;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public int getRendezvousId() {
		return this.rendezvousId;
	}

	public void setRendezvousId(final int rendezvousId) {
		this.rendezvousId = rendezvousId;
	}

}
