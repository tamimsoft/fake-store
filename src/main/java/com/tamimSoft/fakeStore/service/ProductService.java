package com.tamimSoft.fakeStore.service;

import com.tamimSoft.fakeStore.entity.Product;
import com.tamimSoft.fakeStore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    final private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    public Product findProductById(Object id) {
        return productRepository.findProductById(id);
    }
    public Product findProductByName(String name) {
        return productRepository.findProductByName(name);
    }
    public List<Product> findProductByUserId(Object userId) {
        return productRepository.findAllByUserId(userId);
    }
}

