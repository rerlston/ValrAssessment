package za.co.richarde.services.valr.rest.models.v1;

import java.util.Arrays;
import java.util.List;

public class ValrOrderBook {
	private Asks[] Asks;
	private Bids[] Bids;

	public List<Asks> getAsks() {
		return Arrays.asList(Asks);
	}
	
	public void setAsks(final Asks[] asks) {
		this.Asks = asks;
	}
	
	public List<Bids> getBids() {
		return Arrays.asList(Bids);
	}

	public void setBids(final Bids[] bids) {
		this.Bids = bids;
	}
}
