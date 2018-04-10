package poof;

import java.io.IOException;

public class IsNotFileExceptionC extends Exception {
	/** Invalid entry name. */
	private final String _entryName;

	/**
	 * @param entryName
	 */
	public IsNotFileExceptionC(String entryName) {
		_entryName = entryName;
	}
}