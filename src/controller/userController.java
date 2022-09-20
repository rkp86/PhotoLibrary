package controller;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.Admin;
import app.Album;
import app.Photo;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * this controller controls the methods of the user fxml and also is the
 * scene in which the album names are diplayed to the UI
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29 
 */
public class userController  {
	
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
	@FXML
	static Parent root;
  
	/**
	 * the textfield that the user inputs a new album name into
	 */
    @FXML
    private TextField AlbumName;

    /**
     * the tableview of strings that displays the album names after retrieving the 
     * String values from the observable list
     */
    @FXML
    private TableView<Album> albums = new TableView<Album>();
    
    /**
     * the table column in the table that displays date range of the album
     */
    @FXML
    private TableColumn<Album,String> dateRange = new TableColumn<Album,String>("Date Range");

    /**
     * the table column in the table that displays the number of photos
     */
    @FXML
    private TableColumn<Album, String> numPhotos = new TableColumn<Album,String>("# of photos");
    
    /**
     * the table column in the table that displays album names
     */
    @FXML
    private TableColumn<Album, String> albumname = new TableColumn<Album,String>("Album");
    
    /**
     * the observable list that holds the Strings of the album names 
     * or the items that are set in the listView
     */
    private ObservableList<Album> albumNames = FXCollections.observableArrayList(); 
    
    /**
     * an int that tracks the index of the album selected in the user stage
     */
    public static int indexAlbum=0;
    
    /**
     * the int value that tells the controller the user opened the stock album
     */
    public static int stock = -1;
    
    /**
     * the button to create a new album under a user
     */
    @FXML
    private Button createButton;
    
    /**
     * the button to delete an album from the list of albums
     */
    @FXML
    private Button deleteButton;

    /**
     * the button that switches scenes from user to the login stage
     */
    @FXML
    private Button logout;

    /**
     * the button to open a selected album
     */
    @FXML
    private Button openButton;
    
    /**
     * the textfield that the user can edit a current album name from
     */
    @FXML
    private TextField renameAlbumName;

    /**
     * the button for a user to click when renaming an album name
     */
    @FXML
    private Button renameButton;
    
