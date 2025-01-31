package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.dto.LoginDTO;
import com.tamimSoft.fakeStore.dto.SignUpDTO;
import com.tamimSoft.fakeStore.entity.Brand;
import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.entity.Product;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.service.BrandService;
import com.tamimSoft.fakeStore.service.CategoryService;
import com.tamimSoft.fakeStore.service.ProductService;
import com.tamimSoft.fakeStore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class PublicController {

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final UserService userService;

    public PublicController(ProductService productService, BrandService brandService, CategoryService categoryService, UserService userService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping()
    public String healthCheck() {
        //log.info("Health is ok !");
        return "Ok";
    }

    @GetMapping("/products")
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

    @GetMapping("/brands")
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

    @GetMapping("/categories")
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

    @PostMapping("/signup")
    public ResponseEntity<?> userSignUp(@RequestBody SignUpDTO userDTO) {

        try {
            User existUser = userService.findByUserName(userDTO.getUserName());
            if (existUser != null) {
                return ResponseEntity.badRequest().body("User Already Exist!");
            }
            User user = new User();
            user.setUserName(userDTO.getUserName());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setRoles(List.of("USER"));
            user.setCreatedAt(LocalDateTime.now());

            userService.save(user);
            return ResponseEntity.ok("User Sign Up Success!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginDTO userDTO) {
        try {
            User existUser = userService.findByUserName(userDTO.getUserName());
            if (existUser != null) {
                return ResponseEntity.ok("Login Successfully!");
            }
            return ResponseEntity.badRequest().body("User Can't Exist!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
