package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.entity.Product;
import com.tamimSoft.fakeStore.service.BrandService;
import com.tamimSoft.fakeStore.service.CategoryService;
import com.tamimSoft.fakeStore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PublicController {

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public PublicController(ProductService productService, BrandService brandService, CategoryService categoryService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String healthCheck() {
        //log.info("Health is ok !");
        return "Ok";
    }

    @GetMapping("products")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.findAllProducts();
            if (!products.isEmpty()) {
                return ResponseEntity.ok(products);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("brands")
    public ResponseEntity<?> getAllBrands() {
        try {
            List<Brand> brands = brandService.findAllBrands();
            if (!brands.isEmpty()) {
                return ResponseEntity.ok(brands);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.findAllCategories();
            if (!categories.isEmpty()) {
                return ResponseEntity.ok(categories);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
