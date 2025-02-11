package com.tamimSoft.fakeStore.controller.admin;

import com.tamimSoft.fakeStore.dto.CategoryDTO;
import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.response.ApiResponse;
import com.tamimSoft.fakeStore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/category")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin APIs", description = "Admin-only operations")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    @Operation(summary = "Create a category", description = "Allows admin to create a new category.")
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setImageUrl(categoryDTO.getImageUrl());
        Category savedCategory = categoryService.saveCategory(category);
        log.info("Category created successfully: {}", savedCategory);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "Category created successfully", savedCategory));
    }

    @DeleteMapping()
    @Operation(summary = "Delete a category", description = "Allows admin to delete a category.")
    public ResponseEntity<?> deleteCategory(@RequestParam String categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
