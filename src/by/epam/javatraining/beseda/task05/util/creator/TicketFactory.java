package by.epam.javatraining.beseda.task05.util.creator;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.entity.Ticket;
import java.util.Random;
import by.epam.javatraining.beseda.task05.model.logic.PropertyValue;

/**
 *
 * @author User
 */
public class TicketFactory{

    public static Ticket createTicket(Airplane plane) {
        return new Ticket(plane);
    }

    public static Ticket createRandomTicket() {
        return new Ticket(PropertyValue.DESTINATION[
                new Random().nextInt(PropertyValue.DESTINATION.length)]);
    }

}
