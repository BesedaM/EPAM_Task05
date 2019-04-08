package by.epam.javatraining.beseda.task05.model.exception;

/**
 *
 * @author User
 */
public class IllegalTicketValueException extends AirportLogicException{

    public IllegalTicketValueException() {
    }

    public IllegalTicketValueException(String message) {
        super(message);
    }
    
    
}
