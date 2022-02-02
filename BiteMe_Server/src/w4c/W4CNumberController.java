package w4c;

/**
 * This class is the functionality for the W4CNumberController.
 */
public class W4CNumberController {

	/** The w 4 c number controller inst. */
	private static W4CNumberController w4cNumberControllerInst = null;
	
	/** The W 4 C number. */
	private static int W4CNumber = 1000;
	
	/** The business W 4 C number. */
	private static int businessW4CNumber = 5000;
	

	/**
	 * Instantiates a new w 4 C number controller.
	 */
	private W4CNumberController() {};
	
	
	/**
	 * Gets the single instance of W4CNumberController.
	 *
	 * @return single instance of W4CNumberController
	 */
	public static W4CNumberController getInstance() {
		if (w4cNumberControllerInst == null) {
			w4cNumberControllerInst = new W4CNumberController();
		}
		return w4cNumberControllerInst;
	}
	
	/**
	 * Gets the next W 4 C number.
	 *
	 * @return the next W 4 C number
	 */
	public synchronized int getNextW4CNumber() {
		return ++W4CNumber;
	}
	
	/**
	 * Gets the nextbusiness W 4 C number.
	 *
	 * @return the nextbusiness W 4 C number
	 */
	public synchronized int getNextbusinessW4CNumber() {
		return ++businessW4CNumber;
	}
	
	/**
	 * Sets the next W 4 C number.
	 *
	 * @param lastOrderNumber the new next W 4 C number
	 */
	public void setNextW4CNumber(int lastOrderNumber) {
		W4CNumber = lastOrderNumber;
	}
	
	/**
	 * Gets the business W 4 C number.
	 *
	 * @return the business W 4 C number
	 */
	public int getBusinessW4CNumber() {
		return businessW4CNumber;
	}

	/**
	 * Sets the nextbusiness W 4 C number.
	 *
	 * @param lastOrderNumber the new nextbusiness W 4 C number
	 */
	public void setNextbusinessW4CNumber(int lastOrderNumber) {
		businessW4CNumber = lastOrderNumber;
	}
}
