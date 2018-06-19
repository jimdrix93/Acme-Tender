
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class EvaluationCriteria extends DomainEntity {

	private String	title;
	private String	description;
	private Integer	maxScore;


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
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Min(0)
	public Integer getMaxScore() {
		return this.maxScore;
	}

	public void setMaxScore(final Integer maxScore) {
		this.maxScore = maxScore;
	}
	
	//Relationships
	private Tender tender;
	private EvaluationCriteriaType evaluationCriteriaType;

	@Valid
	@NotNull	
	@ManyToOne(optional = false)
	public Tender getTender() {
		return tender;
	}

	public void setTender(Tender tender) {
		this.tender = tender;
	}
	
	@Valid
	@NotNull	
	@ManyToOne(optional = false)
	public EvaluationCriteriaType getEvaluationCriteriaType() {
		return evaluationCriteriaType;
	}

	public void setEvaluationCriteriaType(EvaluationCriteriaType evaluationCriteriaType) {
		this.evaluationCriteriaType = evaluationCriteriaType;
	}
	
	

}
