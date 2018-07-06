
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Constructors ---------------------------------------------

	public Configuration() {
		super();
	}


	// Attributes -----------------------------------------------

	private String				banner;
	private String				englishWelcome;
	private String				spanishWelcome;
	private Collection<String>	spamWords;
	private Double				vat;
	private String				countryCode;

	private int					finderCache;
	private int					maxTripsDisplay;


	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getEnglishWelcome() {
		return this.englishWelcome;
	}

	public void setEnglishWelcome(final String englishWelcome) {
		this.englishWelcome = englishWelcome;
	}

	@NotBlank
	public String getSpanishWelcome() {
		return this.spanishWelcome;
	}

	public void setSpanishWelcome(final String spanishWelcome) {
		this.spanishWelcome = spanishWelcome;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@Digits(integer = 3, fraction = 2)
	@Range(min = 0, max = 100)
	public Double getVat() {
		return this.vat;
	}

	public void setVat(final Double vat) {
		this.vat = vat;
	}

	@NotBlank
	@Pattern(regexp = "^\\+\\d{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Range(min = 0, max = 100)
	public int getMaxTripsDisplay() {
		return this.maxTripsDisplay;
	}

	public void setMaxTripsDisplay(final int maxTripsDisplay) {
		this.maxTripsDisplay = maxTripsDisplay;
	}

	@Range(min = 60, max = 1440)
	public int getFinderCache() {
		return this.finderCache;
	}

	public void setFinderCache(final int finderCache) {
		this.finderCache = finderCache;
	}

	// Relationships ----------------------------------------------------------

}
