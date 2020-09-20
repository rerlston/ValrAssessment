package za.co.richarde.api.models;

public class OrderRequest {
	private String apiKey;
	private String apiSecret;
	private boolean isSell;
	private String quantity;
	private String price;
	private String fromCurrency;
	private String toCurrency;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(final String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(final String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public boolean isSell() {
		return isSell;
	}

	public void setSell(final boolean isSell) {
		this.isSell = isSell;
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
