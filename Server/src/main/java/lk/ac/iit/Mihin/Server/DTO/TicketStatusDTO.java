
package lk.ac.iit.Mihin.Server.DTO;

public class TicketStatusDTO {
    private int currentTickets;
    private int totalTicketsReleased;
    private int totalTicketsPurchased;

    public TicketStatusDTO() {
    }

    public int getCurrentTickets() {
        return currentTickets;
    }

    public void setCurrentTickets(int currentTickets) {
        this.currentTickets = currentTickets;
    }

    public int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    public void setTotalTicketsReleased(int totalTicketsReleased) {
        this.totalTicketsReleased = totalTicketsReleased;
    }

    public int getTotalTicketsPurchased() {
        return totalTicketsPurchased;
    }

    public void setTotalTicketsPurchased(int totalTicketsPurchased) {
        this.totalTicketsPurchased = totalTicketsPurchased;
    }
}
