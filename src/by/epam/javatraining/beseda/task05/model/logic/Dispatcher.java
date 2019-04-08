package by.epam.javatraining.beseda.task05.model.logic;

import by.epam.javatraining.beseda.task05.model.entity.airport.Terminal;
import by.epam.javatraining.beseda.task05.model.exception.IllegalDestinationException;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class Dispatcher {

    public static void setTerminalDestination(Terminal t) {
        try {
            t.setReadyForDeparture();
            t.setDepartureDestination(PropertyValue.DESTINATION[new Random()
                    .nextInt(PropertyValue.DESTINATION.length)]);
        } catch (IllegalDestinationException ex) {
            Logger.getLogger(Dispatcher.class.getSimpleName()).error(ex);
        }
    }

}
