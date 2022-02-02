package server;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Database.DataBaseController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import orderNumber.OrderNumberController;
import report.Report;
import reports.ReportsHandler;
import restaurantNumber.RestaurantNumberController;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;
import w4c.W4CNumberController;

/**
 * This class is the functionality for the ServerController.
 */
public class ServerController extends AbstractServer implements Runnable {
	
	/** The db. */
	private DataBaseController DB;
	
	/** The boolean if we connected to DB. */
	private static boolean isConnectedToDB;
	
	/** The order number controller. */
	private OrderNumberController orderNumberController;
	
	/** The reports handler. */
	private ReportsHandler reportsHandler;
	
	/** The W4C number controller. */
	private W4CNumberController w4cNumberController;
	
	/** The restaurant number controller. */
	private RestaurantNumberController restaurantNumberController;

	/** The server entity. */
	private Server server = new Server();
	
	/** The server controller instance. */
	private static ServerController serverControllerInstance = null;

	/** The client restaurant order map to save the connection between a client and a restaurant. */
	private Map<String, ArrayList<ConnectionToClient>> clientRestaurantOrderMap = new HashMap<>();
	
	/** The rest clients to save the connection of restaurants. */
	private Map<Integer, ConnectionToClient> restClients = new HashMap<>();
	
	/** The Constant DEFAULT_PORT. */
	final public static int DEFAULT_PORT = 5555;

	/**
	 * Instantiates a new server controller, connect to the DB, initiate the numbers.
	 *
	 * @param port the port
	 * @param dbName the db name
	 * @param dbUsername the db username
	 * @param dbPassword the db password
	 */
	private ServerController(int port, String dbName, String dbUsername, String dbPassword) {
		super(port);
		server.setPort(port);

		DB = new DataBaseController();
		isConnectedToDB = DB.connectToDB(dbName, dbUsername, dbPassword);
		if(!isConnectedToDB) {
			serverControllerInstance = null;
			return;
		}
			
		orderNumberController = OrderNumberController.getInstance();
		getLastOrderNumber();
		reportsHandler = new ReportsHandler(this);
		getLastReportCreatedMonth();
		getLastReportCreatedQuarterly();
		w4cNumberController = W4CNumberController.getInstance();
		restaurantNumberController = RestaurantNumberController.getInstance();
		getLastW4CNumber();
		getLastRestaurantNumber();
		Runnable run = new Runnable() {
			public void run() {
				try {
					LocalDate lastUpdateMonthly = LocalDate.now().minusYears(1);

					while (true) {
						if (lastUpdateMonthly.isBefore(LocalDate.now())) {
							LocalDate now = LocalDate.now(); // 2015-11-23
							if (now.getDayOfMonth() == 1) {
								if (reportsHandler.getMonthOfLastReports() < now.getMonthValue()
										|| reportsHandler.getYearOfLastReports() < now.getYear()) {
									lastUpdateMonthly = LocalDate.now();
									handleMonthlyReports();
								}
								if (reportsHandler.getQuarterOfLastQuarterReports() < now.get(IsoFields.QUARTER_OF_YEAR)
										|| reportsHandler.getYearOfLastQuarterReports() < now.getYear()) {
									handleQuaterlyReports();
								}
							}
							// Thread.sleep(60000*1440);

							// WAIT AN HOUR IN BETWEEN TRYING
							Thread.sleep(3600000);
						}
					}
				} catch (InterruptedException e) {
					System.out.println("Interrupted");
				}
			}
		};
		new Thread(run).start();

	}

	/**
	 * Gets the single instance of ServerController.
	 *
	 * @param port the port
	 * @param dbName the db name
	 * @param dbUsername the db username
	 * @param dbPassword the db password
	 * @return single instance of ServerController
	 */
	public static ServerController getInstance(int port, String dbName, String dbUsername, String dbPassword) {
		if (serverControllerInstance == null || !isConnectedToDB) {
			serverControllerInstance = new ServerController(port, dbName, dbUsername, dbPassword);
			try {
				if(isConnectedToDB)
					serverControllerInstance.listen(); // Start listening for connections
			} catch (Exception ex) {
				System.out.println("ERROR - Could not listen for clients!");
			}
		}
		return serverControllerInstance;
	}

