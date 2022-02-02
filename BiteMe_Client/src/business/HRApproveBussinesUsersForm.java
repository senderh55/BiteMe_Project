package business;

import java.util.ArrayList;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the HRApproveBusinessUsersWindow.
 */
public class HRApproveBussinesUsersForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

    /** The personal welcome label. */
    @FXML
    private Text personalWelcomeLabel;

    /** The business user scroll pane. */
    @FXML
    private ScrollPane businessUserScrollPane;

    /** The business user V box. */
    @FXML
    private VBox businessUserVBox;

    /** The back button. */
    @FXML
    private Button backButton;

    /** The logout button. */
    @FXML
    private Button logoutButton;

    /** The Home page button. */
    @FXML
    private Button HomePageButton;
    
    
	/**
	 * Logout of account.
	 *
	 * @param event - an ActionEvent from the HRApproveBusinessUsersWindow
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
	 * Open home page.
	 *
	 * @param event - an ActionEvent from the HRApproveBusinessUsersWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the HRApproveBusinessUsersWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().setScreen(MyScreenEnum.HR_ENTRANCE);
	}

	/**
	 * Sets fields and functionality in the BranchManChooseRestaurantsWindow.
	 */
	@Override
	public void setParameters() {
		getUsersData();
		while (!userViewController.getClientController().getMessageController().getE_USER_LIST_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_USER_LIST...");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_USER_LIST_MsgProccessed(false);
		buildUserHBox();
	}

	/**
	 * Gets the users data from the server (DB) into the userViewController.
	 */
	public void getUsersData() {
	int w4cBusiness = userViewController.getUserController().getUser().getBusinessW4C();

		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GETALLUSERS);
		message.addToList(
				"SELECT * FROM user WHERE userType = 'E_UNAPPROVED_BUSINESS_USER' AND accountStatus = 'E_ACTIVE' AND businessW4CNumber = "
						+ w4cBusiness + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

	/**
	 * Builds the approved business users list.
	 */
	public void buildUserHBox() {
		ArrayList<User> userList = userViewController.getUserController().getUserList();

		if (userList != null) {
			for (User u : userList) {
				HBox userRow = new HBox();
				userRow.setSpacing(25);
				userRow.setStyle("-fx-background-color white;");
				userRow.setStyle("-fx-padding 5;");
				userRow.setStyle("-fx-border-width 1;");
				userRow.setStyle("-fx-border-color black;");
				userRow.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);"
						+ "-fx-background-color: white;");

				userRow.setOnMouseEntered(eventMouseEntered -> {
					((HBox) eventMouseEntered.getSource()).scaleXProperty().set(1.01);
					((HBox) eventMouseEntered.getSource()).scaleYProperty().set(1.01);
					((HBox) eventMouseEntered.getSource()).scaleZProperty().set(1.01);
				});
				userRow.setOnMouseExited(eventMouseExit -> {
					((HBox) eventMouseExit.getSource()).scaleXProperty().set(1);
					((HBox) eventMouseExit.getSource()).scaleYProperty().set(1);
					((HBox) eventMouseExit.getSource()).scaleZProperty().set(1);
				});

				Button approveButton = new Button();
				approveButton.setPrefSize(80, 50);
				approveButton.setText("Approve");
				approveButton.setLayoutX(-40);
				approveButton.setLayoutY(253);
				approveButton.setStyle("-fx-background-color: DARKSEAGREEN;" + "-fx-font-weight: bold;");
				DropShadow shadow = new DropShadow();
				shadow.setRadius(5.0);
				approveButton.setEffect(shadow);
				approveButton.setOnMouseClicked(e -> {
					u.setUserType(E_UserTypeEnum.E_UNAPPROVED_BUSINESS_USER);
					approveUpdateInDB(u.getUserID());
					businessUserVBox.getChildren().remove(userRow);
				});

				// Image restLogo = new Image("test");
				// ImageView iv = new ImageView(restLogo);
				StackPane imageHolder = new StackPane();
				imageHolder.setPrefSize(117, 62);
				imageHolder.setMinSize(117, 62);
				imageHolder.setMaxSize(117, 62);
				// imageHolder.getChildren().add(iv);
				// ImageView imageView = new ImageView(); ADD SAVING IMAGES TO DB AND
				// IMPLEMENTING THEM HERE
				Text userName = new Text(u.getPrivateName() + " " + u.getLastName());
				userName.setWrappingWidth(275);
				userName.setFont(Font.font("System", 18));
				VBox userInfo = new VBox();
				userInfo.setPadding(new Insets(0, 0, 0, 50));

				Text BusinessName = new Text("Allocated to: " + u.getBusinessName());
				Text BusinessW4Ccode = new Text("Business W4C Code: " + u.getBusinessW4C());
				Text phoneNum = new Text("Phone Number: " + u.getPhoneNumber());
				userInfo.getChildren().addAll(BusinessName, BusinessW4Ccode, phoneNum);
				userRow.getChildren().addAll(imageHolder, userName, userInfo, approveButton);
				userRow.setAlignment(Pos.CENTER_LEFT);
				userInfo.setAlignment(Pos.CENTER_LEFT);
				businessUserVBox.getChildren().add(userRow);

			}

		}

	}	
	
	/**
	 * Approve update in DB.
	 *
	 * @param userid - int represents the user ID.
	 */

	public void approveUpdateInDB(int userid) {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.addToList("UPDATE user SET UserType = 'E_BUSINESS_USER' WHERE userid = " + userid + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

}
