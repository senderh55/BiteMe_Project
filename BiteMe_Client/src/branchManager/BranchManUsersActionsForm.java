package branchManager;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the BranchManUsersActionsWindow.
 */
public class BranchManUsersActionsForm implements IScreenController{
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;
    
    /** The change user actions button. */
    @FXML
    private Button changeUserActionsButton;

    /** The personal welcome label. */
    @FXML
    private Text personalWelcomeLabel;

    /** The open new account button. */
    @FXML
    private Button openNewAccountButton;

    /** The logout button. */
    @FXML
    private Button logoutButton;

    /** The Home page button. */
    @FXML
    private Button HomePageButton;
    
    /** The back button. */
    @FXML
    private Button backButton;

    /**
     * Logout of account.
     *
     * @param event - an ActionEvent from the BranchManUsersActionsWindow
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
     * Open change user permission window.
     *
     * @param event - an ActionEvent from the BranchManUsersActionsWindow
     */
    @FXML
    void openChangeUserPermission(ActionEvent event) {
    	userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_USERS, ClientUI.class,
				MyScreenEnum.BRANCHMAN_USERS.getFXMLName(), userViewController);
    	userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_USERS);
    }

    /**
     * Open new account window.
     *
     * @param event - an ActionEvent from the BranchManUsersActionsWindow
     */
    @FXML
    void openNewAccount(ActionEvent event) {
    	userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_CHOOSE_UNAPPROVED_USERS, ClientUI.class,
				MyScreenEnum.BRANCHMAN_CHOOSE_UNAPPROVED_USERS.getFXMLName(), userViewController);
    	userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_CHOOSE_UNAPPROVED_USERS);
    }
    
    /**
     * Go to the previous window.
     *
     * @param event - an ActionEvent from the BranchManUsersActionsWindow
     */
    @FXML
    void backWindow(ActionEvent event) {
    	userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_ENTRANCE);
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
	 * Sets fields and functionality in the BranchManUsersActionsWindow.
	 */
	@Override
	public void setParameters() {
		// TODO Auto-generated method stub
		
	}

}
