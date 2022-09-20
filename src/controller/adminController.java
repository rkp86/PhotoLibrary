package controller;

import java.io.FileOutputStream;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import app.Admin;
import app.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This is admin Controller class that controlls the UI for the admin stage
 * It helps display the different buttons and textfields and the list of users
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */
public class adminController {

	/**
	 * creates a new stage for different fxml files
	 */
	@FXML
	static Stage stage;
	
	/**
	 * creates a new scene to display the stage
	 */
	@FXML
	static Scene scene;
	
	/**
	 * the root helps load the fxml file for a new scene 
	 */
	@FXML static Parent root;
  
	/**
	 * the button that creates a user when clicked on
	 */
    @FXML
    private Button create;

    /**
     * the button that deletes a user when clicked on
     */
    @FXML
    private Button delete;

    /**
     * the list view that is displayed with the user names
     */
    @FXML
    private ListView<String> listOfUsers = new ListView<String>();
    
    /**
     * the observable list that is connected to the listview and holds the String values of user names
     */
    private ObservableList<String> users = FXCollections.observableArrayList();; 
    
    /**
     * an int value that stores the index of the user that is logged in
     */
    public static int indexUser=-1;
    
    /**
     * the button that logs out of admin when clicked on
     */
    @FXML
    private Button logout;

    /**
     * the textfield that a user inputs a new user name on
     */
    @FXML
    private TextField typeUsername;
    
    /**
     * the method that is called when the admin stage is opened to display the current list of users
     */
    @FXML
    private void initialize() {
		listOfUsers.getItems().clear();
	    	
    	for (User u: Admin.usersList) 
    		users.add(u.getName()); 
	    	
   
	    listOfUsers.setItems(users);
	    
	    listOfUsers.getSelectionModel().select(0);
    }
    
    /**
     * adds a new user to the list of users when the button create is clicked upon
     * @param event is the mouse click 
     * @throws Exception 
     */
    @FXML
    private void createUser(MouseEvent event) throws Exception {
    	//usersList (ArrayList) -> users (Observable List) -> listOfUsers (ListView)
    	String newUser = typeUsername.getText();
    	if (newUser.length()<=0) {
    		 Alert alert = new Alert(AlertType.ERROR, "No username entered.", ButtonType.OK);
    			alert.showAndWait();
    				if (alert.getResult() == ButtonType.OK) {	
    				}
    	}
    	else {
    		boolean duplicate = false; 
	    	for (User u : Admin.usersList) {
	    		if (u.getName().equals(newUser))
	    			duplicate = true;
	    	}
	    	if (!duplicate) 
	    		Admin.usersList.add(new User(newUser));
	    	else { Alert alert = new Alert(AlertType.ERROR, "User already exists.", ButtonType.OK);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.OK) {	
				}
	    	}  	
	    	listOfUsers.getItems().clear();
	    
	    	Collections.sort(Admin.usersList, userCompare);
	    	
	    	for (User u: Admin.usersList) 
	    		users.add(u.getName()); 
	    	
	    	listOfUsers.setItems(users);
	    	typeUsername.clear();
	 
	    	save();
    	}
    }
    
 
    /**
     * deletes a user from the ArrayList of users when the delete button is clicked on
     * @param event is the mouse click on delete
     * @throws Exception
     */
    @FXML
    private void deleteUser(MouseEvent event) throws Exception {
    	//usersList (ArrayList) -> users (Observable List) -> listOfUsers (ListView)
    	int selectedID = listOfUsers.getSelectionModel().getSelectedIndex();
    	if(selectedID>=0) {
	        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this user?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {	
				Admin.usersList.remove(selectedID);
		        listOfUsers.getItems().remove(selectedID);
			}
			if (selectedID == listOfUsers.getItems().size())
				listOfUsers.getSelectionModel().select(selectedID-1);
			else  listOfUsers.getSelectionModel().select(selectedID);
    	}
    	save();
    }
    
    /**
     * the anonymous interface implementation of Comparator that sorts the users by comparing their names
     */
    public static Comparator<User> userCompare = new Comparator<User>() {

		public int compare(User u1, User u2) {
		   String user1 = u1.getName().toLowerCase();
		   String user2 = u2.getName().toLowerCase();

		   //ascending order
		   return user1.compareTo(user2);

		   //descending order
		   //return StudentName2.compareTo(StudentName1);
	    }};

   /**
    * the method to switch scenes to the login stage when the button logout is clicked on
    * @param event is the MouseEvent or mouse click
    * @throws Exception
    */
    @FXML
    private void logoutUser(MouseEvent event) throws Exception{
    	root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		save();
    }
    
    /**
     * the method that most methods call to serialize the usersList object when new information is added
     * @throws Exception when serializing
     */
    public static void save() throws Exception {
    	
		FileOutputStream fileOutputStream = new FileOutputStream("src/data/data.dat");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(Admin.usersList);

		objectOutputStream.close();
		fileOutputStream.close();
	 
    }
}
