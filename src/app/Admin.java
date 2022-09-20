package app;

import java.util.ArrayList;

/**
 * this model class is based off singleton pattern to store solely the users list of Admin
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29 
 */
public class Admin {
	
	/**
	 * a list of users that saves all the user information
	 */
	public static ArrayList<User> usersList = new ArrayList<User>();
	
	/**
	 * a no argument constructor
	 */
	public Admin() {
	
	}
	
	/**
	 * returns the list of users
	 * @return the ArrayList of users
	 */
	public ArrayList<User> getUsers() {
		return usersList;
	}

}
