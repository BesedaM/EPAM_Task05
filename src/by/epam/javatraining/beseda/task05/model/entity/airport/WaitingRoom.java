package by.epam.javatraining.beseda.task05.model.entity.airport;

import by.epam.javatraining.beseda.task05.model.entity.Passenger;
import by.epam.javatraining.beseda.task05.model.exception.AirportLogicException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalCapacityException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalPassengerValueException;
import by.epam.javatraining.beseda.task05.model.exception.NotEnoughSpaceException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author User
 */
public class WaitingRoom {

    private int capacity;
    private Queue<Passenger> passengers;
    private AtomicInteger passengersNumber;

    {
        passengersNumber = new AtomicInteger(0);
        passengers = new ConcurrentLinkedQueue<>();
    }

    public WaitingRoom() {
    }

    public WaitingRoom(int capacity) {
        this.capacity = capacity;
    }

    public void setCapacity(int capacity) throws IllegalCapacityException {
        if (capacity > 0) {
            this.capacity = capacity;
        } else {
            throw new IllegalCapacityException("An attempt to set a negative "
                    + "waiting room capacity");
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public int passengersNumber() {
        return passengersNumber.get();
    }

    public void addPassenger(Passenger p) throws AirportLogicException {
        if (p != null) {
            if (passengers.size() < this.capacity) {
                passengers.add(p);
                passengersNumber.addAndGet(1);
                p.startAction();
            } else {
                throw new NotEnoughSpaceException("Not enough space in waiting room "
                        + "for adding another passenger");
            }
        } else {
            throw new IllegalPassengerValueException("An attempt to add "
                    + "null value passenger to waiting room");
        }
    }

    public void removePassenger(Passenger p) throws IllegalPassengerValueException {
        if (p != null) {
            this.passengers.remove(p);
        } else {
            throw new IllegalPassengerValueException();
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.capacity;
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
        final WaitingRoom other = (WaitingRoom) obj;
        if (this.capacity != other.capacity) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WaitingRoom for " + capacity + " passengers";
    }

}
