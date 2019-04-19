package by.epam.javatraining.beseda.task05.systemconfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Beseda
 * @version 1.0 19/04/2019
 */
public class PropertyValue {

    public static final String[] DESTINATION;

    private static final Properties prop;

    public static final int WAIT_BEFORE_LANDING;
    public static final int TERMINAL_WAIT_AFTER_DEPARTURE;
    public static final int WAIT_BEFORE_TERMINAL_FREE;
    public static final int WAIT_BEFORE_DEPARTURE;
    public static final int WAITING_FOR_TICKETS_EXCHANGE;
    public static final int PASSENGER_WAIT_BEFORE_CHECKING;
    public static final int PASSENGER_FACTORY_WAITING_INTERVAL;
    public static final int PASSENGER_FACTORY_PASSANGERS_TO_CREATE;
    public static final int WAITING_TIME;

    static {
        prop = new Properties();
        loadProperties();
        DESTINATION = new String[]{"London", "Paris", "Vienna", "Madrid", "Praha"};
        WAIT_BEFORE_LANDING = Integer.parseInt(prop.getProperty("waitBeforeLanding").trim());
        TERMINAL_WAIT_AFTER_DEPARTURE
                = Integer.parseInt(prop.getProperty("terminalWaitAfterDeparture").trim());
        WAIT_BEFORE_TERMINAL_FREE
                = Integer.parseInt(prop.getProperty("waitBeforeTerminalFree").trim());
        WAIT_BEFORE_DEPARTURE
                = Integer.parseInt(prop.getProperty("waitBeforeNextDeparture").trim());
        WAITING_FOR_TICKETS_EXCHANGE
                = Integer.parseInt(prop.getProperty("waitingForTicketsExchange").trim());
        PASSENGER_WAIT_BEFORE_CHECKING
                = Integer.parseInt(prop.getProperty("passengerWaitForAnotherFlight").trim());
        PASSENGER_FACTORY_WAITING_INTERVAL
                = Integer.parseInt(prop.getProperty("passengerFactorySleep").trim());
        PASSENGER_FACTORY_PASSANGERS_TO_CREATE
                = Integer.parseInt(prop.getProperty("passengerNumberToCreate").trim());
        WAITING_TIME
                = Integer.parseInt(prop.getProperty("waitingTime").trim());
    }

    private static void loadProperties() {
        try {
            prop.load(new FileInputStream("src\\by\\epam\\javatraining\\beseda\\task05\\systemconfig\\config.properties"));
        } catch (IOException ex) {
            Logger.getLogger(PropertyValue.class.getSimpleName()).error(ex);
        }

    }

}
