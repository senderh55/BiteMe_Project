
package newOrder;

import delivery.Delivery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the DeliveryInfoWindow.
 */
public class DeliveryInfoForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	@FXML
	private TextField BldCodeTextField;

	/** The Information label. */
	@FXML
	private Text InformationLabel;
	
	/** The business name text. */
	@FXML
	private Text businessNameText;

	/** The business star. */
	@FXML
	private Label businessStar;

	/** The business text field. */
	@FXML
	private TextField businessTextField;

	/** The apartment text field. */
	@FXML
	private TextField aprtTextField;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The or label. */
	@FXML
	private Text orLabel;

	/** The business name error label. */
	@FXML
	private Label businessNameErrorLabel;

	/** The city text field. */
	@FXML
	private TextField cityTextField;

	/** The email text field. */
	@FXML
	private TextField emailTextField;

	/** The floor text field. */
	@FXML
	private TextField floorTextField;

	/** The first name text field. */
	@FXML
	private TextField fnTextField;

	/** The header label. */
	@FXML
	private Text headerLabel;
	/** The next button. */
	@FXML
	private Button nextButton;

	/** The phone number text field. */
	@FXML
	private TextField pnTextField;

	/** The street text field. */
	@FXML
	private TextField streetTextField;

	/** The zip code text field. */
	@FXML
	private TextField zipcodeTextField;

	/** The message label. */
	@FXML
	private Label msgLabel;

	/**
	 * Logout from account.
	 *
	 * @param event - an ActionEvent from the DeliveryInfoWindow
	 */
	@FXML
	void logout(ActionEvent event) {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userViewController.getUserController().getUser().getUserID() + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
	}

	/**
	 * Go to home page.
	 *
	 * @param event - an ActionEvent from the DeliveryInfoWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the DeliveryInfoWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		updateUserInfo.getMsgArrayL().add("UPDATE user SET isGroupOrder = '0' WHERE userID = '"
				+ userViewController.getUserController().getUser().getUserID() + "';");
		userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);
		userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DELIVERY_TYPE);

	}

	/**
	 * Go to the next window - choosing restaurant.
	 *
	 * @param event - an ActionEvent from the DeliveryInfoWindow
	 */
	@FXML
	void nextWindow(ActionEvent event) {
		if (pnTextField.getText().equals("")
				|| (streetTextField.getText().equals("") && cityTextField.getText().equals("")
						&& businessTextField.getText().equals(""))
				|| ((streetTextField.getText().equals("") || cityTextField.getText().equals(""))
						&& businessTextField.getText().equals(""))
				|| fnTextField.getText().equals("")) {

			msgLabel.setVisible(true);
		}else if(userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_BUSINESS_USER) 
				&& !businessTextField.getText().equals(userViewController.getUserController().getUser().getBusinessName())){
			businessNameErrorLabel.setVisible(true);
		}
		
		else {
			Delivery delivery = new Delivery();
			if (streetTextField.getText().equals("")) {
				delivery.setStreet("NA");
			} else {
				delivery.setStreet(streetTextField.getText());
			}
			delivery.setPhoneNumber(pnTextField.getText());
			if (cityTextField.getText().equals("")) {
				delivery.setCity("NA");
			} else {
				delivery.setCity(cityTextField.getText());
			}

			delivery.setReceiverName(fnTextField.getText());
			delivery.setEmail(emailTextField.getText());
			delivery.setZipCode(zipcodeTextField.getText());
			delivery.setApartment(aprtTextField.getText());
			delivery.setFloor(floorTextField.getText());
			delivery.setBuildingCode(BldCodeTextField.getText());
			if (businessTextField.getText().equals("")) {
				delivery.setBusinessName("NA");
			} else {
				delivery.setBusinessName(businessTextField.getText());
			}
			userViewController.getDeliveryController().setDelivery(delivery);

			userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_RESTAURANT);
		}
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
	 * Sets fields and functionality in the DeliveryInfoWindow.
	 */
	@Override
	public void setParameters() {
		businessNameText.setVisible(false);
		businessStar.setVisible(false);
		businessTextField.setVisible(false);
		businessNameErrorLabel.setVisible(false);
		orLabel.setVisible(false);
		if (userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_BUSINESS_USER)) {
			businessNameText.setVisible(true);
			businessStar.setVisible(true);
			businessTextField.setVisible(true);
			orLabel.setVisible(true);
		}
		msgLabel.setVisible(false);
		BldCodeTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		zipcodeTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		streetTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		pnTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		fnTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		zipcodeTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		floorTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		emailTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		cityTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		aprtTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		BldCodeTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
		});
		businessTextField.setOnMouseClicked(e -> {
			msgLabel.setVisible(false);
			businessNameErrorLabel.setVisible(false);
		});
	}
}
