package server;

import gui.ServerForm;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Class ServerUI.
 */
public class ServerUI extends Application{

		/**
		 * The main method.
		 *
		 * @param args the arguments
		 * @throws Exception the exception
		 */
		public static void main( String args[] ) throws Exception
		   { 
			    launch(args);  
		   } // end main
		 
		/**
		 * Start.
		 *
		 * @param primaryStage the primary stage
		 * @throws Exception the exception
		 */
		@Override
		public void start(Stage primaryStage) throws Exception {
							  		
			ServerForm serverFormController = new ServerForm(); // create StudentFrame
			serverFormController.start(primaryStage);
		}
}