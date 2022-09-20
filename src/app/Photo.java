package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.image.Image;

/**
 * This is the Photo class that stores the information of a photo including
 * its caption, date/time, tags, and the actual image itself
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */

public class Photo implements Serializable{
	
	/**
	 * Version ID for this class
	 */
	private static final long serialVersionUID = 759770130392048280L;
	
	/**
	 * the String that saves the file path to the image
	 */
	private String image;
	
	/**
	 * the String that saves the caption of the photo
	 */
	private String caption;
	
	/**
	 * the String that saves the date of the photo
	 */
	private String date; 
	
	/**
	 * the Date object that holds the date
	 */
	private Date dated;
	
	
	/**
	 * the list of tags that describe the photo
	 */
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	
	/**
	 * a no argument constructor that sets image, 
	 * caption, and dateTime to an empty string
	 */
	public Photo() {
		image = ("");
		setCaption("");
		setDateTime(null);
	}
	

	/**
	 * a three argument constructor to intialize the instance variable
	 * @param imageFile is the file path to the image being displayed
	 * @param c is the String value of caption
	 * @param date2 is the Calender object with the date and time stored
	 */
	public Photo(String imageFile, String c, Date date2) {
		image = imageFile;
		setCaption(c);
		this.dated = date2;
	}
	

	/**
	 * a three argument constructor to intialize the instance variable
	 * @param imageFile is the file path to the image being displayed
	 * @param c is the String value of caption
	 * @param date2 is the Calender object with the date and time stored
	 * @param date is the String of the date of the photo
	 */
	public Photo(String imageFile, String c, Date date2, String date) {
		image = imageFile;
		setCaption(c);
		this.dated = date2;
		this.date = date;
	}

	/**
	 * sets a new caption to the Photo if it's renamed
	 * @param caption is the String value of the caption set to a picture
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * sets a new dateTime to the Photo if edited
	 * @param date is the string value of the date
	 */
	public void setDateTime(String date) {
		this.date = date;
	}
	
	/**
	 * returns the image object being displayed
	 * @return the Image of the Photo
	 */
	public String getImage () {
		return image;
	}
	
	
	/**
	 * returns the caption of the Photo
	 * @return String value of the photo's caption
	 */
	public String getCaption() {
		if (caption.length()<1) return "";
		return caption;
	}
	
	/**
	 * returns the date stored for a photo
	 * @return the String value of the date
	 */
	public String getDate() {
		return date;
	}

	
	/**
	 * returns the Date object associated with the photo
	 * @return the Date object that stores the date and time
	 */
	public Date getDated() {
		return dated;
	}
	/**
	 * returns the list of tags about a picture
	 * @return an ArrayList of Tags
	 */
	public ArrayList<Tag> getTags(){
		return tags;
	}
}
