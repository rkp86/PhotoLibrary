package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import app.Admin;
import app.Album;
import app.Photo;
import app.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This is the controller for the album fxml. It consists of the methods to edit the 
 * different photos in an album such as add, delete, or display a Photo.
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */
public class albumController {
	
	/**
	 * creates a new stage for different fxml files
	 */
	@FXML
	private Stage stage;
	
	/**
	 * creates a new scene to display the stage
	 */
	@FXML
	private Scene scene;
	
	/**
	 * the root helps load the fxml file for a new scene 
	 */
	@FXML
	private Parent root;
	

	/**
	 * the listview that displays the photos and their captions using the observable list
	 */
	@FXML
    private ListView<String> photos = new ListView<String>();
	
	/**
	 * the observable list of strings that hold the value of the photo's caption
	 */
    private ObservableList<String> photoNames = FXCollections.observableArrayList(); 
    
    /**
     * the ArrayList that stores the string of photo names to give to the observable list
     */
    public static ArrayList<String> photoos = new ArrayList<String>();

    /**
     * the index of the picture selected in the open album
     */
    public static int indexPic=-1;
    
    /**
     * keeps track of the number of photos in the album
     */
    public static int sizeAlbum;
    
    /**
     * the int that will keep track of whether a photo is being moved or copied
     */
    public static int moveOrCopy = -3;
    

    /**
     * button to add a photo from a file to an album
     */
    @FXML
    private Button addButton;
    
    /**
     * button to open the bigger display of the photo on a new stage
     */
    @FXML
    static Button open;

    /**
     * button for user to click when they want to copy the photo to a different album
     */
    @FXML
    private Button copy;

    /**
     * button for user to click when they wany to move the photo to a different album
     */
    @FXML
    private Button move;

    /**
     * button to delete a photo from the album
     */
    @FXML
    private Button removeButton;

    /**
     * button to click to open the search window and load its controller
     */
    @FXML
    private Button searchButton;

    /**
     * the album label that will update to the name of the album that was opened
     */
    @FXML 
    private Label album; 

