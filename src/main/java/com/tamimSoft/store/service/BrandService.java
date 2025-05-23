package com.tamimSoft.store.service;

import com.tamimSoft.store.dto.BrandDto;
import com.tamimSoft.store.entity.Brand;
import com.tamimSoft.store.entity.Category;
import com.tamimSoft.store.exception.ResourceNotFoundException;
import com.tamimSoft.store.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Automatically injects BrandRepository via constructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final CategoryService categoryService;


    public void createBrand(BrandDto brandDTO) {
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        brand.setImageUrl(brandDTO.getImageUrl());
        brand.setCategory(brandDTO.getCategoryIds()
                .stream()
                .map(categoryService::getCategoryById)
                .collect(Collectors.toSet())
        );
        brandRepository.save(brand);
    }

    public Page<BrandDto> getAllBrandDTOs(Pageable pageable) {
        return brandRepository.findAll(pageable).map(
                brand -> new BrandDto(
                        brand.getId(),
                        brand.getName(),
                        brand.getDescription(),
                        brand.getImageUrl(),
                        brand.getCategory().stream().map(
                                Category::getId
                        ).collect(Collectors.toSet())

                )
        );
    }

    public BrandDto getBrandDTOById(String brandId) {
        return brandRepository.findById(brandId).map(
                        brand -> new BrandDto(
                                brand.getId(),
                                brand.getName(),
                                brand.getDescription(),
                                brand.getImageUrl(),
                                brand.getCategory().stream().map(
                                        Category::getId
                                ).collect(Collectors.toSet())

                        )
                )
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));
    }

    public Brand getBrandById(String brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));
    }

    public void updateBrand(String brandId, BrandDto brandDTO) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));

        // Update only non-null fields
        if (brandDTO.getName() != null) {
            brand.setName(brandDTO.getName());
        }
        if (brandDTO.getDescription() != null) {
            brand.setDescription(brandDTO.getDescription());
        }
        if (brandDTO.getImageUrl() != null) {
            brand.setImageUrl(brandDTO.getImageUrl());
        }
        if (brandDTO.getCategoryIds() != null && !brandDTO.getCategoryIds().isEmpty()) {
            brand.setCategory(
                    brandDTO.getCategoryIds().stream()
                            .map(categoryService::getCategoryById)
                            .collect(Collectors.toSet())
            );
        }

        brandRepository.save(brand);
    }

    public void deleteById(String brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }
        brandRepository.deleteById(brandId);
    }


}