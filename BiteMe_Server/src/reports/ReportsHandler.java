package reports;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import Order.Order;
import report.MonthlyReport;
import report.Report;
import server.ServerController;
import utils.E_Branches;
import utils.E_DeliveredOnTime;
import utils.E_DeliveryType;
import utils.E_DishCategory;
import utils.E_OrderStatus;

/**
 * This class is the functionality for the ReportsHandler.
 */
public class ReportsHandler {

	/** The income reports. */
	String incomeReports;

	/** The month of last monthly report that was made. */
	int monthOfLastReports;

	/** The year of last monthly report that was made. */
	int yearOfLastReports;

	/** The quarter of last quarter report that was made. */
	int quarterOfLastQuarterReports;

	/** The year of last quarter report that was made. */
	int yearOfLastQuarterReports;

	/** The server controller. */
	ServerController serverController;

	/** The list of reports for a restaurants. */
	TreeMap<Integer, ArrayList<Report>> reportsForRestaurants = new TreeMap<>();

	/** The list of orders for restaurants. */
	TreeMap<Integer, ArrayList<Order>> ordersForRestaurants = new TreeMap<>();

	/** The list of LATE orders for a restaurant. */
	HashMap<Integer, ArrayList<Order>> performanceLate = new HashMap<>();

	/** The list of ON-TIME orders for a restaurant. */
	HashMap<Integer, ArrayList<Order>> performanceOnTime = new HashMap<>();

	/** The list of UNKNOWN orders for a restaurant. */
	HashMap<Integer, ArrayList<Order>> performanceUnkown = new HashMap<>();

	/** The quaterly reports for the NORTH branch. */
	HashMap<Integer, ArrayList<MonthlyReport>> quaterlyReportsNORTH = new HashMap<>();

	/** The quaterly reports for the CENTER branch. */
	HashMap<Integer, ArrayList<MonthlyReport>> quaterlyReportsCENTER = new HashMap<>();

	/** The quaterly reports for the SOUTH branch. */
	HashMap<Integer, ArrayList<MonthlyReport>> quaterlyReportsSOUTH = new HashMap<>();

	/** The rest id name map of all the restaurants and their names. */
	HashMap<Integer, String> restIdNameMap = new HashMap<>();

	/**
	 * Instantiates a new reports handler.
	 *
	 * @param serverController the server controller in order to save the
	 *                         serverController
	 */
	public ReportsHandler(ServerController serverController) {
		this.serverController = serverController;
	}

	/**
	 * Builds the reports list for the restaurants.
	 *
	 * @param reportsStrings the reports in the format of strings
	 * @return the tree map filled with the restaurantID - Arraylist of reports
	 */
	public TreeMap<Integer, ArrayList<Report>> buildReportsList(ArrayList<String> reportsStrings) {
		reportsForRestaurants.clear();
		for (int i = 0; i < reportsStrings.size(); i++) {
			String[] splitter = ((String) reportsStrings.get(i)).split(";");
			Report report = new Report();

			report.setRestID(Integer.parseInt(splitter[0]));
			report.setMonth(Integer.parseInt(splitter[1]));
			report.setYear(Integer.parseInt(splitter[2]));
			report.setBranch(E_Branches.valueOf(splitter[3]));
			report.setCategory(E_DishCategory.valueOf(splitter[4]));
			report.setAmount(Integer.parseInt(splitter[5]));
			report.setTotal(Double.parseDouble(splitter[6]));

			if (!reportsForRestaurants.containsKey(report.getRestID())) {
				ArrayList<Report> reportSpecificRestaurant = new ArrayList<>();
				reportSpecificRestaurant.add(report);
				reportsForRestaurants.put(report.getRestID(), reportSpecificRestaurant);
			} else {
				reportsForRestaurants.get(report.getRestID()).add(report);
			}
		}
		return reportsForRestaurants;

	}

