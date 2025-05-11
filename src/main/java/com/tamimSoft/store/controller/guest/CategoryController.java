package com.tamimSoft.store.controller.guest;

import com.tamimSoft.store.dto.CategoryDTO;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.CategoryService;
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
    public ResponseEntity<ApiResponse<Page<CategoryDTO>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CategoryDTO> categoryDTO = categoryService.getAllCategoryDTOs(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Categories retrieved successfully", categoryDTO));
    }

    @GetMapping("/id")
    @Operation(summary = "Get a category by ID", description = "Retrieve a category by its unique ID.")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(@RequestParam String categoryId
    ) {
        CategoryDTO categoryDTO = categoryService.getCategoryDTOById(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Category retrieved successfully", categoryDTO));

    }
}
