package kartenverkauf.exceptions;

public class ReservierbarGlobalFalseException extends RuntimeException {
    public ReservierbarGlobalFalseException() {
        super();
    }

    public ReservierbarGlobalFalseException(String message) {
        super(message);
    }

    public ReservierbarGlobalFalseException(Throwable cause) {
        super(cause);
    }

    public ReservierbarGlobalFalseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservierbarGlobalFalseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
