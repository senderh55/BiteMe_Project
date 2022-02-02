package restaurant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Order.Order;
import utils.E_Branches;
import utils.E_DeliveryType;
import utils.E_OrderStatus;
import utils.Message;

/**
 * This class is the controller for the Restaurant entity.
 */
public class RestaurantController {
	
	/** The restaurants list. */
	private ArrayList<Restaurant> restaurantList = new ArrayList<>();

	/** Represents the chosen restaurant in some of the screens (where the end user selects one specific restaurant). */
	private Restaurant chosenRestaurant = null;
	
	/** The all restaurant's orders list. */
	private ArrayList<Order> allRestaurantOrders = new ArrayList<>();
	
	/** The state menu update. */
	private boolean stateMenuUpdate = false;


	/**
	 * Adds the restaurants from the DB.
	 *
	 * @param restaurantListInfo - Message represents the restaurant list info
	 */
	public void addRestaurants(Message restaurantListInfo) {
		restaurantList.clear();
		for (int i = 0; i < restaurantListInfo.getMsgArrayL().size(); i++) {
			String[] splitter = ((String) restaurantListInfo.getMsgArrayL().get(i)).split(";");
			Restaurant rest = new Restaurant();
			rest.setRestaurantID(Integer.parseInt(splitter[0]));
			rest.setName(splitter[1]);
			rest.setBmBranch(E_Branches.valueOf(splitter[2]));
			rest.setDescription(splitter[3]);
			rest.setAddress(splitter[4]);
			rest.setPhoneNumber(splitter[5]);

			restaurantList.add(rest);
		}
			
	}

	/**
	 * Builds the all restaurant's orders list.
	 *
	 * @param messageFromServer - Message from the server
	 */
	public void buildAllRestaurantOrders(Message messageFromServer) {
		allRestaurantOrders.clear();
		for(int i = 0; i< messageFromServer.getMsgArrayL().size();i++) {
			String orderInfoStr = (String) messageFromServer.getMsgArrayL().get(i);
			String[] splitter = orderInfoStr.split(";");
			Order order = new Order();
			order.setOrderNumber(splitter[0]);
			order.setRestID(Integer.parseInt(splitter[1]));
			//Skipped UserID
			//Skipped Restaurant Name
			LocalTime time = LocalTime.parse(splitter[4]);
			LocalDate date = LocalDate.parse(splitter[5]);
			order.setDesiredOrderTime(time);
			order.setDesiredOrderDate(date);
			// Skipped timeApproved, timeRecieved, Address, City, Phonenumber
			order.setOrderStatus(E_OrderStatus.valueOf(splitter[11]));
			order.setDeliveryType(E_DeliveryType.valueOf(splitter[12]));
			//Skipped delOnTime
			order.setTotalPrice(Double.parseDouble(splitter[14]));
			
			//Skipped branch
			allRestaurantOrders.add(order);
		}
	}
	
	
	
	
	/**
	 * Gets the restaurant list.
	 *
	 * @return the restaurants list
	 */
	public ArrayList<Restaurant> getRestaurantList() {
		return restaurantList;
	}

	/**
	 * Gets the chosen restaurant.
	 *
	 * @return Restaurant represents the chosen restaurant
	 */
	public Restaurant getChosenRestaurant() {
		return chosenRestaurant;
	}

	/**
	 * Sets the chosen restaurant.
	 *
	 * @param chosenRestaurant - Restaurant represents the new chosen restaurant
	 */
	public void setChosenRestaurant(Restaurant chosenRestaurant) {
		this.chosenRestaurant = chosenRestaurant;
	}

	/**
	 * Gets the all restaurant orders.
	 *
	 * @return list of orders represents all the restaurant's orders
	 */
	public ArrayList<Order> getAllRestaurantOrders() {
		return allRestaurantOrders;
	}

	/**
	 * Adds the active order list to the restaurants list.
	 *
	 * @param currentOrder - Order represents the current order
	 * @return true, if successful, else false
	 */
	public boolean addActiveOrderList(Order currentOrder) {
		for (Order order : allRestaurantOrders) {
			if (order.getOrderNumber().equals(currentOrder.getOrderNumber())) {
				return false;
			}
		}
		currentOrder.setOrderStatus(E_OrderStatus.E_WAITING_REST_APPROVE);
		allRestaurantOrders.add(currentOrder);
		return true;

	}

	/**
	 * Gets the state menu update.
	 *
	 * @return boolean represents the state menu update
	 */
	public boolean getStateMenuUpdate() {
		return stateMenuUpdate;
	}


	/**
	 * Sets the state menu update.
	 *
	 * @param stateMenuUpdate - boolean represents the new state menu update
	 */
	public void setStateMenuUpdate(boolean stateMenuUpdate) {
		this.stateMenuUpdate = stateMenuUpdate;
	}

	
}
