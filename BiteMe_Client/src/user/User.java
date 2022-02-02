package user;

import java.util.ArrayList;

import utils.Credit;
import utils.E_AccountStatus;
import utils.E_Branches;
import utils.E_UserTypeEnum;

/**
 * This class represents the regular end user in BiteMe system
 */
public class User {
	/**
	 * Represents the user private name
	 */
	String privateName;
	/**
	 * Represents the user last name
	 */
	String lastName;
	/**
	 * Represents the user's system user name
	 */
	String username;
	/**
	 * Represents the user's system password
	 */
	String password;
	/**
	 * Represents the user ID
	 */
	int userID;
	/**
	 * Represents the user Email
	 */
	String email;
	/**
	 * Represents the user phone number
	 */
	String phoneNumber;
	/**
	 * Represents the user default BiteMe branch
	 */
	E_Branches defaultBranch;
	/**
	 * Represents the user role in his\her company
	 */
	String role;
	/**
	 * Represents the user type in BiteMe system (regular, business, restaurant, HR,
	 * CEO, branch manager)
	 */
	E_UserTypeEnum userType;
	/**
	 * Represents the restaurant ID - only if the user type is restaurant! else it
	 * will be 0
	 */
	int manageRestID;
	/**
	 * Represent if the user is logged in or not
	 */
	int isLoggedIn;
	/**
	 * Represent the user account status - active, freeze or waiting for approval
	 */
	E_AccountStatus accountStatus;
	/**
	 * Represents the user W4C number
	 */
	int W4CNumber;
	/**
	 * Represents the user business name - only if the user type is business! else
	 * NA
	 */
	String businessName;
	/**
	 * Represents the user business W4C number- only if the user type is business!
	 * else 0
	 */
	int businessW4CNumber;
	/**
	 * Represents the amount of business money that the user used - only if the user
	 * type is business! else 0
	 */
	double businessAmountUsed;
	/**
	 * Represents the monthly limit for user business money - only if the user type
	 * is business! else 0
	 */
	double businessMonthlyLimit;

	/** The restaurant name. */
	String restName;

	/** The description. */
	String description;

	/** The address. */
	String address;

	/**
	 * Represents the user credits list from all the restaurants (if such credit
	 * exists)
	 */
	ArrayList<Credit> userCreditList = new ArrayList<>();

	/**
	 * Represents the user credit card number
	 */
	String cardNumber;

	/**
	 * Represents the user temporary branch
	 */
	E_Branches tempBranch;

	/**
	 * Returns the user W4C number
	 * 
	 * @return int representing the user W4C number
	 */

	public int getW4CNumber() {
		return W4CNumber;
	}

	/**
	 * Returns the user credit list
	 * 
	 * @return an ArrayList of credits that represents the user credit list
	 */
	public ArrayList<Credit> getUserCreditList() {
		return userCreditList;
	}

