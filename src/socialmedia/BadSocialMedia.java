package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class BadSocialMedia implements SocialMediaPlatform {
	int accountId = 1;
	int postId = 1;

	ArrayList<Account> accounts = new ArrayList<Account>();
	ArrayList<Post> posts = new ArrayList<Post>();

	private Account findAccountByHandle(String handle) {
		for (Account account : accounts) {
			if (account.Handle.equals(handle)) {
				return account;
			}
		}
		return null;
	}

	private Account findAccountById(int id) throws AccountIDNotRecognisedException {
		for (Account account : accounts) {
			if (account.getId() == id) {
				return account;
			}
		}
		throw new AccountIDNotRecognisedException("ID not recognised.");
	}

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {

		if (handle.length() > 30 || handle.contains(" ")) {
			throw new InvalidHandleException("Handle must be under 30 characters, cannot contain whitespace");
		}
		if (findAccountByHandle(handle) != null) {
			throw new IllegalHandleException("Handle already exists on the platform.");
		} else {
			Account account = new Account();
			account.id = accountId;
			account.Handle = handle;
			accounts.add(account);
			accountId++;

			return account.id;
		}
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		if (handle.length() > 30 || handle.contains(" ")) {
			throw new InvalidHandleException("Handle must be under 30 characters, cannot contain whitespace");
		}
		if (findAccountByHandle(handle) != null) {
			throw new IllegalHandleException("Handle already exists on the platform.");
		} else {
			Account account = new Account();
			account.id = accountId;
			account.Handle = handle;
			accounts.add(account);
			accountId++;

			return account.id;
		}
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		try {
			accounts.remove(findAccountById(id));
		} catch (Exception AccountIDNotRecognisedException) {
			System.out.println("Account ID is not recognised in the system");
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		accounts.removeIf(account -> account.Handle == handle);
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		try {
			findAccountByHandle(oldHandle).Handle = newHandle;
		} catch (Exception HandleNotRecognisedException) {
			System.out.println("Handle not recognised.");
			// TODO: handle exception
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		findAccountByHandle(handle).Description = description;
		// TODO Auto-generated method stub

	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// return findAccountByHandle(handle); It should return a formatted sum of a
		// user.
		Account accountToShow = findAccountByHandle(handle);
		int postCount = 0;
		int endorsementCount = 0;
		for (Post p : posts) {
			if (p.getAccount().equals(accountToShow)) {
				postCount++;
				endorsementCount = p.getEndorsements() + endorsementCount;
			}

		}
		// This method should create a formatted string, doesn't have to print them
		System.out.println("ID:" + accountToShow.getId());
		System.out.println("Handle:" + accountToShow.getHandle());
		System.out.println("Description:" + accountToShow.getDescription());
		System.out.println("Post Count:" + postCount);
		System.out.println("Endorse Count:" + endorsementCount);
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		if (findAccountByHandle(handle) == null) {
			throw new HandleNotRecognisedException("Handle not found in platform, Please try again");
		}
		if (message.isEmpty() || message.length() > 100) {
			throw new InvalidPostException("Message was greater than 100 characters or empty");
		} else {
			Post post = new Post();
			post.id = postId;
			post.account = findAccountByHandle(handle);
			post.message = message;
			post.endorsedPost = false;
			posts.add(post);
			postId++;
			return post.getId();
		}

	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		if (findAccountByHandle(handle) == null) {
			throw new HandleNotRecognisedException("Handle not found in platform, Please try again");
		} else {
			for (Post p : posts) {
				//if (p.getAccount().getHandle()==handle&& p.parentId==id&&p.endorsedPost)
				//I think we should add the line above, it needs all conditions to be true if it's gonna throw an exception.
				if (p.getId() == id) {
					
					
					if (p.isEndorsedPost()) {
						if (p.getAccount().equals(findAccountByHandle(handle))) { //What does it check?
							throw new NotActionablePostException("Cannot endorse the same post twice");
						}
						throw new NotActionablePostException("Can't endorse another endorsed post");
					} else {
						String endorsedPost = "EP@" + p.account.getHandle() + ": " + p.getMessage();
						Post post = new Post();
						post.id = postId;
						post.parentId = p.getId();
						post.account = findAccountByHandle(handle);
						post.message = endorsedPost;
						post.endorsedPost = true;
						posts.add(post);
						p.endorsements++;
						p.getAccount().endorsementCount++;
						postId++;
						return post.getId();
					}
				} else {
					throw new PostIDNotRecognisedException("Post ID not found in the platform");
				}

			}

		}
		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		if (message.length() > 100 || message.isEmpty()) {
			throw new InvalidPostException("Message cannot be empty or greater than 100 characters");
		}
		if (findAccountByHandle(handle) == null) {
			throw new HandleNotRecognisedException("Handle not found in the platform");
		} else {
			for (Post p : posts) {
				if (p.isEndorsedPost()) {
					throw new NotActionablePostException("Can't comment on an endorsed post");
				}
				if (p.getId() == id) {
					p.comments++;
					Post post = new Post();
					post.id = postId;
					post.parentId =id;
					post.account = findAccountByHandle(handle);
					post.message = message;
					post.endorsedPost = false;
					posts.add(post);
					postId++;
					return post.getId();
				} else {
					throw new PostIDNotRecognisedException("Post ID not found in the platform");
				}
			}
		}

		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		posts.removeIf(post -> post.id == id);
		// TODO Auto-generated method stub

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		for (Post p:posts) {
			if (p.getId() == id){
				String formattedString = "ID: " + p.getId() + "\n" +
										"Account: " + p.getAccount().getHandle() + "\n" +
										"No. endorsements: " + p.getEndorsements() + " | " + "No. comments: " + p.getComments() + "\n" +
										p.getMessage();

				return formattedString;

			}
		}
		throw new PostIDNotRecognisedException("Post ID not found in the platform");
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
	//Looks like the most difficult method.
			throws PostIDNotRecognisedException, NotActionablePostException {

		StringBuilder sb = new StringBuilder(showIndividualPost(id));


		for (Post p:posts){
			if (p.getParentId() == id && !p.isEndorsedPost()){

				String indent = showPostChildrenDetails(p.getId()).toString();
				indent = indent.replaceAll("\n", "\n\t");
				sb.append("\n|");
				sb.append("\n| > ");
				sb.append(indent);




			}



		}



		return sb;
	}

	@Override
	public int getNumberOfAccounts() { return accounts.size(); }

	@Override
	public int getTotalOriginalPosts() {
		int originalPosts = 0;
		for (Post post : posts) {
			if (post.endorsedPost || post.parentId > 0) {
			} else {
				originalPosts++;
			}

		}
		return originalPosts;

	}

	@Override
	public int getTotalEndorsmentPosts() {
		int endorsementPosts = 0;
		for (Post post : posts) {
			if (post.endorsedPost) {
				endorsementPosts++;
			}
		}
		return endorsementPosts;
	}

	@Override
	public int getTotalCommentPosts() {
		int commentPosts = 0;
		for (Post post : posts) {
			if (post.parentId > 0 && !post.endorsedPost) {
				commentPosts++;
			}

		}
		return commentPosts;
	}

	@Override
	public int getMostEndorsedPost() {
		Post mostEndorsedPost = new Post();
		mostEndorsedPost.endorsements = 0;
		for (Post post : posts) {
			if (post.endorsements>mostEndorsedPost.endorsements) {
				mostEndorsedPost = post;
			}
		}
		return mostEndorsedPost.id;
	}

	@Override
	public int getMostEndorsedAccount() {
		int mostEndorsedAccountId = 0;
		for (Account account : accounts) {
			if (account.endorsementCount>mostEndorsedAccountId) {
				mostEndorsedAccountId = account.getId();
			}
		}
		// TODO Auto-generated method stub
		return mostEndorsedAccountId;
	}

	@Override
	public void erasePlatform() {
		accountId = 1;
		postId = 1;
		accounts.clear();
		posts.clear();
		// TODO Should we reset all the variables created in methods?

	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
