
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Comment;
import domain.Rendezvous;
import domain.User;
import forms.CommentForm;

@Service
@Transactional
public class CommentService {

	// Managed repository

	@Autowired
	private CommentRepository	commentRepository;

	@Autowired
	private UserService			userService;

	@Autowired
	private Validator			validator;

	@Autowired
	private AdminService		adminService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Constructors

	public CommentService() {
		super();
	}

	// Simple CRUD methods

	public Comment create(final int rendezvousId, final Integer commentId) {

		Assert.isTrue(rendezvousId != 0);
		if (commentId != null)
			Assert.isTrue(commentId != 0);

		this.userService.checkAuthority();
		final Comment res = new Comment();
		final Collection<Comment> replies = new ArrayList<Comment>();
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getAttendants().contains(u));
		Comment parent = null;
		if (commentId != null)
			parent = this.findOne(commentId);

		Assert.notNull(u);

		final Date moment = new Date(System.currentTimeMillis() - 1000);

		res.setMoment(moment);
		res.setUser(u);
		res.setCommentParent(parent);
		res.setReplies(replies);
		res.setRendezvous(rendezvous);
		return res;
	}
	public Collection<Comment> findAll() {
		Collection<Comment> res;
		res = this.commentRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Comment findOne(final int comment) {
		Assert.isTrue(comment != 0);
		Comment res;
		res = this.commentRepository.findOne(comment);
		Assert.notNull(res);
		return res;
	}

	public Comment save(final Comment comment) {

		this.userService.checkAuthority();

		if (comment.getId() == 0)
			comment.setMoment(new Date(System.currentTimeMillis() - 1000));

		final Comment result = this.commentRepository.save(comment);

		if (comment.getId() == 0) {
			result.getRendezvous().getComments().add(result);
			if (result.getCommentParent() != null)
				result.getCommentParent().getReplies().add(result);
		}

		return result;
	}

	public void delete(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() != 0);
		Assert.isTrue(this.commentRepository.findOne(comment.getId()) != null);
		this.adminService.checkAuthority();

		if (comment.getReplies().size() != 0)
			for (final Comment c : comment.getReplies())
				this.delete(c);
		this.commentRepository.delete(comment);
	}

	//Other bussines methods

	public void deleteAll(final Collection<Comment> comments) {
		//Assert.notNull(comments);
		this.adminService.checkAuthority();
		for (final Comment c : comments) {
			this.deleteAll(c.getReplies());
			this.commentRepository.delete(c);
		}
	}

	public Collection<Comment> commentsOfThisRendezvouseWithCommentNull(final int rendezvouseId) {
		Collection<Comment> commentsOfThisRendezvouse;

		commentsOfThisRendezvouse = this.commentRepository.commentsOfThisRendezvouseWithCommentNull(rendezvouseId);

		return commentsOfThisRendezvouse;
	}

	public void flush() {
		this.commentRepository.flush();
	}

	public Comment reconstruct(final CommentForm commentForm, final BindingResult binding) {
		final Comment res = new Comment();

		final Collection<Comment> replies = new ArrayList<Comment>();
		User user = new User();
		user = this.userService.findByPrincipal();

		final Date moment = new Date(System.currentTimeMillis() - 1);

		res.setMoment(moment);
		res.setPicture(commentForm.getPicture());
		res.setText(commentForm.getText());

		res.setReplies(replies);
		res.setUser(user);
		res.setRendezvous(this.rendezvousService.findOne(commentForm.getRendezvousId()));
		if (commentForm.getCommentParentId() != null)
			res.setCommentParent(this.findOne(commentForm.getCommentParentId()));

		this.validator.validate(res, binding);

		return res;
	}

	public CommentForm construct(final Comment comment) {

		final CommentForm res = new CommentForm();

		res.setPicture(comment.getPicture());
		res.setText(comment.getText());
		res.setId(comment.getId());
		res.setRendezvousId(comment.getRendezvous().getId());
		if (comment.getCommentParent() != null)
			res.setCommentParentId(comment.getCommentParent().getId());
		res.setUserId(comment.getUser().getId());

		return res;
	}

	public void checkPrincipalForm(final CommentForm commentForm) {

		Assert.notNull(commentForm);

		final Comment comment = this.reconstruct(commentForm, null);
		final User principal = this.userService.findByPrincipal();

		Assert.isTrue(comment.getUser().equals(principal));
	}

}
