package restaurant;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;

import Order.Order;
import client.ClientUI;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_OrderStatus;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the RestaurantActiveOrdersWindow.
 */
public class RestaurantActiveOrdersForm implements IScreenController {

	/**The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The Orders V box - to show all the relevant orders list. */
	@FXML
	private VBox OrdersVBox;

	/** The update status button. */
	@FXML
	private Button updateStatusButton;

	/** The header label. */
	@FXML
	private Text headerLabel;

	/** The header label 1. */
	@FXML
	private Text headerLabel1;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The Log out button. */
	@FXML
	private Button LogOutButton;

	/** The home page button. */
	@FXML
	private Button homePageButton;
    
    /** The status updated. */
    @FXML
    private Text statusUpdated;


	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the RestaurantActiveOrdersWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ENTRANCE);
	}

	/**
	 * Update order status.
	 *
	 * @param event - an ActionEvent from the RestaurantActiveOrdersWindow
	 */
	@FXML
	void updateStatus(ActionEvent event) {
		StringBuilder orderNumbers = new StringBuilder();
		String time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
		orderNumbers.append("UPDATE biteme_db.order SET orderStatus = 'E_PREPARING', timeApproved = '" + time + "' WHERE orderNumber IN (");
		for(Order order : userViewController.getRestaurantController().getAllRestaurantOrders()) {
			if(order.getOrderStatus().equals(E_OrderStatus.E_READY)) {
				orderNumbers.append("'" + order.getOrderNumber() + "', ");
			}
		}
		orderNumbers.deleteCharAt(orderNumbers.length() -1);
		orderNumbers.deleteCharAt(orderNumbers.length() -1);
		orderNumbers.append(");");
		
		Message updateOrderStatus = new Message(OwnerEnum.E_CLIENT, OpEnum.E_RESTAURANT_APPROVE_ORDER);
		updateOrderStatus.getMsgArrayL().add(orderNumbers.toString());
		userViewController.getClientController().handleMessageFromClientUI(updateOrderStatus);
		statusUpdated.setText("Updated the order Statuses succesfully!");
		PauseTransition pt = new PauseTransition(Duration.seconds(5));
		pt.setOnFinished(e -> {
			statusUpdated.setText("");
		});
		userViewController.getScreenManager().loadScreen(MyScreenEnum.RESTAURANT_ACTIVEORDERS, ClientUI.class, MyScreenEnum.RESTAURANT_ACTIVEORDERS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ACTIVEORDERS);
	}

	/**
	 * Logging out of account.
	 *
	 * @param event - an ActionEvent from the RestaurantActiveOrdersWindow
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
	 * @param event - an ActionEvent from the RestaurantActiveOrdersWindow
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
	 * Sets fields and functionality in the RestaurantActiveOrdersWindow.
	 */
	@Override
	public void setParameters() {
		getActiveOrders();
		
		while (!userViewController.getClientController().getMessageController().getE_GET_RESTAURANT_ORDERS_MsgProccessed()) {
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
		getRestOrders.getMsgArrayL().add("SELECT * FROM biteme_db.order WHERE restID = '" + userViewController.getUserController().getUser().getManageRestID() + "'");
		userViewController.getClientController().handleMessageFromClientUI(getRestOrders);
		
	}
	
	/**
	 * Builds the order summary.
	 */
	private void buildOrderSummery() {
		ArrayList<Order> OrderList = userViewController.getRestaurantController().getAllRestaurantOrders();
		DecimalFormat df = new DecimalFormat();
		
		
		for (Order order : OrderList) {
			if(order.getOrderStatus().equals(E_OrderStatus.E_DELIVERD)) {
				continue;
			}
			
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
			orderRow.setPadding(new Insets(5,5,5,5));

			Text orderNumber = new Text(order.getOrderNumber() + "");
			orderNumber.setWrappingWidth(100);
			orderNumber.setFont(Font.font("System", FontWeight.BOLD,20));
			

			VBox orderInfo = new VBox();
			orderInfo.setPadding(new Insets(0, 0, 0, 5));
			orderInfo.setPrefSize(170, 60);
			orderInfo.setMaxSize(170, 60);
			orderInfo.setMinSize(170, 60);

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

			Text orderPrice = new Text("Total: " + df.format(order.getTotalPrice()));
			orderPrice.setWrappingWidth(120);
			orderPrice.setFont(Font.font("System", 16));

			VBox buttonHolder = new VBox();
			buttonHolder.setAlignment(Pos.CENTER);
			
			Button changeToPreparring = new Button("Approve Order");
			Button changeToReady = new Button("Set Ready");
			changeToReady.setVisible(false);
			if(order.getOrderStatus().equals(E_OrderStatus.E_READY)) {
				changeToPreparring.setVisible(false);
			}
			changeToPreparring.setPrefSize(135, 25);
			changeToPreparring.setMaxSize(135, 25);
			changeToPreparring.setMinSize(135, 25);
			changeToPreparring.setOnAction(e -> {
				order.setOrderStatus(E_OrderStatus.E_PREPARING);
				orderStatus.setText(order.getOrderStatus().getName());	
				changeToReady.setVisible(true);
				changeToPreparring.setVisible(false);
				//update db
				
				

				StringBuilder orderNumbers = new StringBuilder();
				int min = LocalTime.now().getMinute();
				int hour = LocalTime.now().getHour();
				String time = "";
				if (min < 10 && hour < 10) {
					time = "0" + hour + ":0" + min;
				} else if(min >= 10 && hour < 10){
					time = "0" + hour + ":" + min;
				}else if(min >= 10 && hour >= 10) {
					time = hour + ":" + min;
				}else {
					time = hour + ":0" + min;
				}
				orderNumbers.append("UPDATE biteme_db.order SET orderStatus = 'E_PREPARING', timeApproved = '" + time + "' WHERE orderNumber = '" + order.getOrderNumber() + "';");
				Message updateOrderStatus = new Message(OwnerEnum.E_CLIENT, OpEnum.E_RESTAURANT_APPROVE_ORDER);
				updateOrderStatus.getMsgArrayL().add(orderNumbers.toString());
				updateOrderStatus.getMsgArrayL().add(order.getOrderNumber());
				userViewController.getClientController().handleMessageFromClientUI(updateOrderStatus);
				statusUpdated.setText("Updated the order Status succesfully!");
				PauseTransition pt = new PauseTransition(Duration.seconds(5));
				pt.setOnFinished(eventPT -> {
					statusUpdated.setText("");
				});	
	
			});
			
			changeToReady.setPrefSize(135, 25);
			changeToReady.setMaxSize(135, 25);
			changeToReady.setMinSize(135, 25);
			changeToReady.setOnAction(e -> {
				order.setOrderStatus(E_OrderStatus.E_READY);
				orderStatus.setText("In route to Customer");	
				changeToReady.setVisible(false);
				
				//update db
				StringBuilder orderNumbers = new StringBuilder();
				orderNumbers.append("UPDATE biteme_db.order SET orderStatus = 'E_READY' WHERE orderNumber = '" + order.getOrderNumber() + "';");
				Message updateOrderStatus = new Message(OwnerEnum.E_CLIENT, OpEnum.E_RESTAURANT_ORDER_READY);
				updateOrderStatus.getMsgArrayL().add(orderNumbers.toString());
				updateOrderStatus.getMsgArrayL().add(order.getOrderNumber());
				userViewController.getClientController().handleMessageFromClientUI(updateOrderStatus);
				statusUpdated.setText("Updated the order Status succesfully!");
				PauseTransition pt = new PauseTransition(Duration.seconds(5));
				pt.setOnFinished(eventPT -> {
					statusUpdated.setText("");
				});	
				
	
			});
			
			buttonHolder.getChildren().addAll(changeToPreparring, changeToReady);
			orderRow.getChildren().addAll(orderNumber, orderInfo, orderPrice, buttonHolder);
			OrdersVBox.getChildren().add(orderRow);
			
		}

	}
}
