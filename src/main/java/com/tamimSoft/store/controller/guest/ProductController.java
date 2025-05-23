package com.tamimSoft.store.controller.guest;

import com.tamimSoft.store.dto.ProductDto;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Public APIs")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    @Operation(summary = "Get all products", description = "Allows admin to create a new brand.")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String brandId,
            @RequestParam(required = false) Set<String> tagIds
    ) {
        Page<ProductDto> productDTOPage = productService.getAllProductDTOs(PageRequest.of(page, size), categoryId, brandId, tagIds);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Products retrieved successfully", productDTOPage));
    }

    @GetMapping("/id")
    @Operation(summary = "Get a product by ID", description = "Retrieve a product by its unique ID.")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@RequestParam String productId) {
        ProductDto productDTO = productService.getProductDTOById(productId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Product retrieved successfully", productDTO));
    }
}
