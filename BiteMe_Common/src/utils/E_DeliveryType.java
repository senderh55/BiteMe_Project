package utils;

/**
 * The Enum E_DeliveryType for the type of delivery.
 */
public enum E_DeliveryType {
	E_DELIVERY, E_PICKUP;
	
	/**
	 * Gets the string name.
	 *
	 * @return the string name
	 */
	public String getStringName() {
		switch(this.name()) {
		case "E_DELIVERY":
			return "Delivery";
		case "E_PICKUP":
			return "Pickup";
		default:
			return "";
		}
	}
}
