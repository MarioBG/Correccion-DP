
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.RendezvousService;
import services.UserService;
import domain.Comment;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CommentService		commentService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;


	// Constructors ---------------------------------------------------------

	public CommentController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvousId) {
		ModelAndView result;
		User principal = null;
		try {
			principal = this.userService.findByPrincipal();
		} catch (Throwable oops) {

		}
		Collection<Comment> comments;

		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		comments = this.commentService.commentsOfThisRendezvouseWithCommentNull(rendezvousId);

		result = new ModelAndView("comment/list");
		if (principal != null)
			result.addObject("canComment", principal.getRsvpdRendezvouses().contains(this.rendezvousService.findOne(rendezvousId)));
		result.addObject("comments", comments);
		result.addObject("rendezvous", rendezvous);
		result.addObject("requestURI", "comment/list.do");

		return result;
	}
	@RequestMapping(value = "/listReplies", method = RequestMethod.GET)
	public ModelAndView listReplies(@RequestParam final int commentId) {
		ModelAndView result;

		Comment comment = new Comment();
		comment = this.commentService.findOne(commentId);

		Collection<Comment> comments;
		comments = comment.getReplies();

		result = new ModelAndView("comment/list");

		result.addObject("comments", comments);
		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/listReplies.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayComment(@RequestParam final int commentId) {
		ModelAndView result;

		Comment comment;
		comment = this.commentService.findOne(commentId);

		result = new ModelAndView("comment/display");

		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/display.do");

		return result;
	}

}
