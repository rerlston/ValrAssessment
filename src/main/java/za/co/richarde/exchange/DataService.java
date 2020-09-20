package za.co.richarde.exchange;

import java.util.List;

import za.co.richarde.exchange.exceptions.ExchangeException;
import za.co.richarde.exchange.models.Currency;
import za.co.richarde.exchange.models.Order;
import za.co.richarde.exchange.models.OrderBook;
import za.co.richarde.exchange.models.OrderStatus;

public interface DataService {
	public List<Currency> getCurrencyList(final String apiKey) throws ExchangeException;

	public OrderBook getOrderBook(final String apiKey, final String fromCurrency, final String toCurrency) throws ExchangeException;
	
	public OrderStatus placeLimitSellOrder(final String apiKey, final String apiSecret, final Order orderRequest) throws ExchangeException;
	
	public OrderStatus placeLimitBuyOrder(final String apiKey, final String apiSecret, final Order order) throws ExchangeException;

	public List<OrderStatus> getHistory(final String userIdentifier);
}
