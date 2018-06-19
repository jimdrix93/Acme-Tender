
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubSectionEvaluationCriteriaRepository;
import domain.Commercial;
import domain.SubSection;
import domain.SubSectionEvaluationCriteria;

@Service
@Transactional
public class SubSectionEvaluationCriteriaService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SubSectionEvaluationCriteriaRepository	subSectionEvaluationCriteriaRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	CommercialService								commercialService;
	@Autowired
	SubSectionService								subSectionService;


	// Constructors -----------------------------------------------------------
	public SubSectionEvaluationCriteriaService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public SubSectionEvaluationCriteria create(final int subSectionId) {

		final Commercial commercial = this.commercialService.findByPrincipal();
		Assert.notNull(commercial);

		final SubSection subSection = this.subSectionService.findOne(subSectionId);
		Assert.notNull(subSection);
		Assert.isTrue(subSection.getCommercial().equals(commercial));

		final SubSectionEvaluationCriteria subSectionEvaluationCriteria = new SubSectionEvaluationCriteria();
		subSectionEvaluationCriteria.setSubSection(subSection);

		return subSectionEvaluationCriteria;
	}

	public Collection<SubSectionEvaluationCriteria> findAll() {
		Collection<SubSectionEvaluationCriteria> result;

		result = this.subSectionEvaluationCriteriaRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<SubSectionEvaluationCriteria> findAllWithEvaluationCriteria(final int evaluationCriteriaId) {
		Collection<SubSectionEvaluationCriteria> result;

		result = this.subSectionEvaluationCriteriaRepository.findAllWithEvaluationCriteria(evaluationCriteriaId);
		Assert.notNull(result);

		return result;
	}

	public Collection<SubSectionEvaluationCriteria> findAllBySubSection(final int subSectionId) {
		Collection<SubSectionEvaluationCriteria> result;

		result = this.subSectionEvaluationCriteriaRepository.findAllBySubSection(subSectionId);
		Assert.notNull(result);

		return result;
	}

	public SubSectionEvaluationCriteria findOne(final int subSectionEvaluationCriteriaId) {
		Assert.isTrue(subSectionEvaluationCriteriaId != 0);

		SubSectionEvaluationCriteria result;

		result = this.subSectionEvaluationCriteriaRepository.findOne(subSectionEvaluationCriteriaId);
		Assert.notNull(result);

		return result;
	}

	public SubSectionEvaluationCriteria save(final SubSectionEvaluationCriteria subSectionEvaluationCriteria) {
		Assert.notNull(subSectionEvaluationCriteria);
		final Commercial commercial = this.commercialService.findByPrincipal();
		Assert.notNull(commercial);
		Assert.isTrue(subSectionEvaluationCriteria.getSubSection().getCommercial().equals(commercial));
		SubSectionEvaluationCriteria result;

		result = this.subSectionEvaluationCriteriaRepository.save(subSectionEvaluationCriteria);

		return result;
	}

	public void delete(final SubSectionEvaluationCriteria subSectionEvaluationCriteria) {
		final Commercial commercial = this.commercialService.findByPrincipal();
		Assert.notNull(commercial);
		Assert.isTrue(subSectionEvaluationCriteria.getSubSection().getCommercial().equals(commercial));
		Assert.notNull(subSectionEvaluationCriteria);
		Assert.isTrue(subSectionEvaluationCriteria.getId() != 0);
		Assert.isTrue(this.subSectionEvaluationCriteriaRepository.exists(subSectionEvaluationCriteria.getId()));

		this.subSectionEvaluationCriteriaRepository.delete(subSectionEvaluationCriteria);
	}

	public void deleteInBatch(final Collection<SubSectionEvaluationCriteria> subSectionEvaluationCriteria) {
		Assert.notNull(subSectionEvaluationCriteria);
		this.subSectionEvaluationCriteriaRepository.deleteInBatch(subSectionEvaluationCriteria);

	}

	public Collection<SubSectionEvaluationCriteria> findByOfferAndEvaluationCriteria(final int offerId, final int evaluationCriteriaId) {
		return this.subSectionEvaluationCriteriaRepository.findByOfferAndEvaluationCriteria(offerId, evaluationCriteriaId);
	}

	public void flush() {
		this.subSectionEvaluationCriteriaRepository.flush();

	}

	// Other business methods -------------------------------------------------

}
