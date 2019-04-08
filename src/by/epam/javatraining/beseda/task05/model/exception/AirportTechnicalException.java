package by.epam.javatraining.beseda.task05.model.exception;

/**
 *
 * @author User
 */
public class AirportTechnicalException extends AirportException{

    public AirportTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public AirportTechnicalException(Throwable cause) {
        super(cause);
    }

    public AirportTechnicalException(String message, Throwable cause, 
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
}
