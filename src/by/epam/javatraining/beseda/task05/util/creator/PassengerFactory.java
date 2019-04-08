package by.epam.javatraining.beseda.task05.util.creator;

import by.epam.javatraining.beseda.task05.model.entity.Passenger;
import by.epam.javatraining.beseda.task05.model.entity.airport.Airport;
import by.epam.javatraining.beseda.task05.model.exception.IllegalTicketValueException;
import by.epam.javatraining.beseda.task05.model.logic.PropertyValue;
import static by.epam.javatraining.beseda.task05.util.creator.TicketFactory.createRandomTicket;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class PassengerFactory implements Runnable {

    private Thread thread;
    private Airport aiport;
    private boolean flag = true;

    private static PassengerFactory factory = null;

    {
        this.thread = new Thread(this);
        this.thread.setDaemon(true);
        this.thread.start();
    }

    private PassengerFactory() {
    }

    private PassengerFactory(Airport a) {
        this.aiport = a;
    }

    public static PassengerFactory getFactory(Airport a) {
        if (factory == null) {
            factory = new PassengerFactory(a);
        }
        return factory;
    }

    public static final String[] NAME
            = {"Liza", "Jack", "Russel", "Ben", "Alex", "Mortimer", "Tina"};
    public static final String[] SURNAME
            = {"Black", "Johns", "Smith", "Williams", "Lubovski", "Chang", "Komarov"};

    public static Passenger createPassenger() {
        return new Passenger(NAME[new Random().nextInt(NAME.length)],
                SURNAME[new Random().nextInt(SURNAME.length)]);
    }

    public static Passenger createPassengerWithTicket() throws IllegalTicketValueException {
        Passenger p = createPassenger();
        p.addTicket(createRandomTicket());
        return p;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                TimeUnit.MILLISECONDS.sleep(PropertyValue.PASSENGER_FACTORY_SLEEP);
                for (int i = 0; i < PropertyValue.PASSENGER_NUMBER_TO_CREATE; i++) {
                    Passenger p = createPassenger();
                    p.setAirport(aiport);
                    p.startAction();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(PassengerFactory.class.getSimpleName()).fatal(ex);
            }
        }
    }

    public void stopAction() {
        this.flag = false;
    }
}
