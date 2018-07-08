
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class ServiceForm {

	public ServiceForm() {
		super();
	}


	// Attributes

	private int		id;
	private String	name;
	private String	description;
	private String	picture;
	private boolean	cancelled;
	private int		categoryId;
	private int		managerId;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	@SafeHtml
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(final int categoryId) {
		this.categoryId = categoryId;
	}

	public int getManagerId() {
		return this.managerId;
	}

	public void setManagerId(final int managerId) {
		this.managerId = managerId;
	}
}
