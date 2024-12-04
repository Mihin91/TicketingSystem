package lk.ac.iit.Mihin.Server.Model;

public class TicketStatus {
    private int currentTickets;
    private int remainingTickets;

    // Constructor
    public TicketStatus(int currentTickets, int remainingTickets) {
        this.currentTickets = currentTickets;
        this.remainingTickets = remainingTickets;
    }

    // Getters and Setters
    public int getCurrentTickets() {
        return currentTickets;
    }

    public void setCurrentTickets(int currentTickets) {
        this.currentTickets = currentTickets;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }
}
