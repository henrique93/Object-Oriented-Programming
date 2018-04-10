/** @version $Id: MenuOpenUserManagement.java,v 1.1 2014/10/01 22:45:53 david Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FSManager;  // FIXME: import project-specific classes

/**
 * Open user management menu.
 */
public class MenuOpenUserManagement extends Command<FSManager> /* FIXME: select core type for receiver */ {

	/**
	 * @param receiver
	 */
	public MenuOpenUserManagement(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.MENU_USER_MGT, manager /*FIXME: receiver argument*/, new ValidityPredicate<FSManager>(manager) /*FIXME: validity argument*/ {
									public boolean isValid() {
										return manager.existFileSystem();
									}
								});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() {
		poof.textui.user.MenuBuilder.menuFor(manager /*FIXME: receiver argument*/);
	}

}
