package by.epam.javatraining.beseda.task05.controller;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.entity.airport.Airport;
import by.epam.javatraining.beseda.task05.util.creator.AirplaneFactory;
import by.epam.javatraining.beseda.task05.util.creator.AirportCreator;
import by.epam.javatraining.beseda.task05.util.creator.PassengerFactory;
import by.epam.javatraining.beseda.task05.view.LogPrinter;
import by.epam.javatraining.beseda.task05.view.Printer;

/**
 *
 * @author User
 */
public class Controller {

    public static void main(String[] args) {

        Printer log = LogPrinter.getPrinter();

        Airport a = AirportCreator.createAirport(4);

        Airplane[] aArr = AirplaneFactory.createFilledAirplanes(a, 11);

        PassengerFactory.getFactory(a);
    }
}
