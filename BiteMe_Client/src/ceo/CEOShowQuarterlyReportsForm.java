package ceo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import client.ClientUI;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_Branches;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the CEOShowQuarterlyReportsWindow.
 */
public class CEOShowQuarterlyReportsForm implements IScreenController {
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The Error label. */
	@FXML
	private Label ErrorLabel;

	/** The choose B label. */
	@FXML
	private Text chooseBLabel;

	/** The choose B line. */
	@FXML
	private Line chooseBLine;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The choose branch menu 1. */
	@FXML
	private MenuButton chooseBranchMenu1;

	/** The choose branch menu 2. */
	@FXML
	private MenuButton chooseBranchMenu2;

	/** The compare button. */
	@FXML
	private Button compareButton;

	/** The compare report radio button. */
	@FXML
	private RadioButton compareReportRadioButton;

	/** The logout button. */
	@FXML
	private Button logoutButton;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The report option. */
	@FXML
	private ToggleGroup reportOption;

	/** The report quarter menu 1. */
	@FXML
	private MenuButton reportQuarterMenu1;

	/** The report quarter menu 2. */
	@FXML
	private MenuButton reportQuarterMenu2;

	/** The report year menu 1. */
	@FXML
	private MenuButton reportYearMenu1;

	/** The report year menu 2. */
	@FXML
	private MenuButton reportYearMenu2;

