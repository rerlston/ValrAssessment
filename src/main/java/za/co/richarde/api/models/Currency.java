package za.co.richarde.api.models;

public class Currency {
	private String longName;
	private String symbol;
	private String shortName;

	public String getLongName() {
		return longName;
	}

	public void setLongName(final String longName) {
		this.longName = longName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(final String symbol) {
		this.symbol = symbol;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

}
