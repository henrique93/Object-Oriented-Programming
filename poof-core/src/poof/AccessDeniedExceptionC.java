package poof;

import java.io.IOException;

public class AccessDeniedExceptionC extends Exception {
	/** Invalid user id. */
	private final String _username;

	/**
	 * @param name
	 */
	public AccessDeniedExceptionC(String name) {
		_username = name;
	}
}