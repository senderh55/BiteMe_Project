package screenManager;

import user.UserViewController;

/**
 * The Interface for all the screens in the system.
 */
public interface IScreenController {

	/**
	 * Sets the user view controller.
	 *
	 * @param UVC the new user view controller
	 */
	public abstract void setUserViewController(UserViewController UVC);

	/**
	 * Sets fields and functionality in the screen.
	 */
	public abstract void setParameters();
	
}
