package com.tamimSoft.fakeStore.controller.admin;

import com.tamimSoft.fakeStore.dto.BrandDTO;
import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.service.BrandService;
import com.tamimSoft.fakeStore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/brand")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin APIs", description = "Admin-only operations")
public class AdminBrandController {

    private final BrandService brandService;
    private final CategoryService categoryService;

    @PostMapping()
    @Operation(summary = "Create a brand", description = "Allows admin to create a new brand.")
    public ResponseEntity<Brand> createBrand(@RequestBody BrandDTO brandDTO) {
        Category category = categoryService.findCategoryById(brandDTO.getCategoryId());
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with id: " + brandDTO.getCategoryId());
        }
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        brand.setImageUrl(brandDTO.getImageUrl());
        brand.setCategory(Set.of(category));
        Brand savedBrand = brandService.saveBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    }

    @DeleteMapping("/id")
    @Operation(summary = "Delete a brand", description = "Allows admin to delete a brand.")
    public ResponseEntity<?> deleteBrand(@RequestParam String brandId) {
        brandService.deleteById(brandId);
        return ResponseEntity.noContent().build();
    }
}
