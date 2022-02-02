package utils;

import java.io.IOException;
import java.io.Serializable;

import client.ClientController;
import client.ClientUI;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import newOrder.MessagePopUpForm;
import screenManager.MyScreenEnum;
import user.UserViewController;

/**
 * The Class MessageController to handle messages between the client and server.
 */
public class MessageController implements Serializable {

	/** The user view controller. */
	UserViewController userViewController;
	
	/** The message. */
	Message message;
	
	/** The client controller. */
	ClientController clientController;

	/** The logged in. */
	boolean loggedIn = false;
	
	/** The log in message. */
	String logInMessage = new String();

	/** The log in msg proccessed. */
	boolean logInMsgProccessed = false;


	/** The E_REST_INFO msg proccessed. */
	boolean E_REST_INFO_MsgProccessed = false;
	

	/** The E_GET_MENU msg proccessed. */
	boolean E_GET_MENU_MsgProccessed = false;
	

	/** The E_ADD_ORDER msg proccessed. */
	boolean E_ADD_ORDER_MsgProccessed = false;
	

	/** The E_RESTAURANT_APPROVE_ORDER msg proccessed. */
	boolean E_RESTAURANT_APPROVE_ORDER_MsgProccessed = false;
	
	/** The E_GET_BUSINESS_COLLEGES msg proccessed. */
	boolean E_GET_BUSINESS_COLLEGES_MsgProccessed = false;
	

	/** The E_GET_RESTAURANT_ORDERS_MsgProccessed msg proccessed. */
	boolean E_GET_RESTAURANT_ORDERS_MsgProccessed = false;
	

	/** The E_UPDATE_DISH msg proccessed. */
	boolean E_UPDATE_DISH_MsgProccessed = false;

	/** The E UPDAT E DIS H updated successfully. */
	boolean E_UPDATE_DISH_UpdatedSuccessfully = false;

	/** The E_GET_SPECIFIC_RESTAURANT_MsgProccessed msg proccessed. */
	boolean E_GET_SPECIFIC_RESTAURANT_MsgProccessed = false;
	

	/** The E_NOTIFY_CLIENT_ORDER_APPROVED msg proccessed. */
	boolean E_NOTIFY_CLIENT_ORDER_APPROVED_MsgProccessed = false;
	

	/** The E_NOTIFY_CLIENT_ORDER_IN_ROUTE msg proccessed. */
	boolean E_NOTIFY_CLIENT_ORDER_IN_ROUTE_MsgProccessed = false;
	

	/** The E_RESTAURANT_ORDER_RECIEVED msg proccessed. */
	boolean E_RESTAURANT_ORDER_RECIEVED_MsgProccessed = false;
	

	/** The E_USER_LIST msg proccessed. */
	boolean E_USER_LIST_MsgProccessed = false;
	

	/** The E_GET_BUSINESS msg proccessed. */
	boolean E_GET_BUSINESS_MsgProccessed = false;
	

	/** The E_BRING_REPORTS msg proccessed. */
	boolean E_BRING_REPORTS_MsgProccessed = false;
	

	/** The E_GET_OPEN_ORDERS msg proccessed. */
	boolean E_GET_OPEN_ORDERS_MsgProccessed = false;
	

	/** The E_GET_ALL_USER_ORDERS msg proccessed. */
	boolean E_GET_ALL_USER_ORDERS_MsgProccessed = false;
	

	/** The E_GET_REST_COMMISSIONS msg proccessed. */
	boolean E_GET_REST_COMMISSIONS_MsgProccessed = false;
	

	/** The E_GET_QUARTERLY_REPORT msg proccessed. */
	boolean E_GET_QUARTERLY_REPORT_MsgProccessed = false;
	

	/** The E_GET_W4C_FOR_HR msg proccessed. */
	boolean E_GET_W4C_FOR_HR_MsgProccessed = false;
	

	/** The E_GET_REST_NAME msg proccessed. */
	boolean E_GET_REST_NAME_MsgProccessed = false;

	/** The E_GET_USERS_IN_ORDER_GRUOP msg proccessed. */
	boolean E_GET_USERS_IN_ORDER_GRUOP_MsgProccessed = false;
	

	/** The E_GET_BRANCHMAN_TO_CEO_REPORTS msg proccessed. */
	boolean E_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed = false;
	
