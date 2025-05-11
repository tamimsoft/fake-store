package com.tamimSoft.store.controller.admin;

import com.tamimSoft.store.dto.BrandDTO;
import com.tamimSoft.store.entity.Category;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.BrandService;
import com.tamimSoft.store.service.CategoryService;
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
    public ResponseEntity<ApiResponse<Void>> createBrand(@RequestBody BrandDTO brandDTO) {
        if (brandDTO.getCategoryIds() == null || brandDTO.getCategoryIds().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Category IDs cannot be empty", null));
        }

        Set<Category> categories = brandDTO.getCategoryIds().stream()
                .map(categoryService::getCategoryById)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND, "Category not found with IDs: " + brandDTO.getCategoryIds(), null));
        }


        brandService.createBrand(brandDTO);
        log.info("Brand created successfully");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "Brand created successfully", null));
    }

    @PatchMapping()
    @Operation(summary = "Update a brand", description = "Allows admin to update a brand.")
    public ResponseEntity<ApiResponse<Void>> updateBrand(@RequestParam String id, @RequestBody BrandDTO brandDTO) {
        brandService.updateBrand(id, brandDTO);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Brand updated successfully", null));
    }

    @DeleteMapping()
    @Operation(summary = "Delete a brand", description = "Allows admin to delete a brand.")
    public ResponseEntity<Void> deleteBrand(@RequestParam String brandId) {
        brandService.deleteById(brandId);
        return ResponseEntity.noContent().build();
    }
}
