package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.Vendor.Vendor;
import lk.ac.iit.Mihin.Server.Services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // Corrected endpoint for getting vendor by id
    @GetMapping("/find/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable("id") Long id) {
        Vendor vendor = vendorService.getVendor(id); // Using existing method in service
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    // Add new vendor
    @PostMapping("/add")
    public ResponseEntity<String> addVendor(@RequestBody Vendor vendor) {
        String response = vendorService.addVendor(vendor);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Corrected the mapping for updateVendor to use the correct endpoint
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVendor(@PathVariable("id") Long id, @RequestBody Vendor vendor) {
        String response = vendorService.updateVendor(id, vendor);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete vendor by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable("id") Long id) {
        String response = vendorService.deleteVendor(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
