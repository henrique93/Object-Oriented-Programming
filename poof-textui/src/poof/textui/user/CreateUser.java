/** @version $Id: CreateUser.java,v 1.1 2014/10/01 22:45:55 david Exp $ */
package poof.textui.user;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import poof.AccessDeniedExceptionC;  // FIXME: import project-specific classes
import poof.UserExistsExceptionC;

import poof.FSManager;

/**
 * §2.3.1.
 */
public class CreateUser extends Command<FSManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public CreateUser(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.CREATE_USER, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException, AccessDeniedException, UserExistsException {
		System.out.println(Message.nameRequest());
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String name = stdin.readLine();
		System.out.println(Message.usernameRequest());
		String username = stdin.readLine();
		try {
			_receiver.newUser(username, name);
		}
		catch (AccessDeniedExceptionC e) {throw AccessDeniedException(nameFile); }
		catch (UserExistsExceptionC e) {throw UserExistsException(username); }
	}
}
