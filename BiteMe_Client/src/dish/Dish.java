package dish;

import java.util.ArrayList;

import utils.*;

/**
 * The Class Dish represents a dish in the system.
 */
public class Dish {
	
	/** The restaurant ID. */
	protected int restID;

	/** The dish name. */
	protected String dishName;
	
	/** The restaurant name. */
	protected String restName;
	
	/** The dish prices list according to size. */
	protected ArrayList<String> priceList = new ArrayList<>();
	
	/** The dish sizes list. */
	protected ArrayList<E_DishSize> dishSizes = new ArrayList<>();
	
	/** The dish category list. */
	protected E_DishCategory category;
	
	/** The dish ingredients list. */
	protected ArrayList<String> ingredients = new ArrayList<>();
	
	/** The removable ingredients options list. */
	protected ArrayList<String> removableOptions = new ArrayList<>();
	
	/** The dish possibly allergies categories list. */
	protected ArrayList<E_AllergicCategories> allergyCategories = new ArrayList<>();
	
	/** Represents if the dish is made on the grill or not. */
	protected E_IsGrill isGrill;
	
	/** The  dish description. */
	protected String description;
	
	
	/**
	 * Sets the dish data.
	 *
	 * @param dish - a Dish represents a dish in the system
	 */
	public void setDish(Dish dish) {
		this.restID = dish.restID;
		this.restName = dish.restName;
		this.dishName = dish.dishName;
		this.priceList = dish.priceList;
		this.dishSizes = dish.dishSizes;
		this.category = dish.category;
		this.ingredients = dish.ingredients;
		this.removableOptions = dish.removableOptions;
		this.allergyCategories = dish.allergyCategories;
		this.isGrill = dish.isGrill;
		this.description = dish.description;
	}

	/**
	 * Gets the restaurant name.
	 *
	 * @return a string represents the restaurant name
	 */
	public String getRestName() {
		return restName;
	}

	/**
	 * Sets the restaurant name.
	 *
	 * @param restName - a String represents the restaurant name
	 */
	public void setRestName(String restName) {
		this.restName = restName;
	}

	/**
	 * Gets the dish name.
	 *
	 * @return a String represents the dish name
	 */
	public String getDishName() {
		return dishName;
	}

	/**
	 * Sets the dish name.
	 *
	 * @param dishName - a String represents the new dish name
	 */
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	/**
	 * Gets the dish prices.
	 *
	 * @return a list of Strings represents the dish prices
	 */
	public ArrayList<String> getDishPrices() {
		return priceList;
	}

	/**
	 * Adds the to dish price.
	 *
	 * @param price  - a String represents the dish price
	 */
	public void addToDishPrice(String price) {
		this.priceList.add(price);
	}

	/**
	 * Gets the dish sizes list.
	 *
	 * @return a list of E_DishSize represents the dish sizes
	 */
	public ArrayList<E_DishSize> getDishSizes() {
		return dishSizes;
	}

	/**
	 * Adds the dish sizes to the sizes list.
	 *
	 * @param dishSizes - an E_DishSize represents the dish sizes
	 */
	public void addToDishSizes(E_DishSize dishSizes) {
		this.dishSizes.add(dishSizes);
	}

	/**
	 * Gets the dish category.
	 *
	 * @return a E_DishCategory represents the dish category
	 */
	public E_DishCategory getCategory() {
		return category;
	}

	/**
	 * Sets the dish category.
	 *
	 * @param category - E_DishCategory represents the new dish category
	 */
	public void setCategory(E_DishCategory category) {
		this.category = category;
	}

	/**
	 * Gets the dish ingredients list.
	 *
	 * @return list of Strings represents the ingredients
	 */
	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	/**
	 * Adds the dish the ingredients.
	 *
	 * @param ingredients - String represents the ingredients
	 */
	public void addIngredients(String ingredients) {
		this.ingredients.add(ingredients);
	}

	/**
	 * Gets the dish's removable ingredients options list.
	 *
	 * @return String represents the dish removable ingredients options
	 */
	public ArrayList<String> getRemovableOptions() {
		return removableOptions;
	}

	/**
	 * Adds the removable ingredient to the dish's removable ingredients options.
	 *
	 * @param removableOptions- String represents the dish removable ingredients options
	 */
	public void addRemovableOptions(String removableOptions) {
		this.removableOptions.add(removableOptions);
	}

	/**
	 * Gets the dish possibly allergies categories list.
	 *
	 * @return E_AllergicCategories represents the dish possibly allergies categories
	 */
	public ArrayList<E_AllergicCategories> getAllergyCategories() {
		return allergyCategories;
	}

	/**
	 * Adds the possible allergy to the possibly allergies categories list.
	 *
	 * @param allergyCategories- E_AllergicCategories represents the possibly allergies categories
	 */
	public void addAllergyCategories(E_AllergicCategories allergyCategories) {
		this.allergyCategories.add(allergyCategories);
	}

	/**
	 * Gets the check if the dish is grilled.
	 *
	 * @return boolean represents the checks if the dish is grilled
	 */
	public boolean getIsGrill() {
		if(isGrill.equals(E_IsGrill.E_YES))
			return true;
		else
			return false;
	}

	/**
	 * Sets the check if the dish is grill.
	 *
	 * @param isGrill - E_IsGrill represents the new checks if the dish is grill
	 */
	public void setIsGrill(E_IsGrill isGrill) {
		this.isGrill = isGrill;
	}

	/**
	 * Gets the dish description.
	 *
	 * @return String represents the dish description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the dish description.
	 *
	 * @param description the new dish description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the restaurant ID.
	 *
	 * @return int represents the restaurant ID
	 */
	public int getRestID() {
		return restID;
	}

	/**
	 * Sets the restaurant ID.
	 *
	 * @param restID - int represents the new restaurant ID
	 */
	public void setRestID(int restID) {
		this.restID = restID;
	}

}