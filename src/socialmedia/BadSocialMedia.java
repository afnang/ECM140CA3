package socialmedia;

import java.io.*;
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
		if (handle.length() > 30 || handle.contains(" ") || handle == "") {
			throw new InvalidHandleException(
					"Handle must be under 30 characters, cannot contain whitespace, cannot be null.");
		}
		if (findAccountByHandle(handle) != null) {
			throw new IllegalHandleException("Handle already exists on the platform.");
		} else {
			Account account = new Account();
			account.id = accountId;
			account.Handle = handle;
			accounts.add(account);
			accountId++;
			account.setExists(true);

			return account.id;
		}
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		if (handle.length() > 30 || handle.contains(" ") || handle == "") {
			throw new InvalidHandleException(
					"Handle must be under 30 characters, cannot contain whitespace, cannot be null.");
		}
		if (findAccountByHandle(handle) != null) {
			throw new IllegalHandleException("Handle already exists on the platform.");
		} else {
			Account account = new Account();
			account.id = accountId;
			account.Handle = handle;
			account.setExists(true);
			account.setDescription(description);
			accounts.add(account);
			accountId++;
			account.setExists(true);

			return account.id;
		}
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		try {
			accounts.remove(findAccountById(id));
		} catch (Exception AccountIDNotRecognisedException) {
			throw new AccountIDNotRecognisedException("Account ID not recognised.");
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		for (Post p : posts) {
			if (p.getAccount().getHandle().equals(handle)) {
				try {
					if (p.isEndorsedPost()) {
						for (Post post : posts) {
							if (post.getId() == p.getParentId()) {
								post.endorsements--;
							}
						}
					}
					deletePost(p.getId());
				} catch (PostIDNotRecognisedException e) {
					e.printStackTrace();
					throw new HandleNotRecognisedException("Handle not recognised.");
				}

			}
		}
		accounts.removeIf(account -> account.Handle == handle);

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		if (newHandle.length() > 30 || newHandle.contains(" ") || newHandle == "") {
			throw new InvalidHandleException(
					"Handle must be under 30 characters, cannot contain whitespace, cannot be null.");
		}
		if (findAccountByHandle(newHandle) != null) {
			throw new IllegalHandleException("Handle already exists on the platform.");
		} else {
			try {

				findAccountByHandle(oldHandle).Handle = newHandle;
			} catch (Exception HandleNotRecognisedException) {
				throw new HandleNotRecognisedException("Handle not recognised.");
			}
		}
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		if (findAccountByHandle(handle) == null) {
			throw new HandleNotRecognisedException("Handle not found in the platform.");
		}
		findAccountByHandle(handle).setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// return findAccountByHandle(handle); It should return a formatted sum of a
		// user.
		String showAccount = "";
		Account accountToShow = findAccountByHandle(handle);
		if (accountToShow == null) {
			throw new HandleNotRecognisedException("Handle not found in platform.");
		}
		int postCount = 0;
		int endorsementCount = 0;
		for (Post p : posts) {
			if (p.getAccount().equals(accountToShow)) {
				postCount++;
				endorsementCount = p.getEndorsements() + endorsementCount;
			}

		}
		// This method should create a formatted string, doesn't have to print them
		showAccount = "ID:" + accountToShow.getId() + "\n" + "Handle:" + accountToShow.getHandle() + "\n"
				+ "Description:" + accountToShow.getDescription() + "\n" + "Post Count:" + postCount + "\n"
				+ "Endorse Count:" + endorsementCount;


		return showAccount;
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
			post.exists = true;
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
				if (p.account.getHandle().equals(handle) && p.parentId == id&&p.endorsedPost) {
					throw new NotActionablePostException("Can't endorse same post twice");
				}	
			}
			for (Post p : posts) {
				// if (p.getAccount().getHandle()==handle&& p.parentId==id&&p.endorsedPost)
				// I think we should add the line above, it needs all conditions to be true if
				// it's gonna throw an exception.
				if (p.getId() == id) {
					
					if (p.getParentId() != 0) {

						if (p.isEndorsedPost()) {
							throw new NotActionablePostException("Can't endorse another endorsed post");
						}
						throw new NotActionablePostException("Cannot endorse a comment.");
					}
					if (!p.isExists()) {
						throw new NotActionablePostException("Post has been deleted, cannot comment");
					}

					String endorsedPost = "EP@" + p.account.getHandle() + ": " + p.getMessage();
					Post post = new Post();
					post.id = postId;
					post.parentId = p.getId();
					post.account = findAccountByHandle(handle);
					post.message = endorsedPost;
					post.endorsedPost = true;
					post.exists = true;
					posts.add(post);
					p.endorsements++;
					p.getAccount().endorsementCount++;
					postId++;
					return post.getId();

				}

			}
			throw new PostIDNotRecognisedException("Post ID not found in the platform");
		}
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
				if (p.getId() == id) {
					if (p.isEndorsedPost()) {
						throw new NotActionablePostException("Can't comment on an endorsed post");
					}
					if (!p.isExists()) {
						throw new NotActionablePostException("Post has been deleted, cannot comment");
					}
					p.comments++;
					Post post = new Post();
					post.id = postId;
					post.parentId = id;
					post.account = findAccountByHandle(handle);
					post.message = message;
					post.endorsedPost = false;
					post.exists = true;
					posts.add(post);
					postId++;
					return post.getId();
				}
			}
			throw new PostIDNotRecognisedException("Post ID not found in the platform");
		}

	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		int counter = 0;
		for (Post p : posts) {
			if (p.getParentId() == id) {
				counter++;
			}
			if (counter == 0) {
				posts.remove(p);
			}
			if (p.getId() == id) {

				p.setMessage("The original content was removed from the system and is no longer available.");
				p.setAccount(null);
				p.setEndorsements(0);
				p.setExists(false);
			}

		}
		System.out.println("Post successfully deleted");

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		for (Post p : posts) {
			if (p.getId() == id) {
				String formattedString = "ID: " + p.getId() + "\n" + "Account: " + p.getAccount().getHandle() + "\n"
						+ "No. endorsements: " + p.getEndorsements() + " | " + "No. comments: " + p.getComments() + "\n"
						+ p.getMessage();
				System.out.println("Parent ID:"+p.getParentId());

				return formattedString;

			}
		}
		throw new PostIDNotRecognisedException("Post ID not found in the platform");
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			// Looks like the most difficult method.
	//TODO We didn't add exceptions to this method.
			throws PostIDNotRecognisedException, NotActionablePostException {

		StringBuilder sb = new StringBuilder(showIndividualPost(id));
		
		for (Post p : posts) {
			if (p.getParentId() == id && !p.isEndorsedPost()) {

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
	public int getNumberOfAccounts() {
		int numberOfAccounts = 0;
		for (Account account : accounts) {
			if (account.isExists()) {
				numberOfAccounts++;
			}
		}
		return numberOfAccounts;
	}

	@Override
	public int getTotalOriginalPosts() {
		int originalPosts = 0;
		for (Post post : posts) {
			if (post.isExists()) {
				if (post.endorsedPost || post.parentId > 0) {
				} else {
					originalPosts++;
				}
			}
		}
		return originalPosts;

	}

	@Override
	public int getTotalEndorsmentPosts() {
		int endorsementPosts = 0;
		for (Post post : posts) {
			if (post.isExists()) {
				if (post.endorsedPost) {
					endorsementPosts++;
				}
			}
		}
		return endorsementPosts;
	}

	@Override
	public int getTotalCommentPosts() {
		int commentPosts = 0;
		for (Post post : posts) {
			if (post.isExists()) {
				if (post.parentId > 0 && !post.endorsedPost) {
					commentPosts++;
				}
			}
		}
		return commentPosts;
	}

	@Override
	public int getMostEndorsedPost() {
		Post mostEndorsedPost = new Post();
		mostEndorsedPost.endorsements = 0;
		for (Post post : posts) {
			if (post.isExists()) {
				if (post.endorsements > mostEndorsedPost.endorsements) {
					mostEndorsedPost = post;
				}
			}
		}
		return mostEndorsedPost.id;
	}

	@Override
	public int getMostEndorsedAccount() {
		int mostEndorsedAccountId = 0;
		for (Account account : accounts) {
			if (account.isExists()) {
				if (account.endorsementCount > mostEndorsedAccountId) {
					mostEndorsedAccountId = account.getId();
				}
			}
		}
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
		if (!filename.endsWith(".ser")) {
			filename = filename.concat(".ser");
		}
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(accounts);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		if (!filename.endsWith(".ser")) {
			filename = filename.concat(".ser");
		}
		try {
			FileInputStream readData = new FileInputStream(filename);
			ObjectInputStream readStream = new ObjectInputStream(readData);

			ArrayList<Account> accounts1 = (ArrayList<Account>) readStream.readObject();
			readStream.close();
			for (Account a : accounts1) {
				System.out.println(a.getHandle());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();

		}

	}

}
