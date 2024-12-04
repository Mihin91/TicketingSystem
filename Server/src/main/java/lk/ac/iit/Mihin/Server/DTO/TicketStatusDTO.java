// src/main/java/lk/ac/iit/Mihin/Server/DTO/TicketStatusDTO.java
package lk.ac.iit.Mihin.Server.DTO;



public class TicketStatusDTO {
    private int currentTickets;
    private int totalTicketsReleased;
    private int totalTicketsPurchased;

    public TicketStatusDTO() {
    }


    public int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    public void setTotalTicketsReleased(int totalTicketsReleased) {
        this.totalTicketsReleased = totalTicketsReleased;
    }

    public void setTotalTicketsPurchased(int totalTicketsPurchased) {
        this.totalTicketsPurchased = totalTicketsPurchased;
    }

    public int getCurrentTickets() {
        return currentTickets;
    }

    public void setCurrentTickets(int currentTickets) {
        this.currentTickets = currentTickets;
    }
}
