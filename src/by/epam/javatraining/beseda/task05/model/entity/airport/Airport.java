package by.epam.javatraining.beseda.task05.model.entity.airport;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.entity.Passenger;
import by.epam.javatraining.beseda.task05.model.exception.AirportLogicException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalWaitingRoomException;
import by.epam.javatraining.beseda.task05.model.logic.PropertyValue;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
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
            terminals.add(new Terminal(this));
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

    public void servePlane(Airplane a) throws InterruptedException {
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < this.terminals.size(); i++) {
                if (a.havePassengers()) {
                    if (this.terminals.get(i).workWithLandedPlane(a)) {
                        flag = false;
                    }
                } else {
                    if (this.terminals.get(i).workWithDeparturingPlane(a)) {
                        TimeUnit.MILLISECONDS.sleep(PropertyValue.WAIT_BEFORE_TERMINAL_FREE);
                        flag = false;
                    }
                }
            }
        }
    }

    public List<Terminal> getTerminalList() {
        return this.terminals;
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
            log.info(planePassengers - passengersLeft
                    + " passengers stayed in waiting room");
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(PropertyValue.WAITING_TIME));
            } catch (InterruptedException ex) {
                log.error(ex);
            }
            log.info(passengersLeft + " passengers left the airport");
        }
    }

}
