package user;

import java.io.IOException;

import business.BusinessController;
import client.ClientController;
import client.ClientUI;
import delivery.DeliveryController;
import dish.DishController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import order.OrderController;
import reports.ReportController;
import restaurant.RestaurantController;
import screenManager.MyScreenEnum;
import screenManager.ScreenManager;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the combined controller for many other controllers - so that we
 * can have all functionality in one place
 */
public class UserViewController {
	/**
	 * The combined controller for many other controllers
	 */
	UserController userController = new UserController();
	/**
	 * Restaurant controller entity
	 */
	RestaurantController restaurantController = new RestaurantController();
	/**
	 * Dish controller entity
	 */
	DishController dishController = new DishController();
	/**
	 * Order controller entity
	 */
	OrderController orderController = new OrderController();
	/**
	 * Delivery controller entity
	 */
	DeliveryController deliveryController = new DeliveryController();
	/**
	 * Client controller entity
	 */
	ClientController clientController = null;
	/**
	 * Business controller entity
	 */
	BusinessController businessController = new BusinessController();
	/**
	 * Screen manager (controller) entity
	 */
	ScreenManager screenManager = new ScreenManager();
	/**
	 * Report controller entity
	 */
	ReportController reportController = new ReportController();
	/**
	 * Represents the host (Server-Client)
	 */
	String host;
	/**
	 * Represents the port (Server-Client)
	 */
	int port;

