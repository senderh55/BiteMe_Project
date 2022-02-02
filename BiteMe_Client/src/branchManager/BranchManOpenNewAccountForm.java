package branchManager;

import java.util.ArrayList;

import business.Business;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import restaurant.Restaurant;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the BranchManOpenNewAccountWindow.
 */
public class BranchManOpenNewAccountForm implements IScreenController {

	/** The combined controller for many other controllers. */
	private UserViewController userViewController;
	
	/** The business. */
	private Business business;

    @FXML
    private Text creditCardLabel;

    @FXML
    private TextField creditCardTextField;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The create user button. */
	@FXML
	private Button createUserButton;

	/** The Information label. */
	@FXML
	private Text InformationLabel;

	/** The fn text field. */
	@FXML
	private TextField fnTextField;

	/** The ln text field. */
	@FXML
	private TextField lnTextField;

	/** The phone number text field. */
	@FXML
	private TextField pnTextField;

	/** The id text field. */
	@FXML
	private TextField idTextField;

	/** The email text field. */
	@FXML
	private TextField emailTextField;

	/** The bite me branch text field. */
	@FXML
	private TextField biteMeBranchTextField;

	/** The user type menu. */
	@FXML
	private MenuButton userTypeMenu;

	/** The business menu. */
	@FXML
	private MenuButton businessMenu;

	/** The restaurants menu. */
	@FXML
	private MenuButton restaurantMenu;

	/** The role field. */
	@FXML
	private TextField roleField;

	/** The Log out button. */
	@FXML
	private Button LogOutButton;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	
    /** The select type message. */
    @FXML
    private Text selectTypeMsg;

    /** The choose business message. */
    @FXML
    private Text chooseBusinessMsg;

