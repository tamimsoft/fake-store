package com.tamimSoft.store.repository;

import com.tamimSoft.store.entity.Product;
import com.tamimSoft.store.entity.ProductTag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {
    Optional<Product> findByTagsContaining(ProductTag tag);
}