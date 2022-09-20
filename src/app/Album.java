package app;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

/**
 * This is the Album class that contains the information of an album
 * which includes the photos and the name of the album
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */

public class Album implements Serializable{

	/**
	 * version ID of the Album class
	 */
	private static final long serialVersionUID = 1237725019205013407L;

	/**
	 * the list that holds the photos of an album
	 */
	private ArrayList<Photo> photos = new ArrayList<Photo>();
	
	/**
	 * the name of the album
	 */
	private String name;
	
	/**
	 * the wrapped string property of the album name to display in the table of users
	 */
	private SimpleStringProperty named;

	/**
	 * the wrapped string property of the date range to display in the table of users
	 */
	private SimpleStringProperty date;
	
	/**
	 * the wrapped string property of the number of photos to display in the table of users
	 */
	private SimpleStringProperty numPhotos;
	/**
	 * no argument constructor for an album
	 * initializes name and list of photos to null
	 */
	public Album () {
		photos = null;
		name = "";
	}
	
	/**
	 * one argument constructor for Album
	 * @param name of Album
	 */
	public Album(String name) {
		this.name=name;
	}
	
	/**
	 * a three argument constructor for Album that sets the name, number of photos, and date range 
	 * @param name is the album name
	 * @param num is the number of photos
	 * @param date is the date range of photos in the album
	 */
	public Album(String name, String num, String date) {
		this.named = new SimpleStringProperty(name);
		this.numPhotos = new SimpleStringProperty(num);
		this.date = new SimpleStringProperty(date);

	}
	
	/**
	 * returns the list of photos
	 * @return photos which is an ArrayList of Photo
	 */
	public ArrayList<Photo> getPhotos() {  //getPhotos.get(index).getImage();
		return photos;
	}

	/**
	 * sets the photos ArrayList to the ArrayList parameter
	 * @param photos which is a new ArrayList of photos
	 */
	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}
	
	/**
	 * this method adds a Photo to the ArrayList of photos
	 * @param photo is the new picture being added to the album
	 */
	public void addPhoto(Photo photo) { 
		photos.add(photo);
	}
	
	/**
	 * returns the name of the album
	 * @return name is the String value of Album
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * sets a new album name if the album is renamed
	 * @param name is the String for the new name for the album
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * to get the simplestringproperty of the album name
	 * @return String value of album name
	 */
	public String getNamed() {
		return named.get();
	}


	/**
	 * to set the simplestringproperty of the album name
	 * @param named is the album name
	 */
	public void setNamed(String named) {
		this.named = new SimpleStringProperty(named);
	}

	/**
	 * to get the simplestringproperty of the date range
	 * @return the String of the date range
	 */
	public String getDate() {
		return date.get();
	}

	/**
	 * to set the simplestringproperty of the album name
	 * @param date is the date range
	 */
	public void setDate(String date) {
		this.date = new SimpleStringProperty(date);
	}

	/**
	 * to get the simplestringproperty of the number of photos
	 * @return the String of the number of photos
	 */
	public String getNumPhotos() {
		return numPhotos.get();
	}

	/**
	 * to set the simplestringproperty of the number of photos
	 * @param numPhotos is the number of photos in the album
	 */
	public void setNumPhotos(String numPhotos) {
		this.numPhotos = new SimpleStringProperty(numPhotos);
	}

	/**
	 * the toString method for Album returns its name
	 * @return String value of name
	 */
	public String toString() {
		return getName();
	}
	
}
