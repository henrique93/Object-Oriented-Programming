package poof;

import java.io.IOException;

public class EntryExistsExceptionC extends Exception {
	/** Invalid entry name. */
	private final String _entryName;

	/**
	 * @param entryName
	 */
	public EntryExistsExceptionC(String entryName) {
		_entryName = entryName;
	}
}