package reports;

import utils.E_Branches;

/**
 * The Class BranchmanToCeoReport is the reports that the branch manager sends to the CEO.
 */
public class BranchmanToCeoReport {

	/** The branch. */
	E_Branches branch;
	
	/** The quarter. */
	int quarter;
	
	/** The year. */
	int year;
	
	/** The pdf byte array. */
	byte[] pdfByteArray;

	/**
	 * Gets the branch.
	 *
	 * @return E_Branches represents the branch
	 */
	public E_Branches getBranch() {
		return branch;
	}

	/**
	 * Sets the branch.
	 *
	 * @param branch - E_Branches represents the new branch
	 */
	public void setBranch(E_Branches branch) {
		this.branch = branch;
	}

	/**
	 * Gets the quarter.
	 *
	 * @return int represents the quarter
	 */
	public int getQuarter() {
		return quarter;
	}

	/**
	 * Sets the quarter.
	 *
	 * @param quarter - int represents the new quarter
	 */
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	/**
	 * Gets the year.
	 *
	 * @return int represents the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year - int represents the new year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Gets the pdf byte array.
	 *
	 * @return the pdf byte array
	 */
	public byte[] getPdfByteArray() {
		return pdfByteArray;
	}

	/**
	 * Sets the pdf byte array.
	 *
	 * @param pdfByteArray the new pdf byte array
	 */
	public void setPdfByteArray(byte[] pdfByteArray) {
		this.pdfByteArray = pdfByteArray;
	}
}
