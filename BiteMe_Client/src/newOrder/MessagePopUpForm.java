package newOrder;

import java.util.EventObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import user.UserViewController;

/**
 * This class is the functionality for the MessagePopUp window.
 */
public class MessagePopUpForm{
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;

//	public MessagePopUpForm(UserViewController uvc) {
//		userViewController = uvc;
//	}

	/** The close button. */
@FXML
	private Button closeButton;

	/** The header text. */
	@FXML
	private Text headerText;

	/** The message text. */
	@FXML
	private Text messageText;

	/** The simulation. */
	@FXML
	private Text simulation;

	/** The to text. */
	@FXML
	private Text toText;

	/**
	 * Close the pop up screen.
	 *
	 * @param event - an ActionEvent from the MessagePopUp window
	 */
	@FXML
	void closePopUp(ActionEvent event) {
		Stage stage = (Stage) ((Node) ((EventObject) event).getSource()).getScene().getWindow();
		stage.close();
	}

	/**
	 * Sets fields and functionality in the MessagePopUp window.
	 *
	 * @param messageToDisplay - String represents the message to display
	 * @param email - String represents the email
	 */
	public void setParameters(String messageToDisplay, String email) {
		//String email = userViewController.getUserController().getUser().getEmail();
		toText.setText("To: " + email);
		messageText.setText(messageToDisplay);
	}


}
