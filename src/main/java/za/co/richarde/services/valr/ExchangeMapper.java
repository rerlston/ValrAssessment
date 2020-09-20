package za.co.richarde.services.valr;

import java.util.ArrayList;
import java.util.List;

import za.co.richarde.exchange.models.Currency;
import za.co.richarde.exchange.models.Order;
import za.co.richarde.exchange.models.OrderBook;
import za.co.richarde.exchange.models.OrderValue;
import za.co.richarde.services.valr.rest.models.v1.Asks;
import za.co.richarde.services.valr.rest.models.v1.Bids;
import za.co.richarde.services.valr.rest.models.v1.ValrCurrency;
import za.co.richarde.services.valr.rest.models.v1.ValrOrder;
import za.co.richarde.services.valr.rest.models.v1.ValrOrderBook;

public class ExchangeMapper {
	public List<Currency> toCurrencyList(final List<ValrCurrency> currencies) {
		final List<Currency> response = new ArrayList<Currency>();
		if(currencies != null) {
			for(final ValrCurrency currency : currencies) {
				response.add(toCurrency(currency));
			}
		}
		
		return response;
	}
	
	public Currency toCurrency(final ValrCurrency currency) {
		final Currency response = new Currency();
		response.setCode(currency.getShortName());
		response.setName(currency.getLongName());
		response.setSymbol(currency.getSymbol());
		
		return response;
	}
	
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

	public OrderBook toOrderBook(final ValrOrderBook orderBook) {
		final OrderBook response = new OrderBook();
		final List<Bids> bids = orderBook.getBids();
		final List<Asks> asks = orderBook.getAsks();
		
		response.setAsks(toAskList(asks));
		response.setBids(toBidList(bids));
		
		return response;
	}

	private List<OrderValue> toBidList(final List<Bids> bids) {
		final List<OrderValue> response = new ArrayList<OrderValue>();
		
		if(bids != null) {
			for(final Bids bid : bids) {
				response.add(toOrderValue(bid));
			}
		}
		
		return response;
	}
	
	private List<OrderValue> toAskList(final List<Asks> asks) {
		final List<OrderValue> response = new ArrayList<OrderValue>();
		
		if(asks != null) {
			for(final Asks ask : asks) {
				response.add(toOrderValue(ask));
			}
		}
		
		return response;
	}
	
	private OrderValue toOrderValue(final Asks ask) {
		final OrderValue response = new OrderValue();
		response.setOrderCount(ask.getOrderCount());
		response.setPrice(Long.parseLong(ask.getPrice()));
		response.setQuantity(Double.parseDouble(ask.getQuantity()));
		
		return response;
	}
	
	private OrderValue toOrderValue(final Bids bid) {
		final OrderValue response = new OrderValue();
		response.setOrderCount(bid.getOrderCount());
		response.setPrice(Long.parseLong(bid.getPrice()));
		response.setQuantity(Double.parseDouble(bid.getQuantity()));
		
		return response;
	}

	public ValrOrder toValrOrder(final Order orderRequest, final String side, final boolean postOnly) {
		final ValrOrder response = new ValrOrder();
		response.setPair(toCurrencyPair(orderRequest.getFromCurrency(), orderRequest.getToCurrency()));
		response.setPostOnly(postOnly);
		response.setPrice(orderRequest.getPrice());
		response.setQuantity(orderRequest.getQuantity());
		response.setSide(side);
		
		return response;
	}
}
