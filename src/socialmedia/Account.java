package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;
public class Account implements Serializable {
	int id;
	String Handle;
	String Description;
	boolean Exists;
	int endorsementCount;
	public boolean isExists() {
		return Exists;
	}

	public void setExists(boolean exists) {
		Exists = exists;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHandle() {
		return Handle;
	}

	public void setHandle(String handle) {
		Handle = handle;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

}
