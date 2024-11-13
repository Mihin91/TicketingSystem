package lk.ac.iit.Mihin.TicketingSystem.Vendor;

import lk.ac.iit.Mihin.TicketingSystem.Repositories.VendorRepository;
import lk.ac.iit.Mihin.TicketingSystem.TicketPool.TicketPool;
import lk.ac.iit.Mihin.TicketingSystem.database.VendorData;

public class Vendor implements Runnable {
    private final int vendorId;
    private final int ticketsPerRelease;
    private final int releaseInterval;
    private final TicketPool ticketPool;
    private VendorRepository vendorRepository;

    public Vendor(int vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool
    ,VendorRepository vendorRepository) {
        super();
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
        this.vendorRepository = vendorRepository;

    }
    @Override
    public void run() {
        while (true){
            try{
                synchronized (ticketPool){
                    if(ticketPool.hasCapacity()){
                        ticketPool.addTickets(ticketsPerRelease);
                        System.out.println("Vendor "+vendorId+" has released"+ticketsPerRelease+" tickets");

                        vendorRepository.save(new VendorData(vendorId, ticketsPerRelease));

                    } else{
                        System.out.println("vendor "+vendorId+" has exceeded pool capacity");
                        break;
                    }
                }
                Thread.sleep(releaseInterval);

            } catch (InterruptedException e){
                System.out.println("vendor "+vendorId+" is interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
