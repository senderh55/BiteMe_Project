package user;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Order.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the UserAllOrders window.
 */
public class UserAllOrdersForm implements IScreenController {

	/**
	 * The combined controller for many other controllers
	 */
	UserViewController userViewController;

	/**
	 * Text in the UserAllOrdersWindow screen
	 */
	@FXML
	private Text personalWelcomeLabel;

	/**
	 * ScrollPane in the UserAllOrdersWindow screen
	 */
	@FXML
	private ScrollPane allOrdersScrollPane;

	/**
	 * VBox in the UserAllOrdersWindow screen
	 */
	@FXML
	private VBox usersVBox;

	/**
	 * Button in the UserAllOrdersWindow screen
	 */
	@FXML
	private Button backButton;

	/**
	 * Button in the UserAllOrdersWindow screen
	 */
	@FXML
	private Button logoutButton;

	/**
	 * Button in the UserAllOrdersWindow screen
	 */
	@FXML
	private Button HomePageButton;

	/**
	 * Returns to the previous window
	 * 
	 * @param event - an ActionEvent from the UserAllOrdersWindow
	 *
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().setScreen(MyScreenEnum.USER_ENTRANCE_WINDOW);
	}

	/**
	 * Logging out from logged in account
	 * 
	 * @param event - an ActionEvent from the UserAllOrdersWindow
	 *
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
	 * Go to the user home page
	 * 
	 * @param event - an ActionEvent from the UserAllOrdersWindow
	 *
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();

	}

	/**
	 * Sets the userViewController variable
	 * 
	 * @param UVC set the userViewController
	 *
	 */
	@Override
	public void setUserViewController(UserViewController UVC) {
		userViewController = UVC;
	}

	/**
	 * Sets fields and functionality in the UserAllOrdersWindow
	 *
	 *
	 */
	@Override
	public void setParameters() {
		String tempName = userViewController.getUserController().getUser().getPrivateName();
		String name = tempName.substring(0, 1).toUpperCase() + tempName.substring(1);
		personalWelcomeLabel.setText(name + " Orders");
		personalWelcomeLabel.setUnderline(true);
		getUsersData();
		while (!userViewController.getClientController().getMessageController()
				.getE_GET_ALL_USER_ORDERS_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_GET_ALL_USER_ORDERS...");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_GET_ALL_USER_ORDERS_MsgProccessed(false);
		buildUserHBox();
	}

	/**
	 * Sends a message to the server to get the user data
	 *
	 *
	 */
	public void getUsersData() {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_ALL_USER_ORDERS);
		message.addToList("SELECT * FROM biteme_db.order WHERE userID = '"
				+ userViewController.getUserController().getUser().getUserID() + "';");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

	/**
	 * Builds usersVBox field in the UserAllOrdersWindow
	 * 
	 *
	 */
	public void buildUserHBox() {
		ArrayList<Order> orderList = userViewController.getOrderController().getAllUserOrders();
		if (orderList != null) {
			for (Order o : orderList) {
				HBox userRow = new HBox();
				userRow.setSpacing(45);
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
				// Image restLogo = new Image("test");
				// ImageView iv = new ImageView(restLogo);
				StackPane imageHolder = new StackPane();
				imageHolder.setPrefSize(117, 62);
				imageHolder.setMinSize(117, 62);
				imageHolder.setMaxSize(117, 62);
				// imageHolder.getChildren().add(iv);
				// ImageView imageView = new ImageView(); ADD SAVING IMAGES TO DB AND
				// IMPLEMENTING THEM HERE
				Text orderNumber = new Text("Order: " + o.getOrderNumber());
//				orderNumber.setWrappingWidth(100);
				orderNumber.setFont(Font.font("System", 18));

				Text orderTime = new Text("Time: " + o.getDesiredOrderTime().toString());
				orderTime.setFont(Font.font("System", 18));
				Text orderDate = new Text("Date: " + o.getDesiredOrderDate().toString());
				orderDate.setFont(Font.font("System", 18));
				DecimalFormat df = new DecimalFormat("0.00##");
				Float totalAmountFloat = Float.parseFloat(String.valueOf(o.getTotalPrice()));
				Text orderTotalAmount = new Text("Total: " + df.format(totalAmountFloat));
				orderTotalAmount.setFont(Font.font("System", 18));
				// Text orderRes = new Text(o.getOrderApprovedByRestTime().toString());
				orderTime.setFont(Font.font("System", 18));

//				VBox orderInfo = new VBox();
//				orderInfo.setPadding(new Insets(0, 0, 0, 50));
//			
//				String userTypeFromEnum = o.getUserType().equals(E_UserTypeEnum.E_REG_USER) ? "Regular" : "Business";
//				String userStatusFromEnum = o.getAccountStatus().equals(E_AccountStatus.E_FROZEN) ? "Frozen" : "Active";
//				Text userType = new Text("User Type: " + userTypeFromEnum);
//				Text userStatus = new Text("Status: " + userStatusFromEnum);
//				Text phoneNum = new Text("Phone Number: " + o.getPhoneNumber());
//				orderInfo.getChildren().addAll(userType, userStatus, phoneNum);
				userRow.getChildren().addAll(imageHolder, orderNumber, orderTime, orderDate, orderTotalAmount);// ,
																												// orderInfo
				userRow.setAlignment(Pos.CENTER_LEFT);
//				orderInfo.setAlignment(Pos.CENTER_LEFT);
				usersVBox.getChildren().add(userRow);
			}
		}

	}

}
