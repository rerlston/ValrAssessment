package za.co.richarde.services.valr;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import za.co.richarde.Configuration;
import za.co.richarde.exchange.DataService;
import za.co.richarde.exchange.exceptions.ExchangeException;
import za.co.richarde.exchange.models.Currency;
import za.co.richarde.exchange.models.Order;
import za.co.richarde.exchange.models.OrderBook;
import za.co.richarde.exchange.models.OrderStatus;
import za.co.richarde.services.valr.exceptions.VALRException;
import za.co.richarde.services.valr.rest.models.v1.ValrCurrency;
import za.co.richarde.services.valr.rest.models.v1.ValrOrder;
import za.co.richarde.services.valr.rest.models.v1.ValrOrderBook;

public class ValrRESTService implements DataService {
	private final ExchangeMapper mapper;
	private final ValrSigningService signingService;
	private final JSONRESTClientService restService;
	
	// generally should use injection management
	public ValrRESTService(final ExchangeMapper mapper, final ValrSigningService signingService, final JSONRESTClientService restService) {
		this.mapper = mapper;
		this.signingService = signingService;
		this.restService = restService;
	}
	
	public List<Currency> getCurrencyList(final String apiKey) throws ExchangeException {
		final String timestamp = Long.toString(System.currentTimeMillis());

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Configuration.HEADER_VALR_TIMESTAMP, timestamp);
		headers.put(Configuration.HEADER_VALR_API_KEY, apiKey);
		
		try {
			final String response = restService.doGet(Configuration.VALR_BASE_URL, Configuration.CURRENCY_LIST, headers);
		
			final Type listType = new TypeToken<ArrayList<ValrCurrency>>(){}.getType();
			final List<ValrCurrency> currencies = new Gson().fromJson(response, listType);
		
			return mapper.toCurrencyList(currencies);
		} catch(final VALRException exception) {
			throw new ExchangeException("Failed to retrieve the data from VALR", exception);
		}
	}
	
	public OrderBook getOrderBook(final String apiKey, final String fromCurrency, final String toCurrency) throws ExchangeException {
		final String timestamp = Long.toString(System.currentTimeMillis());

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Configuration.HEADER_VALR_TIMESTAMP, timestamp);
		headers.put(Configuration.HEADER_VALR_API_KEY, apiKey);
		
		final String currencyPair = mapper.toCurrencyPair(fromCurrency, toCurrency);
		final String url = Configuration.ORDER_BOOK_URL.replace("{currencyPair}", currencyPair);
		
		try {
			final String response = restService.doGet(Configuration.VALR_BASE_URL, url, headers);

			final ValrOrderBook orderBook = new Gson().fromJson(response, ValrOrderBook.class);
		
			return mapper.toOrderBook(orderBook);
		} catch(final VALRException exception) {
			throw new ExchangeException("Failed to retrieve the data from VALR", exception);
		}
	}
	
	public OrderStatus placeLimitSellOrder(final String apiKey, final String apiSecret, final Order orderRequest)  throws ExchangeException {
		final String timestamp = Long.toString(System.currentTimeMillis());
		final ValrOrder order = mapper.toValrOrder(orderRequest, "SELL", true);
		final String body = new Gson().toJson(order);
		final String signature = signingService.signRequest(apiSecret, timestamp, "POST", Configuration.LIMIT_ORDER_URL, body);

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Configuration.HEADER_VALR_TIMESTAMP, timestamp);
		headers.put(Configuration.HEADER_VALR_API_KEY, apiKey);
		headers.put(Configuration.HEADER_VALR_SIGNATURE, signature);
		
		try {
			final String response = restService.doPost(Configuration.VALR_BASE_URL, Configuration.LIMIT_ORDER_URL, headers, body);
			return null;
		} catch(final VALRException exception) {
			throw new ExchangeException("Failed to retrieve the data from VALR", exception);
		}
	}

	@Override
	public OrderStatus placeLimitBuyOrder(String apiKey, String apiSecret, Order orderRequest) throws ExchangeException {
		final String timestamp = Long.toString(System.currentTimeMillis());
		final ValrOrder order = mapper.toValrOrder(orderRequest, "SELL", true);
		final String body = new Gson().toJson(order);
		final String signature = signingService.signRequest(apiSecret, timestamp, "POST", Configuration.LIMIT_ORDER_URL, body);

		final Map<String, String> headers = new HashMap<String, String>();
		headers.put(Configuration.HEADER_VALR_TIMESTAMP, timestamp);
		headers.put(Configuration.HEADER_VALR_API_KEY, apiKey);
		headers.put(Configuration.HEADER_VALR_SIGNATURE, signature);
		
		try {
			final String response = restService.doPost(Configuration.VALR_BASE_URL, Configuration.LIMIT_ORDER_URL, headers, body);
			return null;
		} catch(final VALRException exception) {
			throw new ExchangeException("Failed to retrieve the data from VALR", exception);
		}
	}

	@Override
	public List<OrderStatus> getHistory(String userIdentifier) {
		return null;
	}
}
