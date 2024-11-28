package lk.ac.iit.Mihin.Server.Services;

import lk.ac.iit.Mihin.Server.DTO.VendorDTO;
import lk.ac.iit.Mihin.Server.Model.Vendor;
import lk.ac.iit.Mihin.Server.Repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    @Autowired
    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    // Method to save or update Vendor
    public VendorDTO saveVendor(VendorDTO vendorDTO) {
        Vendor vendor = new Vendor(vendorDTO.getName());
        vendor = vendorRepository.save(vendor);
        return new VendorDTO(vendor.getId(), vendor.getName());
    }

    // Method to get Vendor by ID
    public Optional<VendorDTO> getVendor(Long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        return vendor.map(v -> new VendorDTO(v.getId(), v.getName()));
    }

    // Method to delete Vendor by ID
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    // Method to get all vendors (optional)
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(v -> new VendorDTO(v.getId(), v.getName()))
                .collect(Collectors.toList());
    }
}