	/**
	 * Disconnect server from the connection.
	 */
	public void disconnectServer() {
		try {
			serverControllerInstance.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Server started.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * Server stopped.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * Handle message that was sent from the client.
	 *
	 * @param message the message from the client
	 * @param client the client connection to the server
	 */
	@Override
	protected void handleMessageFromClient(Object message, ConnectionToClient client) {

		try {
			clientRestaurantMessageProccesser((Message) message, client);
			System.out.println("Client: " + client.getName() + "Has sent a request to the DB");
			Message dbResult = (DB.HandleDBQuery((Message) message));
			System.out.println("DB sent a return message to Client: " + client.getName());
			client.sendToClient((Object) (dbResult));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Client restaurant message proccesser to handle special casses.
	 *
	 * @param message the message from the client
	 * @param client the client connection
	 */
	private void clientRestaurantMessageProccesser(Message message, ConnectionToClient client) {
		String orderNumber;
		switch (message.getOpCode()) {
		case E_UPDATE_LOGIN:
			if (message.getMsgArrayL().size() > 1)
				restClients.put((int) message.getMsgArrayL().get(1), client);
			break;
		case E_ADD_ORDER:
			orderNumber = (String) message.getMsgArrayL().get(1) + orderNumberController.getNextOrderNumber() + "";
			message.getMsgArrayL().remove(1);
			message.getMsgArrayL().add(1, orderNumber);
			ArrayList<ConnectionToClient> clientRestaurant = new ArrayList<>();
			clientRestaurant.add(client);
			int restID = (int) message.getMsgArrayL().get(2);
			if (restClients.containsKey(restID)) {
				if (!restClients.get(restID).isAlive()) {
					System.out.println("REST NOT ALIVE " + restID);

				} else {
					clientRestaurant.add(restClients.get(restID));
				}
			}

			clientRestaurantOrderMap.put(orderNumber, clientRestaurant);
			sendMessagRestaurantOrClient(orderNumber, OpEnum.E_NOTIFY_REST_OF_NEW_ORDER, "Restaurant");
			break;
		case E_RESTAURANT_APPROVE_ORDER:
			orderNumber = (String) message.getMsgArrayL().get(1);
			if (clientRestaurantOrderMap != null) {
				if (clientRestaurantOrderMap.containsKey(orderNumber)) {
					clientRestaurantOrderMap.get(orderNumber).add(client);
					sendMessagRestaurantOrClient(orderNumber, OpEnum.E_NOTIFY_CLIENT_ORDER_APPROVED, "Client");
				}
			}
			break;
		case E_RESTAURANT_ORDER_READY:
			orderNumber = (String) message.getMsgArrayL().get(1);
			sendMessagRestaurantOrClient(orderNumber, OpEnum.E_NOTIFY_CLIENT_ORDER_IN_ROUTE, "Client");
			break;
		case E_CLIENT_ORDER_RECIEVED:
			orderNumber = (String) message.getMsgArrayL().get(1);
			sendMessagRestaurantOrClient(orderNumber, OpEnum.E_RESTAURANT_ORDER_RECIEVED, "Restaurant");
			break;
		}
	}

	/**
	 * Send a message to a restaurant or client connection.
	 *
	 * @param orderNumber the order number we are referring too
	 * @param messageOpEnum the message OpEnum to be put in the message that is being sent
	 * @param toWho who is the message being sent to
	 */
	private void sendMessagRestaurantOrClient(String orderNumber, OpEnum messageOpEnum, String toWho) {
		Message messageToClient = new Message(OwnerEnum.E_SERVER, messageOpEnum);
		messageToClient.getMsgArrayL().add(1);
		try {
			if (toWho.equals("Client")) {
				// Send messsage to the client that the order was approved
				if (clientRestaurantOrderMap.get(orderNumber) != null
						&& clientRestaurantOrderMap.get(orderNumber).get(0) != null
						&& clientRestaurantOrderMap.get(orderNumber).get(0).isAlive())
					clientRestaurantOrderMap.get(orderNumber).get(0).sendToClient((Object) (messageToClient));
			} else {
				// Send messsage to the client that the order was approved
				if (clientRestaurantOrderMap.get(orderNumber) != null
						&& clientRestaurantOrderMap.get(orderNumber).size() > 1
						&& clientRestaurantOrderMap.get(orderNumber).get(1) != null
						&& clientRestaurantOrderMap.get(orderNumber).get(1).isAlive())
					clientRestaurantOrderMap.get(orderNumber).get(1).sendToClient((Object) (messageToClient));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Handle monthly reports, create the monthly reports.
	 */
	public void handleMonthlyReports() {
		// once a month cycle this:
		// get info needed for reports from db
		// send the info to a method in handle reports
		// returns the results to the DB
		LocalDate monthToHandle = LocalDate.now().minusMonths(1);
		int month = monthToHandle.getMonthValue();
		int year = monthToHandle.getYear();

		ArrayList<Integer> allRestID = new ArrayList<>();
		ArrayList<String> allRestReports = new ArrayList<>();
		ArrayList<String> allOrders = new ArrayList<>();
		Message getRestID = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_ALL_RESTID);
		getRestID.getMsgArrayL().add("SELECT restaurantID FROM restaurant;");

		for (Object restID : DB.HandleDBQuery(getRestID).getMsgArrayL()) {
			allRestID.add(Integer.parseInt(((String) restID).substring(0, ((String) restID).length() - 1)));
		}

		Message getRestReports = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_REST_REPORTS);
		getRestReports.getMsgArrayL()
				.add("SELECT * FROM restreports WHERE month = '" + month + "' AND year = '" + year + "';");
		for (Object restReport : DB.HandleDBQuery(getRestReports).getMsgArrayL()) {
			allRestReports.add((String) restReport);
		}

		TreeMap<Integer, ArrayList<Report>> reportsForRestaurants = reportsHandler.buildReportsList(allRestReports);

		LocalDate beg = LocalDate.now().minusMonths(1);
		String begMonth = year + "-" + month + "-01";
		String endMonth = year + "-" + month + "-" + beg.getMonth().length(beg.isLeapYear());

		Message getAllOrderThisMonth = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_ALL_ORDERS_THIS_MONTH);
		getAllOrderThisMonth.getMsgArrayL().add(
				"SELECT * FROM biteme_db.order WHERE dateDesired BETWEEN '" + begMonth + "' AND '" + endMonth + "';");

		for (Object restReport : DB.HandleDBQuery(getAllOrderThisMonth).getMsgArrayL()) {
			allOrders.add((String) restReport);
		}
		reportsHandler.buildOrderList(allOrders);
		reportsHandler.buildMonthlyReports();
		reportsHandler.setMonthOfLastReports(month);
		reportsHandler.setYearOfLastReports(year);
	}

	/**
	 * Handle quaterly reports, creates the quarterly reports.
	 */
	public void handleQuaterlyReports() {
		Message getreports = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_REPORTS_FOR_QUATERLY);

		YearMonth month = YearMonth.from(LocalDate.now());
		LocalDate now = month.atEndOfMonth();
		LocalDate threeMonthsBack = month.atDay(1).minusMonths(3);

		String getReportsQuery = "SELECT * FROM monthlyreports WHERE date BETWEEN '" + threeMonthsBack + "' AND '" + now
				+ "' ORDER BY date;";
		getreports.getMsgArrayL().add(getReportsQuery);
		ArrayList<String> monthlyReportsThreeMonths = new ArrayList<>();
		for (Object reportIn : DB.HandleDBQuery(getreports).getMsgArrayL()) {
			monthlyReportsThreeMonths.add((String) reportIn);
		}

		String getRestName = "SELECT restaurantID, restName FROM restaurant;";
		Message getRestNamesMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_SELECT);
		getRestNamesMessage.getMsgArrayL().add(getRestName);
		ArrayList<String> restIdNames = new ArrayList<>();
		for (Object rest : DB.HandleDBQuery(getRestNamesMessage).getMsgArrayL()) {
			restIdNames.add((String) rest);
		}
		reportsHandler.buildRestIdNameMap(restIdNames);
		reportsHandler.buildQuaterlyReportsList(monthlyReportsThreeMonths);

	}

	/**
	 * Insert monthly report into the DB.
	 *
	 * @param monthlyReportForRest the monthly report for rest
	 */
	public void insertMonthlyReport(String monthlyReportForRest) {
		Message report = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE);
		report.getMsgArrayL().add(monthlyReportForRest);

		DB.HandleDBQuery(report);

	}

	/**
	 * Gets the sc.
	 *
	 * @return the sc
	 */
	public ServerController getSC() {
		return serverControllerInstance;
	}

	/**
	 * Gets the order number controller.
	 *
	 * @return the order number controller
	 */
	public OrderNumberController getOrderNumberController() {
		return orderNumberController;
	}

	/**
	 * Gets the last order number from the orders in the db and save the order num.
	 *
	 */
	private void getLastOrderNumber() {
		ArrayList<String> allOrderNumbers = DB.getAllOrderNumbers();
		if (allOrderNumbers.size() > 0) {
			int lastOrderNum = Integer.parseInt(allOrderNumbers.get(0));

			for (int i = 1; i < allOrderNumbers.size(); i++) {
				if (lastOrderNum < Integer.parseInt(allOrderNumbers.get(i))) {
					lastOrderNum = Integer.parseInt(allOrderNumbers.get(i));
				}
			}
			orderNumberController.setNextOrderNumber(lastOrderNum);
		}
	}

	/**
	 * Gets the last report created month from the reports in the db and save the month and year.
	 *
	 */
	private void getLastReportCreatedMonth() {
		String maxReportMonthYear = "SELECT MAX(date) FROM monthlyReports;";
		Message getLastMonthYear = new Message(OwnerEnum.E_SERVER, OpEnum.E_SELECT);
		getLastMonthYear.getMsgArrayL().add(maxReportMonthYear);
		String result = (String) DB.HandleDBQuery(getLastMonthYear).getMsgArrayL().get(0);
		if (!result.equals("null;")) {
			int lastMonth = LocalDate.parse(result.substring(0, result.length() - 1)).getMonthValue();
			int lastYear = LocalDate.parse(result.substring(0, result.length() - 1)).getYear();

			reportsHandler.setMonthOfLastReports(lastMonth);
			reportsHandler.setYearOfLastReports(lastYear);
		}
	}

	/**
	 * Gets the last report created quarterly and save the quarter and year.
	 *
	 */
	private void getLastReportCreatedQuarterly() {
		String maxReportQuarterlyYear = "SELECT quarter, year  FROM biteme_db.quarterlyreports order by year DESC, quarter DESC LIMIT 1;";
		Message getLastQuarterlyYear = new Message(OwnerEnum.E_SERVER, OpEnum.E_SELECT);
		getLastQuarterlyYear.getMsgArrayL().add(maxReportQuarterlyYear);
		String result = (String) DB.HandleDBQuery(getLastQuarterlyYear).getMsgArrayL().get(0);
		if (!result.equals("null;")) {
			String[] splitter = result.split(";");
			int lastQuarter = Integer.parseInt(splitter[0]);
			int lastYear = Integer.parseInt(splitter[1]);

			reportsHandler.setQuarterOfLastQuarterReports(lastQuarter);
			reportsHandler.setYearOfLastQuarterReports(lastYear);
		}
	}

	/**
	 * Gets the last W 4 C number from the DB and save the last num.
	 *
	 */
	private void getLastW4CNumber() {
		ArrayList<Integer> maxW4Cnumber = DB.getMAXw4cNumbers();
		w4cNumberController.setNextW4CNumber(maxW4Cnumber.get(0));
		w4cNumberController.setNextbusinessW4CNumber(maxW4Cnumber.get(1));
	}

	/**
	 * Gets the last restaurant number from the db.
	 *
	 */
	private void getLastRestaurantNumber() {
		ArrayList<Integer> maxRestaurantnumber = DB.getMAXrestaurantNumber();
		restaurantNumberController.setNextRestaurantNumber(maxRestaurantnumber.get(0));
	}

	/**
	 * Gets the client restaurant order map.
	 *
	 * @return the client restaurant order map
	 */
	public Map<String, ArrayList<ConnectionToClient>> getClientRestaurantOrderMap() {
		return clientRestaurantOrderMap;
	}

	/**
	 * Import users from external system (User Portal).
	 */
	public void importUsersFromExternalSystem() {
		Message importUsers = new Message(OwnerEnum.E_SERVER, OpEnum.E_SELECT);
		Message exportUsers = new Message(OwnerEnum.E_SERVER, OpEnum.E_INSERT_USER);
		String selectFromExternalUsers = "SELECT * FROM external_users;";
		importUsers.getMsgArrayL().add(selectFromExternalUsers);
		for (Object restReport : DB.HandleDBQuery(importUsers).getMsgArrayL()) {
			exportUsers.getMsgArrayL().clear();
			String userToExport = buildUserFromExternal((String) restReport);
			exportUsers.getMsgArrayL().add(userToExport);
			DB.HandleDBQuery(exportUsers);

		}
	}

	/**
	 * Builds the user from external db (User Portal).
	 *
	 * @param userInfo the user info in the form of a string
	 * @return the string of the user information to be entered into the db
	 */
	private String buildUserFromExternal(String userInfo) {
		String[] splitter = userInfo.split(";");
		StringBuilder user = new StringBuilder();
		user.append(splitter[0] + ", "); // ID
		user.append("'" + splitter[1] + "', "); // private name
		user.append("'" + splitter[2] + "', "); // last name
		user.append("'" + splitter[3] + "', "); // userName
		user.append("'" + splitter[4] + "', "); // password
		user.append("'" + splitter[5] + "', "); // email
		user.append(splitter[6] + ", "); // phone number
		user.append("'E_NA', "); // Default branch
		user.append("'" + splitter[7] + "', "); // role
		user.append("'E_NA', "); // user type
		user.append("0, 0, 'E_WAITING_APPROVAL', 0, 'NA', 0, 0, 0, 0, ");
		user.append("'" + splitter[8] + "', "); // credit card
		user.append("'" + splitter[9] + "', "); // restaurant name
		user.append("'" + splitter[10] + "', "); // description
		user.append("'" + splitter[11] + "', "); // address
		user.append("0"); // isGroupOrder
		return user.toString();
	}

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		String dbName = args[1];
		String dbUsername = args[2];
		String dbPassword = args[3];

		serverControllerInstance = ServerController.getInstance(port, dbName, dbUsername, dbPassword);

	}

	/**
	 * Save PDF byte array to the DB.
	 *
	 * @param pdfToByteArray the pdf report to byte array
	 * @param currentBranch the current branch to save the pdf under
	 * @param quarter the quarter to save the pdf under
	 * @param year the year to save the pdf under
	 */
	public void savePDFByteArray(byte[] pdfToByteArray, String currentBranch, int quarter, int year) {
		Message saveQuarterlyReport = new Message(OwnerEnum.E_SERVER, OpEnum.E_SAVE_QUARTERLY_REPORT);
		saveQuarterlyReport.getMsgArrayL().add(quarter);
		saveQuarterlyReport.getMsgArrayL().add(year);
		saveQuarterlyReport.getMsgArrayL().add(currentBranch);
		saveQuarterlyReport.getMsgArrayL().add(pdfToByteArray);
		DB.HandleDBQuery(saveQuarterlyReport);
		System.out.println("hello");

	}
	
	/**
	 * Checks if is connected to DB.
	 *
	 * @return true, if is connected to DB
	 */
	public static boolean isConnectedToDB() {
		return isConnectedToDB;
	}

}
