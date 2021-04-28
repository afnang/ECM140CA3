package socialmedia;

import java.io.Serializable;

/**
 * @author at753,ag811 This is a class of posts, it keeps all the needed attributes of
 *         a class.
 */
public class Post implements Serializable {
	int id;
	int parentId;
	int endorsements;
	int comments;
	Account account;
	String message;
	boolean exists;
	boolean endorsedPost;

	/**
	 * @return returns a true if that post exists, false if it's deleted.
	 */
	public boolean isExists() {
		return exists;
	}

	/**
	 * @return returns a true if that post is endorsed, false if it's not.
	 */

	public boolean isEndorsedPost() {
		return endorsedPost;
	}

	/**
	 * @param endorsedPost is a boolean to show if a post is an endorsement or not
	 */
	public void setEndorsedPost(boolean endorsedPost) {
		this.endorsedPost = endorsedPost;
	}

	/**
	 * @param exists will be true if it's a deleting procedure, false if it's a
	 *               creating procedure.
	 */
	public void setExists(boolean exists) {
		this.exists = exists;
	}

	/**
	 * @return returns the id of a post.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id is the id of a post.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return returns the parent posts id of a post.
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId is to set when assigning a post as a child.
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return returns the count of endorsements this account got.
	 */
	public int getEndorsements() {
		return endorsements;
	}

	/**
	 * @param endorsements is the new endorsement count.
	 */
	public void setEndorsements(int endorsements) {
		this.endorsements = endorsements;
	}

	/**
	 * @return returns the comment count of a post.
	 */
	public int getComments() {
		return comments;
	}

	/**
	 * @param comments is the new comment count
	 */
	public void setComments(int comments) {
		this.comments = comments;
	}

	/**
	 * @return returns the author account of this post.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account is the author of a post.
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return returns the message of the post.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message is the message of a post.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * This is a constructor method for post class.
	 */
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}
}