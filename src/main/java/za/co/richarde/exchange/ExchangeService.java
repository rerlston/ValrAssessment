package za.co.richarde.exchange;

import java.util.List;

import za.co.richarde.exchange.exceptions.ExchangeException;
import za.co.richarde.exchange.models.Currency;
import za.co.richarde.exchange.models.Order;
import za.co.richarde.exchange.models.OrderBook;
import za.co.richarde.exchange.models.OrderStatus;

public class ExchangeService {
	private DataService service;
	
	// generally should use injection management
	public ExchangeService(final DataService service) {
		this.service = service;
	}

	public List<Currency> getCurrencies(final String userIdentifier) throws ExchangeException {
		return service.getCurrencyList(userIdentifier);
	}
	
	public OrderBook getOrderBook(final String userIdentifier, final String fromCurrency, final String toCurrency) throws ExchangeException {
		return service.getOrderBook(userIdentifier, fromCurrency, toCurrency);
	}

	public OrderStatus placeSellOrder(final String apiKey, final String apiSecret, final Order order) throws ExchangeException {
		return service.placeLimitSellOrder(apiKey, apiSecret, order);
	}
	
	public OrderStatus placeBuyOrder(final String apiKey, final String apiSecret, final Order order) throws ExchangeException {
		return service.placeLimitBuyOrder(apiKey, apiSecret, order);
	}

	public List<OrderStatus> getHistory(final String userIdentifier) {
		return service.getHistory(userIdentifier);
	}
}
