/** @version $Id: MenuBuilder.java,v 1.1 2014/10/01 22:45:55 david Exp $ */
package poof.textui.user;

import ist.po.ui.Command;
import ist.po.ui.Menu;

import poof.FSManager;  // FIXME: import project-specific classes

/**
 * Menu builder for search operations.
 */
public class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FSManager manager /*FIXME: receiver declaration*/) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new CreateUser(manager /*FIXME: receiver argument*/),
				new ListAllUsers(manager /*FIXME: receiver argument*/),
				});
		menu.open();
	}

}
