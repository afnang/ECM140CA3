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
	/**
	 * The method finds an account from the arraylist of accounts. It takes in a string
	 * called handle. The method iterates through all the objects in the arraylist, comparing
	 * the parameter that was inputted with the value of the attribute Handle that has been stored
	 * for each object in the arraylist. When a match is found, the Account object with the match
	 * is returned.
	 *
	 * @param handle The Handle of the account, the user is searching for
	 *
	 */

	private Account findAccountByHandle(String handle) {
		for (Account account : accounts) {
			if (account.Handle.equals(handle)) {
				return account;
			}
		}

		return null;
	}

	/**
	 * The method finds an account from the arraylist of accounts. It takes in an ID as
	 * a parameter and compares it with every ID attribute in the arraylist accounts to
	 * see if there is a match. If there is, then the matching Account object is returned.
	 *
	 * @param id The ID of the account, the user is searching for
	 * @throws AccountIDNotRecognisedException if the ID does not match any IDs in
	 * 										the system.
	 */

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

		// Check if handle meets specification requirements i.e. under 30 chars, can't be null
		// 		or have white spaces
		if (handle.length() > 30 || handle.contains(" ") || handle == "") {
			throw new InvalidHandleException(
					"Handle must be under 30 characters, cannot contain whitespace, cannot be null.");
		}
		if (findAccountByHandle(handle) != null) {
			throw new IllegalHandleException("Handle already exists on the platform.");
		} else {

			// Creates Account object and stores in arraylist accounts if all validation is successful
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

		// Validation

		if (handle.length() > 30 || handle.contains(" ") || handle == "") {
			throw new InvalidHandleException(
					"Handle must be under 30 characters, cannot contain whitespace, cannot be null.");
		}
		if (findAccountByHandle(handle) != null) {
			throw new IllegalHandleException("Handle already exists on the platform.");
		} else {

			// Creates Account object and stores in arraylist accounts if all validation is successful
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

		// Tries to remove account with given account ID if unsuccessful
		// 		an exception is thrown
		try {
			accounts.remove(findAccountById(id));
		} catch (Exception AccountIDNotRecognisedException) {
			throw new AccountIDNotRecognisedException("Account ID not recognised.");
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {

		// Tries to remove account with given account handle if unsuccessful
		// 		an exception is thrown
		// TODO need to fix

		for (Post p : posts) { // Iterating through arraylist
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
		accounts.removeIf(account -> account.Handle.equals(handle));

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {

		// Validation
		if (newHandle.length() > 30 || newHandle.contains(" ") || newHandle == "") {
			throw new InvalidHandleException(
					"Handle must be under 30 characters, cannot contain whitespace, cannot be null.");
		}
		if (findAccountByHandle(newHandle) != null) {
			throw new IllegalHandleException("Handle already exists on the platform.");
		} else {
			try {

				// find the Account object with oldHandle as the matching Handle attribute value
				// 		and set the Handle attribute with the value of newHandle
				//			throw an exception if not found

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

		// Find account object with matching handle and change the value of its attribute description
		// 		throw an exception if unsuccessful
		findAccountByHandle(handle).setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {

		// Initialisation of variables
		String showAccount = "";
		Account accountToShow = findAccountByHandle(handle);
		if (accountToShow == null) {
			throw new HandleNotRecognisedException("Handle not found in platform."); // Thrown when an unknown handle
																					 // 		is inputted
		}
		int postCount = 0;
		int endorsementCount = 0;
		for (Post p : posts) { // Iterate through the arraylist posts

			// Comparing currently iterated object's Account attribute with searched Account
			// 		If true then the endorsement and post counts are updated
			if (p.getAccount().equals(accountToShow)) {
				postCount++;
				endorsementCount = p.getEndorsements() + endorsementCount;
			}

		}
		// Creating and storing a formatted string
		showAccount = "ID:" + accountToShow.getId() + "\n" + "Handle:" + accountToShow.getHandle() + "\n"
				+ "Description:" + accountToShow.getDescription() + "\n" + "Post Count:" + postCount + "\n"
				+ "Endorse Count:" + endorsementCount;

		return showAccount;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {

		// Exception thrown when handle is not found in system
		if (findAccountByHandle(handle) == null) {
			throw new HandleNotRecognisedException("Handle not found in platform, Please try again");
		}

		// Validation
		if (message.isEmpty() || message.length() > 100) {
			throw new InvalidPostException("Message was greater than 100 characters or empty");
		} else {

			// New Post Object created and stored in arraylist posts if validation successful
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

		// Exception thrown when handle is not found in system
		if (findAccountByHandle(handle) == null) {
			throw new HandleNotRecognisedException("Handle not found in platform, Please try again");
		} else {
			for (Post p : posts) {
				// Exception thrown when the same user tries to endorse the same post
				if (p.account.getHandle().equals(handle) && p.parentId == id && p.endorsedPost) {
					throw new NotActionablePostException("Can't endorse same post twice");
				}
			}
			for (Post p : posts) { // Iterating through posts
				if (p.getId() == id) {
					if (p.getParentId() != 0) {

						// Exceptions thrown when user tries to endorse an endorsed post
						// 		or a comment
						if (p.isEndorsedPost()) {
							throw new NotActionablePostException("Can't endorse another endorsed post");
						}
						throw new NotActionablePostException("Cannot endorse a comment.");
					}

					// Exception thrown when user tries to endorse a deleted post
					if (!p.isExists()) {
						throw new NotActionablePostException("Post has been deleted, cannot comment");
					}

					// Creates a new Post object and stores it in arraylist posts to store the endorsement when no exceptions are thrown
					// 		Unlike normal Post objects, endorsed posts have the attribute endorsedPost
					//			set to true to differentiate them from original posts
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

			// Exception thrown if Post ID is not found in the system
			throw new PostIDNotRecognisedException("Post ID not found in the platform");
		}
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {

		// Validation
		if (message.length() > 100 || message.isEmpty()) {
			throw new InvalidPostException("Message cannot be empty or greater than 100 characters");
		}
		if (findAccountByHandle(handle) == null) {
			throw new HandleNotRecognisedException("Handle not found in the platform");
		} else {
			for (Post p : posts) { // Iterating through posts
				if (p.getId() == id) {

					// Exceptions thrown if user tries to comment on an endorsed post
					// 		or a deleted post
					if (p.isEndorsedPost()) {
						throw new NotActionablePostException("Can't comment on an endorsed post");
					}
					if (!p.isExists()) {
						throw new NotActionablePostException("Post has been deleted, cannot comment");
					}

					// New Post Object created and stored in arraylist posts if no exceptions thrown
					// 		with the parentId attribute set to id as both endorsed posts and original posts
					// 			have null parentId attributes, so that it is possible to differentiate between the
					// 				different type of posts.
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

			// Exception thrown when Post ID is not found in the system
			throw new PostIDNotRecognisedException("Post ID not found in the platform");
		}

	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {

		// Initialisation
		int counter = 0;
		for (Post p : posts) { // Iterating through posts
			if (p.getParentId() == id) {
				counter++;
			}
		}
		for (Post p : posts) { // Iterating through posts

			if (p.getId() == id) {

				// If the counter value has not been updated, it means that the post has no comments
				// 		so the post can be removed entirely from the system
				if (counter == 0) {
					posts.remove(p);  // Remove post completely.
				}

				// Placeholder message set instead if post has comment posts
				p.setMessage("The original content was removed from the system and is no longer available.");
				p.setExists(false);

				// Update user total endorsements as this post doesn't exist anymore
				// 		So it should not contribute to user's total endorsements
				p.getAccount().endorsementCount = p.getAccount().endorsementCount - p.endorsements;
				p.setEndorsements(0); // Update value of post endorsements to 0 as it doesn't exist now
				return;
			}

		}
		// Exception thrown when Post ID is not found in the system
		throw new PostIDNotRecognisedException("Post ID not recognised.");

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		for (Post p : posts) { // Iterates through posts
			// returns a formatted string if currently iterated object has the same id value as the parameter
			if (p.getId() == id) {
				String formattedString = "ID: " + p.getId() + "\n" + "Account: " + p.getAccount().getHandle() + "\n"
						+ "No. endorsements: " + p.getEndorsements() + " | " + "No. comments: " + p.getComments() + "\n"
						+ p.getMessage();
				return formattedString;

			}
		}

		// Exception thrown if Post ID not found in system
		throw new PostIDNotRecognisedException("Post ID not found in the platform");
	}
	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {

		// Initialising a StringBuilder object with the parent object as the value
		StringBuilder sb = new StringBuilder(showIndividualPost(id));
		if (sb.isEmpty()) {

			// Exception thrown when Post ID is not found in system
			throw new PostIDNotRecognisedException("Post ID not found in platform");
		}

		for (Post p : posts) {

			// Exception thrown when an endorsed post is selected as endorsed posts cannot have
			// 		comments or endorsements
			if (p.getParentId() == id && p.isEndorsedPost()) {
				throw new NotActionablePostException("Endorsed posts cannot have comments");
			}

			if (p.getParentId() == id && !p.isEndorsedPost()) { // Checks if parent object had any comments

				// Converting object to string to allow for string manipulation for formatting purposes
				String indent = showPostChildrenDetails(p.getId()).toString();
				indent = indent.replaceAll("\n", "\n\t");
				sb.append("\n|");
				sb.append("\n| > ");
				sb.append(indent); // Appending formatted string back to StringBuilder Object
			}
		}
		return sb;
	}

	@Override
	public int getNumberOfAccounts() {
		int numberOfAccounts = 0;
		for (Account account : accounts) { // Iterating through accounts
			if (account.isExists()) {
				numberOfAccounts++; // Increments counter if account exists
			}
		}
		return numberOfAccounts;
	}

	@Override
	public int getTotalOriginalPosts() {
		int originalPosts = 0;
		for (Post post : posts) { // Iterating through posts
			if (post.isExists()) {
				if (post.endorsedPost || post.parentId > 0) {
					// Validating it is not an endorsed post or a comment post
				}
				else {
					originalPosts++; // Increments counter if conditions met
				}
			}
		}
		return originalPosts;

	}

	@Override
	public int getTotalEndorsmentPosts() {
		int endorsementPosts = 0;
		for (Post post : posts) { // Iterating through posts
			if (post.isExists()) { // Check if post still exists
				if (post.endorsedPost) { // Check if post is an endorsed post
					endorsementPosts++; // Increment when conditions met
				}
			}
		}
		return endorsementPosts;
	}

	@Override
	public int getTotalCommentPosts() {
		int commentPosts = 0;
		for (Post post : posts) { // Iterate through posts
			if (post.isExists()) { // Check if post still exists
				if (post.parentId > 0 && !post.endorsedPost) { // Check if post is not an endorsed and is a comment
					commentPosts++; // Increment when conditions met
				}
			}
		}
		return commentPosts;
	}

	@Override
	public int getMostEndorsedPost() {
		Post mostEndorsedPost = new Post(); // Create new object called mostEndorsedPost
		mostEndorsedPost.endorsements = 0;
		for (Post post : posts) { // Iterate through posts
			if (post.isExists()) { // Check if post still exists

				// Compare mostEndorsedPost's endorsement attribute with currently iterated Post's endorsements
				// 		attribute
				//		If greater update mostEndorsedPost to be the currently iterated Post object
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
		for (Account account : accounts) { // Iterated through accounts
			if (account.isExists()) { // Check if account still exists

				// Compare endorsement counts of all existing Account objects in accounts
				//		Keep updating mostEndorsedAccountId to store the id of the object with
				// 			the highest value for the attribute endorsementCount
				if (account.endorsementCount > mostEndorsedAccountId) {
					mostEndorsedAccountId = account.getId();
				}
			}
		}
		return mostEndorsedAccountId;
	}

	@Override
	public void erasePlatform() {

		// Resets variables to starting defaults and clears both arraylists
		accountId = 1;
		postId = 1;
		accounts.clear();
		posts.clear();


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