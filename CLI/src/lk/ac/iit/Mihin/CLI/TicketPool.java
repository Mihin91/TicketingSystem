package lk.ac.iit.Mihin.CLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final List<Integer> tickets;
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }

    public void addTickets(int ticketCount) {
        synchronized (tickets) {
            if (tickets.size() + ticketCount <= maxCapacity) {
                for(int i =0; i < ticketCount; i++){
                    tickets.add(i);
                }
                System.out.println("Added " + tickets + " tickets to the pool");
            } else {
                System.out.println("Cannot add more tickets, maximum capacity reached");
            }
        }
    }

    public boolean removeTicket() {
        synchronized (tickets) {
            if (!tickets.isEmpty()) {
                tickets.remove(0);
                System.out.println("Removed a ticket from the pool");
                return true;
            } else {
                System.out.println("No tickets left to remove from the pool");
                return false;
            }
        }
    }
// checking if there are tickets available
    public boolean hasTickets() {
        synchronized (tickets){
            return !tickets.isEmpty();
        }
    }

    // checking if their is more space to add more tickets
    public boolean hasCapacity() {
        synchronized (tickets) {
            return tickets.size() < maxCapacity;
        }
    }

    public int getCurrentTickets() {
        synchronized (tickets) {
            return tickets.size();
        }
    }
}
