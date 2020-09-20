package za.co.richarde.api.models;

public class OrderBookRequest {
	private String apiKey;
	private String fromCurrency;
	private String toCurrency;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(final String apiKey) {
		this.apiKey = apiKey;
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
