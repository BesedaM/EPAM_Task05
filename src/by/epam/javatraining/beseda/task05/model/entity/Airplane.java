package by.epam.javatraining.beseda.task05.model.entity;

import by.epam.javatraining.beseda.task05.model.entity.airport.Airport;
import by.epam.javatraining.beseda.task05.model.exception.AirportLogicException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalAirportValueException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalDestinationException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalPassengerValueException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalSeatNumberException;
import by.epam.javatraining.beseda.task05.systemconfig.PropertyValue;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.log4j.Logger;

/**
 *
 * @author Beseda
 * @version 1.0 19/04/2019
 */
public class Airplane implements Runnable {

    public static int number = 0;
    public static Logger log = Logger.getLogger(Airplane.class.getSimpleName());

    private int planeNumber;
    private AtomicReference destination;
    private int seatsNumber;
    private AtomicReference airport;
    private ConcurrentLinkedQueue<Passenger> passengers;

    private CountDownLatch latch;
    private boolean flag;

    {
        airport = new AtomicReference(null);
        destination = new AtomicReference(null);
        flag = true;
        passengers = new ConcurrentLinkedQueue<>();
        planeNumber = ++number;
    }

    public Airplane(Airport p) {
        airport.lazySet(p);
    }

    public Airplane(Airport p, String destination, int seatsNumber) {
        airport.lazySet(p);
        this.destination.lazySet(destination);
        this.seatsNumber = seatsNumber;
        latch = new CountDownLatch(this.seatsNumber);
    }

    public void setDestination(AtomicReference<String> destination) throws IllegalDestinationException {
        if (destination != null) {
            this.destination.set(destination);
        } else {
            throw new IllegalDestinationException("An attempt to assign "
                    + "illegal destination value to airplane number " + this.planeNumber);
        }
    }

    public int planeNumber() {
        return planeNumber;
    }

    public String getDestination() {
        return destination.get().toString();
    }

    public void setAirport(Airport p) throws IllegalAirportValueException {
        if (p != null) {
            airport.lazySet(p);
        } else {
            throw new IllegalAirportValueException("An attempt to set null value "
                    + "to airport field for airplane");
        }
    }

    public Airport getAirport() {
        return (Airport) this.airport.get();
    }

    public CountDownLatch getLatch() {
        return this.latch;
    }

    public int seatNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int number) throws IllegalSeatNumberException {
        if (number > 0) {
            this.seatsNumber = number;
            latch = new CountDownLatch(this.seatsNumber);
        } else {
            throw new IllegalSeatNumberException();
        }
    }

    public void stopPlane() {
        this.flag = false;
    }

    public boolean loadPassenger(Passenger p) throws AirportLogicException {
        if (p != null) {
            if (!isFull()
                    && (p.getTicket() == null
                    || p.getTicket().getDestination().equals(this.destination.get()))) {
                synchronized (this) {
                    passengers.add(p);
                    latch.countDown();
                }
                return true;
            } else {
                return false;
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

    public boolean isFull() {
        return seatsNumber - passengers.size() < 1;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                if (!passengers.isEmpty()) {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(
                            PropertyValue.WAIT_BEFORE_LANDING));
                    ((Airport) airport.get()).servePlane(this);
                }

                TimeUnit.MILLISECONDS.sleep(PropertyValue.WAIT_BEFORE_DEPARTURE);
                this.destination = new AtomicReference<>(PropertyValue.DESTINATION[new Random()
                        .nextInt(PropertyValue.DESTINATION.length)]);
                ((Airport) airport.get()).servePlane(this);

                TimeUnit.MILLISECONDS.sleep(PropertyValue.TERMINAL_WAIT_AFTER_DEPARTURE);
                flag = false;
            } catch (InterruptedException ex) {
                Logger.getLogger(Airplane.class.getSimpleName()).error(ex);
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 27 * hash + this.planeNumber;
        hash = 27 * hash + Objects.hashCode(this.destination);
        hash = 27 * hash + this.seatsNumber;
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
        if (this.seatsNumber != other.seatsNumber) {
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
