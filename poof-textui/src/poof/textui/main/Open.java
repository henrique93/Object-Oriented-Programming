/** @version $Id: Open.java,v 1.1 2014/10/01 22:45:53 david Exp $ */
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
 * Open existing file.
 */
public class Open extends Command<FSManager> /* FIXME: select core type for receiver */ {

	/**
	 * @param receiver
	 */
	public Open(FSManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.OPEN, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException{
		System.out.println(Message.openFile());//FIXME: implement command
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String fileName = stdin.readLine();
		try {
		  _receiver.openFileSystem(name);
		}
		catch (DialogException e) { System.out.println(Message.fileNotFound()); }
		catch (IOException e) { }
	}

}
