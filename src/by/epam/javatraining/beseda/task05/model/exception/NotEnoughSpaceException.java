package by.epam.javatraining.beseda.task05.model.exception;

/**
 *
 * @author User
 */
public class NotEnoughSpaceException extends AirportLogicException{

    public NotEnoughSpaceException() {
    }

    public NotEnoughSpaceException(String message) {
        super(message);
    }
    
}
