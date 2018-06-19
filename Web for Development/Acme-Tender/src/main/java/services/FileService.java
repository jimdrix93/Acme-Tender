
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.validator.internal.util.privilegedactions.GetMethodFromPropertyName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Constant;
import domain.Curriculum;
import domain.File;
import domain.SubSection;
import domain.Tender;
import domain.TenderResult;
import forms.FileForm;
import repositories.FileRepository;

@Service
@Transactional
public class FileService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private FileRepository fileRepository;

	// Services
	@Autowired
	private ActorService actorService;
	@Autowired
	private TenderService tenderService;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private TenderResultService tenderResultService;
	@Autowired
	private SubSectionService subSectionService;

	@Autowired
	private Validator validator;

	// Constructor ----------------------------------------------------------
	public FileService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public File createForSubSection(final int subSectionId) {
		Actor actor = this.actorService.findByPrincipal();

		final SubSection subSection = this.subSectionService.findOne(subSectionId);
		Assert.notNull(subSection);

		if (subSection.getAdministrative() != null) {
			Assert.isTrue(subSection.getAdministrative().getId() == actor.getId());
		}

		if (subSection.getCommercial() != null) {
			Assert.isTrue(subSection.getCommercial().getId() == actor.getId());
		}

		final File file = new File();
		file.setSubSection(subSection);

		return file;
	}

	public File createForCurriculum(final int curriculumId) {
		Actor actor = this.actorService.findByPrincipal();

		Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);

		if (curriculum.getSubSection().getCommercial() != null) {
			Assert.isTrue(curriculum.getSubSection().getCommercial().getId() == actor.getId());
		}

		final File file = new File();
		file.setCurriculum(curriculum);

		return file;
	}

	public File createForTender(final int tenderId) {
		Actor actor = this.actorService.findByPrincipal();

		final Tender tender = this.tenderService.findOneToEdit(tenderId);
		Assert.isTrue(tender.getAdministrative().getId() == actor.getId());

		final File file = new File();
		file.setTender(tender);
		return file;
	}

	public File createForTenderResult(final int tenderResultId) {
		Actor actor = this.actorService.findByPrincipal();

		final TenderResult tenderResult = this.tenderResultService.findOne(tenderResultId);
		Assert.isTrue(tenderResult.getTender().getAdministrative().getId() == actor.getId());

		final File file = new File();
		file.setTenderResult(tenderResult);
		return file;
	}

	public File findOne(final int fileId) {
		File result;

		result = this.fileRepository.findOne(fileId);
		Assert.notNull(result);

		return result;
	}

	public Collection<File> findAllByTender(final int tenderId) {

		Collection<File> result;

		result = this.fileRepository.findByTender(tenderId);
		Assert.notNull(result);

		return result;
	}

	public Collection<File> findAllByCurriculum(final int curriculumId) {

		Collection<File> result;

		result = this.fileRepository.findByCurriculum(curriculumId);
		Assert.notNull(result);

		return result;
	}

	public Collection<File> findAllByTenderResult(final int tenderResultId) {

		Collection<File> result;

		result = this.fileRepository.findByTenderResult(tenderResultId);
		Assert.notNull(result);

		return result;
	}

	public Collection<File> findAllBySubSection(final int subSectionId) {

		Collection<File> result;

		result = this.fileRepository.findBySubsection(subSectionId);
		Assert.notNull(result);

		return result;
	}

	public boolean canEditFile(final File file) {

		Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(file);

		if (file.getTender() != null) {
			return file.getTender().getAdministrative().getId() == actor.getId();
		}
		if (file.getTenderResult() != null) {
			return file.getTenderResult().getTender().getAdministrative().getId() == actor.getId();
		}
		if (file.getSubSection() != null) {
			return subSectionService.canEditSubSection(file.getSubSection().getId());
		}
		if (file.getCurriculum() != null) {
			return subSectionService.canEditSubSection(file.getCurriculum().getSubSection().getId());
		}
		return false;
	}

	public boolean canViewFile(final File file) {

		Assert.notNull(file);

		if (file.getTender() != null) {
			return true;
		}
		if (file.getTenderResult() != null) {
			return true;
		}
		if (file.getSubSection() != null) {
			return subSectionService.canViewSubSection(file.getSubSection().getId());
		}
		if (file.getCurriculum() != null) {
			return subSectionService.canViewSubSection(file.getCurriculum().getSubSection().getId());
		}
		return false;
	}

	public File save(final File file) {

		if (this.canEditFile(file)) {
			final File saved = this.fileRepository.save(file);
			return saved;
		}

		return null;

	}

	public void delete(final File file) {
		if (this.canEditFile(file))
			this.fileRepository.delete(file);
	}

	public void deleteInBatch(final Collection<File> files) {
		Assert.notNull(files);
		this.fileRepository.deleteInBatch(files);

	}

	public void flush() {
		this.fileRepository.flush();

	}

	public File reconstruct(FileForm fileForm, BindingResult binding) {
		File resultFile;
		if (fileForm.getId() == 0) {
			resultFile = new File();
			switch (fileForm.getType()) {
			case Constant.FILE_CURRICULUM:
				resultFile = this.createForCurriculum(fileForm.getFk());
				break;

			case Constant.FILE_SUBSECTION:
				resultFile = this.createForSubSection(fileForm.getFk());
				break;

			case Constant.FILE_TENDER:
				resultFile = this.createForTender(fileForm.getFk());
				break;

			case Constant.FILE_TENDER_RESULT:
				resultFile = this.createForTenderResult(fileForm.getFk());
				break;
			default:
				break;
			}
			resultFile.setComment(fileForm.getComment());
			try {
				if (fileForm.getFile().isEmpty()) {
					resultFile.setName(fileForm.getName());
				} else {
					resultFile.setData(fileForm.getFile().getBytes());
					resultFile.setMimeType(fileForm.getFile().getContentType());
					Assert.isTrue(fileForm.getFile().getSize()<52428801, "file.data.size.exceeded.fail");
					resultFile.setSize(fileForm.getFile().getSize());
					resultFile.setUploadDate(new Date(new Date().getTime()-1001));
					if (fileForm.getName().isEmpty()) {
						resultFile.setName(fileForm.getFile().getOriginalFilename());
					} else {
						String originalName = fileForm.getFile().getOriginalFilename();
						String originalExtension = originalName.substring((originalName.lastIndexOf(".")==-1)?originalName.length():(originalName.lastIndexOf(".")));
						String name = fileForm.getName();
						String extension = name.substring((name.lastIndexOf(".")==-1)?name.length():(name.lastIndexOf(".")));
						extension = (originalExtension.equals(extension)) ? "" : originalExtension;
						resultFile.setName(name + extension);
					}
				}

			} catch (Throwable e) {
				this.validator.validate(resultFile, binding);
				Assert.isTrue(fileForm.getFile().getSize()<52428801, "file.data.size.exceeded.fail");
				Assert.notNull(fileForm.getFile(), "file.data.load.fail");
			}		

		} else {
			resultFile = this.findOne(fileForm.getId());
			try {
				resultFile.setComment(fileForm.getComment());
				if (fileForm.getFile().isEmpty()) {
					if (!fileForm.getName().isEmpty()) {
						String originalName = resultFile.getName();
						String originalExtension = originalName.substring((originalName.lastIndexOf(".")==-1)?originalName.length():(originalName.lastIndexOf(".")));
						String name = fileForm.getName();
						String extension = name.substring((name.lastIndexOf(".")==-1)?name.length():(name.lastIndexOf(".")));
						extension = (originalExtension.equals(extension)) ? "" : originalExtension;
						resultFile.setName(name + extension);
					}else {
						//resultFile.setName(fileForm.getName());
					}
				} else {
					resultFile.setData(fileForm.getFile().getBytes());
					resultFile.setMimeType(fileForm.getFile().getContentType());
					Assert.isTrue(fileForm.getFile().getSize()<52428801, "file.data.size.exceeded.fail");
					resultFile.setSize(fileForm.getFile().getSize());
					resultFile.setUploadDate(new Date(new Date().getTime()-1001));
					if (fileForm.getName().isEmpty()) {
						resultFile.setName(fileForm.getFile().getOriginalFilename());
					} else {
						String originalName = fileForm.getFile().getOriginalFilename();
						String originalExtension = originalName.substring(originalName.lastIndexOf("."));
						String name = fileForm.getName();
						String extension = name.substring(name.lastIndexOf("."));
						extension = (originalExtension.equals(extension)) ? "" : originalExtension;
						resultFile.setName(name + extension);
					}
				}

			} catch (Throwable e) {
				this.validator.validate(resultFile, binding);
				Assert.notNull(fileForm, "file.not.found.fail");
				Assert.notNull(fileForm.getFile(), "file.data.load.fail");
				Assert.isTrue(fileForm.getFile().getSize()<52428801, "file.data.size.exceeded.fail");
				Assert.isTrue(false, "file.data.load.fail");

			}			
		}
		this.validator.validate(resultFile, binding);
		return resultFile;
	}

}
