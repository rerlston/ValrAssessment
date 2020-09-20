package za.co.richarde.services.internal;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;

import za.co.richarde.Configuration;
import za.co.richarde.exchange.DataService;
import za.co.richarde.exchange.exceptions.ExchangeException;
import za.co.richarde.exchange.models.Currency;
import za.co.richarde.exchange.models.Order;
import za.co.richarde.exchange.models.OrderBook;
import za.co.richarde.exchange.models.OrderStatus;
import za.co.richarde.services.internal.models.InternalOrderBook;

public class InternalDataService implements DataService {
	private final List<Currency> currencies;
	private final InternalOrderBook orderBooks;
	private final ExchangeMapper mapper;
	private final Map<String, Double> owned;
	private final List<OrderStatus> history;
	
	public InternalDataService(final ExchangeMapper mapper) {
		currencies = new ArrayList<Currency>();
		
		currencies.add(createCurrency("ZAR", "South African Rands", "R"));
		currencies.add(createCurrency("BTC", "Bitcoin", "BTC"));
		currencies.add(createCurrency("ETH", "Ethereum", "ETH"));
		currencies.add(createCurrency("LTC", "Litecoin", "LTC"));
		
		final InputStream is = getClass().getClassLoader().getResourceAsStream("orderbook.json");
		final Reader reader = new InputStreamReader(is);
		orderBooks = new Gson().fromJson(reader, InternalOrderBook.class);
		
		owned = new HashMap<String, Double>();
		owned.put("ZAR", 1d);
		owned.put("BTC", 1d);
		owned.put("ETH", 1d);
		owned.put("LTC", 1d);
		
		history = new ArrayList<OrderStatus>();
		
		this.mapper = mapper;
	}
	
	private Currency createCurrency(final String code, final String longName, final String symbol) {
		final Currency response = new Currency();
		response.setCode(code);
		response.setName(longName);
		response.setSymbol(symbol);
		
		return response;
	}
	
	@Override
	public List<Currency> getCurrencyList(String apiKey) throws ExchangeException {
		return currencies;
	}

	@Override
	public OrderBook getOrderBook(String apiKey, String fromCurrency, String toCurrency) throws ExchangeException {
		return mapper.toOrderBook(orderBooks, fromCurrency, toCurrency);
	}

	// Creative license taken here that sell means exchange between 2 currencies
	@Override
	public OrderStatus placeLimitSellOrder(String apiKey, String apiSecret, Order orderRequest) throws ExchangeException {
		final Order counterOrder = mapper.createCounterOrder(orderRequest);
		final String fromCurrency = orderRequest.getFromCurrency();
		final String toCurrency = orderRequest.getToCurrency();
		
		final Double ownedQuantity = owned.get(fromCurrency);
		final Double originalToQuantity = owned.get(toCurrency);
		if(ownedQuantity.compareTo(Double.parseDouble(orderRequest.getQuantity())) >= 0) {
			final double price = Double.parseDouble(orderRequest.getPrice());
			final double quantity = Double.parseDouble(orderRequest.getQuantity());
			final double value = price * quantity;
			
			owned.put(fromCurrency, ownedQuantity - quantity);
			owned.put(toCurrency, originalToQuantity + value);
			
			final OrderStatus orderStatus = createTransactionHistory(orderRequest, true, ownedQuantity, fromCurrency);
			
			history.add(orderStatus);
			history.add(createTransactionHistory(counterOrder, false, originalToQuantity, toCurrency));
			
			return orderStatus;
		} else {
			final OrderStatus orderStatus = createTransactionHistory(orderRequest, false, ownedQuantity, fromCurrency);
			orderStatus.setFailedReason("You have insufficient quantity of " + fromCurrency + " to sell the requested amount");
			history.add(orderStatus);
			return orderStatus;
		}
	}

	// Creative license taken here that buy means purchase a cryptocurrency from your ZAR wallet
	@Override
	public OrderStatus placeLimitBuyOrder(String apiKey, String apiSecret, Order orderRequest) throws ExchangeException {
		final Order counterOrder = mapper.createCounterOrder(orderRequest);
		final String fromCurrency = "ZAR";
		String toCurrency = orderRequest.getToCurrency();
		if(toCurrency.contentEquals("ZAR")) {
			toCurrency = orderRequest.getFromCurrency();
		}
		
		final Double ownedQuantity = owned.get(fromCurrency);
		final Double originalToQuantity = owned.get(toCurrency);
		if(ownedQuantity.compareTo(Double.parseDouble(orderRequest.getPrice())) >= 0) {
			final double price = Double.parseDouble(orderRequest.getPrice());
			final double quantity = Double.parseDouble(orderRequest.getQuantity());
			
			owned.put(fromCurrency, ownedQuantity - price);
			owned.put(toCurrency, originalToQuantity + quantity);
			
			final OrderStatus orderStatus = createTransactionHistory(orderRequest, false, ownedQuantity, fromCurrency);
			
			history.add(orderStatus);
			history.add(createTransactionHistory(counterOrder, false, originalToQuantity, toCurrency));
			
			return orderStatus;
		} else {
			final OrderStatus orderStatus = createTransactionHistory(orderRequest, false, ownedQuantity, fromCurrency);
			orderStatus.setFailedReason("Insufficient Rand funds to buy " + toCurrency);
			history.add(orderStatus);
			return orderStatus;
		}
	}
	
	private OrderStatus createTransactionHistory(final Order order, final boolean isSell, double originalQuantity, final String currency) {
		final DateFormat dateFormatter = new SimpleDateFormat(Configuration.STANDARD_DATE_FORMAT);
		final OrderStatus response = new OrderStatus();
		response.setCurrencyPair(mapper.toCurrencyPair(order.getFromCurrency(), order.getToCurrency()));
		response.setOrderCreatedAt(dateFormatter.format(new Date()));
		response.setOrderId(UUID.randomUUID().toString());
		if(isSell) {
			response.setOrderSide(Configuration.SELL);
		} else {
			response.setOrderSide(Configuration.BUY);
		}
		response.setOrderStatusType(Configuration.PLACED);
		response.setOrderType("order");
		response.setOriginalPrice(order.getPrice());
		response.setOriginalQuantity(Double.toString(originalQuantity));
		response.setRemainingQuantity(owned.get(currency).toString());
		
		return response;
	}
	
	@Override
	public List<OrderStatus> getHistory(String userIdentifier) {
		return history;
	}
	
	public double getOwned(final String currency) {
		return owned.get(currency);
	}
	
	public void setOwned(final String currency, final double amount) {
		owned.put(currency, amount);
	}
}
