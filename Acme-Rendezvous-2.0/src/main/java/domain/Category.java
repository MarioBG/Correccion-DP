
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "categoryParent_id")
})
public class Category extends DomainEntity {

	// Constructors

	public Category() {
		super();
	}


	// Attributes

	private String	name;
	private String	description;


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


	// Relationships

	private Collection<Service>		services;
	private Collection<Category>	categories;
	private Category				categoryParent;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "category")
	public Collection<Service> getServices() {
		return this.services;
	}

	public void setServices(final Collection<Service> services) {
		this.services = services;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "categoryParent")
	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getCategoryParent() {
		return this.categoryParent;
	}

	public void setCategoryParent(final Category categoryParent) {
		this.categoryParent = categoryParent;
	}

}
