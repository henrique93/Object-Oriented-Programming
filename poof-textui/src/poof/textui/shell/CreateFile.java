/** @version $Id: CreateFile.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import poof.EntryExistsExceptionC;  // FIXME: import project-specific classes
import poof.AccessDeniedExceptionC;

import poof.FSManager;

/**
 * §2.2.5.
 */
public class CreateFile extends Command<FSManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public CreateFile(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.TOUCH, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException, EntryExistsException, AccessDeniedException {
		System.out.println(Message.fileRequest());
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String nameFile = stdin.readLine();
		try {
			_receiver.newDirectory(nameFile);
		}
		catch (EntryExistsExceptionC e) {throw EntryExistsException(nameFile); }
		catch (AccessDeniedExceptionC e) {throw AccessDeniedException(nameFile); }
	}

}
