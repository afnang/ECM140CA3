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

	private int findAccountByHandle(String handle) {
		for (Account account : accounts) {
			if (account.Handle.equals(handle)) {
				return account.id;
			}
		}
		return 0;
	}

	private Account findAccountById(int id) throws AccountIDNotRecognisedException {
		for (Account account : accounts) {
			if (account.id == id) {
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
		if (findAccountByHandle(handle) != 0) {
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
		Account account = new Account();
		// TODO Auto-generated method stub
		account.id = accountId;
		account.Handle = handle;
		account.Description = description;
		accounts.add(account);
		accountId++;

		return 0;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		try {
			accounts.remove(findAccountById(id));
		} catch (Exception e) {

			// TODO: handle exception
		}

	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		for (Account account : accounts) {
			if (account.Handle == handle) {
				accounts.remove(account);
			}
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// TODO Auto-generated method stub
		postId++;
		return 0;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// TODO Auto-generated method stub
		postId++;
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfAccounts() {

		// TODO Auto-generated method stub
		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

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
