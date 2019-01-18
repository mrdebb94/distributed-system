package exceptions;

public class RentalCompanyNotFoundException extends Exception {
    public RentalCompanyNotFoundException() {
    }

    public RentalCompanyNotFoundException(String message) {
        super(message);
    }

    public RentalCompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalCompanyNotFoundException(Throwable cause) {
        super(cause);
    }

    public RentalCompanyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
