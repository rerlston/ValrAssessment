package za.co.richarde.services.valr.rest.models.v1;

public class ValrCurrency {
	private String symbol;
	private boolean isActive;
	private String shortName;
	private String longName;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(final String symbol) {
		this.symbol = symbol;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(final boolean isActive) {
		this.isActive = isActive;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(final String longName) {
		this.longName = longName;
	}

}
