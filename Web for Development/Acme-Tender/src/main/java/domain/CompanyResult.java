
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class CompanyResult extends DomainEntity {

	private String	name;
	private Double	economicalOffer;
	private Double	score;
	private Integer	position;
	private String	comments;
	private String	state;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Min(0)
	@NotNull
	public Double getEconomicalOffer() {
		return this.economicalOffer;
	}

	public void setEconomicalOffer(final Double economicalOffer) {
		this.economicalOffer = economicalOffer;
	}

	@Min(0)
	@NotNull	
	public Double getScore() {
		return this.score;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

	@Min(1)
	@NotNull	
	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(final Integer position) {
		this.position = position;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^(" + Constant.COMPANY_RESULT_WINNER + "|" +  Constant.COMPANY_RESULT_LOSER + "|" +  Constant.COMPANY_RESULT_RECKLESS_OFFER + ")$")
	@NotBlank
	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}
	
	//Relationships
	
	private TenderResult tenderResult;

	@Valid
	@NotNull	
	@ManyToOne(optional = false)
	public TenderResult getTenderResult() {
		return tenderResult;
	}

	public void setTenderResult(TenderResult tenderResult) {
		this.tenderResult = tenderResult;
	}
	
	

}
