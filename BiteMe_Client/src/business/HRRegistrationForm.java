package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_Branches;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the HRRegistrationWindow.
 */
public class HRRegistrationForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The business register button. */
	@FXML
	private Button businessRegisterButton;

	/** The company name text. */
	@FXML
	private TextField companyNameTxt;

	/** The employers name text. */
	@FXML
	private TextField employersNameTxt;

	/** The branch split menu button. */
	@FXML
	private MenuButton branchSplitMenuButton;

	/** The north branch. */
	@FXML
	private MenuItem northBranch;

	/** The center branch. */
	@FXML
	private MenuItem centerBranch;

	/** The south branch. */
	@FXML
	private MenuItem southBranch;

	/** The logout button. */
	@FXML
	private Button logoutButton;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The input empty message. */
	@FXML
	private Text inputEmptyMsg;

	/** The success message. */
	@FXML
	private Text successMsg;

	/** The company exist message. */
	@FXML
	private Text companyExistMsg;

	/** The monthly limit text. */
	@FXML
	private TextField mouthlyLimitText;

    /** The registered message. */
    @FXML
    private Text registedmsg;


	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the HRRegistrationWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().setScreen(MyScreenEnum.HR_ENTRANCE);
	}

	/**
	 * Logout of account.
	 *
	 * @param event - an ActionEvent from the HRRegistrationWindow
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
	 * @param event - an ActionEvent from the HRRegistrationWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Registering the business in the DB.
	 *
	 * @param event - an ActionEvent from the HRRegistrationWindow
	 */
	@FXML
	void businessRegister(ActionEvent event) {
		String companyName = "'" + companyNameTxt.getText() + "'";
		String employerName = "'" + employersNameTxt.getText() + "', ";
		String branch = "'" + E_Branches.getEnum(branchSplitMenuButton.getText()) + "', ";
		String isApproved = "'NO', ";
		String mounthlyLimit = mouthlyLimitText.getText();
		if (checkIfValid()) {
			if (checkIfExist()) {
				showCompanyExistMsg();
			} else {
				int userID = userViewController.getUserController().getUser().getUserID();
				Message insertNewBusiness = new Message(OwnerEnum.E_CLIENT, OpEnum.E_INSERT_NEW_BUSINESS);
				sendMsg(insertNewBusiness, companyName +", " + employerName + branch + isApproved + mounthlyLimit);
				Message udpateHR = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE_HR);
				sendMsg(udpateHR, "businessName = " + companyName + " WHERE userID = " + userID + ";");
				while (!userViewController.getClientController().getMessageController().getE_GET_W4C_FOR_HR_MsgProccessed()) {
					try {
						System.out.println("Waiting on E_GET_W4C_FOR_HR_MsgProccessed");
						Thread.currentThread().sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				userViewController.getClientController().getMessageController().setE_GET_W4C_FOR_HR_MsgProccessed(false);
				showSuccessMsg();
			}
		} else {
			showInputEmptyMsg();
		}
	}

	/**
	 * Show input empty error message.
	 */
	private void showInputEmptyMsg() {
		companyExistMsg.setVisible(false);
		successMsg.setVisible(false);
		inputEmptyMsg.setVisible(true);	
	}

	/**
	 * Show success message.
	 */
	private void showSuccessMsg() {
		companyExistMsg.setVisible(false);
		inputEmptyMsg.setVisible(false);
		successMsg.setVisible(true);
		businessRegisterButton.setVisible(false);
	}

	/**
	 * Show company exist message.
	 */
	private void showCompanyExistMsg() {
		companyExistMsg.setVisible(true);
		inputEmptyMsg.setVisible(false);
		successMsg.setVisible(false);
	}

	/**
	 * Check if the input from the screen is valid.
	 *
	 * @return true, if valid, else false
	 */
	private boolean checkIfValid() {
		if (mouthlyLimitText.getText().isEmpty())
			return false;
		try {
			Integer.parseInt(mouthlyLimitText.getText());
		} catch (NumberFormatException e) {
			return false;
		}
		return !(companyNameTxt.getText().isEmpty() && employersNameTxt.getText().isEmpty()
				&& branchSplitMenuButton.getText().equals("Choose branch"));
	}

	/**
	 * Check if exist the input business in the DB.
	 *
	 * @return true, if exist, else false
	 */
	private boolean checkIfExist() {

		// IMPORT Business from DB
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_BUSINESS);
		sendMsg(message, "SELECT * FROM biteme_db.business;");

		while (!userViewController.getClientController().getMessageController().getE_GET_BUSINESS_MsgProccessed()) {
			try {
				System.out.println("Waiting...E_GET_BUSINESS");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_GET_BUSINESS_MsgProccessed(false);
		String companyName = companyNameTxt.getText();
		String branch = branchSplitMenuButton.getText();
		String employerName = employersNameTxt.getText();
		BusinessController businessController = userViewController.getBusinessController();
		resetInputs();
		switch (branch) {
		case "North":
			for (Business b : businessController.northBusinessList) {
				if (b.getCompanyName().equals(companyName) && b.getEmployerName().equals(employerName))
					return true;
			}
			break;
		case "Center":
			for (Business b : businessController.centerBusinessList) {
				if (b.getCompanyName().equals(companyName) && b.getEmployerName().equals(employerName))
					return true;
			}
			break;
		case "South":
			for (Business b : businessController.southBusinessList) {
				if (b.getCompanyName().equals(companyName) && b.getEmployerName().equals(employerName))
					return true;
			}
			break;
		}

		return false;
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
	 * Sets fields and functionality in the HRRegistrationWindow.
	 */
	@Override
	public void setParameters() {
		resetInputs();
		int businessW4C = userViewController.getUserController().getUser().getBusinessW4C();
		if(businessW4C != 0) {
			businessRegisterButton.setVisible(false);
			registedmsg.setVisible(true);
			return;
		}
		
		northBranch.setOnAction(e -> {
			branchSplitMenuButton.setText("North");
		});
		centerBranch.setOnAction(e -> {
			branchSplitMenuButton.setText("Center");
		});
		southBranch.setOnAction(e -> {
			branchSplitMenuButton.setText("South");
		});

	}

	/**
	 * Reset screen inputs (fields).
	 */
	private void resetInputs() {
		companyExistMsg.setVisible(false);
		inputEmptyMsg.setVisible(false);
		successMsg.setVisible(false);
		registedmsg.setVisible(false);
		companyNameTxt.setText("");
		employersNameTxt.setText("");
		branchSplitMenuButton.setText("Choose branch");
		mouthlyLimitText.setText("");
	}
	
	/**
	 * Send message to DB.
	 *
	 * @param msg - Message represents the message to the DB.
	 * @param str - String represents the query.
	 */
	private void sendMsg(Message msg, String str) {
		msg.addToList(str);
		userViewController.getClientController().handleMessageFromClientUI(msg);
	}

}
