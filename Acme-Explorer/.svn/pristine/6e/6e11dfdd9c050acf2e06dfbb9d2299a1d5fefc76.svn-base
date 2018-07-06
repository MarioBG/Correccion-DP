
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	// Service under test ----------------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;


	// Tests ------------------------------------------------------------------------

	@Test
	public void testCreate() {

		Configuration configuration;

		this.authenticate("admin1");

		configuration = this.configurationService.create();

		Assert.isNull(configuration.getBanner());
		Assert.isNull(configuration.getCountryCode());
		Assert.isNull(configuration.getEnglishWelcome());
		Assert.notNull(configuration.getFinderCache());
		Assert.notNull(configuration.getMaxTripsDisplay());
		Assert.isNull(configuration.getSpamWords());
		Assert.isNull(configuration.getSpanishWelcome());
		Assert.isNull(configuration.getVat());

		super.unauthenticate();
	}
	@Test
	public void testSave() {

		Configuration configuration, saved;
		final String spamWord = "call now";
		final Collection<String> spamWords = new ArrayList<String>();
		spamWords.add(spamWord);

		super.authenticate("admin1");

		configuration = this.configurationService.create();
		configuration.setBanner("");
		configuration.setCountryCode("");
		configuration.setEnglishWelcome("");
		configuration.setFinderCache(0);
		configuration.setMaxTripsDisplay(1);
		configuration.setSpamWords(spamWords);
		configuration.setSpanishWelcome("Hola");
		configuration.setVat(0.21);

		saved = this.configurationService.save(configuration);
		final Integer id = saved.getId();

		Assert.isTrue(this.configurationService.findOne(id).equals(saved));

		super.unauthenticate();
	}

	@Test
	public void testDelete() {

		Configuration configuration, saved, configurationRetrieved;
		final String spamWord = "call now";
		final Collection<String> spamWords = new ArrayList<String>();
		spamWords.add(spamWord);

		super.authenticate("admin1");

		configuration = this.configurationService.create();
		configuration.setBanner("");
		configuration.setCountryCode("");
		configuration.setEnglishWelcome("");
		configuration.setFinderCache(0);
		configuration.setMaxTripsDisplay(1);
		configuration.setSpamWords(spamWords);
		configuration.setSpanishWelcome("Hola");
		configuration.setVat(0.21);

		saved = this.configurationService.save(configuration);
		final Integer id = saved.getId();
		this.configurationService.delete(saved);
		configurationRetrieved = this.configurationService.findOne(id);

		Assert.isNull(configurationRetrieved);

		super.unauthenticate();

	}

}
