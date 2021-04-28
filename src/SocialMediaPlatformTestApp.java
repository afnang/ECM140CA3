import socialmedia.*;
import socialmedia.SocialMedia;

import java.io.IOException;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * SocialMedia class).
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

		SocialMediaPlatform platform = new SocialMedia();

		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		try {
			platform.erasePlatform();
			platform.createAccount("ddiogo", "Hello World!");
			platform.createAccount("diogo", "Hello World!");
			platform.createAccount("obama", "Former president of USA");
			platform.createAccount("dida", "Milan legendary XI");
			platform.createAccount("maldini", "Milan legendary XI");
			platform.createAccount("nesta", "Milan legendary XI");
			platform.createAccount("stam", "Milan legendary XI");
			platform.createAccount("cafu", "Milan legendary XI");
			platform.createAccount("gattuso", "Milan legendary XI");
			platform.createAccount("seedorf", "Milan legendary XI");
			platform.createAccount("pirlo", "Milan legendary XI");
			platform.createAccount("kaka", "Milan legendary XI");
			platform.createAccount("crespo", "Milan legendary XI");
			platform.createAccount("shevchenko", "Milan legendary XI");
			platform.createAccount("ronaldinho");
			platform.createAccount("henry");
			platform.createAccount("zlatan");
			System.out.println("YEAH:" + platform.getNumberOfAccounts());

			// The code above must create an error.
			platform.createPost("diogo", "This is the first post"); // id 1
			platform.createPost("kaka", "Thierry Henry would've been a perfect fit"); // id 2
			platform.createPost("shevchenko", "Ukraine is the best"); // id 3
			platform.createPost("gattuso", "I'm Gattuso."); // id 4
			platform.createPost("crespo", "GOOAAALL!!!"); // id 5
			platform.createPost("pirlo", "spaghetti is the best"); // id 6
			platform.createPost("crespo", "Is Zlatan better than me?"); // id 7

			// assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered
			// in the system does not match";
			platform.endorsePost("obama", 1); // id 8
			platform.endorsePost("diogo", 1); // id 9
			platform.endorsePost("henry", 2); // id 10

			platform.commentPost("obama", 1, "Yes we can"); // id 11
			platform.commentPost("obama", 11, "Yes we can again"); // id 12
			platform.commentPost("obama", 11, "Yes we can again"); // id 12

			platform.commentPost("obama", 12, "commenting on my comment"); // id 14
			platform.commentPost("pirlo", 4, "I'm Pirlo"); // id 15
			platform.commentPost("zlatan", 7, ":D"); //id 16
			System.out.println(platform.showIndividualPost(16));
			platform.commentPost("maldini", 16, "He yarram.");
			platform.deletePost(16);
			System.out.println(platform.showIndividualPost(16));
			System.out.println("No. Comments:" + platform.getTotalCommentPosts());
			System.out.println("No. Endorsements:" + platform.getTotalEndorsmentPosts());
			System.out.println("No. Original posts:" + platform.getTotalOriginalPosts());

			platform.changeAccountHandle("pirlo", "tirlo");
			// System.out.println(platform.showPostChildrenDetails(1));
			// System.out.println(platform.showPostChildrenDetails(4));
			// System.out.println(platform.showPostChildrenDetails(7));
			System.out.println("TestApp worked fine,");
			platform.savePlatform("user");
		} catch (IllegalHandleException e) {
			e.printStackTrace();
			// assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			e.printStackTrace();
			// assert (false) : "InvalidHandleException thrown incorrectly";

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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Finally here.");
		}

	}

}
