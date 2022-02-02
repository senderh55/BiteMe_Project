package utils;

/**
 * The Enum E_Branches for the different branches in the BITEME.
 */
public enum E_Branches {
	E_NORTH, E_SOUTH, E_CENTER, E_NA;

	/**
	 * Gets the string of the enum to a number.
	 *
	 * @return the string
	 */
	public String getStringNum() {
		switch (this.name()) {
		case "E_NORTH":
			return "1";

		case "E_CENTER":
			return "2";

		case "E_SOUTH":
			return "3";
			
		case "E_NA":
			return "4";
			
		default:
			return "";
		}

	}
	/**
	 * Gets the enum.
	 *
	 * @param branch  String represents the branch
	 * @return the enum or null if not a brunch
	 */
	public static E_Branches getEnum(String branch) {
		switch (branch) {
		case "North":
			return E_NORTH;
		case "Center":
			return E_CENTER;
		case "South":
			return E_SOUTH;
		}
		return null;

	}

	/**
	 * Gets the string.
	 *
	 * @return String representing the branch
	 */
	public String getString() {
		switch (this) {
		case E_NORTH:
			return "North";
		case E_CENTER:
			return "Center";
		case E_SOUTH:
			return "South";
		}
		return null;
	}
	
	/**
	 * Gets the branch int.
	 *
	 * @return int represents the branch
	 */
	public int getBranchInt() {
		switch (this) {
		case E_NORTH:
			return 1;
		case E_CENTER:
			return 2;
		case E_SOUTH:
			return 3;
		}
		return 0;
	}
}
