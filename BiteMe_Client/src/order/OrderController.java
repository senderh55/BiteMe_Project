package order;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.ArrayList;

import Order.Order;
import dish.DishInOrder;
import utils.E_OrderStatus;
import utils.Message;

/**
 * This class is the controller for order entity.
 */
public class OrderController {
	
	/** The order entity. */
	Order order = new Order();
	
	/** The order - list of dishes (DishInOrder). */
	ArrayList<DishInOrder> orderList = new ArrayList<>();
	
	/** The open orders - list of open orders. */
	ArrayList<Order> openOrders = new ArrayList<>();
	
	/** The All user orders - list of orders history for a user. */
	ArrayList<Order> AllUserOrders = new ArrayList<>();

	/** The number of group order. */
	int numberOfGroupOrder = 0;
	
	/** The confirmation stage. */
	int confirmationStage = 0;

	/**
	 * Builds the open orders list.
	 *
	 * @param message - Message to the DB
	 */
	public void buildOpenOrdersList(Message message) {
		openOrders.clear();
		for (int i = 0; i < message.getMsgArrayL().size(); i++) {
			String orderInfo = (String) message.getMsgArrayL().get(i);
			String[] splitter = orderInfo.split(";");
			if (!splitter[6].equals("NA") && splitter[7].equals("NA")) {
				Order order = new Order();
				order.setOrderNumber(splitter[0]);
				order.setRestID(Integer.parseInt(splitter[1]));
				order.setRestName(splitter[3]);
				order.setDesiredOrderDate(LocalDate.parse(splitter[5]));
				order.setOrderApprovedByRestTime(LocalTime.parse(splitter[6]));
				order.setOrderStatus(E_OrderStatus.valueOf(splitter[11]));

				openOrders.add(order);
			}
		}
	}

	/**
	 * Adds the to order list.
	 *
	 * @param dishInOrder - DishInOrder represents the dish in order
	 * @return true, if added, else false
	 */
	public boolean addToOrderList(DishInOrder dishInOrder) {

		if (!orderList.add(dishInOrder)) {
			return false;
		}
		order.addTotalPrice(dishInOrder.getPriceOfDishBySize() * dishInOrder.getAmountOfDish());
		for (DishInOrder dio : orderList) {
			System.out.println(dio.getDishName() + " " + dio.getPriceOfDishBySize() + " " + dio.getAmountOfDish()
					+ dio.getOptionToRemove().toString() + ";  ");
		}
		System.out.println(order.getTotalPrice());
		return true;
	}

	/**
	 * Reset order (all the dishes in it).
	 */
	public void resetOrder() {
		orderList.clear();
		// order.setDishList(new ArrayList<>());
		order.setTotalPrice(0);
	}

	/**
	 * Gets the order.
	 *
	 * @return Order represents the order
	 */
	public Order getOrder() {
		return this.order;
	}

	/**
	 * Gets the order list.
	 *
	 * @return list of DishesIn Order represents the order dishes list
	 */
	public ArrayList<DishInOrder> getOrderList() {
		return orderList;
	}

	/**
	 * Sets the order list.
	 *
	 * @param orderList - list of DishInOrder represents the new order dishes list
	 */
	public void setOrderList(ArrayList<DishInOrder> orderList) {
		this.orderList = orderList;
	}

	/**
	 * Gets the confirmation stage.
	 *
	 * @return inr represents the confirmation stage
	 */
	public int getConfirmationStage() {
		return confirmationStage;
	}

	/**
	 * Sets the confirmation stage.
	 *
	 * @param confirmationStage - int represents the new confirmation stage
	 */
	public void setConfirmationStage(int confirmationStage) {
		this.confirmationStage = confirmationStage;
	}

	/**
	 * Gets the open orders.
	 *
	 * @return list of ORders represents the open orders
	 */
	public ArrayList<Order> getOpenOrders() {
		return openOrders;
	}

	/**
	 * Builds the orders history list for a user.
	 *
	 * @param message - Message from the server (DB)
	 */
	public void buildAllUserOrdersList(Message message) {
		AllUserOrders.clear();
		for (int i = 0; i < message.getMsgArrayL().size(); i++) {
			String orderInfo = (String) message.getMsgArrayL().get(i);
			String[] splitter = orderInfo.split(";");
			Order order = new Order();
			order.setOrderNumber(splitter[0]);
			order.setRestName(splitter[3]);
			;
			order.setDesiredOrderTime(LocalTime.parse(splitter[4]));
			order.setDesiredOrderDate(LocalDate.parse(splitter[5]));
			order.setTotalPrice(Double.parseDouble(splitter[14]));
			// SKIP: address, city, phonnumber,
			order.setOrderStatus(E_OrderStatus.valueOf(splitter[11]));

			AllUserOrders.add(order);
		}
	}

	/**
	 * Gets the user's orders history list.
	 *
	 * @return list of orders represents the user's orders history
	 */
	public ArrayList<Order> getAllUserOrders() {
		return AllUserOrders;
	}

	/**
	 * Gets the number of group order.
	 *
	 * @return int represents the number of group order
	 */
	public int getNumberOfGroupOrder() {
		return numberOfGroupOrder;
	}

	/**
	 * Sets the number of group order.
	 *
	 * @param numberOfGroupOrder - int represents the new number of group order
	 */
	public void setNumberOfGroupOrder(int numberOfGroupOrder) {
		this.numberOfGroupOrder = numberOfGroupOrder;
	}

}
