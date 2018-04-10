/** @version $Id: ListAllEntries.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FSManager;  // FIXME: import project-specific classes

/**
 * §2.2.1.
 */
public class ListAllEntries extends Command<FSManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public ListAllEntries(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.LS, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		_receiver.list();
	}

}
