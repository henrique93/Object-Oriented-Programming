package poof;

import java.io.Serializable;
import java.util.TreeMap;

public class FileSystem implements Serializable {
/**
 * Directory / is the first directory created and it is it's own parent
 */
	private Directory dirSlash;

/**
 * Users is a map which contains every user created in this FileSystem
 */
	private TreeMap<String, User> users = new TreeMap<String, User>();

/**
 * FileSystem constructor, creates a FileSystem, creates a super user, 
 * creates a directory /, creates a directory home in the directory /
 * and creates a diretory root in the directory home
 */	
	public FileSystem() {
		Directory currentDirectory;
		users.put("root", new User("root", "super user"));	/* creates the Super User */
		Directory dirSlash = new Directory();		/* creates the directory "/" */
		currentDirectory = new Directory("home", "root", dirSlash);	/* creates the directory "home" */
		dirSlash.putEntry(currentDirectory);		/* puts directory "home" in the directory "/" */
		currentDirectory.putEntry(new Directory("root", "root", currentDirectory));	/* creates and puts the directory "root" in the directory "home" */
	}

/**
 * @param useraname
 * @return user who has the received username
 */
	public User getUser(String username){
		return users.get(username);
	}

/**
 * @return Directory /
 */
	public Directory getSlash() {
		return dirSlash;
	}

/**
 * list all entries in the current directory
 * @param directory  Current directory
 * @return String with all the entries in the current directory
 */
	public String list(Directory directory) {
		String EntryList = null;
		for (Entry entry : directory.getValues()) {
			if (entry.getPermission() == true && entry instanceof File){
				File file = (File)entry;
				if (file.getPermission() == true)
					EntryList += "- w " + file.getOwner() + " " + file.getSize() + " " + file.getName();
				else
					EntryList += "- - " + file.getOwner() + " " + file.getSize() + " " + file.getName();
			}
			else if (entry instanceof Directory) {
				Directory dir = (Directory)entry;
				if (dir.getPermission() == true)
					EntryList += "d w " + dir.getOwner() + " " + dir.getSize() + " " + dir.getName();
				else
					EntryList += "d - " + dir.getOwner() + " " + dir.getSize() + " " + dir.getName();
			}
		}
		return EntryList;
	}

/**
 * lists a chosen entry in the current directory
 * @param nameEntry
 * @param directory  Current directory
 * @return String with the listed entry
 */
	public String listEntry(String nameEntry, Directory directory) throws EntryUnknownExceptionC {  /* [Exception 2] */
		Entry entry = directory.getEntry(nameEntry);
		if (entry != null &&  entry instanceof File) {
			File file = (File)entry;
			if (file.getPermission() == true)
				return ("- w " + file.getOwner() + " " + file.getSize() + " " + file.getName());
			else
				return ("- - " + file.getOwner() + " " + file.getSize() + " " + file.getName());
		}
		else if (entry instanceof Directory) {
			Directory dir = (Directory)entry;
			if (dir.getPermission() == true)
				return ("d w " + dir.getOwner() + " " + dir.getSize() + " " + dir.getName());
			else
				return ("d - " + dir.getOwner() + " " + dir.getSize() + " " + dir.getName());
		}
		throw new EntryUnknownExceptionC(nameEntry); /* Throw exception 2 if entry is not known */
	}

/**
 * Remove an entry from the current directory
 * @param nameEntry
 * @param user		 Current user
 * @param directory	 Current directory
 */
	public void removeEntry(String nameEntry, User user, Directory directory) throws EntryUnknownExceptionC, AccessDeniedExceptionC, IllegalRemovalExceptionC {  /* [Exceptions 2, 5, 6] */
		Entry entry = directory.getEntry(nameEntry);
		if ((entry.getPermission() == false || directory.getPermission() == false) && (!(entry.getOwner().equals(user.getUsername())) || !(directory.getOwner().equals(user.getUsername()))) && !(user.getUsername().equals("root")))
			throw new AccessDeniedExceptionC(user.getUsername());  /* Throw exception 5 if current user has not permission to remove entry */
		else if (directory.getEntry(nameEntry) == null)
			throw new EntryUnknownExceptionC(nameEntry);  /* Throw exception 2 if entry is not known */
		else if (directory.getEntry(nameEntry).equals(".") || directory.getEntry(nameEntry).equals(".."))
			throw new IllegalRemovalExceptionC();  /* Throw exception 6 if current user is trying to remove directory . or .. */
		else {
			directory.delEntry(nameEntry);
		}
	}

/**
 * Create a new file in the current directory
 * @param nameFile
 * @param username  Current user's username, will be the file's owner
 * @param current	Current directory
 */
	public File newFile(String nameFile, String username, Directory current) throws EntryExistsExceptionC, AccessDeniedExceptionC {  /* [Exceptions 1, 5] */
		if (current.getEntry(nameFile) != null)
			throw new EntryExistsExceptionC(nameFile);  /* Throw exception 1 if an entry with the chosen name already exists in current directory */
		else if ((current.getPermission() == false) && (!(current.getOwner().equals(username))) && !(username.equals("root")))
			throw new AccessDeniedExceptionC(username);  /* Throw exception 5 if current user has not permission to create a file in current directory */
		else {
			File file = new File(nameFile, username);
			current.putEntry(file);
			return file;
		}
	}

/**
 * Create a new directory in the current directory
 * @param nameDirectory
 * @param username		 Current user's username, will be the directory's owner
 * @param current		 Current directory
 */
	public Directory newDirectory(String nameDirectory,String username, Directory current) throws EntryExistsExceptionC, AccessDeniedExceptionC {  /* [Exceptions 1, 5] */
		if (current.getEntry(nameDirectory) != null)
			throw new EntryExistsExceptionC(nameDirectory);  /* Throw exception 1 if an entry with the chosen name already exists in current directory */
		else if ((current.getPermission() == false) && (!(current.getOwner().equals(username))) && !(username.equals("root")))
			throw new AccessDeniedExceptionC(username);  /* Throw exception 5 if current user has not permission to create a file in current directory */
		else {
			Directory directory = new Directory(nameDirectory, username, current);
			current.putEntry(directory);
			return directory;
		}
	}

/**
 * Modify a specific file from the current directory
 * @param nameFile
 * @param text		 String to add to file's text
 * @param username	 Current user's username, may or may not have permission to change the file
 * @param directory  Current directory
 */
	public void changeFile(String nameFile, String text, String username, Directory directory) throws EntryUnknownExceptionC, IsNotFileExceptionC, AccessDeniedExceptionC { /* [Exceptions 2, 4, 5] */
		if (directory.getEntry(nameFile) == null)
			throw new EntryUnknownExceptionC(nameFile);  /* Throw exception 2 if entry is not known */
		else if (!(directory.getEntry(nameFile) instanceof File))
			throw new IsNotFileExceptionC(nameFile);  /* Throw exception 4 if entry is not a file */
		else if ((directory.getPermission() == false) && (!(directory.getOwner().equals(username))) && !(username.equals("root")))
			throw new AccessDeniedExceptionC(username);  /* Throw exception 5 if current user does not have permission to change the file */
		else{
			File file = (File) directory.getEntry(nameFile);
			file.changeText(text);
		}
	}

/**
 * Show a specific file's text
 * @param nameFile
 * @param directory  Current directory
 * @return 			 String with the file's text
 */
	public String showFile(String nameFile, Directory directory) throws EntryUnknownExceptionC, IsNotFileExceptionC { /* [Exceptions 2, 4] */
		if (directory.getEntry(nameFile) == null)
			throw new EntryUnknownExceptionC(nameFile);  /* Throw exception 2 if entry is not known */
		else if (!(directory.getEntry(nameFile) instanceof File))
			throw new IsNotFileExceptionC(nameFile);  /* Throw exception 4 if entry is not a file */
		else{
		File file = (File)directory.getEntry(nameFile);
		return file.getText();
		}
	}

/**
 * Change a specific entry's permission
 * @param nameEntry
 * @param permission  Permission to change the file's permission (true is public, false is private)
 * @param directory	  Current Directory
 */
	public void changePermission(String nameEntry, boolean permission, String username, Directory directory) throws EntryUnknownExceptionC, AccessDeniedExceptionC {  /* [Exceptions 2, 5] */
		if (directory.getEntry(nameEntry) == null)
			throw new EntryUnknownExceptionC(nameEntry);  /* Throw exception 2 if entry is not known */
		else if ((directory.getPermission() == false) && (!(directory.getOwner().equals(username)) || !(directory.getEntry(nameEntry).getOwner().equals(username))) && !(username.equals("root")))
			throw new AccessDeniedExceptionC(username);  /* Throw exception 5 if current user does not have permission to change the file */
		else {
			directory.getEntry(nameEntry).setPermission(permission);
		}
	}

/**
 * Change a specific entry's owner
 * @param nameEntry
 * @param username	 New owner's username
 * @param user       Current user, may or may not have permission to change entry's owner
 * @param directory  Current directory
 */
	public void changeOwner(String nameEntry, String username, User user, Directory directory) throws EntryUnknownExceptionC, AccessDeniedExceptionC, UserUnknownExceptionC {  /* [Exceptions 2, 5, 8] */
		if (directory.getEntry(nameEntry) == null)
			throw new EntryUnknownExceptionC(nameEntry);  /* Throw exception 2 if entry is not known */
		else if ((directory.getPermission() == false || directory.getEntry(nameEntry).getPermission() == false) && (!(directory.getOwner().equals(username)) || !(directory.getEntry(nameEntry).getOwner().equals(user.getUsername()))) && !(user.getUsername().equals("root")))
			throw new AccessDeniedExceptionC(username);  /* Throw exception 5 if current user does not have permission to change the file */
		else if (users.get(username) == null)
			throw new UserUnknownExceptionC(username);  /* Throw exception 8 if new owner does not exist */
		else {
			directory.getEntry(nameEntry).setOwner(username);
		}
	}

/**
 * Create a new user
 * @param username
 * @param name
 * @param current   Current user, only the super user has permission to create a new user
 */
	public void newUser(String username, String name, User current) throws AccessDeniedExceptionC, UserExistsExceptionC {  /* [Exceptions 5, 7] */
		if (current != users.get("root"))
			throw new AccessDeniedExceptionC(username);  /* Throw exception 5 if current user is not Super User */
		else if (users.get(username) != null)
			throw new UserExistsExceptionC(username);  /* Throw exception 7 if a user with the chosen username already exists */
		else {
			User user = new User(username, name);
			users.put(user.getUsername(), user);
			Directory home = (Directory)dirSlash.getEntry("home");
			home.putEntry(new Directory("username", "username", home));
		}
	}

/**
 * List all existing users
 * @return String with all existing users in this FileSystem listed
 */
	public String listUser() {
		String UserList = null;
		for (User usr : users.values()) {
			UserList += (usr.getUsername() + ':' + usr.getName() + ":/home/" + usr.getUsername() + '\n');
		}
		return UserList;
	}
}
