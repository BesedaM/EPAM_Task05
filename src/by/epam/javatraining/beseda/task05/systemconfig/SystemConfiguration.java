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
public class SystemConfiguration {

    private static final Properties PROP;
    public static final int TERMINAL_NUMBER;
    public static final int AIRPLANE_NUMBER;

    static {
        PROP = new Properties();
        loadProperties();
        TERMINAL_NUMBER = Integer.parseInt(PROP.getProperty("terminalNumber").trim());
        AIRPLANE_NUMBER = Integer.parseInt(PROP.getProperty("planeNumber").trim());
    }

    private static void loadProperties() {
        try {
            PROP.load(new FileInputStream("src\\by\\epam\\javatraining\\beseda\\task05\\systemconfig\\systemconfig.properties"));
        } catch (IOException ex) {
            Logger.getLogger(SystemConfiguration.class.getSimpleName()).error(ex);
        }

    }
}
