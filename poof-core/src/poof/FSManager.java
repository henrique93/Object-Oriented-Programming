package poof;

import java.util.TreeMap;

import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;

public class FSManager {
	private User _currentUser;
	private Directory _currentDirectory;
/**
 * Currently opened FileSystem, starts as null (no FileSystem opened)
 */
	private FileSystem _currentFileSystem = null;

/**
 * Name of the file on which the FileSystem is saved, null if it was
 * never saved yet
 */
	private String _FSName = null;

/**
 * Change the current user
 * @param username
 */
	public void login(String username) throws UserUnknownExceptionC {
		User user = _currentFileSystem.getUser(username);
		if (user != null) {
			_currentUser = user;
			while (_currentDirectory.getName() != "home");
				_currentDirectory = _currentDirectory.getParent();
			_currentDirectory = (Directory)_currentDirectory.getEntry(username);
		}
		else throw new UserUnknownExceptionC(username);	/* Throws Exception 8 */
	}

/**
 * Check if exists a current FileSystem
 * @return true if _currentFileSystem is not null; false otherwise
 */
	public boolean existFileSystem() {
		return _currentFileSystem != null;
	}

/**
 * Check if the current FileSystem is associated with a file (has been
 * saved at some point)
 * @return true if _FSName is not null; false otherwise
 */
	public boolean associatedFileSystem() {
		return _FSName != null;
	}
/**
 * Create a FileSystem
 */
	public void createFileSystem(){
		_currentFileSystem = new FileSystem();
		_FSName = null;
		try {
		login("root");
		}
		catch (UserUnknownExceptionC e ) {}
		
	}

/**
 * Open a FileSystem with a specific name
 * @param name  File's name on which the FileSystem is saved
 */
	public void openFileSystem(String name) throws UserUnknownExceptionC, IOException{
		try {
			ObjectInputStream ios = new ObjectInputStream(new BufferedInputStream(new FileInputStream(name)));
			FileSystem  _currentFileSystem = (FileSystem)ios.readObject();
			User _currentUser = (User)ios.readObject();
			login(_currentUser.getName());
			ios.close();
			_FSName = name;
		}
		catch (UserUnknownExceptionC e ) {}
		catch (ClassNotFoundException e) {}
	}

/**
 * Read a file with commands and executes them
 * @param name  File's name
 */
	public void ReadFile(String name) throws UserUnknownExceptionC {
		try {
			BufferedReader file = new BufferedReader(new FileReader(name));
			String line = file.readLine();
			while (line != null) {
				String[] parts = line.split("\\|");
				switch(parts[0]) {
					case "USER":		try {
									newUser(parts[1], parts[2]);
								}
								
								catch (AccessDeniedExceptionC e){}
								catch (UserExistsExceptionC e) {}

					case "DIRECTORY": 	String[] dir = parts[1].split("/");
								login(parts[2]);
								boolean permission;
								for (String nameDirectory : dir) {
									Directory directory = new Directory(name, _currentUser.getName(), _currentDirectory);
									_currentDirectory.putEntry(directory);
									_currentDirectory = directory;
								}
								if (parts[3].equals("public"))
									permission = true;
								else
									permission = false;
								_currentDirectory.setPermission(permission);

				/*	case "FILE": 		String[] dir = parts[1].split("/");
								login(parts[2]);
								boolean permission;
								for (String nameDirectory : dir) {
									Directory directory = new Directory(name, _currentUser.getName(), _currentDirectory);
									_currentDirectory.putEntry(directory);
									_currentDirectory = directory;
								}
								if (parts[3].equals("public"))
									permission = true;
								else
									permission = false;
								fileToAdd = newFile(dir.get(dir.size()-1), _currentUser.getName, _currentDirectory);
								fileToAdd.setPermission(permission);
								fileToAdd.changeText(parts[4]);
								*/
				}
			}
		}
		catch (IOException e) { e.printStackTrace(); }
	}

/**
 * Save current FileSystem, this method is used if the current
 * FileSystem is associated to a file (has been saved before)
 */
	public void saveFileSystem() {
		try {
			ObjectOutputStream ios = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_FSName)));
			ios.writeObject(_currentFileSystem);
			ios.writeObject(_currentUser);
			ios.close();
		}
		catch (IOException e) { e.printStackTrace(); }
	}

/**
 * Save current FileSystem, this method is used if the current
 * FileSystem is not associated to a file (was never saved before)
 * @param name  File's name to save the FileSystem
 */
	public void saveFileSystem(String name) {
		try {
			ObjectOutputStream ios = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(name)));
			ios.writeObject(_currentFileSystem);
			ios.writeObject(_currentUser);
			ios.close();
		}
		catch (IOException e) { e.printStackTrace(); }
	}

