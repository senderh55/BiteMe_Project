package restaurant;

import java.util.ArrayList;

import client.ClientUI;
import dish.Dish;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_AllergicCategories;
import utils.E_DishSize;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the RestaurantUpdateDishWindow
 */
public class RestaurantUpdateDishForm implements IScreenController {
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The Doneness level label. */
	@FXML
	private Text DonenessLevelLabel;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The dairy checkbox. */
	@FXML
	private CheckBox dairyCheckbox;

	/** The description text area. */
	@FXML
	private TextArea descriptionTextArea;

	/** The dish category menu button. */
	@FXML
	private MenuButton dishCategoryMenuButton;

	/** The dish name text field. */
	@FXML
	private TextField dishNameTextField;

	/** The eggs checkbox. */
	@FXML
	private CheckBox eggsCheckbox;

	/** The fish checkbox. */
	@FXML
	private CheckBox fishCheckbox;

	/** The ingredients textarea. */
	@FXML
	private TextArea ingredientsTextarea;

	/** The is grill checkbox. */
	@FXML
	private CheckBox isGrillCheckbox;

	/** The large checkbox. */
	@FXML
	private CheckBox largeCheckbox;

	/** The large price text field. */
	@FXML
	private TextField largePriceTextField;

	/** The logout button. */
	@FXML
	private Button logoutButton;

	/** The med price text field. */
	@FXML
	private TextField medPriceTextField;

	/** The medium checkbox. */
	@FXML
	private CheckBox mediumCheckbox;

	/** The nuts checkbox. */
	@FXML
	private CheckBox nutsCheckbox;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The removable ingredients text area. */
	@FXML
	private TextArea removableIngredientsTextArea;

	/** The shellfish checkbox. */
	@FXML
	private CheckBox shellfishCheckbox;

	/** The small checkbox. */
	@FXML
	private CheckBox smallCheckbox;

	/** The small price text field. */
	@FXML
	private TextField smallPriceTextField;

	/** The soy checkbox. */
	@FXML
	private CheckBox soyCheckbox;

	/** The update dish button. */
	@FXML
	private Button updateDishButton;

	/** The wheat checkbox. */
	@FXML
	private CheckBox wheatCheckbox;

	/** The Dish cat label. */
	@FXML
	private Text DishCatLabel;

	/** The dish edit text. */
	@FXML
	private Text dishEditText;

	/** The error label. */
	@FXML
	private Label errorLabel;

