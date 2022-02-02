package utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Class Credit.
 */
public class Credit implements Serializable {

	/** The credit amount. */
	double creditAmount = 0;

	/** The credit for restaurant ID. */
	int creditForRestID = 0;

	/**
	 * Instantiates a new credit.
	 *
	 * @param restID the rest ID
	 * @param amnt   the amnt
	 */
	public Credit(int restID, double amnt) {
		creditAmount = amnt;
		creditForRestID = restID;
	}

	/**
	 * Adds the credit to list of credits.
	 *
	 * @param creditList   the credit list
	 * @param creditAmount the credit amount
	 * @param restID       the rest ID
	 * @return the array list
	 */
	public static ArrayList<Credit> addCreditToList(ArrayList<Credit> creditList, double creditAmount, int restID) {

		for (Credit credit : creditList) {
			if (credit.getCreditForRestID() == restID) {
				credit.addCreditAmount(creditAmount);
				return creditList;
			}
		}
		creditList.add(new Credit(restID, creditAmount));
		return creditList;
	}

	/**
	 * Gets the credit amount.
	 *
	 * @return the credit amount
	 */
	public double getCreditAmount() {
		return creditAmount;
	}

	/**
	 * Adds the credit amount.
	 *
	 * @param creditAmount the credit amount
	 */
	public void addCreditAmount(double creditAmount) {
		this.creditAmount += creditAmount;
	}

	/**
	 * Sets the credit amount.
	 *
	 * @param creditAmount the new credit amount
	 */
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * Removes the credit amount.
	 *
	 * @param creditAmount the credit amount
	 * @return true, if successful
	 */
	public boolean removeCreditAmount(double creditAmount) {
		if (this.creditAmount >= creditAmount) {
			this.creditAmount -= creditAmount;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the credit for rest ID.
	 *
	 * @return the credit for rest ID
	 */
	public int getCreditForRestID() {
		return creditForRestID;
	}

	/**
	 * Sets the credit for rest ID.
	 *
	 * @param creditForRestID the new credit for rest ID
	 */
	public void setCreditForRestID(int creditForRestID) {
		this.creditForRestID = creditForRestID;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return creditForRestID + "-" + creditAmount;
	}

}
