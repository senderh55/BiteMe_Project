package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.ServerController;

/**
 * This class is the functionality for the ServerForm.
 */
public class ServerForm extends Application {
	
	/** The server controller. */
	static server.ServerController serverController;
	
	/** The is connected. */
	private boolean isConnected = false;

	/** The connect button. */
	@FXML
	private Button connectButton;

	/** The db name text field. */
	@FXML
	private TextField dbNameTextField;

	/** The db password text field. */
	@FXML
	private PasswordField dbPasswordTextField;

	/** The db username text field. */
	@FXML
	private TextField dbUsernameTextField;

	/** The disc button. */
	@FXML
	private Button discButton;

	/** The import users button. */
	@FXML
	private Button importUsersButton;

	/** The ip text field. */
	@FXML
	private TextField ipTextField;
	
	/** The show clients text area. */
	@FXML
	private TextArea showClientsTextArea;

	/** The port text field. */
	@FXML
	private TextField portTextField;

	/** The server con status label. */
	@FXML
	private Label serverConStatusLabel;

	/** The server error text. */
	@FXML
	private Text serverErrorText;

	/** The show clients button. */
	@FXML
	private Button showClientsButton;

	/**
	 * Import users from the external DB "User Portal Import".
	 *
	 * @param event - an ActionEvent from the ServerForm
	 */
	@FXML
	void ImportUsers(ActionEvent event) {
		if (!isConnected) {
			serverErrorText.setText("Please connect first");
			return;
		}
		serverController.importUsersFromExternalSystem();
		serverErrorText.setText("Import user's from external system succeed");
		importUsersButton.setVisible(false);
	}

	/**
	 * Open the server connection for clients to connect to.
	 *
	 *  @param event - an ActionEvent from the ServerForm
	 */
	@FXML
	void connectServer(ActionEvent event) {
		serverErrorText.setText("");
		if (!portTextField.getText().equals("")) {
			if (!dbPasswordTextField.getText().equals("")) {
				if (checkPortFormat()) {
					String[] argStr = new String[4];
					argStr[0] = portTextField.getText();
					argStr[1] = dbNameTextField.getText();
					argStr[2] = dbUsernameTextField.getText();
					argStr[3] = dbPasswordTextField.getText();
					serverController = ServerController.getInstance(Integer.parseInt(argStr[0]), argStr[1], argStr[2],
							argStr[3]);
					if (ServerController.isConnectedToDB() == false) {
						serverErrorText.setText("Incorrent DB login");
					} else {
						try {
							ipTextField.setText(InetAddress.getLocalHost().getHostAddress());

						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						serverConStatusLabel.setText("Connected");
						isConnected = true;
						serverConStatusLabel.setTextFill(Paint.valueOf("#3CB371"));
					}
				} else {
					serverErrorText.setText("Port number does not match format. Ex '5555'");
				}

			} else {
				serverErrorText.setText("Please Enter a password");
			}
		} else {
			serverErrorText.setText("Please enter port number.");
		}

	}

	/**
	 * Check port format that was entered to make sure it is a valid format.
	 *
	 * @return true, if the format is good
	 */
	private boolean checkPortFormat() {
		String[] portTextFieldArr = portTextField.getText().split("");
		for (String str : portTextFieldArr) {
			if (str.compareTo("0") < 0 || str.compareTo("9") > 0)
				return false;
		}
		return true;
	}

	/**
	 * Disconnect server connection to close the server.
	 *
	 * @param event - an ActionEvent from the ServerForm
	 */
	@FXML
	void disconnectServer(ActionEvent event) {
		if (serverController != null) {
			serverController.disconnectServer();
			serverConStatusLabel.setText("Disconnected");
			serverConStatusLabel.setTextFill(Paint.valueOf("#FF0000"));
		}
	}

	/**
	 * Show the list of clients that are connected to the server
	 *
	 * @param event - an ActionEvent from the ServerForm
	 */
	@FXML
	void showClients(ActionEvent event) {
		showClientsTextArea.setText("");
		if (serverController != null && serverController.getSC().getClientConnections().length > 0) {
			showClientsTextArea.setText("");
			for (Thread client : serverController.getSC().getClientConnections()) {
				showClientsTextArea.appendText(client.toString());
				showClientsTextArea.appendText("\n");
			}
			String[] hostANDip = serverController.getSC().getClientConnections()[0].toString().split(" ");
			// serverConHostNameLabel.setText(hostANDip[0]);
			StringBuilder host = new StringBuilder(hostANDip[1]);
			host.deleteCharAt(0);
			host.deleteCharAt(host.length() - 1);
			// serverConIPLabel.setText(host.toString());
		}
	}

	/**
	 * Start the serverForm gui that displays the server portal.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = null;
		VBox vbox;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/gui/ServerForm.fxml")); // LOADER EXCEPTION THROWN
			vbox = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Scene scene = new Scene(vbox);
		primaryStage.setTitle("BiteMe");
		primaryStage.setScene(scene);

		primaryStage.show();

	}

}
