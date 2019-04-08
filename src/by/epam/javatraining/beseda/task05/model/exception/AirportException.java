package by.epam.javatraining.beseda.task05.model.exception;

/**
 *
 * @author User
 */
public class AirportException extends Exception {

    public AirportException() {
    }

    public AirportException(String message) {
        super(message);
    }

    public AirportException(String message, Throwable cause) {
        super(message, cause);
    }

    public AirportException(Throwable cause) {
        super(cause);
    }

    public AirportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