	/** The single report radio button. */
	@FXML
	private RadioButton singleReportRadioButton;

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the CEOShowQuarterlyReportsWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		userViewController.getScreenManager().loadScreen(MyScreenEnum.CEO_ENTRANCE, ClientUI.class,
				MyScreenEnum.CEO_ENTRANCE.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.CEO_ENTRANCE);
	}

	/**
	 * Open quarterly reports in order to compare between reports.
	 *
	 * @param event - an ActionEvent from the CEOShowQuarterlyReportsWindow
	 */
	@FXML
	void compareReports(ActionEvent event) {
		if (checkStateOfData()) {
			String branchA = E_Branches.E_NA.toString();
			String branchB = E_Branches.E_NA.toString();
			String quarterA = reportQuarterMenu1.getText();
			String quarterB = reportQuarterMenu2.getText();
			String yearA = reportYearMenu1.getText();
			String yearB = reportYearMenu2.getText();

			switch (chooseBranchMenu1.getText()) {
			case "North":
				branchA = E_Branches.E_NORTH.toString();
				break;
			case "Center":
				branchA = E_Branches.E_CENTER.toString();
				break;
			case "South":
				branchA = E_Branches.E_SOUTH.toString();
				break;

			}

			switch (chooseBranchMenu2.getText()) {
			case "North":
				branchB = E_Branches.E_NORTH.toString();
				break;
			case "Center":
				branchB = E_Branches.E_CENTER.toString();
				break;
			case "South":
				branchB = E_Branches.E_SOUTH.toString();
				break;
			}
			Message getReportsA = null;
			Message getReportsB = null;
			if (!branchA.equals(E_Branches.E_NA.toString()) && !branchB.equals(E_Branches.E_NA.toString())) {
				getReportsA = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_QUARTERLY_REPORT);
				String getReportstrA = "SELECT * FROM quarterlyreports WHERE quarter = '" + quarterA + "' AND year = '"
						+ yearA + "' AND branch = '" + branchA + "';";
				getReportsA.getMsgArrayL().add(getReportstrA);
			}
			if (compareReportRadioButton.isSelected()) {

				if (!branchB.equals(E_Branches.E_NA.toString()) && !branchB.equals(E_Branches.E_NA.toString())) {
					getReportsB = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_QUARTERLY_REPORT);
					String getReportstrB = "SELECT * FROM quarterlyreports WHERE quarter = '" + quarterB
							+ "' AND year = '" + yearB + "' AND branch = '" + branchB + "';";
					getReportsB.getMsgArrayL().add(getReportstrB);
				}
			}

			if (getReportsA != null) {
				userViewController.getClientController().handleMessageFromClientUI(getReportsA);

				while (!userViewController.getClientController().getMessageController()
						.getE_GET_QUARTERLY_REPORT_MsgProccessed()) {
					try {
						System.out.println("Waiting on E_GET_QUARTERLY_REPORT...");
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				userViewController.getClientController().getMessageController()
						.setE_GET_QUARTERLY_REPORT_MsgProccessed(false);

			}
			if (compareReportRadioButton.isSelected()) {
				if (getReportsB != null) {
					userViewController.getClientController().handleMessageFromClientUI(getReportsB);
					while (!userViewController.getClientController().getMessageController()
							.getE_GET_QUARTERLY_REPORT_MsgProccessed()) {
						try {
							System.out.println("Waiting on E_GET_QUARTERLY_REPORT...");
							Thread.currentThread().sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					userViewController.getClientController().getMessageController()
							.setE_GET_QUARTERLY_REPORT_MsgProccessed(false);
				}
			}
			showReport(branchA, quarterA, yearA);
			if (compareReportRadioButton.isSelected()) {
				showReport(branchB, quarterB, yearB);
			}
		}
	}

	/**
	 * Checks for missing input values in the screen. 
	 *
	 * @return true, if all of the inputs are filled and valid, else false
	 */
	private boolean checkStateOfData() {
		PauseTransition pt = new PauseTransition(Duration.seconds(4));
		pt.setOnFinished(e -> {
			ErrorLabel.setText("");
		});

		ErrorLabel.setText("");
		ErrorLabel.setVisible(true);

		if (chooseBranchMenu1.getText().equals("Choose Branch")) {
			ErrorLabel.setText("Missing Branch A");
			pt.play();
			return false;
		}
		if (reportQuarterMenu1.getText().equals("Report Quarter")) {
			ErrorLabel.setText("Missing Quarter A");
			pt.play();
			return false;
		}
		if (reportYearMenu1.getText().equals("Report Year")) {
			ErrorLabel.setText("Missing Year A");
			pt.play();
			return false;
		}
		if (compareReportRadioButton.isSelected()) {
			if (chooseBranchMenu2.getText().equals("Choose Branch")) {
				ErrorLabel.setText("Missing Branch B");
				pt.play();
				return false;
			}
			if (reportQuarterMenu2.getText().equals("Report Quarter")) {
				ErrorLabel.setText("Missing Quarter B");
				pt.play();
				return false;
			}
			if (reportYearMenu2.getText().equals("Report Year")) {
				ErrorLabel.setText("Missing Year B");
				pt.play();
				return false;
			}
		}
		pt.play();
		return true;
	}

	/**
	 * Show quarterly report.
	 *
	 * @param branch - String represents the branch
	 * @param quarter - String represents the quarter
	 * @param year - String represents the year
	 */
	public void showReport(String branch, String quarter, String year) {
		String path = "../BiteMe_Client/downloadedFiles/" + quarter + "_" + year + "_" + branch + ".pdf";
		File fileReport = new File(path); // create a file with the same name as new_file.getFileName()

		if (Desktop.isDesktopSupported()) {
			new Thread(() -> {
				try {
					if (fileReport.exists() && !fileReport.isDirectory()) {
						Desktop.getDesktop().open(fileReport);
					} else {
						PauseTransition pt = new PauseTransition(Duration.seconds(4));
						pt.setOnFinished(e -> {
							ErrorLabel.setText("");
						});
						Platform.runLater(() -> {
							ErrorLabel.setText("No Reports found with those details");
						});
						pt.play();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();
		}

		// vboxToPutPDF.getChildren().add(webView);
	}

	/**
	 * Logout of account.
	 *
	 * @param event - an ActionEvent from the CEOShowQuarterlyReportsWindow
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
	 * @param event - an ActionEvent from the CEOShowQuarterlyReportsWindow
	 */
	@FXML
	void openHomePage(ActionEvent event) {
		userViewController.goToHomeScreen();
	}

	/**
	 * Show all B report fields on the screen.
	 *
	 * @param event - an ActionEvent from the CEOShowQuarterlyReportsWindow
	 */
	@FXML
	void compareRadio(MouseEvent event) {

		compareButton.setText("Compare Report");
		chooseBranchMenu2.setVisible(true);
		reportYearMenu2.setVisible(true);
		reportQuarterMenu2.setVisible(true);
		chooseBLabel.setVisible(true);
		chooseBLine.setVisible(true);

	}

	/**
	 * Hide all B report fields on the screen.
	 *
	 * @param event - an ActionEvent from the CEOShowQuarterlyReportsWindow
	 */
	@FXML
	void singleRadio(MouseEvent event) {

		compareButton.setText("View Report");
		chooseBranchMenu2.setVisible(false);
		reportYearMenu2.setVisible(false);
		reportQuarterMenu2.setVisible(false);
		chooseBLabel.setVisible(false);
		chooseBLine.setVisible(false);

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
	 * Sets fields and functionality in the CEOShowQuarterlyReportsWindow.
	 */
	@Override
	public void setParameters() {
		ErrorLabel.setVisible(false);
		for (MenuItem mi : reportQuarterMenu1.getItems()) {
			mi.setOnAction(e -> {
				String text = mi.getText();
				reportQuarterMenu1.setText(text);
			});

		}
		for (MenuItem mi : reportQuarterMenu2.getItems()) {
			mi.setOnAction(e -> {
				String text = mi.getText();
				reportQuarterMenu2.setText(text);
			});

		}
		for (MenuItem mi : reportYearMenu1.getItems()) {
			mi.setOnAction(e -> {
				String text = mi.getText();
				reportYearMenu1.setText(text);
			});

		}
		for (MenuItem mi : reportYearMenu2.getItems()) {
			mi.setOnAction(e -> {
				String text = mi.getText();
				reportYearMenu2.setText(text);
			});

		}
		for (MenuItem mi : chooseBranchMenu1.getItems()) {
			mi.setOnAction(e -> {
				String text = mi.getText();
				chooseBranchMenu1.setText(text);
			});

		}
		for (MenuItem mi : chooseBranchMenu2.getItems()) {
			mi.setOnAction(e -> {
				String text = mi.getText();
				chooseBranchMenu2.setText(text);
			});

		}

		compareReportRadioButton.setOnMouseClicked(e -> {
			if (compareReportRadioButton.isSelected()) {
				compareButton.setText("Compare Report");
				chooseBranchMenu2.setVisible(true);
				reportYearMenu2.setVisible(true);
				reportQuarterMenu2.setVisible(true);
				chooseBLabel.setVisible(true);
				chooseBLine.setVisible(true);
			}
		});

		singleReportRadioButton.setOnMouseClicked(e -> {
			if (singleReportRadioButton.isSelected()) {
				compareButton.setText("View Report");
				chooseBranchMenu2.setVisible(false);
				reportYearMenu2.setVisible(false);
				reportQuarterMenu2.setVisible(false);
				chooseBLabel.setVisible(false);
				chooseBLine.setVisible(false);
			}
		});
	}
}