/**
 * List all entries in the current directory
 */
	public void list() {
		_currentFileSystem.list(_currentDirectory);
	}

/**
 * List an entry with a specific name in the current directory
 * @param nameEntry
 */
	public void listEntry(String nameEntry) throws EntryUnknownExceptionC { // Exception 2
		_currentFileSystem.listEntry(nameEntry, _currentDirectory);
	}

/**
 * Remove a specific entry from the current directory
 * @param nameEntry
 */
	public void removeEntry(String nameEntry) throws EntryUnknownExceptionC, AccessDeniedExceptionC, IllegalRemovalExceptionC { // Exceptions 2, 5, 6
		_currentFileSystem.removeEntry(nameEntry, _currentUser, _currentDirectory);
	}

/**
 * Change the current directory to either one it contains or to it's parent directory
 * @param nameDirectory
 */
	public void changeWorkDirectory(String nameDirectory) throws EntryUnknownExceptionC, IsNotDirectoryExceptionC { // Exceptions 2, 3
		if (_currentDirectory.getEntry(nameDirectory) instanceof Directory) {
			if (nameDirectory.equals(".."))
				_currentDirectory = _currentDirectory.getParent();
			if (nameDirectory.equals(".")) { }
			else
				_currentDirectory = (Directory)_currentDirectory.getEntry(nameDirectory);
		}
		else if (_currentDirectory.getEntry(nameDirectory) == null)
			 throw new EntryUnknownExceptionC(nameDirectory);	// Throw Exception 2
		else
			throw new IsNotDirectoryExceptionC(nameDirectory);	// Throw Exception 3
	}

/**
 * Creates a new file in the current directory
 * @param nameFile
 * @return  		Created file
 */
	public File newFile(String nameFile) throws EntryExistsExceptionC, AccessDeniedExceptionC { // Exceptions 1, 5
		return _currentFileSystem.newFile(nameFile, _currentUser.getUsername(), _currentDirectory);
	}

/**
 * Creates a new directory in the current directory
 * @param nameDirectory  
 * @return				 Created directory
 */
	public Directory newDirectory(String nameDirectory) throws EntryExistsExceptionC, AccessDeniedExceptionC { // Exceptions 1, 5
		return _currentFileSystem.newDirectory(nameDirectory, _currentUser.getUsername(), _currentDirectory);
	}

/**
 * Show the complete path from directory / to current directory
 * @param directory  Starts with the current directory, then receives it's parent because of the recursion
 * @return path		 String with the complete path
 */
	public String showCurrentPath(Directory directory) {  // Starts with _currentDirectory
		String path = null;
		if (directory.getParent() != directory)
			return (showCurrentPath(directory.getParent()) + '/' + directory.getName());
		else
			return path;
	}

/**
 * Modify a specific file in the current directory adding a text
 * @param nameFile
 * @param text		Text to add to file
 */
	public void changeFile(String nameFile, String text) throws EntryUnknownExceptionC, IsNotFileExceptionC, AccessDeniedExceptionC { // Exceptions 2, 4, 5
		_currentFileSystem.changeFile(nameFile, text, _currentUser.getUsername(), _currentDirectory);
	}

/**
 * Show the content of a specific file in the current directory
 * @param nameFile
 * @return 			String with the file's text
 */
	public String showFile(String nameFile) throws EntryUnknownExceptionC, IsNotFileExceptionC { // Exceptions 2, 4
		return _currentFileSystem.showFile(nameFile, _currentDirectory);
	}

/**
 * Change the permission (to public or private) of a specific entry in the current directory
 * @param nameEntry
 * @param permission  Permission to change to
 */
	public void changePermission(String nameEntry, boolean permission) throws EntryUnknownExceptionC, AccessDeniedExceptionC { // Exceptions 2, 5
		_currentFileSystem.changePermission(nameEntry, permission, _currentUser.getUsername(), _currentDirectory);
	}

/**
 * Change the owner of a specific entry in the current directory
 * @param nameEntry
 * @param username	 New owner's username
 */
	public void changeOwner(String nameEntry, String username) throws EntryUnknownExceptionC, AccessDeniedExceptionC, UserUnknownExceptionC { // Exceptions 2, 5, 8
		_currentFileSystem.changeOwner(nameEntry, username, _currentUser, _currentDirectory);
	}

/**
 * Create a new user
 * @param username
 * @param name
 */
	public void newUser(String username, String name) throws AccessDeniedExceptionC, UserExistsExceptionC { // Exceptions 5, 7
		_currentFileSystem.newUser(username, name, _currentUser);
	}

/**
 * List all existing users
 * @return String with all existing users in this FileSystem listed
 */
	public String listUser() {
		return _currentFileSystem.listUser();
	}
}
