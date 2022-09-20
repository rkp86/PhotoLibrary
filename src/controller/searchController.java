package controller;


import java.util.ArrayList;
import java.util.Date;

import app.Admin;
import app.Album;
import app.Photo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * This is controller class for the search fxml. It allows the user to input a date range or 
 * tag value to search through all the photos in all the user's albums
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */

public class searchController{
	
	/**
	 * an int variable to keep track of the type of search (dateTime or tag)
	 */
	private int stype = 0;

	/**
	 * a drop down list with AND and OR for tag searches
	 */
	@FXML
    private ChoiceBox<String> comboSearch = new ChoiceBox<String>();;

    /**
     * a button for the user to click when they want to make a new album
     */
    @FXML
    private Button createButton;

    /**
     * a drop down list for the type of search the user wants to make - dateTime or tag
     */
    @FXML
    private ChoiceBox<String> fieldChoices = new ChoiceBox<String>();;

    /**
     * a field for the user to type in the album name for a new album with search results
     */
    @FXML
    private TextField newAlbum;
    
    /**
     * a list that displays the search results which are images and their captions
     */
    @FXML
    private ListView<String> imageList = new ListView<String>();
    
    /**
     * a list that holds the objects being displayed in the listView
     */
    private ObservableList<String> images = FXCollections.observableArrayList();

    /**
     * a button for the user to click when they are ready to search
     */
    @FXML
    private Button searchButton;

    /**
     * a field for the user to input their first search term
     */
    @FXML
    private TextField searchInput;

    /**
     * a field for the user to input their second search term
     */
    @FXML
    private TextField searchInput2;
    
    /**
     * a list to keep track of the result images and update them or search through them 
     */
    public static ArrayList<Photo> results = new ArrayList<Photo>();
    
    /**
     * the method that's called when the search fxml is loaded 
     * it sets up the listener for the choicebox object and sets up the search
     * bar for the user in general
     */
    @FXML 
    private void initialize() {
    	setSearch(); 
    	
    	fieldChoices.setOnAction((event) -> {
    		if (fieldChoices.getSelectionModel().getSelectedIndex() == 0) {
 		    	searchInput2.setVisible(true);
 		    	searchInput.setPromptText("enter first date"); 
 		    	searchInput2.setPromptText("enter second date"); 
 		    	comboSearch.setVisible(false);
 		    	stype = 0;
 		    }
    		if (fieldChoices.getSelectionModel().getSelectedIndex() == 1) {
		    	searchInput2.setVisible(true);
		    	searchInput.setPromptText("enter tagName=tagValue");
		    	searchInput2.setPromptText("enter tagName=tagValue");
		    	comboSearch.setVisible(true);
		    	stype = 1;
		    }
		   
		});
    	
    	fieldChoices.getSelectionModel().select(0);
    }
    
