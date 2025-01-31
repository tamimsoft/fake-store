package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.dto.CategoryDTO;
import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        if (isAuthenticated) {
            try {
                Category category = new Category();
                category.setName(categoryDTO.getName());
                category.setDescription(categoryDTO.getDescription());
                category.setImageUrl(categoryDTO.getImageUrl());
                categoryService.saveCategory(category);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/id/{brandId}")
    public ResponseEntity<?> deleteCategory(@PathVariable ObjectId brandId) {
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        if (isAuthenticated) {
            try {
                categoryService.deleteById(brandId);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
