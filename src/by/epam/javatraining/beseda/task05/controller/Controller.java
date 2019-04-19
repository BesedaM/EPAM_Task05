package by.epam.javatraining.beseda.task05.controller;

import by.epam.javatraining.beseda.task05.systemconfig.SystemConfiguration;
import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.entity.airport.Airport;
import by.epam.javatraining.beseda.task05.util.creator.AirplaneFactory;
import by.epam.javatraining.beseda.task05.util.creator.AirportCreator;
import by.epam.javatraining.beseda.task05.util.creator.PassengerFactory;
import by.epam.javatraining.beseda.task05.view.LogPrinter;
import by.epam.javatraining.beseda.task05.view.Printer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Beseda
 * @version 1.0 19/04/2019
 */
public class Controller {

    public static void main(String[] args) {

        Printer log = LogPrinter.getPrinter();

        Airport a = AirportCreator.createAirport(SystemConfiguration.TERMINAL_NUMBER);

        Airplane[] aArr = AirplaneFactory.createFilledAirplanesArray(a, SystemConfiguration.AIRPLANE_NUMBER);

        ExecutorService es = Executors.newFixedThreadPool(SystemConfiguration.AIRPLANE_NUMBER + 2);
        for (Airplane aArr1 : aArr) {
            es.execute(aArr1);
        }
        es.shutdown();

        PassengerFactory.getFactory(a);

    }
}
