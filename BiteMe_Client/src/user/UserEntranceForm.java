package user;

import java.time.LocalTime;
import java.util.ArrayList;

import Order.Order;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import utils.E_Branches;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the UserEntrnaceWindow.
 */
public class UserEntranceForm implements IScreenController {
	/**
	 * The combined controller for many other controllers
	 */
	UserViewController userViewController;

	/**
	 * Button in the UserEntranceWindow screen
	 */
	@FXML
	private Button AboutUsButton;

	/**
	 * Button in the UserEntranceWindow screen
	 */
	@FXML
	private Button ContactUsButton;

	/**
	 * Button in the UserEntranceWindow screen
	 */
	@FXML
	private Button HomePageButton;

	/**
	 * MenuButton in the UserEntranceWindow screen
	 */
	@FXML
	private MenuButton branchSplitMenuButton;

	/**
	 * MenuItem in the UserEntranceWindow screen
	 */
	@FXML
	private MenuItem centerBranch;

	/**
	 * Button in the UserEntranceWindow screen
	 */
	@FXML
	private Button logoutButton;

	/**
	 * Button in the UserEntranceWindow screen
	 */
	@FXML
	private Button newOrderButton;

	/**
	 * MenuItem in the UserEntranceWindow screen
	 */
	@FXML
	private MenuItem northBranch;

	/**
	 * Text in the UserEntranceWindow screen
	 */
	@FXML
	private Text personalWelcomeLabel;

	/**
	 * Button in the UserEntranceWindow screen
	 */
	@FXML
	private Button recentOrderButton;

	/**
	 * MenuItem in the UserEntranceWindow screen
	 */
	@FXML
	private MenuItem southBranch;

	/**
	 * ScrollPane in the UserEntranceWindow screen
	 */
	@FXML
	private ScrollPane openOrdersScroll;

	/**
	 * VBox in the UserEntranceWindow screen
	 */
	@FXML
	private Text openOrdersLabel;

	/** The open orders V box - to show all the relevant orders list. */
	@FXML
	private VBox openOrdersVBox;