	/**
	 * Builds the order list of the restaurants and their orders.
	 *
	 * @param allOrders the all orders
	 * @return the tree map of the restaurants and the list of their orders
	 */
	public TreeMap<Integer, ArrayList<Order>> buildOrderList(ArrayList<String> allOrders) {
		int restID;
		ordersForRestaurants.clear();
		for (String orderIn : allOrders) {

			String[] splitter = orderIn.split(";");
			Order order = new Order();
			order.setOrderNumber(splitter[0]);
			order.setRestID(Integer.parseInt(splitter[1]));
			order.setRestName(splitter[3]);
			order.setDesiredOrderTime(LocalTime.parse(splitter[4]));
			order.setDesiredOrderDate(LocalDate.parse(splitter[5]));
			order.setOrderApprovedByRestTime(LocalTime.parse(splitter[6]));
			order.setRecievedTime(LocalTime.parse(splitter[7]));
			// Skip: address, city, phonenumber
			order.setOrderStatus(E_OrderStatus.valueOf(splitter[11]));
			order.setDeliveryType(E_DeliveryType.valueOf(splitter[12]));
			order.setDelOnTime(E_DeliveredOnTime.valueOf(splitter[13]));
			order.setTotalPrice(Double.parseDouble(splitter[14]));
			// Skip: businessW4CNum, branch, isEarlyOrder

			restID = order.getRestID();

			switch (order.getDelOnTime()) {

			case E_ON_TIME:
				if (performanceOnTime.containsKey(restID)) {
					performanceOnTime.get(restID).add(order);
				} else {
					ArrayList<Order> ordersSpecificRest = new ArrayList<>();
					ordersSpecificRest.add(order);
					performanceOnTime.put(restID, ordersSpecificRest);
				}
				break;
			case E_LATE:
				if (performanceLate.containsKey(restID)) {
					performanceLate.get(restID).add(order);
				} else {
					ArrayList<Order> ordersSpecificRest = new ArrayList<>();
					ordersSpecificRest.add(order);
					performanceLate.put(restID, ordersSpecificRest);
				}
				break;
			case E_NA:
				if (performanceUnkown.containsKey(restID)) {
					performanceUnkown.get(restID).add(order);
				} else {
					ArrayList<Order> ordersSpecificRest = new ArrayList<>();
					ordersSpecificRest.add(order);
					performanceUnkown.put(restID, ordersSpecificRest);
				}
				break;
			}

			if (ordersForRestaurants.containsKey(restID)) {
				ordersForRestaurants.get(restID).add(order);
			} else {
				ArrayList<Order> ordersSpecificRest = new ArrayList<>();
				ordersSpecificRest.add(order);
				ordersForRestaurants.put(restID, ordersSpecificRest);
			}
		}
		return ordersForRestaurants;
	}

	/**
	 * Builds the monthly reports for each restaurant.
	 */
	public void buildMonthlyReports() {

		Set<Integer> allRestID = reportsForRestaurants.keySet();

		for (int restID : allRestID) {
			// INCOME AND ORDER REPORTS
			ArrayList<Report> restReports = reportsForRestaurants.get(restID);
			String branch = restReports.get(0).getBranch().toString();
			int appetizerAmnt = 0;
			int saladAmnt = 0;
			int soupAmnt = 0;
			int mainAmnt = 0;
			int dessertAmnt = 0;
			int drinksAmnt = 0;

			double appetizerSum = 0;
			double saladSum = 0;
			double soupSum = 0;
			double mainSum = 0;
			double dessertSum = 0;
			double drinksSum = 0;

			int totalAmnt = 0;
			double totalSum = 0;

			for (Report report : restReports) {

				switch (report.getCategory()) {
				case E_APPETIZER:
					appetizerAmnt = report.getAmount();
					appetizerSum = report.getTotal();
					break;
				case E_SALAD:
					saladAmnt = report.getAmount();
					saladSum = report.getTotal();
					break;
				case E_SOUP:
					soupAmnt = report.getAmount();
					soupSum = report.getTotal();
					break;
				case E_MAIN:
					mainAmnt = report.getAmount();
					mainSum = report.getTotal();
					break;
				case E_DESSERT:
					dessertAmnt = report.getAmount();
					dessertSum = report.getTotal();
					break;
				case E_DRINKS:
					drinksAmnt = report.getAmount();
					drinksSum = report.getTotal();
					break;
				}
			}
			totalAmnt = appetizerAmnt + saladAmnt + soupAmnt + mainAmnt + dessertAmnt + drinksAmnt;
			totalSum = appetizerSum + saladSum + soupSum + mainSum + dessertSum + drinksSum;

			int delOnTime = 0;
			int delLate = 0;
			int delUnkown = 0;
			float performanceRate = 0;

			if (ordersForRestaurants.containsKey(restID)) {
				// PERFORMANCE REPORTS

				// amount of orders in each category
				delOnTime = performanceOnTime.get(restID).size();
				delLate = performanceLate.get(restID).size();
				delUnkown = performanceUnkown.get(restID).size();
				performanceRate = (float) delOnTime / (delOnTime + delLate);
			}

			double fee = 0.07 + Math.random() * (0.12 - 0.07);
			String insertMonthlyReport = "INSERT INTO monthlyReports VALUES('" + restID + "', '" + LocalDate.now()
					+ "', '" + branch + "', '" + appetizerAmnt + "', '" + saladAmnt + "', '" + soupAmnt + "', '"
					+ mainAmnt + "', '" + dessertAmnt + "', '" + drinksAmnt + "', '" + totalAmnt + "', '" + totalSum
					+ "', '" + delOnTime + "', '" + delLate + "', '" + performanceRate + "', '" + fee * totalSum
					+ "', '" + fee + "', '" + restIdNameMap.get(restID) + "');";

			serverController.insertMonthlyReport(insertMonthlyReport);

		}
	}

