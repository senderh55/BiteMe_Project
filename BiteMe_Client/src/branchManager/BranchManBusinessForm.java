package branchManager;

import java.util.ArrayList;

import business.Business;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import screenManager.IScreenController;
import screenManager.MyScreenEnum;
import user.UserViewController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This class is the functionality for the BranchManBusinessWindow.
 */
public class BranchManBusinessForm implements IScreenController{
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;
	
    /** The personal welcome label. */
    @FXML
    private Text personalWelcomeLabel;

    /** The business scroll pane. */
    @FXML
    private ScrollPane businessScrollPane;

    /** The business V box - to show all relevant business list. */
    @FXML
    private VBox businessVBox;

    /** The back button. */
    @FXML
    private Button backButton;

    /** The logout button. */
    @FXML
    private Button logoutButton;

    /** The Home page button. */
    @FXML
    private Button HomePageButton;

    /**
     * Go to the previous window.
     *
     * @param event - an ActionEvent from the BranchManBusinessWindow
     */
    @FXML
    void backWindow(ActionEvent event) {
    	userViewController.getScreenManager().setScreen(MyScreenEnum.BRANCHMAN_ENTRANCE);
    }

    /**
     * Logout of account.
     *
     * @param event - an ActionEvent from the BranchManBusinessWindow
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
     * @param event - an ActionEvent from the BranchManBusinessWindow
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
	 * Sets fields and functionality in the BranchManBusinessWindow.
	 */
	@Override
	public void setParameters() {
		getBussinessData();
		while (!userViewController.getClientController().getMessageController().getE_GET_BUSINESS_MsgProccessed()) {
			try {
				System.out.println("Waiting on E_BUSINESS_LIST...");
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		userViewController.getClientController().getMessageController().setE_GET_BUSINESS_MsgProccessed(false);
		buildBussinessHBox();
	}


	/**
	 * Gets the business data from server (DB) into the userViewController.
	 */
	public void getBussinessData() {
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_GET_BUSINESS);
		String branch = "'" + userViewController.getUserController().getUser().getDefaultBranch() +"';";
		message.addToList("SELECT * FROM business WHERE isApproved = 'NO' AND branch = " + branch);
		userViewController.getClientController().handleMessageFromClientUI(message);
	}
	

	/**
	 * Builds the business to be approved list on the screen.
	 */
	public void buildBussinessHBox() {
		ArrayList<Business> unApprovedBusinessList = userViewController.getBusinessController().getAllBusinessList();
	
		if (unApprovedBusinessList != null) {
			for (Business b : unApprovedBusinessList) {
				HBox businessRow = new HBox();
				//businessRow.setSpacing(25);
				businessRow.setStyle("-fx-background-color white;");
				businessRow.setStyle("-fx-padding 5;");
				businessRow.setStyle("-fx-border-width 1;");
				businessRow.setStyle("-fx-border-color black;");
				businessRow.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);"
						+ "-fx-background-color: white;");
				

				businessRow .setOnMouseEntered(eventMouseEntered -> {
					((HBox) eventMouseEntered.getSource()).scaleXProperty().set(1.01);
					((HBox) eventMouseEntered.getSource()).scaleYProperty().set(1.01);
					((HBox) eventMouseEntered.getSource()).scaleZProperty().set(1.01);
				});
				businessRow .setOnMouseExited(eventMouseExit -> {
					((HBox) eventMouseExit.getSource()).scaleXProperty().set(1);
					((HBox) eventMouseExit.getSource()).scaleYProperty().set(1);
					((HBox) eventMouseExit.getSource()).scaleZProperty().set(1);
				});
				
				Button approveButton = new Button();
				approveButton.setPrefSize(80, 50);
				approveButton.setText("Approve");
				approveButton.setLayoutX(-40);
				approveButton.setLayoutY(253);
				approveButton.setStyle("-fx-background-color: DARKSEAGREEN;" + "-fx-font-weight: bold;");
				DropShadow shadow = new DropShadow();
				shadow.setRadius(5.0);
				approveButton.setEffect(shadow);
				approveButton.setOnMouseClicked(e -> {
					b.setApproved(true);
					approveUpdateInDB(b.getCompanyName());
					businessVBox.getChildren().remove(businessRow);
				});
				
				StackPane imageHolder = new StackPane();
				imageHolder.setPrefSize(100, 62);
				imageHolder.setMinSize(100, 62);
				imageHolder.setMaxSize(100, 62);


				Text businessName = new Text(b.getCompanyName());
				businessName.setWrappingWidth(200);
				businessName.setFont(Font.font("System", 18));
				VBox businessInfo = new VBox();
				businessInfo.setPadding(new Insets(0, 75, 0, 25));
				Text employerName = new Text("Employer name: " + b.getEmployerName());
				Text mounthlyLimit = new Text("Mounthly limit: " + b.getMounthlyLimit());
				Text W4C = new Text("W4C Code: " + b.getBusinessW4CNumber());
				businessInfo.getChildren().addAll(employerName, mounthlyLimit, W4C);
				
				businessRow.getChildren().addAll(imageHolder, businessName, businessInfo, approveButton);
				businessRow.setAlignment(Pos.CENTER_LEFT);
				businessInfo.setAlignment(Pos.CENTER_LEFT);
				businessVBox.getChildren().add(businessRow);
			}
		}

	}
	
	/**
	 * Update the DB that the business is now approved.
	 *
	 * @param name - String represents the company name
	 */
	public void approveUpdateInDB(String name) {
		String branch = "'" + userViewController.getUserController().getUser().getDefaultBranch() +"';";
		String companyName = "'" + name +"'";
		Message message = new Message(OwnerEnum.E_CLIENT, OpEnum.E_UPDATE);
		message.addToList("UPDATE business SET isApproved = 'YES' WHERE companyName = "+ companyName + " AND branch = " + branch);
		userViewController.getClientController().handleMessageFromClientUI(message);
	}
	
	


}
