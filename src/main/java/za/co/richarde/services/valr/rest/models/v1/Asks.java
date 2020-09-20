package za.co.richarde.services.valr.rest.models.v1;

public class Asks {
	private String side;
	private String quantity;
	private String price;
	private String currencyPair;
	private int orderCount;

	public String getSide() {
		return side;
	}

	public void setSide(final String side) {
		this.side = side;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(final String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(final String price) {
		this.price = price;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(final String currencyPair) {
		this.currencyPair = currencyPair;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(final int orderCount) {
		this.orderCount = orderCount;
	}
}
