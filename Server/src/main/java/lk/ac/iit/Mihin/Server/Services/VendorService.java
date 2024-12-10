// src/main/java/lk/ac/iit/Mihin/Server/Services/VendorService.java
package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.Model.Vendor;
import lk.ac.iit.Mihin.Server.Repositories.VendorRepository;
import lk.ac.iit.Mihin.Server.Runnables.VendorRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final TicketPoolService ticketPoolService;
    private final LogService logService;
    private final Map<Integer, Thread> vendorThreads = new ConcurrentHashMap<>();

    @Autowired
    public VendorService(VendorRepository vendorRepository, TicketPoolService ticketPoolService, LogService logService) {
        this.vendorRepository = vendorRepository;
        this.ticketPoolService = ticketPoolService;
        this.logService = logService;
    }

    /**
     * Starts a vendor thread.
     *
     * @param vendorId          ID of the vendor.
     * @param ticketReleaseRate Interval between ticket releases in milliseconds.
     */
    public void startVendor(int vendorId, int ticketReleaseRate) {
        if (vendorThreads.containsKey(vendorId) && vendorThreads.get(vendorId).isAlive()) {
            logService.addLog("[System] Vendor-" + vendorId + " is already running.");
            return;
        }

        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorId);
        vendor.setTicketReleaseRate(ticketReleaseRate);
        vendorRepository.save(vendor);

        VendorRunnable vendorRunnable = new VendorRunnable(vendorId, ticketReleaseRate, ticketPoolService, logService);
        Thread vendorThread = new Thread(vendorRunnable, "Vendor-" + vendorId);
        vendorThreads.put(vendorId, vendorThread);
        vendorThread.start();
        logService.addLog("[System] Vendor-" + vendorId + " started.");
    }

    /**
     * Stops all vendor threads.
     */
    public void stopAllVendors() {
        for (Map.Entry<Integer, Thread> entry : vendorThreads.entrySet()) {
            Thread thread = entry.getValue();
            thread.interrupt();
            logService.addLog("[System] Vendor-" + entry.getKey() + " stopped.");
        }
        vendorThreads.clear();
    }

    /**
     * Stops a specific vendor thread.
     *
     * @param vendorId ID of the vendor to stop.
     */
    public void stopVendor(int vendorId) {
        Thread thread = vendorThreads.get(vendorId);
        if (thread != null) {
            thread.interrupt();
            vendorThreads.remove(vendorId);
            logService.addLog("[System] Vendor-" + vendorId + " stopped.");
        }
    }

    /**
     * Checks if a specific vendor is running.
     *
     * @param vendorId ID of the vendor.
     * @return true if running, false otherwise.
     */
    public boolean isVendorRunning(int vendorId) {
        return vendorThreads.containsKey(vendorId) && vendorThreads.get(vendorId).isAlive();
    }
}
