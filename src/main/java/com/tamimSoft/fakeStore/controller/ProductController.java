package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.entity.Product;
import com.tamimSoft.fakeStore.response.ApiResponse;
import com.tamimSoft.fakeStore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Public APIs")
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    @Operation(summary = "Get all products", description = "Allows admin to create a new brand.")
    public ResponseEntity<ApiResponse<Page<Product>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Product> products = productService.findAllProducts(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Products retrieved successfully", products));
    }

    @GetMapping("/id")
    @Operation(summary = "Get a product by ID", description = "Retrieve a product by its unique ID.")
    public ResponseEntity<ApiResponse<Product>> getProductById(@RequestParam String productId) {
        Product product = productService.findProductById(productId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Product retrieved successfully", product));
    }
}
