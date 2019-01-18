package exceptions;

public class RentalIntervalInvalidException  extends  Exception{
    public RentalIntervalInvalidException() {
    }

    public RentalIntervalInvalidException(String message) {
        super(message);
    }

    public RentalIntervalInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalIntervalInvalidException(Throwable cause) {
        super(cause);
    }

    public RentalIntervalInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}