package by.epam.javatraining.beseda.task05.model.exception;

/**
 *
 * @author User
 */
public class IllegalPassengerNameException extends AirportLogicException{

    public IllegalPassengerNameException() {
    }

    public IllegalPassengerNameException(String message) {
        super(message);
    }
    
    
}
