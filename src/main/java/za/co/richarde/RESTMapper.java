package za.co.richarde;

import java.util.ArrayList;
import java.util.List;

import za.co.richarde.api.models.Currency;
import za.co.richarde.api.models.OrderBookResponse;
import za.co.richarde.api.models.OrderStatusResponse;
import za.co.richarde.api.models.OrderValue;
import za.co.richarde.exchange.models.OrderStatus;


public class RESTMapper {

	public List<Currency> toCurrencyResponse(List<za.co.richarde.exchange.models.Currency> currencies) {
		final List<Currency> response = new ArrayList<za.co.richarde.api.models.Currency>();
		
		if(currencies != null) {
			for(final za.co.richarde.exchange.models.Currency currency : currencies) {
				response.add(toCurrencyResponse(currency));
			}
		}
		
		return response;
	}

	public Currency toCurrencyResponse(final za.co.richarde.exchange.models.Currency currency) {
		final Currency response = new Currency();
		
		response.setLongName(currency.getName());
		response.setShortName(currency.getCode());
		response.setSymbol(currency.getSymbol());
		
		return response;
	}

	public OrderBookResponse toOrderBookResponse(za.co.richarde.exchange.models.OrderBook orderBook) {
		final OrderBookResponse response = new OrderBookResponse();
		response.setAsks(toAskResponse(orderBook.getAsks()));
		response.setBids(toBidResponse(orderBook.getBids()));
		
		return response;
	}
	
	public List<OrderValue> toAskResponse(List<za.co.richarde.exchange.models.OrderValue> askList) {
		final List<OrderValue> response = new ArrayList<OrderValue>();
		if(askList != null) {
			for(final za.co.richarde.exchange.models.OrderValue orderValue : askList) {
				response.add(toOrderValue(orderValue));
			}
		}
		
		return response;
	}
	
	public List<OrderValue> toBidResponse(List<za.co.richarde.exchange.models.OrderValue> bidList) {
		final List<OrderValue> response = new ArrayList<OrderValue>();
		if(bidList != null) {
			for(final za.co.richarde.exchange.models.OrderValue orderValue : bidList) {
				response.add(toOrderValue(orderValue));
			}
		}
		
		return response;
	}
	
	public OrderValue toOrderValue(final za.co.richarde.exchange.models.OrderValue orderValue) {
		final OrderValue response = new OrderValue();
		
		response.setOrderCount(orderValue.getOrderCount());
		response.setPrice(orderValue.getPrice());
		response.setQuantity(orderValue.getQuantity());
		
		return response;
	}
	
	public OrderStatusResponse toOrderStatus(final OrderStatus orderStatus) {
		final OrderStatusResponse response = new OrderStatusResponse();
		
		response.setCurrencyPair(orderStatus.getCurrencyPair());
		response.setFailedReason(orderStatus.getFailedReason());
		response.setOrderCreatedAt(orderStatus.getOrderCreatedAt());
		response.setOrderId(orderStatus.getOrderId());
		response.setOrderSide(orderStatus.getOrderSide());
		response.setOrderStatusType(orderStatus.getOrderStatusType());
		response.setOrderType(orderStatus.getOrderType());
		response.setOrderUpdatedAt(orderStatus.getOrderUpdatedAt());
		response.setOriginalPrice(orderStatus.getOriginalPrice());
		response.setOriginalQuantity(orderStatus.getOriginalQuantity());
		response.setRemainingQuantity(orderStatus.getRemainingQuantity());
		
		return response;
	}

	public List<OrderStatusResponse> toHistory(final List<OrderStatus> history) {
		final List<OrderStatusResponse> response = new ArrayList<OrderStatusResponse>();
		
		for(OrderStatus orderStatus : history) {
			response.add(toHistory(orderStatus));
		}
		
		return response;
	}
	
	public OrderStatusResponse toHistory(final OrderStatus orderStatus) {
		final OrderStatusResponse response = new OrderStatusResponse();
		
		response.setCurrencyPair(orderStatus.getCurrencyPair());
		response.setFailedReason(orderStatus.getFailedReason());
		response.setOrderCreatedAt(orderStatus.getOrderCreatedAt());
		response.setOrderId(orderStatus.getOrderId());
		response.setOrderSide(orderStatus.getOrderSide());
		response.setOrderStatusType(orderStatus.getOrderStatusType());
		response.setOrderType(orderStatus.getOrderType());
		response.setOrderUpdatedAt(orderStatus.getOrderUpdatedAt());
		response.setOriginalPrice(orderStatus.getOriginalPrice());
		response.setOriginalQuantity(orderStatus.getOriginalQuantity());
		response.setRemainingQuantity(orderStatus.getRemainingQuantity());
		
		return response;
	}
}
