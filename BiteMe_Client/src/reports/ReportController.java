package reports;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import dish.DishInOrder;
import report.MonthlyReport;
import report.Report;
import utils.E_Branches;
import utils.E_DishCategory;
import utils.Message;
import utils.OpEnum;
import utils.OwnerEnum;

/**
 * This is the controller for the Report entity.
 */
public class ReportController {
	
	/** The reports map (dish amount - total sum). */
	Map<E_DishCategory, double[]> reportsMap = new HashMap<>();
	
	/** The report. */
	Report report = new Report();

	/** The reports for restaurants (restaurant ID - list of the restaurant reports). */
	TreeMap<Integer, ArrayList<MonthlyReport>> reportsForRestaurants = new TreeMap<>();

	/** The branch man to CEO reports - list of reports that the branch managers sends to the CEO. */
	ArrayList<BranchmanToCeoReport> branchManToCeoReports = new ArrayList<>();

	/**
	 * Builds the reports list.
	 *
	 * @param messageFromServer - Message from server
	 */
	public void buildReportsList(Message messageFromServer) {
		reportsForRestaurants.clear();
		for (int i = 0; i < messageFromServer.getMsgArrayL().size(); i++) {
			String[] splitter = ((String) messageFromServer.getMsgArrayL().get(i)).split(";");
			MonthlyReport report = new MonthlyReport();

			report.setRestID(Integer.parseInt(splitter[0]));
			report.setReportDate(LocalDate.parse(splitter[1]));
			report.setBranch(E_Branches.valueOf(splitter[2]));
			report.setAppetizerAmnt(Integer.parseInt(splitter[3]));
			report.setSaladAmnt(Integer.parseInt(splitter[4]));
			report.setSoupAmnt(Integer.parseInt(splitter[5]));
			report.setMainAmnt(Integer.parseInt(splitter[6]));
			report.setDessertAmnt(Integer.parseInt(splitter[7]));
			report.setDrinkAmnt(Integer.parseInt(splitter[8]));
			report.setTotalOrders(Integer.parseInt(splitter[9]));
			report.setTotalSum(Double.parseDouble(splitter[10]));
			report.setOrdersOnTimeAmnt(Integer.parseInt(splitter[11]));
			report.setOrdersLateAmnt(Integer.parseInt(splitter[12]));
			report.setPerformance(Float.parseFloat(splitter[13]));
			report.setFeeSum(Double.parseDouble(splitter[14]));
			report.setFee(Float.parseFloat(splitter[15]));
			report.setRestName(splitter[16]);

			if (!reportsForRestaurants.containsKey(report.getRestID())) {
				ArrayList<MonthlyReport> reportSpecificRestaurant = new ArrayList<>();
				reportSpecificRestaurant.add(report);
				reportsForRestaurants.put(report.getRestID(), reportSpecificRestaurant);
			} else {
				reportsForRestaurants.get(report.getRestID()).add(report);
			}

		}

	}

