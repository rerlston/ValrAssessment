package za.co.richarde.exchange.exceptions;

public class ExchangeException extends Exception {
	private static final long serialVersionUID = 3134340051035358452L;
	
	public ExchangeException(final String message) {
		super(message);
	}

	public ExchangeException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
