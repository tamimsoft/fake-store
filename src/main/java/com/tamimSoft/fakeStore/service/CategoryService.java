package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.entity.Category;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Save a new category. to the database.
     *
     * @param category The category to be saved.
     * @return The saved category.
     */
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Retrieves all categories from the database.
     *
     * @param pageable The pageable object for pagination.
     * @return A page of categories.
     */
    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param categoryId The ID of the category to be deleted.
     * @throws ResourceNotFoundException If the category is not found.
     */
    public void deleteById(String categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    /**
     * Finds a category by its ID.
     *
     * @param categoryId The ID of the category to find.
     * @return The found category.
     * @throws ResourceNotFoundException If the category is not found.
     */
    public Category findCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category not found with id: " + categoryId));
    }
}
