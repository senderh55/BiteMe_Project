package newOrder;

import java.time.LocalTime;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the OrderRecievedWindow.
 */
public class OrderRecievedForm implements IScreenController {
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The Log out button. */
	@FXML
	private Button LogOutButton;

	/** The approve order was received button. */
	@FXML
	private Button approveOrderWasRecievedButton;

	/** The order number text. */
	@FXML
	private Text orderNumberText;

	/** The order status. */
	@FXML
	private Text orderStatus;



	/**
	 * Approve order was received.
	 *
	 * @param event - an ActionEvent from the OrderRecievedWindow
	 */
	@FXML
	void approveOrderWasRecieved(ActionEvent event) {
		Message approveOrderRecieved = new Message(OwnerEnum.E_CLIENT, OpEnum.E_CLIENT_ORDER_RECIEVED);
		String time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
		approveOrderRecieved.getMsgArrayL()
				.add("UPDATE biteme_db.order SET orderStatus = 'E_DELIVERD', timeRecieved = '" + time + "' WHERE orderNumber = '"
						+ userViewController.getOrderController().getOrder().getOrderNumber() + "';");
		approveOrderRecieved.getMsgArrayL().add(userViewController.getOrderController().getOrder().getOrderNumber());
		approveOrderRecieved.getMsgArrayL().add(userViewController.getUserController().getUser().getUserCreditList());
		approveOrderRecieved.getMsgArrayL().add(userViewController.getRestaurantController().getChosenRestaurant().getRestaurantID());
		approveOrderRecieved.getMsgArrayL().add(userViewController.getUserController().getUser().getUserID());
		userViewController.getClientController().handleMessageFromClientUI(approveOrderRecieved);
		userViewController.resetUserOrderDetails();
		userViewController.setUpUserScreens();
		userViewController.getScreenManager().setScreen(MyScreenEnum.USER_ENTRANCE_WINDOW);
	}



	/**
	 * Go to the Home page.
	 *
	 * @param event - an ActionEvent from the OrderRecievedWindow
	 */
	@FXML
	void homePage(ActionEvent event) {
		userViewController.goToHomeScreen();
		
	}

	/**
	 * Logging out of current account.
	 *
	 * @param event - an ActionEvent from the OrderRecievedWindow
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
	 * Sets the user view controller.
	 *
	 * @param UVC the new user view controller
	 */
	@Override
	public void setUserViewController(UserViewController UVC) {
		userViewController = UVC;

	}

	/**
	 * Sets fields and functionality in the OrderRecievedWindow.
	 */
	@Override
	public void setParameters() {
		orderNumberText.setText("#" + userViewController.getOrderController().getOrder().getOrderNumber());

	}

}
