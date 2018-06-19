
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EvaluationCriteriaTypeRepository;
import domain.Actor;
import domain.Administrative;
import domain.EvaluationCriteria;
import domain.EvaluationCriteriaType;

@Service
@Transactional
public class EvaluationCriteriaTypeService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private EvaluationCriteriaTypeRepository	evaluationCriteriaTypeRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	AdministrativeService						administrativeService;
	@Autowired
	EvaluationCriteriaService					evaluationCriteriaService;
	@Autowired
	ActorService								actorService;


	// Constructors -----------------------------------------------------------
	public EvaluationCriteriaTypeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public EvaluationCriteriaType create() {

		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(administrative);

		final EvaluationCriteriaType evaluationCriteriaType = new EvaluationCriteriaType();

		return evaluationCriteriaType;
	}

	public Collection<EvaluationCriteriaType> findAll() {
		Collection<EvaluationCriteriaType> result;

		result = this.evaluationCriteriaTypeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EvaluationCriteriaType findOne(final int evaluationCriteriaTypeId) {
		Assert.isTrue(evaluationCriteriaTypeId != 0);

		EvaluationCriteriaType result;

		result = this.evaluationCriteriaTypeRepository.findOne(evaluationCriteriaTypeId);
		Assert.notNull(result);

		return result;
	}

	public EvaluationCriteriaType save(final EvaluationCriteriaType evaluationCriteriaType) {
		Assert.notNull(evaluationCriteriaType);
		this.checkRole(evaluationCriteriaType);

		return this.evaluationCriteriaTypeRepository.save(evaluationCriteriaType);
	}

	public void delete(final EvaluationCriteriaType evaluationCriteriaType) {
		Assert.notNull(evaluationCriteriaType);
		Assert.isTrue(evaluationCriteriaType.getId() != 0);
		Assert.isTrue(this.evaluationCriteriaTypeRepository.exists(evaluationCriteriaType.getId()));

		final Collection<EvaluationCriteria> evaluationCriterias = this.evaluationCriteriaService.findAllWithType(evaluationCriteriaType.getId());

		Assert.isTrue(evaluationCriterias.size() == 0, "evaluationCriteriaType.cannot.delete.in.use");

		this.evaluationCriteriaTypeRepository.delete(evaluationCriteriaType);
	}

	public void flush() {
		this.evaluationCriteriaTypeRepository.flush();
	}

	// Other business methods -------------------------------------------------
	public void checkRole(final EvaluationCriteriaType ect) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal instanceof Administrative);
	}
}
