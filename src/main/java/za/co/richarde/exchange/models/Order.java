package za.co.richarde.exchange.models;

public class Order {
	private String quantity;
	private String price;
	private String fromCurrency;
	private String toCurrency;

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
}
