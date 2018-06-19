
package usecases;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.FolderService;
import utilities.AbstractTest;
import domain.Administrative;
import domain.Commercial;
import domain.Executive;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UseCaseAnonymous extends AbstractTest {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private FolderService		folderService;
	@Autowired
	private UserAccountService	userAccountService;


	/*
	 * Caso de uso:
	 * No auth-> Registro como ejecutivo(CU01)
	 */
	@Test
	public void createExecutiveTest() {

		final Object testingData[][] = {
			{// Positive
				"Pedro", "Dominguez Lopez", "652956526", "test@gmail.com", "Address Test", "Username1", "Password1", "EXECUTIVE", null
			}, {//Positive
				"Amaia", "Fernandez Rodriguezáéíóú", "652956526", "test@gmail.com", "Address Test", "Username2", "Password2", "EXECUTIVE", null
			}, {// Negative: without name
				"Name", "Surname Test", "652956526", "email", "Address Test", "Username3", "Password3", "EXECUTIVE", ConstraintViolationException.class
			}, {// Negative: with name null
				"", "Surname Test", "652956526", "emailTest@email.com", "Address Test", "Username4", "Password4", "EXECUTIVE", DataIntegrityViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAdministrativeTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void templateCreateExecutiveTest(final Integer i, final String name, final String surname, final String phone, final String email, final String address, final String username, final String password, final String authority,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Executive executive = new Executive();
			final UserAccount userAccount = this.userAccountService.createAsExecutive();

			executive.setName(name);
			executive.setAddress(address);
			executive.setEmail(email);
			executive.setPhone(phone);
			executive.setSurname(surname);
			userAccount.setPassword(password);
			userAccount.setUsername(username);
			executive.setUserAccount(userAccount);
			this.folderService.createSystemFolders(executive);

			this.actorService.save(executive);
			this.actorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * No auth-> Registro como administrativo(CU01)
	 */
	@Test
	public void createAdministrativeTest() {

		final Object testingData[][] = {
			{// Positive
				"Pedro", "Dominguez Lopez", "652956526", "test@gmail.com", "Address Test", "Username1", "Password1", "ADMINISTRATIVE", null
			}, {//Positive
				"Amaia", "Fernandez Rodriguezáéíóú", "652956526", "test@gmail.com", "Address Test", "Username2", "Password2", "ADMINISTRATIVE", null
			}, {// Negative: without name
				"Name", "Surname Test", "652956526", "email", "Address Test", "Username3", "Password3", "ADMINISTRATIVE", ConstraintViolationException.class
			}, {// Negative: with name null
				"", "Surname Test", "652956526", "emailTest@email.com", "Address Test", "Username4", "Password4", "ADMINISTRATIVE", DataIntegrityViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAdministrativeTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void templateCreateAdministrativeTest(final Integer i, final String name, final String surname, final String phone, final String email, final String address, final String username, final String password, final String authority,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Administrative administrative = new Administrative();
			final UserAccount userAccount = this.userAccountService.createAsAdministrative();

			administrative.setName(name);
			administrative.setAddress(address);
			administrative.setEmail(email);
			administrative.setPhone(phone);
			administrative.setSurname(surname);
			userAccount.setPassword(password);
			userAccount.setUsername(username);
			administrative.setUserAccount(userAccount);
			this.folderService.createSystemFolders(administrative);

			this.actorService.save(administrative);
			this.actorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * No auth-> Registro como administrativo(CU01)
	 */
	@Test
	public void createCommercialTest() {

		final Object testingData[][] = {
			{// Positive
				"Pedro", "Dominguez Lopez", "652956526", "test@gmail.com", "Address Test", "Username1", "Password1", "COMMERCIAL", null
			}, {//Positive
				"Amaia", "Fernandez Rodriguezáéíóú", "652956526", "test@gmail.com", "Address Test", "Username2", "Password2", "COMMERCIAL", null
			}, {// Negative: without name
				"Name", "Surname Test", "652956526", "email", "Address Test", "Username3", "Password3", "COMMERCIAL", ConstraintViolationException.class
			}, {// Negative: with name null
				"", "Surname Test", "652956526", "emailTest@email.com", "Address Test", "Username4", "Password4", "COMMERCIAL", DataIntegrityViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAdministrativeTest(i, (String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void templateCreateCommercialTest(final Integer i, final String name, final String surname, final String phone, final String email, final String address, final String username, final String password, final String authority,
		final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			final Commercial commercial = new Commercial();
			final UserAccount userAccount = this.userAccountService.createAsCommercial();

			commercial.setName(name);
			commercial.setAddress(address);
			commercial.setEmail(email);
			commercial.setPhone(phone);
			commercial.setSurname(surname);
			userAccount.setPassword(password);
			userAccount.setUsername(username);
			commercial.setUserAccount(userAccount);
			this.folderService.createSystemFolders(commercial);

			this.actorService.save(commercial);
			this.actorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
