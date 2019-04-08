package by.epam.javatraining.beseda.task05.view;

import org.apache.log4j.Logger;

/**
 *
 * @author Beseda
 * @version 1.0 07/03/2019
 */
public class LogPrinter implements Printer {

    private static Logger logger;
    private static Printer printer = null;

    private LogPrinter() {
        logger = Logger.getRootLogger();
    }

    public static Printer getPrinter() {
        if (printer == null) {
            printer = new LogPrinter();
        }
        return printer;
    }

    @Override
    public void print(Object obj) {
        logger.info(obj.toString());
    }
}
