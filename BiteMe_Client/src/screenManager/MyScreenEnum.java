package screenManager;

/**
 * The Enum MyScreenEnum.
 */
public enum MyScreenEnum implements IScreenEnum {


	MAIN_WINDOW, LOGIN_WINDOW, USER_ENTRANCE_WINDOW, CHOOSE_DELIVERY_TYPE, NEW_ORDER_IDENTIFICATION, DELIVERY_INFO, USER_ORDER_HISTORY,
	CHOOSE_RESTAURANT, CHOOSE_DISHES, ORDER_SUMMARY, ORDER_PAYMENT, ORDER_CONFIRMATION, ORDER_WAITING_APPROVAL, ORDER_RECIEVED,
	RESTAURANT_ACTIVEORDERS, RESTAURANT_ENTRANCE, RESTAURANT_ORDER_HISTORY, RESTAURANT_UPDATE_DISH,
	 BRANCHMAN_ENTRANCE, BRANCHMAN_USERS, BRANCHMAN_USERINFO,
	BRANCHMAN_RESTAURANTS, BRANCHMAN_RESTAURANTINFO, BRANCHMAN_MYREPORTS,
	BRANCHMAN_MYREPORTINFO, BRANCHMAN_APPROVEBUSINESS, BRANCHMAN_USERSACTIONS, 
	BRANCHMAN_OPENNEWACCOUNT, BRANCHMAN_CHOOSE_UNAPPROVED_USERS, BRANCHMAN_CHOOSE_RESTAURANTS, BRANCHMAN_PRODUCE_REPORTS,
	HR_ENTRANCE, HR_REGISTER, HR_BUSINESSACCOUNT, HR_APPROVE_BUSINESS_ACCOUNT, RESTAURANT_COMMISSIONS,
	CEO_ENTRANCE, CEO_BM_REPORTS, CEO_SHOW_QUARTERLY_REPORTS, CEO_SHOW_BRANCHMAN_TO_CEO_REPORTS;

	/**
	 * Gets the FXML name.
	 *
	 * @return String represents the FXML file name
	 */
	@Override
	public String getFXMLName() {
		switch (this.name()) {
		case "MAIN_WINDOW":
			return "/common/MainWindow.fxml";
		case "LOGIN_WINDOW":
			return "/common/LogIn.fxml";
		case "USER_ENTRANCE_WINDOW":
			return "/user/UserEntranceWindow.fxml";
		case "UPDATE_USER_INFO":
			return "/user/UserUpdateinfo.fxml";
		case "CHOOSE_DELIVERY_TYPE":
			return "/newOrder/ChooseDeliveryType.fxml";
		case "NEW_ORDER_IDENTIFICATION":
			return "/newOrder/NewOrderIdentification.fxml";
		case "DELIVERY_INFO":
			return "/newOrder/DeliveryInfo.fxml";
		case "CHOOSE_RESTAURANT":
			return "/newOrder/ChooseRestaurant.fxml";
		case "CHOOSE_DISHES":
			return "/newOrder/ChooseDish.fxml";
		case "ORDER_SUMMARY":
			return "/newOrder/OrderSummary.fxml";
		case "ORDER_PAYMENT":
			return "/newOrder/OrderPayment.fxml";
		case "ORDER_WAITING_APPROVAL":
			return "/newOrder/OrderWaitingApproval.fxml";
		case "ORDER_CONFIRMATION":
			return "/newOrder/OrderConfirmation.fxml";
		case "ORDER_RECIEVED":
			return "/newOrder/OrderRecieved.fxml";
		case "RESTAURANT_ACTIVEORDERS":
			return "/restaurant/RestaurantActiveOrdersWindow.fxml";
		case "RESTAURANT_ENTRANCE":
			return "/restaurant/RestaurantEntranceWindow.fxml";
		case "RESTAURANT_ORDER_HISTORY":
			return "/restaurant/RestaurantOrderHistoryWindow.fxml";
		case "RESTAURANT_UPDATE_DISH":
			return "/restaurant/RestaurantUpdateDish.fxml";
		case "BRANCHMAN_ENTRANCE":
			return "/branchManager/BranchManEntranceWindow.fxml";
		case "BRANCHMAN_USERS":
			return "/branchManager/BranchManUsersWindow.fxml";
		case "BRANCHMAN_USERINFO":
			return "/branchManager/BranchManUserInfoWindow.fxml";
		case "BRANCHMAN_RESTAURANTS":
			return "/branchManager/BranchManRestaurantsWindow.fxml";
		case "BRANCHMAN_RESTAURANTINFO":
			return "/branchManager/BranchManRestaurantInfoWindow.fxml";
		case "BRANCHMAN_MYREPORTS":
			return "/branchManager/BranchManMyReportsWindow.fxml";
		case "BRANCHMAN_MYREPORTINFO":
			return "/branchManager/BranchManMyReportsInfoWindow.fxml";
		case "HR_ENTRANCE":
			return "/business/HREntranceWindow.fxml";
		case "HR_REGISTER":
			return "/business/HRRegistrationWindow.fxml";
		case "HR_APPROVE_BUSINESS_ACCOUNT":
			return "/business/HRApproveBusinessUsers.fxml";
		case "BRANCHMAN_APPROVEBUSINESS":
			return "/branchManager/BranchManBusinessWindow.fxml";
		case "BRANCHMAN_USERSACTIONS":
			return "/branchManager/BranchManUsersActionsWindow.fxml";
		case "BRANCHMAN_OPENNEWACCOUNT":
			return "/branchManager/BranchManOpenNewAccountWindow.fxml";
		case "BRANCHMAN_CHOOSE_UNAPPROVED_USERS":
			return "/branchManager/BranchManOpenAcoountForUsers.fxml";
		case "BRANCHMAN_CHOOSE_RESTAURANTS":
			return "/branchManager/BranchManChooseRestaurantsWindow.fxml";
		case "BRANCHMAN_PRODUCE_REPORTS":
			return "/branchManager/BranchManProduceReports.fxml";
		case "USER_ORDER_HISTORY":
			return "/user/UserAllOrdersWindow.fxml";
		case "RESTAURANT_COMMISSIONS":
			return "/restaurant/RestaurantShowCommission.fxml";
		case "CEO_ENTRANCE":
			return "/ceo/CEOEntranceWindow.fxml";
		case "CEO_BM_REPORTS":
			return "/ceo/CEOBMReportsWindow.fxml";
		case "CEO_SHOW_QUARTERLY_REPORTS":
			return "/ceo/CEOShowQuarterlyReportsWindow.fxml";
		case "CEO_SHOW_BRANCHMAN_TO_CEO_REPORTS":
			return "/ceo/CEOReportsFromBranchMan.fxml";
		default:
			return null;
		}
	}

	/**
	 * Gets the screen enum.
	 *
	 * @return the screen enum
	 */
	@Override
	public MyScreenEnum getScreenEnum() {
		return this;
	}
}