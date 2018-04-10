package poof;

import java.io.Serializable;

public class User implements Serializable {
	private String _username;
	private String _name;

	public User(String username, String name){
		_username = username;
		_name = name;
	}

/**
 * @return This user's username
 */
	public String getUsername() {
		return _username;
	}

/**
 * @return This user's name
 */
	public String getName() {
		return _name;
	}
}
