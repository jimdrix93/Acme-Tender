
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrative;
import domain.Administrator;
import domain.File;
import domain.Offer;
import domain.Tender;
import domain.TenderResult;
import repositories.TenderRepository;

@Service
@Transactional
public class TenderService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private TenderRepository		tenderRepository;

	// Managed services ------------------------------------------------
	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministrativeService	administrativeService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private TenderResultService		tenderResultService;
	@Autowired
	private FileService				fileService;
	@Autowired
	private OfferService			offerService;


	// Constructor ----------------------------------------------------------
	public TenderService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public Tender create() {
		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(administrative);

		final Tender tender = new Tender();

		tender.setReference(this.generateReference());
		tender.setAdministrative(administrative);

		return tender;
	}

	public Tender save(final Tender tender) {
		Tender savedTender;

		// Restrictions dates
		Assert.isTrue(tender.getMaxPresentationDate().after(tender.getOpeningDate()), "Invalid maxPresentationDate");

		// Comprobamos que el propietario sea el creador del concurso
		this.checkPrincipal(tender);
		
		//Solo en la creación del concurso pueden editar las fechas
		//De otra manera, aseguramos que no las han cambiado por hack
		if (tender.getId() != 0) {
			Tender oldTender = this.tenderRepository.findOne(tender.getId());
			tender.setBulletinDate(oldTender.getBulletinDate());
			tender.setOpeningDate(oldTender.getOpeningDate());
			tender.setMaxPresentationDate(oldTender.getMaxPresentationDate());
		}

		savedTender = this.tenderRepository.save(tender);

		return savedTender;
	}

	public Tender findOneToEdit(final int tenderId) {
		final Tender result = this.tenderRepository.findOne(tenderId);

		Assert.notNull(result);
		this.checkPrincipal(result);

		return result;
	}

	public Tender findOne(final int tenderId) {
		Tender result;

		result = this.tenderRepository.findOne(tenderId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Tender> findAll() {

		Collection<Tender> result;

		result = this.tenderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Tender> findAllOffertable() {

		Collection<Tender> result;

		result = this.tenderRepository.findAllOffertable();
		Assert.notNull(result);

		return result;
	}

	public Collection<Tender> findTenderByKeyWord(final String word) {
		final Collection<Tender> tenders;
		if (word.isEmpty())
			tenders = this.findAll();
		else
			tenders = this.tenderRepository.findTenderByKeyword(word);

		return tenders;
	}

	public void deleteByAdmin(final Tender tender) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final TenderResult tenderResult = this.tenderResultService.findOneByTenderAnonymous(tender.getId());
		if (tenderResult != null)
			this.tenderResultService.deleteByAdmin(tenderResult);

		final Collection<File> files = this.fileService.findAllByTender(tender.getId());
		this.fileService.deleteInBatch(files);

		final Offer offer = this.offerService.findByTender(tender.getId());
		if (offer != null)
			this.offerService.deleteByAdmin(offer);

		this.tenderRepository.delete(tender);

	}

	//Other methods ---------------------------------------------------------------

	public void checkPrincipal(final Tender tender) {
		final Actor principal = this.actorService.findByPrincipal();
		Administrative administrativePrincipal = null;
		if (principal instanceof Administrative) {
			administrativePrincipal = (Administrative) principal;
			Assert.isTrue(tender.getAdministrative().equals(administrativePrincipal));
		} else
			Assert.isTrue(Boolean.TRUE, "Usuario no válido.");
	}

	/**
	 * Generate an unique reference
	 *
	 * @return the reference
	 */
	private String generateReference() {
		final SimpleDateFormat dt = new SimpleDateFormat("ddMMyyyy");
		final Random r = new Random();
		String randomLetter = "";
		String reference = "";

		while (this.checkReference(reference) || reference == "") {
			for (int i = 0; i < 2; i++)
				randomLetter += String.valueOf((char) (r.nextInt(26) + 'A'));

			reference = dt.format(new Date()).toString() + "-" + randomLetter;
		}
		return reference;
	}

	/**
	 * Check if exist a coincidence
	 *
	 * @param reference
	 * @return
	 */
	private boolean checkReference(final String reference) {
		Boolean result = false;

		if (this.tenderRepository.checkReference(reference) != 0)
			result = true;

		return result;
	}

	public Tender findOneToComment(final Integer tenderId) {

		Tender result;
		result = this.tenderRepository.findOne(tenderId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Tender> findAllByAdministrative() {
		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(administrative);
		final Collection<Tender> tenders = this.tenderRepository.findAllByAdministrative(administrative.getId());
		Assert.notNull(tenders);
		return tenders;
	}

	public Collection<Tender> findAllTenderWithTabooWords() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Collection<Tender> ret = new LinkedList<Tender>();

		final Collection<Object[]> source = this.tenderRepository.findAllTenderWithTabooWord();

		for (final Object obj[] : source) {
			final Tender t = this.tenderRepository.findOne((Integer) obj[0]);

			ret.add(t);
		}

		return ret;
	}

	public void flush() {
		this.tenderRepository.flush();

	}
}
