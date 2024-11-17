package lk.ac.iit.Mihin.Server.Controllers;


import lk.ac.iit.Mihin.Server.Vendor.VendorEntity;
import lk.ac.iit.Mihin.Server.Services.VendorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    public String addVendor(@RequestBody VendorEntity vendor) {
        return vendorService.addVendor(vendor);
    }

    @GetMapping("/{id}")
    public VendorEntity getVendor(@PathVariable Long id) {
        return vendorService.getVendor(id);
    }

    @DeleteMapping("/{id}")
    public String deleteVendor(@PathVariable Long id) {
        return vendorService.deleteVendor(id);
    }
}
