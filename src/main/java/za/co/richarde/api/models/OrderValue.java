package za.co.richarde.api.models;

public class OrderValue {
	private double quantity;
	private long price;
	private int orderCount;

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(final double quantity) {
		this.quantity = quantity;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(final long price) {
		this.price = price;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(final int orderCount) {
		this.orderCount = orderCount;
	}

}
