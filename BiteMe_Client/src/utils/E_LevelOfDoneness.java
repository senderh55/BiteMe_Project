package utils;

import java.util.ArrayList;

/**
 * The Enum E_LevelOfDoneness.
 */
public enum E_LevelOfDoneness {
	E_NA, E_RARE, E_MEDIUM, E_MEDIUM_WELL, E_WELLDONE;
	
	/**
	 * Gets the doneness string.
	 *
	 * @return String represents doneness
	 */
	public String getDonenessString() {
		switch(this.name()) {
		case "E_RARE":
			return "Rare";
		case "E_MEDIUM":
			return "Medium";
		case "E_MEDIUM_WELL":
			return "Medium Well";
		case "E_WELLDONE":
			return "Well Done";
			default:
				return "";
		}
	}
	
	/**
	 * Gets the enum.
	 *
	 * @param enumString the enum string
	 * @return the doneness enum
	 */
	public static E_LevelOfDoneness getEnum(String enumString) {
		switch(enumString) {
		case "Rare":
			return E_RARE;
		case "Medium":
			return E_MEDIUM;
		case "Medium Well":
			return E_MEDIUM_WELL;
		case "Well Done":
			return E_WELLDONE;
		default:
			return E_NA;
		}
		
	}
	
}
