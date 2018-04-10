/** @version $Id: MenuBuilder.java,v 1.1 2014/10/01 22:45:52 david Exp $ */
package poof.textui.main;

import ist.po.ui.Command;
import ist.po.ui.Menu;

import poof.FSManager;  // FIXME: import project-specific classes

/**
 * Menu builder.
 */
public abstract class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FSManager manager /*FIXME: receiver declaration */) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new New(manager /*FIXME: receiver argument*/),
				new Open(manager /*FIXME: receiver argument*/),
				new Save(manager /*FIXME: receiver argument*/),
				new Login(manager /*FIXME: receiver argument*/),
				new MenuOpenShell(manager /*FIXME: receiver argument*/),
				new MenuOpenUserManagement(manager /*FIXME: receiver argument*/)
		});
		menu.open();
	}

}
