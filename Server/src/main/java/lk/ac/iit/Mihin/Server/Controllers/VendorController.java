package lk.ac.iit.Mihin.Server.Controllers;

import jakarta.validation.Valid;
import lk.ac.iit.Mihin.Server.DTO.VendorDTO;
import lk.ac.iit.Mihin.Server.Services.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> startVendor(@Valid @RequestBody VendorDTO vendorDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getFieldErrors().forEach(error ->
                    errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
            );
            return ResponseEntity.badRequest().body("Validation errors: " + errors.toString());
        }
        vendorService.startVendor(vendorDTO.getVendorId(), vendorDTO.getTicketReleaseRate());
        return ResponseEntity.ok("Vendor started");
    }
}
