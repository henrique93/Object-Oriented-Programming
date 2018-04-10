/** @version $Id: Save.java,v 1.1 2014/10/01 22:45:53 david Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import poof.FSManager;  // FIXME: import project-specific classes

/**
 * Save to file under current name (if unnamed, query for name).
 */
public class Save extends Command<FSManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public Save(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.SAVE, manager /*FIXME: receiver argument*/, new ValidityPredicate<FSManager>(manager) /*FIXME: validity argument*/ {
									public boolean isValid() {
										return manager.existFileSystem();
									}
								});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		if (manager.associatedFileSystem() == true) //FIXME: implement command
			_receiver.saveFileSystem();
		else {
			System.out.println(Message.newSaveAs());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String name = stdin.readLine();
			_receiver.saveFileSystem(name);
		}
	}
}