    /**
     * this method makes a new album of the search results under the logged-in user
     * @param event is the mouse click on the create button
     * @throws Exception when serializing
     */
    @FXML
    private void createAlbum(MouseEvent event) throws Exception {
    	
    	if (results.size() == 0) {
    		Alert alert = new Alert(AlertType.ERROR, "No search results to create an album with", ButtonType.OK);
    		alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {	   
				newAlbum.setText("");
			}
    	}
    	else if (newAlbum.getText().length() == 0) {
    		Alert alert = new Alert(AlertType.ERROR, "No album name entered", ButtonType.OK);
    		alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {	   
				newAlbum.setText("");
			}
    	}
    	
    	else {
    		boolean duplicate = false; 
        	for (Album a : Admin.usersList.get(adminController.indexUser).getAlbums()) {
        		if (a.getName().equals(newAlbum.getText()))
        			duplicate = true;
        	} 
        	
        	if (duplicate) {
        		Alert alert = new Alert(AlertType.ERROR, "Album name already exists.", ButtonType.OK);
    			alert.showAndWait();
    			if (alert.getResult() == ButtonType.OK) {	
    			}
        	}  	
        	else {
		    	Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to create an album of the search results?", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {	   	
					Admin.usersList.get(adminController.indexUser).addAlbum(new Album(newAlbum.getText()));
	
			    	
			    	for (int i = 0; i < Admin.usersList.get(adminController.indexUser).getAlbums().size();i++) {
			    		if (newAlbum.getText().equals(Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getName())) {
					    	for (Photo p: results) {
					    		Admin.usersList.get(adminController.indexUser).getAlbums().get(i).addPhoto(p);
					    	}
					    	break;
			    		}
			    	}
			    	 Stage stage = (Stage)imageList.getScene().getWindow();
				     stage.close();
				     userController.root = FXMLLoader.load(getClass().getResource("/view/search.fxml"));
						userController.scene = new Scene(userController.root);
						userController.stage.setScene(userController.scene);
						userController.stage.show();
			    	
				}
        	}
		
    	}
    	newAlbum.setText("");
    	adminController.save();
    }

    /**
     * this method traverses through all the albums of the user and the pictures in the albums
     * and will check whether the picture consists of the search term to add it to the results list
     * @param event is the Mouse click on the search button
     */
    @SuppressWarnings("deprecation")
	@FXML
    private void search(MouseEvent event) {
    	
    	results.clear();

    	
    	if (searchInput.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR, "Must enter a search term.", ButtonType.OK);
    		alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {	   				
			}
    	}
    	else if (stype==0 && searchInput2.getText().length()<=0) {
			Alert alert = new Alert(AlertType.ERROR, "Please enter two dates", ButtonType.OK);
    		alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {	   				
			}
		}
    	else {
    		int b=0;
    		for (int i = 0; i < Admin.usersList.get(adminController.indexUser).
    														   getAlbums().size(); i++ ) { //for loop goes through all albums of user
    			if (b==1) break;
				for (int j = 0; j < Admin.usersList.get(adminController.indexUser).
												 getAlbums().get(i).getPhotos().size(); j++) { //for loop goes through all photos in each album
				
					
					if (stype==0) { //if it's a dateTime search 
					
						String[] MDY = new String[3];
						MDY = Admin.usersList.get(adminController.indexUser).
								 getAlbums().get(i).getPhotos().get(j).getDate().split("/");
						
						Date date = new Date (Integer.parseInt(MDY[2]), Integer.parseInt(MDY[0]), Integer.parseInt(MDY[1])); 
						
						String [] search1 = {"","",""};
						search1 = searchInput.getText().split("/");
									
						String [] search2 = new String[3];
						search2 = searchInput2.getText().split("/");
						
						if (search1.length !=3 || search2.length!=3) {
							Alert alert = new Alert(AlertType.ERROR, "Must enter a date of the format mm/dd/yyyy", ButtonType.OK);
				    		alert.showAndWait();
							if (alert.getResult() == ButtonType.OK) {	
								searchInput.setText("");
								searchInput2.setText("");
							}
							b=1;
							break;
						}
						else if (search1[2].length() !=4 || search2[2].length() !=4) {
							Alert alert = new Alert(AlertType.ERROR, "Must enter a date of the format mm/dd/yyyy", ButtonType.OK);
				    		alert.showAndWait();
							if (alert.getResult() == ButtonType.OK) {	
								searchInput.setText("");
								searchInput2.setText("");
							}
							b=1;
							break;
						}
						else {
							Date s1 = new Date (Integer.parseInt(search1[2]), Integer.parseInt(search1[0]), Integer.parseInt(search1[1]));
							Date s2 = new Date (Integer.parseInt(search2[2]), Integer.parseInt(search2[0]), Integer.parseInt(search2[1]));
						
							if (date.compareTo(s1) >= 0 && date.compareTo(s2) <= 0 ) {
								if (!results.contains(Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().get(j)))
									results.add(Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().get(j));
							}
						}
					}
					
					else if (stype==1) { //if its a tag-value search 
						if (searchInput2.getText().isEmpty()) searchInput2.setText("");
						for (int k = 0; k < Admin.usersList.get(adminController.indexUser).
												 getAlbums().get(i).getPhotos().get(j).getTags().size(); k++) { //for loop goes thorhg list of tags for a specific photo
							if (comboSearch.getSelectionModel().getSelectedIndex() == 1) { //if the search is OR
								if (searchInput.getText().equals(Admin.usersList.get(adminController.indexUser).
										 getAlbums().get(i).getPhotos().get(j).getTags().get(k).toString()) || 
										searchInput2.getText().equals(Admin.usersList.get(adminController.indexUser).
												 getAlbums().get(i).getPhotos().get(j).getTags().get(k).toString())) {
									if (!results.contains(Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().get(j)))
										results.add(Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().get(j));
									break; //found right tag so photo is part of results
								}
							}
							
							
							else if (comboSearch.getSelectionModel().getSelectedIndex() == 0 && searchInput2.getText().length()>0) { //if the search is AND 
								if (searchInput.getText().equals(Admin.usersList.get(adminController.indexUser).
										 getAlbums().get(i).getPhotos().get(j).getTags().get(k).toString())) {
									for (int l = 0; l < Admin.usersList.get(adminController.indexUser).
											 getAlbums().get(i).getPhotos().get(j).getTags().size(); l++ ) {
										if (searchInput2.getText().equals(Admin.usersList.get(adminController.indexUser).
												 getAlbums().get(i).getPhotos().get(j).getTags().get(l).toString())) {
											if (!results.contains(Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().get(j)))
												results.add(Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().get(j));											
											break; //found right tag so photo is part of results
										
										}
									}
									
								}
								
							}
							
							else { //default OR 
								if (searchInput.getText().equals(Admin.usersList.get(adminController.indexUser).
										 getAlbums().get(i).getPhotos().get(j).getTags().get(k).toString()) || 
										searchInput2.getText().equals(Admin.usersList.get(adminController.indexUser).
												 getAlbums().get(i).getPhotos().get(j).getTags().get(k).toString())) {
									if (!results.contains(Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().get(j)))
										results.add(Admin.usersList.get(adminController.indexUser).getAlbums().get(i).getPhotos().get(j));									
									break; //found right tag so photo is part of results
								}
							}
						}
					}
				}
    		}
    		setPics();
    	}
    }
    
    /**
     * sets all the pictures in the results list to the listView to display for the user
     */
    public void setPics() { 
    	imageList.getItems().clear();
    	if (!Admin.usersList.get(adminController.indexUser).
		  getAlbums().get(userController.indexAlbum).
		  getPhotos().isEmpty()) {
	    
	    	for(int i=0; i< results.size(); i++) {
	    		if (!results.get(i).getCaption().isEmpty()) {
	    			images.add(results.get(i).getCaption());
	    		}
	    		else {
	    			images.add(i+"");
	    		}
	    	}
    	
	    	/* for (Photo s: results) {
	    		images.add(s.getCaption());
	    	}
	    	*/
	    	
	    	imageList.setItems(images);
			
			imageList.setCellFactory(param -> new ListCell<String>() {
	   		private ImageView img = new ImageView();
	   		 @Override
	        public void updateItem(String name, boolean empty) {
	            super.updateItem(name, empty);
	            if (empty) {
	            	setText(null);
	            	setGraphic(null);
	            } else {
	            	for(int i=0; i<results.size(); i++) {
	            		if (name.equals(results.get(i).getCaption())) {
	            			img.setImage(new Image(results.get(i).getImage()));
	            			setText(name);
	            		}
	            		else if (name.equals(i+"")) {
	            			img.setImage(new Image(results.get(i).getImage()));
	            		}
	            	} 
	            
	                img.setFitWidth(50);
	                img.setFitHeight(40);
	                setGraphic(img);
	                if (name.length()>1)
	                	setText(name);
	            }
	   		 }});
    	
    	}
    
    }
    
    /**
     * changes if the listener notices a change in index on the ChoiceBox
     * sets up the search stage based on the type of search (dateTime or tag)
     */
    public void setSearch() {
    	fieldChoices.getItems().clear();
    	fieldChoices.getItems().add("date range");
    	fieldChoices.getItems().add("tag-value");
    
    	comboSearch.getItems().add("AND");
    	comboSearch.getItems().add("OR");
    	
    	comboSearch.setVisible(false);
    	
    }
}
