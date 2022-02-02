package client;

import javafx.application.Application;
import javafx.stage.Stage;
import user.UserViewController;

/**
 * This class is where the client side begins - main function.
 */
public class ClientUI extends Application {
	

	// public static ClientController chat; //only one instance
	
	/** The key. */
	Object key = new Object();

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception if any kind of Exception occurs
	 */
	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	/**
	 * Start the GUI.
	 *
	 * @param primaryStage - Stage represents the primary stage
	 * @throws Exception if any kind of Exception occurs
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
						  		
//		MainWindowForm mainWindowForm = new MainWindowForm(); // create Main window form
//		mainWindowForm.start(primaryStage);
		UserViewController uvc;
		uvc = new UserViewController(primaryStage);
		
	}


}
