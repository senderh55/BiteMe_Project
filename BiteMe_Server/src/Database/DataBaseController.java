package Database;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import restaurantNumber.RestaurantNumberController;
import utils.Credit;
import utils.E_DishCategory;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;
import w4c.W4CNumberController;

/**
 * The Class DataBaseController that handles the request between the DB and the server.
 */
public class DataBaseController {

	/** The conn. */
	private static Connection conn;

	

	/**
	 * Connect to DB.
	 *
	 * @param dbName the db name
	 * @param dbUsername the db username
	 * @param dbPassword the db password
	 * @return true, if successful
	 */
	public boolean connectToDB(String dbName, String dbUsername, String dbPassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
			return false;
		}

		try {
			String dbPath = "jdbc:mysql://127.0.0.1/" + dbName + "?serverTimezone=IST";
			conn = DriverManager.getConnection(dbPath, dbUsername, dbPassword);
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
		return true;
	}



	/**
	 * Handle DB query and return information if needed.
	 *
	 * @param message - Object represents the message to be handled for the DB.
	 * @return A result message to send the information back to the sender
	 */
	public synchronized Message HandleDBQuery(Message message) {
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder tupple = new StringBuilder();
		Message resultMessage = null;
		ResultSetMetaData metadata;
		int rsColumnCount;
		try {
			stmt = conn.createStatement();

			switch (message.getOpCode()) {
			case E_INSERT_USER:
				String insertNewUser = "INSERT INTO biteme_db.user VALUES (" + (String) message.getMsgArrayL().get(0)
						+ ");";

				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate(insertNewUser));
				break;

			case E_UPDATE_USER:
				int w4cNumber = W4CNumberController.getInstance().getNextW4CNumber();
				String updateNewUser = "UPDATE user SET userType = 'E_REG_USER', accountStatus = 'E_ACTIVE', businessName = 'NA', businessW4CNumber = 0, businessMonthlyLimit = 0, businessAmountUsed = 0, W4CNumber = "
						+ w4cNumber + ", " + (String) message.getMsgArrayL().get(0);
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate(updateNewUser));
				break;

			case E_INSERT_BUSINESS_USER:
				int regularw4cNumber = W4CNumberController.getInstance().getNextW4CNumber();
				String insertNewBusinessUser = "UPDATE user SET userType = 'E_UNAPPROVED_BUSINESS_USER', accountStatus = 'E_ACTIVE', W4CNumber = "
						+ regularw4cNumber + ", " + (String) message.getMsgArrayL().get(0);
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate(insertNewBusinessUser));
				break;
			case E_INSERT_NEW_BUSINESS:
				String insertNewBusiness = "INSERT INTO biteme_db.business VALUES ("
						+ W4CNumberController.getInstance().getNextbusinessW4CNumber() + ", "
						+ (String) message.getMsgArrayL().get(0) + ");";
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate(insertNewBusiness));
				break;

			case E_INSERT_HR_USER:
				String insertHRUser = "UPDATE user SET userType = 'E_HR', accountStatus = 'E_ACTIVE', businessName = 'NA', businessW4CNumber = 0, businessMonthlyLimit = 0, businessAmountUsed = 0, W4CNumber = 0 "
						+ (String) message.getMsgArrayL().get(0);
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate(insertHRUser));
				break;
			case E_UPDATE_HR:
				String updateHRUser = "UPDATE user SET businessW4CNumber = "

						+ W4CNumberController.getInstance().getBusinessW4CNumber() + ", "
						+ (String) message.getMsgArrayL().get(0);
				stmt.executeUpdate(updateHRUser);
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_SET_W4C_FOR_HR);
				resultMessage.getMsgArrayL().add(W4CNumberController.getInstance().getBusinessW4CNumber());
				break;
			case E_INSERT_NEW_RESTAURANT:
				int restaurantNumber = RestaurantNumberController.getInstance().getNextRestaurantNumber();
				String insertNewRestaurant = "INSERT INTO biteme_db.restaurant VALUES (" + restaurantNumber + ", "
						+ (String) message.getMsgArrayL().get(0) + ");";
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate(insertNewRestaurant));
				break;

			case E_UPDATE_USER_TO_RESTAURANT:
				int restaurantNumberForUser = RestaurantNumberController.getInstance().getRestaurantNumber();
				String updateUserToRestaurant = "UPDATE user SET manageRestID = " + restaurantNumberForUser
						+ (String) message.getMsgArrayL().get(0) + ";";
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate(updateUserToRestaurant));
				break;
			case E_UPDATE_LOGIN:
			case E_UPDATE:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate((String) message.getMsgArrayL().get(0)));
				break;
			case E_GET_REPORTS_FOR_QUATERLY:
			case E_GET_ALL_ORDERS_THIS_MONTH:
			case E_GET_REST_REPORTS:
			case E_GET_ALL_RESTID:
			case E_SELECT:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_SELECT);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;
			case E_REST_INFO:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_REST_INFO);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;

			case E_GETALLUSERS:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GETALLUSERS);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;

			case E_GET_BUSINESS:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_BUSINESS);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;
			case E_GET_USERS_IN_ORDER_GRUOP:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_USERS_IN_ORDER_GRUOP);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();
				int count = 0;
				while (rs.next()) {
					count++;
				}
				resultMessage.getMsgArrayL().add(count);
				return resultMessage;

			case E_GET_MENU:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_MENU);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;

			case E_LOGIN:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_LOGIN);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();
				if (!rs.isBeforeFirst()) {
					resultMessage.getMsgArrayL().add("Incorrect Login details");
					return resultMessage;
				}
				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;
			case E_BUSINESS_AMNT_USED:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_BUSINESS_AMNT_USED);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate((String) message.getMsgArrayL().get(0)));
				break;

			case E_ADD_ORDER:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_ADD_ORDER);
				// Order number is of the following form: Branch-RestID-OrderNumber for example
				StringBuilder addOrderString = new StringBuilder();
				String orderNumber = (String) message.getMsgArrayL().get(1);
				addOrderString.append("INSERT INTO biteme_db.order " + "VALUES ('");
				addOrderString.append(orderNumber);
				addOrderString.append((String) message.getMsgArrayL().get(0));
				resultMessage.getMsgArrayL().add(stmt.executeUpdate(addOrderString.toString()));
				resultMessage.getMsgArrayL().add(orderNumber);

				break;
			case E_GET_BUSINESS_COLLEAGUES:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_BUSINESS_COLLEAGUES);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();
				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;
			case E_REPORTS_DATA:
				// restID , month, year , Branch , reportsMap, ......
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_REPORTS_DATA);
				double totalInDB = 0;
				int amountInDB = 0;
				double newTotal;
				int newAmount;
				int restID = (int) message.getMsgArrayL().get(0);
				int month = (int) message.getMsgArrayL().get(1);
				int year = (int) message.getMsgArrayL().get(2);
				String branch = ((String) message.getMsgArrayL().get(3));

				Map<E_DishCategory, double[]> reportsMap = (Map<E_DishCategory, double[]>) message.getMsgArrayL()
						.get(4);

				for (E_DishCategory dishCategory : reportsMap.keySet()) {
					double sumFromOrder = reportsMap.get(dishCategory)[0];
					int amountFromOrder = (int) reportsMap.get(dishCategory)[1];

					rs = stmt.executeQuery("SELECT amount, total FROM biteme_db.restreports" + " WHERE restID = '"
							+ restID + "' AND month = '" + month + "' AND year = '" + year + "' AND branch = '" + branch
							+ "' AND dishType = '" + dishCategory + "';");
					if (rs.next()) {
						amountInDB = Integer.parseInt(rs.getString(1));
						totalInDB = Double.parseDouble(rs.getString(2));
						newAmount = amountInDB + amountFromOrder;
						newTotal = totalInDB + sumFromOrder;
						resultMessage.getMsgArrayL()
								.add(stmt.executeUpdate("UPDATE biteme_db.restreports SET amount = '" + newAmount
										+ "', total = '" + newTotal + "' WHERE restID = '" + restID + "' AND month = '"
										+ month + "' AND year = '" + year + "' AND branch = '" + branch
										+ "' AND dishType = '" + dishCategory + "';"));
					} else {
						resultMessage.getMsgArrayL()
								.add(stmt.executeUpdate("INSERT INTO biteme_db.restreports VALUES ('" + restID + "', '"
										+ month + "', '" + year + "', '" + branch + "', '" + dishCategory.toString()
										+ "', '" + amountFromOrder + "', '" + sumFromOrder + "');"));
					}
				}

				break;
			case E_GET_RESTAURANT_ORDERS:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_RESTAURANT_ORDERS);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();
				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;
			case E_UPDATE_DISH:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_UPDATE_DISH);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate((String) message.getMsgArrayL().get(0)));
				break;
			case E_DELETE_DISH:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_DELETE_DISH);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate((String) message.getMsgArrayL().get(0)));
				break;
			case E_GET_SPECIFIC_RESTAURANT:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_SPECIFIC_RESTAURANT);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;
			case E_RESTAURANT_APPROVE_ORDER:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_RESTAURANT_APPROVE_ORDER);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate((String) message.getMsgArrayL().get(0)));

				// remove client from map
				// send him a message

				break;
			case E_RESTAURANT_ORDER_READY:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_RESTAURANT_ORDER_READY);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate((String) message.getMsgArrayL().get(0)));
				break;
			case E_CLIENT_ORDER_RECIEVED:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_CLIENT_ORDER_RECIEVED);
				resultMessage.getMsgArrayL().add(stmt.executeUpdate((String) message.getMsgArrayL().get(0)));

				String orderNumToCheckDel = (String) message.getMsgArrayL().get(1);

				ArrayList<Credit> creditsList = (ArrayList<Credit>) message.getMsgArrayL().get(2);
				int restid = (int) message.getMsgArrayL().get(3);
				int userid = (int) message.getMsgArrayL().get(4);

				String bringDelTimes = "SELECT timeApproved, timeRecieved, isEarlyOrder, total FROM biteme_db.order WHERE orderNumber = '"
						+ orderNumToCheckDel + "';";

				rs = stmt.executeQuery(bringDelTimes);

				if (rs.next()) {
					String timeApp = rs.getString(1);
					String timeRec = rs.getString(2);
					int isEarlyOrder = Integer.parseInt(rs.getString(3));
					double total = Double.parseDouble(rs.getString(4));
					double credit = 0;

					LocalTime timeA = LocalTime.parse(timeApp);
					LocalTime timeR = LocalTime.parse(timeRec);
					long diff = ChronoUnit.MINUTES.between(timeA, timeR);

					if (isEarlyOrder == 1) {
						if (diff > 20) {

							Credit.addCreditToList(creditsList, total * 0.50, restid);
							String setDelLate = "UPDATE biteme_db.order SET delOnTime = 'E_LATE' WHERE orderNumber = '"
									+ orderNumToCheckDel + "';";
							stmt.executeUpdate(setDelLate);

							String updateCredits = "UPDATE biteme_db.user SET credits = '"
									+ creditsList.toString().substring(1, creditsList.toString().length() - 1)
									+ "' WHERE userID = '" + userid + "';";
							stmt.executeUpdate(updateCredits);
						} else {
							String setDelOnTime = "UPDATE biteme_db.order SET delOnTime = 'E_ON_TIME' WHERE orderNumber = '"
									+ orderNumToCheckDel + "';";
							stmt.executeUpdate(setDelOnTime);
						}
					} else {
						if (diff > 60) {
							Credit.addCreditToList(creditsList, total * 0.50, restid);
							String setDelLate = "UPDATE biteme_db.order SET delOnTime = 'E_LATE' WHERE orderNumber = '"
									+ orderNumToCheckDel + "';";
							stmt.executeUpdate(setDelLate);

							String updateCredits = "UPDATE biteme_db.user SET credits = '"
									+ creditsList.toString().substring(1, creditsList.toString().length() - 1)
									+ "' WHERE userID = '" + userid + "';";
							stmt.executeUpdate(updateCredits);
						} else {
							String setDelOnTime = "UPDATE biteme_db.order SET delOnTime = 'E_ON_TIME' WHERE orderNumber = '"
									+ orderNumToCheckDel + "';";
							stmt.executeUpdate(setDelOnTime);
						}
					}
				}

				break;
			case E_BRING_REPORTS:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_BRING_REPORTS);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;
			case E_GET_OPEN_ORDERS:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_OPEN_ORDERS);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;

			case E_GET_ALL_USER_ORDERS:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_ALL_USER_ORDERS);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;
			case E_GET_REST_COMMISSIONS:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_REST_COMMISSIONS);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i <= rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
				}
				return resultMessage;
			case E_SAVE_QUARTERLY_REPORT:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_SAVE_QUARTERLY_REPORT);
				resultMessage.getMsgArrayL().add("Ignore");
				int quarterReport = (int) message.getMsgArrayL().get(0);
				int yearReport = (int) message.getMsgArrayL().get(1);
				String branchReport = (String) message.getMsgArrayL().get(2);
				InputStream is = new ByteArrayInputStream((byte[]) message.getMsgArrayL().get(3));
				String sql = "INSERT INTO quarterlyreports VALUES(?, ?, ?, ?)";
				try {
					PreparedStatement statement = conn.prepareStatement(sql);
					statement.setInt(1, quarterReport);
					statement.setInt(2, yearReport);
					statement.setString(3, branchReport);
					statement.setBlob(4, is);
					statement.executeUpdate();
				} catch (SQLIntegrityConstraintViolationException e) {
					System.out.println("Duplicate Primary Key");
				}
				break;
			case E_BRANCHMAN_TO_CEO_REPORT:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_BRANCHMAN_TO_CEO_REPORT);
				resultMessage.getMsgArrayL().add("Ignore");
				String branchReportCEO = (String) message.getMsgArrayL().get(0);
				int quarterReportCEO = (int) message.getMsgArrayL().get(1);
				int yearReportCEO = (int) message.getMsgArrayL().get(2);

				InputStream isCEO = new ByteArrayInputStream((byte[]) message.getMsgArrayL().get(3));
				String sqlCEO = "INSERT INTO branchmantoceo VALUES(?, ?, ?, ?)";
				try {
					PreparedStatement statement = conn.prepareStatement(sqlCEO);
					statement.setString(1, branchReportCEO);
					statement.setInt(2, quarterReportCEO);
					statement.setInt(3, yearReportCEO);
					statement.setBlob(4, isCEO);
					statement.executeUpdate();
				} catch (SQLIntegrityConstraintViolationException e) {
					System.out.println("Duplicate Primary Key");
				}
				break;
			case E_GET_QUARTERLY_REPORT:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_QUARTERLY_REPORT);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				rs.next();
				for (int i = 1; i < rsColumnCount; i++) {
					resultMessage.getMsgArrayL().add(rs.getString(i));
				}

				Blob blob = rs.getBlob("pdfReport");

				int blobLength = (int) blob.length();
				byte[] blobAsBytes = blob.getBytes(1, blobLength);

				// release the blob and free up memory. (since JDBC 4.0)
				blob.free();
				resultMessage.getMsgArrayL().add(blobAsBytes);

				return resultMessage;
			case E_GET_BRANCHMAN_TO_CEO_REPORTS:
				resultMessage = new Message(OwnerEnum.E_SERVER, OpEnum.E_GET_BRANCHMAN_TO_CEO_REPORTS);
				rs = stmt.executeQuery((String) message.getMsgArrayL().get(0));
				metadata = (ResultSetMetaData) rs.getMetaData();
				rsColumnCount = metadata.getColumnCount();

				while (rs.next()) {
					tupple.setLength(0);
					for (int i = 1; i < rsColumnCount; i++) {
						tupple.append(rs.getString(i) + ";");
					}
					resultMessage.getMsgArrayL().add(tupple.toString());
					
					Blob blobCEO = rs.getBlob("pdfFile");

					int blobLengthCEO = (int) blobCEO.length();
					byte[] blobAsBytesCEO = blobCEO.getBytes(1, blobLengthCEO);

					// release the blob and free up memory. (since JDBC 4.0)
					blobCEO.free();
					resultMessage.getMsgArrayL().add(blobAsBytesCEO);
				}
				return resultMessage;
			default:
				System.out.println("NEED TO IMPLEMENT THE MESSAGE OPENUM");
				break;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultMessage;
	}

	/**
	 * Gets the all order numbers.
	 *
	 * @return the all order numbers
	 */
	public ArrayList<String> getAllOrderNumbers() {
		Statement stmt;
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT orderNumber FROM biteme_db.order;");

			while (rs.next()) {
				result.add(rs.getString(1).substring(6));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * Gets the MAX W4C numbers in the DB in order to continue from the max.
	 *
	 * @return the MAX W4C numbers
	 */
	public ArrayList<Integer> getMAXw4cNumbers() {
		Statement stmt;
		ResultSet rs = null;
		ArrayList<Integer> result = new ArrayList<Integer>();
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT W4CNumber FROM biteme_db.user ORDER BY W4CNumber DESC;");
			if (!rs.next()) {
				result.add(1000);
			} else if (Integer.parseInt(rs.getString(1)) == 0) {
				result.add(1000);
			}
			else{
				result.add(Integer.parseInt(rs.getString(1)));
			}

			rs = stmt.executeQuery("SELECT businessW4C FROM business ORDER BY businessW4C DESC;");
			if (!rs.next()) {
				result.add(5000);
			} else if (Integer.parseInt(rs.getString(1)) == 0) {
				result.add(5000);
			}
			else{
				result.add(Integer.parseInt(rs.getString(1)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * Gets the MAX restaurantID numbers in the DB in order to continue from the max.
	 *
	 * @return the MAX restaurantID numbers
	 */
	public ArrayList<Integer> getMAXrestaurantNumber() {
		Statement stmt;
		ResultSet rs = null;
		ArrayList<Integer> result = new ArrayList<Integer>();
		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT restaurantID FROM biteme_db.restaurant ORDER BY restaurantID DESC;");
			if (!rs.next()) {
				result.add(0);
			} else {
				result.add(Integer.parseInt(rs.getString(1)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
