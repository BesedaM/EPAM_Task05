package by.epam.javatraining.beseda.task05.model.entity;

import by.epam.javatraining.beseda.task05.model.exception.IllegalDestinationException;
import java.util.Objects;

/**
 *
 * @author User
 */
public class Ticket {

    public static int ticketNumber = 0;

    private int number;
    private String destination;
    private boolean isValid;

    {
        this.number = ++ticketNumber;
        this.isValid = true;
    }

    public Ticket() {
    }

    public Ticket(String destination) {
        this.destination = destination;
    }

    public Ticket(Airplane plane) {
        this.destination = plane.getDestination();
    }

    public void setDestination(String destination) throws IllegalDestinationException {
        if (destination != null) {
            this.destination = destination;
        } else {
            throw new IllegalDestinationException("An attempt to assign null "
                    + "value to ticket's destination field");
        }
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public int getTicketNumber() {
        return number;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isValid() {
        return isValid;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.number;
        hash = 23 * hash + Objects.hashCode(this.destination);
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
        final Ticket other = (Ticket) obj;
        if (this.number != other.number) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ticket number " + number + ", destination - " + destination;
    }

}
