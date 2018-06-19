
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class CollaborationRequest extends DomainEntity {

	private Double	benefitsPercentage;
	private String	section;
	private String	requirements;
	private Integer	numberOfPages;

	private String	subSectionTitle;
	private String	subSectionDescription;
	private Date	maxAcceptanceDate;
	private Date	maxDeliveryDate;
	private Boolean	accepted;
	private String	rejectedReason;


	@Min(0)
	@Max(100)
	@NotNull
	public Double getBenefitsPercentage() {
		return this.benefitsPercentage;
	}

	public void setBenefitsPercentage(final Double benefitsPercentage) {
		this.benefitsPercentage = benefitsPercentage;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^(TECHNICAL_OFFER|ECONOMICAL_OFFER)$")
	@NotBlank
	public String getSection() {
		return this.section;
	}

	public void setSection(final String section) {
		this.section = section;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getRequirements() {
		return this.requirements;
	}

	public void setRequirements(final String requirements) {
		this.requirements = requirements;
	}

	@Min(1)
	public Integer getNumberOfPages() {
		return this.numberOfPages;
	}

	public void setNumberOfPages(final Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getSubSectionTitle() {
		return this.subSectionTitle;
	}

	public void setSubSectionTitle(final String subSectionTitle) {
		this.subSectionTitle = subSectionTitle;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getSubSectionDescription() {
		return this.subSectionDescription;
	}

	public void setSubSectionDescription(final String subSectionDescription) {
		this.subSectionDescription = subSectionDescription;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	@Future
	public Date getMaxAcceptanceDate() {
		return this.maxAcceptanceDate;
	}

	public void setMaxAcceptanceDate(final Date maxAcceptanceDate) {
		this.maxAcceptanceDate = maxAcceptanceDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	@Future
	public Date getMaxDeliveryDate() {
		return this.maxDeliveryDate;
	}

	public void setMaxDeliveryDate(final Date maxDeliveryDate) {
		this.maxDeliveryDate = maxDeliveryDate;
	}

	public Boolean getAccepted() {
		return this.accepted;
	}

	public void setAccepted(final Boolean accepted) {
		this.accepted = accepted;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getRejectedReason() {
		return this.rejectedReason;
	}

	public void setRejectedReason(final String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}


	// Relationships
	private Commercial	commercial;
	private Offer		offer;


	@Valid
	@ManyToOne(optional = false)
	@NotNull
	public Commercial getCommercial() {
		return this.commercial;
	}

	public void setCommercial(final Commercial commercial) {
		this.commercial = commercial;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Offer getOffer() {
		return this.offer;
	}

	public void setOffer(final Offer offer) {
		this.offer = offer;
	}

	
	@Transient 
	public boolean isAnswerable() {
		
		if (this.getMaxAcceptanceDate() == null)
			return false;
		
		return this.getMaxAcceptanceDate().after(new Date());
	}
	
	public void setAnswerable(final boolean answerable) {

	}	
}