	/**
	 * Gets the user view controller.
	 *
	 * @return the user view controller
	 */
	public UserViewController getUserViewController() {
		return userViewController;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * Gets the client controller.
	 *
	 * @return the client controller
	 */
	public ClientController getClientController() {
		return clientController;
	}

	/**
	 * Checks if is logged in.
	 *
	 * @return true, if is logged in
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Instantiates a new message controller.
	 *
	 * @param userViewController the user view controller
	 */
	public MessageController(UserViewController userViewController) {
		this.userViewController = userViewController;
	}

	/**
	 * Process the message from the server.
	 *
	 * @param messageFromServer the message from server
	 */
	public void processMessage(Message messageFromServer) {
		message = messageFromServer;
		clientController = userViewController.getClientController();
		switch (message.getOpCode()) {
		case E_SELECT:
			break;
		case E_GET_BUSINESS:
			userViewController.getBusinessController().buildBusinessList(messageFromServer);
			E_GET_BUSINESS_MsgProccessed = true;
			break;
		case E_GETALLUSERS:
			userViewController.getUserController().buildUsersList(messageFromServer);
			E_USER_LIST_MsgProccessed = true;
			break;
		case E_REST_INFO:
			userViewController.getRestaurantController().addRestaurants(message);
			E_REST_INFO_MsgProccessed = true;
			break;
		case E_GET_MENU:
			userViewController.getDishController().addDishes(message);
			E_GET_MENU_MsgProccessed = true;
			break;
		case E_GET_USERS_IN_ORDER_GRUOP:
			userViewController.getOrderController().setNumberOfGroupOrder((int) message.getMsgArrayL().get(0));
			E_GET_USERS_IN_ORDER_GRUOP_MsgProccessed = true;
			break;
		case E_UPDATE:
			break;
		case E_LOGIN:
			System.out.println(message.getMsgArrayL().get(0));
			setLogInMessage("NA");
			if (message.getMsgArrayL().get(0).equals("Incorrect Login details")) {
				setLogInMessage("Incorrect Login details");
				setLoggedIn(false);
			} else {
				userViewController.getUserController().buildUser(message);
				E_AccountStatus accountStatus = userViewController.getUserController().getUser().getAccountStatus();
				int isLoggedIn = userViewController.getUserController().getUser().getIsLoggedIn();
				E_UserTypeEnum userType = userViewController.getUserController().getUser().getUserType();

				if (isLoggedIn == 0) {
					if (accountStatus == E_AccountStatus.E_ACTIVE
							&& userType != E_UserTypeEnum.E_UNAPPROVED_BUSINESS_USER) {

						Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE_LOGIN);
						message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 1 WHERE userID = "
								+ userViewController.getUserController().getUser().getUserID() + ";");
						if (userViewController.getUserController().getUser().getUserType()
								.equals(E_UserTypeEnum.E_RESTAURANT_USER)) {
							message.getMsgArrayL()
									.add(userViewController.getUserController().getUser().getManageRestID());
						}
						clientController.handleMessageFromClientUI(message);
						setLoggedIn(true);
						setLogInMsgProccessed(true);
					} else if (accountStatus == E_AccountStatus.E_WAITING_APPROVAL) {
						setLogInParameters(false, "User has not been approved by the Branch Manager");
					} else if (accountStatus == E_AccountStatus.E_FROZEN) {
						setLogInParameters(false, "User account has been Frozen.");
					} else if (userType == E_UserTypeEnum.E_UNAPPROVED_BUSINESS_USER) {
						setLogInParameters(false, "Buisness User account has not been approved by his HR.");
					}
				} else {
					setLogInParameters(false, "User is already connected");

				}
			}
			setLogInMsgProccessed(true);
			break;
		case E_ADD_ORDER:
			if (((int) messageFromServer.getMsgArrayL().get(0)) == 1) {
				userViewController.getOrderController().getOrder()
						.setOrderNumber(((String) messageFromServer.getMsgArrayL().get(1)));
				setE_ADD_ORDER_MsgProccessed(true);
			} else {
				System.out.println("The order was not added to the DB correctly");
			}
			break;
		case E_NOTIFY_REST_OF_NEW_ORDER:
			if ((int) messageFromServer.getMsgArrayL().get(0) == 1) {
				if (userViewController.getScreenManager().getCurrentScreen()
						.equals(MyScreenEnum.RESTAURANT_ACTIVEORDERS)) {
					Platform.runLater(() -> {
						userViewController.getScreenManager().loadScreen(MyScreenEnum.RESTAURANT_ACTIVEORDERS,
								ClientUI.class, MyScreenEnum.RESTAURANT_ACTIVEORDERS.getFXMLName(), userViewController);
						userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ACTIVEORDERS);
					});
				}

			}
			break;
		case E_RESTAURANT_APPROVE_ORDER:
			E_RESTAURANT_APPROVE_ORDER_MsgProccessed = true;
			break;
		case E_NOTIFY_CLIENT_ORDER_APPROVED:
			if ((int) messageFromServer.getMsgArrayL().get(0) == 1) {

				Platform.runLater(() -> {
					openUserPopUp("Your order has been approved and is being prepared!");
//					userViewController.getScreenManager().loadScreen(MyScreenEnum.ORDER_CONFIRMATION, ClientUI.class,
//							MyScreenEnum.ORDER_CONFIRMATION.getFXMLName(), userViewController);
//					userViewController.getScreenManager().setScreen(MyScreenEnum.ORDER_CONFIRMATION);
				});

			}

