package newOrder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import client.ClientUI;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.E_DeliveryType;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the ChooseDeliveryTypeWindow.
 */
public class ChooseDeliveryTypeForm implements IScreenController {

	/** The combined controller for many other controllers */
	UserViewController userViewController;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The delivery option button. */
	@FXML
	private Button deliveryOptionButton;

	/** The pickup option button. */
	@FXML
	private Button pickupOptionButton;

	/** The Time text field. */
	@FXML
	private TextField TimeTextField;

	/** The Early order button. */
	@FXML
	private CheckBox EarlyOrderButton;

	/** The Error message. */
	@FXML
	private Label ErrorMsg;

	/** The date picker. */
	@FXML
	private DatePicker datePicker;

	/** The Log out button. */
	@FXML
	private Button LogOutButton;

    @FXML
    private Label groupOrderText;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The robot option button. */
	@FXML
	private Button robotOptionButton;

	/** The group order check box. */
	@FXML
	private CheckBox groupOrderCheckBox;
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
	 * @param event - an ActionEvent from the ChooseDeliveryTypeWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}
	
	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the ChooseDeliveryTypeWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.NEW_ORDER_IDENTIFICATION, ClientUI.class, MyScreenEnum.NEW_ORDER_IDENTIFICATION.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.NEW_ORDER_IDENTIFICATION);
	}

	/**
	 * Check if the time and date input are valid.
	 *
	 * @return true, if valid, else false
	 */
	public boolean checkIfTimeAndDateValid() {
		String time = TimeTextField.getText();
		if (!checkTime()) {
			ErrorMsg.setText("Please enter valid date and time!");
			ErrorMsg.setVisible(true);
			PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
			pt.setOnFinished(event -> {
				ErrorMsg.setVisible(false);
			});
			pt.play();
			return false;
		}
		try {
			if (datePicker.getValue().isAfter(LocalDate.now()) || datePicker.getValue().isEqual(LocalDate.now())
					&& LocalTime.now().isBefore(LocalTime.parse(time))) {
				userViewController.getOrderController().getOrder().setDesiredOrderDate(datePicker.getValue());
				userViewController.getOrderController().getOrder()
						.setDesiredOrderTime(LocalTime.parse(TimeTextField.getText()));
				return true;
			} else {

				ErrorMsg.setText("Please enter valid date and time!");
				ErrorMsg.setVisible(true);
				PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
				pt.setOnFinished(event -> {
					ErrorMsg.setVisible(false);
				});
				pt.play();
				return false;
			}
		} catch (Exception e) {

			ErrorMsg.setText("Please enter valid date and time!");
			ErrorMsg.setVisible(true);
			PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
			pt.setOnFinished(event -> {
				ErrorMsg.setVisible(false);
			});
			pt.play();
			return false;
		}
//		
//		try {
//			if(datePicker.getValue() == null || datePicker.getValue().isBefore(LocalDate.now()) || LocalTime.now().isAfter(LocalTime.parse(time))){
//				ErrorMsg.setVisible(true);
//				return false;
//			}
//		}catch(DateTimeParseException e) {
//			ErrorMsg.setVisible(true);
//			return false;
//		}
	}

	/**
	 * Logging out from current account.
	 *
	 * @param event - an ActionEvent from the ChooseDeliveryTypeWindow
	 */
	@FXML
	void logOut(ActionEvent event) {
		
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userViewController.getUserController().getUser().getUserID() + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
	}

