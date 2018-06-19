
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TabooWordRepository;
import domain.Administrator;
import domain.TabooWord;

@Service
@Transactional
public class TabooWordService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private TabooWordRepository		tabooWordRepository;

	//Services
	@Autowired
	private AdministratorService	administratorService;


	// Constructor ----------------------------------------------------------
	public TabooWordService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public TabooWord create() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final TabooWord tabooWord = new TabooWord();

		return tabooWord;
	}

	public TabooWord findOne(final int tabooWordId) {
		TabooWord result;
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		result = this.tabooWordRepository.findOne(tabooWordId);
		Assert.notNull(result);

		return result;
	}

	public Collection<TabooWord> findAll() {

		Collection<TabooWord> result;

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		result = this.tabooWordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public TabooWord save(final TabooWord tabooWord) {

		Assert.notNull(tabooWord);

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final TabooWord saved = this.tabooWordRepository.save(tabooWord);

		return saved;
	}

	public void delete(final TabooWord tabooWord) {
		Assert.notNull(tabooWord);
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		this.tabooWordRepository.delete(tabooWord);
	}

	public void flush() {
		this.tabooWordRepository.flush();

	}
	
	public Collection<TabooWord> getTabooWordFromMyMessageSubjectAndBody(final String subject, final String body) {

		Assert.notNull(subject);
		Assert.notNull(body);

		return this.tabooWordRepository.getTabooWordFromMyMessageSubjectAndBody(subject, body);

	}
}
