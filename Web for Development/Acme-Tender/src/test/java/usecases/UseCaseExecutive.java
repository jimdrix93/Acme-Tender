
package usecases;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ActorService;
import services.FolderService;
import services.OfferService;
import utilities.AbstractTest;
import domain.Offer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UseCaseExecutive extends AbstractTest {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private FolderService	folderService;
	@Autowired
	private OfferService	offerService;


	/*
	 * Caso de uso:
	 * Auth Executive-> Visualizar las ofertas existentes en el sistema.(CU51)
	 */
	@Test
	public void listOffersTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", null
			}, {//Negative
				"", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListOffersTest(i, (String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateListOffersTest(final Integer i, final String principal, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			final Collection<Offer> offers = this.offerService.findAllPublished();

			Assert.notNull(offers);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth Executive-> Cambiar el estado de una oferta a DENEGADA indicando un motivo.(CU52)
	 */
	@Test
	public void changeStateOfferTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "offer1", null
			}, {//Negative
				"", "offer1", IllegalArgumentException.class
			}, {//Negative
				"administrative1", "offer1", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateChangeStateOfferTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateChangeStateOfferTest(final Integer i, final String principal, final String offerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);

			final Offer offer = this.offerService.findOne(super.getEntityId(offerId));
			offer.setDenialReason("reason");

			this.offerService.saveToDeny(offer);
			this.offerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso:
	 * Auth Executive-> Listar las ofertas con fecha de presentación pasada, presentes en el sistema y realizar búsquedas en dichas ofertas filtrando por palabra clave.(CU54)
	 */
	@Test
	public void searchOfferTest() {

		final Object testingData[][] = {
			{// Positive
				"executive1", "", null
			}, {//Positive
				"executive2", "web", null
			}, {//Positive
				"executive1", "andaluz", null
			}, {// Negative: without user
				"", "web", IllegalArgumentException.class
			}, {// Negative: with user not exist
				"user55", "web", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSearchOfferTest(i, (String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateSearchOfferTest(final Integer i, final String principal, final String word, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(principal);
			final Collection<Offer> offers = this.offerService.findOfferByKeyWord(word);

			Assert.notNull(offers);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
