package common;

import client.ClientController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_AccountStatus;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the LogInWindow.
 */
public class LogInForm extends Application implements IScreenController {

	/** The combined controller for many other controllers */
	UserViewController userViewController;

	/** The client controller. */
	ClientController clientController;

	/** The About us button. */
	@FXML
	private Button AboutUsButton;

	/** The Contact us button. */
	@FXML
	private Button ContactUsButton;

	/** The password Show hide button. */
	@FXML
	private CheckBox ShowHideButton;

	/** The log in button. */
	@FXML
	private Button logInButton;

	/** The login status text. */
	@FXML
	private Text loginStatusText;

	/** The password field. */
	@FXML
	private PasswordField passwordPasswordField;

	/** The user name text field. */
	@FXML
	private TextField usenameTextField;

	/**
	 * Logging into account.
	 *
	 * @param event - an ActionEvent from the LogInWindow
	 */
	@FXML
	void LogIntoAccount(ActionEvent event) {
		clientController = userViewController.getClientController();
		if (clientController == null) {
			if(userViewController.setClientController(userViewController.getHost(), userViewController.getPort()))
				clientController = userViewController.getClientController();
			else {
				loginStatusText.setText("Bad connection! going back to the main window...");
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				userViewController.getScreenManager().setScreen(MyScreenEnum.MAIN_WINDOW);
			}
		}
		if (!(usenameTextField.getText().equals("") || passwordPasswordField.getText().equals(""))) {
			String searchForUser = "SELECT * from biteme_db.user WHERE username = '" + usenameTextField.getText()
					+ "' AND " + "password = '" + passwordPasswordField.getText() + "'";
			Message msg = new Message(OwnerEnum.E_CLIENT, OpEnum.E_LOGIN);
			msg.addToList(searchForUser);
			
			clientController.handleMessageFromClientUI(msg);

			while (!clientController.getMessageController().getLogInMsgProccessed()) {
				System.out.println("waiting on LogInMsgProccessed ...");

				try {
					Thread.currentThread().sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			clientController.getMessageController().setLogInMsgProccessed(false);
			boolean status = (userViewController.getUserController().getUser()
					.getAccountStatus() == E_AccountStatus.E_ACTIVE);
			
			
			if (clientController.getMessageController().getLogInMessage().equals("Incorrect Login details")) {
				loginStatusText.setText("Wrong username OR password");
			}
			// USER IS ACTIVE, Choose one of the user types
			else if (clientController.getMessageController().getIsLoggedIn() && status) {

				System.out.println("WE LOGGED IN");
				userViewController.setUpUserScreens();
				usenameTextField.setText("");
				passwordPasswordField.setText("");
				loginStatusText.setText("");
				E_UserTypeEnum userType = userViewController.getUserController().getUser().getUserType();
				switch (userType) {
				case E_REG_USER:
					userViewController.getScreenManager().setScreen(MyScreenEnum.USER_ENTRANCE_WINDOW);
					break;
				case E_BUSINESS_USER:
					userViewController.getScreenManager().setScreen(MyScreenEnum.USER_ENTRANCE_WINDOW);
					break;
				case E_UNAPPROVED_BUSINESS_USER:
					loginStatusText.setText("Buisness Account is wating for approval");
					break;
				case E_RESTAURANT_USER:
					userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ENTRANCE);
					break;
				case E_BRANCH_MAN_USER:
					userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_ENTRANCE);
					break;
				case E_HR:
					userViewController.getScreenManager().setScreen(MyScreenEnum.HR_ENTRANCE);
					break;
				case E_CEO_USER:
					userViewController.getScreenManager().setScreen(MyScreenEnum.CEO_ENTRANCE);

				default:
					break;
				}
				clientController.getMessageController().setLogInMsgProccessed(false);

			}
			else if(!clientController.getMessageController().getIsLoggedIn()) {
				loginStatusText.setText(clientController.getMessageController().getLogInMessage());
				System.out.println("User is already connected.");
			}
			// User is not active
			else {
				if (userViewController.getUserController().getUser().getAccountStatus()
						.equals(E_AccountStatus.E_FROZEN)) {
					loginStatusText.setText("User account is Frozen");
				} else if (userViewController.getUserController().getUser().getAccountStatus()
						.equals(E_AccountStatus.E_WAITING_APPROVAL)) {
					loginStatusText.setText("User account is wating for approval");
			
				}
			}
		}

		else {
			loginStatusText.setText("The Username and Password fields must be filled");
		}

	}

	/**
	 * Start function to start the screen javafx.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
//		
//		VBox vbox;
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("/common/LogIn.fxml")); // LOADER EXCEPTION THROWN
//			vbox = loader.load();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//		Scene sceneLogInWindow = new Scene(vbox);
//		primaryStage.setTitle("BiteMe");
//		primaryStage.setScene(sceneLogInWindow);
//		primaryStage.show();
//		
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
		// TODO Auto-generated method stub

	}

}
