package poof;

import java.io.IOException;

public class IsNotDirectoryExceptionC extends Exception {
	/** Invalid entry name. */
	private final String _entryName;

	/**
	 * @param entryName
	 */
	public IsNotDirectoryExceptionC(String entryName) {
		_entryName = entryName;
	}
}