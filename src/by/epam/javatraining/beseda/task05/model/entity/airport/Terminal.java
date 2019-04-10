package by.epam.javatraining.beseda.task05.model.entity.airport;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.exception.IllegalDestinationException;
import by.epam.javatraining.beseda.task05.model.logic.Dispatcher;
import by.epam.javatraining.beseda.task05.model.logic.PropertyValue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class Terminal {

    public static int terminalNumber = 0;
    public static Logger log = Logger.getLogger(Terminal.class.getSimpleName());

    private int number;
    private Airport airport;
    private Lock lock;
    private AtomicBoolean forDeparture;
    private AtomicReference<String> destination;
    private AtomicReference<Airplane> airplane;

    {
        forDeparture = new AtomicBoolean(false);
        airplane = new AtomicReference<>(null);
        number = ++terminalNumber;
        lock = new ReentrantLock(true);
    }

    public Terminal() {
    }

    public Terminal(Airport a) {
        this.airport = a;
    }

    public static int getTerminalsNumber() {
        return terminalNumber;
    }

    public int getNumber() {
        return number;
    }

    public boolean isFree() {
        return lock.tryLock();
    }

    public void setAirport(Airport a) {
        this.airport = a;
    }

    public void setReadyForDeparture() {
        this.forDeparture.lazySet(true);
    }

    public void registerAirplane(Airplane a) {
        this.airplane.set(a);
    }

    public void deregisterAirplane(Airplane a) {
        this.forDeparture.lazySet(false);
        this.airplane = null;
    }

    public boolean isReadyForDeparture() {
        return this.forDeparture.get();
    }

    public void setDepartureDestination(String destination) throws IllegalDestinationException {
        if (destination != null) {
            this.destination.set(destination);
        } else {
            throw new IllegalDestinationException("An attempt to assign "
                    + "null value to terminal destination field");
        }
    }

    public boolean workWithLandedPlane(Airplane plane) {
        boolean flag = false;
        if (lock.tryLock()) {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(PropertyValue.WAITING_TIME));
                log.info("Plane №" + plane.planeNumber() + " landed " + this);
                TimeUnit.MILLISECONDS.sleep(PropertyValue.WAITING_TIME);
                airport.takePassengers(plane);
                TimeUnit.MILLISECONDS.sleep(PropertyValue.WAIT_BEFORE_TERMINAL_FREE);
            } catch (InterruptedException ex) {
                log.fatal(ex);
            } finally {
                deregisterAirplane(plane);
                lock.unlock();
            }
            flag = true;
        }
        return flag;
    }

    public boolean workWithDeparturingPlane(Airplane a) {
        boolean flag = false;
        if (lock.tryLock()) {
            try {
                a.setDestination(destination);
                log.info(this.airplane.get() + " airplane set");
                airplane.set(a);

                Dispatcher.setTerminalDestination(this);

                this.setReadyForDeparture();

                log.info("!Plane to " + this.destination
                        + " departures from terminal " + this.number);
                TimeUnit.MILLISECONDS.sleep(PropertyValue.WAITING_TIME);

                a.getLatch().await();

                log.info("The plane to " + this.destination + " has departured...");
            } catch (InterruptedException | IllegalDestinationException ex) {
                log.fatal(ex);
            } finally {
                deregisterAirplane(a);
                lock.unlock();
            }
            flag = true;
        }
        return flag;
    }

    public String getDestination() {
        return this.destination.get();
    }

    public Airplane getAiplane() {
        return (Airplane) this.airplane.get();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.number;
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
        final Terminal other = (Terminal) obj;
        if (this.number != other.number) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Terminal №" + number;
    }

}
