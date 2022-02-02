package ceo;

import java.io.File;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the CEOEntranceWindow.
 */
public class CEOEntranceForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The BM reports button. */
	@FXML
	private Button BMReportsButton;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The logout button. */
	@FXML
	private Button logoutButton;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The quarterly reports button. */
	@FXML
	private Button quarterlyReportsButton;
	
	/** The branch manager quarterly reports. */
	@FXML
	private Button branchManagerQuarterlyReports;

	/**
	 * Go to the quarterly reports produced by the branch managers window.
	 *
	 * @param event - an ActionEvent from the CEOEntranceWindow
	 */
	@FXML
	void showBMQuarterlyReports(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.CEO_SHOW_BRANCHMAN_TO_CEO_REPORTS, ClientUI.class,
				MyScreenEnum.CEO_SHOW_BRANCHMAN_TO_CEO_REPORTS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.CEO_SHOW_BRANCHMAN_TO_CEO_REPORTS);
	}

	/**
	 * Logout of account.
	 *
	 * @param event - an ActionEvent from the CEOEntranceWindow
	 */
	@FXML
	void logoutOfAccount(ActionEvent event) {

		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userViewController.getUserController().getUser().getUserID() + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
	}

	/**
	 * Open home page.
	 *
	 * @param event - an ActionEvent from the CEOEntranceWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Go to the Branch manager reports window.
	 *
	 * @param event - an ActionEvent from the CEOEntranceWindow
	 */
	@FXML
	void showBMReports(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_MYREPORTS, ClientUI.class,
				MyScreenEnum.BRANCHMAN_MYREPORTS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_MYREPORTS);
	}

	/**
	 * Go to the quarterly reports produced by the system window.
	 *
	 * @param event - an ActionEvent from the CEOEntranceWindow
	 */
	@FXML
	void showQuarterlyReports(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.CEO_SHOW_QUARTERLY_REPORTS, ClientUI.class,
				MyScreenEnum.CEO_SHOW_QUARTERLY_REPORTS.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.CEO_SHOW_QUARTERLY_REPORTS);
	}

	/**
	 * Sets the user view controller.
	 *
	 * @param UVC the new user view controller
	 */
	@Override
	public void setUserViewController(UserViewController UVC) {
		userViewController = UVC;

	}

	/**
	 * Delete all the files (reports).
	 *
	 * @param element - File represents the file that deletes all other files
	 */
	public static void deleteFile(File element) {
		if (element.isDirectory()) {
			for (File sub : element.listFiles()) {
				deleteFile(sub);
			}
		}
		if (!element.getName().equals("readMe.txt"))
			element.delete();
	}

	/**
	 * Sets fields and functionality in the CEOEntranceWindow.
	 */
	@Override
	public void setParameters() {
		String pathToBeDeleted = "../BiteMe_Client/downloadedFiles/";
		File f = new File(pathToBeDeleted);
		deleteFile(f);

	}

}
