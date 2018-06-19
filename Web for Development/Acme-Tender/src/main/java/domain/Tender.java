
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "title, expedient, interestComment, informationPage")
})
public class Tender extends DomainEntity {

	private String	reference;
	private String	title;
	private String	expedient;
	private double	estimatedAmount;
	private String	organism;
	private String	bulletin;
	private Date	bulletinDate;
	private Date	openingDate;
	private Date	maxPresentationDate;
	private Integer	executionTime;
	private String	observations;
	private String	informationPage;
	private String	interest;
	private String	interestComment;


	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^\\d{8}-[A-Z]{2}$")
	public String getReference() {
		return this.reference;
	}

	public void setReference(final String reference) {
		this.reference = reference;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getExpedient() {
		return this.expedient;
	}

	public void setExpedient(final String expedient) {
		this.expedient = expedient;
	}

	@DecimalMin("0.0")
	public double getEstimatedAmount() {
		return this.estimatedAmount;
	}

	public void setEstimatedAmount(final double estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getOrganism() {
		return this.organism;
	}

	public void setOrganism(final String organism) {
		this.organism = organism;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getBulletin() {
		return this.bulletin;
	}

	public void setBulletin(final String bulletin) {
		this.bulletin = bulletin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getBulletinDate() {
		return this.bulletinDate;
	}

	public void setBulletinDate(final Date bulletinDate) {
		this.bulletinDate = bulletinDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getOpeningDate() {
		return this.openingDate;
	}

	public void setOpeningDate(final Date openingDate) {
		this.openingDate = openingDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getMaxPresentationDate() {
		return this.maxPresentationDate;
	}

	public void setMaxPresentationDate(final Date maxPresentationDate) {
		this.maxPresentationDate = maxPresentationDate;
	}

	@Min(0)
	public Integer getExecutionTime() {
		return this.executionTime;
	}

	public void setExecutionTime(final Integer executionTime) {
		this.executionTime = executionTime;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getObservations() {
		return this.observations;
	}

	public void setObservations(final String observations) {
		this.observations = observations;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@URL
	public String getInformationPage() {
		return this.informationPage;
	}

	public void setInformationPage(final String informationPage) {
		this.informationPage = informationPage;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^(" + Constant.TENDER_INTEREST_UNDEFINED + "|" + Constant.TENDER_INTEREST_LOW + "|" + Constant.TENDER_INTEREST_MEDIUM + "|" + Constant.TENDER_INTEREST_HIGH + ")$")
	@NotBlank
	public String getInterest() {
		return this.interest;
	}

	public void setInterest(final String interest) {
		this.interest = interest;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getInterestComment() {
		return this.interestComment;
	}

	public void setInterestComment(final String interestComment) {
		this.interestComment = interestComment;
	}


	//Relationships
	private Administrative	administrative;
	private Offer			offer;
	private Category		category;
	private TenderResult 	tenderResult;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Administrative getAdministrative() {
		return this.administrative;
	}

	public void setAdministrative(final Administrative administrative) {
		this.administrative = administrative;
	}

	@Valid
	@OneToOne(optional = true)
	public Offer getOffer() {
		return this.offer;
	}

	public void setOffer(final Offer offer) {
		this.offer = offer;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}
	
	@Valid
	@OneToOne(optional = true)
	public TenderResult getTenderResult() {
		return tenderResult;
	}

	public void setTenderResult(TenderResult tenderResult) {
		this.tenderResult = tenderResult;
	}

	@Transient
	public boolean isOffertable() {
		
		if (this.getMaxPresentationDate() == null)
			return false;
		
		Date date = new Date();
		return this.getOffer() == null && this.getMaxPresentationDate().after(date);
	}

	public void setOffertable(final boolean offertable) {

	}

}
