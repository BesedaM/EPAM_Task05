package by.epam.javatraining.beseda.task05.model.entity.airport;

import by.epam.javatraining.beseda.task05.model.entity.Airplane;
import by.epam.javatraining.beseda.task05.model.exception.IllegalDestinationException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author User
 */
public class Terminal {

    public static int terminalNumber = 0;

    private int number;
    private Lock lock;
    private boolean forDeparture;
    private String destination;
    private Airplane airplane;

    {
        number = ++terminalNumber;
        lock = new ReentrantLock(true);
    }

    public Terminal() {
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

    public void lockTerminal() {
        this.lock.lock();
    }

    public void unlockTerminal() {
        this.airplane = null;
        this.forDeparture = false;
        this.lock.unlock();
    }

    public void setReadyForDeparture() {
        this.forDeparture = true;
    }

    public void registerAirplane(Airplane a) {
        this.airplane = a;
    }

    public boolean isReadyForDeparture() {
        return this.forDeparture;
    }

    public void setDepartureDestination(String destination) throws IllegalDestinationException {
        if (destination != null) {
            this.destination = destination;
        } else {
            throw new IllegalDestinationException("An attempt to assign "
                    + "null value to terminal destination field");
        }
    }

    public String getDestination() {
        return this.destination;
    }

    public Airplane getAiplane() {
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
