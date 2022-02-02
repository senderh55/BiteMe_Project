package orderNumber;

/**
 * This class is the functionality for the OrderNumberController.
 */
public class OrderNumberController {

	/** The order number controller inst. */
	private static OrderNumberController orderNumberControllerInst = null;
	
	/** The order number to keep a consistant order number. */
	private static int orderNumber = 1;
	
	
	/**
	 * Instantiates a new order number controller.
	 */
	private OrderNumberController() {};
	
	
	/**
	 * Gets the single instance of OrderNumberController.
	 *
	 * @return single instance of OrderNumberController
	 */
	public static OrderNumberController getInstance() {
		if (orderNumberControllerInst == null) {
			orderNumberControllerInst = new OrderNumberController();
		}
		return orderNumberControllerInst;
	}
	
	/**
	 * Gets the next order number.
	 *
	 * @return the next order number
	 */
	public synchronized int getNextOrderNumber() {
		return ++orderNumber;
	}
	
	/**
	 * Sets the starting point for the order number.
	 *
	 * @param lastOrderNumber the last order number (max)
	 */
	public void setNextOrderNumber(int lastOrderNumber) {
		orderNumber = lastOrderNumber;
	}
}
