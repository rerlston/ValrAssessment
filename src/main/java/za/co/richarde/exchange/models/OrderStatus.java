package za.co.richarde.exchange.models;

public class OrderStatus {
	private String orderId;
	private String orderStatusType;
	private String currencyPair;
	private String originalPrice;
	private String remainingQuantity;
	private String originalQuantity;
	private String orderSide;
	private String orderType;
	private String failedReason;
	private String orderUpdatedAt;
	private String orderCreatedAt;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(final String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatusType() {
		return orderStatusType;
	}

	public void setOrderStatusType(final String orderStatusType) {
		this.orderStatusType = orderStatusType;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(final String currencyPair) {
		this.currencyPair = currencyPair;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(final String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(final String remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}

	public String getOriginalQuantity() {
		return originalQuantity;
	}

	public void setOriginalQuantity(final String originalQuantity) {
		this.originalQuantity = originalQuantity;
	}

	public String getOrderSide() {
		return orderSide;
	}

	public void setOrderSide(final String orderSide) {
		this.orderSide = orderSide;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(final String orderType) {
		this.orderType = orderType;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(final String failedReason) {
		this.failedReason = failedReason;
	}

	public String getOrderUpdatedAt() {
		return orderUpdatedAt;
	}

	public void setOrderUpdatedAt(final String orderUpdatedAt) {
		this.orderUpdatedAt = orderUpdatedAt;
	}

	public String getOrderCreatedAt() {
		return orderCreatedAt;
	}

	public void setOrderCreatedAt(final String orderCreatedAt) {
		this.orderCreatedAt = orderCreatedAt;
	}
}