			E_NOTIFY_CLIENT_ORDER_APPROVED_MsgProccessed = true;
			break;
		case E_NOTIFY_CLIENT_ORDER_IN_ROUTE:
			if ((int) messageFromServer.getMsgArrayL().get(0) == 1) {
				Platform.runLater(() -> {
					openUserPopUp("Your order is ready. Please hit the 'Recieved' button on your home screen when you have recieved the order");
					if(userViewController.getScreenManager().getCurrentScreen().equals(MyScreenEnum.USER_ENTRANCE_WINDOW)) {
						userViewController.getScreenManager().loadScreen(MyScreenEnum.USER_ENTRANCE_WINDOW, ClientUI.class,
								MyScreenEnum.USER_ENTRANCE_WINDOW.getFXMLName(), userViewController);
						userViewController.getScreenManager().setScreen(MyScreenEnum.USER_ENTRANCE_WINDOW);
					}	
				});
			}
			E_NOTIFY_CLIENT_ORDER_IN_ROUTE_MsgProccessed = true;
			break;
		case E_RESTAURANT_ORDER_RECIEVED:

			Platform.runLater(() -> {
				userViewController.getScreenManager().loadScreen(MyScreenEnum.RESTAURANT_ACTIVEORDERS, ClientUI.class,
						MyScreenEnum.RESTAURANT_ACTIVEORDERS.getFXMLName(), userViewController);
				if (userViewController.getScreenManager().getCurrentScreen()
						.equals(MyScreenEnum.RESTAURANT_ACTIVEORDERS)) {
					userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ACTIVEORDERS);
				}
			});
			E_RESTAURANT_ORDER_RECIEVED_MsgProccessed = true;
			break;
		case E_GET_BUSINESS_COLLEAGUES:
