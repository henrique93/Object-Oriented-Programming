package poof;

import java.io.IOException;

public class UserUnknownExceptionC extends Exception {
	/** Invalid user name. */
	private final String _username;

	/**
	 * @param username
	 */
	public UserUnknownExceptionC(String username) {
		_username = username;
	}
} 
