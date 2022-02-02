package Order;

import java.time.LocalDate;
import java.time.LocalTime;

import utils.E_DeliveredOnTime;
import utils.E_DeliveryType;
import utils.E_OrderStatus;
import utils.E_PaymentMethod;

/**
 * The Class Order.
 */
public class Order {

	/** The order number. */
	String orderNumber;

	/** The rest ID. */
	int restID;

	/** The total price. */
	double totalPrice = 0.00;

	/** The delivery type. */
	E_DeliveryType deliveryType;

	/** The payment method. */
	E_PaymentMethod paymentMethod;

	/** The desired order date. */
	// Time Variables
	LocalDate desiredOrderDate;

	/** The desired order time. */
	LocalTime desiredOrderTime;

	/** The recieved time. */
	LocalTime recievedTime;

	/** The order approved by rest time. */
	LocalTime orderApprovedByRestTime;

	/** The order status. */
	E_OrderStatus orderStatus;

	/** The rest name. */
	String restName;

	/** The is group order. */
	boolean isGroupOrder = false;

	/** The early order. */
	boolean earlyOrder;

	/** The del on time. */
	E_DeliveredOnTime delOnTime;

	/**
	 * Gets the del on time.
	 *
	 * @return the del on time
	 */
	public E_DeliveredOnTime getDelOnTime() {
		return delOnTime;
	}

	/**
	 * Sets the del on time.
	 *
	 * @param delOnTime the new del on time
	 */
	public void setDelOnTime(E_DeliveredOnTime delOnTime) {
		this.delOnTime = delOnTime;
	}

	/**
	 * Gets the rest name.
	 *
	 * @return the rest name
	 */
	public String getRestName() {
		return restName;
	}

	/**
	 * Sets the rest name.
	 *
	 * @param restName the new rest name
	 */
	public void setRestName(String restName) {
		this.restName = restName;
	}

	/**
	 * Gets the order number.
	 *
	 * @return the order number
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * Sets the order number.
	 *
	 * @param orderNumber the new order number
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

//	public ArrayList<Dish> getDishList() {
//		return dishList;
//	}
//
//	public void setDishList(ArrayList<Dish> dishList) {
//		this.dishList = dishList;
//	}

	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Sets the total price.
	 *
	 * @param totalPrice the new total price
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * Adds the total price.
	 *
	 * @param totalPrice the total price
	 */
	public void addTotalPrice(double totalPrice) {
		this.totalPrice += totalPrice;
	}

	/**
	 * Gets the delivery type.
	 *
	 * @return the delivery type
	 */
	public E_DeliveryType getDeliveryType() {
		return deliveryType;
	}

	/**
	 * Sets the delivery type.
	 *
	 * @param deliveryType the new delivery type
	 */
	public void setDeliveryType(E_DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * Gets the payment method.
	 *
	 * @return the payment method
	 */
	public E_PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentMethod the new payment method
	 */
	public void setPaymentMethod(E_PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Gets the desired order date.
	 *
	 * @return the desired order date
	 */
	public LocalDate getDesiredOrderDate() {
		return desiredOrderDate;
	}

	/**
	 * Sets the desired order date.
	 *
	 * @param desiredOrderDate the new desired order date
	 */
	public void setDesiredOrderDate(LocalDate desiredOrderDate) {
		this.desiredOrderDate = desiredOrderDate;
	}

	/**
	 * Gets the desired order time.
	 *
	 * @return the desired order time
	 */
	public LocalTime getDesiredOrderTime() {
		return desiredOrderTime;
	}

	/**
	 * Sets the desired order time.
	 *
	 * @param desiredOrderTime the new desired order time
	 */
	public void setDesiredOrderTime(LocalTime desiredOrderTime) {
		this.desiredOrderTime = desiredOrderTime;
	}

	/**
	 * Gets the early order.
	 *
	 * @return the early order
	 */
	public int getEarlyOrder() {
		return earlyOrder == true ? 1 : 0;
	}

	/**
	 * Sets the early order.
	 *
	 * @param earlyOrder the new early order
	 */
	public void setEarlyOrder(boolean earlyOrder) {
		this.earlyOrder = earlyOrder;
	}

	/**
	 * Gets the order status.
	 *
	 * @return the order status
	 */
	public E_OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * Sets the order status.
	 *
	 * @param orderStatus the new order status
	 */
	public void setOrderStatus(E_OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

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
	 * Gets the recieved time.
	 *
	 * @return the recieved time
	 */
	public LocalTime getRecievedTime() {
		return recievedTime;
	}

	/**
	 * Sets the recieved time.
	 *
	 * @param recievedTime the new recieved time
	 */
	public void setRecievedTime(LocalTime recievedTime) {
		this.recievedTime = recievedTime;
	}

	/**
	 * Gets the order approved by rest time.
	 *
	 * @return the order approved by rest time
	 */
	public LocalTime getOrderApprovedByRestTime() {
		return orderApprovedByRestTime;
	}

	/**
	 * Sets the order approved by rest time.
	 *
	 * @param orderApprovedByRestTime the new order approved by rest time
	 */
	public void setOrderApprovedByRestTime(LocalTime orderApprovedByRestTime) {
		this.orderApprovedByRestTime = orderApprovedByRestTime;
	}

	/**
	 * Sets the checks if is group order.
	 *
	 * @param b the new checks if is group order
	 */
	public void setIsGroupOrder(boolean b) {
		isGroupOrder = b;

	}

	/**
	 * Gets the group order.
	 *
	 * @return the group order
	 */
	public boolean getGroupOrder() {
		return isGroupOrder;
	}

}
