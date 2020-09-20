package za.co.richarde.services.valr.rest.models.v1;

public class ValrOrder {
	private String side;
	private String quantity;
	private String price;
	private String pair;
	private boolean postOnly;

	public String getSide() {
		return side;
	}

	public void setSide(final String side) {
		this.side = side;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(final String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(final String price) {
		this.price = price;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(final String pair) {
		this.pair = pair;
	}

	public boolean isPostOnly() {
		return postOnly;
	}

	public void setPostOnly(final boolean postOnly) {
		this.postOnly = postOnly;
	}
}
