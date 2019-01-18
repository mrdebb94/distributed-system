package exceptions;

public class RentalCarNotFoundException extends Exception {
    public RentalCarNotFoundException() {
    }

    public RentalCarNotFoundException(String message) {
        super(message);
    }

    public RentalCarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalCarNotFoundException(Throwable cause) {
        super(cause);
    }

    public RentalCarNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
