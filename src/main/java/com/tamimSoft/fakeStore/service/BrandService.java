package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Automatically injects BrandRepository via constructor
public class BrandService {

    private final BrandRepository brandRepository;

    /**
     * Saves a brand to the database.
     *
     * @param brand The brand to save.
     * @return The saved brand.
     */
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    /**
     * Retrieves all brands with pagination.
     *
     * @param pageable Pagination information (page number, page size, sorting).
     * @return A page of brands.
     */
    public Page<Brand> findAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    /**
     * Deletes a brand by its ID.
     *
     * @param brandId The ID of the brand to delete.
     * @throws ResourceNotFoundException If the brand is not found.
     */
    public void deleteById(String brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }
        brandRepository.deleteById(brandId);
    }

    /**
     * Finds a brand by its ID.
     *
     * @param brandId The ID of the brand to find.
     * @return The found brand.
     * @throws ResourceNotFoundException If the brand is not found.
     */
    public Brand findBrandById(String brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));
    }
}