	/**
	 * Process orders list for the report.
	 *
	 * @param orderList the order list
	 */
	public void proccessOrder(ArrayList<DishInOrder> orderList) {
		for (DishInOrder dish : orderList) {

			switch (dish.getCategory()) {

			case E_APPETIZER:
				if (reportsMap.get(E_DishCategory.E_APPETIZER) == null) {
					reportsMap.put(E_DishCategory.E_APPETIZER, new double[2]);
					reportsMap.get(E_DishCategory.E_APPETIZER)[0] = 0;
					reportsMap.get(E_DishCategory.E_APPETIZER)[1] = 0;
				}
				reportsMap.get(E_DishCategory.E_APPETIZER)[0] += dish.getPriceOfDishBySize() * dish.getAmountOfDish();
				reportsMap.get(E_DishCategory.E_APPETIZER)[1] += dish.getAmountOfDish();
				break;
			case E_SALAD:
				if (reportsMap.get(E_DishCategory.E_SALAD) == null) {
					reportsMap.put(E_DishCategory.E_SALAD, new double[2]);
					reportsMap.get(E_DishCategory.E_SALAD)[0] = 0;
					reportsMap.get(E_DishCategory.E_SALAD)[1] = 0;
				}
				reportsMap.get(E_DishCategory.E_SALAD)[0] += dish.getPriceOfDishBySize() * dish.getAmountOfDish();
				reportsMap.get(E_DishCategory.E_SALAD)[1] += dish.getAmountOfDish();
				break;
			case E_SOUP:
				if (reportsMap.get(E_DishCategory.E_SOUP) == null) {
					reportsMap.put(E_DishCategory.E_SOUP, new double[2]);
					reportsMap.get(E_DishCategory.E_SOUP)[0] = 0;
					reportsMap.get(E_DishCategory.E_SOUP)[1] = 0;
				}
				reportsMap.get(E_DishCategory.E_SOUP)[0] += dish.getPriceOfDishBySize() * dish.getAmountOfDish();
				reportsMap.get(E_DishCategory.E_SOUP)[1] += dish.getAmountOfDish();

				break;
			case E_MAIN:
				if (reportsMap.get(E_DishCategory.E_MAIN) == null) {
					reportsMap.put(E_DishCategory.E_MAIN, new double[2]);
					reportsMap.get(E_DishCategory.E_MAIN)[0] = 0;
					reportsMap.get(E_DishCategory.E_MAIN)[1] = 0;
				}
				reportsMap.get(E_DishCategory.E_MAIN)[0] += dish.getPriceOfDishBySize() * dish.getAmountOfDish();
				reportsMap.get(E_DishCategory.E_MAIN)[1] += dish.getAmountOfDish();

				break;
			case E_DESSERT:
				if (reportsMap.get(E_DishCategory.E_DESSERT) == null) {
					reportsMap.put(E_DishCategory.E_DESSERT, new double[2]);
					reportsMap.get(E_DishCategory.E_DESSERT)[0] = 0;
					reportsMap.get(E_DishCategory.E_DESSERT)[1] = 0;
				}
				reportsMap.get(E_DishCategory.E_DESSERT)[0] += dish.getPriceOfDishBySize() * dish.getAmountOfDish();
				reportsMap.get(E_DishCategory.E_DESSERT)[1] += dish.getAmountOfDish();

				break;
			case E_DRINKS:
				if (reportsMap.get(E_DishCategory.E_DRINKS) == null) {
					reportsMap.put(E_DishCategory.E_DRINKS, new double[2]);
					reportsMap.get(E_DishCategory.E_DRINKS)[0] = 0;
					reportsMap.get(E_DishCategory.E_DRINKS)[1] = 0;
				}
				reportsMap.get(E_DishCategory.E_DRINKS)[0] += dish.getPriceOfDishBySize() * dish.getAmountOfDish();
				reportsMap.get(E_DishCategory.E_DRINKS)[1] += dish.getAmountOfDish();

				break;
			}

		}

	}

	/**
	 * Gets the reports map.
	 *
	 * @return the reports map
	 */
	public Map<E_DishCategory, double[]> getReportsMap() {
		return reportsMap;
	}

	/**
	 * Sets the reports map.
	 *
	 * @param reportsMap the reports map
	 */
	public void setReportsMap(Map<E_DishCategory, double[]> reportsMap) {
		this.reportsMap = reportsMap;
	}

	/**
	 * Gets the reports for restaurants.
	 *
	 * @return the reports for restaurants
	 */
	public TreeMap<Integer, ArrayList<MonthlyReport>> getReportsForRestaurants() {
		return reportsForRestaurants;
	}

	/**
	 * Builds the report PDF.
	 *
	 * @param messageFromServer the message from the server
	 */
	public void buildReportPDF(Message messageFromServer) {

		try {
			int quarter = Integer.parseInt((String) messageFromServer.getMsgArrayL().get(0));
			int year = Integer.parseInt((String) messageFromServer.getMsgArrayL().get(1));
			String branch = (String) messageFromServer.getMsgArrayL().get(2);
			String path = "../BiteMe_Client/downloadedFiles/" + quarter + "_" + year + "_" + branch;
			File newFile = new File(path + ".pdf"); // create a file with the same name as new_file.getFileName()
			FileOutputStream FOS = new FileOutputStream(newFile);
			BufferedOutputStream BOS = new BufferedOutputStream(FOS);
//			Blob blob = (Blob) messageFromServer.getMsgArrayL().get(3);
//			int blobLength = (int) blob.length();
			int blobLen = ((byte[]) messageFromServer.getMsgArrayL().get(3)).length;
			byte[] blobAsBytes = new byte[blobLen];
			for (int i = 0; i < blobLen; i++) {
				blobAsBytes[i] = ((byte[]) messageFromServer.getMsgArrayL().get(3))[i];
			}
			BOS.write(blobAsBytes, 0, blobAsBytes.length);
			BOS.close();
			FOS.close();
		} catch (Exception e) {
			System.out.println("Error send (Files)msg) to Server");
		}

	}

