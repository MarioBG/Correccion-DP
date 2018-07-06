
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	// Constructors

	public User() {
		super();
	}


	// Relationships

	//private Collection<Answer>		answer;
	private Collection<Rendezvous>	organisedRendezvouses;
	private Collection<Rendezvous>	rsvpdRendezvouses;


	//	@Valid
	//	@OneToMany(mappedBy = "user")
	//	public Collection<Answer> getAnswer() {
	//		return this.answer;
	//	}
	//
	//	public void setAnswer(final Collection<Answer> answer) {
	//		this.answer = answer;
	//	}

	@Valid
	@OneToMany(mappedBy = "organiser")
	public Collection<Rendezvous> getOrganisedRendezvouses() {
		return this.organisedRendezvouses;
	}

	public void setOrganisedRendezvouses(final Collection<Rendezvous> organisedRendezvouses) {
		this.organisedRendezvouses = organisedRendezvouses;
	}

	@Valid
	@ManyToMany(mappedBy = "attendants")
	public Collection<Rendezvous> getRsvpdRendezvouses() {
		return this.rsvpdRendezvouses;
	}

	public void setRsvpdRendezvouses(final Collection<Rendezvous> rsvpdRendezvouses) {
		this.rsvpdRendezvouses = rsvpdRendezvouses;
	}

}
