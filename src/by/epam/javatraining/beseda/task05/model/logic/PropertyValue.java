package by.epam.javatraining.beseda.task05.model.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class PropertyValue {

    public static final String[] DESTINATION = new String[5];

    private static final Properties prop;

    public static final int WAIT_BEFORE_LANDING;
    public static final int TERMINAL_WAIT_AFTER_DEPARTURE;
    public static final int WAIT_BEFORE_TERMINAL_FREE;
    public static final int WAIT_BEFORE_DEPARTURE;
    public static final int PASSENGER_WAIT_BEFORE_CHECKING;
    public static final int SPARE_SEATS;
    public static final int PASSENGER_FACTORY_SLEEP;
    public static final int PASSENGER_NUMBER_TO_CREATE;

    static {
        prop = new Properties();
        loadProperties();
        WAIT_BEFORE_LANDING = Integer.parseInt(prop.getProperty("waitBeforeLanding"));
        TERMINAL_WAIT_AFTER_DEPARTURE
                = Integer.parseInt(prop.getProperty("terminalWaitAfterDeparture"));
        WAIT_BEFORE_TERMINAL_FREE
                = Integer.parseInt(prop.getProperty("waitBeforeTerminalFree"));
        WAIT_BEFORE_DEPARTURE
                = Integer.parseInt(prop.getProperty("waitBeforeNextDeparture"));
        PASSENGER_WAIT_BEFORE_CHECKING
                = Integer.parseInt(prop.getProperty("passengerWaitForAnotherFlight"));
        SPARE_SEATS
                = Integer.parseInt(prop.getProperty("seatsLeftSpare"));
        PASSENGER_FACTORY_SLEEP
                = Integer.parseInt(prop.getProperty("passengerFactorySleep"));
        PASSENGER_NUMBER_TO_CREATE
                = Integer.parseInt(prop.getProperty("passengerNumberToCreate"));
        DESTINATION[0] = "London";
        DESTINATION[1] = "Paris";
        DESTINATION[2] = "Vienna";
        DESTINATION[3] = "Madrid";
        DESTINATION[4] = "Praha";
    }

    private static void loadProperties() {
        try {
            prop.load(new FileInputStream("src//config.properties"));
        } catch (IOException ex) {
            Logger.getLogger(PropertyValue.class.getSimpleName()).error(ex);
        }

    }

}
