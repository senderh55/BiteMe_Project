package user;

import java.util.ArrayList;

import utils.Credit;
import utils.E_AccountStatus;
import utils.E_Branches;
import utils.E_UserTypeEnum;
import utils.Message;

/**
 * This class is the controller for User entity
 * 
 * @version 1.0
 * @since 1.0
 */
public class UserController {
	/**
	 * Represents the user entity
	 */
	User user = new User();

//	ArrayList<User> businessColleagueList = new ArrayList<>();
//	ArrayList<User> addedBusinessColleagueList = new ArrayList<>();
	/**
	 * Represents a list of users
	 */
	ArrayList<User> usersList = new ArrayList<>();
	/**
	 * Represents the chosen user in some of the screens (where the end user selects
	 * one specific user)
	 */
	User chosenUser;

	/**
	 * Builds a user entity based on data from the server
	 * 
	 * @param userInfo - a Message from the server (all user data from DB)
	 *
	 * @since 1.0
	 */
	public void buildUser(Message userInfo) {
		String userInfoStr = (String) userInfo.getMsgArrayL().get(0);
		String[] splitter = userInfoStr.split(";");
		user = new User();
		user.setUserID(Integer.parseInt(splitter[0]));
		user.setPrivateName(splitter[1]);
		user.setLastName(splitter[2]);
		user.setUsername(splitter[3]);
		user.setPassword(splitter[4]);
		user.setEmail(splitter[5]);
		user.setPhoneNumber(splitter[6]);
		user.setDefaultBranch(E_Branches.valueOf(splitter[7]));
		user.setTempBranch(E_Branches.valueOf(splitter[7]));
		user.setRole(splitter[8]);
		user.setUserType(E_UserTypeEnum.valueOf(splitter[9]));
		user.setManageRestID(Integer.parseInt(splitter[10]));
		user.setIsLoggedIn(Integer.parseInt(splitter[11]));
		user.setAccountStatus(E_AccountStatus.valueOf(splitter[12]));
		user.setW4CNumber(Integer.parseInt(splitter[13]));
		user.setBusinessName(splitter[14]);
		user.setBusinessW4CNumber(Integer.parseInt(splitter[15]));
		user.setBusinessAmountUsed(Double.parseDouble(splitter[16]));
		user.setBusinessMonthlyLimit(Double.parseDouble(splitter[17]));
		if (!splitter[18].equals("0")) {
			String[] creditsSplitter = splitter[18].split(", ");
			for (String specificRestCreditInfo : creditsSplitter) {
				String[] restCreditSplitter = specificRestCreditInfo.split("-");
				int restID = Integer.parseInt(restCreditSplitter[0]);
				double credit = Double.parseDouble(restCreditSplitter[1]);
				user.addUserCreditList(new Credit(restID, credit));
			}
		}
		user.setCardNumber(splitter[19]);
		user.setRestName(splitter[20]);
		user.setDescription(splitter[21]);
		user.setAddress(splitter[22]);
	}

	/**
	 * Builds a users list based on data from the server
	 * 
	 * @param userInfo - a Message from the server (all users data from DB)
	 *
	 * @since 1.0
	 */
	public void buildUsersList(Message userInfo) {
		usersList.clear();
		for (int i = 0; i < userInfo.getMsgArrayL().size(); i++) {
			String userInfoStr = (String) userInfo.getMsgArrayL().get(i);
			String[] splitter = userInfoStr.split(";");

			User user = new User();
			user.setUserID(Integer.parseInt(splitter[0]));
			user.setPrivateName(splitter[1]);
			user.setLastName(splitter[2]);
			user.setUsername(splitter[3]);
			user.setPassword(splitter[4]);
			user.setEmail(splitter[5]);
			user.setPhoneNumber(splitter[6]);
			user.setDefaultBranch(E_Branches.valueOf(splitter[7]));
			user.setTempBranch(E_Branches.valueOf(splitter[7]));
			user.setRole(splitter[8]);
			user.setUserType(E_UserTypeEnum.valueOf(splitter[9]));
			user.setManageRestID(Integer.parseInt(splitter[10]));
			user.setIsLoggedIn(Integer.parseInt(splitter[11]));
			user.setAccountStatus(E_AccountStatus.valueOf(splitter[12]));
			user.setW4CNumber(Integer.parseInt(splitter[13]));
			user.setBusinessName(splitter[14]);
			user.setBusinessW4CNumber(Integer.parseInt(splitter[15]));
			user.setBusinessAmountUsed(Double.parseDouble(splitter[16]));
			user.setBusinessMonthlyLimit(Double.parseDouble(splitter[17]));
			if (!splitter[18].equals("0")) {
				String[] creditsSplitter = splitter[18].split(",");
			}
			user.setCardNumber(splitter[19]);
			user.setRestName(splitter[20]);
			user.setDescription(splitter[21]);
			user.setAddress(splitter[22]);
			usersList.add(user);
		}
	}

	/**
	 * Gets the user entity.
	 *
	 * @return the user
	 * @since 1.0
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Gets the user list
	 * 
	 *
	 * @return a list of User entity represents the users
	 * @since 1.0
	 */
	public ArrayList<User> getUserList() {
		return usersList;
	}

	/**
	 * Sets the chosen user
	 * 
	 * @param user - a User entity that represent a chosen user
	 *
	 * @since 1.0
	 */
	public void setChosenUser(User user) {// asaf
		chosenUser = user;
	}

	/**
	 * Gets the chosen user
	 * 
	 *
	 * @return a User entity that represent a chosen user
	 * @since 1.0
	 */
	public User getChosenUser() {
		return chosenUser;
	}

	/**
	 * Checks user credit in a given restaurant
	 * 
	 * @param restID - an int represents the restaurant ID
	 * @return a double represents the user credit in a given restaurant
	 * @since 1.0
	 */
	public double checkCreditForRest(int restID) {
		for (Credit credit : user.getUserCreditList()) {
			if (credit.getCreditForRestID() == restID) {
				return credit.getCreditAmount();
			}
		}
		return 0.00;
	}
}