package poof;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.Collection;

public class Directory extends Entry implements Serializable {
/**
 * _parent directory - directory which contains this directory
 */
	private Directory _parent;

/**
 * entries - map which contains every entry in this entry
 */
	private TreeMap<String, Entry> entries = new TreeMap<String, Entry>();

/**
 * Constructor to create a usual directory
 * @param name
 * @param username	Owner's username
 * @param parent
 */
	public Directory(String name, String username, Directory parent) {
		super.setName(name);
		super.setOwner(username);
		_parent = parent;
	}

/**
 * Constructor to create specificly the / directory
 */
	public Directory() {
		super.setName("/");
		super.setOwner("root");
		_parent = this;
	}

/**
 * @return Directory's size
 */
	public int getSize() {
		return (8 * (entries.size() + 2));
	}

/**
 * @return Directory's parent
 */
	public Directory getParent() {
		return _parent;
	}

/**
 * @return Specific entry in this directory
 */
	public Entry getEntry(String name) {
		return entries.get(name);
	}

/**
 * Add a new entry to the directory
 */
	public void putEntry(Entry entry) {
		entries.put(entry.getName(), entry);
	}

/**
 * @return Every entry in this directory
 */
	public Collection<Entry> getValues() {
		return entries.values();
	}

/**
 * Delete a specific entry from this directory
 */
	public void delEntry(String nameEntry) {
		entries.remove(nameEntry);
	}
}
