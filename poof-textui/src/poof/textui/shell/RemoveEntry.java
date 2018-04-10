/** @version $Id: RemoveEntry.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import poof.EntryUnknownExceptionC;  // FIXME: import project-specific classes
import poof.AccessDeniedExceptionC;
import poof.IllegalRemovalExceptionC;

import poof.FSManager;

/**
 * §2.2.3.
 */
public class RemoveEntry extends Command<FSManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public RemoveEntry(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.RM, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException, EntryUnknownExceptionm, AccessDeniedException, IllegalRemovalException {
		System.out.println(Message.nameRequest());
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String nameEntry = stdin.readLine();
		try {
			_receiver.removeEntry(nameEnry);
		}
		catch (EntryUnknownExceptionC e) {throw EntryUnknownException(nameEntry); }
		catch (AccessDeniedExceptionC e) {throw AccessDeniedException(nameEntry); }
		catch (IllegalRemovalExceptionC e) {throw IllegalRemovalException(); }
	}
}
