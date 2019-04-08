package by.epam.javatraining.beseda.task05.model.entity.airport;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.entity.Passenger;
import by.epam.javatraining.beseda.task05.model.entity.Ticket;
import by.epam.javatraining.beseda.task05.model.exception.AirportLogicException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalWaitingRoomException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class Airport {

    private WaitingRoom waitingRoom;
    private List<Terminal> terminals;

    private static Airport instance = null;

    private Logger log = Logger.getLogger(Airport.class.getSimpleName());

    {
        terminals = new CopyOnWriteArrayList<>();
    }

    private Airport() {
    }

    private Airport(WaitingRoom waitingRoom, int terminalsNumber) {
        this.waitingRoom = waitingRoom;
        for (int i = 0; i < terminalsNumber; i++) {
            terminals.add(new Terminal());
        }
    }

    public static Airport getAirport() {
        if (instance == null) {
            instance = new Airport();
        }
        return instance;
    }

    public static Airport getAirport(WaitingRoom waitingRoom, int terminalsNumber) {
        if (instance == null) {
            instance = new Airport(waitingRoom, terminalsNumber);
        }
        return instance;
    }

    public void setWaitingRoom(WaitingRoom waitingRoom) throws IllegalWaitingRoomException {
        if (waitingRoom != null) {
            this.waitingRoom = waitingRoom;
        } else {
            throw new IllegalWaitingRoomException("An attemp to assing "
                    + "null value as a waiting room to the airport");
        }
    }

    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    public Terminal getFreeTerminal() {
        Terminal t = null;
        for (int i = 0; i < terminals.size(); i++) {
            if (terminals.get(i).isFree()) {
                t = terminals.get(i);
                break;
            }
        }
        return t;
    }

    public Terminal getTerminalByTicket(Ticket p) {
        Terminal t = null;
        for (int i = 0; i < terminals.size(); i++) {
            if (terminals.get(i).getDestination().equals(p.getDestination())) {
                t = terminals.get(i);
                break;
            }
        }
        return t;
    }

    public void addTerminal() {
        terminals.add(new Terminal());
    }

    public void takePassengers(Airplane plane) {
        if (plane != null) {
            int planePassengers = plane.passengersNumber();
            int passengersLeft = 0;
            while (plane.havePassengers()) {
                Passenger p = plane.pollPassenger();
                if (p.getTicket() != null && p.getTicket().isValid()) {
                    try {
                        p.setAirport(this);
                        waitingRoom.addPassenger(p);
                    } catch (AirportLogicException ex) {
                        log.error(ex);
                    }
                } else {
                    passengersLeft++;
                }
            }
            log.trace(passengersLeft + " left the airport");
            log.trace(planePassengers - passengersLeft
                    + " passengers stayed in waiting room");
        }
    }

}
