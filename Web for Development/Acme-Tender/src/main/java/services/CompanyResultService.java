
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Administrative;
import domain.CompanyResult;
import domain.Constant;
import domain.TenderResult;
import repositories.CompanyResultRepository;

@Service
@Transactional
public class CompanyResultService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private CompanyResultRepository	companyResultRepository;

	@Autowired
	private TenderResultService		tenderResultService;
	@Autowired
	private AdministrativeService	administrativeService;


	// Constructor ----------------------------------------------------------
	public CompanyResultService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public CompanyResult create(final int tenderResultId) {

		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(administrative);

		final TenderResult tenderResult = this.tenderResultService.findOne(tenderResultId);
		Assert.notNull(tenderResult);
		Assert.isTrue(tenderResult.getTender().getAdministrative().equals(administrative));

		final CompanyResult companyResult = new CompanyResult();
		companyResult.setTenderResult(tenderResult);

		return companyResult;
	}

	public CompanyResult findOne(final int companyResultId) {
		CompanyResult result;

		result = this.companyResultRepository.findOne(companyResultId);
		Assert.notNull(result);

		return result;
	}

	public Collection<CompanyResult> findAll() {

		Collection<CompanyResult> result;

		result = this.companyResultRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public CompanyResult save(final CompanyResult companyResult) {
		final CompanyResult saved;
		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(administrative);
		Assert.isTrue(companyResult.getTenderResult().getTender().getAdministrative().equals(administrative));

		this.checkPosition(companyResult);
		this.checkState(companyResult);

		saved = this.companyResultRepository.save(companyResult);

		return saved;
	}

	private void checkState(final CompanyResult companyResult) {

		final Collection<CompanyResult> companyResults = this.companyResultRepository.findAllByTenderResult(companyResult.getTenderResult().getId());

		if (companyResult.getState().equals(Constant.COMPANY_RESULT_WINNER))
			for (final CompanyResult c : companyResults)
				if (c.getId() != companyResult.getId())
					Assert.isTrue(!c.getState().equals(companyResult.getState()), "companyResult.only.winner");
	}

	private void checkPosition(final CompanyResult companyResult) {

		final Collection<CompanyResult> companyResults = this.companyResultRepository.findAllByTenderResult(companyResult.getTenderResult().getId());

		for (final CompanyResult c : companyResults)
			if (c.getId() != companyResult.getId())
				Assert.isTrue(!c.getPosition().equals(companyResult.getPosition()), "companyResult.not.repeat.position");

	}

	public void delete(final CompanyResult companyResult) {
		Assert.notNull(companyResult);
		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(administrative);
		Assert.isTrue(companyResult.getTenderResult().getTender().getAdministrative().equals(administrative));

		this.companyResultRepository.delete(companyResult);

	}

	public Collection<CompanyResult> findAllByTenderResult(final int tenderResultId) {
		final Administrative administrative = this.administrativeService.findByPrincipal();
		final TenderResult tenderResult = this.tenderResultService.findOne(tenderResultId);
		Assert.notNull(administrative);
		Assert.notNull(tenderResult);
		Assert.isTrue(tenderResult.getTender().getAdministrative().equals(administrative));

		final Collection<CompanyResult> companyResults = this.companyResultRepository.findAllByTenderResult(tenderResultId);

		return companyResults;
	}

	public Collection<CompanyResult> findAllWinnerByTenderResult(final int tenderResultId) {
		return this.companyResultRepository.findAllWinnerByTenderResult(tenderResultId);
	}

	public Collection<CompanyResult> findAllByTenderResultAnonymous(final int tenderResultId) {

		final Collection<CompanyResult> companyResults = this.companyResultRepository.findAllByTenderResult(tenderResultId);

		return companyResults;
	}

	public void deleteInBatch(final Collection<CompanyResult> companyResults) {
		Assert.notNull(companyResults);
		this.companyResultRepository.deleteInBatch(companyResults);

	}

	public void flush() {
		this.companyResultRepository.flush();
	}
}
