package utils;

/**
 * The Enum E_DishSize.
 */
public enum E_DishSize {
	E_NA, E_SMALL, E_MED, E_LARGE;

	/**
	 * Gets the string name.
	 *
	 * @return String represents the dish size
	 */
	public String getStringName() {
		switch(this.name()) {
		case "E_SMALL":
			return "Small";
		case "E_MED":
			return "Medium";
		case "E_LARGE":
			return "Large";
		default:
			return "";
		}
		
	}
	
	/**
	 * Gets the enum.
	 *
	 * @param enumString the enum string
	 * @return the size enum
	 */
	public static E_DishSize getEnum(String enumString) {
		switch(enumString) {
		case "Small":
			return E_SMALL;
		case "Medium":
			return E_MED;
		case "Large":
			return E_LARGE;
		default:
			return E_NA;
		}
		
	}
}