	/**
	 * Builds the quaterly reports list for each branch.
	 *
	 * @param monthlyReportsThreeMonths the monthly reports for the last three
	 *                                  months in the form of strings
	 */
	public void buildQuaterlyReportsList(ArrayList<String> monthlyReportsThreeMonths) {
		quaterlyReportsNORTH.clear();
		quaterlyReportsCENTER.clear();
		quaterlyReportsSOUTH.clear();
		for (String monthlyReportIn : monthlyReportsThreeMonths) {
			String[] splitter = monthlyReportIn.split(";");
			MonthlyReport monthlyReport = new MonthlyReport();
			monthlyReport.setRestID(Integer.parseInt(splitter[0]));
			monthlyReport.setReportDate(LocalDate.parse(splitter[1]));
			monthlyReport.setBranch(E_Branches.valueOf(splitter[2]));
			monthlyReport.setAppetizerAmnt(Integer.parseInt(splitter[3]));
			monthlyReport.setSaladAmnt(Integer.parseInt(splitter[4]));
			monthlyReport.setSoupAmnt(Integer.parseInt(splitter[5]));
			monthlyReport.setMainAmnt(Integer.parseInt(splitter[6]));
			monthlyReport.setDessertAmnt(Integer.parseInt(splitter[7]));
			monthlyReport.setDrinkAmnt(Integer.parseInt(splitter[8]));
			monthlyReport.setTotalOrders(Integer.parseInt(splitter[9]));
			monthlyReport.setTotalSum(Double.parseDouble(splitter[10]));
			monthlyReport.setOrdersOnTimeAmnt(Integer.parseInt(splitter[11]));
			monthlyReport.setOrdersLateAmnt(Integer.parseInt(splitter[12]));
			monthlyReport.setPerformance(Float.parseFloat(splitter[13]));
			monthlyReport.setFeeSum(Double.parseDouble(splitter[14]));
			monthlyReport.setFee(Float.parseFloat(splitter[15]));
			monthlyReport.setRestName(splitter[16]);

			int restID = monthlyReport.getRestID();

			switch (monthlyReport.getBranch()) {
			case E_NORTH:
				if (!quaterlyReportsNORTH.containsKey(restID)) {
					ArrayList<MonthlyReport> monthlyReportsList = new ArrayList<>();
					monthlyReportsList.add(monthlyReport);
					quaterlyReportsNORTH.put(restID, monthlyReportsList);
				} else {
					quaterlyReportsNORTH.get(restID).add(monthlyReport);
				}
				break;
			case E_CENTER:
				if (!quaterlyReportsCENTER.containsKey(restID)) {
					ArrayList<MonthlyReport> monthlyReportsList = new ArrayList<>();
					monthlyReportsList.add(monthlyReport);
					quaterlyReportsCENTER.put(restID, monthlyReportsList);
				} else {
					quaterlyReportsCENTER.get(restID).add(monthlyReport);
				}
				break;

			case E_SOUTH:
				if (!quaterlyReportsSOUTH.containsKey(restID)) {
					ArrayList<MonthlyReport> monthlyReportsList = new ArrayList<>();
					monthlyReportsList.add(monthlyReport);
					quaterlyReportsSOUTH.put(restID, monthlyReportsList);
				} else {
					quaterlyReportsSOUTH.get(restID).add(monthlyReport);
				}
				break;
			}

		}
	}