//			userViewController.getUserController().buildBusinessColegesList(messageFromServer);
			E_GET_BUSINESS_COLLEGES_MsgProccessed = true;
			break;
		case E_GET_RESTAURANT_ORDERS:
			userViewController.getRestaurantController().buildAllRestaurantOrders(messageFromServer);
			E_GET_RESTAURANT_ORDERS_MsgProccessed = true;
			break;
		case E_UPDATE_DISH:
			if ((int) messageFromServer.getMsgArrayL().get(0) == 1) {
				E_UPDATE_DISH_UpdatedSuccessfully = true;
			}
			E_UPDATE_DISH_MsgProccessed = true;
			break;
		case E_GET_SPECIFIC_RESTAURANT:
			userViewController.getRestaurantController().addRestaurants(messageFromServer);
			userViewController.getRestaurantController()
					.setChosenRestaurant(userViewController.getRestaurantController().getRestaurantList().get(0));
			E_GET_SPECIFIC_RESTAURANT_MsgProccessed = true;
			break;
		case E_BRING_REPORTS:
			userViewController.getReportController().buildReportsList(messageFromServer);
			E_BRING_REPORTS_MsgProccessed = true;
			break;
		case E_GET_OPEN_ORDERS:
			userViewController.getOrderController().buildOpenOrdersList(messageFromServer);
			E_GET_OPEN_ORDERS_MsgProccessed = true;
			break;
		case E_GET_ALL_USER_ORDERS:
			userViewController.getOrderController().buildAllUserOrdersList(messageFromServer);
			E_GET_ALL_USER_ORDERS_MsgProccessed = true;
			break;
		case E_GET_REST_COMMISSIONS:
			userViewController.getReportController().buildReportsList(messageFromServer);
			E_GET_REST_COMMISSIONS_MsgProccessed = true;
			break;
		case E_SET_W4C_FOR_HR:
			userViewController.getUserController().getUser()
					.setBusinessW4CNumber((int) messageFromServer.getMsgArrayL().get(0));
			E_GET_W4C_FOR_HR_MsgProccessed = true;
			break;
		case E_GET_QUARTERLY_REPORT:
			userViewController.getReportController().buildReportPDF(messageFromServer);
			E_GET_QUARTERLY_REPORT_MsgProccessed = true;
			break;
		case E_GET_BRANCHMAN_TO_CEO_REPORTS:
			userViewController.getReportController().buildBranchmanToCeoReportList(messageFromServer);
			E_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed = true;
			break;
		default:
			break;
		}

	}

	/**
	 * Open user pop up window for the message simulation.
	 *
	 * @param message the message to display on the pop up window
	 */
	private void openUserPopUp(String message) {
		Stage newStage = new Stage();
		
		Parent screen;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/newOrder/MessagePopUp.fxml"));
			screen = (Parent) loader.load();
			MessagePopUpForm mpuf = loader.getController();
			mpuf.setParameters(message, userViewController.getUserController().getUser().getEmail());
			newStage.setTitle("SMS/Email Simulation");
			Scene stageScene = new Scene(screen);
			newStage.setScene(stageScene);
			newStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sets the log in parameters.
	 *
	 * @param logInState the log in state
	 * @param logInMessage the log in message
	 */
	public void setLogInParameters(boolean logInState, String logInMessage) {
		setLoggedIn(logInState);
		setLogInMessage(logInMessage);
	}

	/**
	 * Gets the checks if is logged in.
	 *
	 * @return the checks if is logged in
	 */
	public boolean getIsLoggedIn() {
		return loggedIn;
	}

	/**
	 * Sets the logged in.
	 *
	 * @param loggedIn the new logged in
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * Gets the log in message.
	 *
	 * @return the log in message
	 */
	public String getLogInMessage() {
		return logInMessage;
	}

	/**
	 * Sets the log in message.
	 *
	 * @param logInMessage the new log in message
	 */
	public void setLogInMessage(String logInMessage) {
		this.logInMessage = logInMessage;
	}

	/**
	 * Gets the log in msg proccessed.
	 *
	 * @return the log in msg proccessed
	 */
	public boolean getLogInMsgProccessed() {
		return logInMsgProccessed;
	}

	/**
	 * Sets the log in msg proccessed.
	 *
	 * @param logInMsgProccessed the new log in msg proccessed
	 */
	public void setLogInMsgProccessed(boolean logInMsgProccessed) {
		this.logInMsgProccessed = logInMsgProccessed;
	}

	/**
	 * Gets the e RES T INF O msg proccessed.
	 *
	 * @return the e RES T INF O msg proccessed
	 */
	public boolean getE_REST_INFO_MsgProccessed() {
		return E_REST_INFO_MsgProccessed;
	}

	/**
	 * Sets the e RES T INF O msg proccessed.
	 *
	 * @param e_REST_INFO_MsgProccessed the new e RES T INF O msg proccessed
	 */
	public void setE_REST_INFO_MsgProccessed(boolean e_REST_INFO_MsgProccessed) {
		E_REST_INFO_MsgProccessed = e_REST_INFO_MsgProccessed;
	}

	/**
	 * Gets the e GE T MEN U msg proccessed.
	 *
	 * @return the e GE T MEN U msg proccessed
	 */
	public boolean getE_GET_MENU_MsgProccessed() {
		return E_GET_MENU_MsgProccessed;
	}

	/**
	 * Sets the e GE T MEN U msg proccessed.
	 *
	 * @param e_GET_MENU_MsgProccessed the new e GE T MEN U msg proccessed
	 */
	public void setE_GET_MENU_MsgProccessed(boolean e_GET_MENU_MsgProccessed) {
		E_GET_MENU_MsgProccessed = e_GET_MENU_MsgProccessed;
	}

	/**
	 * Gets the e USE R LIS T msg proccessed.
	 *
	 * @return the e USE R LIS T msg proccessed
	 */
	public boolean getE_USER_LIST_MsgProccessed() {// asaf
		return E_USER_LIST_MsgProccessed;
	}

	/**
	 * Sets the e USE R LIS T msg proccessed.
	 *
	 * @param e_USER_LIST_MsgProccessed the new e USE R LIS T msg proccessed
	 */
	public void setE_USER_LIST_MsgProccessed(boolean e_USER_LIST_MsgProccessed) {
		E_USER_LIST_MsgProccessed = e_USER_LIST_MsgProccessed;
	}

	/**
	 * Gets the e GE T BUSINES S msg proccessed.
	 *
	 * @return the e GE T BUSINES S msg proccessed
	 */
	public boolean getE_GET_BUSINESS_MsgProccessed() {
		return E_GET_BUSINESS_MsgProccessed;
	}

	/**
	 * Sets the e GE T BUSINES S msg proccessed.
	 *
	 * @param e_GET_BUSINESS_MsgProccessed the new e GE T BUSINES S msg proccessed
	 */
	public void setE_GET_BUSINESS_MsgProccessed(boolean e_GET_BUSINESS_MsgProccessed) {
		E_GET_BUSINESS_MsgProccessed = e_GET_BUSINESS_MsgProccessed;
	}

	/**
	 * Gets the e AD D ORDE R msg proccessed.
	 *
	 * @return the e AD D ORDE R msg proccessed
	 */
	public boolean getE_ADD_ORDER_MsgProccessed() {
		return E_ADD_ORDER_MsgProccessed;
	}

	/**
	 * Sets the e AD D ORDE R msg proccessed.
	 *
	 * @param e_ADD_ORDER_MsgProccessed the new e AD D ORDE R msg proccessed
	 */
	public void setE_ADD_ORDER_MsgProccessed(boolean e_ADD_ORDER_MsgProccessed) {
		E_ADD_ORDER_MsgProccessed = e_ADD_ORDER_MsgProccessed;
	}

	/**
	 * Gets the e RESTAURAN T APPROV E ORDE R msg proccessed.
	 *
	 * @return the e RESTAURAN T APPROV E ORDE R msg proccessed
	 */
	public boolean getE_RESTAURANT_APPROVE_ORDER_MsgProccessed() {
		return E_RESTAURANT_APPROVE_ORDER_MsgProccessed;
	}

	/**
	 * Sets the e RESTAURAN T APPROV E ORDE R msg proccessed.
	 *
	 * @param e_RESTAURANT_APPROVE_ORDER_MsgProccessed the new e RESTAURAN T APPROV E ORDE R msg proccessed
	 */
	public void setE_RESTAURANT_APPROVE_ORDER_MsgProccessed(boolean e_RESTAURANT_APPROVE_ORDER_MsgProccessed) {
		E_RESTAURANT_APPROVE_ORDER_MsgProccessed = e_RESTAURANT_APPROVE_ORDER_MsgProccessed;
	}

	/**
	 * Gets the e GE T BUSINES S COLLEGE S msg proccessed.
	 *
	 * @return the e GE T BUSINES S COLLEGE S msg proccessed
	 */
	public boolean getE_GET_BUSINESS_COLLEGES_MsgProccessed() {
		return E_GET_BUSINESS_COLLEGES_MsgProccessed;
	}

	/**
	 * Sets the e GE T BUSINES S COLLEGE S msg proccessed.
	 *
	 * @param e_GET_BUSINESS_COLLEGES_MsgProccessed the new e GE T BUSINES S COLLEGE S msg proccessed
	 */
	public void setE_GET_BUSINESS_COLLEGES_MsgProccessed(boolean e_GET_BUSINESS_COLLEGES_MsgProccessed) {
		E_GET_BUSINESS_COLLEGES_MsgProccessed = e_GET_BUSINESS_COLLEGES_MsgProccessed;
	}

	/**
	 * Gets the e GE T RESTAURAN T ORDER S msg proccessed.
	 *
	 * @return the e GE T RESTAURAN T ORDER S msg proccessed
	 */
	public boolean getE_GET_RESTAURANT_ORDERS_MsgProccessed() {
		return E_GET_RESTAURANT_ORDERS_MsgProccessed;
	}

	/**
	 * Sets the e GE T RESTAURAN T ORDER S msg proccessed.
	 *
	 * @param e_GET_RESTAURANT_ORDERS_MsgProccessed the new e GE T RESTAURAN T ORDER S msg proccessed
	 */
	public void setE_GET_RESTAURANT_ORDERS_MsgProccessed(boolean e_GET_RESTAURANT_ORDERS_MsgProccessed) {
		E_GET_RESTAURANT_ORDERS_MsgProccessed = e_GET_RESTAURANT_ORDERS_MsgProccessed;
	}

	/**
	 * Gets the e UPDAT E DIS H msg proccessed.
	 *
	 * @return the e UPDAT E DIS H msg proccessed
	 */
	public boolean getE_UPDATE_DISH_MsgProccessed() {
		return E_UPDATE_DISH_MsgProccessed;
	}

	/**
	 * Sets the e UPDAT E DIS H msg proccessed.
	 *
	 * @param e_UPDATE_DISH_MsgProccessed the new e UPDAT E DIS H msg proccessed
	 */
	public void setE_UPDATE_DISH_MsgProccessed(boolean e_UPDATE_DISH_MsgProccessed) {
		E_UPDATE_DISH_MsgProccessed = e_UPDATE_DISH_MsgProccessed;
	}

	/**
	 * Gets the e UPDAT E DIS H updated successfully.
	 *
	 * @return the e UPDAT E DIS H updated successfully
	 */
	public boolean getE_UPDATE_DISH_UpdatedSuccessfully() {
		return E_UPDATE_DISH_UpdatedSuccessfully;
	}

	/**
	 * Sets the e UPDAT E DIS H updated successfully.
	 *
	 * @param e_UPDATE_DISH_UpdatedSuccessfully the new e UPDAT E DIS H updated successfully
	 */
	public void setE_UPDATE_DISH_UpdatedSuccessfully(boolean e_UPDATE_DISH_UpdatedSuccessfully) {
		E_UPDATE_DISH_UpdatedSuccessfully = e_UPDATE_DISH_UpdatedSuccessfully;
	}

	/**
	 * Gets the e GE T SPECIFI C RESTAURAN T msg proccessed.
	 *
	 * @return the e GE T SPECIFI C RESTAURAN T msg proccessed
	 */
	public boolean getE_GET_SPECIFIC_RESTAURANT_MsgProccessed() {
		return E_GET_SPECIFIC_RESTAURANT_MsgProccessed;
	}

	/**
	 * Sets the e GE T SPECIFI C RESTAURAN T msg proccessed.
	 *
	 * @param e_GET_SPECIFIC_RESTAURANT_MsgProccessed the new e GE T SPECIFI C RESTAURAN T msg proccessed
	 */
	public void setE_GET_SPECIFIC_RESTAURANT_MsgProccessed(boolean e_GET_SPECIFIC_RESTAURANT_MsgProccessed) {
		E_GET_SPECIFIC_RESTAURANT_MsgProccessed = e_GET_SPECIFIC_RESTAURANT_MsgProccessed;
	}

	/**
	 * Gets the e NOTIF Y CLIEN T ORDE R APPROVE D msg proccessed.
	 *
	 * @return the e NOTIF Y CLIEN T ORDE R APPROVE D msg proccessed
	 */
	public boolean getE_NOTIFY_CLIENT_ORDER_APPROVED_MsgProccessed() {
		return E_NOTIFY_CLIENT_ORDER_APPROVED_MsgProccessed;
	}

	/**
	 * Sets the e NOTIF Y CLIEN T ORDE R APPROVE D msg proccessed.
	 *
	 * @param e_NOTIFY_CLIENT_ORDER_APPROVED_MsgProccessed the new e NOTIF Y CLIEN T ORDE R APPROVE D msg proccessed
	 */
	public void setE_NOTIFY_CLIENT_ORDER_APPROVED_MsgProccessed(boolean e_NOTIFY_CLIENT_ORDER_APPROVED_MsgProccessed) {
		E_NOTIFY_CLIENT_ORDER_APPROVED_MsgProccessed = e_NOTIFY_CLIENT_ORDER_APPROVED_MsgProccessed;
	}

	/**
	 * Gets the e NOTIF Y CLIEN T ORDE R I N ROUT E msg proccessed.
	 *
	 * @return the e NOTIF Y CLIEN T ORDE R I N ROUT E msg proccessed
	 */
	public boolean getE_NOTIFY_CLIENT_ORDER_IN_ROUTE_MsgProccessed() {
		return E_NOTIFY_CLIENT_ORDER_IN_ROUTE_MsgProccessed;
	}

	/**
	 * Sets the e NOTIF Y CLIEN T ORDE R I N ROUT E msg proccessed.
	 *
	 * @param e_NOTIFY_CLIENT_ORDER_IN_ROUTE_MsgProccessed the new e NOTIF Y CLIEN T ORDE R I N ROUT E msg proccessed
	 */
	public void setE_NOTIFY_CLIENT_ORDER_IN_ROUTE_MsgProccessed(boolean e_NOTIFY_CLIENT_ORDER_IN_ROUTE_MsgProccessed) {
		E_NOTIFY_CLIENT_ORDER_IN_ROUTE_MsgProccessed = e_NOTIFY_CLIENT_ORDER_IN_ROUTE_MsgProccessed;
	}

	/**
	 * Gets the e RESTAURAN T ORDE R RECIEVE D msg proccessed.
	 *
	 * @return the e RESTAURAN T ORDE R RECIEVE D msg proccessed
	 */
	public boolean getE_RESTAURANT_ORDER_RECIEVED_MsgProccessed() {
		return E_RESTAURANT_ORDER_RECIEVED_MsgProccessed;
	}

	/**
	 * Sets the e RESTAURAN T ORDE R RECIEVE D msg proccessed.
	 *
	 * @param e_RESTAURANT_ORDER_RECIEVED_MsgProccessed the new e RESTAURAN T ORDE R RECIEVE D msg proccessed
	 */
	public void setE_RESTAURANT_ORDER_RECIEVED_MsgProccessed(boolean e_RESTAURANT_ORDER_RECIEVED_MsgProccessed) {
		E_RESTAURANT_ORDER_RECIEVED_MsgProccessed = e_RESTAURANT_ORDER_RECIEVED_MsgProccessed;
	}

	/**
	 * Gets the e BRIN G REPORT S msg proccessed.
	 *
	 * @return the e BRIN G REPORT S msg proccessed
	 */
	public boolean getE_BRING_REPORTS_MsgProccessed() {
		return E_BRING_REPORTS_MsgProccessed;
	}

	/**
	 * Sets the e BRIN G REPORT S msg proccessed.
	 *
	 * @param e_BRING_REPORTS_MsgProccessed the new e BRIN G REPORT S msg proccessed
	 */
	public void setE_BRING_REPORTS_MsgProccessed(boolean e_BRING_REPORTS_MsgProccessed) {
		E_BRING_REPORTS_MsgProccessed = e_BRING_REPORTS_MsgProccessed;
	}

	/**
	 * Gets the e GE T OPE N ORDER S msg proccessed.
	 *
	 * @return the e GE T OPE N ORDER S msg proccessed
	 */
	public boolean getE_GET_OPEN_ORDERS_MsgProccessed() {
		return E_GET_OPEN_ORDERS_MsgProccessed;
	}

	/**
	 * Sets the e GE T OPE N ORDER S msg proccessed.
	 *
	 * @param e_GET_OPEN_ORDERS_MsgProccessed the new e GE T OPE N ORDER S msg proccessed
	 */
	public void setE_GET_OPEN_ORDERS_MsgProccessed(boolean e_GET_OPEN_ORDERS_MsgProccessed) {
		E_GET_OPEN_ORDERS_MsgProccessed = e_GET_OPEN_ORDERS_MsgProccessed;
	}

	/**
	 * Gets the e GE T RES T COMMISSION S msg proccessed.
	 *
	 * @return the e GE T RES T COMMISSION S msg proccessed
	 */
	public boolean getE_GET_REST_COMMISSIONS_MsgProccessed() {
		return E_GET_REST_COMMISSIONS_MsgProccessed;
	}

	/**
	 * Sets the e GE T RES T COMMISSION S msg proccessed.
	 *
	 * @param e_GET_REST_COMMISSIONS_MsgProccessed the new e GE T RES T COMMISSION S msg proccessed
	 */
	public void setE_GET_REST_COMMISSIONS_MsgProccessed(boolean e_GET_REST_COMMISSIONS_MsgProccessed) {
		E_GET_REST_COMMISSIONS_MsgProccessed = e_GET_REST_COMMISSIONS_MsgProccessed;
	}

	/**
	 * Gets the e GE T AL L USE R ORDER S msg proccessed.
	 *
	 * @return the e GE T AL L USE R ORDER S msg proccessed
	 */
	public boolean getE_GET_ALL_USER_ORDERS_MsgProccessed() {
		return E_GET_ALL_USER_ORDERS_MsgProccessed;
	}

	/**
	 * Sets the e GE T AL L USE R ORDER S msg proccessed.
	 *
	 * @param e_GET_ALL_USER_ORDERS_MsgProccessed the new e GE T AL L USE R ORDER S msg proccessed
	 */
	public void setE_GET_ALL_USER_ORDERS_MsgProccessed(boolean e_GET_ALL_USER_ORDERS_MsgProccessed) {
		E_GET_ALL_USER_ORDERS_MsgProccessed = e_GET_ALL_USER_ORDERS_MsgProccessed;
	}

	/**
	 * Gets the e GE T QUARTERL Y REPOR T msg proccessed.
	 *
	 * @return the e GE T QUARTERL Y REPOR T msg proccessed
	 */
	public boolean getE_GET_QUARTERLY_REPORT_MsgProccessed() {
		return E_GET_QUARTERLY_REPORT_MsgProccessed;
	}

	/**
	 * Gets the e GE T W 4 C FO R H R msg proccessed.
	 *
	 * @return the e GE T W 4 C FO R H R msg proccessed
	 */
	public boolean getE_GET_W4C_FOR_HR_MsgProccessed() {
		return E_GET_W4C_FOR_HR_MsgProccessed;
	}

	/**
	 * Sets the e GE T W 4 C FO R H R msg proccessed.
	 *
	 * @param e_GET_W4C_FOR_HR_MsgProccessed the new e GE T W 4 C FO R H R msg proccessed
	 */
	public void setE_GET_W4C_FOR_HR_MsgProccessed(boolean e_GET_W4C_FOR_HR_MsgProccessed) {
		E_GET_W4C_FOR_HR_MsgProccessed = e_GET_W4C_FOR_HR_MsgProccessed;
	}

	/**
	 * Gets the e GE T RES T NAM E msg proccessed.
	 *
	 * @return the e GE T RES T NAM E msg proccessed
	 */
	public boolean getE_GET_REST_NAME_MsgProccessed() {
		return E_GET_REST_NAME_MsgProccessed;
	}

	/**
	 * Sets the e GE T RES T NAM E msg proccessed.
	 *
	 * @param e_GET_REST_NAME_MsgProccessed the new e GE T RES T NAM E msg proccessed
	 */
	public void setE_GET_REST_NAME_MsgProccessed(boolean e_GET_REST_NAME_MsgProccessed) {
		E_GET_REST_NAME_MsgProccessed = e_GET_REST_NAME_MsgProccessed;

	}

	/**
	 * Sets the e GE T QUARTERL Y REPOR T msg proccessed.
	 *
	 * @param e_GET_QUARTERLY_REPORT_MsgProccessed the new e GE T QUARTERL Y REPOR T msg proccessed
	 */
	public void setE_GET_QUARTERLY_REPORT_MsgProccessed(boolean e_GET_QUARTERLY_REPORT_MsgProccessed) {
		E_GET_QUARTERLY_REPORT_MsgProccessed = e_GET_QUARTERLY_REPORT_MsgProccessed;
	}

	/**
	 * Gets the e GE T USER S I N ORDE R GRUO P msg proccessed.
	 *
	 * @return the e GE T USER S I N ORDE R GRUO P msg proccessed
	 */
	public boolean getE_GET_USERS_IN_ORDER_GRUOP_MsgProccessed() {
		return E_GET_USERS_IN_ORDER_GRUOP_MsgProccessed;
	}

	/**
	 * Sets the e GE T USER S I N ORDE R GRUO P msg proccessed.
	 *
	 * @param e_GET_USERS_IN_ORDER_GRUOP_MsgProccessed the new e GE T USER S I N ORDE R GRUO P msg proccessed
	 */
	public void setE_GET_USERS_IN_ORDER_GRUOP_MsgProccessed(boolean e_GET_USERS_IN_ORDER_GRUOP_MsgProccessed) {
		E_GET_USERS_IN_ORDER_GRUOP_MsgProccessed = e_GET_USERS_IN_ORDER_GRUOP_MsgProccessed;
	}
	
	/**
	 * Gets the e GE T BRANCHMA N T O CE O REPORT S msg proccessed.
	 *
	 * @return the e GE T BRANCHMA N T O CE O REPORT S msg proccessed
	 */
	public boolean getE_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed() {
		return E_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed;
	}

	/**
	 * Sets the e GE T BRANCHMA N T O CE O REPORT S msg proccessed.
	 *
	 * @param e_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed the new e GE T BRANCHMA N T O CE O REPORT S msg proccessed
	 */
	public void setE_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed(boolean e_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed) {
		E_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed = e_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed;
	}


}
