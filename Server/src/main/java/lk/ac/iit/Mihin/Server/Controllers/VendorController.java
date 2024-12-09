// src/main/java/lk/ac/iit/Mihin/Server/Controllers/VendorController.java

package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.VendorDTO;
import lk.ac.iit.Mihin.Server.Services.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST Controller for Vendor-related operations.
 */
@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    private final VendorService vendorService;
    private final ModelMapper modelMapper;

    @Autowired
    public VendorController(VendorService vendorService, ModelMapper modelMapper) {
        this.vendorService = vendorService;
        this.modelMapper = modelMapper;
    }

    /**
     * Endpoint to start a Vendor.
     *
     * @param vendorDTO The Vendor Data Transfer Object containing vendorId and ticketReleaseRate.
     * @return ResponseEntity with success message or validation errors.
     */
    @PostMapping("/start")
    public ResponseEntity<?> startVendor(@Valid @RequestBody VendorDTO vendorDTO) {
        vendorService.startVendor(
                vendorDTO.getVendorId(),
                vendorDTO.getTicketReleaseRate()
        );
        return ResponseEntity.ok("Vendor started");
    }

}