	/** The success msg. */
	@FXML
	private Label successMsg;

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the RestaurantUpdateDishWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.CHOOSE_DISHES, ClientUI.class,
				MyScreenEnum.CHOOSE_DISHES.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.CHOOSE_DISHES);
	}

	/**
	 * Logout of account.
	 *
	 * @param event - an ActionEvent from the RestaurantUpdateDishWindow
	 */
	@FXML
	void logoutOfAccount(ActionEvent event) {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userViewController.getUserController().getUser().getUserID() + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
	}

	/**
	 * Go to home page.
	 *
	 * @param event - an ActionEvent from the RestaurantUpdateDishWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();

	}

	/**
	 * Update dish data.
	 *
	 * @param event - an ActionEvent from the RestaurantUpdateDishWindow
	 */
	@FXML
	void updateDish(ActionEvent event) {
		Dish dish = userViewController.getDishController().getChosenDish();
		String category = "";
		String oldName = "";
		if (checkDishDetailsAreValid()) {
			StringBuilder newDishUpdate = new StringBuilder();
			if (userViewController.getDishController().getUpdate()) {
				oldName = userViewController.getDishController().getChosenDish().getDishName();
				newDishUpdate.append("UPDATE biteme_db.dishes SET dishName = '" + dishNameTextField.getText() + "', ");
			} else {

				newDishUpdate.append("INSERT INTO biteme_db.dishes SET restID = '"
						+ userViewController.getUserController().getUser().getManageRestID() + "', dishName = '"
						+ dishNameTextField.getText() + "', restName = '"
						+ userViewController.getRestaurantController().getChosenRestaurant().getName() + "', ");
			}

			StringBuilder newPrice = new StringBuilder();
			if (!smallPriceTextField.getText().equals(""))
				newPrice.append(smallPriceTextField.getText() + ",");
			if (!medPriceTextField.getText().equals(""))
				newPrice.append(medPriceTextField.getText() + ",");
			if (!largePriceTextField.getText().equals(""))
				newPrice.append(largePriceTextField.getText() + ",");
			newPrice.deleteCharAt(newPrice.length() - 1);

			newDishUpdate.append("price = '" + newPrice.toString() + "', ");

			StringBuilder newSizes = new StringBuilder();
			if (smallCheckbox.isSelected())
				newSizes.append("E_SMALL" + ",");
			if (mediumCheckbox.isSelected())
				newSizes.append("E_MED" + ",");
			if (largeCheckbox.isSelected())
				newSizes.append("E_LARGE" + ",");
			newSizes.deleteCharAt(newSizes.length() - 1);

			newDishUpdate.append("dishSizes = '" + newSizes + "', ");

			if (userViewController.getDishController().getUpdate()) {
				category = dish.getCategory().toString();
			} else {

				switch (dishCategoryMenuButton.getText()) {
				case "Appetizer":
					category = "E_APPETIZER";
					break;
				case "Salad":
					category = "E_SALAD";
					break;
				case "Soup":
					category = "E_SOUP";
					break;
				case "Main":
					category = "E_MAIN";
					break;
				case "Dessert":
					category = "E_DESSERT";
					break;
				case "Drinks":
					category = "E_DRINKS";
					break;
				}

			}

			newDishUpdate.append("category = '" + category + "', ");

			StringBuilder newAllergic = new StringBuilder();
			if (dairyCheckbox.isSelected())
				newAllergic.append("E_DAIRY" + ",");
			if (eggsCheckbox.isSelected())
				newAllergic.append("E_EGGS" + ",");
			if (wheatCheckbox.isSelected())
				newAllergic.append("E_WHEAT" + ",");
			if (nutsCheckbox.isSelected())
				newAllergic.append("E_NUTS" + ",");
			if (shellfishCheckbox.isSelected())
				newAllergic.append("E_SHELLFISH" + ",");
			if (soyCheckbox.isSelected())
				newAllergic.append("E_SOY" + ",");
			if (fishCheckbox.isSelected())
				newAllergic.append("E_FISH" + ",");
			if (newAllergic.length() > 0)
				newAllergic.deleteCharAt(newAllergic.length() - 1);

			if (newAllergic.toString().equals("")) {
				newDishUpdate.append("allergyCategories = '" + "E_NA" + "', ");
			} else {
				newDishUpdate.append("allergyCategories = '" + newAllergic + "', ");
			}
			if (ingredientsTextarea.getText().equals("")) {
				newDishUpdate.append("ingredients = '" + "NA" + "', ");
			} else {
				newDishUpdate.append("ingredients = '" + ingredientsTextarea.getText().replaceAll("\\s+", "") + "', ");
			}

			if (removableIngredientsTextArea.getText().equals("")) {
				newDishUpdate.append("removableOptions = '" + "NA" + "', ");
			} else {
				newDishUpdate.append("removableOptions = '" + removableIngredientsTextArea.getText().replaceAll("\\s+", "") + "', ");
			}
			if (isGrillCheckbox.isSelected()) {
				newDishUpdate.append("grillDoneness = 'E_YES'" + ", ");
			} else {
				newDishUpdate.append("grillDoneness = 'E_NO'" + ", ");
			}

			newDishUpdate.append("description = '" + descriptionTextArea.getText() + "'");

			if (userViewController.getDishController().getUpdate()) {
				newDishUpdate
						.append(" WHERE restID = '" + userViewController.getUserController().getUser().getManageRestID()
								+ "' AND dishName = '" + oldName + "';");
			} else {
				newDishUpdate.append(";");
			}

			Message updateDish = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE_DISH);
			updateDish.getMsgArrayL().add(newDishUpdate.toString());
			userViewController.getClientController().handleMessageFromClientUI(updateDish);

			while (!userViewController.getClientController().getMessageController().getE_UPDATE_DISH_MsgProccessed()) {
				try {
					System.out.println("Waiting on E_UPDATE_DISH...");
					Thread.currentThread().sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			userViewController.getClientController().getMessageController().setE_UPDATE_DISH_MsgProccessed(false);

			if (userViewController.getClientController().getMessageController()
					.getE_UPDATE_DISH_UpdatedSuccessfully()) {
				userViewController.getClientController().getMessageController()
						.setE_UPDATE_DISH_UpdatedSuccessfully(false);
				successMsg.setVisible(true);
				errorLabel.setVisible(false);

			} else {
				errorLabel.setText("The dish was not updated successfully!");
				PauseTransition pt = new PauseTransition(Duration.seconds(5));
				pt.setOnFinished(e -> {
					errorLabel.setVisible(false);
				});
				pt.play();
			}

		}
	}

	/**
	 * Check dish details are valid.
	 *
	 * @return true, if successful, else false
	 */
	public boolean checkDishDetailsAreValid() {
		PauseTransition pt = new PauseTransition(Duration.seconds(5));
		pt.setOnFinished(e -> {
			errorLabel.setVisible(false);
		});

		if (dishNameTextField.getText().equals("")) {
			errorLabel.setVisible(true);
			errorLabel.setText("Please enter a Dish Name");
			errorLabel.setTextFill(Color.RED);
			errorLabel.setStyle("-fx-background-color: #FFE5CC");
			return false;
		}

		if (!(smallCheckbox.isSelected() || mediumCheckbox.isSelected() || largeCheckbox.isSelected())) {
			errorLabel.setVisible(true);
			errorLabel.setText("Please select a size");
			errorLabel.setTextFill(Color.RED);
			errorLabel.setStyle("-fx-background-color: #FFE5CC");
			return false;
		}

		if (smallCheckbox.isSelected()) {
			String price = smallPriceTextField.getText();
			if (price.equals("")) {
				errorLabel.setVisible(true);
				errorLabel.setText("Please enter a price for the small dish size");
				errorLabel.setTextFill(Color.RED);
				errorLabel.setStyle("-fx-background-color: #FFE5CC");
				return false;
			}
			if (checkValidPrice(price) == false) {
				errorLabel.setVisible(true);
				errorLabel.setText("Please enter a Valid price for the small dish size");
				errorLabel.setTextFill(Color.RED);
				errorLabel.setStyle("-fx-background-color: #FFE5CC");
				return false;
			}

		}

		if (mediumCheckbox.isSelected()) {
			String price = medPriceTextField.getText();
			if (price.equals("")) {
				errorLabel.setVisible(true);
				errorLabel.setText("Please enter a price for the medium dish size");
				errorLabel.setTextFill(Color.RED);
				errorLabel.setStyle("-fx-background-color: #FFE5CC");
				return false;
			}
			if (checkValidPrice(price) == false) {
				errorLabel.setVisible(true);
				errorLabel.setText("Please enter a Valid price for the medium dish size");
				errorLabel.setTextFill(Color.RED);
				errorLabel.setStyle("-fx-background-color: #FFE5CC");
				return false;
			}

		}

		if (largeCheckbox.isSelected()) {
			String price = largePriceTextField.getText();
			if (price.equals("")) {
				errorLabel.setVisible(true);
				errorLabel.setText("Please enter a price for the large dish size");
				errorLabel.setTextFill(Color.RED);
				errorLabel.setStyle("-fx-background-color: #FFE5CC");
				return false;
			}
			if (checkValidPrice(price) == false) {
				errorLabel.setVisible(true);
				errorLabel.setText("Please enter a valid price for the large dish size");
				errorLabel.setTextFill(Color.RED);
				errorLabel.setStyle("-fx-background-color: #FFE5CC");
				return false;
			}

		}

		if (ingredientsTextarea.getText().equals("")) {
			errorLabel.setVisible(true);
			errorLabel.setText("Please enter dish ingredients");
			errorLabel.setTextFill(Color.RED);
			errorLabel.setStyle("-fx-background-color: #FFE5CC");
			return false;
		}

		if (descriptionTextArea.getText().equals("")) {
			errorLabel.setVisible(true);
			errorLabel.setText("Please enter a description");
			errorLabel.setTextFill(Color.RED);
			errorLabel.setStyle("-fx-background-color: #FFE5CC");
			return false;
		}

		ArrayList<String> ingredientsArr = new ArrayList<>();
		ArrayList<String> removableIngredientsArr = new ArrayList<>();
		String ingredients = ingredientsTextarea.getText().replaceAll("\\s+", "");
		String removableIngredients = removableIngredientsTextArea.getText().replaceAll("\\s+", "");
		String[] splitter = ingredients.split(",");
		for (String str : splitter) {
			ingredientsArr.add(str);
		}

		splitter = removableIngredients.split(",");
		for (String str : splitter) {
			removableIngredientsArr.add(str);
		}

		for (String removeableIngredient : removableIngredientsArr) {
			if (!ingredientsArr.contains(removeableIngredient)) {
				errorLabel.setVisible(true);
				errorLabel.setText("Each Removable Ingredient must be written the Ingredients");
				errorLabel.setTextFill(Color.RED);
				errorLabel.setStyle("-fx-background-color: #FFE5CC");
				return false;
			}
		}

		if (!userViewController.getDishController().getUpdate()
				&& dishCategoryMenuButton.getText().equals("Choose Dish Category")) {
			errorLabel.setVisible(true);
			errorLabel.setText("Please choose dish category");
			errorLabel.setTextFill(Color.RED);
			errorLabel.setStyle("-fx-background-color: #FFE5CC");
			return false;
		}

		pt.play();
		return true;
	}

	/**
	 * Check valid price.
	 *
	 * @param price - String represents the price
	 * @return true, if successful, else false
	 */
	private boolean checkValidPrice(String price) {
		boolean hasDot = false;
		String dot = "\\.";
		for (int i = 0; i < price.length(); i++) {
			if (price.charAt(i) == '.') {
				if (i == (price.length() - 1)) // for example 22.
					return false;
				hasDot = true;
				break;
			}
		}
		try {
			if (hasDot) {
				String[] saparated = price.split(dot, 2);
				Integer.parseInt(saparated[0]);
				Integer.parseInt(saparated[1]);
			} else {
				Integer.parseInt(price);
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
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
	 * Sets fields and functionality in the RestaurantUpdateDishWindow.
	 */
	@Override
	public void setParameters() {
		successMsg.setVisible(false);
		updateDishButton.setVisible(true);
		resetSizeSelects();
		Dish dish = userViewController.getDishController().getChosenDish();
		if (userViewController.getDishController().getUpdate()) {
			successMsg.setText("The dish was updated successfully");
			dishEditText.setText("Update Dish");
			updateDishButton.setText("Update Dish");
			dishNameTextField.setText(dish.getDishName());
			smallPriceTextField.setEditable(false);
			medPriceTextField.setEditable(false);
			largePriceTextField.setEditable(false);
			int i = 0;
			for (E_DishSize size : dish.getDishSizes()) {
				if (size.equals(E_DishSize.E_SMALL)) {
					smallCheckbox.setSelected(true);
					smallPriceTextField.setText(dish.getDishPrices().get(i++));
					smallPriceTextField.setEditable(true);
				}
				if (size.equals(E_DishSize.E_MED)) {
					mediumCheckbox.setSelected(true);
					medPriceTextField.setText(dish.getDishPrices().get(i++));
					medPriceTextField.setEditable(true);
				}
				if (size.equals(E_DishSize.E_LARGE)) {
					largeCheckbox.setSelected(true);
					largePriceTextField.setText(dish.getDishPrices().get(i++));
					largePriceTextField.setEditable(true);
				}

			}

			if (dish.getIsGrill())
				isGrillCheckbox.setSelected(true);

			ingredientsTextarea.setText(
					dish.getIngredients().toString().substring(1, dish.getIngredients().toString().length() - 1));
			removableIngredientsTextArea.setText(dish.getRemovableOptions().toString().substring(1,
					dish.getRemovableOptions().toString().length() - 1));
			descriptionTextArea.setText(dish.getDescription());

			DishCatLabel.setVisible(false);
			dishCategoryMenuButton.setVisible(false);

			for (E_AllergicCategories allergy : dish.getAllergyCategories()) {
				switch (allergy.toString()) {
				case "E_DAIRY":
					dairyCheckbox.setSelected(true);
					break;
				case "E_EGGS":
					eggsCheckbox.setSelected(true);
					break;
				case "E_WHEAT":
					wheatCheckbox.setSelected(true);
					break;
				case "E_NUTS":
					nutsCheckbox.setSelected(true);
					break;
				case "E_SHELLFISH":
					shellfishCheckbox.setSelected(true);
					break;
				case "E_SOY":
					soyCheckbox.setSelected(true);
					break;
				case "E_FISH":
					fishCheckbox.setSelected(true);
					break;
				}
			}

		} else {
			dishEditText.setText("Create Dish");
			updateDishButton.setText("Create Dish");
			successMsg.setText("The dish was created successfully");
			DishCatLabel.setVisible(true);
			dishCategoryMenuButton.setVisible(true);
			MenuItem appetizerMI = new MenuItem("Appetizer");
			MenuItem saladMI = new MenuItem("Salad");
			MenuItem soupMI = new MenuItem("Soup");
			MenuItem mainMI = new MenuItem("Main");
			MenuItem dessertMI = new MenuItem("Dessert");
			MenuItem drinksMI = new MenuItem("Drinks");

			appetizerMI.setOnAction(e -> {
				dishCategoryMenuButton.setText("Appetizer");
			});
			saladMI.setOnAction(e -> {
				dishCategoryMenuButton.setText("Salad");
			});
			soupMI.setOnAction(e -> {
				dishCategoryMenuButton.setText("Soup");
			});
			mainMI.setOnAction(e -> {
				dishCategoryMenuButton.setText("Main");
			});
			dessertMI.setOnAction(e -> {
				dishCategoryMenuButton.setText("Dessert");
			});
			drinksMI.setOnAction(e -> {
				dishCategoryMenuButton.setText("Drinks");
			});

			dishCategoryMenuButton.getItems().add(appetizerMI);
			dishCategoryMenuButton.getItems().add(saladMI);
			dishCategoryMenuButton.getItems().add(soupMI);
			dishCategoryMenuButton.getItems().add(mainMI);
			dishCategoryMenuButton.getItems().add(dessertMI);
			dishCategoryMenuButton.getItems().add(drinksMI);

			dishEditText.setText("Create Dish");

		}

	}

	/**
	 * Reset size selects in the screen.
	 */
	private void resetSizeSelects() {
		smallCheckbox.setOnAction(e -> {
			if (smallCheckbox.isSelected()) {
				smallPriceTextField.setEditable(true);
			} else {
				smallPriceTextField.setEditable(false);
				smallPriceTextField.setText("");
			}
		});
		mediumCheckbox.setOnAction(e -> {
			if (mediumCheckbox.isSelected()) {
				medPriceTextField.setEditable(true);
			} else {
				medPriceTextField.setEditable(false);
				medPriceTextField.setText("");
			}
		});
		largeCheckbox.setOnAction(e -> {
			if (largeCheckbox.isSelected()) {
				largePriceTextField.setEditable(true);
			} else {
				largePriceTextField.setEditable(false);
				largePriceTextField.setText("");
			}
		});

	}

}
