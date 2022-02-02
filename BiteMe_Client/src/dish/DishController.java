package dish;

import java.util.ArrayList;

import utils.E_AllergicCategories;
import utils.E_DishCategory;
import utils.E_DishSize;
import utils.E_IsGrill;
import utils.Message;

/**
 * The Class DishController is a controller for Dish entity.
 */
public class DishController {
	
	/** The menu dishes list. */
	ArrayList<Dish> menuList = new ArrayList<>();
	
	/** Represents the chosen dish in some of the screens (where the end user selects one specific dish) */
	Dish chosenDish;
	
	/** The dish in order. */
	DishInOrder dishInOrder = new DishInOrder();
	
	/** The is update boolean to know if dish needs to be updated in some of the screens. */
	boolean isUpdate;

	/**
	 * Adds the dishes to the menu dishes list.
	 *
	 * @param dishListInfo - Message from server represents list of dish info
	 */
	public void addDishes(Message dishListInfo) {
		menuList.clear();
		for (int i = 0; i < dishListInfo.getMsgArrayL().size(); i++) {
			String[] splitter = ((String) dishListInfo.getMsgArrayL().get(i)).split(";");
			Dish dish = new Dish();
			dish.setRestID(Integer.parseInt(splitter[0]));
			dish.setDishName(splitter[1]);
			dish.setRestName(splitter[2]);
			String[] dishPriceSplitter = splitter[3].split(",");
			for (String dishPrice : dishPriceSplitter) {
				dish.addToDishPrice(dishPrice);
			}
			String[] dishSizeSplitter = splitter[4].split(",");
			for (String dishSize : dishSizeSplitter) {
				dish.addToDishSizes(E_DishSize.valueOf(dishSize));
			}
			dish.setCategory(E_DishCategory.valueOf(splitter[5]));
			String[] allergyCatSplitter = splitter[6].split(",");
			for (String allergyCat : allergyCatSplitter) {
				dish.addAllergyCategories(E_AllergicCategories.valueOf(allergyCat));
			}
			String[] ingredientsSplitter = splitter[7].split(",");
			for (String ingredient : ingredientsSplitter) {
				dish.addIngredients(ingredient);
			}
			String[] removableOptionsSplitter = splitter[8].split(",");
			for (String removableOption : removableOptionsSplitter) {
				dish.addRemovableOptions(removableOption);
			}
			dish.setIsGrill(E_IsGrill.valueOf(splitter[9]));
			dish.setDescription(splitter[10]);
			menuList.add(dish);
		}
	}

	/**
	 * Gets the menu list of dishes.
	 *
	 * @return list of Dishes represents the menu list
	 */
	public ArrayList<Dish> getMenuList() {
		return menuList;
	}

	/**
	 * Gets the chosen dish.
	 *
	 * @return Dish represents the chosen dish
	 */
	public Dish getChosenDish() {
		return chosenDish;
	}

	/**
	 * Sets the chosen dish.
	 *
	 * @param chosenDish - Dish represents the new chosen dish
	 */
	public void setChosenDish(Dish chosenDish) {
		this.chosenDish = chosenDish;
	}

	/**
	 * Gets the dish in order.
	 *
	 * @return DishInOrder represents the dish in order
	 */
	public DishInOrder getDishInOrder() {
		return dishInOrder;
	}

	/**
	 * Sets the dish in order.
	 *
	 * @param dishInOrder- DishInOrder represents the new dish in order
	 */
	public void setDishInOrder(DishInOrder dishInOrder) {
		this.dishInOrder = dishInOrder;
	}
	
	/**
	 * Gets the update boolean.
	 *
	 * @return boolean represents the update status
	 */
	public boolean getUpdate() {
		return isUpdate;
	}

	/**
	 * Sets the update boolean.
	 *
	 * @param isUpadate - boolean represents the new update status
	 */
	public void setUpdate(boolean isUpadate) {
		this.isUpdate = isUpadate;
	}

}
