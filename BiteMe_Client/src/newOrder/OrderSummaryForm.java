package newOrder;

import java.text.DecimalFormat;
import java.util.ArrayList;

import client.ClientUI;
import dish.DishInOrder;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the OrderSummaryForm.
 */
public class OrderSummaryForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The added dish status label. */
	@FXML
	private Text addedDishStatusLabel;

	/** The at hbox - to show all the dishes that are in the order. */
	@FXML
	private HBox at_hbox;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The confirm stage text. */
	@FXML
	private Text confirmStageText;

	/** The dishes stage text. */
	@FXML
	private Text dishesStageText;

	/** The dishes summary V box. */
	@FXML
	private VBox dishesSummaryVBox;

	/** The ident stage text. */
	@FXML
	private Text identStageText;

	/** The oder stage anchor pane - to show all the dishes that are in the order. */
	@FXML
	private AnchorPane oderStageAnchorPane;

	/** The order type stage text. */
	@FXML
	private Text orderTypeStageText;

	/** The payment button. */
	@FXML
	private Button paymentButton;

	/** The payment stage text. */
	@FXML
	private Text paymentStageText;

	/** The error text. */
	@FXML
	private Label errorLabel;

	/** The rest details pane. */
	@FXML
	private Pane restDetailsPane;

	/** The rest image view. */
	@FXML
	private ImageView restImageView;

	/** The rest name text. */
	@FXML
	private Text restNameText;

	/** The rest stage text. */
	@FXML
	private Text restStageText;

	/** The summary stage text. */
	@FXML
	private Text summaryStageText;

	/** The total text field. */
	@FXML
	private TextField totalTextField;
	
	/**
	 * Logout from account.
	 *
	 * @param event - an ActionEvent from the OrderSummaryForm
	 */
	@FXML
	void logout(ActionEvent event) {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userViewController.getUserController().getUser().getUserID() + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
	}

	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the OrderSummaryForm
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.CHOOSE_DISHES, ClientUI.class,
				MyScreenEnum.CHOOSE_DISHES.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DISHES);
	}

	/**
	 * Go to the Payment window.
	 *
	 * @param event - an ActionEvent from the OrderSummaryForm
	 */
	@FXML
	void paymentWindow(ActionEvent event) {
		if (userViewController.getOrderController().getOrder().getTotalPrice() > 0) {
			userViewController.getScreenManager().loadScreen(MyScreenEnum.ORDER_PAYMENT, ClientUI.class,
					MyScreenEnum.ORDER_PAYMENT.getFXMLName(), userViewController);
			userViewController.getScreenManager().setScreen(MyScreenEnum.ORDER_PAYMENT);
		} else {
			errorLabel.setText("Please choose dishes");
			errorLabel.setTextFill(Color.RED);
			errorLabel.setStyle("-fx-background-color: #FFE5CC");
			errorLabel.setVisible(true);
			PauseTransition pt = new PauseTransition(Duration.seconds(2.5));
			pt.setOnFinished(errorEvent -> {
				errorLabel.setVisible(false);
			});
			pt.play();
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
	 * Sets fields and functionality in the OrderSummaryWindow.
	 */
	@Override
	public void setParameters() {
		buildSummary();

	}

	/**
	 * Builds the order summary page.
	 */
	private void buildSummary() {

		ArrayList<DishInOrder> orderList = userViewController.getOrderController().getOrderList();

		restNameText.setText(userViewController.getRestaurantController().getChosenRestaurant().getName());

		DecimalFormat df = new DecimalFormat();
		for (DishInOrder dish : orderList) {
			HBox dishRow = new HBox();
			dishRow.setStyle("-fx-background-color white;");
			dishRow.setStyle("-fx-padding: 5;" + "-fx-spacing: 10;");
			dishRow.setStyle("-fx-border-width: 1;");
			dishRow.setStyle("-fx-border-color: black;");
			dishRow.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);"
					+ "-fx-background-color: white;");
			dishRow.setPrefSize(678, 70);
			dishRow.setMaxSize(678, 70);
			dishRow.setMinSize(678, 70);

			StackPane imageHolder = new StackPane();
			imageHolder.setPrefSize(99, 60);
			imageHolder.setMinSize(99, 60);
			imageHolder.setMaxSize(99, 60);
			imageHolder.setPadding(new Insets(0, 5, 0, 15));
			// imageHolder.getChildren().add(iv);
			// ImageView imageView = new ImageView(); ADD SAVING IMAGES TO DB AND
			// IMPLEMENTING THEM HERE
			Text dishName = new Text(dish.getDishName());
			dishName.setWrappingWidth(135);
			dishName.setFont(Font.font("System", 16));

			VBox dishInfo = new VBox();
			dishInfo.setPadding(new Insets(0, 0, 0, 10));
			dishInfo.setPrefSize(154, 60);
			dishInfo.setMaxSize(154, 60);
			dishInfo.setMinSize(154, 60);

			Text dishSizes = new Text(dish.getDishSize().getStringName());
			dishSizes.setWrappingWidth(135);
			dishSizes.setFont(Font.font("System", 11));

			Text prices = new Text(dish.getPriceOfDishBySize() + "");
			prices.setWrappingWidth(135);
			prices.setFont(Font.font("System", 11));
			dishInfo.getChildren().addAll(dishName, dishSizes, prices);

			StringBuilder str = new StringBuilder();
			for (int i = 0; i < dish.getOptionToRemove().size(); i++) {
				if (i == 0) {
					str.append("Remove: ");
				}
				str.append(dish.getOptionToRemove().get(i));
				if (i != dish.getOptionToRemove().size() - 1)
					str.append(", ");
				else {
					str.append("\n");
				}
			}
			if (dish.getIsGrill())
				str.append("Doneness: " + dish.getDonenessLevel().getDonenessString());

			TextArea dishDetails = new TextArea(str.toString());

			dishDetails.setMaxSize(188, 60);
			dishDetails.setMinSize(188, 60);
			dishDetails.setWrapText(true);
			dishDetails.setFont(Font.font("System", 11));
			dishDetails.setStyle("-fx-border-color: white;");
			dishDetails.setEditable(false);

			VBox amountPriceInfo = new VBox();
			amountPriceInfo.setPadding(new Insets(0, 0, 0, 10));
			amountPriceInfo.setPrefSize(100, 60);
			amountPriceInfo.setMaxSize(100, 60);
			amountPriceInfo.setMinSize(100, 60);

			Text amountOfDish = new Text("Amount: " + dish.getAmountOfDish());
			amountOfDish.setWrappingWidth(100);
			amountOfDish.setFont(Font.font("System", 11));

			Text totalByAmount = new Text("Total: " + (dish.getAmountOfDish() * dish.getPriceOfDishBySize()));
			totalByAmount.setWrappingWidth(100);
			totalByAmount.setFont(Font.font("System", FontWeight.BOLD, 11));

			amountPriceInfo.getChildren().addAll(amountOfDish, totalByAmount);

			HBox dishDetailsUpdate = new HBox();
			dishDetailsUpdate.setAlignment(Pos.CENTER_LEFT);
			dishDetailsUpdate.setSpacing(5);

			Button subtractAmount = new Button();
			subtractAmount.setText("-");
			subtractAmount.setMaxSize(28, 28);
			subtractAmount.setMinSize(28, 28);
			subtractAmount.setStyle("-fx-border-color: #ff7f50;");
			subtractAmount.setStyle("-fx-background-radius: 7;");
			subtractAmount.setOnAction(e -> {
				if (dish.getAmountOfDish() > 1) {
					dish.setAmountOfDish(dish.getAmountOfDish() - 1);
					amountOfDish.setText("Amount: " + dish.getAmountOfDish());
					totalByAmount.setText("Total: " + df.format(dish.getAmountOfDish() * dish.getPriceOfDishBySize()));
				} else {
					orderList.remove(orderList.indexOf(dish));
					dishesSummaryVBox.getChildren().remove(dishRow);
				}
				userViewController.getOrderController().getOrder().addTotalPrice(-dish.getPriceOfDishBySize());
				totalTextField.setText(
						"Total: " + df.format(userViewController.getOrderController().getOrder().getTotalPrice()));

			});

			Button addAmount = new Button();
			addAmount.setText("+");
			addAmount.setMaxSize(28, 28);
			addAmount.setMinSize(28, 28);
			addAmount.setStyle("-fx-background-radius: 7;");
			addAmount.setOnAction(e -> {
				dish.setAmountOfDish(dish.getAmountOfDish() + 1);
				amountOfDish.setText("Amount: " + dish.getAmountOfDish());
				totalByAmount.setText("Total: " + df.format(dish.getAmountOfDish() * dish.getPriceOfDishBySize()));
				userViewController.getOrderController().getOrder().addTotalPrice(dish.getPriceOfDishBySize());
				totalTextField.setText(
						"Total: " + df.format(userViewController.getOrderController().getOrder().getTotalPrice()));

			});

			Button removeDish = new Button();
			removeDish.setText("Remove");
			removeDish.setMaxSize(60, 28);
			removeDish.setMinSize(60, 28);
			removeDish.setStyle("-fx-background-radius: 10;" + "-fx-background-color:  #EF9A9A;");
			removeDish.setOnAction(e -> {

				userViewController.getOrderController().getOrder()
						.addTotalPrice(-(dish.getPriceOfDishBySize() * dish.getAmountOfDish()));
				orderList.remove(orderList.indexOf(dish));
				dishesSummaryVBox.getChildren().remove(dishRow);
				totalTextField.setText(
						"Total: " + df.format(userViewController.getOrderController().getOrder().getTotalPrice()));
			});

			dishDetailsUpdate.getChildren().addAll(subtractAmount, addAmount, removeDish);

			dishRow.getChildren().addAll(imageHolder, dishInfo, dishDetails, amountPriceInfo, dishDetailsUpdate);
			amountPriceInfo.setAlignment(Pos.CENTER_LEFT);
			dishRow.setAlignment(Pos.CENTER_LEFT);
			dishInfo.setAlignment(Pos.CENTER_LEFT);
			dishesSummaryVBox.getChildren().add(dishRow);

			dishRow.setOnMouseEntered(e -> {
				((HBox) e.getSource()).scaleXProperty().set(1.01);
				((HBox) e.getSource()).scaleYProperty().set(1.01);
				((HBox) e.getSource()).scaleZProperty().set(1.01);
			});
			dishRow.setOnMouseExited(e -> {
				((HBox) e.getSource()).scaleXProperty().set(1);
				((HBox) e.getSource()).scaleYProperty().set(1);
				((HBox) e.getSource()).scaleZProperty().set(1);
			});
		}

		totalTextField.setText("Total: " + userViewController.getOrderController().getOrder().getTotalPrice());

	}

}
