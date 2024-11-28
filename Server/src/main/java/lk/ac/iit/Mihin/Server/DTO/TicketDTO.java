package lk.ac.iit.Mihin.Server.DTO;

public class TicketDTO {
    private Long id;
    private int maxCapacity;
    private int currentTickets;
    private int remainingTickets;

    // Constructors
    public TicketDTO() {
        super();
    }

    public TicketDTO(Long id, int maxCapacity, int currentTickets, int remainingTickets) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.currentTickets = currentTickets;
        this.remainingTickets = remainingTickets;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

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
