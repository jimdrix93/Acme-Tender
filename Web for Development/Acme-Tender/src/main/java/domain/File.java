
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class File extends DomainEntity {

	private String			name;
	private String			comment;
	private Date			uploadDate;
	private String			mimeType;
	private byte[]			data;
	private Long			size;						//Relationships
	private Curriculum		curriculum;
	private SubSection		subSection;
	private Tender			tender;
	private TenderResult	tenderResult;


	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(final Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(final String mimeType) {
		this.mimeType = mimeType;
	}

	@NotNull
	@Column(length = 52428800)
	public byte[] getData() {
		return this.data;
	}

	public void setData(final byte[] data) {
		this.data = data;
	}
	@Valid
	@ManyToOne(optional = true)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	@Valid
	@ManyToOne(optional = true)
	public SubSection getSubSection() {
		return this.subSection;
	}

	public void setSubSection(final SubSection subSection) {
		this.subSection = subSection;
	}
	@Valid
	@ManyToOne(optional = true)
	public Tender getTender() {
		return this.tender;
	}

	public void setTender(final Tender tender) {
		this.tender = tender;
	}
	@Valid
	@ManyToOne(optional = true)
	public TenderResult getTenderResult() {
		return this.tenderResult;
	}

	public void setTenderResult(final TenderResult tenderResult) {
		this.tenderResult = tenderResult;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public Long getSize() {
		return this.size;
	}

	public void setSize(final Long size) {
		this.size = size;
	}

}
