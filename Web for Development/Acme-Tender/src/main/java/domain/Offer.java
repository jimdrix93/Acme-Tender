
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "state")
})
public class Offer extends DomainEntity {

	private String	state;
	private String	denialReason;
	private Date	presentationDate;
	private double	amount;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^(" + Constant.OFFER_CREATED + "|" + Constant.OFFER_IN_DEVELOPMENT + "|" + Constant.OFFER_ABANDONED + "|" + Constant.OFFER_PRESENTED + "|" + Constant.OFFER_WINNED + "|" + Constant.OFFER_LOSED + "|" + Constant.OFFER_CHALLENGED + "|"
		+ Constant.OFFER_DENIED + ")$")
	@NotBlank
	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDenialReason() {
		return this.denialReason;
	}

	public void setDenialReason(final String denialReason) {
		this.denialReason = denialReason;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPresentationDate() {
		return this.presentationDate;
	}

	public void setPresentationDate(final Date presentationDate) {
		this.presentationDate = presentationDate;
	}

	@Min(0)
	public double getAmount() {
		return this.amount;
	}

	public void setAmount(final double amount) {
		this.amount = amount;
	}


	//Relationships
	private Commercial	commercial;
	private Tender		tender;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Commercial getCommercial() {
		return this.commercial;
	}

	public void setCommercial(final Commercial commercial) {
		this.commercial = commercial;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Tender getTender() {
		return this.tender;
	}

	public void setTender(final Tender tender) {
		this.tender = tender;
	}


	@Transient
	public boolean isInDevelopment() {
		if (this.getState() == null)
			return false;
		
		return this.getState().equals(Constant.OFFER_IN_DEVELOPMENT) || this.getState().equals(Constant.OFFER_CREATED);
	}
	
	public void setInDevelopment(final boolean inDevelopment) {

	}	
	
	@Transient
	public boolean isPublished() {
		if (this.getState() == null)
			return false;
					
		switch (this.getState()) {
		case Constant.OFFER_PRESENTED:
		case Constant.OFFER_WINNED:
		case Constant.OFFER_LOSED:
		case Constant.OFFER_CHALLENGED:
			return true;

		case Constant.OFFER_CREATED:
		case Constant.OFFER_IN_DEVELOPMENT:
		case Constant.OFFER_ABANDONED:
		case Constant.OFFER_DENIED:				
			return false;
		}

		return true;
	}

	public void setPublished(final boolean published) {

	}

}
