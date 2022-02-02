package branchManager;

import java.util.ArrayList;

import business.Business;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the BranchManUserInfoWindow.
 */
public class BranchManUserInfoForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;
	
	/** The business. */
	Business business;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The update user button. */
	@FXML
	private Button updateUserButton;

	/** The regular button. */
	@FXML
	private RadioButton regularBtn;

	/** The business button. */
	@FXML
	private RadioButton businessBtn;

	/** The active button. */
	@FXML
	private RadioButton activeBtn;

	/** The frozen button. */
	@FXML
	private RadioButton frozenBtn;

	/** The delete button. */
	@FXML
	private Button deleteButton;

	/** The logout button. */
	@FXML
	private Button logoutButton;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The choose business menu. */
	@FXML
	private MenuButton chooseBussinesMenu;
	
    /** The choose options message. */
    @FXML
    private Text chooseOptionsMsg;

    /** The choose business message. */
    @FXML
    private Text chooseBusinessMsg;

    /** The Success message. */
    @FXML
    private Text SucsessMsg;


	/**
	 * Logout of account.
	 *
	 * @param event - an ActionEvent from the BranchManUserInfoWindow
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
	 * Sets the user view controller.
	 *
	 * @param UVC the new user view controller
	 */
	@Override
	public void setUserViewController(UserViewController UVC) {
		userViewController = UVC;
	}

	/**
	 * Sets fields and functionality in the BranchManUserInfoWindow.
	 */
	@Override
	public void setParameters() {
		String firstName = userViewController.getUserController().getChosenUser().getPrivateName();
		String lastName = userViewController.getUserController().getChosenUser().getLastName();
		firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
		lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
		personalWelcomeLabel.setText(firstName + "  " + lastName);
		personalWelcomeLabel.setUnderline(true);
		
		chooseOptionsMsg.setVisible(false);
		chooseBusinessMsg.setVisible(false);
		SucsessMsg.setVisible(false);

		getApprovedBussinessData();
		while (!userViewController.getClientController().getMessageController().getE_GET_BUSINESS_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_BUSINESS_LIST...");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_GET_BUSINESS_MsgProccessed(false);
		buildBussinessMenu();

		regularBtn.setOnMouseClicked(e -> {
			regularBtn.setSelected(true);
			businessBtn.setSelected(false);
			chooseBussinesMenu.setVisible(false);
			frozenBtn.setSelected(false);
		});

		businessBtn.setOnMouseClicked(e -> {
			regularBtn.setSelected(false);
			businessBtn.setSelected(true);
			chooseBussinesMenu.setVisible(true);
			frozenBtn.setSelected(false);
		});

		activeBtn.setOnMouseClicked(e -> {
			frozenBtn.setSelected(false);
			activeBtn.setSelected(true);
		});

		frozenBtn.setOnMouseClicked(e -> {
			activeBtn.setSelected(false);
			regularBtn.setSelected(false);
			businessBtn.setSelected(false);
			frozenBtn.setSelected(true);
			chooseBussinesMenu.setVisible(false);
		});

	}

	/**
	 * Open home page.
	 *
	 * @param event - an ActionEvent from the BranchManUserInfoWindow
	 */
	@FXML
	public void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the BranchManUserInfoWindow
	 */
	@FXML
	public void backWindow(ActionEvent event) {
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_USERS);
	}

	/**
	 * Update user in DB according to the branch manager selection on the screen.
	 *
	 * @param event - an ActionEvent from the BranchManUserInfoWindow
	 */
	@FXML
	public void updateUser(ActionEvent event) {
		if(frozenBtn.isSelected()) {
			updateToFrozen();
		}
		else if(!(regularBtn.isSelected() || businessBtn.isSelected()) || !(frozenBtn.isSelected() || activeBtn.isSelected())) {
			chooseOptionsMsg.setVisible(true);
			chooseBusinessMsg.setVisible(false);
			SucsessMsg.setVisible(false);
			return;
		}
		else if (businessBtn.isSelected()) {
			if (chooseBussinesMenu.getText().equals("Choose Business")) {
				chooseOptionsMsg.setVisible(false);
				chooseBusinessMsg.setVisible(true);
				SucsessMsg.setVisible(false);
				return;
			} else {
				updateToBusinessUser();
			}
		} else {
			updateToRegular();
		}
		chooseOptionsMsg.setVisible(false);
		chooseBusinessMsg.setVisible(false);
		SucsessMsg.setVisible(true);

	}

	/**
	 * Delete the selected user in DB.
	 *
	 * @param event - an ActionEvent from the BranchManUserInfoWindow
	 */
	@FXML
	public void deleteUser(ActionEvent event) {
		int userID = userViewController.getUserController().getChosenUser().getUserID();
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.addToList("DELETE FROM user WHERE userID = " + userID + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		SucsessMsg.setText("User deleted successfully");
		chooseOptionsMsg.setVisible(false);
		chooseBusinessMsg.setVisible(false);
		SucsessMsg.setVisible(true);
	}

	/**
	 * Gets the approved business data from the server (DB).
	 */
	public void getApprovedBussinessData() {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_BUSINESS);
		String branch = "'" + userViewController.getUserController().getUser().getDefaultBranch() + "';";
		message.addToList("SELECT * FROM business WHERE isApproved = 'YES' AND branch = " + branch);
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

	/**
	 * Builds the business menu on the screen.
	 */
	public void buildBussinessMenu() {
		ArrayList<Business> ApprovedBusinessList = userViewController.getBusinessController().getAllBusinessList();
		if (ApprovedBusinessList != null) {
			for (Business b : ApprovedBusinessList) {
				MenuItem menuItem = new MenuItem(b.getCompanyName() + "-" + b.getEmployerName());
				menuItem.setOnAction(eventMenuSizes -> {
					chooseBussinesMenu.setText(b.getCompanyName() + "-" + b.getEmployerName());
					setBusiness(b);
				});
				chooseBussinesMenu.getItems().add(menuItem);
			}
		}
	}

	/**
	 * Gets the business.
	 *
	 * @return business - Business entity
	 */
	public Business getBusiness() {
		return business;
	}

	/**
	 * Sets the business.
	 *
	 * @param business - Business represents the new business
	 */
	public void setBusiness(Business business) {
		this.business = business;
	}

	/**
	 * Update to business user according to the branch manager selections on the screen.
	 */
	public void updateToBusinessUser() {
		int userID = userViewController.getUserController().getChosenUser().getUserID();
		String companyName = "'" + getBusiness().getCompanyName() + "'";
		String businessW4CNumber = "" + getBusiness().getBusinessW4CNumber();
		String mounthlyLimit = getBusiness().getMounthlyLimit();
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.addToList("UPDATE user SET userType = 'E_UNAPPROVED_BUSINESS_USER', accountStatus = 'E_ACTIVE', businessName = " + companyName
				+ ", businessW4CNumber = " + businessW4CNumber + ", businessMonthlyLimit = " + mounthlyLimit
				+ ", businessAmountUsed = 0 " + "WHERE userID = " + userID + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

	/**
	 * Update user to be regular user.
	 */
	public void updateToRegular() {
		int userID = userViewController.getUserController().getChosenUser().getUserID();
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.addToList(
				"UPDATE user SET userType = 'E_REG_USER', accountStatus = 'E_ACTIVE', businessName = 'NA', businessW4CNumber = 0, businessMonthlyLimit = 0, businessAmountUsed = 0 "
						+ "WHERE userID = " + userID + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

	/**
	 * Update account to be frozen.
	 */
	public void updateToFrozen() {
		int userID = userViewController.getUserController().getChosenUser().getUserID();
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.addToList("UPDATE user SET accountStatus = 'E_FROZEN' WHERE userID = " + userID + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

}