    /** The Success message. */
    @FXML
    private Text SucsessMsg;

    
	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the BranchManOpenNewAccountWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_USERSACTIONS);
	}

	/**
	 * Creates the new user - updates his/hers information in the DB according to the user's type.
	 *
	 * @param event - an ActionEvent from the BranchManOpenNewAccountWindow
	 */
	@FXML
	void createNewUser(ActionEvent event) {
		if(userTypeMenu.getText().equals("Select type")) {
			showChooseTypeMsg();
			return;
		}
		else if(userTypeMenu.getText().equals("Business User") && businessMenu.getText().equals("Select business")) {
			showChooseBusinessMsg();
			return;
		}
		showSucsessMsg();
		switch (userTypeMenu.getText()) {
		case "Regular User":
			updateToRegular();
			break;
		case "Business User":
			updateToBusinessUser();
			break;
		case "HR":
			updateToHR();
			break;
		}
	}

	/**
	 * Logging out from account.
	 *
	 * @param event - an ActionEvent from the BranchManOpenNewAccountWindow
	 */
	@FXML
	void logOut(ActionEvent event) {
		
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userViewController.getUserController().getUser().getUserID() + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
	}

	/**
	 * Open the home page window.
	 *
	 * @param event - an ActionEvent from the BranchManOpenNewAccountWindow
	 */
	@FXML
	void openHomePageWindow(ActionEvent event) {
		userViewController.goToHomeScreen();
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
	 * Sets fields and functionality in the BranchManOpenNewAccountWindow.
	 */
	@Override
	public void setParameters() {
		creditCardTextField.setVisible(false);
		creditCardLabel.setVisible(false);
		addMenuItemForUserType("Regular User");
		addMenuItemForUserType("Business User");
		addMenuItemForUserType("HR");
		businessData();
		hideAllMsg();
		setInputs();
		
		
	}
	
	/**
	 * Adds the menu item inside the users menu on the screen.
	 *
	 * @param type - String represents the type
	 */
	public void addMenuItemForUserType(String type) {
		MenuItem menuItem = new MenuItem(type);
		menuItem.setOnAction(eventMenuSizes -> {
			userTypeMenu.setText(type);
			boolean isBusiness = type.equals("Business User");
			businessMenu.setVisible(isBusiness);
			
			boolean isRegular = type.equals("Regular User");
			creditCardTextField.setVisible(isRegular);
			creditCardLabel.setVisible(isRegular);
		});
		userTypeMenu.getItems().add(menuItem);
	}
	
	/**
	 * Sets the text fields data.
	 */
	public void setInputs() {
		User u = userViewController.getUserController().getChosenUser();
		String branch = userViewController.getUserController().getUser().getDefaultBranch().getString();
		fnTextField.setText(u.getPrivateName());
		lnTextField.setText(u.getLastName());
		pnTextField.setText(u.getPhoneNumber());
		idTextField.setText("" + u.getUserID());
		emailTextField.setText(u.getEmail());
		roleField.setText(u.getRole());
		biteMeBranchTextField.setText(branch);
		creditCardTextField.setText(u.getCardNumber());
	}
	
	/**
	 * Sets the business data.
	 */
	public void businessData() {
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
		
	}
	
	
	/**
	 * Gets the approved business data from the server (DB) into the userViewController.
	 */
	public void getApprovedBussinessData() {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_BUSINESS);
		String branch = "'" + userViewController.getUserController().getUser().getDefaultBranch() + "';";
		message.addToList("SELECT * FROM business WHERE isApproved = 'YES' AND branch = " + branch);
		userViewController.getClientController().handleMessageFromClientUI(message);
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
	 * @param business - the new business
	 */
	public void setBusiness(Business business) {
		this.business = business;
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
					businessMenu.setText(b.getCompanyName() + "-" + b.getEmployerName());
					setBusiness(b);
				});
				businessMenu.getItems().add(menuItem);
			}
		}
	}
	
	/**
	 * Update user to be regular user.
	 */
	public void updateToRegular() {
		int userID = userViewController.getUserController().getChosenUser().getUserID();
		String branch = "'" + userViewController.getUserController().getUser().getDefaultBranch() + "', ";
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE_USER);
		message.addToList("defaultBranch = " + branch +  "businessW4CNumber = 0 WHERE userID = " + userID + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

	/**
	 * Update user to be business user.
	 */
	public void updateToBusinessUser() {
		int userID = userViewController.getUserController().getChosenUser().getUserID();
		String companyName = "'" + getBusiness().getCompanyName() + "'";
		String businessW4CNumber = "" + getBusiness().getBusinessW4CNumber();
		String mounthlyLimit = getBusiness().getMounthlyLimit();
		String branch = "'" + userViewController.getUserController().getUser().getDefaultBranch() + "', ";
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_INSERT_BUSINESS_USER);
		message.addToList("defaultBranch = " + branch + "businessName = " + companyName
				+ ", businessW4CNumber = " + businessW4CNumber + ", businessMonthlyLimit = " + mounthlyLimit
				+ ", businessAmountUsed = 0 " + "WHERE userID = " + userID + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}
	
	/**
	 * Update user to be HR user.
	 */
	public void updateToHR() {
		int userID = userViewController.getUserController().getChosenUser().getUserID();
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_INSERT_HR_USER);
		String branch = "'" + userViewController.getUserController().getUser().getDefaultBranch() + "' ";
		message.addToList(", defaultBranch = " + branch + "WHERE userID = " + userID + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}
	
	/**
	 * Show success message on the screen.
	 */
	public void showSucsessMsg() {
		selectTypeMsg.setVisible(false);
		chooseBusinessMsg.setVisible(false);
		SucsessMsg.setVisible(true);
		createUserButton.setVisible(false);
		
	}
	
	/**
	 * Show choose type message on the screen.
	 */
	public void showChooseTypeMsg() {
		selectTypeMsg.setVisible(true);
		chooseBusinessMsg.setVisible(false);
		SucsessMsg.setVisible(false);
	}
	
	/**
	 * Show choose business message on the screen.
	 */
	public void showChooseBusinessMsg() {
		selectTypeMsg.setVisible(false);
		chooseBusinessMsg.setVisible(true);
		SucsessMsg.setVisible(false);
	}
	
	/**
	 * Hide all message on the screen.
	 */
	public void hideAllMsg() {
		selectTypeMsg.setVisible(false);
		chooseBusinessMsg.setVisible(false);
		SucsessMsg.setVisible(false);
	}
}
