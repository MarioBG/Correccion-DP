
package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import controllers.AbstractController;
import domain.Comment;

@Controller
@RequestMapping("/comment/admin")
public class CommentAdminController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CommentService	commentService;


	// Constructors ---------------------------------------------------------

	public CommentAdminController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Comment> comments;
		comments = this.commentService.findAll();

		result = new ModelAndView("comment/list");

		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/admin/list.do");

		return result;
	}

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int commentId) {

		final Comment comment = this.commentService.findOne(commentId);

		this.commentService.delete(comment);

		return new ModelAndView("redirect:list.do");
	}

}
