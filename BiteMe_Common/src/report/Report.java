package report;

import utils.E_Branches;
import utils.E_DishCategory;

/**
 * The Class Report.
 */
public class Report {

	/** The rest ID. */
	int restID;

	/** The month. */
	int month;

	/** The year. */
	int year;

	/** The branch. */
	E_Branches branch;

	/** The category. */
	E_DishCategory category;

	/** The amount. */
	int amount;

	/** The total. */
	double total;

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
	 * Gets the month.
	 *
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Sets the month.
	 *
	 * @param month the new month
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(int year) {
		this.year = year;
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
	 * Gets the category.
	 *
	 * @return the category
	 */
	public E_DishCategory getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(E_DishCategory category) {
		this.category = category;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(double total) {
		this.total = total;
	}
}
