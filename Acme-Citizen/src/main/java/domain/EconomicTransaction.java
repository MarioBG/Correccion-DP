
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "debtor_id, creditor_id")
})
public class EconomicTransaction extends DomainEntity {

	public EconomicTransaction() {

	}


	private double	quantity;
	private Date	transactionMoment;
	private String	concept;
	private Boolean	doMoney;


	@Digits(fraction = 2, integer = 12)
	@Min(0)
	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final double quantity) {
		this.quantity = quantity;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getTransactionMoment() {
		return this.transactionMoment;
	}

	public void setTransactionMoment(final Date transactionMoment) {
		this.transactionMoment = transactionMoment;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getConcept() {
		return this.concept;
	}

	public void setConcept(final String concept) {
		this.concept = concept;
	}

	public Boolean getDoMoney() {
		return this.doMoney;
	}

	public void setDoMoney(final Boolean doMoney) {
		this.doMoney = doMoney;
	}


	// Relationships

	private BankAccount	creditor;
	private BankAccount	debtor;


	@ManyToOne(optional = true)
	@Valid
	@JoinColumn(name = "creditor_id", nullable = true)
	public BankAccount getCreditor() {
		return this.creditor;
	}

	public void setCreditor(final BankAccount creditor) {
		this.creditor = creditor;
	}

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "debtor_id", nullable = true)
	public BankAccount getDebtor() {
		return this.debtor;
	}

	public void setDebtor(final BankAccount debtor) {
		this.debtor = debtor;
	}

}
