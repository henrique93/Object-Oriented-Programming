/** @version $Id: MenuBuilder.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import ist.po.ui.Command;
import ist.po.ui.Menu;

import poof.FSManager;  // FIXME: import project-specific classes

/**
 * Menu builder for shell operations.
 */
public class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FSManager manager /*FIXME: receiver declaration*/) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new ListAllEntries(manager /*FIXME: receiver argument*/),
				new ListEntry(manager /*FIXME: receiver argument*/),
				new RemoveEntry(manager /*FIXME: receiver argument*/),
				new ChangeWorkingDirectory(manager /*FIXME: receiver argument*/),
				new CreateFile(manager /*FIXME: receiver argument*/),
				new CreateDirectory(manager /*FIXME: receiver argument*/),
				new ShowWorkingDirectory(manager /*FIXME: receiver argument*/),
				new AppendDataToFile(manager /*FIXME: receiver argument*/),
				new ShowFileData(manager /*FIXME: receiver argument*/),
				new ChangeEntryPermissions(manager /*FIXME: receiver argument*/),
				new ChangeOwner(manager /*FIXME: receiver argument*/),
				});
		menu.open();
	}

}
