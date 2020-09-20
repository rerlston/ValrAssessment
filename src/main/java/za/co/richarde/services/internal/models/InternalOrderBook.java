package za.co.richarde.services.internal.models;

import java.util.List;

public class InternalOrderBook {
	private List<InternalAsk> asks;
	private List<InternalBid> bids;

	public List<InternalAsk> getAsks() {
		return asks;
	}

	public void setAsks(final List<InternalAsk> asks) {
		this.asks = asks;
	}

	public List<InternalBid> getBids() {
		return bids;
	}

	public void setBids(final List<InternalBid> bids) {
		this.bids = bids;
	}
}
