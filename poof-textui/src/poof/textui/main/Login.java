/** @version $Id: Login.java,v 1.1 2014/10/01 22:45:52 david Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import poof.UserUnknownExceptionC;  // FIXME: import project-specific classes

import poof.FSManager;

/**
 * §2.1.2.
 */
public class Login extends Command<FSManager> /* FIXME: select core type for receiver */ {

	/**
	 * @param receiver
	 */
	public Login(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.LOGIN, manager /*FIXME: receiver argument*/, new ValidityPredicate<FSManager>(manager) /*FIXME: validity argument*/ {
									public boolean isValid() {
										return manager.existFileSystem();
									}
								});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException, UserUnknownException {
		System.out.println(Message.usernameRequest());
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String username = stdin.readLine();
		try {
			_receiver.login(username);
		}
		catch (UserUnknownExceptionC e) { throw UserUnknownException(username); }
	}
}
