package lk.ac.iit.Mihin.CLI;

public abstract class Participant implements Runnable {
    protected final int id;
    protected final TicketPool ticketPool;

    public Participant(int id, TicketPool ticketPool) {
        this.id = id;
        this.ticketPool = ticketPool;
    }

    @Override
    public abstract void run();
}
