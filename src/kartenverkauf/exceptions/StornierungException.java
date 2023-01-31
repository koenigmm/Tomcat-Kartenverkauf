package kartenverkauf.exceptions;

public class StornierungException extends RuntimeException {
    public StornierungException() {
        super();
    }

    public StornierungException(String message) {
        super(message);
    }

    public StornierungException(String message, Throwable cause) {
        super(message, cause);
    }

    public StornierungException(Throwable cause) {
        super(cause);
    }

    protected StornierungException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
