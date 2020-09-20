package za.co.richarde;

public class Configuration {
	public static final String VALR_BASE_URL = "https://api.valr.com";
	public static final String CURRENCY_LIST = "/v1/public/currencies";
	public static final String ORDER_BOOK_URL = "/v1/public/{currencyPair}/orderbook";
	public static final String LIMIT_ORDER_URL = "/v1/orders/limit";
	
	public static final String HEADER_VALR_API_KEY = "X-VALR-API-KEY";
	public static final String HEADER_VALR_SIGNATURE = "X-VALR-SIGNATURE";
	public static final String HEADER_VALR_TIMESTAMP = "X-VALR-TIMESTAMP";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	
	public static final String APPLICATION_JSON = "application/json";
	
	public static final String SELL = "SELL";
	public static final String BUY = "BUY";
	public static final String PLACED = "Placed";
	
	public static final String STANDARD_DATE_FORMAT = "yyyy-mm-dd hh:mm:ss";
	
	private Configuration() {}
}
