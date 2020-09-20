package za.co.richarde.api.models;

import java.util.ArrayList;
import java.util.List;

public class OrderBookResponse {
	private List<OrderValue> bids;
	private List<OrderValue> asks;

	public List<OrderValue> getBids() {
		return bids;
	}

	public void setBids(final List<OrderValue> bids) {
		this.bids = bids;
	}
	
	public void addBid(final OrderValue bid) {
		if(bids == null) {
			bids = new ArrayList<OrderValue>();
		}
		bids.add(bid);
	}

	public List<OrderValue> getAsks() {
		return asks;
	}

	public void setAsks(final List<OrderValue> asks) {
		this.asks = asks;
	}
	
	public void addAsk(final OrderValue ask) {
		if(asks == null) {
			asks = new ArrayList<OrderValue>();
		}
		asks.add(ask);
	}

}
