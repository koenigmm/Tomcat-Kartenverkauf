package kartenverkauf.exceptions;

public class VerkaufReserviertesTicketException extends RuntimeException {

	public VerkaufReserviertesTicketException() {
		super();
	}

	public VerkaufReserviertesTicketException(String message) {
		super(message);
	}

	public VerkaufReserviertesTicketException(Throwable cause) {
		super(cause);
	}

	public VerkaufReserviertesTicketException(String message, Throwable cause) {
		super(message, cause);
	}

	public VerkaufReserviertesTicketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
