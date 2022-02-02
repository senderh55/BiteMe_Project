package business;

import java.util.ArrayList;

import utils.E_Branches;
import utils.Message;

/**
 * The Class BusinessController is the controller for the Business entity.
 */
public class BusinessController {
	
	/** The north business list. */
	ArrayList<Business> northBusinessList = new ArrayList<>();
	
	/** The center business list. */
	ArrayList<Business> centerBusinessList = new ArrayList<>();
	
	/** The south business list. */
	ArrayList<Business> southBusinessList = new ArrayList<>();
	
	/** The all business list. */
	ArrayList<Business> allBusinessList = new ArrayList<>();
	
	/**
	 * Builds the relevant business list.
	 *
	 * @param businessInfo - Message from the server represents the business info
	 */
	public void buildBusinessList(Message businessInfo) {
		northBusinessList.clear();
		centerBusinessList.clear();
		southBusinessList.clear();
		allBusinessList.clear();
		for (int i = 0; i < businessInfo.getMsgArrayL().size(); i++) {
			String businessInfoStr = (String) businessInfo.getMsgArrayL().get(i);
			String[] splitter = businessInfoStr.split(";");
			Business business = new Business();
			business.setBusinessW4CNumber(Integer.parseInt(splitter[0]));
			business.setCompanyName(splitter[1]);
			business.setEmployerName(splitter[2]);
			business.setDefaultBranch(E_Branches.valueOf(splitter[3]));
			business.setMounthlyLimit(splitter[5]);
			allBusinessList.add(business);
			switch (business.getDefaultBranch()) {
			case E_NORTH:
				northBusinessList.add(business);
				break;
			case E_CENTER:
				centerBusinessList.add(business);
				break;
			case E_SOUTH:
				southBusinessList.add(business);
				break;
			default:
				break;
			}
			 
		}
	}

	/**
	 * Gets the all business list.
	 *
	 * @return list of businesses represents the all business list
	 */
	public ArrayList<Business> getAllBusinessList() {
		return allBusinessList;
	}

	/**
	 * Sets the all business list.
	 *
	 * @param allBusinessList - list of businesses represents the new all business list
	 */
	public void setAllBusinessList(ArrayList<Business> allBusinessList) {
		this.allBusinessList = allBusinessList;
	}

	/**
	 * Gets the north business list.
	 *
	 * @return list of businesses represents the north business list
	 */
	public ArrayList<Business> getNorthBusinessList() {
		return northBusinessList;
	}

	/**
	 * Sets the north business list.
	 *
	 * @param northBusinessList - list of businesses represents the new north business list
	 */
	public void setNorthBusinessList(ArrayList<Business> northBusinessList) {
		this.northBusinessList = northBusinessList;
	}

	/**
	 * Gets the center business list.
	 *
	 * @return list of businesses represents the center business list
	 */
	public ArrayList<Business> getCenterBusinessList() {
		return centerBusinessList;
	}

	/**
	 * Sets the center business list.
	 *
	 * @param centerBusinessList - list of businesses represents the new center business list
	 */
	public void setCenterBusinessList(ArrayList<Business> centerBusinessList) {
		this.centerBusinessList = centerBusinessList;
	}

	/**
	 * Gets the south business list.
	 *
	 * @return list of businesses represents the south business list
	 */
	public ArrayList<Business> getSouthBusinessList() {
		return southBusinessList;
	}

	/**
	 * Sets the south business list.
	 *
	 * @param southBusinessList - list of businesses represents the new south business list
	 */
	public void setSouthBusinessList(ArrayList<Business> southBusinessList) {
		this.southBusinessList = southBusinessList;
	}

}
