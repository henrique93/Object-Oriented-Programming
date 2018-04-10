package poof;

import java.io.Serializable;

public class File extends Entry implements Serializable {
/**
 * Text written in this file
 */
	private String _text = null;

	public File(String name, String username) {
		super.setName(name);
		super.setOwner(username);
	}

/**
 * @return File's text
 */
	public String getText() {
		return _text;
	}

/**
 * Change the text in this file
 * @param text  String to add to this file's text
 */
	public void changeText(String text) {
		_text += text;
	}

/**
 * @return File's size
 */
	public int getSize() {
		return _text.length();
	}
}