	/**
	 * Builds the branch manager report to the CEO.
	 *
	 * @param branch - String represents the branch
	 * @return Message represents the message
	 */
	public Message buildBranchmanToCEOReport(String branch) {

		LocalDate now = LocalDate.now();
		String path = "../BiteMe_Client/branchManTempFiles/" + branch + "_" + now.getMonthValue() + "_" + now.getYear()
				+ ".pdf";
		try {
			Document quaterlyReport = new Document();

			PdfWriter writer = null;
			try {
				writer = PdfWriter.getInstance(quaterlyReport, new FileOutputStream(path));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("PDF created.");
			// opens the PDF
			quaterlyReport.open();
			// adds paragraph to the PDF file
			quaterlyReport.add(new Paragraph(" "));
			quaterlyReport.add(new Paragraph("Quaterly Reports"));
			// close the PDF file
			quaterlyReport.close();
			// closes the writer
			writer.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] pdfToByteArray = calculateByteArray(path);
		Message sendTOCEO = new Message(OwnerEnum.E_CLIENT, OpEnum.E_BRANCHMAN_TO_CEO_REPORT);
		if (pdfToByteArray.length > 0) {
			LocalDate myLocal = LocalDate.now();
			int year = myLocal.getYear();
			int quarter = myLocal.get(IsoFields.QUARTER_OF_YEAR);

			sendTOCEO.getMsgArrayL().add(branch);
			sendTOCEO.getMsgArrayL().add(quarter);
			sendTOCEO.getMsgArrayL().add(year);
			sendTOCEO.getMsgArrayL().add(pdfToByteArray);

		}
		return sendTOCEO;
	}

	/**
	 * Calculate byte array.
	 *
	 * @param pathFile - String represents the path file
	 * @return the byte[] array
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
	 * Builds the branch manager reports list to the CEO.
	 *
	 * @param messageFromServer  - the message from the server
	 */
	public void buildBranchmanToCeoReportList(Message messageFromServer) {
		branchManToCeoReports.clear();
		for (int i = 0; i < messageFromServer.getMsgArrayL().size();) {
			String reportData = (String) messageFromServer.getMsgArrayL().get(i);
			String[] splitter = reportData.split(";");

			BranchmanToCeoReport branchManToCeoReport = new BranchmanToCeoReport();
			branchManToCeoReport.setBranch(E_Branches.valueOf(splitter[0]));
			branchManToCeoReport.setQuarter(Integer.parseInt(splitter[1]));
			branchManToCeoReport.setYear(Integer.parseInt(splitter[2]));
			int blobLen = ((byte[]) messageFromServer.getMsgArrayL().get(i+1)).length;
			byte[] blobAsBytes = new byte[blobLen];
			for (int j = 0; j < blobLen; j++) {
				blobAsBytes[j] = ((byte[]) messageFromServer.getMsgArrayL().get(i+1))[j];
			}
			branchManToCeoReport.setPdfByteArray(blobAsBytes);

			branchManToCeoReports.add(branchManToCeoReport);
			i += 2;
		}

	}

	/**
	 * Gets the branch man to CEO reports.
	 *
	 * @return list of reports from the branch manager to the CEO
	 */
	public ArrayList<BranchmanToCeoReport> getBranchManToCeoReports() {
		return branchManToCeoReports;
	}

	/**
	 * Download branch to CEO report.
	 *
	 * @param report to the CEO
	 */
	public void downloadBranchToCeoReport(BranchmanToCeoReport report) {
		try {

			String path = "../BiteMe_Client/ceoBranchReports/" + report.getBranch() + "_" + report.getQuarter() + "_"
					+ report.getYear() + ".pdf";
			File newFile = new File(path);
			FileOutputStream FOS = new FileOutputStream(newFile);
			BufferedOutputStream BOS = new BufferedOutputStream(FOS);

			int blobLen = report.getPdfByteArray().length;

			BOS.write(report.getPdfByteArray(), 0, blobLen);
			BOS.close();
			FOS.close();
		} catch (Exception e) {
			System.out.println("Error send (Files)msg) to Server");
		}

	}

}
