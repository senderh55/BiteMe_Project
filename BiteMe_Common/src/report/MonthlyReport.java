package report;

import java.time.LocalDate;

import utils.E_Branches;

/**
 * The Class MonthlyReport.
 */
public class MonthlyReport {
	
	/** The rest ID. */
	int restID;
	
	/** The report date. */
	LocalDate reportDate;
	
	/** The branch. */
	E_Branches branch;
	
	/** The appetizer amnt. */
	int appetizerAmnt;
	
	/** The salad amnt. */
	int saladAmnt;
	
	/** The soup amnt. */
	int soupAmnt;
	
	/** The main amnt. */
	int mainAmnt;
	
	/** The dessert amnt. */
	int dessertAmnt;
	
	/** The drink amnt. */
	int drinkAmnt;
	
	/** The total orders. */
	int totalOrders;
	
	/** The total sum. */
	double totalSum;
	
	/** The orders on time amnt. */
	int ordersOnTimeAmnt;
	
	/** The orders late amnt. */
	int ordersLateAmnt;
	
	/** The performance. */
	float performance;
	
	/** The fee sum. */
	double feeSum;
	
	/** The fee. */
	float fee;
	
	/** The rest name. */
	String restName;


	/**
	 * Gets the rest ID.
	 *
	 * @return the rest ID
	 */
	public int getRestID() {
		return restID;
	}

	/**
	 * Sets the rest ID.
	 *
	 * @param restID the new rest ID
	 */
	public void setRestID(int restID) {
		this.restID = restID;
	}

	/**
	 * Gets the report date.
	 *
	 * @return the report date
	 */
	public LocalDate getReportDate() {
		return reportDate;
	}

	/**
	 * Sets the report date.
	 *
	 * @param reportDate the new report date
	 */
	public void setReportDate(LocalDate reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * Gets the branch.
	 *
	 * @return the branch
	 */
	public E_Branches getBranch() {
		return branch;
	}

	/**
	 * Sets the branch.
	 *
	 * @param branch the new branch
	 */
	public void setBranch(E_Branches branch) {
		this.branch = branch;
	}

	/**
	 * Gets the appetizer amnt.
	 *
	 * @return the appetizer amnt
	 */
	public int getAppetizerAmnt() {
		return appetizerAmnt;
	}

	/**
	 * Sets the appetizer amnt.
	 *
	 * @param appetizerAmnt the new appetizer amnt
	 */
	public void setAppetizerAmnt(int appetizerAmnt) {
		this.appetizerAmnt = appetizerAmnt;
	}

	/**
	 * Gets the salad amnt.
	 *
	 * @return the salad amnt
	 */
	public int getSaladAmnt() {
		return saladAmnt;
	}

	/**
	 * Sets the salad amnt.
	 *
	 * @param saladAmnt the new salad amnt
	 */
	public void setSaladAmnt(int saladAmnt) {
		this.saladAmnt = saladAmnt;
	}

	/**
	 * Gets the soup amnt.
	 *
	 * @return the soup amnt
	 */
	public int getSoupAmnt() {
		return soupAmnt;
	}

	/**
	 * Sets the soup amnt.
	 *
	 * @param soupAmnt the new soup amnt
	 */
	public void setSoupAmnt(int soupAmnt) {
		this.soupAmnt = soupAmnt;
	}

	/**
	 * Gets the main amnt.
	 *
	 * @return the main amnt
	 */
	public int getMainAmnt() {
		return mainAmnt;
	}

	/**
	 * Sets the main amnt.
	 *
	 * @param mainAmnt the new main amnt
	 */
	public void setMainAmnt(int mainAmnt) {
		this.mainAmnt = mainAmnt;
	}

	/**
	 * Gets the dessert amnt.
	 *
	 * @return the dessert amnt
	 */
	public int getDessertAmnt() {
		return dessertAmnt;
	}

	/**
	 * Sets the dessert amnt.
	 *
	 * @param dessertAmnt the new dessert amnt
	 */
	public void setDessertAmnt(int dessertAmnt) {
		this.dessertAmnt = dessertAmnt;
	}

	/**
	 * Gets the drink amnt.
	 *
	 * @return the drink amnt
	 */
	public int getDrinkAmnt() {
		return drinkAmnt;
	}

	/**
	 * Sets the drink amnt.
	 *
	 * @param drinkAmnt the new drink amnt
	 */
	public void setDrinkAmnt(int drinkAmnt) {
		this.drinkAmnt = drinkAmnt;
	}

	/**
	 * Gets the total orders.
	 *
	 * @return the total orders
	 */
	public int getTotalOrders() {
		return totalOrders;
	}

	/**
	 * Sets the total orders.
	 *
	 * @param totalOrders the new total orders
	 */
	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}

	/**
	 * Gets the total sum.
	 *
	 * @return the total sum
	 */
	public double getTotalSum() {
		return totalSum;
	}

	/**
	 * Sets the total sum.
	 *
	 * @param totalSum the new total sum
	 */
	public void setTotalSum(double totalSum) {
		this.totalSum = totalSum;
	}

	/**
	 * Gets the orders on time amnt.
	 *
	 * @return the orders on time amnt
	 */
	public int getOrdersOnTimeAmnt() {
		return ordersOnTimeAmnt;
	}

	/**
	 * Sets the orders on time amnt.
	 *
	 * @param ordersOnTimeAmnt the new orders on time amnt
	 */
	public void setOrdersOnTimeAmnt(int ordersOnTimeAmnt) {
		this.ordersOnTimeAmnt = ordersOnTimeAmnt;
	}

	/**
	 * Gets the orders late amnt.
	 *
	 * @return the orders late amnt
	 */
	public int getOrdersLateAmnt() {
		return ordersLateAmnt;
	}

	/**
	 * Sets the orders late amnt.
	 *
	 * @param ordersLateAmnt the new orders late amnt
	 */
	public void setOrdersLateAmnt(int ordersLateAmnt) {
		this.ordersLateAmnt = ordersLateAmnt;
	}

	/**
	 * Gets the performance.
	 *
	 * @return the performance
	 */
	public float getPerformance() {
		return performance;
	}

	/**
	 * Sets the performance.
	 *
	 * @param performance the new performance
	 */
	public void setPerformance(float performance) {
		this.performance = performance;
	}

	/**
	 * Gets the fee sum.
	 *
	 * @return the fee sum
	 */
	public double getFeeSum() {
		return feeSum;
	}

	/**
	 * Sets the fee sum.
	 *
	 * @param feeSum the new fee sum
	 */
	public void setFeeSum(double feeSum) {
		this.feeSum = feeSum;
	}

	/**
	 * Gets the fee.
	 *
	 * @return the fee
	 */
	public float getFee() {
		return fee;
	}

	/**
	 * Sets the fee.
	 *
	 * @param fee the new fee
	 */
	public void setFee(float fee) {
		this.fee = fee;
	}

	/**
	 * Gets the rest name.
	 *
	 * @return the rest name
	 */
	public String getRestName() {
		return restName;
	}

	/**
	 * Sets the rest name.
	 *
	 * @param restName the new rest name
	 */
	public void setRestName(String restName) {
		this.restName = restName;
	}
}
