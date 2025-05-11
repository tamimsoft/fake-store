package com.tamimSoft.store.controller.admin;

import com.tamimSoft.store.dto.CategoryDTO;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.CategoryService;
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
    public ResponseEntity<ApiResponse<Void>> createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        log.info("Category created successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, "Category created successfully", null));
    }
    @PatchMapping()
    @Operation(summary = "Update a category", description = "Allows admin to update a category.")
    public ResponseEntity<ApiResponse<Void>> updateCategory(@RequestParam String id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Brand updated successfully", null));
    }

    @DeleteMapping()
    @Operation(summary = "Delete a category", description = "Allows admin to delete a category.")
    public ResponseEntity<Void> deleteCategory(@RequestParam String id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
