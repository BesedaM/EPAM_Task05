package by.epam.javatraining.beseda.task05.util.creator;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.entity.Ticket;
import java.util.Random;
import by.epam.javatraining.beseda.task05.systemconfig.PropertyValue;

/**
 *
 * @author Beseda
 * @version 1.0 19/04/2019
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
