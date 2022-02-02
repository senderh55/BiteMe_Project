package newOrder;

import client.ClientUI;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the NewOrderIdentificationWindow.
 */
public class NewOrderIdentificationForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The About us button. */
	@FXML
	private Button AboutUsButton;

	/** The Contact us button. */
	@FXML
	private Button ContactUsButton;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The Log out button. */
	@FXML
	private Button LogOutButton;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The business H box. */
	@FXML
	private HBox businessHBox;

	/** The business w4c label. */
	@FXML
	private Label businessw4cLabel;

	/** The business w4c number text field. */
	@FXML
	private TextField businessw4cNmbrTextField;

	/** The header label. */
	@FXML
	private Text headerLabel;

	/** The lets get started button. */
	@FXML
	private Button letsGetStartedButton;

	/** The private H box. */
	@FXML
	private HBox privateHBox;

	/** The read QR button. */
	@FXML
	private Button readQRButton;

	/** The status label. */
	@FXML
	private Label statusLabel;

	/** The w 4 c label. */
	@FXML
	private Label w4cLabel;

	/** The w4c number text field. */
	@FXML
	private TextField w4cNmbrTextField;

	/** The w 4 c V box. */
	@FXML
	private VBox w4cVBox;

	/** The Error message. */
	@FXML
	private Label ErrorMsg;

	/**
	 * Logout from account.
	 *
	 * @param event - an ActionEvent from the NewOrderIdentificationWindow
	 */
	@FXML
	void logout(ActionEvent event) {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userViewController.getUserController().getUser().getUserID() + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
	}

	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the NewOrderIdentificationWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.USER_ENTRANCE_WINDOW, ClientUI.class,
				MyScreenEnum.USER_ENTRANCE_WINDOW.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.USER_ENTRANCE_WINDOW);
	}

	/**
	 * Checks the input from the user and Goes to the next screen (choosing delivery type).
	 *
	 * @param event - an ActionEvent from the NewOrderIdentificationWindow
	 */
	@FXML
	void letsGetStarted(ActionEvent event) {
		if (userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_REG_USER)) {
			if (!w4cNmbrTextField.getText().equals("")) {
				try {
					if (userViewController.getUserController().getUser().getW4CNumber() == Integer
							.parseInt(w4cNmbrTextField.getText())) {
						// statusLabel.setText("");
						w4cNmbrTextField.setText("");
						userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DELIVERY_TYPE);
					} else {
						ErrorMsg.setVisible(true);
						// statusLabel.setText("Incorrect W4C details");
					}
				} catch (NumberFormatException e) {
					ErrorMsg.setVisible(true);
				}
			} else {
				ErrorMsg.setVisible(true);
				// statusLabel.setText("W4C field must be filled");
			}
		} else if (userViewController.getUserController().getUser().getUserType()
				.equals(E_UserTypeEnum.E_BUSINESS_USER)) {
			if (!businessw4cNmbrTextField.getText().equals("")) {
				if (userViewController.getUserController().getUser().getBusinessW4C() == Integer
						.parseInt(businessw4cNmbrTextField.getText())) {
					userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DELIVERY_TYPE);
				}
			} else if (userViewController.getUserController().getUser().getW4CNumber() == Integer
					.parseInt(w4cNmbrTextField.getText())) {
				userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DELIVERY_TYPE);
			} else {
				ErrorMsg.setVisible(true);
				// statusLabel.setText("Incorrect W4C details");
			}
		}
	}

	/**
	 * Read QR.
	 *
	 * @param event - an ActionEvent from the NewOrderIdentificationWindow
	 */
	@FXML
	void readQR(ActionEvent event) {
		w4cNmbrTextField.setText("" + userViewController.getUserController().getUser().getW4CNumber());
		statusLabel.setText("Succesfully Scanned QR!");
		PauseTransition pt = new PauseTransition(Duration.seconds(2));
		pt.setOnFinished(e -> {
			statusLabel.setText("");
			letsGetStarted(event);
		});
		pt.play();
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
	 * Sets fields and functionality in the NewOrderIdentificationWindow.
	 */
	@Override
	public void setParameters() {
		ErrorMsg.setVisible(false);
		User user = userViewController.getUserController().getUser();
		switch (user.getUserType()) {
		case E_REG_USER:
			w4cVBox.getChildren().remove(1);
			break;
		default:
			break;
		}

		businessw4cNmbrTextField.setOnMouseClicked(e -> {
			ErrorMsg.setVisible(false);
		});
		w4cNmbrTextField.setOnMouseClicked(e -> {
			ErrorMsg.setVisible(false);
		});
	}

}
