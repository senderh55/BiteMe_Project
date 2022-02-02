package branchManager;

import client.ClientUI;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the BranchManRestaurantsWindow.
 */
public class BranchManRestaurantsForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The register button. */
	@FXML
	private Button registerButton;

	/** The name text field. */
	@FXML
	private TextField nameTextField;

	/** The branch text field. */
	@FXML
	private TextField branchTextField;

	/** The phone text field. */
	@FXML
	private TextField phoneTextField;

	/** The description text field. */
	@FXML
	private TextField descriptionTextField;

	/** The address text field. */
	@FXML
	private TextField addressTextField;

	/** The success label. */
	@FXML
	private Label successLabel;

	/** The user name text field. */
	@FXML
	private TextField userNameTextField;

	/** The logout button. */
	@FXML
	private Button logoutButton;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the BranchManRestaurantsWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_CHOOSE_RESTAURANTS, ClientUI.class,
				MyScreenEnum.BRANCHMAN_CHOOSE_RESTAURANTS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_CHOOSE_RESTAURANTS);
	}

	/**
	 * Logout of account.
	 *
	 * @param event - an ActionEvent from the BranchManRestaurantsWindow
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
	 * Open home page.
	 *
	 * @param event - an ActionEvent from the BranchManRestaurantsWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Register new restaurant.
	 *
	 * @param event - an ActionEvent from the BranchManRestaurantsWindow
	 */
	@FXML
	void registerNewRestaurant(ActionEvent event) {
		Message updateRegisterRestaurant = new Message(OwnerEnum.E_CLIENT, OpEnum.E_INSERT_NEW_RESTAURANT);
		String restName = "'" + nameTextField.getText() + "', ";
		String branch = "'" + userViewController.getUserController().getUser().getDefaultBranch().toString() + "', ";
		String description = "'" + descriptionTextField.getText() + "', ";
		String address = "'" + addressTextField.getText() + "', ";
		String PhoneNumber = "'" + phoneTextField.getText() + "'";
		updateRegisterRestaurant.getMsgArrayL().add(restName + branch + description + address + PhoneNumber);
		userViewController.getClientController().handleMessageFromClientUI(updateRegisterRestaurant);

		Message updateUserToRestaurant = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE_USER_TO_RESTAURANT);
		String toUpdate = ", defaultBranch = " + branch + "userType = 'E_RESTAURANT_USER', accountStatus = 'E_ACTIVE' "
				+ "WHERE username = '" + userNameTextField.getText() + "'";
		updateUserToRestaurant.getMsgArrayL().add(toUpdate);
		userViewController.getClientController().handleMessageFromClientUI(updateUserToRestaurant);

		successLabel.setVisible(true);
		registerButton.setVisible(false);

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
	 * Sets fields and functionality in the BranchManRestaurantsWindow.
	 */
	@Override
	public void setParameters() {
		registerButton.setVisible(true);
		User u = userViewController.getUserController().getChosenUser();
		String branch = userViewController.getUserController().getUser().getDefaultBranch().getString();
		successLabel.setVisible(false);
		branchTextField.setText(branch);
		nameTextField.setText(u.getRestName());
		phoneTextField.setText(u.getPhoneNumber());
		descriptionTextField.setText(u.getDescription());
		addressTextField.setText(u.getAddress());
		userNameTextField.setText(u.getUsername());

	}

}
