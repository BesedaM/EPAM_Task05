package by.epam.javatraining.beseda.task05.model.entity;

import by.epam.javatraining.beseda.task05.model.entity.airport.Airport;
import by.epam.javatraining.beseda.task05.model.entity.airport.Terminal;
import by.epam.javatraining.beseda.task05.model.exception.AirportLogicException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalPassengerNameException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalPassengerSurnameException;
import by.epam.javatraining.beseda.task05.model.exception.IllegalTicketValueException;
import by.epam.javatraining.beseda.task05.model.logic.PropertyValue;
import java.util.Objects;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class Passenger implements Runnable, Comparable<Passenger> {

    private String name;
    private String surname;
    private Ticket ticket;
    private Airport airport;

    private boolean flag;
    private Thread thread;

    Logger log = Logger.getLogger(Passenger.class.getSimpleName());

    {
        flag = true;
        thread = new Thread(this);
    }

    public Passenger() {
    }

    public Passenger(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void setName(String name) throws IllegalPassengerNameException {
        if (name != null) {
            this.name = name;
        } else {
            throw new IllegalPassengerNameException("An attempt to assign "
                    + "null value to passenger's name field");
        }
    }

    public void setSurname(String surname) throws IllegalPassengerSurnameException {
        if (surname != null) {
            this.surname = surname;
        } else {
            throw new IllegalPassengerSurnameException("An attempt to assign "
                    + "null value to passenger's surname field");
        }
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public void addTicket(Ticket ticket) throws IllegalTicketValueException {
        if (ticket != null) {
            this.ticket = ticket;
        } else {
            throw new IllegalTicketValueException("An attempt to assign "
                    + "null value to passenger's ticket list");
        }
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public boolean findTerminal(Terminal t) {
        if (t != null) {
            return this.ticket.getDestination().equals(t.getDestination());
        } else {
            return false;
        }
    }

    public void startAction() {
        if (ticket != null) {
            thread.start();
        }
    }

    public void stopAction() {
        this.flag = false;
    }

    @Override
    public void run() {

        try {
            while (this.flag) {
                for (int i = 0; i < 2; i++) {
                    Terminal t = airport.getTerminalByTicket(ticket);
                    if (t == null) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(PropertyValue.PASSENGER_WAIT_BEFORE_CHECKING);
                        } catch (InterruptedException ex) {
                            log.fatal(ex);
                        }
                    } else {
                        t.getAiplane().loadPassenger(this);
                        this.airport.getWaitingRoom().removePassenger(this);
                        this.flag = false;
                    }
                }
                
                ticket = new Exchanger<Ticket>().exchange(ticket);
                
                log.trace("Passengers exchanged tickets");
                for (int i = 0; i < 2; i++) {
                    Terminal t = airport.getTerminalByTicket(ticket);
                    if (t == null) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(PropertyValue.PASSENGER_WAIT_BEFORE_CHECKING);
                        } catch (InterruptedException ex) {
                            log.fatal(ex);
                        }
                    } else {
                        t.getAiplane().loadPassenger(this);
                        this.flag = false;
                    }
                }
            }
        } catch (InterruptedException | AirportLogicException ex) {
            log.fatal(ex);
        }
    }

    @Override
    public int compareTo(Passenger obj) {
        return this.surname.compareTo(obj.surname);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.surname);
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
        final Passenger other = (Passenger) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Passenger " + name + " " + surname;
    }

}
