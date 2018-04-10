package poof;

import java.io.IOException;

public class UserExistsExceptionC extends Exception {
	/** Invalid user name. */
	private final String _username;

	/**
	 * @param username
	 */
	public UserExistsExceptionC(String username) {
		_username = username;
	}
}