	/**
	 * Adds a new credit to the user credit list
	 * 
	 * @param creditToAdd - the credit that we want to add to the list
	 * @return a boolean - true if added, else false
	 */
	public boolean addUserCreditList(Credit creditToAdd) {
		if (userCreditList.add(creditToAdd)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the user logged in status
	 * 
	 * @return an int representing the user logged in status
	 */
	public int getIsLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * sets the logged in status
	 * 
	 * @param isLoggedIn - an int representing the user logged in status
	 *
	 */
	public void setIsLoggedIn(int isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	/**
	 * Gets the user private name
	 *
	 * @return a string represents the user private name
	 * 
	 */
	public String getPrivateName() {
		return privateName;
	}

	/**
	 * Sets the user private name
	 * 
	 * @param privateName - a string represents the user private name
	 *
	 * 
	 */
	public void setPrivateName(String privateName) {
		this.privateName = privateName;
	}

	/**
	 * Gets the user last name
	 * 
	 * @return a string represents the user last name
	 * 
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the user last name
	 * 
	 * @param lastName - a string represents the user last name
	 *
	 * 
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the user system user name
	 * 
	 * @return a string represents the user system user name
	 * 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the user system user name
	 * 
	 * @param userName - a string represents te user system user name
	 *
	 * 
	 */
	public void setUsername(String userName) {
		this.username = userName;
	}

	/**
	 * Gets the user system password
	 *
	 * @return a string represents the user system password
	 * 
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user system password
	 * 
	 * @param password - a string represents the user system password
	 *
	 * 
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the user ID
	 * 
	 * @return an int represents the user ID
	 * 
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Sets the user ID
	 * 
	 * @param userID - an int represents the user ID
	 *
	 * 
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * Gets the user Email
	 *
	 * @return a string represents the user Email
	 * 
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user Email
	 * 
	 * @param email - a string represents the user email
	 *
	 * 
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the user phone number
	 * 
	 * @return a string represents the user phone number
	 * 
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the user phone number
	 * 
	 * @param phoneNumber - a string represents the user phone number
	 *
	 * 
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the user default branch
	 * 
	 * @return an ENUM represents the user default branch
	 * 
	 */
	public E_Branches getDefaultBranch() {
		return defaultBranch;
	}

	/**
	 * Sets the user default branch
	 * 
	 * @param defaultBranch - an ENUM represents the user default branch
	 *
	 * 
	 */
	public void setDefaultBranch(E_Branches defaultBranch) {
		this.defaultBranch = defaultBranch;
	}

	/**
	 * Gets the user type
	 * 
	 * @return an ENUM represents the user type
	 * 
	 */
	public E_UserTypeEnum getUserType() {
		return userType;
	}

	/**
	 * Sets the user type
	 * 
	 * @param userType - an ENUM represents the user type
	 *
	 * 
	 */
	public void setUserType(E_UserTypeEnum userType) {
		this.userType = userType;
	}

	/**
	 * Gets the user account status
	 * 
	 * @return an ENUM represents the user account status
	 * 
	 */
	public E_AccountStatus getAccountStatus() {
		return accountStatus;
	}

	/**
	 * Sets the user account status
	 * 
	 * @param accountStatus - an ENUM represents the user account status
	 *
	 * 
	 */
	public void setAccountStatus(E_AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * Sets user W4C number
	 * 
	 * @param W4CNumber - an int represents the user W4C number
	 *
	 * 
	 */
	public void setW4CNumber(int W4CNumber) {
		this.W4CNumber = W4CNumber;
	}

	/**
	 * Sets the user Business W4C number
	 * 
	 * @param businessW4CNumber - an int represents the user business W4C number
	 *
	 * 
	 */
	public void setBusinessW4CNumber(int businessW4CNumber) {
		this.businessW4CNumber = businessW4CNumber;
	}

	/**
	 * Gets the user Business W4C number
	 *
	 * @return an int represents the user business W4C number
	 * 
	 */
	public int getBusinessW4C() {
		return businessW4CNumber;
	}

	/**
	 * Gets the user temporary branch
	 * 
	 * @return an ENUM represents the user temporary branch
	 * 
	 */
	public E_Branches getTempBranch() {
		return tempBranch;
	}

	/**
	 * Gets the user temporary branch
	 * 
	 * @param tempBranch - an ENUM represents the user temporary branch
	 *
	 * 
	 */
	public void setTempBranch(E_Branches tempBranch) {
		this.tempBranch = tempBranch;
		System.out.println(this.tempBranch.toString());
	}

	/**
	 * Gets the user credit card number
	 * 
	 * @return a string represents the user credit card number
	 * 
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * Sets the user credit card number
	 * 
	 * @param cardNumber - a string represents the user credit card number
	 *
	 * 
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * Gets the user business name
	 * 
	 * @return a string represents the user business name
	 * 
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * Sets the user business name
	 * 
	 * @param businessName - a string represents the user business name
	 *
	 * 
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * Gets the user business used money amount
	 *
	 * @return a double represents the user business used money amount
	 * 
	 */
	public double getBusinessAmountUsed() {
		return businessAmountUsed;
	}

	/**
	 * Sets the user business used money amount
	 * 
	 * @param businessAmountUsed - a double represents the user business used money
	 *                           amount
	 *
	 * 
	 */
	public void setBusinessAmountUsed(double businessAmountUsed) {
		this.businessAmountUsed = businessAmountUsed;
	}

	/**
	 * Adds user business user money amount
	 * 
	 * @param businessAmountUsed - a double represents the user business used money
	 *                           amount
	 *
	 * 
	 */
	public void addBusinessAmountUsed(double businessAmountUsed) {
		this.businessAmountUsed += businessAmountUsed;
	}

	/**
	 * Gets the user business monthly money limit
	 * 
	 * @return a double represents the user business monthly money limit
	 * 
	 */
	public double getBusinessMonthlyLimit() {
		return businessMonthlyLimit;
	}

	/**
	 * Sets the user business monthly money limit
	 * 
	 * @param businessMonthlyLimit - a double represents the user business monthly
	 *                             money limit
	 *
	 * 
	 */
	public void setBusinessMonthlyLimit(double businessMonthlyLimit) {
		this.businessMonthlyLimit = businessMonthlyLimit;
	}

	/**
	 * Gets the user business role
	 * 
	 * @return a string represents the user business role
	 * 
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the user business role
	 * 
	 * @param role - a string represents the user business role
	 *
	 * 
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Updates the user credit for given restaurant
	 * 
	 * @param restID - an int represents the restaurant ID
	 * @param amnt   a double represents the new credit amount
	 * @return the amount of health hero has after attack
	 * 
	 */
	public boolean updateCredit(int restID, double amnt) {
		for (Credit credit : userCreditList) {
			if (credit.getCreditForRestID() == restID) {
				if (amnt == 0) {
					userCreditList.remove(credit);
				} else {
					credit.setCreditAmount(amnt);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the restaurant manger ID
	 * 
	 * @return an int represents the restaurant manager ID
	 * 
	 */
	public int getManageRestID() {
		return manageRestID;
	}

	/**
	 * Sets the restaurant manger ID
	 * 
	 * @param manageRestID - an int represents the restaurant manager ID
	 *
	 */
	public void setManageRestID(int manageRestID) {
		this.manageRestID = manageRestID;
	}

	/**
	 * Gets the rest name.
	 *
	 * @return String represents the restaurant name
	 */
	public String getRestName() {
		return restName;
	}

	/**
	 * Sets the rest name.
	 *
	 * @param restName - String represents the new restaurant name
	 */
	public void setRestName(String restName) {
		this.restName = restName;
	}

	/**
	 * Gets the description.
	 *
	 * @return String represents the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description - String represents the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the address.
	 *
	 * @return String represents the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address String represents the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