	/**
	 * Go to users recent order window
	 * 
	 * @param event - an ActionEvent from the UserEntranceWindow
	 *
	 */
	@FXML
	void OpenRecentOrderWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.USER_ORDER_HISTORY, ClientUI.class,
				MyScreenEnum.USER_ORDER_HISTORY.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.USER_ORDER_HISTORY);
	}

	/**
	 * Go to create a new order for user
	 * 
	 * @param event - an ActionEvent from the UserEntranceWindow
	 *
	 */
	@FXML
	void openNewOrderWindow(ActionEvent event) {
		userViewController.getOrderController().getOrderList().clear();
		userViewController.getScreenManager().loadScreen(MyScreenEnum.CHOOSE_RESTAURANT, ClientUI.class,
				MyScreenEnum.CHOOSE_RESTAURANT.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.NEW_ORDER_IDENTIFICATION);
	}

	/**
	 * Log out from account
	 * 
	 * @param event - an ActionEvent from the UserEntranceWindow
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
	 * Sets the userViewController variable
	 * 
	 * @param UVC set the userViewController
	 */
	@Override
	public void setUserViewController(UserViewController UVC) {
		userViewController = UVC;
	}

	/**
	 * Sets fields and functionality in the UserAllOrdersWindow
	 * 
	 */
	@Override
	public void setParameters() {
		User user = userViewController.getUserController().getUser();
		personalWelcomeLabel.setText("Welcome " + user.getPrivateName() + "!");
		openOrdersScroll.setVisible(false);
		openOrdersLabel.setVisible(false);

		branchSplitMenuButton.setText(user.getTempBranch().toString());
		northBranch.setOnAction(e -> {
			user.setTempBranch(E_Branches.E_NORTH);
			branchSplitMenuButton.setText(user.getTempBranch().toString());

		});
		centerBranch.setOnAction(e -> {
			user.setTempBranch(E_Branches.E_CENTER);
			branchSplitMenuButton.setText(user.getTempBranch().toString());
		});
		southBranch.setOnAction(e -> {
			user.setTempBranch(E_Branches.E_SOUTH);
			branchSplitMenuButton.setText(user.getTempBranch().toString());
		});

		// Check if user has opens orders
		Message getOpenOrders = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_OPEN_ORDERS);

		getOpenOrders.getMsgArrayL().add(
				"SELECT * FROM biteme_db.order WHERE userID = '" + user.getUserID() + "' AND orderStatus = 'E_READY';");
		userViewController.getClientController().handleMessageFromClientUI(getOpenOrders);
		while (!userViewController.getClientController().getMessageController().getE_GET_OPEN_ORDERS_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_GET_OPEN_ORDERS...");
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_GET_OPEN_ORDERS_MsgProccessed(false);

		ArrayList<Order> openOrders = userViewController.getOrderController().getOpenOrders();
		if (openOrders.size() > 0) {
			openOrdersScroll.setVisible(true);
			openOrdersLabel.setVisible(true);

			for (Order order : openOrders) {

				HBox openOrderRow = new HBox();
				openOrderRow.setSpacing(5);
				openOrderRow.setMaxWidth(260);
				openOrderRow.setMaxHeight(40);
				openOrderRow.setStyle("-fx-background-color: white;");
				openOrderRow.setStyle("-fx-padding: 5;");
				openOrderRow.setStyle("-fx-border-width: 1;");
				openOrderRow.setStyle("-fx-border-color: black;");
				openOrderRow.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
				openOrderRow.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");

				openOrderRow.setOnMouseEntered(eventMouseEntered -> {
					((HBox) eventMouseEntered.getSource()).scaleXProperty().set(1.01);
					((HBox) eventMouseEntered.getSource()).scaleYProperty().set(1.01);
					((HBox) eventMouseEntered.getSource()).scaleZProperty().set(1.01);
				});
				openOrderRow.setOnMouseExited(eventMouseExit -> {
					((HBox) eventMouseExit.getSource()).scaleXProperty().set(1);
					((HBox) eventMouseExit.getSource()).scaleYProperty().set(1);
					((HBox) eventMouseExit.getSource()).scaleZProperty().set(1);
				});

				VBox reportInfo = new VBox();
				reportInfo.setPadding(new Insets(0, 0, 0, 10));

				Text restName = new Text(order.getRestName() + "");
				restName.setWrappingWidth(165);
				restName.setFont(Font.font("System", FontWeight.BOLD, 16));

				Text date = new Text("Date: " + order.getDesiredOrderDate().toString());

				reportInfo.getChildren().addAll(restName, date);

				Button recievedButton = new Button("Recieved");
				recievedButton.setFont(Font.font(12));
				recievedButton.setBackground(new Background(new BackgroundFill(Color.web("#F9975D"), null, null)));
				recievedButton.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");
				recievedButton.setOnAction(e -> {

					Message approveOrderRecieved = new Message(OwnerEnum.E_CLIENT, OpEnum.E_CLIENT_ORDER_RECIEVED);
					int min = LocalTime.now().getMinute();
					int hour = LocalTime.now().getHour();
					String time = "";
					if (min < 10 && hour < 10) {
						time = "0" + hour + ":0" + min;
					} else if (min >= 10 && hour < 10) {
						time = "0" + hour + ":" + min;
					} else if (min >= 10 && hour >= 10) {
						time = hour + ":" + min;
					} else {
						time = hour + ":0" + min;
					}
					approveOrderRecieved.getMsgArrayL()
							.add("UPDATE biteme_db.order SET orderStatus = 'E_DELIVERD', timeRecieved = '" + time
									+ "' WHERE orderNumber = '" + order.getOrderNumber() + "';");
					approveOrderRecieved.getMsgArrayL().add(order.getOrderNumber());
					approveOrderRecieved.getMsgArrayL()
							.add(userViewController.getUserController().getUser().getUserCreditList());
					approveOrderRecieved.getMsgArrayL().add(order.getRestID());
					approveOrderRecieved.getMsgArrayL()
							.add(userViewController.getUserController().getUser().getUserID());
					userViewController.getClientController().handleMessageFromClientUI(approveOrderRecieved);
					userViewController.getScreenManager().loadScreen(MyScreenEnum.USER_ENTRANCE_WINDOW, ClientUI.class,
							MyScreenEnum.USER_ENTRANCE_WINDOW.getFXMLName(), userViewController);
					userViewController.getScreenManager().setScreen(MyScreenEnum.USER_ENTRANCE_WINDOW);

				});

				openOrderRow.getChildren().addAll(reportInfo, recievedButton);
				openOrderRow.setAlignment(Pos.CENTER_LEFT);
				reportInfo.setAlignment(Pos.CENTER_LEFT);

				openOrdersVBox.getChildren().add(openOrderRow);
			}
		}
	}

}
