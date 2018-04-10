package poof;

import java.io.IOException;

public class EntryUnknownExceptionC extends Exception {
	/** Invalid entry name. */
	private final String _entryName;

	/**
	 * @param entryName
	 */
	public EntryUnknownExceptionC(String entryName) {
		_entryName = entryName;
	}
}