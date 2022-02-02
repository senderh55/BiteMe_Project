package branchManager;

import java.util.ArrayList;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the BranchManUnApprovedUsersWindow.
 */
public class BranchManUnApprovedUsersForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The user scroll pane. */
	@FXML
	private ScrollPane userScrollPane;

	/** The user V box - to show all the relevant users list. */
	@FXML
	private VBox userVBox;

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
	 * @param event - an ActionEvent from the BranchManUnApprovedUsersWindow
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
	 * @param event - an ActionEvent from the BranchManUnApprovedUsersWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the BranchManUnApprovedUsersWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_USERSACTIONS);
	}

	/**
	 * Sets fields and functionality in the BranchManUnApprovedUsersWindow.
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
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GETALLUSERS);
		message.addToList(
				"SELECT * FROM user WHERE accountStatus = 'E_WAITING_APPROVAL' AND restName = 'NA';");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

	/**
	 * Builds the unapproved user list on the screen.
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

				userRow.setOnMouseClicked(eventMouseClicked -> {
					userViewController.getUserController().setChosenUser(u);
				  	userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_OPENNEWACCOUNT, ClientUI.class,
							MyScreenEnum.BRANCHMAN_OPENNEWACCOUNT.getFXMLName(), userViewController);
					userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_OPENNEWACCOUNT);	
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

				Text userRole = new Text("Role: " +  u.getRole());
				Text phoneNum = new Text("Phone Number: " + u.getPhoneNumber());
				userInfo.getChildren().addAll(userRole, phoneNum);
				userRow.getChildren().addAll(imageHolder, userName, userInfo);
				userRow.setAlignment(Pos.CENTER_LEFT);
				userInfo.setAlignment(Pos.CENTER_LEFT);
				userVBox.getChildren().add(userRow);

			}

		}
	}


}