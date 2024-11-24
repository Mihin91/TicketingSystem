package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.Vendor.Vendor;
import lk.ac.iit.Mihin.Server.Repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    // Add a new vendor
    public String addVendor(Vendor vendor) {
        vendorRepository.save(vendor);
        return "Vendor added successfully";
    }

    // Get vendor by ID
    public Vendor getVendor(Long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        return vendor.orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    // Update vendor by ID
    public String updateVendor(Long id, Vendor vendor) {
        if (vendorRepository.existsById(id)) {
            vendor.setId(id); // Set the vendor's ID to the one passed in the path
            vendorRepository.save(vendor);
            return "Vendor updated successfully";
        } else {
            throw new RuntimeException("Vendor not found");
        }
    }

    // Delete vendor by ID
    public String deleteVendor(Long id) {
        if (vendorRepository.existsById(id)) {
            vendorRepository.deleteById(id);
            return "Vendor deleted successfully";
        } else {
            throw new RuntimeException("Vendor not found");
        }
    }
}
