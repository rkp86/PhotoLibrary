package controller;

import app.Admin;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

/**
 * This is the controller for a list window that opens up for the user to pick which album 
 * they want to move or copy their photo to
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */
public class listController {

	
	/**
	 * the listview display of the album names under the user that is logged in
	 */
    @FXML
    private ListView<String> listView = new ListView<String>();
    
    /**
     * the observable list that holds the String value of the album names
     * controls the list view Strings that are displayed
     */
    private ObservableList<String> albums = FXCollections.observableArrayList(); 
    
    
    /**
     * the method that is called when the list fxml is loaded
     * it sets the items of the list view which are the album names
     */
    @FXML 
    public void initialize() {
    	listView.getItems().clear();
    	for (int i=0; i < Admin.usersList.get(adminController.indexUser).getAlbums().size();i++ ) {
    		albums.add(Admin.usersList.get(adminController.indexUser).getAlbum(i));
    	}
    	listView.setItems(albums);
    	
    	
    	 listView
   		.getSelectionModel()
   		.selectedIndexProperty()
   		.addListener(
   				(obs, oldVal, newVal) -> 
   				{
					try {
						setIndex();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
   				);	
    }
    
    /**
     * selects and stores the index of the album the user selects from the list view
     * to copy or move a specified photo
     * @throws Exception when serializing
     */
    public void setIndex() throws Exception {
    	
    	 if (albumController.moveOrCopy == -1) {
	    	 Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to move the \nphoto to this album: " + listView.getSelectionModel().getSelectedItem() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {	
					 albumController.moveOrCopy = listView.getSelectionModel().getSelectedIndex();
			        	Admin.usersList.get(adminController.indexUser).
			    		getAlbums().get(albumController.moveOrCopy).getPhotos().add(Admin.usersList.get(adminController.indexUser).
			    														getAlbums().get(userController.indexAlbum).getPhotos().get(albumController.indexPic)); 
			        	Admin.usersList.get(adminController.indexUser).getAlbums().get(userController.indexAlbum).getPhotos().remove(albumController.indexPic);

			        	//albumController.photoos.remove(albumController.indexPic);

			        	Stage stage = (Stage)listView.getScene().getWindow();
					     stage.close();
					     userController.root = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
							userController.scene = new Scene(userController.root);
							userController.stage.setScene(userController.scene);
							userController.stage.show();
				}
				adminController.save();
    	 }
    	 else if (albumController.moveOrCopy == -2) {
	    	 Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to copy the \nphoto to this album: " + listView.getSelectionModel().getSelectedItem() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {	
					 albumController.moveOrCopy = listView.getSelectionModel().getSelectedIndex();
					 Admin.usersList.get(adminController.indexUser).
					  getAlbums().get(albumController.moveOrCopy).getPhotos().add(Admin.usersList.get(adminController.indexUser).
																	getAlbums().get(userController.indexAlbum).getPhotos().get(albumController.indexPic)); 
					 Stage stage = (Stage)listView.getScene().getWindow();
				     stage.close();
				     userController.root = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
						userController.scene = new Scene(userController.root);
						userController.stage.setScene(userController.scene);
						userController.stage.show();
				}
    	 }
			adminController.save();

    }
}
