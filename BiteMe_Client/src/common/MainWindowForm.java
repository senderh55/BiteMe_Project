package common;

import java.net.UnknownHostException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;

/**
 * The Class MainWindowForm.
 */
public class MainWindowForm extends Application implements IScreenController {
	
	/** The combined controller for many other controllers */
	UserViewController userViewController;

    /** The About us button. */
    @FXML
    private Button AboutUsButton;

    /** The Contact us button. */
    @FXML
    private Button ContactUsButton;

    /** The Host text filed. */
    @FXML
    private TextField HostTextFiled;

    /** The Port text filed. */
    @FXML
    private TextField PortTextFiled;

    /** The log in button. */
    @FXML
    private Button logInButton;
    /** The error message label */
    @FXML
    private Label errorMessageLabel;

	/**
	 * Open log in window.
	 *
	 * @param event - an ActionEvent from the LogInWindow
	 * @throws Exception - open connection with the server
	 */
	@FXML
	void OpenLogInWindow(ActionEvent event) throws Exception {
		if(!(HostTextFiled.getText().equals("") || PortTextFiled.getText().equals(""))){
			
			if(checkHostAndPortInputAreValid()) {
				OpenConnectionWithServer();
				userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
			}
			else {
				errorMessageLabel.setVisible(true);
				PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
				pt.setOnFinished(e -> {
					errorMessageLabel.setVisible(false);
				});
				System.out.println("Client did not connect");
			}
		}
		errorMessageLabel.setVisible(true);
		PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
		pt.setOnFinished(e -> {
			errorMessageLabel.setVisible(false);
		});
	}

	/**
	 * Open connection with server.
	 */
	void OpenConnectionWithServer() {
			userViewController.setHost(HostTextFiled.getText());
			userViewController.setPort(Integer.parseInt(PortTextFiled.getText()));
	}

	/*
	 * NOTES System.out.println("Student ID Found"); ((Node)
	 * event.getSource()).getScene().getWindow().hide(); // hiding primary window
	 * Stage primaryStage = new Stage(); Pane root =
	 * loader.load(getClass().getResource("/gui/StudentForm.fxml").openStream());
	 * StudentFormController studentFormController = loader.getController();
	 * studentFormController.loadStudent(ChatClient.s1);
	 * 
	 * Scene scene = new Scene(root);
	 * scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").
	 * toExternalForm()); primaryStage.setTitle("Student Managment Tool");
	 * 
	 * primaryStage.setScene(scene); primaryStage.show();
	 */

	/**
	 * Start function to start the screen javafx.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
//		VBox vbox;
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("/common/MainWindow.fxml")); // LOADER EXCEPTION THROWN
//			vbox = loader.load();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//		Scene sceneMainWindow = new Scene(vbox);
//		primaryStage.setTitle("BiteMe");
//		primaryStage.setScene(sceneMainWindow);
//		primaryStage.show();

	}

	/**
	 * Sets the user view controller.
	 *
	 * @param UVC the new user view controller
	 */
	@Override
	public void setUserViewController(UserViewController UVC) {
		userViewController = UVC;

	}

	/**
	 * Sets fields and functionality in the UserAllOrdersWindow
	 */
	@Override
	public void setParameters() {
		
		errorMessageLabel.setVisible(false);
		errorMessageLabel.setText("Invalid host or port!");
		errorMessageLabel.setTextFill(Color.RED);
		errorMessageLabel.setStyle("-fx-background-color: #FFE5CC");
		HostTextFiled.setOnAction(e -> {
			errorMessageLabel.setVisible(false);
		});
		PortTextFiled.setOnAction(e -> {
			errorMessageLabel.setVisible(false);
		});
		PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
		pt.setOnFinished(i -> {
			errorMessageLabel.setVisible(false);
		});
		pt.play();
	}
	
	
	/**
	 * Check host and port input are valid.
	 *
	 * @return true, if valid, else false
	 * @throws UnknownHostException - the host input is unknown
	 */
	public boolean checkHostAndPortInputAreValid() throws UnknownHostException{
		try {
			if(!userViewController.setClientController(HostTextFiled.getText(), Integer.parseInt(PortTextFiled.getText()))) {
				return false;
			}
			if(userViewController.getClientController().getHost().equals(HostTextFiled.getText())
					&& userViewController.getClientController().getPort() == Integer.parseInt(PortTextFiled.getText())) {
				return true;
			}
		} catch (NumberFormatException e) {
			errorMessageLabel.setVisible(true);
			PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
			pt.setOnFinished(i -> {
				errorMessageLabel.setVisible(false);
			});
			pt.play();
		} 
		
		return false;
	}

}
