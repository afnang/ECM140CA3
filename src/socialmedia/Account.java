package socialmedia;


import java.io.Serializable;

/**
 * @author at753,ag811
 * This is a class of accounts, it keeps all the needed attributes of an account.
 */
public class Account implements Serializable {

    int id;
    String Handle;
    String Description;
    boolean Exists;
    int endorsementCount;

    /**
     * @return returns a boolean to show if an account is deleted or not.
     */
    public boolean isExists() {
        return Exists;
    }

    /**
     * @param exists is a boolean to show if an account is deleted or not.
     */
    public void setExists(boolean exists) {
        Exists = exists;
    }

    /**
     * @return returns the id of an account.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id of an account.
     */
    public void setId(int id) {
        this.id = id;

    }

    /**
     * @return returns the handle of an account.
     */
    public String getHandle() {
        return Handle;
    }

    /**
     * @param handle the handle of an account.
     */
    public void setHandle(String handle) {
        Handle = handle;
    }

    /**
     * @return returns the description of an account.
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param description the description of an account
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Constructor method for Account class.
     */
    public Account() {
        super();
        // TODO Auto-generated constructor stub
    }

}