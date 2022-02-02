package client;

import java.io.IOException;

import ocsf.client.AbstractClient;
import user.UserViewController;
import utils.Message;
import utils.MessageController;

/**
 * The controller for the Client actions.
 */
public class ClientController extends AbstractClient {
	
	/** The message from server. */
	Message messageFromServer;
	
	/** The combined controller for many other controllers. */
	UserViewController userViewController;
	
	/** The message controller. */
	MessageController messageController;

	/**
	 * Initiates a new client controller.
	 *
	 * @param host- String represents the host
	 * @param port - int represents the port
	 * @param userViewController - UserViewController represents the user view controller
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ClientController(String host, int port, UserViewController userViewController) throws IOException {
		super(host, port); // Call the superclass constructor
		this.userViewController = userViewController;
		messageController = new MessageController(userViewController);
		openServerConnection();
	}

	/**
	 * Handle message from server.
	 *
	 * @param msgFromServer - Object represents the message from server
	 */
	@Override
	protected void handleMessageFromServer(Object msgFromServer) {
		// resultMsgFromServer = (ArrayList<String>)msgFromServer;
		messageFromServer = (Message) msgFromServer;
		messageController.processMessage(messageFromServer);
	}

	/**
	 * This method opens the connection between the client and the server
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void openServerConnection() throws IOException {
		openConnection();
	}

	/**
	 * Handle message from client UI.
	 *
	 * @param message - Message represents the message to the server
	 */
	public void handleMessageFromClientUI(Message message) {
		try {
			sendToServer((Object) message);
		} catch (IOException e) {
			// clientUI.display("Could not send message to server. Terminating client.");
			quit();
		}
	}

	/*
	 * Description: This method sends the client request to the server to be
	 * processed
	 */
//	public void handleRequestFromClientUI(String orderNum) {
//		try {
//			String msg = "SELECT * FROM biteme_db.order WHERE OrderNumber = " + orderNum + ";";
//			sendToServer(msg);
//		} catch (IOException e) {
//			// clientUI.display("Could not send message to server. Terminating client.");
//			quit();
//		}
/**
	 * Gets the message controller.
	 *
	 * @return MessageController represents the message controller
	 */
	public MessageController getMessageController() {
		return messageController;
	}

	/**
	 * Close client-server connection.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
