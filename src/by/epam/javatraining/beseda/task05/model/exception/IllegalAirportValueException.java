package by.epam.javatraining.beseda.task05.model.exception;

/**
 *
 * @author User
 */
public class IllegalAirportValueException extends AirportLogicException{

    public IllegalAirportValueException() {
    }

    public IllegalAirportValueException(String message) {
        super(message);
    }
    
}
