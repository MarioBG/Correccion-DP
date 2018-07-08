
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

public class CommentForm {

	// Constructors

	public CommentForm() {
		super();
	}


	// Attributes

	private String	text;
	private String	picture;

	private int		id;
	private int		rendezvousId;
	private Integer	commentParentId;
	private int		userId;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getRendezvousId() {
		return this.rendezvousId;
	}

	public void setRendezvousId(final int rendezvousId) {
		this.rendezvousId = rendezvousId;
	}

	public Integer getCommentParentId() {
		return this.commentParentId;
	}

	public void setCommentParentId(final Integer commentParentId) {
		this.commentParentId = commentParentId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(final int userId) {
		this.userId = userId;
	}

}
