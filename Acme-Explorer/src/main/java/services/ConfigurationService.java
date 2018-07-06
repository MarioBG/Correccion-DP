
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ConfigurationRepository	configurationRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Configuration create() {
		final Configuration configuration = new Configuration();
		return configuration;
	}

	public Collection<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findOne(final int id) {
		return this.configurationRepository.findOne(id);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		return this.configurationRepository.save(configuration);
	}

	public void delete(final Configuration configuration) {
		this.configurationRepository.delete(configuration);
	}

	// Other business methods -------------------------------------------------

}
