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

    public static final String[] DESTINATION
            = {"London", "Paris", "Vienna", "Madrid", "Praha"};
    private static final Properties prop = new Properties();

    public static final int WAIT_BEFORE_LANDING
            = Integer.parseInt(prop.getProperty("waitBeforeLanding"));
    public static final int TERMINAL_WAIT_AFTER_DEPARTURE
            = Integer.parseInt(prop.getProperty("terminalWaitAfterDeparture"));
    public static final int WAIT_BEFORE_TERMINAL_FREE
            = Integer.parseInt(prop.getProperty("waitBeforeTerminalFree"));
    public static final int WAIT_BEFORE_DEPARTURE
            = Integer.parseInt(prop.getProperty("waitBeforeNextDeparture"));
    public static final int PASSENGER_WAIT_BEFORE_CHECKING
            = Integer.parseInt(prop.getProperty("passengerWaitForAnotherFlight"));    
    
    
    
    

    private static void loadProperties() {
        try {
            prop.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            Logger.getLogger(PropertyValue.class.getSimpleName()).error(ex);
        }

    }

}
