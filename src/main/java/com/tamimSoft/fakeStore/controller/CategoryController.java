package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.response.ApiResponse;
import com.tamimSoft.fakeStore.service.CategoryService;
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

@RestController
@Slf4j
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Public APIs")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories.")
    public ResponseEntity<ApiResponse<Page<Category>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Category> categories = categoryService.findAllCategories(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Categories retrieved successfully", categories));

    }

    @GetMapping("/id")
    @Operation(summary = "Get a category by ID", description = "Retrieve a category by its unique ID.")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@RequestParam String categoryId
    ) {
        Category category = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Categories retrieved successfully", category));

    }
}
