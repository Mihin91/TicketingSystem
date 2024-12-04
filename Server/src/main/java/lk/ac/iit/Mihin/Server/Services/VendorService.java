package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.Model.TicketPool;
import lk.ac.iit.Mihin.Server.Model.Vendor;
import lk.ac.iit.Mihin.Server.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final TicketPool ticketPool;
    private final Map<Integer, Thread> vendorThreads = new ConcurrentHashMap<>();

    @Autowired
    public VendorService(VendorRepository vendorRepository, TicketPool ticketPool) {
        this.vendorRepository = vendorRepository;
        this.ticketPool = ticketPool;
    }

    public void startVendor(int vendorId, int ticketsPerRelease, int releaseInterval) {
        Vendor vendor = new Vendor(vendorId, ticketsPerRelease, releaseInterval, ticketPool);
        Thread vendorThread = new Thread(vendor);
        vendorThreads.put(vendorId, vendorThread);
        vendorThread.start();
        // Save vendor to the repository if needed
        vendorRepository.save(vendor);
    }

    public void stopAllVendors() {
        for (Map.Entry<Integer, Thread> entry : vendorThreads.entrySet()) {
            Thread thread = entry.getValue();
            thread.interrupt();
        }
        vendorThreads.clear();
    }

    public void stopVendor(int vendorId) {
        Thread thread = vendorThreads.get(vendorId);
        if (thread != null) {
            thread.interrupt();
            vendorThreads.remove(vendorId);
        }
    }

    // Additional methods for managing vendors
}
