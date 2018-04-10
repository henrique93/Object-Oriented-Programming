/** @version $Id: New.java,v 1.1 2014/10/01 22:45:53 david Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FSManager;  // FIXME: import project-specific classes

/**
 * Open a new file.
 */
public class New extends Command<FSManager> /* FIXME: select core type for receiver */ {

	/**
	 * @param receiver
	 */
	public New(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.NEW, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		_receiver.createFileSystem();//FIXME: implement command
	}

}
