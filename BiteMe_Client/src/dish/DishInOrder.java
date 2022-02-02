package dish;

import java.util.ArrayList;

import utils.E_DishSize;
import utils.E_LevelOfDoneness;

/**
 * The Class DishInOrder represents a dish in the order made by the user.
 */
public class DishInOrder extends Dish {
	
	/** The dish doneness level. */
	E_LevelOfDoneness donenessLevel = null;
	
	/** The amount of dish. */
	int amountOfDish;
	
	/** The dish size. */
	E_DishSize dishSize;
	
	/** The price of dish by size. */
	double priceOfDishBySize;

	/** The dish optional ingredients to remove. */
	ArrayList<String> optionToRemove = new ArrayList<>();

	/**
	 * Gets the dish doneness level.
	 *
	 * @return E_LevelOfDoneness represents the dish doneness level
	 */
	public E_LevelOfDoneness getDonenessLevel() {
		return donenessLevel;
	}

	/**
	 * Sets the dish doneness level.
	 *
	 * @param donenessLevel - E_LevelOfDoneness represents the new dish doneness level
	 */
	public void setDonenessLevel(E_LevelOfDoneness donenessLevel) {
		this.donenessLevel = donenessLevel;
	}

	/**
	 * Gets the dish optional ingredients to remove.
	 *
	 * @return String represents the dish optional ingredients to remove
	 */
	public ArrayList<String> getOptionToRemove() {
		return optionToRemove;
	}

	/**
	 * Sets the dish optional ingredients to remove.
	 *
	 * @param optionToRemove- String represents the new dish optional ingredients to remove
	 */
	public void setOptionToRemove(ArrayList<String> optionToRemove) {
		this.optionToRemove = optionToRemove;
	}

	/**
	 * Adds the dish optional ingredients to remove.
	 *
	 * @param option String represents the dish optional ingredients the option
	 */
	public void addOptionToRemove(String option) {
		optionToRemove.add(option);
	}

	/**
	 * Delete from dish optional ingredients to remove.
	 *
	 * @param option - String represents the dish optional ingredients
	 * @return true, if delete successful
	 */
	public boolean deleteFromOptionToRemove(String option) {
		return optionToRemove.remove(option);
	}

	/**
	 * Gets the amount of dish in order.
	 *
	 * @return int represents the amount of dish in order
	 */
	public int getAmountOfDish() {
		return amountOfDish;
	}

	/**
	 * Sets the amount of dish in order.
	 *
	 * @param amountOfDish - int represents the new amount of dish in order
	 */
	public void setAmountOfDish(int amountOfDish) {
		this.amountOfDish = amountOfDish;
	}

	/**
	 * Gets the dish size.
	 *
	 * @return E_DishSize represents the dish size
	 */
	public E_DishSize getDishSize() {
		return dishSize;
	}

	/**
	 * Sets the dish size.
	 *
	 * @param dishSize- E_DishSize represents the new dish size
	 */
	public void setDishSize(E_DishSize dishSize) {
		this.dishSize = dishSize;
	}

	/**
	 * Gets the price of dish by size.
	 *
	 * @return double represents the price of dish by size
	 */
	public double getPriceOfDishBySize() {
		return priceOfDishBySize;
	}

	/**
	 * Sets the price of dish by size.
	 *
	 * @param priceOfDishBySize - double represents the new price of dish by size
	 */
	public void setPriceOfDishBySize(double priceOfDishBySize) {
		this.priceOfDishBySize = priceOfDishBySize;
	}

	/**
	 * Adds the to amount of dishes in order.
	 *
	 * @param amountToAdd- int represents the amount of dishes in order
	 */
	public void addToAmountOfDish(int amountToAdd) {
		amountOfDish += amountToAdd;
	}
//
//	@Override
//	public boolean equals(Object dishObject) {
//		DishInOrder otherDish = (DishInOrder) dishObject;
//		if (this.dishName.equals(otherDish.dishName) && this.dishSize.equals(otherDish.dishSize)
//				&& this.restName.equals(otherDish.restName)) {
//			for (String option : removableOptions) {
//				if (otherDish.removableOptions.containsAll(this.removableOptions)
//						&& this.removableOptions.containsAll(otherDish.removableOptions)) {
//					return true;
//				}
//			}
//
//		}
//		return false;
//	}
}
