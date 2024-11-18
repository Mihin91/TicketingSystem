package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.Vendor.Vendor;
import lk.ac.iit.Mihin.Server.Services.VendorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // Correct path with @PathVariable
    @GetMapping("/{id}")
    public Vendor getVendor(@PathVariable Long id) {
        return vendorService.getVendor(id);
    }

    @PostMapping
    public String addVendor(@RequestBody Vendor vendor) {
        return vendorService.addVendor(vendor);
    }

    // Correct path with @PathVariable
    @DeleteMapping("/{id}")
    public String deleteVendor(@PathVariable Long id) {
        return vendorService.deleteVendor(id);
    }
}
