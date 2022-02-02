package utils;

/**
 * The Enum E_OrderStatus for the statuses of the orders.
 */
public enum E_OrderStatus {
	E_WAITING_REST_APPROVE, E_PREPARING, E_READY, E_DELIVERD;

	/**
	 * Gets the string for the enum state.
	 *
	 * @return the name
	 */
	public String getName() {
		switch (this.name()) {
		case "E_WAITING_REST_APPROVE":
			return "Waiting for restaurant approve";
		case "E_READY":
			return "Ready";
		case "E_DELIVERD":
			return "Deliverd";
		case "E_PREPARING":
			return "Preparing";
		default:
			return null;
		}
	}

}
