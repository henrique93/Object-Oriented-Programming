package poof;

public abstract class Entry {
	private String _name;
/**
 * Entry's permission, every entry starts as private (false)
 */
	private boolean _permission = false;  /* Public = True, Private = False */
	private String _owner;

/**
 * @return Entry's name
 */
	public String getName() {
		return _name;
	}

/**
 * Set a name to this entry
 */
	public void setName(String name) {
		_name = name;
	}

/**
 * @return Entry's permission
 */
	public boolean getPermission() {
		return _permission;
	}

/**
 * Change this entry's permission (true is public, false is private)
 */
	public void setPermission(boolean permission) {
		_permission = permission;
	}

/**
 * @return Entry's owner
 */
	public String getOwner() {
		return _owner;
	}

/**
 * Set a owner to this entry
 */
	public void setOwner(String username) {
		_owner = username;
	}
}
