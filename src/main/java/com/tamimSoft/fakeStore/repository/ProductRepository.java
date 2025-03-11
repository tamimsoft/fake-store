package com.tamimSoft.fakeStore.repository;

import com.tamimSoft.fakeStore.entity.Product;
import com.tamimSoft.fakeStore.entity.ProductTag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {
    Optional<Product> findByTagsContaining(ProductTag tag);
}