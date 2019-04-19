package by.epam.javatraining.beseda.task05.model.entity.airport;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.exception.IllegalDestinationException;
import by.epam.javatraining.beseda.task05.systemconfig.PropertyValue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 *
 * @author Beseda
 * @version 1.0 19/04/2019
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
        destination = new AtomicReference<>();
        forDeparture = new AtomicBoolean(false);
        airplane = new AtomicReference<>();
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

    public synchronized boolean isReadyForDeparture() {
        return this.forDeparture.get();
    }

    public void setDepartureDestination(AtomicReference<String> destination)
            throws IllegalDestinationException {
        if (destination.get() != null) {
            this.destination = destination;
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
                log.info("Plane from " + plane.getDestination() + "(№" + plane.planeNumber() + ") landed ");
                TimeUnit.MILLISECONDS.sleep(PropertyValue.WAITING_TIME);
                airport.takePassengers(plane);
                TimeUnit.MILLISECONDS.sleep(PropertyValue.WAIT_BEFORE_TERMINAL_FREE);
            } catch (InterruptedException ex) {
                log.fatal(ex);
            } finally {
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
                airplane.set(a);
                this.destination.set(a.getDestination());

                forDeparture.set(true);

                log.info("ATTENTION! Plane to " + this.destination
                        + " departures from terminal №" + this.number);
                TimeUnit.MILLISECONDS.sleep(PropertyValue.WAITING_TIME);

                a.getLatch().await();
                TimeUnit.MILLISECONDS.sleep(PropertyValue.WAITING_TIME);
                forDeparture.set(false);
                TimeUnit.MILLISECONDS.sleep(100);
                airplane.set(null);
                log.info("...Plane to " + this.destination + "(№" + a.planeNumber()
                        + ") departured with " + a.passengersNumber() + " passangers onboard");
            } catch (InterruptedException ex) {
                log.fatal(ex);
            } finally {
                lock.unlock();
            }
            flag = true;
        }
        return flag;
    }

    public String getDestination() {
        return this.destination.get();
    }

    public AtomicReference<Airplane> getAiplane() {
        return this.airplane;
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
