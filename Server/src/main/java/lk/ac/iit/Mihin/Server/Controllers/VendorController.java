package lk.ac.iit.Mihin.Server.Controllers;

import lk.ac.iit.Mihin.Server.DTO.VendorDTO;
import lk.ac.iit.Mihin.Server.Services.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/start")
    public ResponseEntity<?> startVendor(@RequestBody VendorDTO vendorDTO) {
        vendorService.startVendor(
                vendorDTO.getVendorId(),
                vendorDTO.getTicketsPerRelease(),
                vendorDTO.getReleaseInterval()
        );
        return ResponseEntity.ok("Vendor started");
    }

    // Additional endpoints for vendors
}
