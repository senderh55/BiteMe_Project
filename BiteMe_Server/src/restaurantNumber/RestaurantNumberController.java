package restaurantNumber;

/**
 * This class is the functionality for the RestaurantNumberController.
 */
public class RestaurantNumberController {

	/** The restaurant number controller. */
	private static RestaurantNumberController restaurantNumberController = null;
	
	/** The restaurant number. */
	private static int restaurantNumber = 0;
	
	/**
	 * Instantiates a new restaurant number controller.
	 */
	private RestaurantNumberController() {};
	
	/**
	 * Gets the single instance of RestaurantNumberController.
	 *
	 * @return single instance of RestaurantNumberController
	 */
	public static RestaurantNumberController getInstance() {
		if (restaurantNumberController == null) {
			restaurantNumberController = new RestaurantNumberController();
		}
		return restaurantNumberController;
	}
	
	/**
	 * Gets the next restaurant number.
	 *
	 * @return the next restaurant number
	 */
	public synchronized int getNextRestaurantNumber() {
		return ++restaurantNumber;
	}

	/**
	 * Sets the next restaurant number.
	 *
	 * @param lastOrderNumber the new next restaurant number
	 */
	public void setNextRestaurantNumber(int lastOrderNumber) {
		restaurantNumber = lastOrderNumber;
	}
	
	/**
	 * Gets the restaurant number.
	 *
	 * @return the restaurant number
	 */
	public int getRestaurantNumber() {
		return restaurantNumber;
	}

	/**
	 * Sets the last restaurant number (max).
	 *
	 * @param restaurantNumber the new restaurant number
	 */
	public static void setRestaurantNumber(int restaurantNumber) {
		RestaurantNumberController.restaurantNumber = restaurantNumber;
	}
}
