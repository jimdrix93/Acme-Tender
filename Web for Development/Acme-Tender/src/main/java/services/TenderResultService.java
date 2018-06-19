
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Administrative;
import domain.CompanyResult;
import domain.File;
import domain.Tender;
import domain.TenderResult;
import repositories.TenderResultRepository;

@Service
@Transactional
public class TenderResultService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private TenderResultRepository	tenderResultRepository;

	// Managed services ------------------------------------------------
	@Autowired
	private TenderService			tenderService;
	@Autowired
	private AdministrativeService	administrativeService;
	@Autowired
	private CompanyResultService	companyResultService;
	@Autowired
	private FileService				fileService;


	// Constructor ----------------------------------------------------------
	public TenderResultService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public TenderResult create(final int tenderId) {

		final Tender tender = this.tenderService.findOneToEdit(tenderId);

		final TenderResult tenderResult = new TenderResult();
		tenderResult.setTender(tender);

		return tenderResult;
	}

	public TenderResult findOne(final int tenderResultId) {
		TenderResult result;

		result = this.tenderResultRepository.findOne(tenderResultId);
		Assert.notNull(result);

		return result;
	}

	public Collection<TenderResult> findAll() {

		Collection<TenderResult> result;

		result = this.tenderResultRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public TenderResult save(final TenderResult tenderResult) {
		final TenderResult saved;
		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(administrative);
		Assert.isTrue(tenderResult.getTender().getAdministrative().equals(administrative));

		Assert.isTrue(tenderResult.getTenderDate().after(tenderResult.getTender().getMaxPresentationDate()), "tenderResult.error.tenderDate.must.be.after.tender.maxPresentationDate");

		saved = this.tenderResultRepository.save(tenderResult);

		if (saved.getTender().getTenderResult() == null) {
			Tender tender = this.tenderService.findOne(tenderResult.getTender().getId());
			tender.setTenderResult(saved);
			this.tenderService.save(tender);
		}

		return saved;
	}

	public void delete(final TenderResult tenderResult) {
		Assert.notNull(tenderResult);
		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(administrative);
		Assert.isTrue(tenderResult.getTender().getAdministrative().equals(administrative));

		final Collection<CompanyResult> companyResults = this.companyResultService.findAllByTenderResult(tenderResult.getId());
		this.companyResultService.deleteInBatch(companyResults);

		final Collection<File> files = this.fileService.findAllByTenderResult(tenderResult.getId());
		this.fileService.deleteInBatch(files);

		this.tenderResultRepository.delete(tenderResult);

	}

	public void flush() {
		this.tenderResultRepository.flush();
	}

	public Boolean hasTenderResult(final Tender tender) {
		Boolean res = false;
		final TenderResult tenderResult = this.tenderResultRepository.findOneByTender(tender.getId());
		if (tenderResult != null)
			res = true;
		return res;

	}

	public TenderResult findOneByTender(final int tenderId) {
		final Tender tender = this.tenderService.findOne(tenderId);
		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(tender);
		Assert.isTrue(administrative.equals(tender.getAdministrative()));

		final TenderResult tenderResult = this.tenderResultRepository.findOneByTender(tenderId);

		return tenderResult;
	}

	public TenderResult findOneByTenderAnonymous(final int tenderId) {

		final TenderResult tenderResult = this.tenderResultRepository.findOneByTender(tenderId);

		return tenderResult;
	}

	public void deleteByAdmin(final TenderResult tenderResult) {
		final Collection<CompanyResult> companyResults = this.companyResultService.findAllByTenderResultAnonymous(tenderResult.getId());
		this.companyResultService.deleteInBatch(companyResults);

		final Collection<File> files = this.fileService.findAllByTenderResult(tenderResult.getId());
		this.fileService.deleteInBatch(files);

		this.tenderResultRepository.delete(tenderResult);

	}
}
