/** @version $Id: ChangeWorkingDirectory.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import poof.EntryUnknownExceptionC;  // FIXME: import project-specific classes
import poof.IsNotDirectoryExceptionC;

import poof.FSManager;

/**
 * §2.2.4.
 */
public class ChangeWorkingDirectory extends Command<FSManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public ChangeWorkingDirectory(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.CD, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException, EntryUnknownException, IsNotDirectoryException {
		System.out.println(Message.directoryRequest());
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String nameDirectory = stdin.readLine();
		try {
			_receiver.changeWorkDirectory(nameDirectory);
		}
		catch (EntryUnknownExceptionC e) {throw EntryUnknownException(nameDirectory); }
		catch (IsNotDirectoryExceptionC e) {throw IsNotDirectoryException(nameDirectory); }
	}

}