	/**
	 * Builds the quaterly reports reports for each branch.
	 */
	public void buildQuaterlyReports() {

		ArrayList<HashMap<Integer, ArrayList<MonthlyReport>>> allQuaterlyReports = new ArrayList<>();
		allQuaterlyReports.add(quaterlyReportsNORTH);
		allQuaterlyReports.add(quaterlyReportsCENTER);
		allQuaterlyReports.add(quaterlyReportsSOUTH);

		for (HashMap<Integer, ArrayList<MonthlyReport>> quaterReportsByBranch : allQuaterlyReports) {
			if (quaterReportsByBranch.size() == 0) {
				continue;
			}
			Set<Integer> allRestID = quaterReportsByBranch.keySet();
			String currentBranch = quaterReportsByBranch.get(allRestID.iterator().next()).get(0).getBranch().toString();
			final DefaultCategoryDataset datasetOrders = new DefaultCategoryDataset();
			final DefaultCategoryDataset datasetRevenue = new DefaultCategoryDataset();
			JFreeChart orderBarChart = null;
			JFreeChart revenueBarChart = null;
			for (Integer restID : allRestID) {
				// String restNameForFile =
				// quaterReportsByBranch.get(restID).get(0).getRestName();
				for (MonthlyReport monthlyReport : quaterReportsByBranch.get(restID)) {

					final String reportDate = "Month:" + monthlyReport.getReportDate().getMonthValue();
					final String restName = monthlyReport.getRestName();
					final double amntOrders = monthlyReport.getTotalOrders();

					datasetOrders.addValue(amntOrders, reportDate, restName);
				}
				double sum = 0;
				for (MonthlyReport monthlyReport : quaterReportsByBranch.get(restID)) {
					final String reportDate = "Month:" + monthlyReport.getReportDate().getMonthValue();
					final String restName = monthlyReport.getRestName();
					final double revenue = monthlyReport.getTotalSum() + sum;
					sum += monthlyReport.getTotalSum();

					datasetRevenue.addValue(revenue, reportDate, restName);
				}
			}
			orderBarChart = ChartFactory.createBarChart("RESTAURANT QUATERLY ORDERS", "Restaurant", "Orders",
					datasetOrders, PlotOrientation.VERTICAL, true, true, false);

			revenueBarChart = ChartFactory.createBarChart("RESTAURANT QUATERLY REVENUE", "Restaurant", "Revenue",
					datasetRevenue, PlotOrientation.VERTICAL, true, true, false);

			try {
				Document quaterlyReport = new Document();
				String path = "../BiteMe_Server/Reports/QuaterlyReports/quaterlyReport_From_"
						+ LocalDate.now().minusMonths(3).getMonthValue() + "_To_"
						+ LocalDate.now().minusMonths(1).getMonthValue() + ".pdf";
				PdfWriter writer = PdfWriter.getInstance(quaterlyReport, new FileOutputStream(path));
				System.out.println("PDF created.");
				// opens the PDF
				quaterlyReport.open();
				// adds paragraph to the PDF file
				quaterlyReport.add(new Paragraph("Quaterly Reports"));
				quaterlyReport.add(new Paragraph(" "));

				// generate a PDF at the specified location
				PdfTemplate orderBarChartTemp = null;
				if (orderBarChart != null) {
					PdfContentByte cb = writer.getDirectContent();
					orderBarChartTemp = cb.createTemplate(500, 300);
					Graphics2D g2d2 = new PdfGraphics2D(orderBarChartTemp, 500, 300);
					Rectangle2D r2d2 = new Rectangle2D.Double(0, 0, 500, 300);
					orderBarChart.draw(g2d2, r2d2);
					g2d2.dispose();
					// cb.addTemplate(bar, 0, 0);
				}
				PdfTemplate revenueBarChartTemp = null;
				if (revenueBarChart != null) {
					PdfContentByte cb = writer.getDirectContent();
					revenueBarChartTemp = cb.createTemplate(500, 300);
					Graphics2D g2d2 = new PdfGraphics2D(revenueBarChartTemp, 500, 300);
					Rectangle2D r2d2 = new Rectangle2D.Double(0, 0, 500, 300);
					revenueBarChart.draw(g2d2, r2d2);
					g2d2.dispose();
					// cb.addTemplate(bar, 0, 0);
				}
				Image img;
				if (orderBarChartTemp != null) {
					img = Image.getInstance(orderBarChartTemp);
					quaterlyReport.add(img);
				}
				quaterlyReport.add(new Paragraph(" "));
				quaterlyReport.add(new Paragraph(" "));
				if (revenueBarChartTemp != null) {
					img = Image.getInstance(revenueBarChartTemp);
					quaterlyReport.add(img);
				}
				quaterlyReport.newPage();

				quaterlyReport.add(new Paragraph("Restaurant Income Reports"));
				for (Integer restID : allRestID) {
					for (MonthlyReport monthlyReport : quaterReportsByBranch.get(restID)) {
						quaterlyReport.add(new Paragraph("Month: " + monthlyReport.getReportDate().getMonthValue() + "-"
								+ monthlyReport.getRestName() + " " + monthlyReport.getTotalSum()));
					}
				}
				// close the PDF file
				quaterlyReport.close();
				// closes the writer
				writer.close();

				byte[] pdfToByteArray = calculateByteArray(path);
				if (pdfToByteArray.length > 0) {
					LocalDate myLocal = LocalDate.now();
					int year = myLocal.getYear();
					int quarter = myLocal.get(IsoFields.QUARTER_OF_YEAR);
					serverController.savePDFByteArray(pdfToByteArray, currentBranch, quarter, year);
				}

			} catch (

			DocumentException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Calculate byte array for the pdf in order to save it in the db.
	 *
	 * @param pathFile the path file of the pdf report
	 * @return the byte[] array of the pdf report
	 */
	private byte[] calculateByteArray(String pathFile) {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			File getFile = new File(pathFile);
			final byte[] buffer = new byte[500];
			FileInputStream fis = new FileInputStream(pathFile);
			BufferedInputStream inputStream = new BufferedInputStream(fis);

			int readAmount = -1;
			while ((readAmount = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, readAmount);
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

	/**
	 * Gets the month of last reports.
	 *
	 * @return the month of last reports
	 */
	public int getMonthOfLastReports() {
		return monthOfLastReports;
	}

	/**
	 * Sets the month of last reports.
	 *
	 * @param monthOfLastReports the new month of last reports
	 */
	public void setMonthOfLastReports(int monthOfLastReports) {
		this.monthOfLastReports = monthOfLastReports;
	}

	/**
	 * Gets the year of last reports.
	 *
	 * @return the year of last reports
	 */
	public int getYearOfLastReports() {
		return yearOfLastReports;
	}

	/**
	 * Sets the year of last reports.
	 *
	 * @param yearOfLastReports the new year of last reports
	 */
	public void setYearOfLastReports(int yearOfLastReports) {
		this.yearOfLastReports = yearOfLastReports;
	}

	/**
	 * Builds the restaurant id name map for the names of the restaurants.
	 *
	 * @param restIdNames the rest id names
	 */
	public void buildRestIdNameMap(ArrayList<String> restIdNames) {
		for (int i = 0; i < restIdNames.size(); i++) {
			String[] splitter = restIdNames.get(i).split(";");
			restIdNameMap.put(Integer.parseInt(splitter[0]), splitter[1]);
		}

	}

	/**
	 * Gets the quarter of last quarter reports.
	 *
	 * @return the quarter of last quarter reports
	 */
	public int getQuarterOfLastQuarterReports() {
		return quarterOfLastQuarterReports;
	}

	/**
	 * Sets the quarter of last quarter reports.
	 *
	 * @param quarterOfLastQuarterReports the new quarter of last quarter reports
	 */
	public void setQuarterOfLastQuarterReports(int quarterOfLastQuarterReports) {
		this.quarterOfLastQuarterReports = quarterOfLastQuarterReports;
	}

	/**
	 * Gets the year of last quarter reports.
	 *
	 * @return the year of last quarter reports
	 */
	public int getYearOfLastQuarterReports() {
		return yearOfLastQuarterReports;
	}

	/**
	 * Sets the year of last quarter reports.
	 *
	 * @param yearOfLastQuarterReports the new year of last quarter reports
	 */
	public void setYearOfLastQuarterReports(int yearOfLastQuarterReports) {
		this.yearOfLastQuarterReports = yearOfLastQuarterReports;
	}

}
