package restaurant;

import java.text.DecimalFormat;
import java.time.LocalDate;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.util.Duration;
import report.MonthlyReport;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the RestaurantCommissionsWindow.
 */
public class RestaurantCommissionsForm implements IScreenController {
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;
	
	/** The Log out button. */
	@FXML
	private Button LogOutButton;

	/** The commissions text area. */
	@FXML
	private TextArea commissionssTextArea;

	/** The error text. */
	@FXML
	private Text errorText;
	
	/** The back button. */
	@FXML
	private Button backButton;

	/** The home page button. */
	@FXML
	private Button homePageButton;

	/** The month menu button. */
	@FXML
	private MenuButton monthMenuButton;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The show commissions button. */
	@FXML
	private Button showCommissionsButton;

	/** The year menu button. */
	@FXML
	private MenuButton yearMenuButton;

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the RestaurantCommissionsWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().setScreen(MyScreenEnum.RESTAURANT_ENTRANCE);
	}

	/**
	 * Choose date.
	 *
	 * @param event - an ActionEvent from the RestaurantCommissionsWindow
	 */
	@FXML
	void chooseDate(ActionEvent event) {

	}

	/**
	 * Logging out of account.
	 *
	 * @param event - an ActionEvent from the RestaurantCommissionsWindow
	 */
	@FXML
	void logOut(ActionEvent event) {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.getMsgArrayL().add("UPDATE user SET isLoggedIn = 0 WHERE userID = "
				+ userViewController.getUserController().getUser().getUserID() + ";");
		userViewController.getClientController().handleMessageFromClientUI(message);
		userViewController.getScreenManager().setScreen(MyScreenEnum.LOGIN_WINDOW);
	}

	/**
	 * Go to home page.
	 *
	 * @param event - an ActionEvent from the RestaurantCommissionsWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
		
	}

	/**
	 * Show commissions list on the screen.
	 *
	 * @param event - an ActionEvent from the RestaurantCommissionsWindow
	 */
	@FXML
	void showCommissions(ActionEvent event) {
		DecimalFormat df = new DecimalFormat();
		if (monthMenuButton.getText().equals("Month") || yearMenuButton.getText().equals("Year")) {
			errorText.setVisible(true);
			PauseTransition pt = new PauseTransition(Duration.seconds(5));
			pt.setOnFinished(e -> {
				errorText.setVisible(false);
			});
			pt.play();
		} else {
			String dateStr = yearMenuButton.getText() + "-" + monthMenuButton.getText().substring(0,2) + "-01";
			LocalDate date = LocalDate.parse(dateStr);
			Message getCommissions = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_REST_COMMISSIONS);
			getCommissions.getMsgArrayL().add("SELECT * FROM monthlyreports WHERE restID = '" + userViewController.getUserController().getUser().getManageRestID() + 
												"' AND  MONTH(date)='" + date.getMonthValue() + "' AND YEAR(date)= '" + date.getYear() + "';");
			
			userViewController.getClientController().handleMessageFromClientUI(getCommissions);

			while (!userViewController.getClientController().getMessageController()
					.getE_GET_REST_COMMISSIONS_MsgProccessed()) {
				System.out.println("waiting on E_GET_REST_COMMISSIONS...");
				try {
					Thread.currentThread().sleep(1); // Eli - Check if we can use sleep
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			userViewController.getClientController().getMessageController()
					.setE_GET_REST_COMMISSIONS_MsgProccessed(false);
			int restID = userViewController.getUserController().getUser().getManageRestID();
			if(userViewController.getReportController().getReportsForRestaurants().containsKey(restID) 
					&& userViewController.getReportController().getReportsForRestaurants().get(restID).size() == 1){
				MonthlyReport commission = userViewController.getReportController().getReportsForRestaurants().get(restID).get(0);
				commissionssTextArea.setText("");
				commissionssTextArea.appendText("Commissions Report:");
				commissionssTextArea.appendText("\n");
				commissionssTextArea.appendText("Month: " + df.format(commission.getReportDate().getMonthValue()));
				commissionssTextArea.appendText("\n");
				commissionssTextArea.appendText("Year: " + commission.getReportDate().getYear());
				commissionssTextArea.appendText("\n");
				commissionssTextArea.appendText("Total Revenue: " + df.format(commission.getTotalSum()));
				commissionssTextArea.appendText("\n");
				commissionssTextArea.appendText("Fee Taken: " + df.format(commission.getFee()*100) + "%");
				commissionssTextArea.appendText("\n");
				commissionssTextArea.appendText("Fee Sum: " + df.format(commission.getFeeSum()));
				commissionssTextArea.appendText("\n");
				commissionssTextArea.appendText("Total Net Earnings: " + df.format((commission.getTotalSum()-commission.getFeeSum())));
			}
			else {
				if(monthMenuButton.getText().subSequence(0, 2).equals(LocalDate.now().getMonthValue() + "") &&
						yearMenuButton.getText().equals(LocalDate.now().getYear() + "")) {
					commissionssTextArea.setText("Please wait for the end of the month in order to view the current months commissions.");
				}
				commissionssTextArea.setText("No reports found.");
			}
			
		}
		
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
	 * Sets fields and functionality in the RestaurantCommissionsWindow.
	 */
	@Override
	public void setParameters() {
		DecimalFormat df = new DecimalFormat();
		for (MenuItem mi : monthMenuButton.getItems()) {
			mi.setOnAction(e -> {
				String text = mi.getText();
				monthMenuButton.setText(text);
			});

		}

		for (MenuItem mi : yearMenuButton.getItems()) {
			mi.setOnAction(e -> {
				String text = mi.getText();
				yearMenuButton.setText(text);
			});
		}
		errorText.setVisible(false);

	}

}
