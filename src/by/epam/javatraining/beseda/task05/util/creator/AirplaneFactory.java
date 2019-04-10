package by.epam.javatraining.beseda.task05.util.creator;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.entity.Passenger;
import by.epam.javatraining.beseda.task05.model.entity.airport.Airport;
import by.epam.javatraining.beseda.task05.model.exception.AirportLogicException;
import java.util.Random;
import by.epam.javatraining.beseda.task05.model.logic.PropertyValue;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class AirplaneFactory {

    public static int[] AIRPLANE_SEAT_NUMBER = {20, 30, 40};

    public static Airplane createAirplane(Airport a) {
        String destination = PropertyValue.DESTINATION[new Random().nextInt(PropertyValue.DESTINATION.length)];
        int seatNumber = AIRPLANE_SEAT_NUMBER[new Random().nextInt(AIRPLANE_SEAT_NUMBER.length)];
        return new Airplane(a, destination, seatNumber);
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

    public static Airplane[] createFilledAirplanes(Airport a, int airplaneNumber) {
        Airplane[] arr = new Airplane[airplaneNumber];
        for (int i = 0; i < airplaneNumber; i++) {
            try {
                arr[i] = createFilledAirplane(a);
            } catch (AirportLogicException ex) {
                Logger.getLogger(AirplaneFactory.class.getSimpleName()).fatal(ex);
            }
        }
        return arr;
    }

    public static Airplane[] createSeveralAirplanes(Airport a, int airplaneNumber) {
        Airplane[] arr = new Airplane[airplaneNumber];
        for (int i = 0; i < airplaneNumber; i++) {
            arr[i] = createAirplane(a);
        }
        return arr;
    }
}
