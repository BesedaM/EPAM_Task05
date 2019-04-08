package by.epam.javatraining.beseda.task05.util.creator;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.entity.Passenger;
import by.epam.javatraining.beseda.task05.model.entity.airport.Airport;
import by.epam.javatraining.beseda.task05.model.exception.AirportLogicException;
import java.util.Random;
import by.epam.javatraining.beseda.task05.model.logic.PropertyValue;

/**
 *
 * @author User
 */
public class AirplaneFactory {

    public static int[] AIRPLANE_SEAT_NUMBER = {30, 40, 50};

    public static Airplane createAirplane(Airport a) {
        return new Airplane(a, PropertyValue.DESTINATION[
                new Random().nextInt(PropertyValue.DESTINATION.length)],
                AIRPLANE_SEAT_NUMBER[new Random().nextInt(AIRPLANE_SEAT_NUMBER.length)]);
    }

    public static Airplane createFilledAirplane(Airport a) throws AirportLogicException {
        Airplane plane = createAirplane(a);
        for (int i = 0; i < plane.seatNumber(); i++) {
            Passenger p;
            boolean val = new Random().nextBoolean();
            if (val) {
                p = PassengerFactory.createPassengerWithTicket();
            } else {
                p = PassengerFactory.createPassenger();
            }
            plane.loadPassenger(p);
        }
        return plane;
    }
}
