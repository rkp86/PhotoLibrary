package app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This is the main class Photos that begins the application by opening the login window. 
 * 
 * @author Roma Patel(rkp86)
 * @author Omer Syed Farooq (ofs9)
 * @version 1.0
 * @since 2022-03-29

 */
public class Photos extends Application {

		/**
		 * This creates a stage for the fxml to be loaded onto for user display.
		 */
		Stage mainstage; 
		
		/**
		 * the start method opens the login stage in a new window for the first UI
		 */
		@Override
		public void start(Stage stage) throws Exception {
			
			// set up FXML loader
			mainstage = stage;
			mainstage.setTitle("Photo Library");
			
			try {
			FXMLLoader loader = new FXMLLoader();   
			loader.setLocation(getClass().getResource("/view/login.fxml"));
			
			AnchorPane root = (AnchorPane)loader.load();

			Scene scene = new Scene(root); 
			stage.setScene(scene);
			stage.show(); 
			
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * the main method that launches the start method
		 * @param args is the argument array 
		 */
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			launch(args);

		}

	}
