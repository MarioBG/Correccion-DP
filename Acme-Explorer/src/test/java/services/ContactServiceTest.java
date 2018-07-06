
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Contact;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ContactServiceTest extends AbstractTest {

	@Autowired
	private ContactService	contactService;


	@Test
	public void testCreate() {

		super.authenticate("explorer1");

		final Contact contact = new Contact();

		Assert.isNull(contact.getEmail());
		Assert.isNull(contact.getName());
		Assert.isNull(contact.getPhone());

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		this.authenticate("explorer1");

		Contact contact = null;
		Collection<Contact> contacts = null;

		contacts = new ArrayList<Contact>();
		contact = this.contactService.create();

		contact.setEmail("email@email.com");
		contact.setName("Jose");
		contact.setPhone("666666666");

		final Contact saved = this.contactService.save(contact);
		contacts = this.contactService.findAll();

		Assert.isTrue(contacts.contains(saved));
		this.unauthenticate();
	}

	@Test
	public void testDelete() {

		this.authenticate("explorer1");

		Contact contact = null;
		Collection<Contact> contacts = null;

		contacts = new ArrayList<Contact>();
		contact = this.contactService.create();

		contact.setEmail("email@email.com");
		contact.setName("Jose");
		contact.setPhone("666666666");

		this.contactService.delete(contact);
		contacts = this.contactService.findAll();

		Assert.isTrue(!contacts.contains(contact));

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {

		this.authenticate("explorer1");

		Contact contact = null;

		contact = this.contactService.findAll().iterator().next();
		final int contactId = contact.getId();

		Assert.notNull(this.contactService.findOne(contactId));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("explorer1");

		Collection<Contact> res = null;

		res = new ArrayList<Contact>();

		res = this.contactService.findAll();

		Assert.notNull(res);

		this.unauthenticate();

	}

}
