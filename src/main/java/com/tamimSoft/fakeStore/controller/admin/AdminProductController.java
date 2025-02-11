package com.tamimSoft.fakeStore.controller.admin;

import com.tamimSoft.fakeStore.dto.ProductDTO;
import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.entity.Product;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.response.ApiResponse;
import com.tamimSoft.fakeStore.service.BrandService;
import com.tamimSoft.fakeStore.service.CategoryService;
import com.tamimSoft.fakeStore.service.ProductService;
import com.tamimSoft.fakeStore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin APIs", description = "Admin-only operations")
public class AdminProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final UserService userService;

    @PostMapping()
    @Operation(summary = "Create a product", description = "Allows admin to create a new product.")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductDTO productDTO) {

        Brand brand = brandService.findBrandById(productDTO.getBrandId());
        Category category = categoryService.findCategoryById(productDTO.getCategoryId());

        Product product = getProduct(productDTO, brand, category);
        Product savedProduct = productService.saveProduct(product);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());

        user.getProducts().add(savedProduct);
        userService.updateUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "Product created successfully", savedProduct));
    }

    private static Product getProduct(ProductDTO productDTO, Brand brand, Category category) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setColors(productDTO.getColors());
        product.setSizes(productDTO.getSizes());
        product.setMaterial(productDTO.getMaterial());
        product.setImageUrls(productDTO.getImageUrls());
        product.setStock(productDTO.getStock());
        product.setBrand(brand);
        product.setCategory(category);
        return product;
    }

    @DeleteMapping()
    @Operation(summary = "Delete a product", description = "Removes a product by its ID.")
    public ResponseEntity<?> deleteProduct(@RequestParam String productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}
