package kartenverkauf.exceptions;

public class BereitsVerkauftException extends RuntimeException {
    public BereitsVerkauftException() {
        super();
    }

    public BereitsVerkauftException(String message) {
        super(message);
    }

    public BereitsVerkauftException(String message, Throwable cause) {
        super(message, cause);
    }

    public BereitsVerkauftException(Throwable cause) {
        super(cause);
    }

    protected BereitsVerkauftException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