//	public boolean processTimeDate() {
//		if (checkDate() && checkTime()) {
//			userViewController.getOrderController().getOrder().setDesiredOrderDate(datePicker.getValue());
//			if (!TimeTextField.getText().equals("")) {
//				userViewController.getOrderController().getOrder()
//						.setDesiredOrderTime(LocalTime.parse(TimeTextField.getText()));
//				return true;
//			}
//			else {
//				return false;
//			}
//		}
//
//		return false;
//	}

	/**
	 * Checks if the time input is valid.
	 *
	 * @return true, if valid, else false
	 */
	public boolean checkTime() {
		String time = TimeTextField.getText();
		boolean hasDots = false;
		for (int i = 0; i < time.length(); i++) {
			if (time.charAt(i) == ':')
				hasDots = true;
		}
		if (hasDots && time.length() == 5) {
			String[] saparated = time.split(":", 0);
			try {
				int hours = Integer.parseInt(saparated[0]);
				int minutes = Integer.parseInt(saparated[1]);
				if (hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60)
					return true;
			} catch (NumberFormatException e) {
				System.out.println("Number not good");// not important, we can delete if we want
			}
		}
		return false;
//		if (datePicker.getValue() != null) {
//			if (datePicker.getValue().isEqual(LocalDate.now()) || datePicker.getValue().isAfter(LocalDate.now())) {
//				System.out.println("good date");
//				return true;
//			}
//		}
//		System.out.println("Bad date");
//		return false;
//
	}

