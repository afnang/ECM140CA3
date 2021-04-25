package socialmedia;

import java.io.Serializable;

public class Post implements Serializable {
	int id;
	int parentId;
	int endorsements;
	int comments;
	Account account;
	String message;
	boolean exists;
	boolean endorsedPost;

	public boolean isExists() { return exists; }

	public boolean isEndorsedPost() { return endorsedPost; }

	public void setEndorsedPost(boolean endorsedPost) { this.endorsedPost = endorsedPost; }

	public void setExists(boolean exists) {
		exists = exists;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getEndorsements() {
		return endorsements;
	}

	public void setEndorsements(int endorsements) {
		this.endorsements = endorsements;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}
}
