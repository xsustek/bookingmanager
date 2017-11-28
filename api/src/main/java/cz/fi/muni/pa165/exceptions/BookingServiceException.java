package cz.fi.muni.pa165.exceptions;

public class BookingServiceException extends RuntimeException {

    public BookingServiceException() {
        super();
    }

    public BookingServiceException(String message, Throwable cause,
                                   boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BookingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingServiceException(String message) {
        super(message);
    }

    public BookingServiceException(Throwable cause) {
        super(cause);

    }
}
