package lk.ac.iit.Mihin.TicketingSystem.TicketPool;


public class TicketPool {
    private Long id;
    private int currentTickets;
    private int maxCapacity;

    public TicketPool() {
        super();
    }

    public TicketPool(int maxCapacity) {
        super();
        this.maxCapacity = maxCapacity;
        this.currentTickets = 0;
    }

    public synchronized void addTickets(int tickets){
        if(currentTickets + tickets <= maxCapacity){
            currentTickets += tickets;
            System.out.println("added " + tickets + "no of tickets to the pool");
        } else{
            System.out.println("Cannot add more tickets, maximum capacity reached");
        }
    }

    public synchronized boolean removeTicket(){
        if(currentTickets > 0){
            currentTickets--;
            System.out.println("removed a ticket from the pool");
            return true;
        } else{
            System.out.println("No tickets left to remove from the pool");
            return false;
        }
    }

    public synchronized boolean hasTickets(){
        return currentTickets > 0;
    }

    public synchronized boolean hasCapacity(){
        return currentTickets < maxCapacity;
    }

    public synchronized int getCurrentTickets(){
        return currentTickets;
    }
}
