package restaurant;

import java.util.ArrayList;

import dish.Dish;
import utils.E_Branches;

/**
 * The Restaurant entity.
 */
public class Restaurant {

	/** The restaurant ID. */
	int restaurantID;
	
	/** The name. */
	String name;
	
	/** The branch manager branch. */
	E_Branches bmBranch;
	
	/** The description. */
	String description;
	
	/** The address. */
	String address;
	
	/** The phone number. */
	String phoneNumber;

	/** The menu. */
	private ArrayList<Dish> menu;

	/**
	 * Gets the restaurant ID.
	 *
	 * @return int represents the restaurant ID
	 */
	public int getRestaurantID() {
		return restaurantID;
	}

	/**
	 * Sets the restaurant ID.
	 *
	 * @param restaurantID - int represents the new restaurant ID
	 */
	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}

	/**
	 * Gets the name.
	 *
	 * @return String represents the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name  - String represents the new name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Gets the branch manager branch.
	 *
	 * @return E_Branches represents the branch manager branch
	 */
	public E_Branches getBmBranch() {
		return bmBranch;
	}

	/**
	 * Sets the branch manager branch.
	 *
	 * @param bmBranch - E_Branches represents the new branch manager branch
	 */
	public void setBmBranch(E_Branches bmBranch) {
		this.bmBranch = bmBranch;
	}

	/**
	 * Gets the description.
	 *
	 * @return String represents the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description - String represents the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the address.
	 *
	 * @return String represents the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address - String represents the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return String represents the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber - String represents the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the menu.
	 *
	 * @return list of dishes represents the menu
	 */
	public ArrayList<Dish> getMenu() {
		return menu;
	}

	/**
	 * Sets the menu.
	 *
	 * @param menu - list of dishes represents the new menu
	 */
	public void setMenu(ArrayList<Dish> menu) {
		this.menu = menu;
	}
}
