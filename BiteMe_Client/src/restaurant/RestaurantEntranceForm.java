package restaurant;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the RestaurantEntranceWindow.
 */
public class RestaurantEntranceForm implements IScreenController {

	/** The combined controller for many other controllers.. */
	UserViewController userViewController;

	/** The Active orders button. */
	@FXML
	private Button ActiveOrdersButton;

	/** The update information button. */
	@FXML
	private Button updateInformationButton;

	/** The recent orders button. */
	@FXML
	private Button recentOrdersButton;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The show commissions button. */
	@FXML
	private Button showCommissionsButton;

	/** The Log out button. */
	@FXML
	private Button LogOutButton;

	/** The home page button. */
	@FXML
	private Button homePageButton;

	/**
	 * Logging out of account.
	 *
	 * @param event - an ActionEvent from the RestaurantEntranceWindow
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
	 * Open active orders window.
	 *
	 * @param event - an ActionEvent from the RestaurantEntranceWindow
	 */
	@FXML
	void openActiveOrdersWindow(ActionEvent event) {
		
		userViewController.getScreenManager().loadScreen(MyScreenEnum.RESTAURANT_ACTIVEORDERS, ClientUI.class,
				MyScreenEnum.RESTAURANT_ACTIVEORDERS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ACTIVEORDERS);
	}

	/**
	 * Open home page.
	 *
	 * @param event - an ActionEvent from the RestaurantEntranceWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Open order history.
	 *
	 * @param event - an ActionEvent from the RestaurantEntranceWindow
	 */
	@FXML
	void openOrderHistory(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.RESTAURANT_ORDER_HISTORY, ClientUI.class,
				MyScreenEnum.RESTAURANT_ORDER_HISTORY.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ORDER_HISTORY);
	}

	/**
	 * Open update menu window.
	 *
	 * @param event - an ActionEvent from the RestaurantEntranceWindow
	 */
	@FXML
	void openUpdateMenuWindow(ActionEvent event) {
		userViewController.getRestaurantController().setStateMenuUpdate(true);
		userViewController.getScreenManager().loadScreen(MyScreenEnum.CHOOSE_DISHES, ClientUI.class,
				MyScreenEnum.CHOOSE_DISHES.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DISHES);
	}

	/**
	 * Open show commissions window.
	 *
	 * @param event - an ActionEvent from the RestaurantEntranceWindow
	 */
	@FXML
	void openshowCommissionWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.RESTAURANT_COMMISSIONS, ClientUI.class,
				MyScreenEnum.RESTAURANT_COMMISSIONS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_COMMISSIONS);
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
	 * Sets fields and functionality in the RestaurantEntranceWindow.
	 */
	@Override
	public void setParameters() {

		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_SPECIFIC_RESTAURANT);
		message.addToList("SELECT * FROM restaurant WHERE restaurantID = '"
				+ userViewController.getUserController().getUser().getManageRestID() + "'");
		userViewController.getClientController().handleMessageFromClientUI(message);
		while (!userViewController.getClientController().getMessageController()
				.getE_GET_SPECIFIC_RESTAURANT_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_GET_SPECIFIC_RESTAURANT...");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController()
				.setE_GET_SPECIFIC_RESTAURANT_MsgProccessed(false);
		if (userViewController.getRestaurantController().getChosenRestaurant() != null) {
			personalWelcomeLabel.setText("Welcome " + userViewController.getRestaurantController().getChosenRestaurant().getName());
		} else {
			System.out.println("Could not find restaurant in RestaurantEntrance");
		}
	}

}
