package newOrder;

import java.text.DecimalFormat;
import java.time.LocalDate;

import Order.Order;
import client.ClientUI;
import delivery.Delivery;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import restaurant.Restaurant;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.Credit;
import utils.E_DeliveryType;
import utils.E_OrderStatus;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the OrderPaymentWindow.
 */
public class OrderPaymentForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;
	boolean chargeRemainingBalance = false;

	/** The credit amount available. */
	double creditAmntAvailable;

	/** The order with delivery. */
	double orderWithDelivery;

	/** The order status. */
	@FXML
	private Text orderStatus;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The Log out button. */
	@FXML
	private Button LogOutButton;

	/** The approve button. */
	@FXML
	private Button approveButton;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The charge account radio button. */
	@FXML
	private RadioButton chargeAccountRadioBtn;

	/** The charge employer radio button. */
	@FXML
	private RadioButton chargeEmployerRadioButton;

	/** The delivery price label. */
	@FXML
	private Text deliveryPriceLabel;

	@FXML
	private Button chargeRemainingButton;

	/** The employer name. */
	@FXML
	private TextField employerName;

	/** The employer W 4 C. */
	@FXML
	private TextField employerW4C;

	/** The error text. */
	@FXML
	private Text errorText;

	/** The order total label. */
	@FXML
	private Text orderTotalLabel;

	/** The shared order label. */
	@FXML
	private Text sharedOrderLabel;

	/** The total label. */
	@FXML
	private Text totalLabel;

	/** The use credits check box. */
	@FXML
	private CheckBox useCreditsCheckBox;

	/** The user header name label. */
	@FXML
	private Text userHeaderNameLabel;

	/** The employer name text. */
	@FXML
	private Text employerNameText;

	/** The employer W 4 C text. */
	@FXML
	private Text employerW4CText;

	@FXML
	private TextField totalTextField;

	/**
	 * Logout from account.
	 *
	 * @param event - an ActionEvent from the OrderPaymentWindow
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
	 * @param event - an ActionEvent from the OrderPaymentWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Approve user payment.
	 *
	 * @param event - an ActionEvent from the OrderPaymentWindow
	 */
	@FXML
	void approvePayment(ActionEvent event) {
		User u = userViewController.getUserController().getUser();
		Order o = userViewController.getOrderController().getOrder();
		Restaurant r = userViewController.getRestaurantController().getChosenRestaurant();
		Delivery d = userViewController.getDeliveryController().getDelivery();
		if (!chargeRemainingBalance) {
			DecimalFormat df = new DecimalFormat();
			PauseTransition pt = new PauseTransition(Duration.seconds(5));
			pt.setOnFinished(e -> {
				employerW4C.setStyle("");
				employerName.setStyle("");
				errorText.setText("");
			});
			orderStatus.setVisible(true);
			

			if (chargeEmployerRadioButton.isSelected()) {
				if (!(employerW4C.getText().equals(u.getBusinessW4C() + "")
						&& employerName.getText().equals(u.getBusinessName()))) {
					employerW4C.setStyle("-fx-border-width: 2;" + "-fx-border-color: #dc143c;");
					employerName.setStyle("-fx-border-width: 2;" + "-fx-border-color: #dc143c;");
					errorText.setText("Invalid Employer details");
					pt.play();
					return;
				} else if (u.getBusinessAmountUsed() + userViewController.getOrderController().getOrder()
						.getTotalPrice() <= u.getBusinessMonthlyLimit()) {
					u.addBusinessAmountUsed(userViewController.getOrderController().getOrder().getTotalPrice());
					String updateUserQuery = "UPDATE user SET businessAmountUsed = " + u.getBusinessAmountUsed()
							+ " WHERE userID = " + u.getUserID() + ";";
					Message businessAmntUsedMessage = new Message(OwnerEnum.E_CLIENT, OpEnum.E_BUSINESS_AMNT_USED);
					businessAmntUsedMessage.getMsgArrayL().add(updateUserQuery);
					userViewController.getClientController().handleMessageFromClientUI(businessAmntUsedMessage);
				} else {
					double totalMinusLeft = userViewController.getOrderController().getOrder().getTotalPrice()
							- (u.getBusinessMonthlyLimit() - u.getBusinessAmountUsed());
					chargeRemainingButton.setText(
							"Charge remaining balance (" + df.format(totalMinusLeft) + ") to personal account");
					chargeRemainingButton.setVisible(true);
					chargeRemainingButton.setOnAction(e -> {
						u.setBusinessAmountUsed(u.getBusinessMonthlyLimit());
						chargeRemainingBalance = true;
						approvePayment(event);
					});
					
//				errorText.setText("You have reached your monthly business limit.");
//				pt.play();
					return;
				}
			} else if (!chargeAccountRadioBtn.isSelected()) {
				errorText.setText("Please choose a payment method.");
				pt.play();
				return;
			}
		}
		Message addOrderMessage = new Message(OwnerEnum.E_CLIENT, OpEnum.E_ADD_ORDER);
		addOrderMessage.getMsgArrayL().clear();

		String orderQuery = "";
		if (o.getDeliveryType().equals(E_DeliveryType.E_DELIVERY)) {
			orderQuery = "', '" + r.getRestaurantID() + "', '" + u.getUserID() + "', '" + r.getName() + "', '"
					+ o.getDesiredOrderTime() + "', '" + o.getDesiredOrderDate() + "', '" + "NA" + "', '" + "NA"
					+ "', '" + d.getStreet() + "', '" + d.getCity() + "', '" + d.getPhoneNumber() + "', '"
					+ E_OrderStatus.E_WAITING_REST_APPROVE.toString() + "', '" + o.getDeliveryType() + "', '" + "E_NA"
					+ "', '" + orderWithDelivery + "', '" + "NA" + "', '" + u.getTempBranch().toString() + "', '"
					+ o.getEarlyOrder() + "', '" + d.getBusinessName() + "', '" + d.getReceiverName() + "');";
		} else if (o.getDeliveryType().equals(E_DeliveryType.E_PICKUP)) {
			orderQuery = "', '" + r.getRestaurantID() + "', '" + u.getUserID() + "', '" + r.getName() + "', '"
					+ o.getDesiredOrderTime() + "', '" + o.getDesiredOrderDate() + "', '" + "NA" + "', '" + "NA"
					+ "', '" + "NA" + "', '" + "NA" + "', '" + "NA" + "', '"
					+ E_OrderStatus.E_WAITING_REST_APPROVE.toString() + "', '" + o.getDeliveryType() + "', '" + "E_NA"
					+ "', '" + orderWithDelivery + "', '" + "NA" + "', '" + u.getTempBranch().toString() + "', '"
					+ o.getEarlyOrder() + "', '" + "NA" + "', '" + "NA" + "');";
		}

		addOrderMessage.getMsgArrayL().add(orderQuery);
		addOrderMessage.getMsgArrayL().add(u.getTempBranch().getBranchInt() + "-" + r.getRestaurantID() + "-");
		addOrderMessage.getMsgArrayL().add(r.getRestaurantID());
		userViewController.getClientController().handleMessageFromClientUI(addOrderMessage);

		while (!userViewController.getClientController().getMessageController().getE_ADD_ORDER_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_ADD_ORDER...");
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		userViewController.getClientController().getMessageController().setE_ADD_ORDER_MsgProccessed(false);

		userViewController.getReportController().proccessOrder(userViewController.getOrderController().getOrderList());

		// restID , month, year , Branch ,sumAppetizer, amntAppetizer, ......
		Message reportsMessage = new Message(OwnerEnum.E_CLIENT, OpEnum.E_REPORTS_DATA);
		reportsMessage.getMsgArrayL().add(r.getRestaurantID());
		reportsMessage.getMsgArrayL().add(LocalDate.now().getMonthValue());
		reportsMessage.getMsgArrayL().add(LocalDate.now().getYear());
		reportsMessage.getMsgArrayL().add(userViewController.getUserController().getUser().getTempBranch().toString());
		reportsMessage.getMsgArrayL().add(userViewController.getReportController().getReportsMap());

		userViewController.getClientController().handleMessageFromClientUI(reportsMessage);

		if (useCreditsCheckBox.isSelected()) {
			if (!u.updateCredit(r.getRestaurantID(), creditAmntAvailable)) {
				System.out.println("Could not update credit");
			} else {
				StringBuilder creditsStr = new StringBuilder();
				for (Credit credit : u.getUserCreditList()) {
					creditsStr.append(credit.toString() + ", ");
				}
				creditsStr.deleteCharAt(creditsStr.length() - 1);
				Message updateCreditField = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
				updateCreditField.getMsgArrayL().add("UPDATE user SET credits = '" + creditsStr.toString()
						+ "' WHERE userID = '" + u.getUserID() + "';");
				userViewController.getClientController().handleMessageFromClientUI(updateCreditField);
			}
		}

		Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		updateUserInfo.getMsgArrayL().add("UPDATE user SET isGroupOrder = '0', businessAmountUsed = '" + userViewController.getUserController().getUser().getBusinessAmountUsed()  + "' WHERE userID = '"
				+ userViewController.getUserController().getUser().getUserID() + "';");
		userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);

		userViewController.getScreenManager().loadScreen(MyScreenEnum.ORDER_WAITING_APPROVAL, ClientUI.class,
				MyScreenEnum.ORDER_WAITING_APPROVAL.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.ORDER_WAITING_APPROVAL);

	}

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the OrderPaymentWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().setScreen(MyScreenEnum.ORDER_SUMMARY);
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
	 * Sets fields and functionality in the OrderPaymentWindow.
	 */
	@Override
	public void setParameters() {
		chargeRemainingButton.setVisible(false);
		User user = userViewController.getUserController().getUser();
		Order order = userViewController.getOrderController().getOrder();
		double totalWithCredits;
		double deliveryPrice = 25;
		DecimalFormat df = new DecimalFormat();
		String earlyOrder = "";
		if(userViewController.getOrderController().getOrder().getEarlyOrder() == 1) {
			earlyOrder = " (Early Order -10%)";
		}
		if (!user.getUserType().equals(E_UserTypeEnum.E_BUSINESS_USER)) {
			chargeEmployerRadioButton.setVisible(false);
			employerName.setVisible(false);
			employerW4C.setVisible(false);
			sharedOrderLabel.setVisible(false);
			employerNameText.setVisible(false);
			employerW4CText.setVisible(false);
		}

		// SELECT userID FROM user WHERE isGroupOrder = '1';
		if (userViewController.getOrderController().getOrder().getGroupOrder()) {
			Message getGroupNumberOrder = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_USERS_IN_ORDER_GRUOP);
			getGroupNumberOrder.getMsgArrayL().add("SELECT userID FROM user WHERE isGroupOrder = '1';");
			userViewController.getClientController().handleMessageFromClientUI(getGroupNumberOrder);

			while (!userViewController.getClientController().getMessageController()
					.getE_GET_USERS_IN_ORDER_GRUOP_MsgProccessed()) {
				System.out.println("waiting on E_GET_USERS_IN_ORDER_GRUOP...");
				try {
					Thread.currentThread().sleep(1); // Eli - Check if we can use sleep
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			userViewController.getClientController().getMessageController()
					.setE_GET_USERS_IN_ORDER_GRUOP_MsgProccessed(false);

			int sizeOfGroup = userViewController.getOrderController().getNumberOfGroupOrder();

			sharedOrderLabel.setText("Shared order with " + (sizeOfGroup) + " users.");

			switch (sizeOfGroup) {
			case 1:
				deliveryPrice = 25;
				break;
			case 2:
				deliveryPrice = 20;
				break;
			default:
				deliveryPrice = 15;
				break;
			}
		}
		deliveryPriceLabel.setText("Delivery Price: " + deliveryPrice);
		// double orderWithDelivery = order.getTotalPrice() + deliveryPrice;
		double priceWithEarlyOrder = 0;
		if (order.getEarlyOrder() == 1) {
			priceWithEarlyOrder = (order.getTotalPrice() + deliveryPrice)
					- ((order.getTotalPrice() + deliveryPrice) / 10);
		} else {
			priceWithEarlyOrder = order.getTotalPrice() + deliveryPrice;
		}

		orderWithDelivery = priceWithEarlyOrder;

		userHeaderNameLabel.setText(user.getPrivateName() + " " + user.getLastName());
		orderTotalLabel.setText("Order Summary: " + df.format(order.getTotalPrice()));

		totalLabel.setText("Total: " + df.format(orderWithDelivery) + earlyOrder);
		totalTextField.setText("Total: " + df.format(orderWithDelivery));

		creditAmntAvailable = userViewController.getUserController().checkCreditForRest(
				userViewController.getRestaurantController().getChosenRestaurant().getRestaurantID());
		useCreditsCheckBox.setText("Use Credits: (" + df.format(creditAmntAvailable) + ")");

		if (order.getTotalPrice() + deliveryPrice >= creditAmntAvailable) {
			totalWithCredits = order.getTotalPrice() + deliveryPrice - creditAmntAvailable;
			creditAmntAvailable = 0;
		} else {
			creditAmntAvailable -= order.getTotalPrice() + deliveryPrice;
			totalWithCredits = 0;
		}
		final String temp = earlyOrder;
		useCreditsCheckBox.setOnAction(e -> {
			if (!chargeEmployerRadioButton.isSelected()) {
				if (useCreditsCheckBox.isSelected()) {
					totalLabel.setText("Total: " + df.format(totalWithCredits) + temp);
				}
				if (!useCreditsCheckBox.isSelected()) {
					totalLabel.setText("Total: " + df.format(orderWithDelivery) + temp);
				}
			} else {
				useCreditsCheckBox.setSelected(false);
			}
		});
		String last3Digits = user.getCardNumber().substring(user.getCardNumber().length() - 3,
				user.getCardNumber().length());
		chargeAccountRadioBtn.setText("Charge Account(**" + last3Digits + ")");
		chargeAccountRadioBtn.setOnAction(e -> {
			employerName.setEditable(false);
			employerW4C.setEditable(false);
		});

		chargeEmployerRadioButton.setOnAction(e -> {
			employerName.setEditable(true);
			employerW4C.setEditable(true);
			useCreditsCheckBox.setSelected(false);
			totalLabel.setText("Total: " + df.format(orderWithDelivery));
		});

	}

}
