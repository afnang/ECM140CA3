import socialmedia.AccountIDNotRecognisedException;
import socialmedia.BadSocialMedia;
import socialmedia.HandleNotRecognisedException;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.InvalidPostException;
import socialmedia.NotActionablePostException;
import socialmedia.PostIDNotRecognisedException;
import socialmedia.SocialMediaPlatform;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {
	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new BadSocialMedia();

		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		
		Integer id;
		try {
			
			id = platform.createAccount("my_handle");
			platform.createAccount("ddiogo","Hello World!");
			platform.createAccount("diogo","Hello World!");
			platform.createAccount("obama");
			platform.createAccount("bush");

			//The code above must create an error.
			platform.createPost("diogo", "Hi everyone, this is the first post.");
			System.out.println("YEAH:"+ platform.getNumberOfAccounts());
			//platform.createPost("at", " ");
			//assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";
			platform.endorsePost("obama", 1);
			platform.endorsePost("obama", 1); //Must create an error.
			platform.endorsePost("diogo", 1); //It's ok to endorse his own post
			platform.endorsePost("diogo", 1); //Must create an error.
			platform.commentPost("obama", 1, "Yes we can");
			platform.commentPost("obama", 1, "Yes we can again");
			platform.commentPost("obama",6,"commenting on my post");



			System.out.println(platform.showPostChildrenDetails(1));




			//assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";
		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			e.printStackTrace();
			assert (false) : "InvalidHandleException thrown incorrectly";

		} catch (HandleNotRecognisedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PostIDNotRecognisedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotActionablePostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("Finally here.");
		}

	}

}
