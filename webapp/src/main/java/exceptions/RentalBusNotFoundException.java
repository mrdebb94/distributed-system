package exceptions;

public class RentalBusNotFoundException extends  Exception {
    public RentalBusNotFoundException() {
    }

    public RentalBusNotFoundException(String message) {
        super(message);
    }

    public RentalBusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalBusNotFoundException(Throwable cause) {
        super(cause);
    }

    public RentalBusNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
