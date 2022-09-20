package app;

import java.io.Serializable;

/**
 * This is the Tag class that stores a tag name and tag value of one tag
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */
public class Tag implements Serializable{

	
	/**
	 * version ID for the Tag class
	 */
	private static final long serialVersionUID = -4411233571260876183L;
	
	/**
	 * String of the tag name of a Tag
	 */
	private String tagName;
	
	/**
	 * String of the tag value of a Tag
	 */
	private String tagValue;
	
	/**
	 * a no argument constructor of Tag that sets the Strings of the tag name and tag value to null
	 */
	public Tag() {
		tagName = null;
		tagValue = null;
	}
	
	/**
	 * a two argument constructor of Tag that initializes the tag name and tag value
	 * @param n is the String value of tag name
	 * @param v is the String value of tag value
	 */
	public Tag(String n, String v) {
		tagName = n;
		tagValue = v;
	}
	
	/**
	 * a getter method to return the tag name of the Tag
	 * @return String value of the tag name
	 */
	public String getName() {
		return tagName;
	}
	
	/**
	 * a getter method to return the tag value of the Tag
	 * @return String value of the tag value
	 */
	public String getValue() {
		return tagValue;
	}
	
	/**
	 * the toString method that returns the String value of the tag name and tag value
	 */
	public String toString() {
		return tagName + "=" + tagValue;
	}
	
}
