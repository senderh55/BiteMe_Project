package newOrder;

import java.util.ArrayList;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import restaurant.Restaurant;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.E_DeliveryType;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the ChooseRestaurantWindow.
 */
public class ChooseRestaurantForm implements IScreenController {
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The confirm stage text. */
	@FXML
	private Text confirmStageText;

	/** The dishes stage text. */
	@FXML
	private Text dishesStageText;

	/** The header label. */
	@FXML
	private Text headerLabel;

	/** The ident stage text. */
	@FXML
	private Text identStageText;

	/** The order stage anchor pane. */
//	@FXML
//	private AnchorPane orderStageAnchorPane;

	/** The order type stage text. */
	@FXML
	private Text orderTypeStageText;

	/** The payment stage text. */
	@FXML
	private Text paymentStageText;

	/** The restaurant stage text. */
	@FXML
	private Text restStageText;

	/** The summary stage text. */
	@FXML
	private Text summaryStageText;

	/** The restaurant vbox - to show the relevant restaurant list. */
	@FXML
	private VBox restVbox;
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
	 * @param event - an ActionEvent from the ChooseRestaurantWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}
	
	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the ChooseRestaurantWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		if (userViewController.getOrderController().getOrder().getDeliveryType().equals(E_DeliveryType.E_PICKUP)) {
			Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
			updateUserInfo.getMsgArrayL().add("UPDATE user SET isGroupOrder = '0' WHERE userID = '"
					+ userViewController.getUserController().getUser().getUserID() + "';");
			userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);
			userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DELIVERY_TYPE);
		} else {
			Message updateUserInfo = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
			updateUserInfo.getMsgArrayL().add("UPDATE user SET isGroupOrder = '0' WHERE userID = '"
					+ userViewController.getUserController().getUser().getUserID() + "';");
			userViewController.getClientController().handleMessageFromClientUI(updateUserInfo);
			userViewController.getScreenManager().setScreen(MyScreenEnum.DELIVERY_INFO);
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
	 * Sets fields and functionality in the ChooseRestaurantWindow.
	 */
	@Override
	public void setParameters() {
		getRestaurants();
		while (!userViewController.getClientController().getMessageController().getE_REST_INFO_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_REST_INFO...");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_REST_INFO_MsgProccessed(false);

		buildRestHBox();

	}

	/**
	 * Gets the restaurants info from the server (DB) and saves it in userViewController.
	 */
	public void getRestaurants() {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_REST_INFO);
		message.addToList("SELECT * FROM restaurant");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}

	/**
	 * Builds the restaurants list in the screen.
	 */
	public void buildRestHBox() {
		ArrayList<Restaurant> restList = userViewController.getRestaurantController().getRestaurantList();
		User user = userViewController.getUserController().getUser();

		if (restList != null) {

//			if (userViewController.getRestaurantController().getChosenRestaurant() != null) {
//				Restaurant chosenRestFromGroupOrder = userViewController.getRestaurantController()
//						.getChosenRestaurant();
//				for (Restaurant rest : restList) {
//					if (chosenRestFromGroupOrder.getRestaurantID() == rest.getRestaurantID()) {
//						userViewController.getRestaurantController().setChosenRestaurant(rest);
//					}
//				}
//			} else {
			for (Restaurant rest : restList) {
				if (rest.getBmBranch().equals(user.getTempBranch())) {
					HBox restRow = new HBox();
					restRow.setSpacing(25);
					restRow.setStyle("-fx-background-color white;");
					restRow.setStyle("-fx-padding 5;");
					restRow.setStyle("-fx-border-width 1;");
					restRow.setStyle("-fx-border-color black;");
					restRow.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);"
							+ "-fx-background-color: white;");
					restRow.setOnMouseClicked(eventMouseClicked -> {
						userViewController.getRestaurantController().setChosenRestaurant(rest);

						userViewController.getScreenManager().loadScreen(MyScreenEnum.CHOOSE_DISHES, ClientUI.class,
								MyScreenEnum.CHOOSE_DISHES.getFXMLName(), userViewController);
						userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DISHES);
					});
					restRow.setOnMouseEntered(eventMouseEntered -> {
						((HBox) eventMouseEntered.getSource()).scaleXProperty().set(1.01);
						((HBox) eventMouseEntered.getSource()).scaleYProperty().set(1.01);
						((HBox) eventMouseEntered.getSource()).scaleZProperty().set(1.01);
					});
					restRow.setOnMouseExited(eventMouseExit -> {
						((HBox) eventMouseExit.getSource()).scaleXProperty().set(1);
						((HBox) eventMouseExit.getSource()).scaleYProperty().set(1);
						((HBox) eventMouseExit.getSource()).scaleZProperty().set(1);
					});
					// Image restLogo = new Image("test");
					// ImageView iv = new ImageView(restLogo);
					StackPane imageHolder = new StackPane();
					imageHolder.setPrefSize(117, 62);
					imageHolder.setMinSize(117, 62);
					imageHolder.setMaxSize(117, 62);
					// imageHolder.getChildren().add(iv);
					// ImageView imageView = new ImageView(); ADD SAVING IMAGES TO DB AND
					// IMPLEMENTING THEM HERE
					Text restName = new Text(rest.getName());
					restName.setWrappingWidth(200);
					restName.setFont(Font.font("System", FontWeight.BOLD, 20));
					VBox restInfo = new VBox();
					restInfo.setPadding(new Insets(0, 0, 0, 50));
					Text phoneNum = new Text("Phone Number: " + rest.getPhoneNumber());
					Text description = new Text("Description: " + rest.getDescription());

					Text address = new Text("Address: " + rest.getAddress());
					restInfo.getChildren().addAll(phoneNum, description, address);

					restRow.getChildren().addAll(imageHolder, restName, restInfo);
					restRow.setAlignment(Pos.CENTER_LEFT);
					restInfo.setAlignment(Pos.CENTER_LEFT);
					restVbox.getChildren().add(restRow);
				}
			}
//			}
		} else {
			System.out.println("No restaurants were found");
		}

	}

}
