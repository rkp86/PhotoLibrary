package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Collections;
import java.util.Comparator;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * This is the User class that stores the username and photo albums of a user
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */

public class User implements Serializable{

	/**
	 * version ID for the User class
	 */
	private static final long serialVersionUID = -7922120607881767783L;


	/**
	 * the list of albums under a user
	 */
	private ArrayList<Album> albums;
	
	/**
	 * the name of the user
	 */
	private String name;
	
	/**
	 * a no argument constructor that sets the list of albums and name to null
	 */
	public User() {
		albums = null;
		setName(null);
	}
	
	/**
	 * one argument constructor that creates a User with its name and initializes albums
	 * @param n is the name of the user
	 */
	public User(String n) {
		albums = new ArrayList<Album>();
		setName(n);
	}
	
	/**
	 * returns the list of albums
	 * @return the ArrayList of Albums of a user
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}
	
	/**
	 * returns the name of an album at an index
	 * @param i is the index of the album
	 * @return the String vaule of the album name
	 */
	public String getAlbum(int i) {
		return albums.get(i).getName();
	}
	
	/**
	 * creates a new album by adding it to the list of albums
	 * after checking for duplicates
	 * @param album is the new album being added
	 */
	public void addAlbum(Album album) {
		boolean duplicate = false; 
    	for (Album a : albums) {
    		if (a.getName().equals(album.getName()))
    			duplicate = true;
    	} 
    	
    	if (!duplicate)
    		albums.add(album);
    	else if(!album.getName().equals("stock")) { 
    		Alert alert = new Alert(AlertType.ERROR, "Album name already exists.", ButtonType.OK);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {	
			}
    	}  	
		Collections.sort(albums,albumNameCompare);
		duplicate = false;
		
	}
	
	/**
	 * uses the functional interface Comparator and does an anonymous interface implementation
	 * to sort the Album names under a user
	 */
	public static Comparator<Album> albumNameCompare = new Comparator<Album>() {

		public int compare(Album a1, Album a2) {
		   String AlbumName1 = a1.getName().toLowerCase();
		   String AlbumName2 = a2.getName().toLowerCase();

		   //ascending order
		   return AlbumName1.compareTo(AlbumName2);

		   //descending order
		   //return StudentName2.compareTo(StudentName1);
	    }};
	    
	/**
	 * returns the name of the users
	 * @return the string value of username
	 */
	public String getName() {
		return name; //username
	}
	
	/**
	 * sets a new name to the user
	 * @param name of the use
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * the toString method returns the name of the user
	 */
	public String toString() {
		return getName();
	}
}
