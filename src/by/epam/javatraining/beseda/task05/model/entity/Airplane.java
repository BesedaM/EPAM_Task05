package by.epam.javatraining.beseda.task05.model.entity;

import by.epam.javatraining.beseda.task05.model.entity.airport.Airport;
import by.epam.javatraining.beseda.task05.model.entity.airport.Terminal;
import by.epam.javatraining.beseda.task05.model.exception.AirportLogicException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalAirportValueException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalDestinationException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalPassengerValueException;
import by.epam.javatraining.beseda.task05.model.exception.NotEnoughSpaceException;
import by.epam.javatraining.beseda.task05.model.logic.Dispatcher;
import by.epam.javatraining.beseda.task05.model.logic.PropertyValue;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class Airplane implements Runnable {

    public static int number = 0;
    public static Logger log = Logger.getLogger(Airplane.class.getSimpleName());

    private int planeNumber;
    private String destination;
    private int seatNumber;
    private Airport airport;
    private ConcurrentLinkedQueue<Passenger> passengers;

    private CountDownLatch latch = new CountDownLatch(this.seatNumber);
    private Thread thread;

    {
        passengers = new ConcurrentLinkedQueue<>();
        this.planeNumber = ++number;
        thread = new Thread(this);
    }

    public Airplane(Airport p) {
        this.airport = p;
    }

    public Airplane(Airport p, String destination, int seatNumber) {
        this.airport = p;
        this.destination = destination;
        this.seatNumber = seatNumber;
    }

    public void setDestination(String destination) throws IllegalDestinationException {
        if (destination != null) {
            this.destination = destination;
        } else {
            throw new IllegalDestinationException("An attempt to assign "
                    + "illegal destination value to airplane number " + this.planeNumber);
        }
    }

    public int planeNumber() {
        return planeNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setAirport(Airport p) throws IllegalAirportValueException {
        if (p != null) {
            this.airport = p;
        } else {
            throw new IllegalAirportValueException("An attempt to set null value "
                    + "to airport field for airplane");
        }
    }

    public Airport getAirport() {
        return this.airport;
    }

    public int seatNumber() {
        return seatNumber;
    }

    public void setPlane() {
        thread.start();
    }

    public boolean deletePlane() {
        if (passengers.isEmpty()) {
            thread.interrupt();
            return true;
        }
        return false;
    }

    public void loadPassenger(Passenger p) throws AirportLogicException {
        if (p != null) {
            if (passengers.size() < this.seatNumber) {
                passengers.add(p);
                latch.countDown();
            } else {
                throw new NotEnoughSpaceException("The plane is full. "
                        + "Impossible to add another passsenger");
            }
        } else {
            throw new IllegalPassengerValueException("An attempt to add null "
                    + "value to plane passengers list");
        }

    }

    public Passenger pollPassenger() {
        return passengers.poll();
    }

    public boolean havePassengers() {
        return !passengers.isEmpty();
    }

    public int passengersNumber() {
        return passengers.size();
    }

    @Override
    public void run() {

        try {
            Terminal t;
            if (!passengers.isEmpty()) {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(
                        PropertyValue.WAIT_BEFORE_LANDING));

                while (true) {
                    t = airport.getFreeTerminal();
                    if (t != null) {
                        t.lockTerminal();
                        break;
                    } else {
                        TimeUnit.MILLISECONDS.sleep(PropertyValue.WAIT_BEFORE_TERMINAL_FREE);
                    }
                }
                log.trace("Plane №" + this.planeNumber + " landed");

                airport.takePassengers(this);
                TimeUnit.MILLISECONDS.sleep(PropertyValue.WAIT_BEFORE_TERMINAL_FREE);
                t.unlockTerminal();
            }

            TimeUnit.MILLISECONDS.sleep(PropertyValue.WAIT_BEFORE_DEPARTURE);

            while (true) {
                t = airport.getFreeTerminal();
                if (t != null) {
                    Dispatcher.setTerminalDestination(t);
                    log.trace("!Plane to " + t.getDestination()
                            + " departures from terminal " + t.getNumber());
                    t.lockTerminal();
                    t.registerAirplane(this);
                    break;
                } else {
                    TimeUnit.MILLISECONDS.sleep(PropertyValue.WAIT_BEFORE_TERMINAL_FREE);
                }
            }
            this.destination = t.getDestination();
            latch.await();
            
            log.trace("The plane to " + this.destination + " has departed...");
            
            TimeUnit.MILLISECONDS.sleep(PropertyValue.TERMINAL_WAIT_AFTER_DEPARTURE);
            t.unlockTerminal();
        } catch (InterruptedException ex) {
            Logger.getLogger(Airplane.class.getSimpleName()).error(ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 27 * hash + this.planeNumber;
        hash = 27 * hash + Objects.hashCode(this.destination);
        hash = 27 * hash + this.seatNumber;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Airplane other = (Airplane) obj;
        if (this.planeNumber != other.planeNumber) {
            return false;
        }
        if (this.seatNumber != other.seatNumber) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Airplane №" + planeNumber + "  to " + destination;
    }

}
