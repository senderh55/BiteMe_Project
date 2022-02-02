package branchManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import report.MonthlyReport;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.E_UserTypeEnum;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the BranchManMyReportsWindow.
 */
public class BranchManMyReportsForm implements IScreenController {

	/** The combined controller for many other controllers. */
	UserViewController userViewController;

	/** The month chosen input. */
	String monthChosen;

	/** The year chosen input. */
	String yearChosen;

	/** The personal welcome label. */
	@FXML
	private Text personalWelcomeLabel;

	/** The back button 1. */
	@FXML
	private Button backButton1;

	/** The back button. */
	@FXML
	private Button backButton;

	/** The logout button. */
	@FXML
	private Button logoutButton;

	/** The show reports button. */
	@FXML
	private Button showReportsButton;

	/** The Home page button. */
	@FXML
	private Button HomePageButton;

	/** The month menu button. */
	@FXML
	private MenuButton monthMenuButton;

	/** The reports vbox - to show all relevant reports list. */
	@FXML
	private VBox reportsVbox;

	/** The scroll pane and data hbox - to show all relevant reports list. */
	@FXML
	private HBox scrollPaneAndDataHbox;

	/** The year menu button. */
	@FXML
	private MenuButton yearMenuButton;

	/**
	 * Go to the previous window.
	 *
	 * @param event - an ActionEvent from the BranchManMyReportsWindow
	 */
	@FXML
	void backWindow(ActionEvent event) {
		if(userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_CEO_USER)) {
			userViewController.getScreenManager().loadScreen(MyScreenEnum.CEO_ENTRANCE, ClientUI.class,
					MyScreenEnum.CEO_ENTRANCE.getFXMLName(), userViewController);
			userViewController.getScreenManager().setScreen(MyScreenEnum.CEO_ENTRANCE);
		}else {
			userViewController.getScreenManager().loadScreen(MyScreenEnum.BRANCHMAN_ENTRANCE, ClientUI.class,
					MyScreenEnum.BRANCHMAN_ENTRANCE.getFXMLName(), userViewController);
			userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_ENTRANCE);
		}
	}
	
    /**
     * Choose date.
     *
     * @param event the event
     */
    @FXML
    void chooseDate(ActionEvent event) {

    }

	/**
	 * Logout of account.
	 *
	 * @param event - an ActionEvent from the BranchManMyReportsWindow
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
	 * @param event - an ActionEvent from the BranchManMyReportsWindow
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
	 * Sets fields and functionality in the BranchManMyReportsWindow.
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
		Message bringReports = new Message(OwnerEnum.E_CLIENT, OpEnum.E_BRING_REPORTS);
		String query = "";
		if (userViewController.getUserController().getUser().getUserType().equals(E_UserTypeEnum.E_CEO_USER)) {
			query = "SELECT * FROM monthlyReports;";
		} else {
			query = "SELECT * FROM monthlyReports where branch = '"
					+ userViewController.getUserController().getUser().getDefaultBranch().toString() + "';";
		}
		bringReports.getMsgArrayL().add(query);

		userViewController.getClientController().handleMessageFromClientUI(bringReports);

		while (!userViewController.getClientController().getMessageController().getE_BRING_REPORTS_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_BRING_REPORTS...");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_BRING_REPORTS_MsgProccessed(false);

		TreeMap<Integer, ArrayList<MonthlyReport>> reportsForRestaurants = userViewController.getReportController()
				.getReportsForRestaurants();

		Set<Integer> allRestaurantID = reportsForRestaurants.keySet();

		showReportsButton.setOnAction(e -> {
			reportsVbox.getChildren().removeAll(reportsVbox.getChildren());
			if (scrollPaneAndDataHbox.getChildren().size() > 1) {
				scrollPaneAndDataHbox.getChildren().remove(1);
			}

			for (int restID : allRestaurantID) {
				for (MonthlyReport report : reportsForRestaurants.get(restID)) {
					if (!monthMenuButton.getText().equals("") && !yearMenuButton.getText().equals("")) {
						if (monthMenuButton.getText().substring(0, 2)
								.equals("" + report.getReportDate().getMonthValue())
								&& yearMenuButton.getText().equals("" + report.getReportDate().getYear())) {

							HBox reportRow = new HBox();
							reportRow.setSpacing(25);
							reportRow.setMaxWidth(900);
							reportRow.setStyle("-fx-background-color: white;");
							reportRow.setStyle("-fx-padding: 5;");
							reportRow.setStyle("-fx-border-width: 1;");
							reportRow.setStyle("-fx-border-color: black;");
							reportRow.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
							reportRow.setStyle(
									"-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");

							reportRow.setOnMouseClicked(eventMouseClicked -> {
								if (scrollPaneAndDataHbox.getChildren().size() > 1) {
									scrollPaneAndDataHbox.getChildren().remove(1);
								}

								VBox reportDisplayWindow = new VBox();
								reportDisplayWindow.setAlignment(Pos.CENTER);
								reportDisplayWindow.setMaxWidth(500);
								reportDisplayWindow.setMinWidth(500);
								reportDisplayWindow.setSpacing(10);
								reportDisplayWindow.setPadding(new Insets(10, 10, 10, 10));
								reportDisplayWindow.setBorder(new Border(new BorderStroke(Color.web("#F9975D"),
										BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));

								reportDisplayWindow
										.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

								reportDisplayWindow.setStyle(
										"-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");
								Label reportDetails = new Label();
								reportDetails
										.setText(report.getRestID() + " : " + report.getReportDate().getMonthValue()
												+ "/" + report.getReportDate().getYear());
								Label reportType = new Label();
								TextArea reportDataArea = new TextArea();
								reportDataArea.setMaxHeight(160);
								reportDataArea.setMinHeight(160);
								reportDataArea.setFont(Font.font(16));
								reportDataArea.setEditable(false);

								Button closeButton = new Button("Close");
								closeButton.setFont(Font.font(14));
								closeButton.setBackground(
										new Background(new BackgroundFill(Color.web("#F9975D"), null, null)));
								closeButton.setStyle(
										"-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");
								closeButton.setOnAction(eClose -> {
									scrollPaneAndDataHbox.getChildren().remove(1);
								});

								HBox buttonRack = new HBox();
								buttonRack.setSpacing(25);
								buttonRack.setAlignment(Pos.CENTER);

								Button incomeReportsButton = new Button();
								incomeReportsButton.setFont(Font.font(14));
								incomeReportsButton.setText("Income Report");
								incomeReportsButton.setBackground(
										new Background(new BackgroundFill(Color.web("#F9975D"), null, null)));
								incomeReportsButton.setStyle(
										"-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");
								incomeReportsButton.setOnAction(eIncome -> {
									reportType.setText("Income Report");
									reportDataArea.setText("Total Income: " + df.format(report.getTotalSum()));

								});

								Button orderReportButton = new Button();
								orderReportButton.setFont(Font.font(14));
								orderReportButton.setText("Order Report");
								orderReportButton.setBackground(
										new Background(new BackgroundFill(Color.web("#F9975D"), null, null)));
								orderReportButton.setStyle(
										"-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");
								orderReportButton.setOnAction(eOrder -> {
									reportType.setText("Order Report");
									reportDataArea.setText("");

									reportDataArea
											.appendText("Appetizer: " + report.getAppetizerAmnt() + " dishes made.");
									reportDataArea.appendText("\n");
									reportDataArea.appendText("Salad: " + report.getSaladAmnt() + " dishes made.");
									reportDataArea.appendText("\n");
									reportDataArea.appendText("Soup: " + report.getSoupAmnt() + " bowles made.");
									reportDataArea.appendText("\n");
									reportDataArea.appendText("Main: " + report.getMainAmnt() + " dishes made.");
									reportDataArea.appendText("\n");
									reportDataArea.appendText("Dessert: " + report.getDessertAmnt() + " dishes made.");
									reportDataArea.appendText("\n");
									reportDataArea.appendText("Drinks: " + report.getDrinkAmnt() + " drinks served.");
									reportDataArea.appendText("\n");
									reportDataArea.appendText("Total Dishes/Drinks that were created and served: "
											+ report.getTotalOrders());

								});

								Button performanceReportButton = new Button();
								performanceReportButton.setFont(Font.font(14));
								performanceReportButton.setText("Performance Report");
								performanceReportButton.setBackground(
										new Background(new BackgroundFill(Color.web("#F9975D"), null, null)));
								performanceReportButton.setStyle(
										"-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");
								performanceReportButton.setOnAction(ePerformance -> {
									reportType.setText("Performance Report");
									reportDataArea.setText("");
									reportDataArea.appendText("Amount of Orders Delivered/Pick Up on time: "
											+ report.getOrdersOnTimeAmnt());
									reportDataArea.appendText("\n");
									reportDataArea.appendText(
											"Amount of Orders Delivered/Pick Up late: " + report.getOrdersLateAmnt());
									reportDataArea.appendText("\n");
									reportDataArea.appendText("Performance Rate: " + report.getPerformance() * 100);
								});

								buttonRack.getChildren().addAll(incomeReportsButton, orderReportButton,
										performanceReportButton);
								reportDisplayWindow.getChildren().addAll(reportDetails, buttonRack, reportType,
										reportDataArea, closeButton);

								scrollPaneAndDataHbox.getChildren().add(reportDisplayWindow);
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
							// imageHolder.getChildren().add(iv);
							// ImageView imageView = new ImageView(); ADD SAVING IMAGES TO DB AND
							// IMPLEMENTING THEM HERE
							Text restIDText = new Text("Restaurant ID: " + restID);
							restIDText.setWrappingWidth(275);
							restIDText.setFont(Font.font("System", 18));

							VBox reportInfo = new VBox();
							reportInfo.setPadding(new Insets(0, 0, 0, 50));
							Text month = new Text("Month: " + report.getReportDate().getMonthValue());
							Text year = new Text("Year: " + report.getReportDate().getYear());
							Text branch = new Text("Branch: " + report.getBranch().getString());
							reportInfo.getChildren().addAll(month, year, branch);

							reportRow.getChildren().addAll(imageHolder, restIDText, reportInfo);
							reportRow.setAlignment(Pos.CENTER_LEFT);
							reportInfo.setAlignment(Pos.CENTER_LEFT);
							reportsVbox.setAlignment(Pos.CENTER);
							reportsVbox.getChildren().add(reportRow);

						}

					}
				}
			}
		});
	}

}
