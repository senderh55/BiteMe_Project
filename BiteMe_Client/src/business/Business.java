package business;

import utils.E_Branches;

/**
 * The Class Business represents a business that has an agreement with BiteMe that it's employees can have business user.
 */
public class Business {
	
	/** The monthly limit. */
	private String companyName;
	
	/** The employer name. */
	String employerName;
	
	/** The monthly limit. */
	String mounthlyLimit;
	
	/** The Business W4C number. */
	private int BusinessW4CNumber;
	
	/** The default branch - Enum of branches. */
	private E_Branches defaultBranch;
	
	/** The is approved. */
	private boolean isApproved;
	
	/**
	 * Gets the company name.
	 *
	 * @return String represents the company name
	 */
	public String getCompanyName() {
		return companyName;
	}
	
	/**
	 * Sets the company name.
	 *
	 * @param companyName - String represents the new company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	/**
	 * Gets the employer name.
	 *
	 * @return String represents the employer name
	 */
	public String getEmployerName() {
		return employerName;
	}
	
	/**
	 * Sets the employer name.
	 *
	 * @param employerName - String represents the new employer name
	 */
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	
	/**
	 * Gets the default branch.
	 *
	 * @return E_Branches represents the default branch
	 */
	public E_Branches getDefaultBranch() {
		return defaultBranch;
	}
	
	/**
	 * Sets the default branch.
	 *
	 * @param defaultBranch - E_Branches represents the new default branch
	 */
	public void setDefaultBranch(E_Branches defaultBranch) {
		this.defaultBranch = defaultBranch;
	}
	
	/**
	 * Gets the business W4C number.
	 *
	 * @return int represents the business W4C number
	 */
	public int getBusinessW4CNumber() {
		return BusinessW4CNumber;
	}
	
	/**
	 * Sets the business W4C number.
	 *
	 * @param businessW4CNumber - int represents the new business W 4 C number
	 */
	public void setBusinessW4CNumber(int businessW4CNumber) {
		this.BusinessW4CNumber = businessW4CNumber;
	}
	
	/**
	 * Checks if the user is approved.
	 *
	 * @return true, if the user is approved, else false
	 */
	public boolean isApproved() {
		return isApproved;
	}
	
	/**
	 * Sets the approved field.
	 *
	 * @param isApproved - boolean represents the new approved
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	/**
	 * Gets the monthly limit.
	 *
	 * @return String represents the monthly limit
	 */
	public String getMounthlyLimit() {
		return mounthlyLimit;
	}
	
	/**
	 * Sets the monthly limit.
	 *
	 * @param mounthlyLimit - String represents the new monthly limit
	 */
	public void setMounthlyLimit(String mounthlyLimit) {
		this.mounthlyLimit = mounthlyLimit;
	}	
}
