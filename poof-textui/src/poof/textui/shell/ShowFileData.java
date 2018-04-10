/** @version $Id: ShowFileData.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
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

import poof.FSManager;

/**
 * §2.2.9.
 */
public class ShowFileData extends Command<FSManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public ShowFileData(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.CAT, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException, EntryUnknownException, IsNotFileException {
		System.out.println(Message.fileRequest());
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String nameFile = stdin.readLine();
		try {
			_receiver.showFile(nameFile);
		}
		catch (EntryUnknownExceptionC e) {throw EntryUnknownException(nameFile); }
		catch (IsNotFileExceptionC e) {throw IsNotFileException(nameFile); }
	}
}
