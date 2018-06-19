
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Commercial;
import repositories.CommercialRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class CommercialService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private CommercialRepository	commercialRepository;

	//Services
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ActorService			actorService;


	// Constructor ----------------------------------------------------------
	public CommercialService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public Commercial create() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Commercial commercial = new Commercial();

		return commercial;
	}

	public Commercial findOne(final int commercialId) {
		Commercial result;
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		result = this.commercialRepository.findOne(commercialId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Commercial> findAll() {

		Collection<Commercial> result;

		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.commercialRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Commercial save(final Commercial commercial) {

		Assert.notNull(commercial);

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Commercial saved = this.commercialRepository.save(commercial);

		return saved;
	}

	public void delete(final Commercial commercial) {
		Assert.notNull(commercial);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		this.commercialRepository.delete(commercial);
	}

	public void flush() {
		this.commercialRepository.flush();

	}

	public Collection<Commercial> getSubSectionCommercialsFromOfferId(final int offerId) {

		return this.commercialRepository.getSubSectionCommercialsFromOfferId(offerId);

	}

	public Commercial findByPrincipal() {
		Commercial result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Actor actor = this.actorService.findByUserAccount(userAccount);
		Assert.isTrue(actor instanceof Commercial);
		result = (Commercial) actor;
		Assert.notNull(result);

		return result;
	}
}
