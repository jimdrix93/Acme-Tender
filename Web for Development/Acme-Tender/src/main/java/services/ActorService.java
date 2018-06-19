
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Administrative;
import domain.Administrator;
import domain.Commercial;
import domain.Executive;
import forms.RegisterForm;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActorRepository actorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private FolderService folderService;

	@Autowired
	private Validator validator;

	// Constructors -----------------------------------------------------------
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findAllActivated() {
		Collection<Actor> result;

		result = this.actorRepository.findAllActivated();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor result;
		if (actor.getId() != 0)
			Assert.isTrue(actor.equals(this.findByPrincipal()), "not.allowed.action");

		result = this.actorRepository.save(actor);

		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));
		Assert.isTrue(actor.equals(this.findByPrincipal()));

		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public void ActivateOrDeactivate(final Integer id) {
		final Actor res = this.actorRepository.findOne(id);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Assert.notNull(res);
		res.getUserAccount().setActive(!res.getUserAccount().getActive());
		this.actorRepository.save(res);
	}

	public UserAccount findUserAccount(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = actor.getUserAccount();

		return result;
	}

	public Actor findByPrincipal() {
		Actor result = null;
		UserAccount userAccount;

		try {
			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);
			result = this.findByUserAccount(userAccount);
			Assert.notNull(result);

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public String getType(final UserAccount userAccount) {

		final List<Authority> authorities = new ArrayList<Authority>(userAccount.getAuthorities());

		return authorities.get(0).getAuthority();
	}

	public Actor reconstruct(final RegisterForm actorForm, final BindingResult binding) {
		Actor logedActor = null;
		UserAccount useraccount = null;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (actorForm.getId() == 0) {
			actorForm.setNewPassword(actorForm.getPassword());
			useraccount = new UserAccount();
			switch (actorForm.getAuthority()) {
			case Authority.ADMINISTRATIVE:
				logedActor = new Administrative();
				useraccount = this.userAccountService.createAsAdministrative();
				logedActor.setUserAccount(useraccount);
				break;

			case Authority.COMMERCIAL:
				logedActor = new Commercial();
				useraccount = this.userAccountService.createAsCommercial();
				logedActor.setUserAccount(useraccount);
				break;

			case Authority.EXECUTIVE:
				logedActor = new Executive();
				useraccount = this.userAccountService.createAsExecutive();
				logedActor.setUserAccount(useraccount);
				break;

			default:
				break;
			}
			logedActor.getUserAccount().setUsername(actorForm.getUsername());
			logedActor.getUserAccount().setPassword(actorForm.getPassword());
			logedActor.setName(actorForm.getName());
			logedActor.setSurname(actorForm.getSurname());
			logedActor.setEmail(actorForm.getEmail());
			logedActor.setAddress(actorForm.getAddress());
			logedActor.setPhone(actorForm.getPhone());
			this.validator.validate(actorForm, binding);
			Assert.isTrue(actorForm.getPassword().equals(actorForm.getConfirmPassword()),
					"profile.userAccount.repeatPassword.mismatch");
			logedActor.getUserAccount().setPassword(encoder.encodePassword(actorForm.getPassword(), null));

			// Al registrarse, el usuario esta desactivado. El admin debe de activarlo.
			useraccount.setActive(false);
			this.folderService.createSystemFolders(logedActor);

		} else {
			final String formPass = encoder.encodePassword(actorForm.getPassword(), null);
			logedActor = this.findByPrincipal();
			logedActor.setName(actorForm.getName());
			logedActor.setSurname(actorForm.getSurname());
			logedActor.setEmail(actorForm.getEmail());
			logedActor.setAddress(actorForm.getAddress());
			logedActor.setPhone(actorForm.getPhone());

			// Si ha cambiado algún parámetro del Authority (Usuario, password)
			if (!actorForm.getUsername().equals(logedActor.getUserAccount().getUsername())) {

				if (!actorForm.getNewPassword().isEmpty()) {
					// Valida el la cuenta de usuario
					this.validator.validate(actorForm, binding);
					Assert.isTrue(actorForm.getNewPassword().equals(actorForm.getConfirmPassword()),
							"profile.userAccount.repeatPassword.mismatch");
					// Cambia la contraseña
					// Comprueba la contraseña y la cambia si todo ha ido bien
					Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "profile.wrong.password");
					Assert.isTrue(checkLength(actorForm.getNewPassword()), "profile.password.length");
					logedActor.getUserAccount().setPassword(encoder.encodePassword(actorForm.getNewPassword(), null));
				} else {
					actorForm.setNewPassword("XXXXX");
					actorForm.setConfirmPassword("XXXXX");
					// Valida el la cuenta de usuario
					this.validator.validate(actorForm, binding);
					// Comprueba la contraseña 
					Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "profile.wrong.password");
					
				}
				
				// Cambia el nombre de usuario
				logedActor.getUserAccount().setUsername(actorForm.getUsername());

			} else {
				if (!actorForm.getPassword().isEmpty()) {
					if (!actorForm.getNewPassword().isEmpty()) {
						// Valida el la cuenta de usuario
						this.validator.validate(actorForm, binding);
						Assert.isTrue(actorForm.getNewPassword().equals(actorForm.getConfirmPassword()),
								"profile.userAccount.repeatPassword.mismatch");
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "profile.wrong.password");
						Assert.isTrue(checkLength(actorForm.getNewPassword()), "profile.password.length");
						logedActor.getUserAccount()
								.setPassword(encoder.encodePassword(actorForm.getNewPassword(), null));
					} else {
						actorForm.setNewPassword("XXXXX");
						actorForm.setConfirmPassword("XXXXX");
						// Valida el la cuenta de usuario
						this.validator.validate(actorForm, binding);
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "profile.wrong.password");

					}
					
				} else {
					// Como no ha cambiado ni usuario ni escrito contraseña seteamos temporalmente
					// el username y passwords para pasar la validacion de userAccount
					// Valida El formulario
					actorForm.setPassword("XXXXX");
					actorForm.setNewPassword("XXXXX");
					actorForm.setConfirmPassword("XXXXX");
					this.validator.validate(actorForm, binding);
				}

			}
		}
		return logedActor;
	}

	private boolean checkLength(String newPassword) {
		// TODO Auto-generated method stub
		return newPassword.length()>4 && newPassword.length()<33;
	}

	/**
	 * Comprueba que el username no ha cambiado en el formulario y que la password
	 * no es vacía
	 *
	 * @param actorForm:
	 *            Formulario web
	 * @param logedActor:
	 *            Actor logeado
	 * @return boolean
	 */
	private Boolean checkChangeAuthority(final RegisterForm actorForm, final Actor logedActor) {
		final UserAccount ua = logedActor.getUserAccount();
		return ua.getUsername().equals(actorForm.getUsername()) && actorForm.getPassword().isEmpty() ? false : true;
	}

	public void flush() {
		this.actorRepository.flush();

	}
}