	/**
	 * Initializing some of the system's screens, and sets the first one
	 * 
	 * @param primaryStage - the Stage of the screens
	 *
	 */
	public UserViewController(Stage primaryStage) {
		initScreens();
		screenManager.setScreen(MyScreenEnum.MAIN_WINDOW);
		Scene scene = new Scene(screenManager);
		primaryStage.setTitle("Welcome");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * Initializing some of the system's screens
	 * 
	 *
	 *
	 */
	public void initScreens() {
		screenManager.loadScreen(MyScreenEnum.MAIN_WINDOW, ClientUI.class, MyScreenEnum.MAIN_WINDOW.getFXMLName(),
				this);
		screenManager.loadScreen(MyScreenEnum.LOGIN_WINDOW, ClientUI.class, MyScreenEnum.LOGIN_WINDOW.getFXMLName(),
				this);
	}

	/**
	 * Reseting all the UserViewController controllers
	 * 
	 *
	 *
	 */
	public void resetUserViewControllerFields() {
		userController = new UserController();
		restaurantController = new RestaurantController();
		dishController = new DishController();
		orderController = new OrderController();
		deliveryController = new DeliveryController();
		reportController = new ReportController();
		if (clientController != null)
			clientController = null;
	}

	/**
	 * Reseting user order controllers
	 * 
	 *
	 *
	 */
	public void resetUserOrderDetails() {
		restaurantController = new RestaurantController();
		dishController = new DishController();
		orderController = new OrderController();
		deliveryController = new DeliveryController();
		reportController = new ReportController();
	}

	/**
	 * Setting up screens according to the user type
	 * 
	 *
	 *
	 */
	public void setUpUserScreens() {
		switch (userController.getUser().getUserType()) {
		case E_REG_USER:
			screenManager.loadScreen(MyScreenEnum.USER_ENTRANCE_WINDOW, ClientUI.class,
					MyScreenEnum.USER_ENTRANCE_WINDOW.getFXMLName(), this);
			screenManager.loadScreen(MyScreenEnum.NEW_ORDER_IDENTIFICATION, ClientUI.class,
					MyScreenEnum.NEW_ORDER_IDENTIFICATION.getFXMLName(), this);
			screenManager.loadScreen(MyScreenEnum.CHOOSE_DELIVERY_TYPE, ClientUI.class,
					MyScreenEnum.CHOOSE_DELIVERY_TYPE.getFXMLName(), this);
			screenManager.loadScreen(MyScreenEnum.DELIVERY_INFO, ClientUI.class,
					MyScreenEnum.DELIVERY_INFO.getFXMLName(), this);
			break;

		case E_BUSINESS_USER:
			screenManager.loadScreen(MyScreenEnum.USER_ENTRANCE_WINDOW, ClientUI.class,
					MyScreenEnum.USER_ENTRANCE_WINDOW.getFXMLName(), this);
			screenManager.loadScreen(MyScreenEnum.NEW_ORDER_IDENTIFICATION, ClientUI.class,
					MyScreenEnum.NEW_ORDER_IDENTIFICATION.getFXMLName(), this);
			screenManager.loadScreen(MyScreenEnum.CHOOSE_DELIVERY_TYPE, ClientUI.class,
					MyScreenEnum.CHOOSE_DELIVERY_TYPE.getFXMLName(), this);
			screenManager.loadScreen(MyScreenEnum.DELIVERY_INFO, ClientUI.class,
					MyScreenEnum.DELIVERY_INFO.getFXMLName(), this);
			break;
		case E_RESTAURANT_USER:
			screenManager.loadScreen(MyScreenEnum.RESTAURANT_ENTRANCE, ClientUI.class,
					MyScreenEnum.RESTAURANT_ENTRANCE.getFXMLName(), this);
			break;
		case E_BRANCH_MAN_USER:
			screenManager.loadScreen(MyScreenEnum.BRANCHMAN_ENTRANCE, ClientUI.class,
					MyScreenEnum.BRANCHMAN_ENTRANCE.getFXMLName(), this);
			break;
		case E_HR:
			screenManager.loadScreen(MyScreenEnum.HR_ENTRANCE, ClientUI.class, MyScreenEnum.HR_ENTRANCE.getFXMLName(),
					this);
			break;
		case E_CEO_USER:
			screenManager.loadScreen(MyScreenEnum.CEO_ENTRANCE, ClientUI.class, MyScreenEnum.CEO_ENTRANCE.getFXMLName(),
					this);
			break;
		}
	}

	/**
	 * Sets the client controller.
	 *
	 * @param host the host
	 * @param port the port
	 * @return true, if successful
	 */
	public boolean setClientController(String host, int port) {
		try {
			clientController = new ClientController(host, port, this);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the client controller
	 * 
	 *
	 * @return a ClientController entity
	 */
	public ClientController getClientController() {
		return clientController;
	}

	/**
	 * Gets the restaurant controller
	 * 
	 *
	 * @return a RestaurantController entity
	 */
	public RestaurantController getRestaurantController() {
		return restaurantController;
	}

	/**
	 * Gets the Screen manager (controller)
	 * 
	 *
	 * @return a ScreenManager entity
	 */
	public ScreenManager getScreenManager() {
		return screenManager;
	}

	/**
	 * Logging out the current user
	 *
	 *
	 */
	public void logoutUser() {
		
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userController.getUser().getUserID() + ";");
		clientController.handleMessageFromClientUI(message);
		resetUserViewControllerFields();
		
		screenManager = new ScreenManager();
		initScreens();
		Platform.runLater(() -> {
			screenManager.setScreen(MyScreenEnum.LOGIN_WINDOW);
		});
	}

	/**
	 * Gets the dish controller
	 * 
	 *
	 * @return a DishController entity
	 */
	public DishController getDishController() {
		return dishController;
	}

	/**
	 * Gets the order controller
	 * 
	 *
	 * @return an OrderController entity
	 */
	public OrderController getOrderController() {
		return orderController;
	}

	/**
	 * Sets the order controller
	 * 
	 * @param orderController - an OrderController entity
	 *
	 */
	public void setOrderController(OrderController orderController) {
		this.orderController = orderController;
	}

	/**
	 * Gets the connection host
	 * 
	 *
	 * @return a String presents a host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the connection host
	 * 
	 * @param host - a String presents a host
	 *
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the connection port
	 * 
	 *
	 * @return an int presents port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the connection port
	 * 
	 * @param port - an int presents the port
	 *
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Gets the user controller
	 * 
	 *
	 * @return a UserController entity
	 */
	public UserController getUserController() {
		return userController;
	}

	/**
	 * Sets the user controller
	 * 
	 * @param userController - a UserController entity
	 *
	 */
	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	/**
	 * Gets the business controller
	 * 
	 *
	 * @return a BusinessController entity
	 */
	public BusinessController getBusinessController() {
		return businessController;
	}

	/**
	 * Sets the business controller
	 * 
	 * @param businessController - a BusinessController entity
	 *
	 */
	public void setBusinessController(BusinessController businessController) {
		this.businessController = businessController;
	}

	/**
	 * Gets the delivery controller
	 * 
	 *
	 * @return a DeliveryController entity
	 */
	public DeliveryController getDeliveryController() {
		return deliveryController;
	}

	/**
	 * Sets the delivery controller
	 * 
	 * @param deliveryController - a DeliveryController entity
	 *
	 */
	public void setDeliveryController(DeliveryController deliveryController) {
		this.deliveryController = deliveryController;
	}

	/**
	 * Gets the report controller
	 * 
	 *
	 * @return a ReportController entity
	 */
	public ReportController getReportController() {
		return reportController;
	}

	/**
	 * Gets the report controller
	 * 
	 * @param reportController - a ReportController entity
	 *
	 */
	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}

	public void goToHomeScreen() {
		switch (userController.getUser().getUserType()) {
		case E_REG_USER:
			screenManager.loadScreen(MyScreenEnum.USER_ENTRANCE_WINDOW, ClientUI.class,
					MyScreenEnum.USER_ENTRANCE_WINDOW.getFXMLName(), this);
			screenManager.setScreen(MyScreenEnum.USER_ENTRANCE_WINDOW);
			break;
		case E_BUSINESS_USER:
			screenManager.loadScreen(MyScreenEnum.USER_ENTRANCE_WINDOW, ClientUI.class,
					MyScreenEnum.USER_ENTRANCE_WINDOW.getFXMLName(), this);
			screenManager.setScreen(MyScreenEnum.USER_ENTRANCE_WINDOW);
			break;

		case E_BRANCH_MAN_USER:
			screenManager.loadScreen(MyScreenEnum.BRANCHMAN_ENTRANCE, ClientUI.class,
					MyScreenEnum.BRANCHMAN_ENTRANCE.getFXMLName(), this);
			screenManager.setScreen(MyScreenEnum.BRANCHMAN_ENTRANCE);
			break;

		case E_CEO_USER:
			screenManager.loadScreen(MyScreenEnum.CEO_ENTRANCE, ClientUI.class, MyScreenEnum.CEO_ENTRANCE.getFXMLName(),
					this);
			screenManager.setScreen(MyScreenEnum.CEO_ENTRANCE);
			break;
		case E_HR:
			screenManager.loadScreen(MyScreenEnum.HR_ENTRANCE, ClientUI.class, MyScreenEnum.HR_ENTRANCE.getFXMLName(),
					this);
			screenManager.setScreen(MyScreenEnum.HR_ENTRANCE);
			break;
		case E_RESTAURANT_USER:
			screenManager.loadScreen(MyScreenEnum.RESTAURANT_ENTRANCE, ClientUI.class,
					MyScreenEnum.RESTAURANT_ENTRANCE.getFXMLName(), this);
			screenManager.setScreen(MyScreenEnum.RESTAURANT_ENTRANCE);
			break;
		}

	}

}
