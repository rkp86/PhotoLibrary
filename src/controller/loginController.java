package controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import app.Admin;
import app.Photos;
import app.User;
//import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This is login Controller class that controlls the UI for the login stage
 * It is the scene where the user can type in a username to switch to that specific stage
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */

public class loginController {

	/**
	 * creates a new stage for different fxml files
	 */
	private Stage stage;
	
	/**
	 * creates a new scene to display the stage
	 */
	private Scene scene;
	
	/**
	 * the root helps load the fxml file for a new scene 
	 */
	private Parent root;
	
	/**
	 * method that is called when login fxml is loaded - calls the method to deserialize the users object
	 * @throws IOException if the data file is not found
	 * @throws ClassNotFoundException if class definition is not found
	 */
	@FXML
	public void initialize() throws IOException, ClassNotFoundException {
		readApp();
	}
	
	/**
	 * button that user clicks to login
	 */
    @FXML
    private Button loginButton;

    /**
     * field for user to type in their username
     */
    @FXML
    private TextField userlogin;

    /**
     * changes scenes from login to admin, stock, or a specific user if it exists
     * @param event is the Mouse click on the login button
     * @throws Exception
     */
    @FXML
    private void loginUser(MouseEvent event) throws Exception{

    	String user = userlogin.getText();
    	if (!checkUserExists("stock"))
    		Admin.usersList.add(new User("stock")); 
    	
    	if (user.equals("stock") && checkUserExists("stock")){
			root = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			//adminController.indexUser = -1; //-1 for stock 
			
    	}
    	else if (user.equals("admin")) {
    		root = FXMLLoader.load(getClass().getResource("/view/admin.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();	
			
    	}
    	else if (checkUserExists(user)) {
    		root = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
	
    	}
    	
    	
    	else { Alert alert = new Alert(AlertType.ERROR, "This user does not exist.", ButtonType.OK);
		alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {	
				userlogin.setText("");
			}
    	}
    	
    }

    /**
     * checks if a user is registered under admin 
     * @param n is the username
     * @return true if user exists
     */
    public boolean checkUserExists(String n) { 
    	ArrayList<User> users = Admin.usersList;
    	for (int i =0;i<users.size();i++) {
    		if (users.get(i).getName().equals(n)) {
    			adminController.indexUser=i;  //global to access any users info once logged in
	    			return true;
	    		}
	    	}
	    	
	    	return false;
    }
    
    /**
     * deserializes the users object by finding the data.dat file
     * @throws IOException if the data file is not found
     * @throws EOFException if the objectInputStream is unable to read the file
     * @throws ClassNotFoundException when stream cannot find class definition
     */
    public static void readApp()
    		throws IOException, EOFException, ClassNotFoundException {
    		
    		File myFile = new File("src/data/data.dat");
    		if (myFile.length()>0) {
	    		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(myFile));
	    		@SuppressWarnings("unchecked")
				ArrayList<User> users = (ArrayList<User>)ois.readObject();
	    		Admin.usersList = users;
	    		ois.close();
	    	
    		}
    }
}
