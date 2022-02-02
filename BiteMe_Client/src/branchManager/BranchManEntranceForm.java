package branchManager;

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
 * This class is the functionality for the BranchManEntranceWindow.
 */
public class BranchManEntranceForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

    /** The user actions button. */
    @FXML
    private Button userActionsButton;

    /** The restaurant actions button. */
    @FXML
    private Button restaurantActionsButton;

    /** The my reports button. */
    @FXML
    private Button myReportsButton;

    /** The personal welcome label. */
    @FXML
    private Text personalWelcomeLabel;

    /** The system reports button. */
    @FXML
    private Button systemReportsButton;

    /** The approve business button. */
    @FXML
    private Button approveBusinessBtn;

    /** The logout button. */
    @FXML
    private Button logoutButton;

    /** The Home page button. */
    @FXML
    private Button HomePageButton;

	/**
	 * Open branch manager system reports window.
	 *
	 * @param event an ActionEvent from the BranchManEntranceWindow
	 */
	@FXML
	void OpenSystemReportsWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_PRODUCE_REPORTS, ClientUI.class,
				MyScreenEnum.BRANCHMAN_PRODUCE_REPORTS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_PRODUCE_REPORTS);
	}

	/**
	 * Open branch manager  business approval window.
	 *
	 * @param event  an ActionEvent from the BranchManEntranceWindow
	 */
	@FXML
	void OpenBusinessWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_APPROVEBUSINESS, ClientUI.class,
				MyScreenEnum.BRANCHMAN_APPROVEBUSINESS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_APPROVEBUSINESS);
	}
    
    /**
     * Open branch manager  restaurants window.
     *
     * @param event  an ActionEvent from the BranchManEntranceWindow
     */
    @FXML
    void OpenRestaurantWindow(ActionEvent event) {
    	userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_CHOOSE_RESTAURANTS, ClientUI.class,
				MyScreenEnum.BRANCHMAN_CHOOSE_RESTAURANTS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_CHOOSE_RESTAURANTS);
    }
	
	/**
	 * Open branch manager  my reports window.
	 *
	 * @param event  an ActionEvent from the BranchManEntranceWindow
	 */
	@FXML
	void OpenMyReportsWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_MYREPORTS, ClientUI.class,
				MyScreenEnum.BRANCHMAN_MYREPORTS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_MYREPORTS);
	}

	/**
	 * Logout of account.
	 *
	 * @param event  an ActionEvent from the BranchManEntranceWindow
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
	 * Open branch manager  users window.
	 *
	 * @param event  an ActionEvent from the BranchManEntranceWindow
	 */
	@FXML
	void openUsersWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_USERSACTIONS, ClientUI.class,
				MyScreenEnum.BRANCHMAN_USERSACTIONS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_USERSACTIONS);
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
	 * Sets fields and functionality in the BranchManEntranceWindow.
	 */
	@Override
	public void setParameters() {
		User user = userViewController.getUserController().getUser();
		personalWelcomeLabel.setText("Welcome " + user.getPrivateName() + "!");

	}

}
