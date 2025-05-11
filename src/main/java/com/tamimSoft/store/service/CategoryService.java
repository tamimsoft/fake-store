package com.tamimSoft.store.service;

import com.tamimSoft.store.dto.CategoryDTO;
import com.tamimSoft.store.entity.Category;
import com.tamimSoft.store.exception.ResourceNotFoundException;
import com.tamimSoft.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setImageUrl(categoryDTO.getImageUrl());
        categoryRepository.save(category);
    }

    public Page<CategoryDTO> getAllCategoryDTOs(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(
                category -> new CategoryDTO(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getImageUrl()
                )
        );
    }

    public CategoryDTO getCategoryDTOById(String categoryId) {
        return categoryRepository.findById(categoryId).map(
                category -> new CategoryDTO(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getImageUrl()
                )
        ).orElseThrow(() ->
                new ResourceNotFoundException("Category not found with id: " + categoryId));
    }

    public Category getCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category not found with id: " + categoryId));
    }

    public void updateCategory(String id, CategoryDTO categoryDTO) {
        Category categoryToUpdate = getCategoryById(id);
        // Update only non-null fields
        categoryToUpdate.setName(categoryDTO.getName() != null ? categoryDTO.getName() : categoryToUpdate.getName());
        categoryToUpdate.setDescription(categoryDTO.getDescription() != null ? categoryDTO.getDescription() : categoryToUpdate.getDescription());
        categoryToUpdate.setImageUrl(categoryDTO.getImageUrl() != null ? categoryDTO.getImageUrl() : categoryToUpdate.getImageUrl());
        categoryRepository.save(categoryToUpdate);
    }

    public void deleteById(String categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
