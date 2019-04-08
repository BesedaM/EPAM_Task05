package by.epam.javatraining.beseda.task05.util.creator;

import by.epam.javatraining.beseda.task05.model.entity.Passenger;
import by.epam.javatraining.beseda.task05.model.exception.IllegalTicketValueException;
import static by.epam.javatraining.beseda.task05.util.creator.TicketFactory.createRandomTicket;
import java.util.Random;

/**
 *
 * @author User
 */
public class PassengerFactory {

    public static final String[] NAME
            = {"Liza", "Jack", "Russel", "Ben", "Alex", "Mortimer", "Tina"};
    public static final String[] SURNAME
            = {"Black", "Johns", "Smith", "Williams", "Lubovski", "Chang", "Komarov"};

    static Passenger createPassenger() {
        return new Passenger(NAME[new Random().nextInt(NAME.length)],
                SURNAME[new Random().nextInt(SURNAME.length)]);
    }

    public static Passenger createPassengerWithTicket() throws IllegalTicketValueException{
        Passenger p = createPassenger();
        p.addTicket(createRandomTicket());
        return p;
    }
}
