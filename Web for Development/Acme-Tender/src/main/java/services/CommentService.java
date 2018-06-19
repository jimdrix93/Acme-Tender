
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Administrator;
import domain.Answer;
import domain.Comment;
import domain.Commercial;
import domain.Tender;

@Service
@Transactional
public class CommentService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private CommentRepository		commentRepository;

	//Services
	@Autowired
	private CommercialService		commercialService;
	@Autowired
	private TenderService			tenderService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private AnswerService			answerService;


	// Constructor ----------------------------------------------------------
	public CommentService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public Comment create(final int tenderId) {
		final Tender tender = this.tenderService.findOneToComment(tenderId);
		Assert.notNull(tenderId);

		final Commercial commercial = this.commercialService.findByPrincipal();
		Assert.notNull(commercial);

		final Comment comment;

		comment = new Comment();
		comment.setCommercial(commercial);
		comment.setTender(tender);

		return comment;
	}

	public Comment findOne(final int commentId) {
		Comment result;

		result = this.commentRepository.findOne(commentId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Comment> findAll() {

		Collection<Comment> result;

		result = this.commentRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Comment saved;

		final Commercial commercial = this.commercialService.findByPrincipal();
		Assert.notNull(commercial);
		Assert.isTrue(comment.getCommercial().equals(commercial));

		if (comment.getId() == 0) {
			final Date moment = new Date(System.currentTimeMillis() - 1);
			comment.setWritingDate(moment);
		}

		saved = this.commentRepository.save(comment);

		return saved;
	}

	public void delete(final Comment comment) {
		Assert.notNull(comment);

		this.commentRepository.delete(comment);
	}

	public void flush() {
		this.commentRepository.flush();

	}

	// Other methods ---------------------------------------------------------------

	public Collection<Comment> findAllByTender(final Integer tenderId) {

		final Collection<Comment> comments = this.commentRepository.findAllByTenderId(tenderId);
		Assert.notNull(comments);

		return comments;
	}

	public void deleteByAdmin(final Collection<Comment> comments) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		for (final Comment c : comments) {
			final Collection<Answer> answers = this.answerService.findAllByComment(c.getId());
			this.answerService.deleteInBatch(answers);

			this.commentRepository.delete(c);
		}

	}

}
