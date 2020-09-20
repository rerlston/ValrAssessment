package za.co.richarde.services.internal.models;

public class InternalBid {
	private String fromCurrency;
	private String toCurrency;
	private String quantity;
	private String price;
	private int orderCount;

	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(final String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(final String toCurrency) {
		this.toCurrency = toCurrency;
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

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(final int orderCount) {
		this.orderCount = orderCount;
	}

}
