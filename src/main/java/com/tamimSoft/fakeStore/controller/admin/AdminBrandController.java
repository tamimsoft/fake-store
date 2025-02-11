package com.tamimSoft.fakeStore.controller.admin;

import com.tamimSoft.fakeStore.dto.BrandDTO;
import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.response.ApiResponse;
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

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public ResponseEntity<ApiResponse<Brand>> createBrand(@RequestBody BrandDTO brandDTO) {
        if (brandDTO.getCategoryIds() == null || brandDTO.getCategoryIds().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Category IDs cannot be empty", null));
        }

        Set<Category> categories = brandDTO.getCategoryIds().stream()
                .map(categoryService::findCategoryById)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND, "Category not found with IDs: " + brandDTO.getCategoryIds(), null));
        }

        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        brand.setImageUrl(brandDTO.getImageUrl());
        brand.setCategory(categories);

        Brand savedBrand = brandService.saveBrand(brand);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "Brand created successfully", savedBrand));
    }


    @DeleteMapping()
    @Operation(summary = "Delete a brand", description = "Allows admin to delete a brand.")
    public ResponseEntity<?> deleteBrand(@RequestParam String brandId) {
        brandService.deleteById(brandId);
        return ResponseEntity.noContent().build();
    }
}
