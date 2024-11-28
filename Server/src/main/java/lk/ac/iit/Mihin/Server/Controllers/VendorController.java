package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.VendorDTO;
import lk.ac.iit.Mihin.Server.Services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // Create or update a vendor
    @PostMapping
    public ResponseEntity<VendorDTO> createOrUpdateVendor(@RequestBody VendorDTO vendorDTO) {
        VendorDTO savedVendor = vendorService.saveVendor(vendorDTO);
        return ResponseEntity.ok(savedVendor);
    }

    // Get a vendor by ID
    @GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendor(@PathVariable Long id) {
        Optional<VendorDTO> vendor = vendorService.getVendor(id);
        return vendor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all vendors
    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<VendorDTO> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    // Delete a vendor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
