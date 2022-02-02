package ceo;

import java.util.ArrayList;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import reports.BranchmanToCeoReport;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.User;
import user.UserViewController;
import utils.E_AccountStatus;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * TThis class is the functionality for the CEOReportsFromBranchManWindow.
 */
public class CEOReportsFromBranchManForm implements IScreenController{
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;
    
    /** The Home page button. */
    @FXML
    private Button HomePageButton;

    /** The back button. */
    @FXML
    private Button backButton;

    /** The logout button. */
    @FXML
    private Button logoutButton;

    /** The personal welcome label. */
    @FXML
    private Text personalWelcomeLabel;

    /** The reports scroll pane - to show all the relevant reports list. */
    @FXML
    private ScrollPane reportsScrollPane;

    /** The users V box. */
    @FXML
    private VBox usersVBox;

    /**
     * Go to the previous window.
     *
     * @param event - an ActionEvent from the CEOReportsFromBranchManWindow
     */
    @FXML
    void backWindow(ActionEvent event) {
    	userViewController.getScreenManager().loadScreen(MyScreenEnum.CEO_ENTRANCE, ClientUI.class,
				MyScreenEnum.CEO_ENTRANCE.getFXMLName(), userViewController);
		userViewController.getScreenManager().setScreen(MyScreenEnum.CEO_ENTRANCE);
    }

    /**
     * Logout of account.
     *
     * @param event - an ActionEvent from the CEOReportsFromBranchManWindow
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
     * @param event - an ActionEvent from the CEOReportsFromBranchManWindow
     */
    @FXML
    void openHomePage(ActionEvent event) {
    	userViewController.goToHomeScreen();
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
	 * Sets fields and functionality in the CEOReportsFromBranchManWindow.
	 */
	@Override
	public void setParameters() {
		getBranchToCeoReports();
		while (!userViewController.getClientController().getMessageController().getE_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_GET_BRANCHMAN_TO_CEO_REPORTS...");
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_GET_BRANCHMAN_TO_CEO_REPORTS_MsgProccessed(false);
		buildReportsHBox();
		
	}
	
	/**
	 * Gets the branch manager reports to the CEO.
	 */
	public void getBranchToCeoReports() {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_BRANCHMAN_TO_CEO_REPORTS);
		message.addToList("SELECT * FROM branchmantoceo;");
		userViewController.getClientController().handleMessageFromClientUI(message);
	}
	
	
	/**
	 * Builds the branch manager reports list on the screen.
	 */
	public void buildReportsHBox() {
		ArrayList<BranchmanToCeoReport> branchManToCeoReports = userViewController.getReportController().getBranchManToCeoReports();

		if (branchManToCeoReports != null) {
			for (BranchmanToCeoReport report : branchManToCeoReports) {
				HBox reportRow = new HBox();
				reportRow.setSpacing(25);
				reportRow.setStyle("-fx-background-color white;");
				reportRow.setStyle("-fx-padding 5;");
				reportRow.setStyle("-fx-border-width 1;");
				reportRow.setStyle("-fx-border-color black;");
				reportRow.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);"
						+ "-fx-background-color: white;");
				reportRow.setOnMouseClicked(eventMouseClicked -> {
					userViewController.getReportController().downloadBranchToCeoReport(report);
				});
				reportRow.setOnMouseEntered(eventMouseEntered -> {
					((HBox) eventMouseEntered.getSource()).scaleXProperty().set(1.01);
					((HBox) eventMouseEntered.getSource()).scaleYProperty().set(1.01);
					((HBox) eventMouseEntered.getSource()).scaleZProperty().set(1.01);
				});
				reportRow.setOnMouseExited(eventMouseExit -> {
					((HBox) eventMouseExit.getSource()).scaleXProperty().set(1);
					((HBox) eventMouseExit.getSource()).scaleYProperty().set(1);
					((HBox) eventMouseExit.getSource()).scaleZProperty().set(1);
				});
	
				StackPane imageHolder = new StackPane();
				imageHolder.setPrefSize(117, 62);
				imageHolder.setMinSize(117, 62);
				imageHolder.setMaxSize(117, 62);
				
				Text reportBranch = new Text("Branch: " + report.getBranch());
				reportBranch.setWrappingWidth(275);
				reportBranch.setFont(Font.font("System", 18));
				VBox reportInfo = new VBox();
				reportInfo.setPadding(new Insets(0, 0, 0, 50));


				Text quarter = new Text("Report Quarter: " + report.getQuarter());
				Text year = new Text("Report Year: " + report.getYear());
				
				reportInfo.getChildren().addAll(quarter, year);
				reportRow.getChildren().addAll(imageHolder, reportBranch, reportInfo);
				reportRow.setAlignment(Pos.CENTER_LEFT);
				reportInfo.setAlignment(Pos.CENTER_LEFT);
				usersVBox.getChildren().add(reportRow);
			}
		}
	}
}
