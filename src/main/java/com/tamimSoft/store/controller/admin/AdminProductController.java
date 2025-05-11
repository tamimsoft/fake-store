package com.tamimSoft.store.controller.admin;

import com.tamimSoft.store.dto.ProductDTO;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin APIs", description = "Admin-only operations")
public class AdminProductController {
    private final ProductService productService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping()
    @Operation(summary = "Create a product", description = "Allows admin to create a new product.")
    public ResponseEntity<ApiResponse<Void>> createProduct(@RequestBody ProductDTO productDTO) {
        productService.createProduct(productDTO, getAuthenticatedUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "Product created successfully", null));
    }

    @PatchMapping()
    @Operation(summary = "Update a product", description = "Allows admin to update a product.")
    public ResponseEntity<ApiResponse<Void>> updateProduct(
            @RequestParam String id,
            @RequestBody ProductDTO productDTO
    ) {
        productService.updateProduct(id, productDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "Product updated successfully", null));
    }

    @DeleteMapping()
    @Operation(summary = "Delete a product", description = "Removes a product by its ID.")
    public ResponseEntity<Void> deleteProduct(@RequestParam String productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}
