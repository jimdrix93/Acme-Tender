
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnswerRepository;
import domain.Actor;
import domain.Answer;
import domain.Comment;

@Service
@Transactional
public class AnswerService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private AnswerRepository	answerRepository;

	//Services
	@Autowired
	private ActorService		actorService;
	@Autowired
	private CommentService		commentService;


	// Constructor ----------------------------------------------------------
	public AnswerService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public Answer create(final int commentId) {
		final Comment comment = this.commentService.findOne(commentId);
		Assert.notNull(commentId);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Answer answer;

		answer = new Answer();
		answer.setActor(actor);
		answer.setComment(comment);

		return answer;
	}

	public Answer findOne(final int answerId) {
		Answer result;

		result = this.answerRepository.findOne(answerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Answer> findAll() {

		Collection<Answer> result;

		result = this.answerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Answer save(final Answer answer) {
		Assert.notNull(answer);
		Answer saved;

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		if (answer.getId() == 0) {
			final Date moment = new Date(System.currentTimeMillis() - 1);
			answer.setWritingDate(moment);
		}

		saved = this.answerRepository.save(answer);

		return saved;
	}

	public void delete(final Answer answer) {
		Assert.notNull(answer);

		this.answerRepository.delete(answer);
	}

	public void flush() {
		this.answerRepository.flush();

	}

	// Other methods ---------------------------------------------------------------

	public Collection<Answer> findAllByComment(final Integer commentId) {

		final Collection<Answer> answers = this.answerRepository.findAllByCommentId(commentId);
		Assert.notNull(answers);

		return answers;
	}

	public void deleteInBatch(final Collection<Answer> answers) {
		this.answerRepository.deleteInBatch(answers);

	}

}
