package branchManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the BranchManProduceReportsWindow.
 */
public class BranchManProduceReportsForm implements IScreenController {
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;
    
    /** The Home page button. */
    @FXML
    private Button HomePageButton;
  
    /** The logout button. */
    @FXML
    private Button logoutButton;

    /** The personal welcome label. */
    @FXML
    private Text personalWelcomeLabel;

    /** The reports sent label. */
    @FXML
    private Label reportsSentLabel;

    /** The send reports to CEO button. */
    @FXML
    private Button sendReportsToCEOButton;

    /**
     * Logout of account.
     *
     * @param event - an ActionEvent from the BranchManProduceReportsWindow
     */
    @FXML
    void logoutOfAccount(ActionEvent event) {

    }

    /**
     * Send report to CEO.
     *
     * @param event - an ActionEvent from the BranchManProduceReportsWindow
     */
    @FXML
    void sendReportToCEO(ActionEvent event) {
    	String branch = userViewController.getUserController().getUser().getDefaultBranch().toString();
    	Message sendReportsToCEO = userViewController.getReportController().buildBranchmanToCEOReport(branch);
    	if(sendReportsToCEO.getMsgArrayL().size() > 0) {
    		userViewController.getClientController().handleMessageFromClientUI(sendReportsToCEO);
    	}
    	reportsSentLabel.setVisible(true);
    	
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
	 * Sets fields and functionality in the BranchManProduceReportsWindow.
	 */
	@Override
	public void setParameters() {
		reportsSentLabel.setVisible(false);
		HomePageButton.setOnAction(e->{
			userViewController.goToHomeScreen();
		});
		logoutButton.setOnAction(e->{
			Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
			message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
					+ userViewController.getUserController().getUser().getUserID() + ";");
			userViewController.getClientController().handleMessageFromClientUI(message);
			userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
		});
	}

}
