package newOrder;

import client.ClientUI;
import javafx.application.Platform;
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
 * This class is the functionality for the OrderConfiramtionWindow.
 */
public class OrderConfirmationForm implements IScreenController {
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;
	
	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The Log out button. */
	@FXML
	private Button LogOutButton;

	/** The order number text. */
	@FXML
	private Text orderNumberText;
	
	/** The order status. */
	@FXML
	private Text orderStatus;





	/**
	 * Go to the Home page.
	 *
	 * @param event - an ActionEvent from the OrderConfiramtionWindow
	 */
	@FXML
	void homePage(ActionEvent event) {
		userViewController.goToHomeScreen();
		
	}

	/**
	 * Logging out of current account.
	 *
	 * @param event - an ActionEvent from the OrderConfiramtionWindow
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
	 * Sets fields and functionality in the OrderConfiramtionWindow.
	 */
	@Override
	public void setParameters() {

		orderNumberText.setText("#" + userViewController.getOrderController().getOrder().getOrderNumber());

	}

}
