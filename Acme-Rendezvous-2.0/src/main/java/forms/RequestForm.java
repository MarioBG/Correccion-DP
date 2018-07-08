
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class RequestForm {

	// Constructors -----------------------------------------------------------

	public RequestForm() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private int		id;
	private String	holder;
	private String	brand;
	private String	number;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private Integer	cvv;
	private String	comment;
	private int		rendezvousId;
	private int		serviceId;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(final String brand) {
		this.brand = brand;
	}

	@NotBlank
	@CreditCardNumber
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotNull
	@Range(min = 2018, max = 3000)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCvv() {
		return this.cvv;
	}

	public void setCvv(final Integer cvv) {
		this.cvv = cvv;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public int getRendezvousId() {
		return this.rendezvousId;
	}

	public void setRendezvousId(final int rendezvousId) {
		this.rendezvousId = rendezvousId;
	}

	public int getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(final int serviceId) {
		this.serviceId = serviceId;
	}

}
