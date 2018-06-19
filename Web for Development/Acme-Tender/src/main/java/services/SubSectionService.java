
package services;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubSectionRepository;
import domain.Actor;
import domain.Administrative;
import domain.AdministrativeRequest;
import domain.Administrator;
import domain.CollaborationRequest;
import domain.Commercial;
import domain.Constant;
import domain.Curriculum;
import domain.File;
import domain.Offer;
import domain.SubSection;
import domain.SubSectionEvaluationCriteria;

@Service
@Transactional
public class SubSectionService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SubSectionRepository		subSectionRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	AdministrativeService				administrativeService;
	@Autowired
	CommercialService					commercialService;
	@Autowired
	ActorService						actorService;
	@Autowired
	OfferService						offerService;
	@Autowired
	CurriculumService					curriculumService;
	@Autowired
	SubSectionEvaluationCriteriaService	subSectionEvaluationCriteriaService;
	@Autowired
	FileService							fileService;
	@Autowired
	AdministratorService				administratorService;


	// Constructors -----------------------------------------------------------
	public SubSectionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public SubSection createByCommercialPropietary(final int offerId) {

		final Commercial commercial = this.commercialService.findByPrincipal();
		Assert.notNull(commercial);

		final Offer offer = this.offerService.findOne(offerId);
		Assert.notNull(offer);
		Assert.isTrue(offer.getCommercial().getId() == commercial.getId());

		final SubSection subSection = new SubSection();
		subSection.setCommercial(commercial);
		subSection.setOffer(offer);

		return subSection;
	}

	public SubSection createByCommercialCollaborationAcceptation(final CollaborationRequest collaborationRequest) {

		final Commercial commercial = this.commercialService.findByPrincipal();
		Assert.notNull(commercial);
		Assert.isTrue(collaborationRequest.getCommercial() == commercial);

		final Offer offer = this.offerService.findOne(collaborationRequest.getOffer().getId());

		final SubSection subSection = new SubSection();
		subSection.setCommercial(commercial);
		subSection.setOffer(offer);
		subSection.setTitle(collaborationRequest.getSubSectionTitle());
		subSection.setSection(collaborationRequest.getSection());
		subSection.setBody("Pending");
		subSection.setShortDescription("Pending");
		subSection.setSubsectionOrder(99);

		return subSection;
	}

	public SubSection createByAdministrativeCollaborationAcceptation(final AdministrativeRequest administrativeRequest) {

		final Administrative administrative = this.administrativeService.findByPrincipal();
		Assert.notNull(administrative);
		Assert.isTrue(administrativeRequest.getAdministrative() == administrative);

		final Offer offer = this.offerService.findOne(administrativeRequest.getOffer().getId());

		final SubSection subSection = new SubSection();
		subSection.setAdministrative(administrative);
		subSection.setSection(Constant.SECTION_ADMINISTRATIVE_ACREDITATION);
		subSection.setOffer(offer);
		subSection.setTitle(administrativeRequest.getSubSectionTitle());
		subSection.setBody("Pending");
		subSection.setShortDescription("Pending");
		subSection.setSubsectionOrder(99);

		return subSection;
	}

	public Collection<SubSection> findAll() {
		Collection<SubSection> result;

		result = this.subSectionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<SubSection> findAllByOffer(final int offerId) {
		Collection<SubSection> result;

		result = this.subSectionRepository.findAllByOffer(offerId);
		Assert.notNull(result);

		return result;
	}

	public SubSection findOne(final int subSectionId) {
		Assert.isTrue(subSectionId != 0);

		SubSection result;

		result = this.subSectionRepository.findOne(subSectionId);
		Assert.notNull(result);

		return result;
	}

	public SubSection save(final SubSection subSection) {
		Assert.notNull(subSection);

		if (subSection.getId() != 0)
			Assert.isTrue(this.canEditSubSection(subSection.getId()));

		if (subSection.getAdministrative() != null) {
			final Administrative administrative = this.administrativeService.findByPrincipal();

			Assert.isTrue(subSection.getAdministrative().getId() == administrative.getId());
		}

		if (subSection.getCommercial() != null) {
			final Commercial commercial = this.commercialService.findByPrincipal();
			Assert.isTrue(subSection.getCommercial().getId() == commercial.getId());
		}

		SubSection result;

		subSection.setLastReviewDate(new Date(System.currentTimeMillis() - 1));
		result = this.subSectionRepository.save(subSection);

		this.subSectionRepository.flush();

		return result;
	}

	public void delete(final SubSection subSection) {
		Assert.notNull(subSection);
		Assert.isTrue(subSection.getId() != 0);
		Assert.isTrue(this.subSectionRepository.exists(subSection.getId()));
		Assert.isTrue(this.canEditSubSection(subSection.getId()));

		if (subSection.getAdministrative() != null) {
			final Administrative administrative = this.administrativeService.findByPrincipal();
			Assert.isTrue(subSection.getAdministrative().getId() == administrative.getId());
		}

		if (subSection.getCommercial() != null) {
			final Commercial commercial = this.commercialService.findByPrincipal();
			Assert.isTrue(subSection.getCommercial().getId() == commercial.getId());
		}

		//Eliminar curriculums, files y SubSectionEvaluationCriteria asociadas.
		final Collection<Curriculum> curriculums = this.curriculumService.getCurriculumsFromSubSectionId(subSection.getId());
		this.curriculumService.deleteInBatch(curriculums);

		final Collection<File> files = this.fileService.findAllBySubSection(subSection.getId());
		this.fileService.deleteInBatch(files);

		final Collection<SubSectionEvaluationCriteria> subSectionEvaluationCriterias = this.subSectionEvaluationCriteriaService.findAllBySubSection(subSection.getId());
		this.subSectionEvaluationCriteriaService.deleteInBatch(subSectionEvaluationCriterias);

		this.subSectionRepository.delete(subSection);
	}

	// Other business methods -------------------------------------------------

	//Visibilidad de una subseccion = Visibilidad de la oferta asociada
	public boolean canViewSubSection(final int subSectionId) {

		final SubSection subSection = this.subSectionRepository.findOne(subSectionId);
		return this.offerService.canViewOffer(subSection.getOffer().getId());

	}

	//Una subseccion solo puede ser editada por el administrativo o comercial que tiene asignado.
	public boolean canEditSubSection(final int subSectionId) {

		final Actor principal = this.actorService.findByPrincipal();
		final SubSection subSection = this.subSectionRepository.findOne(subSectionId);

		//Si la oferta está presentada, no puede editar la subseccion.
		if (!subSection.getOffer().isInDevelopment())
			return false;

		if (principal instanceof Commercial) {
			final Commercial commercial = this.commercialService.findByPrincipal();

			if (commercial.getId() == subSection.getCommercial().getId())
				return true;
		}

		if (principal instanceof Administrative) {
			final Administrative administrative = this.administrativeService.findByPrincipal();

			if (administrative.getId() == subSection.getAdministrative().getId())
				return true;
		}

		return false;
	}

	public Collection<SubSection> findAllSubSectionsWithTabooWord() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Collection<SubSection> ret = new LinkedList<SubSection>();

		final Collection<Object[]> source = this.subSectionRepository.findAllSubSectionsWithTabooWord();

		for (final Object obj[] : source) {
			final SubSection ss = this.subSectionRepository.findOne((Integer) obj[0]);

			ret.add(ss);
		}

		return ret;
	}

	public void deleteInBatchByAdmin(final Collection<SubSection> subSections) {

		for (final SubSection ss : subSections)
			this.deleteByAdmin(ss);

	}

	public void deleteByAdmin(final SubSection subSection) {
		Assert.notNull(subSection);
		Assert.isTrue(subSection.getId() != 0);
		Assert.isTrue(this.subSectionRepository.exists(subSection.getId()));

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		//Eliminar curriculums, files y SubSectionEvaluationCriteria asociadas.
		final Collection<Curriculum> curriculums = this.curriculumService.findAllBySubsection(subSection.getId());
		this.curriculumService.deleteInBatch(curriculums);

		final Collection<File> files = this.fileService.findAllBySubSection(subSection.getId());
		this.fileService.deleteInBatch(files);

		final Collection<SubSectionEvaluationCriteria> subSectionEvaluationCriterias = this.subSectionEvaluationCriteriaService.findAllBySubSection(subSection.getId());
		this.subSectionEvaluationCriteriaService.deleteInBatch(subSectionEvaluationCriterias);

		this.subSectionRepository.delete(subSection);
	}

	public void flush() {
		this.subSectionRepository.flush();

	}

}