    /**
     * button to open the search scene
     */
    @FXML
    private Button search;
    /** 
     * the user label at the top of the stage that will update with the user name
     */
    @FXML
    private Label userlabel;

  
    /**
     * the format of the date display
     */
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    /**
     * the method called when the user fxml is loaded 
     * it sets the listview items to the names of the albums for display
     */
    @FXML
    private void initialize() {
    	if (Admin.usersList.get(adminController.indexUser).toString().equals("stock"))
    		loadStock();
        userlabel.setText("Welcome " + Admin.usersList.get(adminController.indexUser).getName());
        
     
        
        albumname.setCellValueFactory(new PropertyValueFactory<Album,String>("named"));
        albumname.setCellFactory(TextFieldTableCell.forTableColumn());
        numPhotos.setCellValueFactory(new PropertyValueFactory<Album,String>("numPhotos"));
        numPhotos.setCellFactory(TextFieldTableCell.forTableColumn());
        dateRange.setCellValueFactory(new PropertyValueFactory<Album,String>("date"));
        dateRange.setCellFactory(TextFieldTableCell.forTableColumn());

      
        albums.getItems().clear();
    	for (int i=0; i < Admin.usersList.get(adminController.indexUser).getAlbums().size();i++ ) {
    		albumNames.add(new Album (Admin.usersList.get(adminController.indexUser).getAlbum(i), Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().size()+"", getDate(i)));
    	}
    	
    	albums.setItems(albumNames);  
    
    	
    	albums
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				setIndex()
				);	
     
    }
    
    /**
     * sorts through all the dates of the photos in the album to find the date range
     * @param i is the index to keep track of which album
     * @return a String value of the date range
     */
    public String getDate(int i) {
    	Album selectedAlbum = Admin.usersList.get(adminController.indexUser).getAlbums().get(i);
    	
    	String earliestDate ="";
		String latestDate="";
		
		
		
		if(selectedAlbum.getPhotos().size() != 0) {

			Date date=selectedAlbum.getPhotos().get(0).getDated();
			 earliestDate=dateFormat.format(date.getTime());
			 latestDate=dateFormat.format(date.getTime());
		}
		
		 
		 for (Photo photo:selectedAlbum.getPhotos()) {
				Date date=photo.getDated();
				photo.setDateTime(dateFormat.format(date.getTime()));

				if (dateFormat.format(date.getTime()).compareTo(earliestDate)<0) {
					earliestDate=dateFormat.format(date.getTime());
				}
				if (dateFormat.format(date.getTime()).compareTo(latestDate)>0) {
					latestDate=dateFormat.format(date.getTime());
				}
		 
		 
		 }	 
		 if (earliestDate.length()>0)
		 return earliestDate + "-" + latestDate;
		 else return "";
    }
    
    /**
     * the method that the listView listener will call to keep track of which album is selected
     */
    public void setIndex() {
    	if (albums.getSelectionModel().getSelectedIndex() >= 0) {
    		indexAlbum = albums.getSelectionModel().getSelectedIndex();
    		renameAlbumName.setText(Admin.usersList.get(adminController.indexUser).getAlbum(indexAlbum));
    	}
    }
    
    /**
     * adds a stock album to the stock user and loads all its stock images
     */
    public void loadStock() {
    	if (Admin.usersList.get(adminController.indexUser).getAlbums().size()==0) {
    	     	
		ArrayList<Photo> stockImages = new ArrayList<Photo>();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
	    Date date = new Date();
	    
		
		
		//Calendar date = Calendar.getInstance(); 
	
        //System.out.println(date);
        Admin.usersList.get(adminController.indexUser).addAlbum(new Album("stock"));
		stock++;
		stockImages.add(new Photo("/data/stock1.png","", date));
	
    	stockImages.add(new Photo("/data/stock2.png","", date));
    	
    	stockImages.add(new Photo("/data/stock3.png","",date));
    	
    	stockImages.add(new Photo("/data/stock4.png","",date));
    	
    	stockImages.add(new Photo("/data/stock5.png","",date));
    	
    	Admin.usersList.get(adminController.indexUser).getAlbums().get(0).setPhotos(stockImages);
    	 }
    
    	
    	
    }
    /**
     * creates a new album after checking for a duplicate album name. 
     * It is added to the ArrayList of Albums in the User class
     * @param event is the mouse click on the create button
     * @throws Exception when serializing
     */
    @FXML
    private void createAlbum(MouseEvent event) throws Exception {
    	renameAlbumName.setText("");
    	if (AlbumName.getText().length() > 0) {
	    	Admin.usersList.get(adminController.indexUser).addAlbum(new Album(AlbumName.getText()));
	    	albums.getItems().clear();
	    	for (int i=0; i < Admin.usersList.get(adminController.indexUser).getAlbums().size();i++ ) {
	    		albumNames.add(new Album (Admin.usersList.get(adminController.indexUser).getAlbum(i), Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().size()+"", getDate(i)));

	    	}
	    	albums.setItems(albumNames);
	    	AlbumName.setText("");
    	}
    	adminController.save();
    	
    	
    }

    /**
     * deletes the album selected from the list of user albums
     * @param event is the mouseclick on remove album
     * @throws Exception when serializing
     */
    @FXML
    private void deleteAlbum(MouseEvent event) throws Exception {
    	indexAlbum = albums.getSelectionModel().getSelectedIndex();
    	renameAlbumName.setText("");
    	if (indexAlbum < 0 ) {
    		Alert alert = new Alert(AlertType.ERROR, "No album selected.", ButtonType.OK);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {}
    	}
    	if(indexAlbum>=0) {
    		
    		Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete this album: " + Admin.usersList.get(adminController.indexUser).getAlbum(userController.indexAlbum) + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {
				Admin.usersList.get(adminController.indexUser).getAlbums().remove(indexAlbum);
		        albums.getItems().remove(indexAlbum);
				if (indexAlbum == albums.getItems().size())
					albums.getSelectionModel().select(indexAlbum-1);
				else  albums.getSelectionModel().select(indexAlbum);
			}
			
    	}
    	if (albums.getItems().isEmpty()) { renameAlbumName.setText(""); indexAlbum = -1; }
    	adminController.save();
    }
    
	/**
	 * opens a new stage of the specified album and its information
	 * @param event is the mouse click on the open button
	 * @throws IOException if class is not found 
	 */
    @FXML
    private void openAlbum(MouseEvent event) throws IOException {
    	indexAlbum = albums.getSelectionModel().getSelectedIndex();
    	if (indexAlbum < 0 ) {
    		Alert alert = new Alert(AlertType.ERROR, "No album selected.", ButtonType.OK);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {}
    	}
    	else {
	    	root = FXMLLoader.load(getClass().getResource("/view/album.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
    	}
    }

    /**
     * updates the name of an album if the user enters a new name for it 
     * @param event is the mouse click on the rename button
     * @throws Exception when serializing
     */
    @FXML
    private void renameAlbum(MouseEvent event) throws Exception {
    	indexAlbum = albums.getSelectionModel().getSelectedIndex();
    	if (indexAlbum>=0) {
	    	Admin.usersList.get(adminController.indexUser).getAlbums().get(indexAlbum).setName(renameAlbumName.getText());
	    	albums.getItems().clear();
	    	for (int i=0; i < Admin.usersList.get(adminController.indexUser).getAlbums().size();i++ ) {
	    		albumNames.add(new Album (Admin.usersList.get(adminController.indexUser).getAlbum(i), Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().size()+"", getDate(i)));
	    	}
	    	albums.setItems(albumNames);
	    	renameAlbumName.setText("");
    	}
    	else {
    		Alert alert = new Alert(AlertType.ERROR, "No album selected.", ButtonType.OK);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {	
				
			}
    	}
    	adminController.save();
    }
    
    /**
     * opens a new scene that the user can search photos in 
     * @param event is the mouse click on search photos
     * @throws Exception if class is not found
     */
    @FXML
    private void searchPhotos(MouseEvent event) throws Exception {
    	stage = new Stage();
    	stage.setTitle("Search");
    	try {
			FXMLLoader loader = new FXMLLoader();   
			loader.setLocation(getClass().getResource("/view/search.fxml"));
			
			// load the fxml
			AnchorPane root = (AnchorPane)loader.load();

			// get the controller (Do NOT create a new Controller()!!)
			// instead, get it through the loader
			
		//	Controller controller = loader.getController();
			//controller.start();

			Scene scene = new Scene(root); 
			stage.setScene(scene);
			stage.show(); 
			
			} catch (IOException e) {
				e.printStackTrace();
			}

    }
    
    /**
     * the method to switch scenes to the login stage when the button logout is clicked on
     * @param event is the MouseEvent or mouse click on the logout button
     * @throws Exception if class is not found 
     */
     @FXML
    private void logoutUser(MouseEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
    
    

}
