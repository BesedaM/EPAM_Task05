package by.epam.javatraining.beseda.task05.util.creator;

import by.epam.javatraining.beseda.task05.model.entity.airport.Airport;
import by.epam.javatraining.beseda.task05.model.entity.airport.WaitingRoom;

/**
 *
 * @author Beseda
 * @version 1.0 19/04/2019
 */
public class AirportCreator {

    public static int WAITING_ROOM_CAPACITY = 500;

    public static Airport createAirport(int terminalNumber) {
        return Airport.getAirport(new WaitingRoom(WAITING_ROOM_CAPACITY),terminalNumber);
    }
}
