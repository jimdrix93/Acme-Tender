
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Constant;
import domain.Curriculum;
import domain.File;

@Service
@Transactional
public class CurriculumService {

	/*
	 * TODO
	 * restricci�n1 --> LISTAR: puede listar curr�culums a partir del id de una subsecci�n cualquier comercial o administrativo que haya creado una subsecci�n en la oferta a la que pertenece
	 * la subsecci�n que me pasan y el comercial que haya creado la oferta a la que pertenece la subsecci�n que me pasan.
	 * 
	 * restricci�n2 --> MOSTRAR: puede mostrar un curr�culum cualquier comercial o administrativo que haya creado una subsecci�n en la oferta a la que pertenece
	 * la subsecci�n a la que pertenece el curr�culum y el comercial que haya creado la oferta a la que pertenece la subsecci�n a la que pertenece el curr�culum.
	 * 
	 * restricci�n3 --> CREAR: puede crear un curr�culum el comercial que haya creado la subsecci�n a la que pertenece el curr�culum.
	 * 
	 * restricci�n4 --> EDITAR: puede editar un curr�culum el comercial que lo haya creado.
	 * 
	 * restricci�n5 --> BORRAR: puede borrar un curr�culum el comercial que haya creado la subsecci�n a la que pertenece el curr�culum.
	 */

	// Managed repository ------------------------------------------------
	@Autowired
	private CurriculumRepository	curriculumRepository;

	//Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private FileService				fileService;

	@Autowired
	private SubSectionService		subSectionService;

	@Autowired
	private CommercialService		commercialService;

	@Autowired
	private AdministrativeService	administrativeService;


	//CRUD methods

	public Curriculum create() {

		Curriculum result;

		result = new Curriculum();

		return result;

	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}

	public Curriculum findOneToEdit(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);
		this.checkPrincipal(result);

		return result;
	}

	public Collection<Curriculum> findAll() {

		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Curriculum save(final Curriculum curriculum) {

		Assert.notNull(curriculum);
		Assert.isTrue(!curriculum.getSubSection().getSection().equals(Constant.SECTION_ADMINISTRATIVE_ACREDITATION));

		if (curriculum.getId() != 0)
			this.checkPrincipal(curriculum);

		final Curriculum saved = this.curriculumRepository.save(curriculum);

		return saved;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		this.checkPrincipal(curriculum);
		this.curriculumRepository.delete(curriculum);
	}

	public void deleteInBatch(final Collection<Curriculum> curriculums) {
		Assert.notNull(curriculums);

		for (final Curriculum c : curriculums) {
			final Collection<File> files = this.fileService.findAllByCurriculum(c.getId());
			this.fileService.deleteInBatch(files);

			this.curriculumRepository.delete(c);
		}

	}

	public void flush() {
		this.curriculumRepository.flush();

	}

	//Other methods

	public Collection<Curriculum> findAllBySubSection(final int subSectionId) {
		return this.curriculumRepository.getCurriculumsFromSubSectionId(subSectionId);

	}

	public Collection<Curriculum> getCurriculumsFromSubSectionId(final int subSectionId) {
		this.checkListAndDisplay(subSectionId);
		return this.curriculumRepository.getCurriculumsFromSubSectionId(subSectionId);

	}

	public Collection<Curriculum> findAllBySubsection(final int subSectionId) {
		return this.curriculumRepository.getCurriculumsFromSubSectionId(subSectionId);

	}

	public Collection<Curriculum> getCurriculumsFromCommercialId(final int commercialId) {
		return this.curriculumRepository.getCurriculumsFromCommercialId(commercialId);
	}

	public void checkListAndDisplay(final int subSectionId) {

		Assert.isTrue(this.subSectionService.canViewSubSection(subSectionId));

		/*
		 * Actor principal;
		 * principal = this.actorService.findByPrincipal();
		 * Assert.isTrue(principal instanceof Commercial || principal instanceof Administrative);
		 * 
		 * SubSection subSection;
		 * subSection = this.subSectionService.findOne(subSectionId);
		 * Assert.isTrue(!subSection.getSection().equals(Constant.SECTION_ADMINISTRATIVE_ACREDITATION));
		 * 
		 * Collection<Actor> subSectionCreators = new ArrayList<Actor>();
		 * Collection<Commercial> subSectionCommercials = this.commercialService.getSubSectionCommercialsFromOfferId(subSection.getOffer().getId());
		 * Collection<Commercial> subSectionAdministratives = this.administrativeService.getSubSectionAdministrativesFromOfferId(subSection.getOffer().getId());
		 * subSectionCreators.addAll(subSectionCommercials);
		 * subSectionCreators.addAll(subSectionAdministratives);
		 * Assert.isTrue(subSection.getOffer().getCommercial().equals(principal) || subSectionCreators.contains(principal));
		 */
	}

	public void checkPrincipal(final Curriculum curriculum) {

		Assert.isTrue(this.subSectionService.canEditSubSection(curriculum.getSubSection().getId()));

		/*
		 * final Actor principal = this.actorService.findByPrincipal();
		 * Assert.isTrue(principal instanceof Commercial);
		 * Assert.isTrue(curriculumRepository.getCurriculumsFromCommercialId(principal.getId()).contains(curriculum));
		 */
	}

	public boolean checkLegalAge(final Date dateOfBirth) {

		boolean result = false;

		final Date d = new Date();
		final Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(d);

		final Calendar birthDate = new GregorianCalendar();
		birthDate.setTime(dateOfBirth);

		final int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
		final int currentMonth = currentDate.get(Calendar.MONTH) + 1;
		final int currentYear = currentDate.get(Calendar.YEAR);

		final int birthDay = birthDate.get(Calendar.DAY_OF_MONTH);
		final int birthMonth = birthDate.get(Calendar.MONTH) + 1;
		final int birthYear = birthDate.get(Calendar.YEAR);

		if (currentYear - birthYear > 18)
			result = true;
		else if (currentYear - birthYear == 18)
			if (currentMonth > birthMonth)
				result = true;
			else if (currentMonth == birthMonth)
				if (currentDay >= birthDay)
					result = true;

		return result;
	}

}
