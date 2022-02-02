package newOrder;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.security.ntlm.Client;

import client.ClientUI;
import dish.Dish;
import dish.DishInOrder;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_DishCategory;
import utils.E_DishSize;
import utils.E_LevelOfDoneness;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the ChooseDishWindow.
 */
public class ChooseDishForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The dish amount. */
	int dishAmnt = 1;

	/** The temp dish price by amount. */
	double tempDishPriceByAmnt = 0;

	/** The price per dish. */
	double pricePerDish;

	/** The dish name. */
	String dishName;

	/** The add new dish to menu button. */
	@FXML
	private Button addNewDishToMenuButton;

	/** The added dish status label. */
	@FXML
	private Text addedDishStatusLabel;

	/** The appetizer tab. */
	@FXML
	private Tab appetizerTab;

	/** The appetizer tab vbox - to show the appetizer tab. */
	@FXML
	private VBox appetizerTab_vbox;

	/** The at main anchor pane - to show all the relevant dishes. */
	@FXML
	private AnchorPane at_MainAnchorPane;

	/** The at anchor pane - to show all the relevant dishes. */
	@FXML
	private AnchorPane at_anchorPane;

	/** The at hbox - to show all the relevant dishes. */
	@FXML
	private HBox at_hbox;

	/** The at scroll pane - to show all the relevant dishes. */
	@FXML
	private ScrollPane at_scrollPane;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The confirm stage text. */
	@FXML
	private Text confirmStageText;

	/** The dessert tab. */
	@FXML
	private Tab dessertTab;

	/** The dessert tab vbox - to show the dessert tab. */
	@FXML
	private VBox dessertTab_vbox;

	/** The dish description text area. */
	@FXML
	private TextArea dishDescriptionTextArea;

	/** The dish image. */
	@FXML
	private ImageView dishImage;

	/** The dish name text. */
	@FXML
	private Text dishNameText;

	/** The dish prices text. */
	@FXML
	private Text dishPricesText;

	/** The dish sizes text. */
	@FXML
	private Text dishSizesText;

	/** The dishes stage text. */
	@FXML
	private Text dishesStageText;

	/** The drink tab vbox - to show the drink tab. */
	@FXML
	private VBox drinkTab_vbox;

	/** The drinks tab. */
	@FXML
	private Tab drinksTab;

	/** The ident stage text. */
	@FXML
	private Text identStageText;

	/** The main tab. */
	@FXML
	private Tab mainTab;

	/** The main tab vbox - to show the main tab. */
	@FXML
	private VBox mainTab_vbox;

	/** The menu tab pane. */
	@FXML
	private TabPane menuTabPane;

	/** The order stage anchor pane. */
	@FXML
	private AnchorPane orderStageAnchorPane;

	/** The order type stage text. */
	@FXML
	private Text orderTypeStageText;

	/** The payment stage text. */
	@FXML
	private Text paymentStageText;

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

	@FXML
	private Text updateMenuLabel;

	/** The salad tab. */
	@FXML
	private Tab saladTab;

	/** The salad tab vbox - to show the salad tab. */
	@FXML
	private VBox saladTab_vbox;

	/** The soup tab. */
	@FXML
	private Tab soupTab;

	/** The soup tab vbox - to show the soup tab. */
	@FXML
	private VBox soupTab_vbox;

	/** The summary button. */
	@FXML
	private Button summaryButton;

	/** The summary stage text. */
	@FXML
	private Text summaryStageText;

	/** The total text field. */
	@FXML
	private TextField totalTextField;

	/**
	 * Logout from account.
	 *
	 * @param event - an ActionEvent from the ChooseDishWindow
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
	 * Open to home page.
	 *
	 * @param event - an ActionEvent from the ChooseDishWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the ChooseDishWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		// appetizerTab_vbox.getChildren().clear();
		if (userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_RESTAURANT_USER)) {
			userViewController.getRestaurantController().setStateMenuUpdate(false);
			userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ENTRANCE);

		} else {
			userViewController.getOrderController().resetOrder();
			userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_RESTAURANT);
		}
	}

	/**
	 * Go to the order Summary window.
	 *
	 * @param event - an ActionEvent from the ChooseDishWindow
	 */
	@FXML
	void summaryWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.ORDER_SUMMARY, ClientUI.class,
				MyScreenEnum.ORDER_SUMMARY.getFXMLName(), userViewController);
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
	 * Sets fields and functionality in the ChooseDishWindow.
	 */
	@Override
	public void setParameters() {
		DecimalFormat df = new DecimalFormat();
		totalTextField
				.setText("Total: " + df.format(userViewController.getOrderController().getOrder().getTotalPrice()));
		getRestaurantDishes();
		while (!userViewController.getClientController().getMessageController().getE_GET_MENU_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_GET_MENU...");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_GET_MENU_MsgProccessed(false);
		buildMenu();
		if (userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_RESTAURANT_USER)) {
			getRestName();
			restNameText.setText(userViewController.getRestaurantController().getRestaurantList().get(0).getName());

		} else {
			restNameText.setText(userViewController.getRestaurantController().getChosenRestaurant().getName());
		}
	}

	/**
	 * Gets the restaurant dishes from the server (DB) and saves it in userViewController.
	 */
	public void getRestaurantDishes() {
		if (userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_RESTAURANT_USER)) {
			Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_MENU);
			message.addToList("SELECT * FROM dishes WHERE restID = '"
					+ userViewController.getUserController().getUser().getManageRestID() + "'");
			userViewController.getClientController().handleMessageFromClientUI(message);
		} else {
			Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_MENU);
			message.addToList("SELECT * FROM dishes WHERE restID = '"
					+ userViewController.getRestaurantController().getChosenRestaurant().getRestaurantID() + "'");
			userViewController.getClientController().handleMessageFromClientUI(message);
		}
	}

	/**
	 * Builds the menu (list of dishes) on the screen.
	 */
	public void buildMenu() {
		ArrayList<Dish> menuList = userViewController.getDishController().getMenuList();
		DecimalFormat df = new DecimalFormat();

		if (!userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_RESTAURANT_USER)) {
			addNewDishToMenuButton.setVisible(false);
		} else {
			summaryButton.setVisible(false);
			for (Node node : orderStageAnchorPane.getChildren())
				node.setVisible(false);
			// updateMenuLabel.setVisible(true);
		}

		addNewDishToMenuButton.setOnAction(e -> {
			userViewController.getDishController().setUpdate(false);
			userViewController.getScreenManager().loadScreen(MyScreenEnum.RESTAURANT_UPDATE_DISH, ClientUI.class,
					MyScreenEnum.RESTAURANT_UPDATE_DISH.getFXMLName(), userViewController);
			userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_UPDATE_DISH);
		});

		if (menuList != null) {
			for (Dish dish : menuList) {

				VBox dishHolder = new VBox();
				HBox dishRow = new HBox();

				dishRow.setOnMouseClicked(e -> {
					if (dishHolder.getChildren().size() == 1) {

						userViewController.getDishController().setChosenDish(dish);
						Pane dishDetailsOptionsPane = new Pane();
						dishDetailsOptionsPane.setMaxSize(640, 300);
						dishDetailsOptionsPane.setMinSize(640, 300);
						
						Label dishOptionsLabel = new Label("Dish Options");
						dishOptionsLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
						dishOptionsLabel.setLayoutX(260);
						dishOptionsLabel.setLayoutY(6);

						Label chooseIngredientsLabel = new Label("Choose Ingredients:");
						chooseIngredientsLabel.setFont(Font.font("System", 16));
						chooseIngredientsLabel.setUnderline(true);
						chooseIngredientsLabel.setLayoutX(14);
						chooseIngredientsLabel.setLayoutY(49);

						GridPane dishOptionsGridPane = new GridPane();
						dishOptionsGridPane.setLayoutX(14);
						dishOptionsGridPane.setLayoutY(82);
						dishOptionsGridPane.setMaxSize(348, 167);
						dishOptionsGridPane.setMinSize(348, 94);
						dishOptionsGridPane.setPrefSize(348, 155);
						dishOptionsGridPane.setPadding(new Insets(10, 5, 5, 5));

						VBox gridPaneBackgroundVBox = new VBox();
						gridPaneBackgroundVBox.setStyle("-fx-background-color: peachpuff;"
								+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");
						gridPaneBackgroundVBox.setPrefSize(348, 155);
						gridPaneBackgroundVBox.setMaxSize(348, 155);
						gridPaneBackgroundVBox.setLayoutX(14);
						gridPaneBackgroundVBox.setLayoutY(82);
						gridPaneBackgroundVBox.setAlignment(Pos.CENTER);

						gridPaneBackgroundVBox.getChildren().add(dishOptionsGridPane);

						Label donenessLabel = new Label("Doneness:");
						donenessLabel.setFont(Font.font("System", 12));
						donenessLabel.setLayoutX(399);
						donenessLabel.setLayoutY(85);

						MenuButton dishDonenessMenu = new MenuButton();
						dishDonenessMenu.setPrefSize(137, 25);
						dishDonenessMenu.setLayoutX(471);
						dishDonenessMenu.setLayoutY(82);
						dishDonenessMenu.setText("Choose Doneness");

						Label sizeLabel = new Label("Size:");
						sizeLabel.setFont(Font.font("System", 12));
						sizeLabel.setLayoutX(399);
						sizeLabel.setLayoutY(102);

						MenuButton dishSizesMenu = new MenuButton();
						dishSizesMenu.setPrefSize(137, 25);
						dishSizesMenu.setLayoutX(471);
						dishSizesMenu.setLayoutY(99);

						Text priceDish = new Text();// asaf
						priceDish.setFont(Font.font("System", FontWeight.BOLD, 14));
						priceDish.setText("Price:	  " + df.format(Double.parseDouble(dish.getDishPrices().get(0))));
						priceDish.setLayoutX(399);
						priceDish.setLayoutY(206);

						Label amountLabel = new Label("Amount:");
						amountLabel.setFont(Font.font("System", 12));
						amountLabel.setLayoutX(399);
						amountLabel.setLayoutY(147);

						TextField amountTextField = new TextField();
						amountTextField.setAlignment(Pos.CENTER);
						amountTextField.setEditable(false);
						amountTextField.setPrefSize(54, 25);
						amountTextField.setText("1");
						amountTextField.setLayoutX(510);
						amountTextField.setLayoutY(143);

						tempDishPriceByAmnt = Double.parseDouble(dish.getDishPrices().get(0));
						dishAmnt = Integer.parseInt(amountTextField.getText());

						Button subtractButton = new Button();
						subtractButton.setText("-");
						subtractButton.setLayoutX(470);
						subtractButton.setLayoutY(143);
						subtractButton.setOnAction(eventSubButton -> {
							if (dishAmnt > 1) {
								amountTextField.setText(--dishAmnt + "");
								tempDishPriceByAmnt -= pricePerDish;
								priceDish.setText("Price:	  " + df.format(tempDishPriceByAmnt));
							}
						});

						Button addButton = new Button();
						addButton.setText("+");
						addButton.setLayoutX(582);
						addButton.setLayoutY(143);
						addButton.setOnAction(eventAddButton -> {
							amountTextField.setText(++dishAmnt + "");
							tempDishPriceByAmnt += pricePerDish;
							priceDish.setText("Price:	  " + df.format(tempDishPriceByAmnt));
						});


						Button addOrderButton = new Button();
						addOrderButton.setPrefSize(174, 33);
						addOrderButton.setText("Add to Order");
						addOrderButton.setLayoutX(447);
						addOrderButton.setLayoutY(253);
						addOrderButton.setStyle("-fx-background-color: MediumSeaGreen;" + "-fx-font-weight: bold;");
						DropShadow shadow = new DropShadow();
						shadow.setRadius(5.0);
						addOrderButton.setEffect(shadow);
						addOrderButton.setOnAction(eventAddToOrder -> {
							DishInOrder tempDishInOrder = userViewController.getDishController().getDishInOrder();
							tempDishInOrder.setDish(dish);
							if (dish.getIsGrill()) {
								if (dishDonenessMenu.getText().equals("Choose Doneness")) {
									addedDishStatusLabel.setText("Must choose a doneness level.");
									hoverText(addedDishStatusLabel, 4);
									return;
								}
								tempDishInOrder.setDonenessLevel(E_LevelOfDoneness.getEnum(dishDonenessMenu.getText()));
							} else {
								tempDishInOrder.setDonenessLevel(E_LevelOfDoneness.E_NA);
							}

							tempDishInOrder.setAmountOfDish(dishAmnt);
							tempDishInOrder.setPriceOfDishBySize(pricePerDish);
							tempDishInOrder.setDishSize(E_DishSize.getEnum(dishSizesMenu.getText()));
							if (userViewController.getOrderController().addToOrderList(tempDishInOrder)) {
								addedDishStatusLabel.setText("Dish Added!");

							} else {
								addedDishStatusLabel.setText("Dish not added.");
							}
							hoverText(addedDishStatusLabel, 4);

							resetDishOptions(dish, dishOptionsGridPane, dishSizesMenu, priceDish, amountTextField);
							userViewController.getDishController().setDishInOrder(new DishInOrder());
							totalTextField.setText("Total: "
									+ df.format(userViewController.getOrderController().getOrder().getTotalPrice()));

						});

						Button closeButton = new Button();
						closeButton.setPrefSize(100, 33);
						closeButton.setText("Close dish");
						closeButton.setLayoutX(14);
						closeButton.setLayoutY(260);
						closeButton.setStyle("-fx-background-color: pink;" + "-fx-font-weight: bold;");
						shadow = new DropShadow();
						shadow.setRadius(5.0);
						closeButton.setEffect(shadow);
						closeButton.setOnAction(eventCloseButton -> {
							amountTextField.setText("1");
							dishHolder.getChildren().remove(1);
						});

						if (dish.getDishSizes().size() > 1) {
							for (E_DishSize sizes : dish.getDishSizes()) {
								MenuItem menuItem = new MenuItem(sizes.getStringName());
								menuItem.setOnAction(eventMenuSizes -> {
									pricePerDish = Double
											.parseDouble(dish.getDishPrices().get(dish.getDishSizes().indexOf(sizes)));
									dishSizesMenu.setText(sizes.getStringName());
									priceDish.setText("Price:	  " + df.format(pricePerDish));
									amountTextField.setText("1");
									tempDishPriceByAmnt = pricePerDish;
									dishAmnt = 1;
								});
								dishSizesMenu.getItems().add(menuItem);
							}
						} else {
							dishSizesMenu.setVisible(false);
							sizeLabel.setVisible(false);

						}
						pricePerDish = Double.parseDouble(dish.getDishPrices().get(0));
						dishSizesMenu.setText(dish.getDishSizes().get(0).getStringName());

						amountTextField.setText(dishAmnt + "");

						if (dish.getIsGrill()) {
							E_LevelOfDoneness[] donenessValues = E_LevelOfDoneness.class.getEnumConstants();

							for (E_LevelOfDoneness donenessLevel : donenessValues) {
								if (!donenessLevel.getDonenessString().equals("")) {
									MenuItem menuItem = new MenuItem(donenessLevel.getDonenessString());
									menuItem.setOnAction(eventMenuDoneness -> {
										userViewController.getDishController().getDishInOrder()
												.setDonenessLevel(donenessLevel);
										dishDonenessMenu.setText(donenessLevel.getDonenessString());
									});
									dishDonenessMenu.getItems().add(menuItem);
								}
							}
						} else {
							dishDonenessMenu.setVisible(false);
							donenessLabel.setVisible(false);

						}

						int columnCounter = -1;
						for (int i = 0; i < dish.getRemovableOptions().size(); i++) {

							if (i % 3 == 0) {
								columnCounter++;
							}
							if (!dish.getRemovableOptions().get(i).equals("NA")) {

								CheckBox checkBox = new CheckBox();
								checkBox.setSelected(true);
								checkBox.setText(dish.getRemovableOptions().get(i));
								dishOptionsGridPane.addRow(columnCounter, checkBox);
								dishOptionsGridPane.setMargin(checkBox, new Insets(10, 5, 5, 5));
								checkBox.setOnAction(eventCheckbox -> {
									if (!checkBox.isSelected()) {
										userViewController.getDishController().getDishInOrder()
												.addOptionToRemove(checkBox.getText());
									} else {
										if (!userViewController.getDishController().getDishInOrder()
												.deleteFromOptionToRemove(checkBox.getText())) {
											System.out.println(
													"Option " + checkBox.getText() + " was not in the remove list.");
										}
									}
								});

							}
						}

						Button updateDishButton = new Button();
						updateDishButton.setPrefSize(100, 33);
						updateDishButton.setText("Update dish");
						updateDishButton.setLayoutX(290);
						updateDishButton.setLayoutY(260);
						updateDishButton.setStyle("-fx-background-color : DARKSEAGREEN;" + "-fx-font-weight: bold;");
						shadow = new DropShadow();
						shadow.setRadius(5.0);
						updateDishButton.setEffect(shadow);
						updateDishButton.setOnAction(evenEditButton -> {
							userViewController.getDishController().setUpdate(true);
							userViewController.getScreenManager().loadScreen(MyScreenEnum.RESTAURANT_UPDATE_DISH,
									ClientUI.class, MyScreenEnum.RESTAURANT_UPDATE_DISH.getFXMLName(),
									userViewController);

							userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_UPDATE_DISH);
						});

						Button deleteDishButton = new Button();
						deleteDishButton.setPrefSize(100, 33);
						deleteDishButton.setText("Delete dish");
						deleteDishButton.setLayoutX(540);
						deleteDishButton.setLayoutY(260);
						deleteDishButton.setStyle("-fx-background-color : tomato;" + "-fx-font-weight: bold;");
						shadow = new DropShadow();
						shadow.setRadius(5.0);
						deleteDishButton.setEffect(shadow);
						deleteDishButton.setOnAction(evenEditButton -> {

							Message deleteDish = new Message(OwnerEnum.E_CLIENT, OpEnum.E_DELETE_DISH);
							deleteDish.getMsgArrayL()
									.add("DELETE FROM biteme_db.dishes WHERE restID = '"
											+ userViewController.getRestaurantController().getChosenRestaurant()
													.getRestaurantID()
											+ "' AND dishName = '" + dish.getDishName() + "';");
							userViewController.getClientController().handleMessageFromClientUI(deleteDish);

							userViewController.getScreenManager().loadScreen(MyScreenEnum.CHOOSE_DISHES, ClientUI.class,
									MyScreenEnum.CHOOSE_DISHES.getFXMLName(), userViewController);

							userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DISHES);
						});

						if (!userViewController.getUserController().getUser().getUserType()
								.equals(E_UserTypeEnum.E_RESTAURANT_USER)) {
							updateDishButton.setVisible(false);
							deleteDishButton.setVisible(false);
						}

						dishDetailsOptionsPane.getChildren().addAll(dishOptionsLabel, chooseIngredientsLabel,
								gridPaneBackgroundVBox, donenessLabel, dishDonenessMenu, sizeLabel, dishSizesMenu,
								updateDishButton, deleteDishButton, amountLabel, subtractButton, addButton,
								amountTextField, priceDish, closeButton);

						if (!userViewController.getRestaurantController().getStateMenuUpdate()) {
							dishDetailsOptionsPane.getChildren().addAll(addOrderButton);
						}

						dishHolder.getChildren().add(dishDetailsOptionsPane);

					} else {
						dishHolder.getChildren().remove(1);
					}

				});

				dishRow.setStyle("-fx-background-color white;");
				dishRow.setStyle("-fx-padding: 5;" + "-fx-spacing: 10;");
				dishRow.setStyle("-fx-border-width: 1;");
				dishRow.setStyle("-fx-border-color: black;");
				dishRow.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);"
						+ "-fx-background-color: white;");
				dishRow.setPrefSize(640, 70);
				dishRow.setMaxSize(640, 70);
				dishRow.setMinSize(640, 70);

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
				// Image restLogo = new Image("test");
				// ImageView iv = new ImageView(restLogo);
				StackPane imageHolder = new StackPane();
				imageHolder.setPrefSize(99, 60);
				imageHolder.setMinSize(99, 60);
				imageHolder.setMaxSize(99, 60);
				imageHolder.setPadding(new Insets(0, 5, 0, 15));
				// imageHolder.getChildren().add(iv);
				// ImageView imageView = new ImageView(); ADD SAVING IMAGES TO DB AND
				// IMPLEMENTING THEM HERE
				Text dishName = new Text(dish.getDishName());
				dishName.setWrappingWidth(275);
				dishName.setFont(Font.font("System", 16));

				VBox dishInfo = new VBox();
				dishInfo.setPadding(new Insets(0, 0, 0, 10));
				dishInfo.setPrefSize(250, 60);
				dishInfo.setMaxSize(250, 60);
				dishInfo.setMinSize(250, 60);
				StringBuilder dishSize = new StringBuilder();
				for (int i = 0; i < dish.getDishSizes().size(); i++) {
					dishSize.append(dish.getDishSizes().get(i).getStringName());
					if (i != dish.getDishSizes().size() - 1) {
						dishSize.append(" / ");
					}
				}
				Text dishSizes = new Text(dishSize.toString());
				dishSizes.setFont(Font.font("System", 11));
				StringBuilder dishPrices = new StringBuilder();
				for (int i = 0; i < dish.getDishPrices().size(); i++) {
					dishPrices.append(dish.getDishPrices().get(i));
					if (i != dish.getDishPrices().size() - 1) {
						dishPrices.append(" / ");
					}
				}

				Text prices = new Text(dishPrices.toString());
				prices.setFont(Font.font("System", 11));
				dishInfo.getChildren().addAll(dishName, dishSizes, prices);

				TextArea dishDescriptionTextArea = new TextArea(dish.getDescription());
				dishDescriptionTextArea.setMaxSize(265, 60);
				dishDescriptionTextArea.setMinSize(265, 60);
				dishDescriptionTextArea.setWrapText(true);
				dishDescriptionTextArea.setFont(Font.font("System", 11));
				dishDescriptionTextArea.setStyle("-fx-border-color: white;");
				dishDescriptionTextArea.setEditable(false);

				dishRow.getChildren().addAll(imageHolder, dishInfo, dishDescriptionTextArea);
				dishRow.setAlignment(Pos.CENTER_LEFT);
				dishInfo.setAlignment(Pos.CENTER_LEFT);
				dishHolder.getChildren().add(dishRow);

				switch (dish.getCategory()) {
				case E_APPETIZER:
					appetizerTab_vbox.getChildren().add(dishHolder);
					break;
				case E_SALAD:
					saladTab_vbox.getChildren().add(dishHolder);
					break;
				case E_SOUP:
					soupTab_vbox.getChildren().add(dishHolder);
					break;
				case E_MAIN:
					mainTab_vbox.getChildren().add(dishHolder);
					break;
				case E_DESSERT:
					dessertTab_vbox.getChildren().add(dishHolder);
					break;
				case E_DRINKS:
					drinkTab_vbox.getChildren().add(dishHolder);
					break;
				}
			}
		}

	}

	/**
	 * Functionality when hovering over the dishes text.
	 *
	 * @param text - Text represents the text from the screen
	 * @param duration - int represents the duration of the action
	 */
	private void hoverText(Text text, int duration) {
		PauseTransition pt = new PauseTransition(Duration.seconds(duration));
		pt.setOnFinished(e -> {
			text.setText("");
		});
		pt.play();
	}

	/**
	 * Reset dish options.
	 *
	 * @param dish                - Dish represents a dish in the screen
	 * @param dishOptionsGridPane - GridPane contains the dish options grid pane
	 * @param dishSizesMenu       - MenuButton that shows the dish sizes
	 * @param priceDish           - Text represents the price of the dish
	 * @param amountTextField     - TextField represents the dish amount text field
	 */
	private void resetDishOptions(Dish dish, GridPane dishOptionsGridPane, MenuButton dishSizesMenu, Text priceDish,
			TextField amountTextField) {
		for (Node checkBoxNode : dishOptionsGridPane.getChildren())
			((CheckBox) checkBoxNode).setSelected(true);
		dishSizesMenu.setText(dish.getDishSizes().get(0).getStringName());
		pricePerDish = Double.parseDouble(dish.getDishPrices().get(0));
		priceDish.setText("Price:	  " + pricePerDish);
		amountTextField.setText("1");
		tempDishPriceByAmnt = pricePerDish;
		dishAmnt = 1;
	}

	/**
	 * Gets the rest name from the server (DB) into the userViewController.
	 */
	private void getRestName() {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_REST_INFO);
		message.addToList("SELECT * FROM restaurant WHERE restaurantID = " + "'"
				+ userViewController.getUserController().getUser().getManageRestID() + "'");
		userViewController.getClientController().handleMessageFromClientUI(message);

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
	}
}
