package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Page<Category>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Category> categories = categoryService.findAllCategories(PageRequest.of(page, size));
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/id")
    public ResponseEntity<Category> getCategoryById(@RequestParam String categoryId
    ) {
        Category category = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }
}
