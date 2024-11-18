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

    public String addVendor(Vendor vendor) {
        vendorRepository.save(vendor);
        return "Vendor added successfully";
    }

    public Vendor getVendor(Long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        return vendor.orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    public String deleteVendor(Long id) {
        if (vendorRepository.existsById(id)) {
            vendorRepository.deleteById(id);
            return "Vendor deleted successfully";
        } else {
            throw new RuntimeException("Vendor not found");
        }
    }
}
