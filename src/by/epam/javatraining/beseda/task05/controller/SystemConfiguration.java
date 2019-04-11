package by.epam.javatraining.beseda.task05.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class SystemConfiguration {

    private static final Properties PROP;
    public static final int TERMINAL_NUMBER;
    public static final int AIRPLANE_NUMBER;

    static {
        PROP = new Properties();
        loadProperties();
        TERMINAL_NUMBER = Integer.parseInt(PROP.getProperty("terminalNumber"));
        AIRPLANE_NUMBER = Integer.parseInt(PROP.getProperty("planeNumber"));
    }

    private static void loadProperties() {
        try {
            PROP.load(new FileInputStream("src//systemconfig.properties"));
        } catch (IOException ex) {
            Logger.getLogger(SystemConfiguration.class.getSimpleName()).error(ex);
        }

    }
}
