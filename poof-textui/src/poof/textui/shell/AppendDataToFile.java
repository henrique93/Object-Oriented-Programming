/** @version $Id: AppendDataToFile.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import poof.EntryUnknownExceptionC;  // FIXME: import project-specific classes
import poof.IsNotFileExceptionC;
import poof.AccessDeniedExceptionC;

import poof.FSManager;

/**
 * §2.2.8.
 */
public class AppendDataToFile extends Command<FSManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public AppendDataToFile(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.APPEND, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException, EntryUnknownException, IsNotFileException, AccessDeniedException {
		System.out.println(Message.fileRequest());
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String fileName = stdin.readLine();
		System.out.println(Message.textRequest());
		String text = stdin.readLine();
		try {
			_receiver.changeFile(fileName, text);
		}
		catch (EntryUnknownExceptionC e) {throw EntryUnknownException(fileName); }
		catch (IsNotFileExceptionC e) {throw IsNotFileException(fileName); }
		catch (AccessDeniedExceptionC e) {throw AccessDeniedException(fileName); }
	}

}
