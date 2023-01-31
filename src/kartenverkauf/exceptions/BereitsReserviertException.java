package kartenverkauf.exceptions;

public class BereitsReserviertException extends RuntimeException {
    public BereitsReserviertException() {
        super();
    }

    public BereitsReserviertException(String message) {
        super(message);
    }

    public BereitsReserviertException(Throwable cause) {
        super(cause);
    }

    public BereitsReserviertException(String message, Throwable cause) {
        super(message, cause);
    }

    public BereitsReserviertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
