package za.co.richarde.services.internal;

import java.util.List;

import za.co.richarde.api.models.OrderRequest;
import za.co.richarde.exchange.models.Order;
import za.co.richarde.exchange.models.OrderBook;
import za.co.richarde.exchange.models.OrderValue;
import za.co.richarde.services.internal.models.InternalAsk;
import za.co.richarde.services.internal.models.InternalBid;
import za.co.richarde.services.internal.models.InternalOrderBook;

public class ExchangeMapper {
	public String toCurrencyPair(final String fromCurrency, final String toCurrency) {
		String response = "";
		if(fromCurrency == null) {
			response += "ZAR";
		} else {
			response += fromCurrency;
		}
		if(toCurrency == null) {
			response += "BTC";
		} else {
			response += toCurrency;
		}
		
		return response;
	}

	public OrderBook toOrderBook(final InternalOrderBook orderBooks, final String currency1, final String currency2) {
		final OrderBook response = new OrderBook();
		
		final List<InternalAsk> asks = orderBooks.getAsks();
		for(final InternalAsk ask : asks) {
			if(ask.getFromCurrency().equals(currency1) || ask.getFromCurrency().equals(currency2)) {
				if(ask.getToCurrency().equals(currency1) || ask.getToCurrency().equals(currency2)) {
					response.addAsk(toOrderValue(ask));
				}
			}
		}
		
		final List<InternalBid> bids = orderBooks.getBids();
		for(final InternalBid bid : bids) {
			if(bid.getFromCurrency().equals(currency1) || bid.getFromCurrency().equals(currency2)) {
				if(bid.getToCurrency().equals(currency1) || bid.getToCurrency().equals(currency2)) {
					response.addBid(toOrderValue(bid));
				}
			}
		}
		
		return response;
	}

	private OrderValue toOrderValue(final InternalAsk ask) {
		final OrderValue response = new OrderValue();
		
		response.setOrderCount(ask.getOrderCount());
		response.setPrice(Long.parseLong(ask.getPrice()));
		response.setQuantity(Double.parseDouble(ask.getQuantity()));
		
		return response;
	}
	
	private OrderValue toOrderValue(final InternalBid bid) {
		final OrderValue response = new OrderValue();
		
		response.setOrderCount(bid.getOrderCount());
		response.setPrice(Long.parseLong(bid.getPrice()));
		response.setQuantity(Double.parseDouble(bid.getQuantity()));
		
		return response;
	}

	public Order toOrder(final OrderRequest order) {
		final Order response = new Order();
		
		response.setFromCurrency(order.getFromCurrency());
		response.setPrice(order.getPrice());
		response.setQuantity(order.getQuantity());
		response.setToCurrency(order.getToCurrency());
		
		return response;
	}

	public Order createCounterOrder(final Order orderRequest) {
		final Order response = new Order();
		
		response.setFromCurrency(orderRequest.getToCurrency());
		response.setPrice(orderRequest.getPrice());
		response.setQuantity(orderRequest.getQuantity());
		response.setToCurrency(orderRequest.getFromCurrency());
		
		return response;
	}
}
