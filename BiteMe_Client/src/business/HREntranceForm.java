package business;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the HREntranceWindow.
 */
public class HREntranceForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The registration button. */
	@FXML
	private Button registrationButton;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The branch label. */
	@FXML
	private Text branchLabel;

	/** The confirm account button. */
	@FXML
	private Button confirmAccountButton;

	/** The logout button. */
	@FXML
	private Button logoutButton;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/**
	 * Logout of account.
	 *
	 * @param event - an ActionEvent from the HREntranceWindow
	 */
	@FXML
	void logoutOfAccount(ActionEvent event) {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userViewController.getUserController().getUser().getUserID() + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
	}

	/**
	 * Register new business.
	 *
	 * @param event - an ActionEvent from the HREntranceWindow
	 */
	@FXML
	void registerCompany(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.HR_REGISTER, ClientUI.class, MyScreenEnum.HR_REGISTER.getFXMLName(),
				userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.HR_REGISTER);
	}

	/**
	 * Open home page.
	 *
	 * @param event - an ActionEvent from the HREntranceWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	
	}

	/**
	 * Go to the Confirm business account window.
	 *
	 * @param event - an ActionEvent from the HREntranceWindow
	 */
	@FXML
	void confirmAccount(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.HR_APPROVE_BUSINESS_ACCOUNT, ClientUI.class, MyScreenEnum.HR_APPROVE_BUSINESS_ACCOUNT.getFXMLName(),
				userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.HR_APPROVE_BUSINESS_ACCOUNT);
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
	 * Sets fields and functionality in the HREntranceWindow.
	 */
	@Override
	public void setParameters() {
		User user = userViewController.getUserController().getUser();
		personalWelcomeLabel.setText("Welcome " + user.getPrivateName() + "!");
	}

}