    /**
     * method that is called when the album stage is opened to display the current photos and their information
     */
    @FXML
    private void initialize() {
    	photos.getItems().clear();
    	photoos.clear();
    	
    	if (Admin.usersList.get(adminController.indexUser).toString().equals("stock")) {
    		album.setText(Admin.usersList.get(adminController.indexUser).getAlbum(userController.indexAlbum));
    		setPicsList();
    	}
    	else {
			album.setText(Admin.usersList.get(adminController.indexUser).getAlbum(userController.indexAlbum));
			setPicsList();
    	}
    	
        photos
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				setIndex()
				);	
     
        
	}
    
    /**
     * a method to set the index of the pictures selected
     */
    public void setIndex() {
    	indexPic = photos.getSelectionModel().getSelectedIndex();
    }
    
    /**
     * to safely exit the application if "X" is clicked
     * @param event is an ActionEvent, meaning the user interacted or took action with the UI
     */
    @FXML
    public void exitApplication(ActionEvent event) { 
    	System.out.println("closing");
        Platform.exit();
    }

    /**
     * this method opens the photo display by loading the photos fxml elements on a new stage
     * @param event mouse click on "Display Photo"
     * @throws IOException if the fxml file doesnt exist
     */
    @FXML
     private void openPhoto(MouseEvent event) throws IOException {
    	if (photos.getSelectionModel().getSelectedIndex() != -1) {
    		indexPic = photos.getSelectionModel().getSelectedIndex();
    		
    		root = FXMLLoader.load(getClass().getResource("/view/photos.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
    	}
    	
    
    	//photos.getSelectionModel().select(-1);
    	
    }
    
 
    
    /**
     * displays all the pictures of an album in the listView by organizing its captions
     */
    public void setPicsList() {
    	photoos.clear();
    	photos.getItems().clear();
    	
    	if (!Admin.usersList.get(adminController.indexUser).
		  getAlbums().get(userController.indexAlbum).
		  getPhotos().isEmpty()) {
	    	sizeAlbum =  Admin.usersList.get(adminController.indexUser).
													  getAlbums().get(userController.indexAlbum).
													  getPhotos().size();
	    	
	    
	    	for(int i=0; i< sizeAlbum; i++) {
	    		if (!Admin.usersList.get(adminController.indexUser).
													  getAlbums().get(userController.indexAlbum).
													  getPhotos().get(i).getCaption().isEmpty()) {
	    			photoos.add(Admin.usersList.get(adminController.indexUser).
													  getAlbums().get(userController.indexAlbum).
													  getPhotos().get(i).getCaption());
	    		}
	    		else {
	    			photoos.add(i+"");
	    		}
	    	}
    	
	    	for (String s: photoos) {
	    		photoNames.add(s);
	    	}
	    	photos.setItems(photoNames);
			
			photos.setCellFactory(param -> new ListCell<String>() {
	   		private ImageView img = new ImageView();
	   		 @Override
	        public void updateItem(String name, boolean empty) {
	            super.updateItem(name, empty);
	            if (empty) {
	            	setText(null);
	            	setGraphic(null);
	            } else {
	            	for(int i=0; i<sizeAlbum; i++) {
	            		if (name.equals(i+"")) {
	            			img.setImage(new Image(Admin.usersList.get(adminController.indexUser).
									  getAlbums().get(userController.indexAlbum).
									  getPhotos().get(i).getImage()));
	            		}
	            		else 
		            		if (name.equals(Admin.usersList.get(adminController.indexUser).
														  getAlbums().get(userController.indexAlbum).
														  getPhotos().get(i).getCaption())) {
		            			img.setImage(new Image(Admin.usersList.get(adminController.indexUser).
										  getAlbums().get(userController.indexAlbum).
										  getPhotos().get(i).getImage()));
		            			setText(name);
		            		}
	            		
	            		 
	            	} 
	            
	                img.setFitWidth(50);
	                img.setFitHeight(50);
	                setGraphic(img);
	                if (name.length()>1)
	                	setText(name);
	            }
	   		 }});
    	
    	}
    }
    
    
    /**
     * this method adds a photo to the album after the user picks an image from their laptop
     * includes .bmp, .gif, .pie, and .jpeg
     * @param event is a mouse click on "Add photo"
     * @throws Exception
     */
    @FXML
    private void addPhoto(MouseEvent event) throws Exception {
    	FileChooser fileChooser = new FileChooser();

 
        FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("Image files" , Arrays.asList("*.bmp","*.gif", "*.png", "*.jpeg", "*.jpg"));
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);
        

        if (file != null) {
        	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
     	    Date date = new Date(file.lastModified());
        	String imageFile = file.toURI().toString();
                	
            Admin.usersList.get(adminController.indexUser).
            						  getAlbums().get(userController.indexAlbum).
            						  getPhotos().add(new Photo(imageFile, "",date, formatter.format(date.getTime())));   
    	
        }
        setPicsList();
 
    	adminController.save();
            
    }  

   
    /**
     * takes the user back to the user scene after back button is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    private void backToUser(MouseEvent event) throws IOException {
   
	    	root = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
    	
    }

    /**
     * copies a photo to a specified album when the copy button is clicked
     * @param event is the MouseEvent 
     * @throws Exception
     */
    @FXML
    private void copyPhoto(MouseEvent event) throws Exception {
    	moveOrCopy=-2;
    	
    	if (photos.getSelectionModel().getSelectedIndex() != -1) {
	    	stage = new Stage();
	    	try {
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(getClass().getResource("/view/list.fxml"));
				
				// load the fxml
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root); 
				stage.setScene(scene);
				stage.show(); 
				
				} catch (IOException e) {
					e.printStackTrace();
				}	
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR, "No photo selected.", ButtonType.OK);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {}
    	}
    	
    	adminController.save();
    }

	/**
	 * moves a photo to a specified album when the copy button is clicked
	 * @param event is the MouseEvent 
	 * @throws Exception
	 */
    @FXML
    private void movePhoto(MouseEvent event) throws Exception {
    	moveOrCopy=-1;
    	
    	if (photos.getSelectionModel().getSelectedIndex() != -1) {
	    	stage = new Stage();
	    	try {
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(getClass().getResource("/view/list.fxml"));
				
				// load the fxml
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root); 
				stage.setScene(scene);
				stage.show(); 
				
				if (moveOrCopy != -1) setPicsList();
				} catch (IOException e) {
					e.printStackTrace();
				}	
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR, "No photo selected.", ButtonType.OK);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {}
    	}
    	
    	adminController.save();
    }

    /**
     * deletes a photo from an album after the user chooses it
     * @param event
     * @throws Exception
     */
    @FXML
    private void removePhoto(MouseEvent event) throws Exception {
    	if (indexPic<0) {
    		Alert alert = new Alert(AlertType.ERROR, "No photo selected.", ButtonType.OK);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {}
    	}
    	else {
    		Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete this photo?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				Admin.usersList.get(adminController.indexUser).getAlbums().get(userController.indexAlbum).getPhotos().remove(indexPic);
				setPicsList();
			}
    	}
    	adminController.save();
    }

 
}
