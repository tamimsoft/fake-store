package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.entity.Product;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    final private ProductRepository productRepository;

    /**
     * Saves a product to the database.
     *
     * @param product The product to save.
     * @return The saved product.
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Retrieves all products with pagination.
     *
     * @param pageable Pagination information (page number, page size, sorting).
     * @return A page of products.
     */
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * Deletes a Product by its ID.
     *
     * @param productId The ID of the product to delete.
     * @throws ResourceNotFoundException If the product is not found.
     */
    public void deleteById(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    public Product findProductById(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }
}

