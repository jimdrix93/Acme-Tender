
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrative;
import domain.Administrator;
import domain.Commercial;
import repositories.AdministrativeRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministrativeService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private AdministrativeRepository	administrativeRepository;

	//Services
	@Autowired
	private AdministratorService		administratorService;
	@Autowired
	private ActorService				actorService;


	// Constructor ----------------------------------------------------------
	public AdministrativeService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public Administrative create() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Administrative administrative = new Administrative();

		return administrative;
	}

	public Administrative findOne(final int administrativeId) {
		Administrative result;
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		result = this.administrativeRepository.findOne(administrativeId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Administrative> findAll() {

		Collection<Administrative> result;

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		result = this.administrativeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Administrative> simpleFindAll() {

		Collection<Administrative> result;

		result = this.administrativeRepository.findAll();

		return result;

	}

	public Administrative save(final Administrative administrative) {

		Assert.notNull(administrative);

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Administrative saved = this.administrativeRepository.save(administrative);

		return saved;
	}

	public void delete(final Administrative administrative) {
		Assert.notNull(administrative);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		this.administrativeRepository.delete(administrative);
	}

	public void flush() {
		this.administrativeRepository.flush();

	}

	public Administrative findByPrincipal() {
		Administrative result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Actor actor = this.actorService.findByUserAccount(userAccount);
		Assert.isTrue(actor instanceof Administrative);
		result = (Administrative) actor;
		Assert.notNull(result);

		return result;
	}

	public Collection<Commercial> getSubSectionAdministrativesFromOfferId(int offerId) {
		return this.administrativeRepository.getSubSectionAdministrativesFromOfferId(offerId);
	}

}
