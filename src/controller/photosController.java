package controller;

import java.io.IOException;

import app.Admin;
import app.Album;
import app.Photo;
import app.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This is controller class for the photo fxml. It helps display a selected photo and its 
 * details such as its caption, date, and tags. It also allows the user to edit this information.
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */
public class photosController {
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
	 * the field where the user can type a new caption for the selected photo
	 */
    @FXML
    private TextField caption;
    
    /**
     * the text label that shows the caption of the photo if one exists
     */
    @FXML
    private Label captionLabel;

    /**
     * the text label that shows the date of the photo if one exists
     */
    @FXML
    private Label dateTimeLabel;

    /**
     * the imageView object that provides a space to display the selected photo
     */
    @FXML
    private ImageView selectedImage;

    /**
     * the drop down list of tag names for the user to select from
     */
    @FXML
    private ChoiceBox<String> tagChoices = new ChoiceBox<String>();
    
    /**
     * the listview that displays the tags of the selected photo
     */
    @FXML
    private ListView<String> tagView = new ListView<String>();
    
    /**
     * the list that is connceted to the lsitView and stores the strings of the tags to set to the listView
     */
    private ObservableList<String> tagList = FXCollections.observableArrayList();

    /**
     * the field for the user to enter a tag name in
     */
    @FXML
    private TextField tagName;

    /**
     * the field for the user to enter a tag value in
     */
    @FXML
    private TextField tagValue;
   
    /**
     * the method that is called when photo fxml is loaded and
     * @throws Exception when serializing
     */
    @FXML
    private void initialize() throws Exception {
    	
    	if (!Admin.usersList.get(adminController.indexUser).
				getAlbums().get(userController.indexAlbum).
				getPhotos().isEmpty()) {
    		selectedImage.setImage(new Image(Admin.usersList.get(adminController.indexUser).
    													getAlbums().get(userController.indexAlbum).
    													getPhotos().get(albumController.indexPic).getImage()));
    		
    		tagChoices.setOnAction((event) -> {
    		    String name = tagChoices.getSelectionModel().getSelectedItem();
    		    tagName.setText(name);
    		});
    		
    		displayInfo();
    	}  	
    }
    
    /**
     * displays the selected photo and its information (caption, dateTime, tags) 
     * @throws Exception when serializing
     */
    public void displayInfo() throws Exception {

    	captionLabel.setText(Admin.usersList.get(adminController.indexUser).
    													getAlbums().get(userController.indexAlbum).
    													getPhotos().get(albumController.indexPic).getCaption());

    	dateTimeLabel.setText(Admin.usersList.get(adminController.indexUser).
				getAlbums().get(userController.indexAlbum).
				getPhotos().get(albumController.indexPic).getDate());

    	tagView.getItems().clear();
    	
    	for (Tag t: Admin.usersList.get(adminController.indexUser).
				getAlbums().get(userController.indexAlbum).
				getPhotos().get(albumController.indexPic).getTags()) {
    		tagList.add(t.toString());
    	}
    	
    	tagView.setItems(tagList);
    	
    	addTagChoices();    
    }
    
    /**
     * sets a caption to a selected photo if the user types in a new caption
     * @param event is the mouse click on the caption it button 
     * @throws Exception when serializing
     */
    @FXML
    private void captionIt(MouseEvent event) throws Exception {
    	if (!caption.getText().isEmpty())
	    	Admin.usersList.get(adminController.indexUser).
			getAlbums().get(userController.indexAlbum).
			getPhotos().get(albumController.indexPic).setCaption(caption.getText());
    	captionLabel.setText(caption.getText());
		caption.setText("");
		adminController.save();
    }

