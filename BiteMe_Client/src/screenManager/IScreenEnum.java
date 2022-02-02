package screenManager;

/**
 * The Interface for the screens enum.
 */
public interface IScreenEnum {
	
//	enum e_Screens implements IScreenEnum {
//		MAIN_WINDOW, LOGIN_WINDOW;
//
//		@Override
//		public String getFXMLName() {
//			switch (this.name()) {
//			case "MAIN_WINDOW":
//				return "/common/MainWindow.fxml";
//			case "LOGIN_WINDOW":
//				return "/common/LogIn.fxml";
//			default:
//				return null;
//			}
//		}
//	}

	
	/**
 * Gets the FXML name.
 *
 * @return String represents the FXML file name
 */
public abstract String getFXMLName();
	
	/**
	 * Gets the screen enum.
	 *
	 * @return MyScreenEnum represents the screen enum
	 */
	public abstract MyScreenEnum getScreenEnum();
}