//	public boolean checkTime() {//asaf
//		if (datePicker.getValue().isEqual(LocalDate.now())) {
//			String time = TimeTextField.getText();
//			LocalTime.parse(time);
//			try {
//				if (LocalTime.now().isBefore(LocalTime.parse(time))) {
//					System.out.println("Valid time string: " + time);
//					return true;
//				}
//				return false;
//
//			} catch (DateTimeParseException | NullPointerException e) {
//				System.out.println("Invalid time string: " + time);
//				return false;
//			}
//		} else {
//			// check time is within hours of restaurant
//		}
//
//		return true;
//	}

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
	 * Sets fields and functionality in the ChooseDeliveryTypeWindow.
	 */
	@Override
	public void setParameters() {
		User user = userViewController.getUserController().getUser();
		groupOrderCheckBox.setVisible(false);
		groupOrderText.setVisible(false);
		if (user.getUserType().equals(E_UserTypeEnum.E_BUSINESS_USER)) {
			groupOrderCheckBox.setVisible(true);
			groupOrderText.setVisible(true);
		}
		groupOrderCheckBox.setOnAction(e->{
			if(groupOrderCheckBox.isSelected()) {
				userViewController.getOrderController().getOrder().setIsGroupOrder(true);
			}
			else {
				userViewController.getOrderController().getOrder().setIsGroupOrder(false);
			}
		});
		ErrorMsg.setText("Please enter valid date and time!");
		ErrorMsg.setTextFill(Color.RED);
		ErrorMsg.setStyle("-fx-background-color: #FFE5CC");

		ErrorMsg.setVisible(false);
		TimeTextField.setOnMouseClicked(e -> {
			ErrorMsg.setVisible(false);
		});

		datePicker.setOnMouseClicked(e -> {
			ErrorMsg.setVisible(false);
		});

		pickupOptionButton.setOnAction(e -> {
			if (userViewController.getOrderController().getOrder().getEarlyOrder() == 1) {
				if (checkIfTimeAndDateValid()) {
					if (datePicker.getValue().compareTo(LocalDate.now()) > 0) {
						if (userViewController.getUserController().getUser().getUserType()
								.equals(E_UserTypeEnum.E_BUSINESS_USER) && groupOrderCheckBox.isSelected()) {
							Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
							updateUserInfo.getMsgArrayL().add(
									"UPDATE user SET isGroupOrder = '1' WHERE userID = '" + user.getUserID() + "';");
							userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);
						}
						userViewController.getOrderController().getOrder().setDeliveryType(E_DeliveryType.E_PICKUP);
						userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_RESTAURANT);

					} else {
						LocalTime time = LocalTime.now().plus(2, ChronoUnit.HOURS);
						LocalTime ourTime = LocalTime.parse(TimeTextField.getText());
						if (ourTime.compareTo(time) >= 0) {
							if (userViewController.getUserController().getUser().getUserType()
									.equals(E_UserTypeEnum.E_BUSINESS_USER) && groupOrderCheckBox.isSelected()) {
								Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
								updateUserInfo.getMsgArrayL().add("UPDATE user SET isGroupOrder = '1' WHERE userID = '"
										+ user.getUserID() + "';");
								userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);
							}
							userViewController.getOrderController().getOrder().setDeliveryType(E_DeliveryType.E_PICKUP);
							userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_RESTAURANT);
						} else {
							ErrorMsg.setVisible(true);
							ErrorMsg.setText("Early Order can only be provided in more than 2 hours");

							ErrorMsg.setTextFill(Color.RED);
							ErrorMsg.setStyle("-fx-background-color: #FFE5CC");

							PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
							pt.setOnFinished(event -> {
								ErrorMsg.setVisible(false);
							});
							pt.play();
						}
					}
				}
			} else {
				if (checkIfTimeAndDateValid()) {
					if (datePicker.getValue().compareTo(LocalDate.now()) == 0) {
						LocalTime timePlus2 = LocalTime.now().plus(2, ChronoUnit.HOURS);
						LocalTime timeNow = LocalTime.now();
						LocalTime ourTime = LocalTime.parse(TimeTextField.getText());
						if (ourTime.compareTo(timePlus2) < 0 && ourTime.compareTo(timeNow) >= 0) {
							if (userViewController.getUserController().getUser().getUserType()
									.equals(E_UserTypeEnum.E_BUSINESS_USER) && groupOrderCheckBox.isSelected()) {
								Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
								updateUserInfo.getMsgArrayL().add("UPDATE user SET isGroupOrder = '1' WHERE userID = '"
										+ user.getUserID() + "';");
								userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);
							}
							userViewController.getOrderController().getOrder().setDeliveryType(E_DeliveryType.E_PICKUP);
							userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_RESTAURANT);
						} else {
							ErrorMsg.setText("Please choose Early Order for the time you entered");

							ErrorMsg.setTextFill(Color.RED);
							ErrorMsg.setStyle("-fx-background-color: #FFE5CC");

							ErrorMsg.setVisible(true);
							PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
							pt.setOnFinished(event -> {
								ErrorMsg.setVisible(false);
							});
							pt.play();

						}
					} else {
						ErrorMsg.setText("Please choose Early Order for the date you entered");

						ErrorMsg.setTextFill(Color.RED);
						ErrorMsg.setStyle("-fx-background-color: #FFE5CC");

						ErrorMsg.setVisible(true);
						PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
						pt.setOnFinished(event -> {
							ErrorMsg.setVisible(false);
						});
						pt.play();
					}
				}
			}
		});
		deliveryOptionButton.setOnAction(e -> {
			if (userViewController.getOrderController().getOrder().getEarlyOrder() == 1) {
				if (checkIfTimeAndDateValid()) {
					if (datePicker.getValue().compareTo(LocalDate.now()) > 0) {
						if (userViewController.getUserController().getUser().getUserType()
								.equals(E_UserTypeEnum.E_BUSINESS_USER) && groupOrderCheckBox.isSelected()) {

							Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
							updateUserInfo.getMsgArrayL().add(
									"UPDATE user SET isGroupOrder = '1' WHERE userID = '" + user.getUserID() + "';");
							userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);
						}
						userViewController.getOrderController().getOrder().setDeliveryType(E_DeliveryType.E_DELIVERY);
						userViewController.getScreenManager().setScreen(MyScreenEnum.DELIVERY_INFO);
					} else {
						LocalTime time = LocalTime.now().plus(2, ChronoUnit.HOURS);
						LocalTime ourTime = LocalTime.parse(TimeTextField.getText());
						if (ourTime.compareTo(time) >= 0) {
							if (userViewController.getUserController().getUser().getUserType()
									.equals(E_UserTypeEnum.E_BUSINESS_USER) && groupOrderCheckBox.isSelected()) {
								Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
								updateUserInfo.getMsgArrayL().add("UPDATE user SET isGroupOrder = '1' WHERE userID = '"
										+ user.getUserID() + "';");
								userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);
							}
							userViewController.getOrderController().getOrder()
									.setDeliveryType(E_DeliveryType.E_DELIVERY);
							userViewController.getScreenManager().setScreen(MyScreenEnum.DELIVERY_INFO);
						} else {
							ErrorMsg.setVisible(true);
							ErrorMsg.setText("Early Order can only be provided in more than 2 hours");
							ErrorMsg.setTextFill(Color.RED);
							ErrorMsg.setStyle("-fx-background-color: #FFE5CC");
							PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
							pt.setOnFinished(event -> {
								ErrorMsg.setVisible(false);
							});
							pt.play();
						}
					}
				}
			} else {
				if (checkIfTimeAndDateValid()) {
					if (datePicker.getValue().compareTo(LocalDate.now()) == 0) {
						LocalTime timePlus2 = LocalTime.now().plus(2, ChronoUnit.HOURS);
						LocalTime timeNow = LocalTime.now();
						LocalTime ourTime = LocalTime.parse(TimeTextField.getText());
						if (ourTime.compareTo(timePlus2) < 0 && ourTime.compareTo(timeNow) >= 0) {
							if (userViewController.getUserController().getUser().getUserType()
									.equals(E_UserTypeEnum.E_BUSINESS_USER) && groupOrderCheckBox.isSelected()) {
								Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
								updateUserInfo.getMsgArrayL().add("UPDATE user SET isGroupOrder = '1' WHERE userID = '"
										+ user.getUserID() + "';");
								userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);
							}
							userViewController.getOrderController().getOrder()
									.setDeliveryType(E_DeliveryType.E_DELIVERY);
							userViewController.getScreenManager().setScreen(MyScreenEnum.DELIVERY_INFO);
						} else {
							ErrorMsg.setText("Please choose Early Order for the time you entered");
							ErrorMsg.setTextFill(Color.RED);
							ErrorMsg.setStyle("-fx-background-color: #FFE5CC");
							ErrorMsg.setVisible(true);
							PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
							pt.setOnFinished(event -> {
								ErrorMsg.setVisible(false);
							});
							pt.play();

						}
					} else {
						ErrorMsg.setText("Please choose Early Order for the date you entered");
						ErrorMsg.setTextFill(Color.RED);
						ErrorMsg.setStyle("-fx-background-color: #FFE5CC");
						ErrorMsg.setVisible(true);
						PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
						pt.setOnFinished(event -> {
							ErrorMsg.setVisible(false);
						});
						pt.play();
					}
				}
			}
		});

		robotOptionButton.setOnAction(e -> {
			ErrorMsg.setText("This feature is coming soon...");
			ErrorMsg.setVisible(true);
			PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
			pt.setOnFinished(event -> {
				ErrorMsg.setVisible(false);
			});
		});// Tal

		EarlyOrderButton.setOnAction(e -> {
			if (EarlyOrderButton.isSelected()) {
				userViewController.getOrderController().getOrder().setEarlyOrder(true);
			} else {
				userViewController.getOrderController().getOrder().setEarlyOrder(false);
			}
		});
		if (userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_BUSINESS_USER)) {
			Message getBusinessColleges = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_BUSINESS_COLLEAGUES);
			getBusinessColleges.getMsgArrayL()
					.add("SELECT * FROM user WHERE businessW4CNumber = '" + user.getBusinessW4C() + "';");
			userViewController.getClientController().handleMessageFromClientUI(getBusinessColleges);

			while (!userViewController.getClientController().getMessageController()
					.getE_GET_BUSINESS_COLLEGES_MsgProccessed()) {
				System.out.println("waiting on E_GET_BUSINESS_COLLEGES...");
				try {
					Thread.currentThread().sleep(1); // Eli - Check if we can use sleep
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			userViewController.getClientController().getMessageController()
					.setE_GET_BUSINESS_COLLEGES_MsgProccessed(false);
		}
	}
}