    /**
     * the method that adds a tag to the selected photo as well as updates the tag names list
     * @param event is the mouse click on the add tag button
     * @throws Exception when serializing
     */
    @FXML
    private void addTag(MouseEvent event) throws Exception {
    	boolean check = true;
    	boolean oneName = false;
    	
    	if (tagName.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR, "Must enter a tag name.", ButtonType.OK);
    		alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {	   				
			}
    	}
    	else if (tagValue.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR, "Must enter a tag value.", ButtonType.OK);
    		alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {	   				
			}
    	}
    	else {
    		for (Tag t: Admin.usersList.get(adminController.indexUser).
    											getAlbums().get(userController.indexAlbum).
    											getPhotos().get(albumController.indexPic).getTags()) {
	    		if (t.getName().equals(tagName.getText()) && t.getValue().equals(tagValue.getText()))
	    			check = false;
	    		if (t.getName().equals("location") && tagName.getText().equals("location")) {
	    			oneName = true;
	    		}
	    	}
    	
	    	if(!check) { //there is no duplicate tag in the list so you can add the new one
	    		Alert alert = new Alert(AlertType.ERROR, "This tag already exists.", ButtonType.OK);
	    		alert.showAndWait();
				if (alert.getResult() == ButtonType.OK) {	   				
				}
	    	}
	    	else if (oneName) {
	    		Alert alert = new Alert(AlertType.ERROR, "Cannot have more than one of this tagName: " + tagName.getText(), ButtonType.OK);
	    		alert.showAndWait();
	    			if (alert.getResult() == ButtonType.OK) {	   				
	    			}
	    	}
	    	else {
	    		Admin.usersList.get(adminController.indexUser).
				getAlbums().get(userController.indexAlbum).
				getPhotos().get(albumController.indexPic).getTags().add(new Tag(tagName.getText(),tagValue.getText()));
	    	}
	    	
	    	tagView.getItems().clear();
	    	
	    	for (Tag t: Admin.usersList.get(adminController.indexUser).
					getAlbums().get(userController.indexAlbum).
					getPhotos().get(albumController.indexPic).getTags()) {
	    		tagList.add(t.toString());
	    	}
	    	tagView.setItems(tagList);
	    	
	    	tagName.clear();
			tagValue.clear();
			
			addTagChoices();
    	}
    	adminController.save();
    }

    /**
     * updates the tag choices list with the different tag names the user can select 
     * @throws Exception when serializing
     */
    private void addTagChoices() throws Exception {
		tagChoices.getItems().clear();
		tagChoices.getItems().add("location");
		tagChoices.getItems().add("person");
		tagChoices.getItems().add("event");
    	
		for(Album a: Admin.usersList.get(adminController.indexUser).
				getAlbums()) {
			for(Photo p: Admin.usersList.get(adminController.indexUser).
					getAlbums().get(userController.indexAlbum).
					getPhotos()) {
	    		for (Tag t: p.getTags()) {
	        		if (!tagChoices.getItems().contains(t.getName()))
	        			tagChoices.getItems().add(t.getName());
	        	}
	    	}
		}
    	adminController.save();
    }
    
    /**
     * removes a tag from a photo
     * @param event is the mouse click on the remove tag button
     * @throws Exception when serializing
     */
    @FXML
    private void deleteTag(MouseEvent event) throws Exception {
    	int selectedID = tagView.getSelectionModel().getSelectedIndex();
        
    	if (selectedID >=0)
	    	Admin.usersList.get(adminController.indexUser).
			getAlbums().get(userController.indexAlbum).
			getPhotos().get(albumController.indexPic).getTags().remove(selectedID);
    	
    	tagView.getItems().clear();
    	
    	for (Tag t: Admin.usersList.get(adminController.indexUser).
				getAlbums().get(userController.indexAlbum).
				getPhotos().get(albumController.indexPic).getTags()) {
    		tagList.add(t.toString());
    	}
    	tagView.setItems(tagList);
    	adminController.save();
	}

    /**
     * displays the next photo in the album
     * @param event is the mouse click on the next arrow
     * @throws Exception if serializing
     */
    @FXML
    private void nextPhoto(MouseEvent event) throws Exception {
    	albumController.indexPic++;
    	if (albumController.indexPic >= Admin.usersList.get(adminController.indexUser).
					    				getAlbums().get(userController.indexAlbum).
					    				getPhotos().size())
    		albumController.indexPic = 0;
    	selectedImage.setImage(new Image(Admin.usersList.get(adminController.indexUser).
				getAlbums().get(userController.indexAlbum).
				getPhotos().get(albumController.indexPic).getImage()));
    	
    	displayInfo();
    }

    /**
     * displays the previous photo in the album
     * @param event is the mouse click on the previous arrow
     * @throws Exception if serializing
     */
    @FXML
    private void previousPhoto(MouseEvent event) throws Exception {
    	albumController.indexPic--;
    	if (albumController.indexPic < 0)
    		albumController.indexPic = Admin.usersList.get(adminController.indexUser).
					    				getAlbums().get(userController.indexAlbum).
					    				getPhotos().size()-1;
   
    	selectedImage.setImage(new Image(Admin.usersList.get(adminController.indexUser).
				getAlbums().get(userController.indexAlbum).
				getPhotos().get(albumController.indexPic).getImage()));
    	
    	displayInfo();
    }

    /**
     * switches back to the album scene 
     * @param event is the mouse click on the back button
     * @throws IOException if the class for the fxml is not found
     */
    @FXML 
    private void back(MouseEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
}
