package restaurant;

import java.util.ArrayList;

import Order.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_OrderStatus;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the RestaurantOrderHistoryWindow.
 */
public class RestaurantOrderHistoryForm implements IScreenController {
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The Log out button. */
	@FXML
	private Button LogOutButton;

	/** The Orders V box - to show all the relevant orders list. */
	@FXML
	private VBox OrdersVBox;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The header label. */
	@FXML
	private Text headerLabel;

	/** The home page button. */
	@FXML
	private Button homePageButton;

	/** The status updated. */
	@FXML
	private Text statusUpdated;

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the RestaurantOrderHistoryWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		OrdersVBox.getChildren().removeAll(OrdersVBox.getChildren());
		userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ENTRANCE);
	}

	/**
	 * Logging out from account.
	 *
	 * @param event - an ActionEvent from the RestaurantOrderHistoryWindow
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
	 * Go to home page.
	 *
	 * @param event - an ActionEvent from the RestaurantOrderHistoryWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
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
	 * Sets fields and functionality in the RestaurantOrderHistoryWindow.
	 */
	@Override
	public void setParameters() {
		getActiveOrders();

		while (!userViewController.getClientController().getMessageController()
				.getE_GET_RESTAURANT_ORDERS_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_GET_RESTAURANT_ORDERS...");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_GET_RESTAURANT_ORDERS_MsgProccessed(false);

		buildOrderSummery();
	}

	/**
	 * Gets the active orders from the server (DB).
	 */
	private void getActiveOrders() {
		Message getRestOrders = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_RESTAURANT_ORDERS);
		getRestOrders.getMsgArrayL().add("SELECT * FROM biteme_db.order WHERE restID = '"
				+ userViewController.getUserController().getUser().getManageRestID() + "'");
		userViewController.getClientController().handleMessageFromClientUI(getRestOrders);

	}

	/**
	 * Builds the order summary in the screen.
	 */
	private void buildOrderSummery() {
		ArrayList<Order> OrderList = userViewController.getRestaurantController().getAllRestaurantOrders();

		for (Order order : OrderList) {

			HBox orderRow = new HBox();
			orderRow.setStyle("-fx-background-color white;");
			orderRow.setStyle("-fx-padding: 5;" + "-fx-spacing: 10;");
			orderRow.setStyle("-fx-border-width: 1;");
			orderRow.setStyle("-fx-border-color: black;");
			orderRow.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);"
					+ "-fx-background-color: white;");
			orderRow.setPrefSize(610, 70);
			orderRow.setMaxSize(610, 70);
			orderRow.setMinSize(610, 70);
			orderRow.setAlignment(Pos.CENTER_LEFT);
			orderRow.setSpacing(10);
			orderRow.setPadding(new Insets(5, 5, 5, 5));

			Text orderNumber = new Text(order.getOrderNumber() + "");
			orderNumber.setWrappingWidth(135);
			orderNumber.setFont(Font.font("System", FontWeight.BOLD, 17));

			VBox orderInfo = new VBox();
			orderInfo.setPadding(new Insets(0, 0, 0, 10));
			orderInfo.setPrefSize(200, 60);
			orderInfo.setMaxSize(200, 60);
			orderInfo.setMinSize(200, 60);

			Text orderTime = new Text(order.getDesiredOrderTime() + "");
			orderTime.setWrappingWidth(135);
			orderTime.setFont(Font.font("System", 11));

			Text orderDate = new Text(order.getDesiredOrderDate() + "");
			orderDate.setWrappingWidth(135);
			orderDate.setFont(Font.font("System", 11));

			Text orderStatus = new Text(order.getOrderStatus().getName());
			orderDate.setWrappingWidth(135);
			orderDate.setFont(Font.font("System", 11));

			orderInfo.getChildren().addAll(orderTime, orderDate, orderStatus);

			Text orderPrice = new Text(order.getTotalPrice() + "");
			orderPrice.setWrappingWidth(135);
			orderPrice.setFont(Font.font("System", 16));

			
			orderRow.getChildren().addAll(orderNumber, orderInfo, orderPrice);
			OrdersVBox.getChildren().add(orderRow);

		}

	}

}
